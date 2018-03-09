package com.otn.facade.impl;

import com.otn.dao.SysVersionDao;
import com.otn.entity.SysVersion;
import com.otn.facade.BussinessService;
import com.otn.facade.VersionConcreteService;
import com.otn.pojo.VersionDTO;
import com.otn.pojo.VersionDTOWithVersionDictDTO;
import com.otn.pojo.VersionDictDTO;
import com.otn.pojo.VersionQuery;
import com.otn.pojo.SyncResultDTO;
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
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;
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
    public SyncResultDTO dataSynchronize(Long fromVersionId, Long toVersionId) {
        SysVersion fromVersion = sysVersionDao.selectByPrimaryKey(fromVersionId);
        SysVersion toVersion = sysVersionDao.selectByPrimaryKey(toVersionId);
        SyncResultDTO res;
        if (null == fromVersion || null == toVersion) {
            throw new NoneGetException("同步数据失败！!");
        }
        synchronized (synchronizedLock) {
            if (!toVersionId.equals(webServiceConfigInfo.getBASIC_VERSION_ID())) {
                res = notBasicVersionDataSynchronize(fromVersionId, toVersionId);
            } else {
                res = basicVersionDataSynchronize();
            }
        }
        updateVersion(toVersionId, versionQueryCreator(toVersion));
        return res;
    }

    private VersionQuery versionQueryCreator(Object obj) {
        if (null == obj) {
            return null;
        }
        VersionQuery result = new VersionQuery();
        BeanUtils.copyProperties(obj, result);
        return result;
    }


    private SyncResultDTO notBasicVersionDataSynchronize(Long fromVersionId, Long toVersionId) {
        HashMap<String, Integer> oldInfo = batchRemove(toVersionId);
        HashMap<String, Future> newInfo = new HashMap<>();

        SysVersion Version = sysVersionDao.selectByPrimaryKey(toVersionId);
        if (null == Version) {
            throw new NoneGetException("同步数据失败！!");
        }
        VersionDictDTO dictSetting = versionDictService.getVersionDictByName(Version.getVersionDictName());
        //创建的时候最先创建网元数据！！！重要！
        if (dictSetting.getHasNetElement()) {
            Future<Integer> res = versionCreateExecutor.submit(()-> netElementService.batchCreate(fromVersionId, toVersionId));
            while(!res.isDone());
            //int num = netElementService.batchCreate(fromVersionId, toVersionId);
            newInfo.put("NetElement", res);
        }
        if (dictSetting.getHasBussiness()) {
            Future<Integer> res = versionCreateExecutor.submit(() -> bussinessService.batchCreate(fromVersionId, toVersionId));
            newInfo.put("Business", res);
        }
        if (dictSetting.getHasDisk()) {
            Future<Integer> res = versionCreateExecutor.submit(() -> diskService.batchCreate(fromVersionId, toVersionId));
            newInfo.put("Disk", res);
        }
        if (dictSetting.getHasLink()) {
            Future<Integer> res = versionCreateExecutor.submit(() -> linkService.batchCreate(fromVersionId, toVersionId));
            newInfo.put("Link", res);
        }
        if (dictSetting.getHasAmplifier()) {
            versionCreateExecutor.submit(() -> amplifierService.batchCreate(fromVersionId, toVersionId));
        }
        if (dictSetting.getHasLinkType()) {
            versionCreateExecutor.submit(() -> linkTypeService.batchCreate(fromVersionId, toVersionId));
        }

        return mapTransferFactory(oldInfo, newInfo);
    }

    private SyncResultDTO basicVersionDataSynchronize() {
        HashMap<String, Integer> oldInfo = batchRemove(webServiceConfigInfo.getBASIC_VERSION_ID());
        HashMap<String, Future> newInfo = new HashMap<>();

        Future<Integer> res1 = versionCreateExecutor.submit(() -> netElementService.batchInsert(webServiceFactory.listRemoteNetElementRaw()));
        while(!res1.isDone());
        Future<Integer> res2 = versionCreateExecutor.submit(() -> diskService.batchInsert(webServiceFactory.listRemoteDiskRaw()));
        Future<Integer> res3 = versionCreateExecutor.submit(() -> bussinessService.batchInsert(webServiceFactory.listRemoteBusDataRaw
                ()));
        Future<Integer> res4 = versionCreateExecutor.submit(() -> linkService.batchInsert(webServiceFactory.listRemoteLinkRaw()));
        Future<Integer> res5 = versionCreateExecutor.submit(() -> amplifierService.batchInsert(webServiceFactory.listRemoteAmpRaw()));
        while (!(res2.isDone() && res3.isDone() && res4.isDone() && res5.isDone()));
        newInfo.put("NetElement",res1);
        newInfo.put("Disk",res2);
        newInfo.put("Business",res3);
        newInfo.put("Link",res4);

        return mapTransferFactory(oldInfo, newInfo);
    }

    private SyncResultDTO mapTransferFactory(HashMap<String, Integer> oldInfo, HashMap<String, Future> newInfo){
        SyncResultDTO res = new SyncResultDTO();
        newInfo.forEach((key, value)->{
            System.out.println(key);
            try{
                int newValue = (int)value.get();
                int oldValue = oldInfo.get(key).intValue();
                if(key == "Business")
                    res.setBusinessChange(newValue - oldValue);
                else if(key == "Disk")
                    res.setDiskChange(newValue - oldValue);
                else if(key == "NetElement")
                    res.setNetElementChange(newValue - oldValue);
                else if(key == "Link")
                    res.setLinkChange(newValue - oldValue);
                //System.out.println(String.format("oldValue:%s, newValue:%s", oldValue, newValue));
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        });
        return res;
    }
    /***
     * 根据版本设置批量删除该版本所用资源
     */
    private HashMap<String,Integer> batchRemove(Long versionId) {
        SysVersion Version = sysVersionDao.selectByPrimaryKey(versionId);
        if (null == Version) {
            throw new NoneRemoveException("删除版本数据失败！！");
        }
        VersionDictDTO dictSetting = versionDictService.getVersionDictByName(Version.getVersionDictName());
        return subRemove(versionId, dictSetting);
    }

    private HashMap<String,Integer> subRemove(Long versionId, VersionDictDTO dictSetting) {
        //考虑到机盘和网元的外键冲突，先在主线程中删除机盘
        HashMap<String,Integer> res = new HashMap<>();
        int num;
        if (dictSetting.getHasDisk()) {
            num = diskService.batchRemove(versionId);
            res.put("Disk", num);
        }
        if (dictSetting.getHasBussiness()) {
            num = bussinessService.batchRemove(versionId);
            res.put("Business", num);
        }
        if (dictSetting.getHasLink()) {
            num = linkService.batchRemove(versionId);
            res.put("Link", num);
        }
        if (dictSetting.getHasNetElement()) {
            num = netElementService.batchRemove(versionId);
            res.put("NetElement", num);
        }
        if (dictSetting.getHasAmplifier()) {
            amplifierService.batchRemove(versionId);
        }
        if (dictSetting.getHasLinkType() && !versionId.equals(webServiceConfigInfo.getBASIC_VERSION_ID())) {
            linkTypeService.batchRemove(versionId);
        }
        return res;
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

