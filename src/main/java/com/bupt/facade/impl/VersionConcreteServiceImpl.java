package com.bupt.facade.impl;

import com.bupt.dao.SysVersionDao;
import com.bupt.entity.SysVersion;
import com.bupt.facade.BussinessService;
import com.bupt.facade.VersionConcreteService;
import com.bupt.pojo.VersionDTO;
import com.bupt.pojo.VersionDTOWithVersionDictDTO;
import com.bupt.pojo.VersionDictDTO;
import com.bupt.pojo.VersionQuery;
import com.bupt.service.*;
import com.bupt.util.exception.controller.result.NoneGetException;
import com.bupt.util.exception.controller.result.NoneRemoveException;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;

@Service("versionConcreteService")
class VersionConcreteServiceImpl implements VersionConcreteService {
    @Resource
    private SysVersionDao sysVersionDao;
    @Resource
    private VersionBasicService versionBasicService;
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
    private VersionBackUpService versionBackUpService;

    @Resource
    private NetElementService netElementService;


    @Override
    @Transactional
    public VersionDTO saveVersion(VersionQuery versionQuery) {
        return versionBasicService.saveVersion(versionQuery);
    }

    @Override
    @Transactional
    public void listRemoveVersion(List<Long> versionIdList) {
        versionIdList.forEach(versionId -> {
            batchRemove(versionId);
            sysVersionDao.deleteByPrimaryKey(versionId);
            versionBackUpService.removeBackUp(versionId);
        });
    }

    @Override
    @Transactional
    public List<VersionDTO> listVersion() {
        return versionBasicService.listVersion();
    }


    @Override
    public VersionDTOWithVersionDictDTO getVersion(Long versionId) {
        //todo getVersion必须要重新更改 输入检查里涉及大量的getVersion操作
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
        return versionBasicService.updateVersion(versionId, versionQuery);
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
        //todo 多线程并发
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
        //TODO 多线程并发
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


}
