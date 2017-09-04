package com.bupt.facade.impl;

import com.bupt.dao.SysVersionDao;
import com.bupt.entity.SysVersion;
import com.bupt.pojo.VersionCreateInfo;
import com.bupt.pojo.VersionDTO;
import com.bupt.pojo.VersionDTOLess;
import com.bupt.pojo.VersionSetting;
import com.bupt.facade.VersionService;
import com.bupt.pojo.versionSettings.ResourceSetting;
import com.bupt.util.exception.controller.result.NoneGetException;
import com.bupt.util.exception.controller.result.NoneRemoveException;
import com.bupt.util.exception.controller.result.NoneSaveException;
import com.bupt.util.exception.controller.result.NoneUpdateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service("versionService")
public class VersionServiceImpl implements VersionService {
    @Resource
    private SysVersionDao sysVersionDao;

    Logger logger = LoggerFactory.getLogger(VersionServiceImpl.class);

    @Override
    @Transactional
    public VersionDTO saveVersion(VersionCreateInfo versionCreateInfo) {

        SysVersion tempVersion = new SysVersion();
        tempVersion.setVersionSetting(toByteArray(versionCreateInfo.getVersionSetting()));
        tempVersion.setVersionDescription(versionCreateInfo.getVersionDescription());
        tempVersion.setVersionName(versionCreateInfo.getVersionName());

        if (sysVersionDao.insertSelective(tempVersion) > 0) {
            SysVersion newVersion = getVersionByName(versionCreateInfo.getVersionName());
            batchCreate(versionCreateInfo.getVersionSetting().getResourceSetting(),
                    getVersionByName(versionCreateInfo.getBaseVersionName()).getVersionId(), newVersion.getVersionId());
            return DOtoDTO(newVersion);
        }
        throw new NoneSaveException();
    }

    @Override
    @Transactional
    public void listRemoveVersion(List<Long> versionIdList) {
        Iterator<Long> versionIdListIterator = versionIdList.iterator();
        while (versionIdListIterator.hasNext()) {
            SysVersion temp = sysVersionDao.selectByPrimaryKey(versionIdListIterator.next());
            if (null == temp) {
                throw new NoneRemoveException();
            }
            batchRemove(toObject(temp.getVersionSetting()).getResourceSetting());
            sysVersionDao.deleteByPrimaryKey(temp.getVersionId());
        }
    }

    @Override
    public List<VersionDTOLess> listVersion() {
        Iterator<SysVersion> sysVersionIterator = sysVersionDao.selectAll().iterator();
        List<VersionDTOLess> resultList = new ArrayList<VersionDTOLess>();
        while (sysVersionIterator.hasNext()) {
            resultList.add(this.DOtoDTOLess(sysVersionIterator.next()));
        }
        if (resultList.size() == 0 || null == resultList) {
            throw new NoneGetException();
        }
        return resultList;
    }

    @Override
    public VersionDTO getVersion(Long versionId) {
        SysVersion sysVersionDO = sysVersionDao.selectByPrimaryKey(versionId);
        if (null == sysVersionDO) {
            throw new NoneGetException();
        }
        return DOtoDTO(sysVersionDO);
    }

    @Override
    public VersionDTO updateVersion(Long versionId, VersionDTO versionDTO) {
        versionDTO.setVersionId(versionId);
        if (sysVersionDao.updateByPrimaryKeySelective(DTOtoDo(versionDTO)) == 0) {
            throw new NoneUpdateException();
        }
        return DOtoDTO(sysVersionDao.selectByPrimaryKey(versionDTO.getVersionId()));
    }


    /**
     * 根据版本设置信息批量创建所有需要的资源
     * 需要提供基于的版本Id
     */
    @Transactional
    public void batchCreate(ResourceSetting rs, long baseOnId, long newId) {
        if (rs.isAmplifier()) {
            //TODO
        }
        if (rs.isDisk()) {
            //TODO
        }
        if (rs.isBussiness()) {
            //TODO
        }
        if (rs.isLink()) {
            //TODO
        }
        if (rs.isLinkType()) {
            //TODO
        }
        if (rs.isNetElement()) {
            //TODO
        }
    }

    /***
     * 根据版本设置批量删除该版本所用资源
     */
    @Transactional
    public void batchRemove(ResourceSetting rs) {
        if (rs.isAmplifier()) {
            //TODO
        }
        if (rs.isDisk()) {
            //TODO
        }
        if (rs.isBussiness()) {
            //TODO
        }
        if (rs.isLink()) {
            //TODO
        }
        if (rs.isLinkType()) {
            //TODO
        }
        if (rs.isNetElement()) {
            //TODO
        }
    }

    @Transactional
    public SysVersion getVersionByName(String versionName) {
        Example example = new Example(SysVersion.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("versionName", versionName);
        List<SysVersion> sysVersionList = sysVersionDao.selectByExample(example);
        if (sysVersionList.size() == 0 || sysVersionList.size() > 1) {
            throw new NoneGetException();
        }
        return sysVersionList.get(0);
    }


    private VersionDTO DOtoDTO(SysVersion sysVersionDO) {
        if (null == sysVersionDO) {
            return null;
        }
        VersionDTO versionDTO = new VersionDTO();
        versionDTO.setVersionId(sysVersionDO.getVersionId());
        versionDTO.setVersionName(sysVersionDO.getVersionName());
        versionDTO.setVersionSetting(this.toObject(sysVersionDO.getVersionSetting()));
        versionDTO.setVersionDescription(sysVersionDO.getVersionDescription());
        return versionDTO;
    }

    private VersionDTOLess DOtoDTOLess(SysVersion sysVersionDO) {
        if (null == sysVersionDO) {
            return null;
        }
        VersionDTOLess versionDTOLess = new VersionDTOLess();
        versionDTOLess.setVersionId(sysVersionDO.getVersionId());
        versionDTOLess.setVersionName(sysVersionDO.getVersionName());
        versionDTOLess.setVersionDescription(sysVersionDO.getVersionDescription());
        return versionDTOLess;
    }


    private SysVersion DTOtoDo(VersionDTO versionDTO) {
        if (null == versionDTO) {
            return null;
        }
        SysVersion sysVersion = new SysVersion();
        sysVersion.setVersionId(versionDTO.getVersionId());
        sysVersion.setVersionName(versionDTO.getVersionName());
        sysVersion.setVersionSetting(toByteArray(versionDTO.getVersionSetting()));
        return sysVersion;
    }

    /**
     * VersionSetting 序列化
     */
    private byte[] toByteArray(VersionSetting versionSetting) {
        byte[] bytes = null;
        ByteArrayOutputStream bos = null;
        ObjectOutputStream oos = null;
        try {
            bos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(bos);
            oos.writeObject(versionSetting);
            bytes = bos.toByteArray();
            oos.flush();
            oos.close();
            bos.close();
        } catch (IOException ex) {
            logger.error(ex.getMessage());
        } finally {
        }
        return bytes;
    }

    /**
     * versionSetting 反序列化
     */
    private VersionSetting toObject(byte[] bytes) {
        VersionSetting versionSetting = null;
        ObjectInputStream ois = null;
        ByteArrayInputStream bis = null;
        try {
            bis = new ByteArrayInputStream(bytes);
            ois = new ObjectInputStream(bis);
            versionSetting = (VersionSetting) ois.readObject();
            bis.close();
            ois.close();
        } catch (IOException ex) {
            logger.error(ex.getMessage());
        } catch (ClassNotFoundException ex) {
            logger.error(ex.getMessage());
        }
        return versionSetting;
    }


}
