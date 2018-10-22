package com.otn.facade.impl;


import com.otn.dao.ResBussinessDao;
import com.otn.entity.ResBussiness;
import com.otn.facade.BussinessService;
import com.otn.facade.OSNRCalculator.Calculable;
import com.otn.facade.OSNRCalculator.exceptions.OutOfInputLimitsException;
import com.otn.facade.OSNRService;
import com.otn.facade.VersionBackUpService;
import com.otn.facade.util.BussinessPowerStringTransfer;
import com.otn.pojo.BussinessCreateInfo;
import com.otn.pojo.BussinessDTO;
import com.otn.pojo.OSNRGeneralInfo;
import com.otn.util.exception.controller.result.NoneGetException;
import com.otn.util.exception.controller.result.NoneRemoveException;
import com.otn.util.exception.controller.result.NoneSaveException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.stream.Collectors;

@Service("bussinessService")
class BussinessServiceImpl implements BussinessService {
    private static Logger logger = LoggerFactory.getLogger(BussinessServiceImpl.class);
    private final UpdateUtils UPDATE_UTILS = new UpdateUtils();
    @Resource
    private ResBussinessDao resBussinessDao;
    @Resource
    private Calculable calculator;
    @Resource
    private VersionBackUpService versionBackUpService;
    @Resource
    private OSNRService osnrService;

    @Override
    public List<BussinessDTO> listBussiness(Long versionId) {//parallelStream
        List<ResBussiness> list = resBussinessDao.selectByExample(getExample(versionId));
        //list.stream().filter(ResBussiness::getIsValid).collect(Collectors.toList());
        List<BussinessDTO> result = list.stream().sorted(Comparator
                .comparing(ResBussiness::getGmtModified).reversed()).map(this::busFilter).map(this::createBussinessDTO).collect(Collectors.toList());
        if (result.size() == 0) {
            throw new NoneGetException("没有查询到光通道相关记录！");
        }

        //保存计算的osnr值
        versionBackUpService.saveBackUpBussiness(versionId);
        return result;
    }

    private ResBussiness busFilter(ResBussiness bus) {
        if (bus.getIsValid() == null) {
            calculateORSR(bus);
            resBussinessDao.updateByPrimaryKey(bus);
        }
        return bus;
    }

    private void calculateORSR(ResBussiness bus) {
        if (bus.getIsValid() == null) {
            bus.setIsValid(true);
            double[][] mainInputPowers = BussinessPowerStringTransfer.stringTransfer(bus.getMainInputPowers());
            double[][] mainOutputPowers = BussinessPowerStringTransfer.stringTransfer(bus.getMainOutputPowers());
            try {
                calculator.calculate(mainInputPowers, mainOutputPowers, bus.getMainRoute(), bus.getVersionId());
                if (bus.getSpareRoute() != null) {
                    double[][] spareInputPower = BussinessPowerStringTransfer.stringTransfer(bus.getSpareInputPowers());
                    double[][] spareOutputPower = BussinessPowerStringTransfer.stringTransfer(bus.getSpareOutputPowers());
                    calculator.calculate(spareInputPower, spareOutputPower, bus.getSpareRoute(), bus.getVersionId());
                }
            } catch (Exception e) {
                e.printStackTrace();
                bus.setIsValid(false);
            }
        }
    }


    @Override
    public List<BussinessDTO> listBussiness(Long versionId, String circleId) {
        List<BussinessDTO> result = resBussinessDao.selectByExample(getExample(versionId, circleId)).stream().sorted(Comparator
                .comparing(ResBussiness::getGmtModified).reversed()).map(this::createBussinessDTO).collect
                (Collectors.toList());
        if (result.size() == 0) {
            throw new NoneGetException("没有查询到光通道相关记录！");
        }
        return result;
    }

    @Override
    public ResBussiness getBussiness(Long versionId, Long bussinessId) {
        List<ResBussiness> bussiness = resBussinessDao.selectByExample(getExample(versionId, bussinessId));
        if (bussiness.size() != 1) {
            throw new NoneGetException("查询失败，请检查查询的信息是否正确");
        }
        return bussiness.get(0);
    }

    @Override
    public BussinessDTO saveBussiness(Long versionId, BussinessCreateInfo bussinessCreateInfo) {
        if (resBussinessDao.selectByExample(getExampleByBusName(versionId, bussinessCreateInfo.getBussinessName(), bussinessCreateInfo.getCircleId()))
                .size() != 0) {
            throw new DuplicateKeyException("光通道名称重复！");
        }
        ResBussiness insertInfo = UPDATE_UTILS.createBussiness(versionId, bussinessCreateInfo);
        insertInfo.setIsValid(true);

        if (resBussinessDao.insertSelective(insertInfo) > 0) {
            insertInfo = resBussinessDao.selectOne(insertInfo);
            BussinessDTO obj = createBussinessDTO(insertInfo);

            List<OSNRGeneralInfo> osnrResult = osnrService.getRouteOSNRDetail(versionId, insertInfo.getBussinessId());
            for (int i = 0; i < osnrResult.size(); i++) {
                if (osnrResult.get(i).getIsUsable().equals("否")) {
                    insertInfo.setIsValid(false);
                    resBussinessDao.updateByExampleSelective(insertInfo, getExample(versionId, insertInfo.getBussinessId()));
                    insertInfo = getBussiness(insertInfo.getVersionId(),insertInfo.getBussinessId());
                    obj = createBussinessDTO(insertInfo);
                    return obj;
                }
            }
            return obj;
        }
        throw new NoneSaveException();
    }

    @Override
    public BussinessDTO updateBussiness(Long versionId, Long bussinessId, BussinessCreateInfo bussinessCreateInfo) {
        ResBussiness updateBus = UPDATE_UTILS.createBussiness(versionId, bussinessCreateInfo);
        ResBussiness oldBus = resBussinessDao.selectByExample(getExample(versionId, bussinessId)).get(0);
        updateBus.setBussinessId(bussinessId);
        updateBus.setCircleId(oldBus.getCircleId());
        resBussinessDao.updateByExampleSelective(updateBus, getExample(versionId, bussinessId));
        return createBussinessDTO(resBussinessDao.selectByPrimaryKey(bussinessId));
    }

    @Override
    @Transactional
    public void listRemove(Long versionId, List<Long> bussinessIdList) {
        for (Long bussinessId : bussinessIdList) {
            if (resBussinessDao.deleteByExample(getExample(versionId, bussinessId)) == 0) {
                throw new NoneRemoveException();
            }
        }
    }

    @Override
    public int batchRemove(Long versionId) {
        return resBussinessDao.deleteByExample(getExample(versionId));
    }

    @Override
    public int batchCreate(final Long baseVersionId, final Long newVersionId) {
        List<ResBussiness> list = resBussinessDao.selectByExample(getExample(baseVersionId)).parallelStream().map(
                resBussiness -> {
                    resBussiness.setBussinessId(null);
                    resBussiness.setVersionId(newVersionId);
                    resBussiness.setBussinessId(null);
                    return resBussiness;
                }).collect(Collectors.toList());
        try {
            return batchInsert(list);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return -1;
    }

    @Override
    public int batchInsert(final List<ResBussiness> batchList) throws InterruptedException {

        // todo  检查batchList 如果isvalid为null 要通过osnr计算判断是否是有效光通道并将计算结果存入isvalid
        if (batchList != null && batchList.size() > 0) {
            for (ResBussiness item : batchList) {
                calculateORSR(item);
            }
        }
        resBussinessDao.batchInsert(batchList);
        return batchList.size();
    }


    private Example getExample(Long versionId, String condition, String referString) {
        Example example = new Example(ResBussiness.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("versionId", versionId);
        criteria.andLike(condition, referString);
        return example;
    }


    @Override
    public void updateReferBussiness(Long versionId, String oldString, String newString, boolean needRecalculate) {
        List<ResBussiness> relatedBussiness = resBussinessDao.selectByExample(getExample(versionId, "mainRoute",
                "%" + oldString + "%"));
        relatedBussiness.addAll(resBussinessDao.selectByExample(getExample(versionId, "spareRoute",
                "%" + oldString + "%")));
        CountDownLatch count = new CountDownLatch(relatedBussiness.size());
        relatedBussiness.forEach(bus -> {
            BussinessCreateInfo updateInfo = UPDATE_UTILS.createInfo(bus, oldString, newString);
            ResBussiness updateBus = UPDATE_UTILS.createUpdateInfo(versionId, bus.getBussinessId(), updateInfo,
                    newString);
            resBussinessDao.updateByPrimaryKeySelective(updateBus);
        });
    }

    private Example getExample(Long versionId, Long bussinessId) {
        Example example = new Example(ResBussiness.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("versionId", versionId);
        criteria.andEqualTo("bussinessId", bussinessId);
        return example;
    }

    private Example getExample(Long versionId) {
        Example example = new Example(ResBussiness.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("versionId", versionId);
        return example;
    }

    private Example getExample(Long versionId, String circleId) {
        Example example = new Example(ResBussiness.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("versionId", versionId);
        criteria.andEqualTo("circleId", circleId);
        return example;
    }

    private Example getExampleByBusName(Long versionId, String busName) {
        Example example = new Example(ResBussiness.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("versionId", versionId);
        criteria.andEqualTo("bussinessName", busName);
        return example;
    }

    private Example getExampleByBusName(Long versionId, String busName, String circleId) {
        Example example = new Example(ResBussiness.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("versionId", versionId);
        criteria.andEqualTo("bussinessName", busName);
        criteria.andEqualTo("circleId", circleId);
        return example;
    }

    private BussinessDTO createBussinessDTO(ResBussiness bussiness) {
        if (null == bussiness) {
            return null;
        }
        BussinessDTO bussinessInfo = new BussinessDTO();
        BeanUtils.copyProperties(bussiness, bussinessInfo);
        bussinessInfo.setValid(bussiness.getIsValid());
        try {
            bussinessInfo.setInputPower(UPDATE_UTILS.findInputPower(bussiness.getMainInputPowers(), 0));
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(bussinessInfo.getBussinessName());
        }
        return bussinessInfo;
    }

    //辅助类：主要用来承载OSNR计算的一些相关辅助函数，帮助光通道计算OSNR条件
    private class UpdateUtils {
        //根据所给的信息，计算出该条光通道所有节点的输入输出功率以及是否满足OSNR条件
        //todo ONSR计算修改
        ResBussiness createBussiness(Long versionId, BussinessCreateInfo bussinessCreateInfo) {
            ResBussiness result = new ResBussiness();
            result.setVersionId(versionId);
            BeanUtils.copyProperties(bussinessCreateInfo, result);
            try {
                calculator.calculate(bussinessCreateInfo.getInputPower(), bussinessCreateInfo.getMainRoute(), versionId);
            } catch (Exception e) {
                throw new IllegalArgumentException("主路由中的" + e.getMessage());
            }
            result.setMainInputPowers(calculator.getInputPowersString());
            result.setMainOutputPowers(calculator.getOutputPowerString());
            if (null != bussinessCreateInfo.getSpareRoute()) {
                try {
                    calculator.calculate(bussinessCreateInfo.getInputPower(), bussinessCreateInfo.getSpareRoute(), versionId);
                } catch (Exception e) {
                    throw new IllegalArgumentException("备用路由中的" + e.getMessage());
                }
                result.setSpareInputPowers(calculator.getInputPowersString());
                result.setSpareOutputPowers(calculator.getOutputPowerString());
            }
            return result;
        }

        //根据所给的信息，更新光通道计算，注意这个函数根据newString做到只更新指定的新路由所在部分
        ResBussiness createUpdateInfo(Long versionId, Long bussinessId, BussinessCreateInfo bussinessCreateInfo, String
                newString) {
            ResBussiness createdBus = new ResBussiness();
            createdBus.setIsValid(true);
            ResBussiness oldBus = resBussinessDao.selectByExample(getExample(versionId, bussinessId)).get(0);
            BeanUtils.copyProperties(bussinessCreateInfo, createdBus);
            if (true) {
                //主路由
                int index = findIndex(bussinessCreateInfo.getMainRoute(), newString, oldBus, true);
                double inputPower = findInputPower(oldBus.getMainInputPowers(), index);
                String subString = getSubString(bussinessCreateInfo.getMainRoute(), newString);
                PowerResults mainSubResults = calculateResult(inputPower, subString, versionId);
                createdBus.setMainInputPowers(buildNewPowerString(mainSubResults.getInputPowerResult(), oldBus
                        .getMainInputPowers(), index));
                createdBus.setMainOutputPowers(buildNewPowerString(mainSubResults.getOutputPowerResult(), oldBus
                        .getMainOutputPowers(), index));
                createdBus.setIsValid(mainSubResults.isvalid && createdBus.getIsValid());
                //备用路由
                if (null != bussinessCreateInfo.getSpareRoute()) {
                    index = findIndex(bussinessCreateInfo.getSpareRoute(), newString, oldBus, false);
                    inputPower = findInputPower(oldBus.getSpareInputPowers(), index);
                    subString = getSubString(bussinessCreateInfo.getSpareRoute(), newString);
                    mainSubResults = calculateResult(inputPower, subString, versionId);
                    createdBus.setSpareInputPowers(buildNewPowerString(mainSubResults.getInputPowerResult(),
                            oldBus.getSpareInputPowers(), index));
                    createdBus.setSpareOutputPowers(buildNewPowerString(mainSubResults.getOutputPowerResult()
                            , oldBus.getSpareOutputPowers(), index));
                    createdBus.setIsValid(mainSubResults.isvalid && createdBus.getIsValid());
                }
            }
            createdBus.setBussinessId(bussinessId);
            createdBus.setVersionId(versionId);
            return createdBus;
        }

        //辅助createUpdateInfo计算OSNR结果
        // todo ONSR计算修改
        private PowerResults calculateResult(double inputPower, String routeString, Long versionId) {
            try {
                calculator.calculate(inputPower, routeString, versionId);
            } catch (Exception e) {
                logger.warn(e.getMessage());
            }
            return new PowerResults(calculator.getInputPowersString(), calculator.getOutputPowerString());
        }

        //辅助createUpdateInfo
        private BussinessCreateInfo createInfo(ResBussiness bussiness, String oldString, String newString) {
            BussinessCreateInfo createInfo = new BussinessCreateInfo();
            BeanUtils.copyProperties(bussiness, createInfo);
            createInfo.setMainRoute(createNewRoute(bussiness.getMainRoute(), oldString, newString));
            if (null != bussiness.getSpareRoute()) {
                createInfo.setSpareRoute(createNewRoute(bussiness.getSpareRoute(), oldString, newString));
            }
            createInfo.setInputPower(BussinessPowerStringTransfer.stringTransfer(bussiness.getMainInputPowers())[0][0]);
            return createInfo;
        }

        //辅助createUpdateInfo计算OSNR结果
        private String createNewRoute(String routeString, String oldString, String newString) {
            String[] oldNodes = oldString.split("-");
            String[] newNodes = newString.split("-");
            if (oldNodes.length == 1 && newNodes.length == 1) {
                return routeString.replace(oldString, newString);
            } else {
                String[] nodes = routeString.split("-");
                StringBuilder result = new StringBuilder();
                for (int i = 0; i < nodes.length - 1; i++) {
                    if (nodes[i].equals(oldNodes[0]) && nodes[i + 1].equals(oldNodes[1])) {
                        nodes[i] = newNodes[0];
                        nodes[i + 1] = newNodes[1];
                    }
                }
                for (String node : nodes) {
                    result.append(node).append("-");
                }
                return result.substring(0, result.length() - 1);
            }
        }

        //辅助createUpdateInfo计算OSNR结果
        private int findIndex(String routeString, String newString, ResBussiness oldBus, boolean isMain) {
            int stringIndex = routeString.split("-").length - getSubString(routeString, newString).split("-")
                    .length;
            String inputPowerString = isMain ? oldBus.getMainInputPowers() : oldBus.getSpareInputPowers();
            int powerIndex = BussinessPowerStringTransfer.stringTransfer(inputPowerString).length - 1;
            return Math.min(stringIndex, powerIndex);
        }

        //辅助createUpdateInfo计算OSNR结果
        private String getSubString(String routeString, String newString) {
            return routeString.substring(!routeString.contains(newString) ? 0 : routeString.indexOf(newString));
        }

        //辅助createUpdateInfo计算OSNR结果
        private double findInputPower(String inputPowerString, int index) {
            double[][] inputPowers = BussinessPowerStringTransfer.stringTransfer(inputPowerString);
            if (index <= inputPowers.length) {
                return inputPowers[index][0];
            }
            return inputPowers[0][0];
        }

        //拼接两个powerString成新的powerString
        String buildNewPowerString(String subPowerString, String oldPowerString, int index) {
            double[][] oldPowerArr = BussinessPowerStringTransfer.stringTransfer(oldPowerString);
            double[][] subPowerArr = BussinessPowerStringTransfer.stringTransfer(subPowerString);
            double[][] resultArr = new double[index + subPowerArr.length][];
            for (int i = 0; i < resultArr.length; i++) {
                if (i < index) {
                    if (i < oldPowerArr.length) resultArr[i] = oldPowerArr[i];
                } else {
                    resultArr[i] = subPowerArr[i - index];
                }
            }
            return Arrays.deepToString(resultArr);
        }

        private class PowerResults {
            private final String inputPowerResult;
            private final String outputPowerResult;
            private boolean isvalid = true;

            public boolean isIsvalid() {
                return isvalid;
            }

            public void setIsvalid(boolean isvalid) {
                this.isvalid = isvalid;
            }

            PowerResults(String inputPowerResult, String outputPowerResult) {
                this.inputPowerResult = inputPowerResult;
                this.outputPowerResult = outputPowerResult;
            }

            String getInputPowerResult() {
                return inputPowerResult;
            }

            String getOutputPowerResult() {
                return outputPowerResult;
            }
        }
    }


}
