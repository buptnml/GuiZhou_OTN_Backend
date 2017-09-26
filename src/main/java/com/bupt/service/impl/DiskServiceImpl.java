package com.bupt.service.impl;

import com.bupt.dao.ResDiskDao;
import com.bupt.pojo.DiskCreateInfo;
import com.bupt.entity.ResDisk;
import com.bupt.pojo.DiskDTO;
import com.bupt.service.DiskService;
import com.bupt.service.NetElementService;
import com.bupt.util.exception.controller.result.NoneGetException;
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

@Service("diskService")
public class DiskServiceImpl implements DiskService {
    @Resource
    private ResDiskDao resDiskDao;
    @Resource
    private NetElementService netElementService;

    @Override
    public List<DiskDTO> listDiskByNetElement(Long versionId, Long netElementId) {
        Iterator<ResDisk> searchListIte = resDiskDao.selectByExample(getExample(versionId, netElementId)).iterator();
        if (!searchListIte.hasNext()) {
            throw new NoneGetException();
        }
        List<DiskDTO> resultList = new ArrayList<>();
        while (searchListIte.hasNext()) {
            resultList.add(convertToDTO(searchListIte.next()));
        }
        return resultList;
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
        } else return convertToDTO(resDiskDao.selectOne(createResDisk(diskCreateInfo)));
    }


    @Override
    @Transactional
    public void listRemove(Long versionId, Long netElementId, List<Long> diskIdList) {
        for (Long diskId : diskIdList) {
            if (resDiskDao.deleteByExample(getExample(versionId, netElementId, diskId)) != 1) {
                throw new NoneUpdateException();
            }
        }
    }

    @Override
    public void batchRemove(Long versionId) {
        Example example = getExample(versionId);
        resDiskDao.deleteByExample(example);
    }

    @Override
    public void batchCreate(Long baseVersionId, Long newVersionId) {
        Example example = getExample(baseVersionId);
        List<ResDisk> basicVersionList = resDiskDao.selectByExample(example);
        for (ResDisk disk : basicVersionList) {
            DiskCreateInfo newDisk = new DiskCreateInfo(disk.getDiskName(), disk.getDiskType(), disk.getSlotId());
            saveDisk(newVersionId, netElementService.getNewElementId(baseVersionId, disk.getNetElementId(),
                    newVersionId), newDisk);
        }
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


    private Example getExample(Long versionId, Long netElementId) {
        Example example = new Example(ResDisk.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("netElementId", netElementId);
        criteria.andEqualTo("versionId", versionId);
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
