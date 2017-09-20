package com.bupt.service.impl;

import com.bupt.dao.ResLinkDao;
import com.bupt.entity.ResLink;
import com.bupt.pojo.LinkCreateInfo;
import com.bupt.pojo.ResLinkDTO;
import com.bupt.service.LinkService;
import com.bupt.util.exception.controller.result.NoneGetException;
import com.bupt.util.exception.controller.result.NoneRemoveException;
import com.bupt.util.exception.controller.result.NoneSaveException;
import com.bupt.util.exception.controller.result.NoneUpdateException;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service("linkService")
public class LinkServiceImpl implements LinkService {
    @Resource
    private ResLinkDao resLinkDao;

    @Override
    public ResLinkDTO saveResLink(Long versionId, LinkCreateInfo linkCreateInfo) {
        ResLink insertInfo = convertToResLink(linkCreateInfo);
        insertInfo.setVersionId(versionId);
        if (resLinkDao.insertSelective(insertInfo) > 0) {
            return convertToResLinkDTO(resLinkDao.selectOne(insertInfo));
        }
        throw new NoneSaveException();
    }

    @Override
    @Transactional
    public void listRemoveResLink(Long versionId, List<Long> linkIdList) {
        Iterator<Long> idListIterator = linkIdList.iterator();
        while (idListIterator.hasNext()) {
            if (resLinkDao.deleteByPrimaryKey(idListIterator.next()) == 0) {
                throw new NoneRemoveException();
            }
        }
    }

    @Override
    public ResLinkDTO updateResLink(Long versionId, Long linkId, LinkCreateInfo linkCreateInfo) {
        ResLink resLink = convertToResLink(linkCreateInfo);
        Example updateExample =new Example(ResLink.class);
        Example.Criteria criteria = updateExample.createCriteria();
        criteria.andEqualTo("versionId", versionId);
        criteria.andEqualTo("linkId", linkId);
        if (resLinkDao.updateByExampleSelective(resLink, updateExample) == 1) {
            return convertToResLinkDTO(resLinkDao.selectOne(resLink));
        }
        throw new NoneUpdateException();
    }


    private Example getExample(Long versionId) {
        Example example = new Example(ResLink.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("versionId", versionId);
        return example;
    }


    @Override
    public List<ResLinkDTO> getResLink(Long versionId) {
        List<ResLink> resLinksList = resLinkDao.selectByExample(getExample(versionId));
        if (resLinksList.size() == 0) {
            throw new NoneGetException();
        }
        List<ResLinkDTO> resLinkDTOList = new ArrayList<>();
        for (int i = 0; i < resLinksList.size(); i++) {
            resLinkDTOList.add(this.convertToResLinkDTO(resLinksList.get(i)));
        }
        return resLinkDTOList;
    }

    @Override
    public void batchRemove(Long versionId) {
        resLinkDao.deleteByExample(getExample(versionId));
    }

    @Override
    public void batchCreate(Long baseVersionId, Long newVersionId) {
        List<ResLink> resLinksList = resLinkDao.selectByExample(getExample(baseVersionId));
        for (ResLink link : resLinksList) {
            LinkCreateInfo newLink = new LinkCreateInfo();
            BeanUtils.copyProperties(link,newLink);
            saveResLink(newVersionId,newLink);
        }
    }


    private ResLink convertToResLink(Object inputObject) {
        if (null == inputObject) {
            return null;
        }
        ResLink resLink = new ResLink();
        BeanUtils.copyProperties(inputObject, resLink);
        return resLink;
    }

    private ResLinkDTO convertToResLinkDTO(Object inputObject) {
        if (null == inputObject) {
            return null;
        }
        ResLinkDTO resLinkDTO = new ResLinkDTO();
        BeanUtils.copyProperties(inputObject, resLinkDTO);
        return resLinkDTO;
    }

}
