package com.bupt.facade.impl;


import com.bupt.dao.ResBussinessDao;
import com.bupt.entity.ResBussiness;
import com.bupt.facade.BussinessService;
import com.bupt.facade.OSNRCalculator.Calculable;
import com.bupt.facade.util.BussinessPowerStringTransfer;
import com.bupt.pojo.BussinessCreateInfo;
import com.bupt.pojo.BussinessDTO;
import com.bupt.util.exception.controller.result.NoneGetException;
import com.bupt.util.exception.controller.result.NoneRemoveException;
import com.bupt.util.exception.controller.result.NoneSaveException;
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
import java.util.stream.Collectors;

@Service("bussinessService")
class BussinessServiceImpl implements BussinessService {
    @Resource
    private ResBussinessDao resBussinessDao;
    @Resource
    private Calculable calculator;

    private Logger logger = LoggerFactory.getLogger(BussinessServiceImpl.class);


    @Override
    public List<BussinessDTO> listBussiness(Long versionId) {
        List<BussinessDTO> result = resBussinessDao.selectByExample(getExample(versionId)).stream().sorted(Comparator
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
        if (resBussinessDao.selectByExample(getExampleByBusName(versionId, bussinessCreateInfo.getBussinessName()))
                .size() != 0) {
            throw new DuplicateKeyException("bussiness_name");
        }
        ResBussiness insertInfo = createBussiness(versionId, bussinessCreateInfo);
        if (resBussinessDao.insertSelective(insertInfo) > 0) {
            return createBussinessDTO(resBussinessDao.selectOne(insertInfo));
        }
        throw new NoneSaveException();
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
    public void batchRemove(Long versionId) {
        resBussinessDao.deleteByExample(getExample(versionId));
    }

    @Override
    public void batchCreate(Long baseVersionId, Long newVersionId) {
        resBussinessDao.selectByExample(getExample(baseVersionId)).forEach(resBussiness -> {
            resBussiness.setVersionId(newVersionId);
            resBussiness.setBussinessId(null);
            resBussinessDao.insertSelective(resBussiness);
        });
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
        relatedBussiness.forEach(bus -> updateBussiness(versionId, bus.getBussinessId(), createInfo(bus,
                oldString, newString), newString, needRecalculate));
    }

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

    private void updateBussiness(Long versionId, Long bussinessId, BussinessCreateInfo bussinessCreateInfo, String
            newString, boolean needRecalculate) {
        ResBussiness createdBus = new ResBussiness();
        ResBussiness oldBus = resBussinessDao.selectByExample(getExample(versionId, bussinessId)).get(0);
        if (null == oldBus) return;
        BeanUtils.copyProperties(bussinessCreateInfo, createdBus);
        if (needRecalculate) {
            //主路由
            int index = findIndex(bussinessCreateInfo.getMainRoute(), newString, oldBus, true);
            double inputPower = findInputPower(oldBus.getMainInputPowers(), index);
            String subString = getSubString(bussinessCreateInfo.getMainRoute(), newString);
            PowerResults mainSubResults = calculateResult(inputPower, subString, versionId);
            createdBus.setMainInputPowers(buildNewPowerString(mainSubResults.getInputPowerResult(), oldBus
                    .getMainInputPowers(), index));
            createdBus.setMainOutputPowers(buildNewPowerString(mainSubResults.getOutputPowerResult(), oldBus
                    .getMainOutputPowers(), index));

            //备用路由
            if (null != bussinessCreateInfo.getSpareRoute()) {
                index = findIndex(bussinessCreateInfo.getSpareRoute(), newString, oldBus, false);
                inputPower = findInputPower(oldBus.getSpareInputPowers(), index);
                subString = getSubString(bussinessCreateInfo.getSpareRoute(), newString);
                mainSubResults = calculateResult(inputPower, subString, versionId);
                createdBus.setSpareInputPowers(buildNewPowerString(mainSubResults.getInputPowerResult(), oldBus
                        .getSpareInputPowers(), index));
                createdBus.setSpareOutputPowers(buildNewPowerString(mainSubResults.getOutputPowerResult(), oldBus
                        .getSpareOutputPowers(), index));
            }
        }
        createdBus.setBussinessId(bussinessId);
        createdBus.setVersionId(versionId);
        resBussinessDao.updateByPrimaryKeySelective(createdBus);
    }

    private String buildNewPowerString(String subPowerString, String oldPowerString, int index) {
        double[][] oldPowerArr = BussinessPowerStringTransfer.stringTransfer(oldPowerString);
        double[][] subPowerArr = BussinessPowerStringTransfer.stringTransfer(subPowerString);
        double[][] resultArr = new double[index + subPowerArr.length][];
        for (int i = 0; i < resultArr.length; i++) {
            if (i < index) {
                resultArr[i] = oldPowerArr[i];
            } else {
                resultArr[i] = subPowerArr[i - index];
            }
        }
        return Arrays.deepToString(resultArr);
    }


    private int findIndex(String routeString, String newString, ResBussiness oldBus, boolean isMain) {
        int stringIndex = routeString.split("-").length - getSubString(routeString, newString).split("-")
                .length;
        String inputPowerString = isMain ? oldBus.getMainInputPowers() : oldBus.getSpareInputPowers();
        int powerIndex = BussinessPowerStringTransfer.stringTransfer(inputPowerString).length - 1;
        return Math.min(stringIndex, powerIndex);
    }

    private String getSubString(String routeString, String newString) {
        return routeString.substring(routeString.indexOf(newString));
    }

    private double findInputPower(String inputPowerString, int index) {
        double[][] inputPowers = BussinessPowerStringTransfer.stringTransfer(inputPowerString);
        if (index <= inputPowers.length) {
            return inputPowers[index][0];
        }
        return inputPowers[0][0];
    }

    private PowerResults calculateResult(double inputPower, String routeString, Long versionId) {
        try {
            calculator.calculate(inputPower, routeString, versionId);
        } catch (Exception e) {
            logger.warn(e.getMessage());
        }
        return new PowerResults(calculator.getInputPowersString(), calculator.getOutputPowerString());
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

    private Example getExampleByBusName(Long versionId, String busName) {
        Example example = new Example(ResBussiness.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("versionId", versionId);
        criteria.andEqualTo("bussinessName", busName);
        return example;
    }

    private BussinessDTO createBussinessDTO(ResBussiness bussiness) {
        if (null == bussiness) {
            return null;
        }
        BussinessDTO bussinessInfo = new BussinessDTO();
        BeanUtils.copyProperties(bussiness, bussinessInfo);
        return bussinessInfo;
    }


    /*
     * 这个函数既要能正常抛出异常 也要能正常输出结果
     */
    private ResBussiness createBussiness(Long versionId, BussinessCreateInfo bussinessCreateInfo) {
        if (null == bussinessCreateInfo) return null;
        ResBussiness result = new ResBussiness();
        result.setVersionId(versionId);
        BeanUtils.copyProperties(bussinessCreateInfo, result);
        try {
            calculator.calculate(bussinessCreateInfo.getInputPower(), bussinessCreateInfo.getMainRoute(), versionId);
        } catch (Exception e) {
            if (true) {
                throw new IllegalArgumentException("主路由中的" + e.getMessage());
            } else {
                logger.warn("主路由中的" + e.getMessage());
            }
        }
        result.setMainInputPowers(calculator.getInputPowersString());
        result.setMainOutputPowers(calculator.getOutputPowerString());
        if (null != bussinessCreateInfo.getSpareRoute()) {
            try {
                calculator.calculate(bussinessCreateInfo.getInputPower(), bussinessCreateInfo.getSpareRoute(), versionId);
            } catch (Exception e) {
                if (true) {
                    throw new IllegalArgumentException("备用路由中的" + e.getMessage());
                } else {
                    logger.warn("备用路由中的" + e.getMessage());
                }
            }
            result.setSpareInputPowers(calculator.getInputPowersString());
            result.setSpareOutputPowers(calculator.getOutputPowerString());
            return result;
        }
        return result;
    }


    class PowerResults {
        private final String inputPowerResult;
        private final String outputPowerResult;

        public PowerResults(String inputPowerResult, String outputPowerResult) {
            this.inputPowerResult = inputPowerResult;
            this.outputPowerResult = outputPowerResult;
        }

        public String getInputPowerResult() {
            return inputPowerResult;
        }

        public String getOutputPowerResult() {
            return outputPowerResult;
        }
    }

}
