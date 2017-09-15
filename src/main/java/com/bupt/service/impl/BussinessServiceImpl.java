package com.bupt.service.impl;

import com.bupt.dao.ResBussinessDao;
import com.bupt.entity.ResBussiness;
import com.bupt.pojo.BussinessCreateInfo;
import com.bupt.pojo.BussinessDTO;
import com.bupt.service.BussinessService;
import com.bupt.pojo.ChannelQuery;
import com.bupt.service.OsnrChannelService;
import com.bupt.util.exception.controller.result.NoneRemoveException;
import com.bupt.util.exception.controller.result.NoneSaveException;
import com.bupt.util.exception.controller.result.NoneUpdateException;
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
    private OsnrChannelService osnrChannelService;

    @Override
    public List<BussinessDTO> listBussiness(Long versionId) {
        Example example = new Example(ResBussiness.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("versionId", versionId);
        List<ResBussiness> bussinessList = resBussinessDao.selectByExample(example);

        List<BussinessDTO> resultList = new ArrayList<>();
        for (ResBussiness bussiness : bussinessList) {
            BussinessDTO bussinessCreated = DOtoDTO(bussiness);
            bussinessCreated.setMainChannel(osnrChannelService.getOnsrChannel(new ChannelQuery(versionId,
                    bussinessCreated.getBussinessId(), true)));
            if (null != bussinessCreated.getSpareRoute()) {
                bussinessCreated.setSpareChannel(osnrChannelService.getOnsrChannel(new ChannelQuery(versionId,
                        bussinessCreated.getBussinessId(), false)));
            }
            resultList.add(bussinessCreated);
        }
        return resultList;
    }

    @Override
    @Transactional
    public BussinessDTO saveBussiness(Long versionId, BussinessCreateInfo bussinessCreateInfo) {
        if (resBussinessDao.insertSelective(createBussiness(versionId, bussinessCreateInfo)) > 0) {
            BussinessDTO bussinessCreated = DOtoDTO(resBussinessDao.selectByExample(getExample(versionId,
                    bussinessCreateInfo.getBussinessName())).get(0));
            bussinessCreated.setMainChannel(osnrChannelService.saveOsnrChannel(new ChannelQuery(versionId,
                    bussinessCreated.getBussinessId(), true), bussinessCreateInfo.getMainRoute(), bussinessCreateInfo
                    .getMainChannelInfo()));
            if (null != bussinessCreateInfo.getSpareRoute()) {
                bussinessCreated.setSpareChannel(osnrChannelService.saveOsnrChannel(new ChannelQuery(versionId,
                                bussinessCreated.getBussinessId(), false), bussinessCreateInfo.getSpareRoute(),
                        bussinessCreateInfo.getSpareChannelInfo()));
            }
            return bussinessCreated;
        }
        throw new NoneSaveException();
    }

    private Example getExample(Long versionId, String bussinessName) {
        Example example = new Example(ResBussiness.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("versionId", versionId);
        criteria.andEqualTo("bussinessName", bussinessName);
        return example;
    }

    @Override
    @Transactional
    public BussinessDTO updateBussiness(Long versionId, Long bussinessId, BussinessCreateInfo bussinessCreateInfo) {
        ResBussiness updateInfo = createBussiness(versionId, bussinessCreateInfo);
        updateInfo.setBussinessId(bussinessId);
        resBussinessDao.deleteByPrimaryKey(bussinessId);
        if (resBussinessDao.insertSelective(updateInfo) > 0) {
            BussinessDTO newBussiness = DOtoDTO(resBussinessDao.selectByExample(getExample(versionId,
                    bussinessCreateInfo.getBussinessName())).get(0));
            newBussiness.setMainChannel(osnrChannelService.updateOsnrChannel(new ChannelQuery(versionId,
                    bussinessId, true), bussinessCreateInfo.getMainRoute(), bussinessCreateInfo.getMainChannelInfo()));
            if (null != bussinessCreateInfo.getSpareRoute()) {
                newBussiness.setSpareChannel(osnrChannelService.updateOsnrChannel(new ChannelQuery(versionId,
                        bussinessId, false), bussinessCreateInfo.getSpareRoute(), bussinessCreateInfo.getSpareChannelInfo()));
            }else{
                osnrChannelService.removeOsnrChannel(new ChannelQuery(versionId,bussinessId,false));
            }
            return newBussiness;
        }
        throw new NoneUpdateException();
    }

    @Override
    @Transactional
    public void listRemove(Long versionId, List<Long> bussinessIdList) {
        for (Long bussinessId : bussinessIdList) {
            osnrChannelService.removeOsnrChannel(new ChannelQuery(versionId,bussinessId,true));
            osnrChannelService.removeOsnrChannel(new ChannelQuery(versionId,bussinessId,false));
            if (resBussinessDao.deleteByPrimaryKey(bussinessId) == 0) {
                throw new NoneRemoveException();
            }
        }
    }

    @Override
    public void batchRemove(Long versionId) {
        osnrChannelService.batchRemove(versionId);
        Example example = new Example(ResBussiness.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("versionId", versionId);
        resBussinessDao.deleteByExample(example);
    }

    @Override
    @Transactional
    public void batchCreate(Long baseVersionId, Long newVersionId) {
        Example example = new Example(ResBussiness.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("versionId", baseVersionId);
        List<ResBussiness> bussinessList = resBussinessDao.selectByExample(example);
        osnrChannelService.batchCreate(baseVersionId, newVersionId);
        for (ResBussiness bussiness : bussinessList) {
            bussiness.setBussinessId(null);
            bussiness.setVersionId(newVersionId);
            resBussinessDao.insertSelective(bussiness);
        }
    }

    BussinessDTO DOtoDTO(Object inputObject) {
        if (null == inputObject) {
            return null;
        }
        BussinessDTO result = new BussinessDTO();
        BeanUtils.copyProperties(inputObject, result);
        return result;
    }

    ResBussiness createBussiness(Long versionId, Object inputObject) {
        if (null == inputObject) {
            return null;
        }
        ResBussiness result = new ResBussiness();
        BeanUtils.copyProperties(inputObject, result);
        result.setVersionId(versionId);
        return result;
    }
}
