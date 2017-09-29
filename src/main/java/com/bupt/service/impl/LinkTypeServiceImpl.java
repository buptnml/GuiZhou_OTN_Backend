package com.bupt.service.impl;

import com.bupt.dao.ResOsnrLinkTypeDao;
import com.bupt.entity.ResOnsrLinkType;
import com.bupt.pojo.LinkTypeCreateInfo;
import com.bupt.pojo.LinkTypeDTO;
import com.bupt.service.LinkTypeService;
import com.bupt.util.exception.controller.result.NoneGetException;
import com.bupt.util.exception.controller.result.NoneRemoveException;
import com.bupt.util.exception.controller.result.NoneSaveException;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by caoxiaohong on 17/9/20.
 */
@Service(value = "linkTypeService")
public class LinkTypeServiceImpl implements LinkTypeService {
    @Resource
    private ResOsnrLinkTypeDao resOsnrLinkTypeDao;

    @Override
    public LinkTypeDTO updateByLinkTypeId(Long versionId, Long linkTypeId, LinkTypeCreateInfo linkTypeCreateInfo) {
        if (null == resOsnrLinkTypeDao.selectByPrimaryKey(linkTypeId)) {
            throw new NoneGetException();
        }
        ResOnsrLinkType rolt = resOnsrLinkTypeDtoToDao(linkTypeCreateInfo);
        if (resOsnrLinkTypeDao.updateByExampleSelective(rolt, getExample(versionId, linkTypeId)) == 0) {
            throw new NoneSaveException();
        } else {
            return linkTypeDaoToDto(resOsnrLinkTypeDao.selectOne(rolt));
        }
    }

    @Override
    @Transactional
    public boolean deleteByLinkTypeId(Long versionId, List<Long> linkTypeIds) {
        if (linkTypeIds.size() == 0)
            return true;
        for (Long linkTypeId : linkTypeIds) {
            if (resOsnrLinkTypeDao.deleteByPrimaryKey(linkTypeId) == 0) {
                throw new NoneRemoveException();
            }
        }
        return true;
    }

    @Override
    @Transactional
    public LinkTypeDTO createLinkType(Long versionId, LinkTypeCreateInfo linkTypeCreateInfo) {
        //dto转dao
        ResOnsrLinkType resOnsrLinkType = resOnsrLinkTypeDtoToDao(linkTypeCreateInfo);
        resOnsrLinkType.setVersionId(versionId);
        if (resOsnrLinkTypeDao.insertSelective(resOnsrLinkType) == 0) {
            throw new NoneSaveException();
        }
        ResOnsrLinkType result = resOsnrLinkTypeDao.selectOne(resOnsrLinkType);
        if (null == result) {
            throw new NoneSaveException();
        }
        return linkTypeDaoToDto(result);
    }

    @Override
    public List<LinkTypeDTO> selectLinkTypes(Long versionId) {
        List<LinkTypeDTO> result = new ArrayList<>();
        List<ResOnsrLinkType> tmp = resOsnrLinkTypeDao.selectByExample(getExample(versionId));
        if (tmp.size() == 0) {
            throw new NoneGetException();
        }
        for (ResOnsrLinkType rolt : tmp) {
            LinkTypeDTO re = linkTypeDaoToDto(rolt);
            result.add(re);
        }
        return result;
    }

    @Override
    public void batchRemove(Long versionId) {
        resOsnrLinkTypeDao.deleteByExample(getExample(versionId));
    }

    private Example getExample(Long versionId) {
        Example example = new Example(ResOnsrLinkType.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("versionId", versionId);
        return example;
    }

    private Example getExample(Long versionId, Long linkTypeId) {
        Example example = new Example(ResOnsrLinkType.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("versionId", versionId);
        criteria.andEqualTo("linkTypeId", linkTypeId);
        return example;
    }


    @Override
    public void batchCreate(Long baseVersionId, Long newVersionId) {
        List<ResOnsrLinkType> oldInfo = resOsnrLinkTypeDao.selectByExample(getExample(baseVersionId));
        for (ResOnsrLinkType linkType : oldInfo) {
            LinkTypeCreateInfo newLinkType = new LinkTypeCreateInfo();
            BeanUtils.copyProperties(linkType, newLinkType);
            createLinkType(newVersionId, newLinkType);
        }
    }

    /**
     * dto转dao
     *
     * @param linkTypeCreateInfo
     * @return
     */
    private ResOnsrLinkType resOnsrLinkTypeDtoToDao(LinkTypeCreateInfo linkTypeCreateInfo) {
        ResOnsrLinkType result = new ResOnsrLinkType();
        if (linkTypeCreateInfo == null) {
            return result;
        }
        result.setLinkLoss(linkTypeCreateInfo.getLinkLoss());
        result.setLinkRate(linkTypeCreateInfo.getLinkRate());
        result.setLinkType(linkTypeCreateInfo.getLinkType());
        return result;
    }

    /**
     * dao转dto
     *
     * @param resOnsrLinkType
     * @return
     */
    private LinkTypeDTO linkTypeDaoToDto(ResOnsrLinkType resOnsrLinkType) {
        LinkTypeDTO result = new LinkTypeDTO();
        if (resOnsrLinkType == null)
            return result;
        result.setLinkTypeId(resOnsrLinkType.getLinkTypeId());
        result.setLinkType(resOnsrLinkType.getLinkType());
        result.setLinkRate(resOnsrLinkType.getLinkRate());
        result.setLinkLoss(resOnsrLinkType.getLinkLoss());
        return result;
    }
}
