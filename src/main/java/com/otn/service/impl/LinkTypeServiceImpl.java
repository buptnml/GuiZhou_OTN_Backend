package com.otn.service.impl;

import com.otn.dao.ResOsnrLinkTypeDao;
import com.otn.entity.ResOnsrLinkType;
import com.otn.pojo.LinkTypeCreateInfo;
import com.otn.pojo.LinkTypeDTO;
import com.otn.service.LinkTypeService;
import com.otn.service.utils.BatchDMLUtils;
import com.otn.util.exception.controller.result.NoneGetException;
import com.otn.util.exception.controller.result.NoneRemoveException;
import com.otn.util.exception.controller.result.NoneSaveException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

/**
 * Created by caoxiaohong on 17/9/20.
 */
@Service(value = "linkTypeService")
class LinkTypeServiceImpl implements LinkTypeService {
    private final static ExecutorService EXECUTOR = Executors.newWorkStealingPool();

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
            return linkTypeDaoToDto(resOsnrLinkTypeDao.selectByPrimaryKey(linkTypeId));
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
    public List<LinkTypeDTO> listLinkTypes(Long versionId) {
        List<LinkTypeDTO> result = resOsnrLinkTypeDao.selectByExample(getExample(versionId)).stream().sorted
                (Comparator.comparing(ResOnsrLinkType::getGmtModified).reversed()).map(this::linkTypeDaoToDto).
                collect(Collectors.toList());
        if (result.size() == 0) {
            throw new NoneGetException("没有找到链路类型相关记录");
        }
        return result;
    }

    @Override
    public LinkTypeDTO getLinkType(Long versionId, String linkType) {
        return linkTypeDaoToDto(resOsnrLinkTypeDao.selectByExample(getExample(versionId, linkType)).get(0));
    }

    @Override
    public LinkTypeDTO getLinkTypeById(Long versionId, Long linkTypeId) {
        return linkTypeDaoToDto(resOsnrLinkTypeDao.selectByExample(getExample(versionId, linkTypeId)).get(0));
    }

    @Override
    public int batchRemove(Long versionId) {
        return resOsnrLinkTypeDao.deleteByExample(getExample(versionId));
    }

    public Example getExample(Long versionId) {
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

    private Example getExample(Long versionId, String linkType) {
        Example example = new Example(ResOnsrLinkType.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("versionId", versionId);
        criteria.andEqualTo("linkType", linkType);
        return example;
    }


    @Override
    public int batchCreate(Long baseVersionId, Long newVersionId) {
        List<ResOnsrLinkType> list = resOsnrLinkTypeDao.selectByExample(getExample(baseVersionId)).stream().map
                (linkType -> {
                    linkType.setLinkTypeId(null);
                    linkType.setVersionId(newVersionId);
                    linkType.setGmtCreate(null);
                    linkType.setGmtModified(null);
                    return linkType;
                }).collect(Collectors.toList());
        try {
            return batchInsert(list);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return -1;
    }

    @Override
    public int batchInsert(final List<ResOnsrLinkType> batchList) throws InterruptedException {
        return BatchDMLUtils.batchDMLActionForEach(batchList, resOsnrLinkTypeDao::insertSelective);
//        if (batchList.size() <= 2000) {
//            batchList.forEach(resOsnrLinkTypeDao::insertSelective);
//        } else {
//            CountDownLatch count = new CountDownLatch(batchList.size());
//            batchList.parallelStream().forEach(linkType -> EXECUTOR.execute(() -> {
//                resOsnrLinkTypeDao.insertSelective(linkType);
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
