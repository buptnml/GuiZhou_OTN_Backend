package com.bupt.service.impl;

import com.bupt.dao.ResOsnrChannelDao;
import com.bupt.entity.ResOsnrChannel;
import com.bupt.pojo.OsnrChannelCreateInfo;
import com.bupt.pojo.OsnrChannelDTO;
import com.bupt.pojo.ChannelQuery;
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
    public void removeOsnrChannel(ChannelQuery channelQuery) {
        //主波道必然存在，迂回波道不一定存在
        if (resOsnrChannelDao.deleteByExample(getExample(channelQuery)) == 0 && channelQuery.isMain()) {
            throw new NoneRemoveException();
        }
    }

    @Override
    public OsnrChannelDTO saveOsnrChannel(ChannelQuery channelQuery, String route, OsnrChannelCreateInfo osnrChannelCreateInfo) {
        if (resOsnrChannelDao.insertSelective(createChannel(channelQuery,route, osnrChannelCreateInfo)) > 0) {
            return getOnsrChannel(channelQuery);
        }
        throw new NoneSaveException();
    }

    private Example getExample(ChannelQuery channelQuery) {
        Example example = new Example(ResOsnrChannel.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("versionId", channelQuery.getVersionId());
        criteria.andEqualTo("bussinessId", channelQuery.getBussinessId());
        criteria.andEqualTo("isMain", channelQuery.isMain());
        return example;
    }

    @Override
    public OsnrChannelDTO updateOsnrChannel(ChannelQuery channelQuery, String route, OsnrChannelCreateInfo osnrChannelCreateInfo) {
        if (resOsnrChannelDao.updateByExampleSelective(createChannel(new ChannelQuery(),route, osnrChannelCreateInfo), getExample(channelQuery)) > 0) {
            return getOnsrChannel(channelQuery);
        }
        throw new NoneUpdateException();
    }


    @Override
    public OsnrChannelDTO getOnsrChannel(ChannelQuery channelQuery) {
        List<ResOsnrChannel> channels = resOsnrChannelDao.selectByExample(getExample(channelQuery));
        if (channels.size() == 1) {
            OsnrChannelDTO result = DOtoDTO(channels.get(0));
            //属性复制的过程中boolean类型会变成null
            result.setMain(channels.get(0).getIsMain());
            return result;
        }
        throw new NoneGetException();
    }

    @Override
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
            oldChannel.setChannelId(null);
            oldChannel.setVersionId(newVersionId);
            resOsnrChannelDao.insertSelective(oldChannel);
        }
    }


    private ResOsnrChannel createChannel(ChannelQuery channelQuery,String route, Object inputInfo) {
        if (null == inputInfo) {
            return null;
        }
        ResOsnrChannel channel = new ResOsnrChannel();
        BeanUtils.copyProperties(inputInfo, channel);
        channel.setVersionId(channelQuery.getVersionId());
        channel.setBussinessId(channelQuery.getBussinessId());
        channel.setIsMain(channelQuery.isMain());
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
