package com.otn.service.impl;

import com.otn.dao.ResLinkDao;
import com.otn.dao.ResNetElementDao;
import com.otn.entity.ResLink;
import com.otn.entity.ResNetElement;
import com.otn.pojo.LinkCreateInfo;
import com.otn.pojo.LinkDTO;
import com.otn.service.LinkService;
import com.otn.service.utils.BatchDMLUtils;
import com.otn.util.exception.controller.result.NoneGetException;
import com.otn.util.exception.controller.result.NoneRemoveException;
import com.otn.util.exception.controller.result.NoneSaveException;
import com.otn.util.exception.controller.result.NoneUpdateException;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service("linkService")
class LinkServiceImpl implements LinkService {
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
    public LinkDTO getLink(Long versionId, Long linkId) {
        return convertToResLinkDTO(resLinkDao.selectByExample(getExample(versionId, linkId)).get(0));
    }

    @Override
    @Transactional
    public void listRemoveResLink(Long versionId, List<Long> linkIdList) {
        for (Long aLinkIdList : linkIdList) {
            if (resLinkDao.deleteByPrimaryKey(aLinkIdList) == 0) {
                throw new NoneRemoveException();
            }
        }
    }

    @Override
    public LinkDTO updateResLink(Long versionId, Long linkId, LinkCreateInfo linkCreateInfo) {
        if (resLinkDao.updateByExampleSelective(convertToResLink(linkCreateInfo), getExample(versionId, linkId)) == 1) {
            return convertToResLinkDTO(resLinkDao.selectByExample(getExample(versionId, linkId)).get(0));
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


    public Example getExample(Long versionId) {
        Example example = new Example(ResLink.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("versionId", versionId);
        return example;
    }


    @Override
    public List<LinkDTO> listLinks(Long versionId) {
        List<LinkDTO> result = resLinkDao.selectByExample(getExample(versionId)).stream().sorted(Comparator
                .comparing(ResLink::getGmtModified).reversed()).map(this::convertToResLinkDTO).collect(Collectors
                .toList());
        if (result.size() == 0) {
            throw new NoneGetException("没有查询到链路相关记录");
        }
        return result;
    }

    @Override
    public int batchRemove(Long versionId) {
        return resLinkDao.deleteByExample(getExample(versionId));
    }

    @Override
    public int batchCreate(Long baseVersionId, Long newVersionId) {
        List<ResLink> resLinksList = resLinkDao.selectByExample(getExample(baseVersionId)).stream().map(link -> {
            link.setLinkId(null);
            link.setEndAId(getNewElementId(baseVersionId, link.getEndAId(), newVersionId));
            link.setEndZId(getNewElementId(baseVersionId, link.getEndZId(), newVersionId));
            link.setVersionId(newVersionId);
            link.setGmtCreate(null);
            link.setGmtModified(null);
            return link;
        }).collect(Collectors.toList());
        try {
            return batchInsert(resLinksList);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return -1;
    }

    @Override
    public int batchInsert(final List<ResLink> batchList) throws InterruptedException {
        return BatchDMLUtils.batchDMLActionForEach(batchList, resLinkDao::insertSelective);
//        if (batchList.size() <= 2000) {
//            batchList.forEach(resLinkDao::insertSelective);
//        } else {
//            CountDownLatch count = new CountDownLatch(batchList.size());
//            batchList.parallelStream().forEach(link -> EXECUTOR.execute(() -> {
//                resLinkDao.insertSelective(link);
//                count.countDown();
//            }));
//            try {
//                count.await();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//        return batchList.size();
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
        if (forthList.size() == 0 && backList.size() == 0) {
            return null;
        }
        int randomIndex = new Random().nextInt(forthList.size() + backList.size());
        return convertToResLinkDTO(forthList.size() > randomIndex ? forthList.get(randomIndex) : backList.get
                (randomIndex - forthList.size()));
    }

    @Override
    public List<ResLink> getReferLink(Long versionId, Long netElementId) {
        List<ResLink> links = resLinkDao.selectByExample(getExample(versionId, "endAId", netElementId));
        links.addAll(resLinkDao.selectByExample(getExample(versionId, "endZId", netElementId)));
        return links;
    }

    @Override
    public List<LinkDTO> ListLinkByType(Long versionId, String linkType) {
        Example example = new Example(ResLink.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("versionId", versionId);
        criteria.andEqualTo("linkType", linkType);
        return resLinkDao.selectByExample(example).stream().map(this::convertToResLinkDTO).collect
                (Collectors.toList());
    }

    private Example getExample(Long versionId, String condition, Long nodeId) {
        Example removeExample = new Example(ResLink.class);
        Example.Criteria criteria = removeExample.createCriteria();
        criteria.andEqualTo("versionId", versionId);
        criteria.andEqualTo(condition, nodeId);
        return removeExample;
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
