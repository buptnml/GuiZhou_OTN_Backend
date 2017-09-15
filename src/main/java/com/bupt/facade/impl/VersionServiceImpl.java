package com.bupt.facade.impl;

import com.bupt.dao.SysVersionDao;
import com.bupt.entity.SysVersion;
import com.bupt.entity.SysVersionDict;
import com.bupt.service.BussinessService;
import com.bupt.pojo.VersionQuery;
import com.bupt.pojo.VersionDTO;
import com.bupt.pojo.VersionSetting;
import com.bupt.facade.VersionService;
import com.bupt.service.VersionDictService;
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
    private BussinessService bussinessService;
    @Resource
    private VersionDictService versionDictService;

    Logger logger = LoggerFactory.getLogger(VersionServiceImpl.class);

    @Override
    @Transactional
    public VersionDTO saveVersion(VersionQuery versionQuery) {
        if (sysVersionDao.insertSelective(convertToSysVersion(versionQuery)) > 0) {
            SysVersion newVersion = getVersionByName(versionQuery.getVersionName());
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
    public VersionDTO getVersion(Long versionId) {
        SysVersion sysVersionDO = sysVersionDao.selectByPrimaryKey(versionId);
        if (null == sysVersionDO) {
            throw new NoneGetException();
        }
        return DOtoDTO(sysVersionDO);
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
//
//    @Override
//    @Transactional
//    public void dataSynchronize(Long versionId,String fromVersionName) {
//        //首先想要拷贝的版本的源的信息
//        SysVersion fromVersion = getVersionByName(fromVersionName);
//        SysVersionDict fromVersionDict = versionDictService.getVersionDictByName(fromVersion.getVersionDictName());
//        //首先拷贝目标的版本的信息
//        SysVersion newVersion = sysVersionDao.selectByPrimaryKey(versionId);
//        SysVersionDict newVersionDict = versionDictService.getVersionDictByName(newVersion.getVersionDictName());
//
//        //如果源没有但是目标有则出错。否则正常拷贝
//        if(newVersionDict.getHasBussiness()){
//            if(fromVersionDict.getHasBussiness()){
//                bussinessService.batchCreate(fromVersion.getVersionId(),newVersion.getVersionId());
//            }else{
//                throw new RuntimeException();
//            }
//        }
//    }



    /***
     * 根据版本设置批量删除该版本所用资源
     */
    private void batchRemove(Long versionId) {

        SysVersion Version = sysVersionDao.selectByPrimaryKey(versionId);
        if (null == Version){
            throw new NoneRemoveException();
        }

        SysVersionDict versionDict = versionDictService.getVersionDictByName(Version.getVersionDictName());
        if(versionDict.getHasBussiness()){
            bussinessService.batchRemove(versionId);
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

//    private VersionDTOLess DOtoDTOLess(SysVersion sysVersionDO) {
//        if (null == sysVersionDO) {
//            return null;
//        }
//        VersionDTOLess versionDTOLess = new VersionDTOLess();
//        versionDTOLess.setVersionId(sysVersionDO.getVersionId());
//        versionDTOLess.setVersionName(sysVersionDO.getVersionName());
//        versionDTOLess.setVersionDescription(sysVersionDO.getVersionDescription());
//        return versionDTOLess;
//    }


//    private SysVersion DTOtoDo(VersionDTO versionDTO) {
//        if (null == versionDTO) {
//            return null;
//        }
//        SysVersion sysVersion = new SysVersion();
//        sysVersion.setVersionId(versionDTO.getVersionId());
//        sysVersion.setVersionName(versionDTO.getVersionName());
//        sysVersion.setVersionSetting(toByteArray(versionDTO.getVersionSetting()));
//        return sysVersion;
//    }

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
