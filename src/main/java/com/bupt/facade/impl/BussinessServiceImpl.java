package com.bupt.facade.impl;


import com.bupt.dao.ResBussinessDao;
import com.bupt.entity.ResBussiness;
import com.bupt.facade.BussinessService;
import com.bupt.facade.OSNRCalculator.Calculable;
import com.bupt.pojo.BussinessCreateInfo;
import com.bupt.pojo.BussinessDTO;
import com.bupt.util.exception.controller.result.NoneGetException;
import com.bupt.util.exception.controller.result.NoneRemoveException;
import com.bupt.util.exception.controller.result.NoneSaveException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
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
        ResBussiness insertInfo = createBussiness(versionId, bussinessCreateInfo, true);
        if (resBussinessDao.insertSelective(insertInfo) == 1) {
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
    public void updateReferBussiness(Long versionId, String oldString, String newString) {
        List<ResBussiness> relatedBussiness = resBussinessDao.selectByExample(getExample(versionId, "mainRoute",
                "%" + oldString + "%"));
        relatedBussiness.addAll(resBussinessDao.selectByExample(getExample(versionId, "spareRoute",
                "%" + oldString + "%")));
        relatedBussiness.forEach(bus -> updateBussiness(versionId, bus.getBussinessId(), createInfo(bus,
                oldString, newString)));
    }

    private BussinessCreateInfo createInfo(ResBussiness bussiness, String oldString, String newString) {
        BussinessCreateInfo createInfo = new BussinessCreateInfo();
        BeanUtils.copyProperties(bussiness, createInfo);
        createInfo.setMainRoute(createNewRoute(bussiness.getMainRoute(), oldString, newString));
        if (null != bussiness.getSpareRoute()) {
            createInfo.setSpareRoute(createNewRoute(bussiness.getSpareRoute(), oldString, newString));
        }
        createInfo.setInputPower(OSNRServiceImpl.stringTransfer(bussiness.getMainInputPowers())[0][0]);
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

    private void updateBussiness(Long versionId, Long bussinessId, BussinessCreateInfo bussinessCreateInfo) {
        ResBussiness createdBus = new ResBussiness();
        try {
            createdBus = createBussiness(versionId, bussinessCreateInfo, false);
        } catch (Exception e) {
            logger.warn(e.getMessage());
        }
        createdBus.setBussinessId(bussinessId);
        createdBus.setVersionId(versionId);
        resBussinessDao.updateByPrimaryKeySelective(createdBus);

    }


    private Example getExample(Long versionId, Long bussinessId) {
        Example example = new Example(ResBussiness.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("versionId", versionId);
        criteria.andEqualTo("bussinessId", bussinessId);
        return example;
    }

    public Example getExample(Long versionId) {
        Example example = new Example(ResBussiness.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("versionId", versionId);
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
    private ResBussiness createBussiness(Long versionId, BussinessCreateInfo bussinessCreateInfo, boolean
            needException) {
        ResBussiness result = new ResBussiness();
        BeanUtils.copyProperties(bussinessCreateInfo, result);
        result.setVersionId(versionId);
        double[][] inputPower = new double[1][1];
        inputPower[0][0] = bussinessCreateInfo.getInputPower();
        try {
            calculator.calculate(inputPower, null, bussinessCreateInfo.getMainRoute(), versionId);
        } catch (Exception e) {
            if (needException) {
                throw new IllegalArgumentException("主路由中的" + e.getMessage());
            } else {
                logger.warn("主路由中的" + e.getMessage());
            }
        }
        result.setMainInputPowers(calculator.getInputPowersString());
        result.setMainOutputPowers(calculator.getOutputPowerString());
        if (null != bussinessCreateInfo.getSpareRoute()) {
            try {
                calculator.calculate(inputPower, null, bussinessCreateInfo.getSpareRoute(), versionId);
            } catch (Exception e) {
                if (needException) {
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


}
