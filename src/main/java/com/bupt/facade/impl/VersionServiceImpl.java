package com.bupt.facade.impl;

import com.bupt.dao.SysVersionDao;
import com.bupt.entity.SysVersion;
import com.bupt.entity.SysVersionDict;
import com.bupt.pojo.VersionDetail;
import com.bupt.service.*;
import com.bupt.pojo.VersionQuery;
import com.bupt.pojo.VersionDTO;
import com.bupt.pojo.VersionSetting;
import com.bupt.facade.VersionService;
import com.bupt.util.exception.controller.result.NoneGetException;
import com.bupt.util.exception.controller.result.NoneRemoveException;
import com.bupt.util.exception.controller.result.NoneSaveException;
import com.bupt.util.exception.controller.result.NoneUpdateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
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
    @Resource
    private VersionDictService versionDictService;

    @Resource
    private BussinessService bussinessService;
    @Resource
    private DiskService diskService;
    @Resource
    private LinkService linkService;
    @Resource
    private NetElementService netElementService;

    Logger logger = LoggerFactory.getLogger(VersionServiceImpl.class);

    @Override
    @Transactional
    public VersionDTO saveVersion(VersionQuery versionQuery) {
        if (sysVersionDao.insertSelective(convertToSysVersion(versionQuery)) > 0) {
            SysVersion newVersion = getVersionByName(versionQuery.getVersionName());
            batchCreate(newVersion.getVersionId());
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
            batchRemove(temp.getVersionId());
            sysVersionDao.deleteByPrimaryKey(temp.getVersionId());
        }
    }

    @Override
    @Transactional
    public List<VersionDTO> listVersion() {
        Iterator<SysVersion> sysVersionIterator = sysVersionDao.selectAll().iterator();
        List<VersionDTO> resultList = new ArrayList<VersionDTO>();
        while (sysVersionIterator.hasNext()) {
            resultList.add(DOtoDTO(sysVersionIterator.next()));
        }
        if (resultList.size() == 0 || null == resultList) {
            throw new NoneGetException();
        }
        return resultList;
    }

    @Override
    public VersionDetail getVersion(Long versionId) {
        SysVersion sysVersionDO = sysVersionDao.selectByPrimaryKey(versionId);
        if (null == sysVersionDO) {
            throw new NoneGetException();
        }
        VersionDetail versionDetail = new VersionDetail(DOtoDTO(sysVersionDO));
        versionDetail.setVersionDict(versionDictService.getVersionDictByName(sysVersionDO.getVersionDictName()));
        return versionDetail;
    }

    @Override
    public VersionDTO updateVersion(Long versionId, VersionQuery versionQuery) {
        SysVersion updateInfo = convertToSysVersion(versionQuery);
        updateInfo.setVersionId(versionId);

        if (sysVersionDao.updateByPrimaryKeySelective(updateInfo) == 0) {
            throw new NoneUpdateException();
        }
        return DOtoDTO(sysVersionDao.selectByPrimaryKey(versionId));
    }

    /**
     * 从基础版本中拷贝数据到新版本中
     *
     * @param versionId
     */
    private void batchCreate(Long versionId) {
        SysVersion Version = sysVersionDao.selectByPrimaryKey(versionId);
        if (null == Version) {
            throw new NoneRemoveException("Fail to create version!");
        }
        SysVersionDict dictSetting = versionDictService.getVersionDictByName(Version.getVersionDictName());

        //创建的时候最先创建网元数据！！！重要！
        if(dictSetting.getHasNetElement()){
            netElementService.batchCreate(100000000000L,versionId);
        }
        if (dictSetting.getHasBussiness()) {
            bussinessService.batchCreate(100000000000L,versionId);
        }
        if (dictSetting.getHasDisk()) {
            diskService.batchCreate(100000000000L,versionId);
        }
        if(dictSetting.getHasLink()){
            linkService.batchCreate(100000000000L,versionId);
        }

        //TODO 等到未来其他资源补齐以后补充batchRemove内容
    }

    /***
     * 根据版本设置批量删除该版本所用资源
     */
    private void batchRemove(Long versionId) {

        SysVersion Version = sysVersionDao.selectByPrimaryKey(versionId);
        if (null == Version) {
            throw new NoneRemoveException("Fail to delete version info.");
        }

        SysVersionDict dictSetting = versionDictService.getVersionDictByName(Version.getVersionDictName());
        if (dictSetting.getHasBussiness()) {
            bussinessService.batchRemove(versionId);
        }
        if (dictSetting.getHasDisk()) {
            diskService.batchRemove(versionId);
        }
        if(dictSetting.getHasLink()){
            linkService.batchRemove(versionId);
        }
        if(dictSetting.getHasNetElement()){
            netElementService.batchRemove(versionId);
        }
        //TODO 等到未来其他资源补齐以后补充batchRemove内容
    }

    @Transactional
    public SysVersion getVersionByName(String versionName) {
        List<SysVersion> sysVersionList = sysVersionDao.selectByExample(getExample(versionName));
        if (sysVersionList.size() == 0 || sysVersionList.size() > 1) {
            throw new NoneGetException();
        }
        return sysVersionList.get(0);
    }

    private Example getExample(String versionName) {
        Example example = new Example(SysVersion.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("versionName", versionName);
        return example;
    }


    private VersionDTO DOtoDTO(SysVersion sysVersionDO) {
        if (null == sysVersionDO) {
            return null;
        }
        VersionDTO result = new VersionDTO();
//        result.setVersionSetting(this.toObject(sysVersionDO.getVersionSetting()));
        sysVersionDO.setVersionSetting(null);
        BeanUtils.copyProperties(sysVersionDO, result);
        return result;
    }

    private SysVersion convertToSysVersion(VersionQuery versionQuery) {
        if (null == versionQuery) {
            return null;
        }
        SysVersion result = new SysVersion();

//        result.setVersionSetting(toByteArray(versionQuery.getVersionSetting()));
//        versionQuery.setVersionSetting(null);

        BeanUtils.copyProperties(versionQuery, result);
        return result;
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
