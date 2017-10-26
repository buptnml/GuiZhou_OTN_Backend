package com.bupt.service.impl;


import com.bupt.dao.ResBussinessDao;
import com.bupt.entity.ResBussiness;
import com.bupt.facade.OSNRCalculator.Calculable;
import com.bupt.pojo.BussinessCreateInfo;
import com.bupt.pojo.BussinessDTO;
import com.bupt.service.BussinessService;
import com.bupt.util.exception.controller.result.NoneRemoveException;
import com.bupt.util.exception.controller.result.NoneSaveException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service("bussinessService")
public class BussinessServiceImpl implements BussinessService {
    @Resource
    private ResBussinessDao resBussinessDao;
    @Resource
    private Calculable calculator;
    private Logger logger = LoggerFactory.getLogger(BussinessServiceImpl.class);


    @Override
    public List<BussinessDTO> listBussiness(Long versionId) {
        List<ResBussiness> bussinessList = resBussinessDao.selectByExample(getExample(versionId));
        List<BussinessDTO> resultList = new ArrayList<>();
        for (ResBussiness bussiness : bussinessList) {
            resultList.add(createBussinessDTO(bussiness));
        }
        return resultList;
    }

    @Override
    public ResBussiness getBussiness(Long versionId, Long bussinessId) {
        return resBussinessDao.selectByExample(getExample(versionId, bussinessId)).get(0);
    }

    @Override
    public BussinessDTO saveBussiness(Long versionId, BussinessCreateInfo bussinessCreateInfo) {
        ResBussiness insertInfo = createBussiness(versionId, bussinessCreateInfo);
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
    @Transactional
    public void batchCreate(Long baseVersionId, Long newVersionId) {
        List<ResBussiness> bussinessList = resBussinessDao.selectByExample(getExample(baseVersionId));
        for (ResBussiness aBussinessList : bussinessList) {
            aBussinessList.setVersionId(newVersionId);
            aBussinessList.setBussinessId(null);
            resBussinessDao.insertSelective(aBussinessList);
        }
    }


    private Example getExample(Long versionId, String condition, String referString) {
        Example example = new Example(ResBussiness.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("versionId", versionId);
        criteria.andLike(condition, "%" + referString + "%");
        return example;
    }


    @Override
    public void updateReferBussiness(Long versionId, String oldString, String newString) {
        List<ResBussiness> relatedBussiness = resBussinessDao.selectByExample(getExample(versionId, "mainRoute",
                oldString));
        relatedBussiness.addAll(resBussinessDao.selectByExample(getExample(versionId, "spareRoute",
                oldString)));
        for (ResBussiness bus : relatedBussiness) {
            this.updateBussiness(versionId, bus.getBussinessId(), createInfo(bus, oldString, newString));
        }
    }

    private BussinessCreateInfo createInfo(ResBussiness bussiness, String oldString, String newString) {
        BussinessCreateInfo createInfo = new BussinessCreateInfo();
        BeanUtils.copyProperties(bussiness, createInfo);
        createInfo.setMainRoute(bussiness.getMainRoute().replace(oldString, newString));
        createInfo.setSpareRoute(bussiness.getSpareRoute().replace(oldString, newString));
        return createInfo;
    }

    private void updateBussiness(Long versionId, Long bussinessId, BussinessCreateInfo bussinessCreateInfo) {
        ResBussiness createdBus = null;
        try {
            createdBus = createBussiness(versionId, bussinessCreateInfo);
        } catch (Exception e) {
            logger.warn(e.getMessage());
        }
        resBussinessDao.updateByExampleSelective(createdBus, getExample(versionId, bussinessId));
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

    private BussinessDTO createBussinessDTO(ResBussiness bussiness) {
        if (null == bussiness) {
            return null;
        }
        BussinessDTO bussinessInfo = new BussinessDTO();
        BeanUtils.copyProperties(bussiness, bussinessInfo);
        return bussinessInfo;
    }

    private ResBussiness createBussiness(Long versionId, BussinessCreateInfo bussinessCreateInfo) {
        if (null == bussinessCreateInfo) {
            return null;
        }
        ResBussiness result = new ResBussiness();
        BeanUtils.copyProperties(bussinessCreateInfo, result);
        result.setVersionId(versionId);
        double[][] inputPower = new double[1][1];
        inputPower[0][0] = bussinessCreateInfo.getInputPower();
        try {
            calculator.calculate(inputPower, null, bussinessCreateInfo.getMainRoute(), versionId);
        } catch (Exception e) {
            throw new IllegalArgumentException("主路由中的" + e.getMessage());
        }
        result.setMainInputPowers(calculator.getInputPowersString());
        result.setMainOutputPowers(calculator.getOutputPowerString());
        if (null != bussinessCreateInfo.getSpareRoute()) {
            try {
                calculator.calculate(inputPower, null, bussinessCreateInfo.getSpareRoute(), versionId);
            } catch (Exception e) {
                throw new IllegalArgumentException("备用路由中的" + e.getMessage());
            }
            result.setSpareInputPowers(calculator.getInputPowersString());
            result.setSpareOutputPowers(calculator.getOutputPowerString());
        }
        return result;
    }


}
