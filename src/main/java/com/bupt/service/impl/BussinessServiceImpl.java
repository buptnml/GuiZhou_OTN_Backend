package com.bupt.service.impl;

import com.bupt.dao.ResBussinessDao;
import com.bupt.entity.ResBussiness;
import com.bupt.pojo.BussinessCreateInfo;
import com.bupt.pojo.BussinessDTO;
import com.bupt.pojo.ChannelDetail;
import com.bupt.service.BussinessService;
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
    public BussinessDTO saveBussiness(Long versionId, BussinessCreateInfo bussinessCreateInfo) {
        ResBussiness insertInfo = createBussiness(versionId, bussinessCreateInfo);
        if (resBussinessDao.insertSelective(insertInfo) == 1) {
            return createBussinessDTO(resBussinessDao.selectOne(insertInfo));
        }
        throw new NoneSaveException();
    }


    @Override
    @Transactional
    public BussinessDTO updateBussiness(Long versionId, Long bussinessId, BussinessCreateInfo bussinessCreateInfo) {
        if (resBussinessDao.updateByExampleSelective(createBussiness(versionId, bussinessCreateInfo), getExample
                (versionId, bussinessId)) == 1) {
            return createBussinessDTO(resBussinessDao.selectByExample(getExample(versionId, bussinessId
            )).get(0));
        }
        throw new NoneUpdateException();
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
        //TODO 机盘Id中网元Id需要更新
        List<ResBussiness> bussinessList = resBussinessDao.selectByExample(getExample(baseVersionId));
        for (ResBussiness aBussinessList : bussinessList) {
            aBussinessList.setVersionId(newVersionId);
            aBussinessList.setBussinessId(null);
            resBussinessDao.insertSelective(aBussinessList);
        }
    }


    private ChannelDetail setChannelInfo(ResBussiness bussiness, boolean isMain) {
        return new ChannelDetail(bussiness, isMain);
    }


    private Example getExample(Long versionId, String bussinessName) {
        Example example = new Example(ResBussiness.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("versionId", versionId);
        criteria.andEqualTo("bussinessName", bussinessName);
        return example;
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
        BussinessDTO bussinessInfo = new BussinessDTO(bussiness);
        bussinessInfo.setMainChannel(setChannelInfo(bussiness, true));
        if (null != bussiness.getSpareRoute()) {
            bussinessInfo.setSpareChannel(setChannelInfo(bussiness, false));
        }
        return bussinessInfo;
    }

    private ResBussiness createBussiness(Long versionId, BussinessCreateInfo bussinessCreateInfo) {
        if (null == bussinessCreateInfo) {
            return null;
        }
        ResBussiness result = new ResBussiness();
        BeanUtils.copyProperties(bussinessCreateInfo, result);
        result.setVersionId(versionId);
        result.setMainRate(bussinessCreateInfo.getMainChannelInfo().getChannelRate());
        result.setMainFrequency(bussinessCreateInfo.getMainChannelInfo().getChannelFrequency());
        if(null!= bussinessCreateInfo.getSpareChannelInfo()){
            result.setSpareRate(bussinessCreateInfo.getSpareChannelInfo().getChannelRate());
            result.setSpareFrequency(bussinessCreateInfo.getSpareChannelInfo().getChannelFrequency());
        }
        //TODO 未来要在Osnr算法补全以后补上对输入输出功率的计算结果

        return result;
    }




}
