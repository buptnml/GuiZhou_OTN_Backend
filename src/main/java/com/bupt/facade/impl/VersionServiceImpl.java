package com.bupt.facade.impl;

import com.bupt.dao.SysVersionDao;
import com.bupt.entity.SysVersion;
import com.bupt.facade.BussinessService;
import com.bupt.facade.VersionService;
import com.bupt.pojo.VersionDTO;
import com.bupt.pojo.VersionDTOWithVersionDictDTO;
import com.bupt.pojo.VersionDictDTO;
import com.bupt.pojo.VersionQuery;
import com.bupt.pojo.versionSettings.VersionSetting;
import com.bupt.service.*;
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
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service("versionService")
class VersionServiceImpl implements VersionService {
    @Resource
    private SysVersionDao sysVersionDao;
    @Resource
    private VersionDictService versionDictService;
    @Resource
    private AmplifierService amplifierService;
    @Resource
    private LinkTypeService linkTypeService;

    @Resource
    private BussinessService bussinessService;
    @Resource
    private DiskService diskService;
    @Resource
    private LinkService linkService;
    @Resource
    private NetElementService netElementService;

    private Logger logger = LoggerFactory.getLogger(VersionServiceImpl.class);

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
        for (Long aVersionIdList : versionIdList) {
            SysVersion temp = sysVersionDao.selectByPrimaryKey(aVersionIdList);
            if (null == temp) {
                throw new NoneRemoveException("版本ID不存在！");
            }
            batchRemove(temp.getVersionId());
            sysVersionDao.deleteByPrimaryKey(temp.getVersionId());
        }
    }

    @Override
    @Transactional
    public List<VersionDTO> listVersion() {
        List<VersionDTO> resultList = sysVersionDao.selectAll().stream().sorted(Comparator.comparing
                (SysVersion::getGmtModified).reversed()).map(this::DOtoDTO).collect(Collectors.toList());
        if (resultList.size() == 0) {
            throw new NoneGetException("没有查询到版本相关记录！");
        }
        return resultList;
    }

    @Override
    public VersionDTOWithVersionDictDTO getVersion(Long versionId) {
        SysVersion sysVersionDO = sysVersionDao.selectByPrimaryKey(versionId);
        if (null == sysVersionDO) {
            throw new NoneGetException("没有从数据库中找到该版本记录！");
        }
        VersionDTOWithVersionDictDTO versionDTOWithVersionDictDTO = new VersionDTOWithVersionDictDTO(DOtoDTO(sysVersionDO));
        versionDTOWithVersionDictDTO.setVersionDict(versionDictService.getVersionDictByName(sysVersionDO.getVersionDictName()));
        return versionDTOWithVersionDictDTO;
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

    @Override
    public void dataSynchronize(Long fromVersionId, Long toVersionId) {
        SysVersion fromVersion = sysVersionDao.selectByPrimaryKey(fromVersionId);
        SysVersion toVersion = sysVersionDao.selectByPrimaryKey(toVersionId);
        if (null == fromVersion || null == toVersion) {
            throw new NoneGetException("同步数据失败！!");
        }
        batchRemove(toVersionId);
        batchCreate(fromVersionId, toVersionId);
    }

    /**
     * 从指定版本中拷贝数据
     */
    private void batchCreate(Long fromVersionId, Long toVersionId) {
        SysVersion Version = sysVersionDao.selectByPrimaryKey(toVersionId);
        if (null == Version) {
            throw new NoneGetException("同步数据失败！!");
        }
        VersionDictDTO dictSetting = versionDictService.getVersionDictByName(Version.getVersionDictName());
        //创建的时候最先创建网元数据！！！重要！
        if (dictSetting.getHasNetElement()) {
            netElementService.batchCreate(fromVersionId, toVersionId);
        }
        if (dictSetting.getHasDisk()) {
            diskService.batchCreate(fromVersionId, toVersionId);
        }
        if (dictSetting.getHasLink()) {
            linkService.batchCreate(fromVersionId, toVersionId);
        }
        if (dictSetting.getHasAmplifier()) {
            amplifierService.batchCreate(fromVersionId, toVersionId);
        }
        if (dictSetting.getHasLinkType()) {
            linkTypeService.batchCreate(fromVersionId, toVersionId);
        }
        /*最后复制业务*/
        if (dictSetting.getHasBussiness()) {
            bussinessService.batchCreate(fromVersionId, toVersionId);
        }
    }

    /***
     * 根据版本设置批量删除该版本所用资源
     */
    private void batchRemove(Long versionId) {
        SysVersion Version = sysVersionDao.selectByPrimaryKey(versionId);
        if (null == Version) {
            throw new NoneRemoveException("删除版本数据失败！！");
        }
        VersionDictDTO dictSetting = versionDictService.getVersionDictByName(Version.getVersionDictName());
        if (dictSetting.getHasBussiness()) {
            bussinessService.batchRemove(versionId);
        }
        if (dictSetting.getHasDisk()) {
            diskService.batchRemove(versionId);
        }
        if (dictSetting.getHasLink()) {
            linkService.batchRemove(versionId);
        }
        if (dictSetting.getHasNetElement()) {
            netElementService.batchRemove(versionId);
        }
        if (dictSetting.getHasAmplifier()) {
            amplifierService.batchRemove(versionId);
        }
        if (dictSetting.getHasLinkType()) {
            linkTypeService.batchRemove(versionId);
        }
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
        sysVersionDO.setVersionSetting(null);
        BeanUtils.copyProperties(sysVersionDO, result);
        return result;
    }

    private SysVersion convertToSysVersion(VersionQuery versionQuery) {
        if (null == versionQuery) {
            return null;
        }
        SysVersion result = new SysVersion();
        BeanUtils.copyProperties(versionQuery, result);
        return result;
    }

    /**
     * VersionSetting 序列化
     */
    private byte[] toByteArray(VersionSetting versionSetting) {
        byte[] bytes = null;
        ByteArrayOutputStream bos;
        ObjectOutputStream oos;
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
        }
        return bytes;
    }

    /**
     * versionSetting 反序列化
     */
    private VersionSetting toObject(byte[] bytes) {
        VersionSetting versionSetting = null;
        ObjectInputStream ois;
        ByteArrayInputStream bis;
        try {
            bis = new ByteArrayInputStream(bytes);
            ois = new ObjectInputStream(bis);
            versionSetting = (VersionSetting) ois.readObject();
            bis.close();
            ois.close();
        } catch (IOException | ClassNotFoundException ex) {
            logger.error(ex.getMessage());
        }
        return versionSetting;
    }


}
