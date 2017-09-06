package com.bupt.service.impl;

import com.bupt.dao.ResOsnrChannelDao;
import com.bupt.entity.ResOsnrChannel;
import com.bupt.pojo.OsnrChannelCreateInfo;
import com.bupt.pojo.OsnrChannelDTO;
import com.bupt.service.OsnrChannelService;
import com.bupt.util.exception.controller.result.NoneGetException;
import com.bupt.util.exception.controller.result.NoneRemoveException;
import com.bupt.util.exception.controller.result.NoneSaveException;
import com.bupt.util.exception.controller.result.NoneUpdateException;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;


/**
 * Osnr波道Service层
 */
@Service("osnrChannelService")
public class OsnrChannelServiceImpl implements OsnrChannelService {
    @Resource
    private ResOsnrChannelDao resOsnrChannelDao;

    @Override
    public void removeOsnrChannel(Long versionId, Long bussinessId) {
        Example example = new Example(ResOsnrChannel.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("versionId", versionId);
        criteria.andEqualTo("bussinessId", bussinessId);

        if (resOsnrChannelDao.deleteByExample(example) == 0) {
            throw new NoneRemoveException();
        }
    }

    @Override
    public OsnrChannelDTO saveOsnrChannel(Long versionId, Long bussinessId, boolean isMain, OsnrChannelCreateInfo osnrChannelCreateInfo) {
        if (resOsnrChannelDao.insertSelective(createChannel(versionId, bussinessId, isMain, osnrChannelCreateInfo)) > 0) {
            return getOnsrChannel(versionId, bussinessId, isMain);
        }
        throw new NoneSaveException();
    }

    private Example getExample(Long versionId, Long bussinessId, boolean isMain) {
        Example example = new Example(ResOsnrChannel.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("versionId", versionId);
        criteria.andEqualTo("bussinessId", bussinessId);
        criteria.andEqualTo("isMain", isMain);
        return example;
    }

    @Override
    public OsnrChannelDTO updateOsnrChannel(Long versionId, Long bussinessId, boolean isMain, OsnrChannelCreateInfo osnrChannelCreateInfo) {
        if (resOsnrChannelDao.updateByExampleSelective(createChannel(0L, 0L, null, osnrChannelCreateInfo), getExample(versionId, bussinessId, isMain)) > 0) {
            return getOnsrChannel(versionId, bussinessId, isMain);
        }
        throw new NoneUpdateException();
    }

    @Override
    public OsnrChannelDTO getOnsrChannel(Long versionId, Long bussinessId, boolean isMain) {
        List<ResOsnrChannel> channels = resOsnrChannelDao.selectByExample(getExample(versionId, bussinessId, isMain));
        if (channels.size() == 1) {
            return DOtoDTO(channels.get(0));
        }
        throw new NoneGetException();
    }

    @Override
    @Transactional
    public void batchRemove(Long versionId) {
        Example example = new Example(ResOsnrChannel.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("versionId", versionId);
        if (resOsnrChannelDao.deleteByExample(example) == 0) {
            throw new NoneRemoveException();
        }
    }

    @Override
    @Transactional
    public void batchCreate(Long baseVersionId, Long newVersionId) {
        Example example = new Example(ResOsnrChannel.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("versionId", baseVersionId);
        List<ResOsnrChannel> channels = resOsnrChannelDao.selectByExample(example);
        for (ResOsnrChannel oldChannel : channels) {
            resOsnrChannelDao.insertSelective(createChannel(newVersionId, oldChannel.getBussinessId(), oldChannel.getIsMain(), oldChannel));
        }
    }


    private ResOsnrChannel createChannel(Long versionId, Long bussinessId, Boolean isMain, Object inputInfo) {
        if (null == inputInfo) {
            return null;
        }
        ResOsnrChannel channel = new ResOsnrChannel();
        BeanUtils.copyProperties(inputInfo, channel);
        channel.setVersionId(versionId == 0 ? null : versionId);
        channel.setBussinessId(bussinessId == 0 ? null : bussinessId);
        channel.setIsMain(isMain);
        channel.setChannelId(null);
        //TODO 未来写完Osnr算法以后需要在这里补全输入输出计算结果

        return channel;
    }

    private OsnrChannelDTO DOtoDTO(Object inputObject) {
        if (null == inputObject) {
            return null;
        }
        OsnrChannelDTO resultDTO = new OsnrChannelDTO();
        BeanUtils.copyProperties(inputObject, resultDTO);
        return resultDTO;
    }


}
