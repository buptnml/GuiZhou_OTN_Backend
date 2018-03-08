package com.otn.service.impl;

import com.otn.dao.ResDiskDao;
import com.otn.dao.ResNetElementDao;
import com.otn.entity.ResDisk;
import com.otn.entity.ResNetElement;
import com.otn.pojo.DiskCreateInfo;
import com.otn.pojo.DiskDTO;
import com.otn.service.DiskService;
import com.otn.service.utils.BatchDMLUtils;
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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

@Service("diskService")
class DiskServiceImpl implements DiskService {
    private final static ExecutorService EXECUTOR = Executors.newWorkStealingPool();
    @Resource
    private ResDiskDao resDiskDao;
    @Resource
    private ResNetElementDao resNetElementDao;

    @Override
    /*机盘取出来的时候要按照机盘槽位来排序*/
    public List<DiskDTO> listDiskByNetElement(Long versionId, Long netElementId) {
        return resDiskDao.selectByExample(getExample(versionId, netElementId)).stream().sorted
                (Comparator.comparing(ResDisk::getSlotId)).map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<DiskDTO> listDiskByType(Long versionId, String diskType) {
        Example example = new Example(ResDisk.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("versionId", versionId);
        criteria.andEqualTo("diskType", diskType);
        return resDiskDao.selectByExample(example).stream().map(this::convertToDTO).collect(Collectors
                .toList());
    }

    @Override
    public DiskDTO saveDisk(Long versionId, Long netElementId, DiskCreateInfo diskCreateInfo) {
        ResDisk insertInfo = createResDisk(diskCreateInfo);
        insertInfo.setVersionId(versionId);
        insertInfo.setNetElementId(netElementId);
        if (resDiskDao.insertSelective(insertInfo) == 0) {
            throw new NoneSaveException();
        }
        return convertToDTO(resDiskDao.selectOne(insertInfo));
    }

    @Override
    public DiskDTO updateDisk(Long versionId, Long netElementId, Long diskId, DiskCreateInfo diskCreateInfo) {
        if (resDiskDao.updateByExampleSelective(createResDisk(diskCreateInfo), getExample(versionId, netElementId,
                diskId)) != 1) {
            throw new NoneUpdateException();
        } else return convertToDTO(resDiskDao.selectByPrimaryKey(diskId));
    }


    @Override
    @Transactional
    public void listRemove(Long versionId, Long netElementId, List<Long> diskIdList) {
        if (diskIdList.size() == 0) {
            resDiskDao.deleteByExample(getExample(versionId, netElementId));
        }
        diskIdList.forEach(id -> {
            if (resDiskDao.deleteByExample(getExample(versionId, netElementId, id)) != 1) {
                throw new NoneRemoveException("机盘移除失败！");
            }
        });
    }

    @Override
    public int batchRemove(Long versionId) {
        return resDiskDao.deleteByExample(getExample(versionId));
    }

    @Override
    public int batchCreate(Long baseVersionId, Long newVersionId) {
        List<ResDisk> basicVersionList = resDiskDao.selectByExample(getExample(baseVersionId)).stream().map(resDisk -> {
            resDisk.setDiskId(null);
            resDisk.setVersionId(newVersionId);
            resDisk.setNetElementId(getNewElementId(baseVersionId, resDisk.getNetElementId(), newVersionId));
            resDisk.setGmtCreate(null);
            resDisk.setGmtModified(null);
            return resDisk;
        }).collect(Collectors.toList());
        try {
            return batchInsert(basicVersionList);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return -1;
    }

    @Override
    public int batchInsert(final List<ResDisk> batchList) throws InterruptedException {
        return BatchDMLUtils.batchDMLAction(batchList, resDiskDao::insertSelective);
//        if (batchList.size() <= 2000) {
//            batchList.forEach(resDiskDao::insertSelective);
//        } else {
//            CountDownLatch count = new CountDownLatch(batchList.size());
//            batchList.parallelStream().forEach(disk -> EXECUTOR.execute(() -> {
//                resDiskDao.insertSelective(disk);
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

    private Example getExample(Long versionId, Long netElementId, Long diskId) {
        Example updateExample = new Example(ResDisk.class);
        Example.Criteria criteria = updateExample.createCriteria();
        criteria.andEqualTo("netElementId", netElementId);
        criteria.andEqualTo("versionId", versionId);
        criteria.andEqualTo("diskId", diskId);
        return updateExample;
    }

    private Example getExample(Long baseVersionId) {
        Example example = new Example(ResDisk.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("versionId", baseVersionId);
        return example;
    }


    public Example getExample(Long versionId, Long netElementId) {
        Example example = new Example(ResDisk.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("netElementId", netElementId);
        criteria.andEqualTo("versionId", versionId);
        example.orderBy("slotId");
        return example;
    }

    private ResDisk createResDisk(Object inputObject) {
        if (null == inputObject) {
            return null;
        }
        ResDisk resDisk = new ResDisk();
        BeanUtils.copyProperties(inputObject, resDisk);
        return resDisk;
    }

    private DiskDTO convertToDTO(Object inputObject) {
        if (null == inputObject) {
            return null;
        }
        DiskDTO diskDTO = new DiskDTO();
        BeanUtils.copyProperties(inputObject, diskDTO);
        return diskDTO;
    }

}
