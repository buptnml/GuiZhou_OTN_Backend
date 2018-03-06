package com.otn.facade.impl;

import com.otn.dao.*;
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
    private WebServiceFactory webServiceFactory;
    @Resource
    private NetElementService netElementService;
    @Resource
    private ResDiskDao resDiskDao;
    @Resource
    private ResLinkDao resLinkDao;
    @Resource
    private ResBussinessDao resBussinessDao;
    @Resource
    private ResOsnrAmplifierDao resOsnrAmplifierDao;
    @Resource
    private ResNetElementDao resNetElementDao;
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
        versionIdList.forEach(versionId -> {
            sysVersionDao.deleteByPrimaryKey(versionId);
        });
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
        if (!toVersionId.equals(webServiceConfigInfo.getBASIC_VERSION_ID())) {
            batchRemove(toVersionId);
            batchCreate(fromVersionId, toVersionId);
        } else {
            try {
                basicVersionDataSynchronize();
            } catch (InterruptedException e) {
                e.printStackTrace();
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


    private synchronized void basicVersionDataSynchronize() throws InterruptedException {
        batchRemove(webServiceConfigInfo.getBASIC_VERSION_ID());
        Thread netEleDiskThread = new Thread(() -> {
            webServiceFactory.listRemoteNetElementRaw().parallelStream().forEach(resNetElement -> resNetElementDao
                    .insertSelective(resNetElement));
            webServiceFactory.listRemoteDiskRaw().parallelStream().forEach(resDisk -> resDiskDao
                    .insertSelective(resDisk));
        });
        netEleDiskThread.start();
        Thread busThread = new Thread(() -> resBussinessDao.batchInsert(webServiceFactory.listRemoteBusDataRaw()));
        busThread.start();
        Thread linkAmpThread = new Thread(() -> {
            webServiceFactory.listRemoteLinkRaw().parallelStream().forEach(resLink -> resLinkDao.insertSelective(resLink));
            webServiceFactory.listRemoteAmpRaw().parallelStream().forEach(resOsnrAmplifier -> resOsnrAmplifierDao.insertSelective(resOsnrAmplifier));
        });
        linkAmpThread.start();
        netEleDiskThread.join();
        busThread.join();
        linkAmpThread.join();
    }

    /**
     * 从指定版本中拷贝数据
     */
    private void batchCreate(Long fromVersionId, Long toVersionId) {
        SysVersion Version = sysVersionDao.selectByPrimaryKey(toVersionId);
        if (null == Version) {
            throw new NoneGetException("同步数据失败！!");
        }
        Thread netElementThread = new Thread(() -> netElementService.batchCreate(fromVersionId, toVersionId));
        VersionDictDTO dictSetting = versionDictService.getVersionDictByName(Version.getVersionDictName());
        //创建的时候最先创建网元数据！！！重要！
        if (dictSetting.getHasNetElement()) {
            netElementThread.start();
        }
        try {
            netElementThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        subCreate(fromVersionId, toVersionId, dictSetting);
    }

    private void subCreate(Long fromVersionId, Long toVersionId, VersionDictDTO dictSetting) {
        ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() / 2);
        if (dictSetting.getHasBussiness()) {
            executor.execute(new Thread(() -> bussinessService.batchCreate(fromVersionId, toVersionId)));
        }
        if (dictSetting.getHasDisk()) {
            executor.execute(new Thread(() -> diskService.batchCreate(fromVersionId, toVersionId)));
        }
        if (dictSetting.getHasLink()) {
            executor.execute(new Thread(() -> linkService.batchCreate(fromVersionId, toVersionId)));
        }
        if (dictSetting.getHasAmplifier()) {
            executor.execute(new Thread(() -> amplifierService.batchCreate(fromVersionId, toVersionId)));
        }
        if (dictSetting.getHasLinkType()) {
            executor.execute(new Thread(() -> linkTypeService.batchCreate(fromVersionId, toVersionId)));
        }
        executor.shutdown();
        while (!executor.isTerminated()) {
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
        //考虑到机盘和网元的外键冲突，先在主线程中删除机盘
        if (dictSetting.getHasDisk()) {
            diskService.batchRemove(versionId);
        }
        subRemove(versionId, dictSetting);
    }

    private void subRemove(Long versionId, VersionDictDTO dictSetting) {
        ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() / 2);
        if (dictSetting.getHasBussiness()) {
            executor.execute(new Thread(() -> bussinessService.batchRemove(versionId)));
        }
        if (dictSetting.getHasLink()) {
            executor.execute(new Thread(() -> linkService.batchRemove(versionId)));
        }
        if (dictSetting.getHasNetElement()) {
            executor.execute(new Thread(() -> netElementService.batchRemove(versionId)));
        }
        if (dictSetting.getHasAmplifier()) {
            executor.execute(new Thread(() -> amplifierService.batchRemove(versionId)));
        }
        if (dictSetting.getHasLinkType() && !versionId.equals(webServiceConfigInfo.getBASIC_VERSION_ID())) {
            executor.execute(new Thread(() -> linkTypeService.batchRemove(versionId)));
        }
        executor.shutdown();
        while (!executor.isTerminated()) {
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
