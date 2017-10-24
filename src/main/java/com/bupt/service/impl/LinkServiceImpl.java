package com.bupt.service.impl;

import com.bupt.dao.ResLinkDao;
import com.bupt.dao.ResNetElementDao;
import com.bupt.entity.ResLink;
import com.bupt.entity.ResNetElement;
import com.bupt.pojo.LinkCreateInfo;
import com.bupt.pojo.LinkDTO;
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
import java.util.List;
import java.util.Random;

@Service("linkService")
public class LinkServiceImpl implements LinkService {
    @Resource
    private ResLinkDao resLinkDao;
    @Resource
    private ResNetElementDao resNetElementDao;

    @Override
    public LinkDTO saveResLink(Long versionId, LinkCreateInfo linkCreateInfo) {
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
        //todo 删除链路的时候要删除链路所在的路由
        for (Long aLinkIdList : linkIdList) {
            if (resLinkDao.deleteByPrimaryKey(aLinkIdList) == 0) {
                throw new NoneRemoveException();
            }
        }
    }

    @Override
    public LinkDTO updateResLink(Long versionId, Long linkId, LinkCreateInfo linkCreateInfo) {
        ResLink updateInfo = convertToResLink(linkCreateInfo);
        if (resLinkDao.updateByExampleSelective(convertToResLink(linkCreateInfo), getExample(versionId, linkId)) == 1) {
            return convertToResLinkDTO(resLinkDao.selectOne(updateInfo));
        }
        throw new NoneUpdateException();
    }

    private Example getExample(Long versionId, Long linkId) {
        Example updateExample = new Example(ResLink.class);
        Example.Criteria criteria = updateExample.createCriteria();
        criteria.andEqualTo("versionId", versionId);
        criteria.andEqualTo("linkId", linkId);
        return updateExample;
    }


    private Example getExample(Long versionId) {
        Example example = new Example(ResLink.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("versionId", versionId);
        return example;
    }


    @Override
    public List<LinkDTO> getResLink(Long versionId) {
        List<ResLink> resLinksList = resLinkDao.selectByExample(getExample(versionId));
        if (resLinksList.size() == 0) {
            throw new NoneGetException();
        }
        List<LinkDTO> linkDTOList = new ArrayList<>();
        for (ResLink aResLinksList : resLinksList) {
            linkDTOList.add(this.convertToResLinkDTO(aResLinksList));
        }
        return linkDTOList;
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
            BeanUtils.copyProperties(link, newLink);
            newLink.setEndAId(getNewElementId(baseVersionId, newLink.getEndAId(), newVersionId));
            newLink.setEndZId(getNewElementId(baseVersionId, newLink.getEndZId(), newVersionId));
            saveResLink(newVersionId, newLink);
        }
    }

    /**
     * 给定一个旧版本的网元ID和旧版本id
     * 指定一个新版本Id，返回该版本新生成的网元id
     */
    private Long getNewElementId(Long oldVersionId, Long oldNetElementId, Long newVersionId) {
        ResNetElement oldNetElement = resNetElementDao.selectByExample(getNetElementExample(oldVersionId,
                oldNetElementId)).get(0);
        return resNetElementDao.selectByExample(getNetElementExample(newVersionId, oldNetElement.getNetElementName())
        ).get(0).getNetElementId();
    }

    private Example getNetElementExample(Long versionId, Long netElementId) {
        Example example = new Example(ResNetElement.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("versionId", versionId);
        criteria.andEqualTo("netElementId", netElementId);
        return example;
    }

    private Example getNetElementExample(Long versionId, String netElementName) {
        Example example = new Example(ResNetElement.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("versionId", versionId);
        criteria.andEqualTo("netElementName", netElementName);
        return example;
    }

    @Override
    public LinkDTO getLinkByNodes(Long versionId, String node1Name, String node2Name) {
        /*Link是没有方向的，但是数据是有方向的，因此要考虑两个方向查询的结果*/
        /*前向*/
        List<ResLink> forthList = resLinkDao.selectByExample(getExample(versionId, node1Name, node2Name));
        /*后向*/
        List<ResLink> backList = resLinkDao.selectByExample(getExample(versionId, node2Name, node1Name));
        /*在两个结果集中随机制定一个结果*/
        int randomIndex = new Random().nextInt(forthList.size() + backList.size());
        return convertToResLinkDTO(forthList.size() > randomIndex ? forthList.get(randomIndex) : backList.get
                (randomIndex - forthList.size()));
    }


    private Example getExample(Long versionId, String node1Name, String node2Name) {
        Example updateExample = new Example(ResLink.class);
        Example.Criteria criteria = updateExample.createCriteria();
        criteria.andEqualTo("versionId", versionId);
        criteria.andEqualTo("endAName", node1Name);
        criteria.andEqualTo("endZName", node2Name);
        return updateExample;
    }

    private ResLink convertToResLink(Object inputObject) {
        if (null == inputObject) {
            return null;
        }
        ResLink resLink = new ResLink();
        BeanUtils.copyProperties(inputObject, resLink);
        return resLink;
    }

    private LinkDTO convertToResLinkDTO(Object inputObject) {
        if (null == inputObject) {
            return null;
        }
        LinkDTO linkDTO = new LinkDTO();
        BeanUtils.copyProperties(inputObject, linkDTO);
        return linkDTO;
    }

}
