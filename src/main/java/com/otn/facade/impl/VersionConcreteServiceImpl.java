package com.otn.facade.impl;

import com.otn.dao.SysVersionDao;
import com.otn.entity.SysVersion;
import com.otn.facade.BussinessService;
import com.otn.facade.VersionConcreteService;
import com.otn.pojo.VersionDTO;
import com.otn.pojo.VersionDTOWithVersionDictDTO;
import com.otn.pojo.VersionDictDTO;
import com.otn.pojo.VersionQuery;
import com.otn.service.*;
import com.otn.util.exception.controller.result.NoneGetException;
import com.otn.util.exception.controller.result.NoneRemoveException;
import com.otn.webservice.WebServiceFactory;
import com.otn.webservice.com.pojo.WebServiceConfigInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Service("versionConcreteService")
class VersionConcreteServiceImpl implements VersionConcreteService {
    private static Object synchronizedLock = new Object();
    private static ExecutorService versionCreateExecutor = Executors.newWorkStealingPool(Runtime.getRuntime()
            .availableProcessors() * 3);
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
    private WebServiceFactory webServiceFactory;
    @Resource
    private NetElementService netElementService;
    @Resource
    private WebServiceConfigInfo webServiceConfigInfo;

    @Override
    @Transactional
    public VersionDTO saveVersion(VersionQuery versionQuery) {
        return versionBasicService.saveVersion(versionQuery);
    }

    @Override
    @Transactional
    public void listRemoveVersion(List<Long> versionIdList) {
        versionIdList.forEach(sysVersionDao::deleteByPrimaryKey);
    }

    @Override
    @Transactional
    public List<VersionDTO> listVersion() {
        return versionBasicService.listVersion();
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
        return versionBasicService.updateVersion(versionId, versionQuery);
    }

    @Override
    public void dataSynchronize(Long fromVersionId, Long toVersionId) {
        SysVersion fromVersion = sysVersionDao.selectByPrimaryKey(fromVersionId);
        SysVersion toVersion = sysVersionDao.selectByPrimaryKey(toVersionId);
        if (null == fromVersion || null == toVersion) {
            throw new NoneGetException("同步数据失败！!");
        }
        synchronized (synchronizedLock) {
            if (!toVersionId.equals(webServiceConfigInfo.getBASIC_VERSION_ID())) {
                notBasicVersionDataSynchronize(fromVersionId, toVersionId);
            } else {
                basicVersionDataSynchronize();
            }
        }
        updateVersion(toVersionId, versionQueryCreator(toVersion));
    }

    private VersionQuery versionQueryCreator(Object obj) {
        if (null == obj) {
            return null;
        }
        VersionQuery result = new VersionQuery();
        BeanUtils.copyProperties(obj, result);
        return result;
    }


    private void notBasicVersionDataSynchronize(Long fromVersionId, Long toVersionId) {
        batchRemove(toVersionId);
        SysVersion Version = sysVersionDao.selectByPrimaryKey(toVersionId);
        if (null == Version) {
            throw new NoneGetException("同步数据失败！!");
        }
        VersionDictDTO dictSetting = versionDictService.getVersionDictByName(Version.getVersionDictName());
        //创建的时候最先创建网元数据！！！重要！
        if (dictSetting.getHasNetElement()) {
            netElementService.batchCreate(fromVersionId, toVersionId);
        }
        if (dictSetting.getHasBussiness()) {
            versionCreateExecutor.submit(() -> bussinessService.batchCreate(fromVersionId, toVersionId));
        }
        if (dictSetting.getHasDisk()) {
            versionCreateExecutor.submit(() -> diskService.batchCreate(fromVersionId, toVersionId));
        }
        if (dictSetting.getHasLink()) {
            versionCreateExecutor.submit(() -> linkService.batchCreate(fromVersionId, toVersionId));
        }
        if (dictSetting.getHasAmplifier()) {
            versionCreateExecutor.submit(() -> amplifierService.batchCreate(fromVersionId, toVersionId));
        }
        if (dictSetting.getHasLinkType()) {
            versionCreateExecutor.submit(() -> linkTypeService.batchCreate(fromVersionId, toVersionId));
        }
    }

    private void basicVersionDataSynchronize() {
        batchRemove(webServiceConfigInfo.getBASIC_VERSION_ID());
        int res1 = netElementService.batchInsert(webServiceFactory.listRemoteNetElementRaw());
        Future<Integer> res2 = versionCreateExecutor.submit(() -> diskService.batchInsert(webServiceFactory.listRemoteDiskRaw()));
        Future<Integer> res3 = versionCreateExecutor.submit(() -> bussinessService.batchInsert(webServiceFactory.listRemoteBusDataRaw
                ()));
        Future<Integer> res4 = versionCreateExecutor.submit(() -> linkService.batchInsert(webServiceFactory.listRemoteLinkRaw()));
        Future<Integer> res5 = versionCreateExecutor.submit(() -> amplifierService.batchInsert(webServiceFactory.listRemoteAmpRaw()));
        while (!(res3.isDone() && res4.isDone() && res2.isDone() && res5.isDone())) ;
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
        subRemove(versionId, dictSetting);
    }

    private void subRemove(Long versionId, VersionDictDTO dictSetting) {
        //考虑到机盘和网元的外键冲突，先在主线程中删除机盘
        if (dictSetting.getHasDisk()) {
            diskService.batchRemove(versionId);
        }
        if (dictSetting.getHasBussiness()) {
            bussinessService.batchRemove(versionId);
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
        if (dictSetting.getHasLinkType() && !versionId.equals(webServiceConfigInfo.getBASIC_VERSION_ID())) {
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
