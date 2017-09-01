package com.bupt.facade.impl;

import com.bupt.dao.SysVersionDao;
import com.bupt.entity.SysVersion;
import com.bupt.pojo.VersionCreateInfo;
import com.bupt.pojo.VersionDTO;
import com.bupt.pojo.VersionSetting;
import com.bupt.facade.VersionService;
import com.bupt.util.exception.controller.result.NoneGetException;
import com.bupt.util.exception.controller.result.NoneRemoveException;
import com.bupt.util.exception.controller.result.NoneUpdateException;
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

    @Override
    @Transactional
    public VersionDTO saveVersion(VersionCreateInfo versionCreateInfo) throws IOException, ClassNotFoundException {
        SysVersion tempVersion = new SysVersion();
        tempVersion.setVersionSetting(toByteArray(versionCreateInfo.getVersionSetting()));
        tempVersion.setVersionName(versionCreateInfo.getVersionName());
        sysVersionDao.insertSelective(tempVersion);
        SysVersion newVersion = getVersionByName(versionCreateInfo.getVersionName());
        batchCreate(versionCreateInfo.getVersionSetting(),
                getVersionByName(versionCreateInfo.getBaseVersionName()).getVersionId(), newVersion.getVersionId());
        return DOtoDTO(newVersion);
    }

    @Override
    @Transactional
    public void listRemoveVersion(List<Long> versionIdList) throws IOException, ClassNotFoundException {
        Iterator<Long> versionIdListIterator = versionIdList.iterator();
        while (versionIdListIterator.hasNext()) {
            SysVersion temp = sysVersionDao.selectByPrimaryKey(versionIdListIterator.next());
            if (null == temp) {
                throw new NoneRemoveException();
            }
            batchRemove(toObject(temp.getVersionSetting()));
            sysVersionDao.deleteByPrimaryKey(temp.getVersionId());
        }
    }

    @Override
    public List<VersionDTO> listVersion() throws IOException, ClassNotFoundException {
        Iterator<SysVersion> sysVersionIterator = sysVersionDao.selectAll().iterator();
        List<VersionDTO> resultList = new ArrayList<>();
        while (sysVersionIterator.hasNext()) {
            resultList.add(this.DOtoDTO(sysVersionIterator.next()));
        }
        if(resultList.size()==0||null==resultList){
            throw new NoneGetException();
        }
        return resultList;
    }

    @Override
    public VersionDTO getVersion(Long versionId) throws IOException, ClassNotFoundException {
        SysVersion sysVersionDO = sysVersionDao.selectByPrimaryKey(versionId);
        if (null == sysVersionDO) {
            throw new NoneGetException();
        }
        return DOtoDTO(sysVersionDO);
    }


    @Override
    public VersionDTO updateVersion(VersionDTO versionDTO) throws IOException, ClassNotFoundException {
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
    public void batchCreate(VersionSetting vs, long baseOnId, long newId) {
        if (vs.hasAmplifier()) {
            //TODO
        }
        if (vs.hasDisk()) {
            //TODO
        }
        if (vs.hasBussiness()) {
            //TODO
        }
        if (vs.hasLink()) {
            //TODO
        }
        if (vs.hasLinkType()) {
            //TODO
        }
        if (vs.hasNetElement()) {
            //TODO
        }
    }

    /***
     * 根据版本设置批量删除该版本所用资源
     */
    @Transactional
    public void batchRemove(VersionSetting vs) {
        if (vs.hasAmplifier()) {
            //TODO
        }
        if (vs.hasDisk()) {
            //TODO
        }
        if (vs.hasBussiness()) {
            //TODO
        }
        if (vs.hasLink()) {
            //TODO
        }
        if (vs.hasLinkType()) {
            //TODO
        }
        if (vs.hasNetElement()) {
            //TODO
        }
    }

    private SysVersion getVersionByName(String versionName) {
        Example example = new Example(SysVersion.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("versionName", versionName);
        List<SysVersion> sysVersionList = sysVersionDao.selectByExample(example);
        if (sysVersionList.size() == 0 || sysVersionList.size() > 1) {
            throw new NoneGetException();
        }
        return sysVersionList.get(0);
    }


    private VersionDTO DOtoDTO(SysVersion sysVersionDO) throws IOException, ClassNotFoundException {
        if (null == sysVersionDO) {
            return null;
        }
        VersionDTO versionDTO = new VersionDTO();
        versionDTO.setVersionId(sysVersionDO.getVersionId());
        versionDTO.setVersionName(sysVersionDO.getVersionName());
        versionDTO.setVersionSetting(this.toObject(sysVersionDO.getVersionSetting()));
        return versionDTO;
    }

    private SysVersion DTOtoDo(VersionDTO versionDTO) throws IOException {
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
    private byte[] toByteArray(VersionSetting versionSetting) throws IOException {
        byte[] bytes = null;
        ByteArrayOutputStream bos = null;
        ObjectOutputStream oos = null;
        try {
            bos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(bos);
            oos.writeObject(versionSetting);
            bytes = bos.toByteArray();
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            oos.flush();
            oos.close();
            bos.close();
        }
        return bytes;
    }

    /**
     * versionSetting 反序列化
     */
    private VersionSetting toObject(byte[] bytes) throws IOException, ClassNotFoundException {
        VersionSetting versionSetting = null;
        ObjectInputStream ois = null;
        ByteArrayInputStream bis = null;
        try {
            bis = new ByteArrayInputStream(bytes);
            ois = new ObjectInputStream(bis);
            versionSetting = (VersionSetting) ois.readObject();
        } finally {
            bis.close();
            ois.close();
        }
        return versionSetting;
    }


}
