package com.bupt.service.impl;

import com.bupt.dao.SysVersionDao;
import com.bupt.dao.SysVersionDictDao;
import com.bupt.entity.SysVersion;
import com.bupt.entity.SysVersionDict;
import com.bupt.pojo.VersionDictInfo;
import com.bupt.service.VersionDictService;
import com.bupt.util.exception.controller.result.NoneGetException;
import com.bupt.util.exception.controller.result.NoneRemoveException;
import com.bupt.util.exception.controller.result.NoneSaveException;
import com.bupt.util.exception.controller.result.NoneUpdateException;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.Iterator;
import java.util.List;

@Service("versionDictService")
public class VersionDictServiceImpl implements VersionDictService {
    @Resource
    private SysVersionDictDao sysVersionDictDao;
    @Resource
    private SysVersionDao sysVersionDao;

    @Override
    public SysVersionDict saveVersionDict(VersionDictInfo versionDictInfo) {
        if (sysVersionDictDao.insertSelective(convertToDO(versionDictInfo)) > 0) {
            return this.getVersionDictByName(versionDictInfo.getVersionDictName());
        }
        throw new NoneSaveException();
    }

    @Override
    public SysVersionDict updateVersionDict(long versionDictId, VersionDictInfo versionDictInfo) {
        updateVersionInfo(versionDictId, versionDictInfo.getVersionDictName());
        SysVersionDict updateInfo = convertToDO(versionDictInfo);
        updateInfo.setVersionDictId(versionDictId);
        if (sysVersionDictDao.updateByPrimaryKeySelective(updateInfo) > 0) {
            return sysVersionDictDao.selectByPrimaryKey(versionDictId);
        }
        throw new NoneUpdateException();
    }

    /**
     * 将旧版本字典名更新为新版本字典名
     */
    private void updateVersionInfo(long versionDictId, String newName) {
        SysVersionDict oldInfo = sysVersionDictDao.selectByPrimaryKey(versionDictId);
        Example example = new Example(SysVersion.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("versionDictName", oldInfo.getVersionDictName());
        SysVersion updateInfo = new SysVersion();
        updateInfo.setVersionDictName(newName);
        sysVersionDao.updateByExampleSelective(updateInfo, example);
    }

    @Override
    @Transactional
    public void listRemoveVersionDict(List<Long> versionDictIdList) {
        Iterator<Long> idListIterator = versionDictIdList.iterator();
        while (idListIterator.hasNext()) {
            long key = idListIterator.next();
            updateVersionInfo(key, "基础字典");
            if (sysVersionDictDao.deleteByPrimaryKey(key) == 0) {
                throw new NoneRemoveException();
            }
        }
    }

    @Override
    public SysVersionDict getVersionDictByName(String versionDictName) {
        List<SysVersionDict> result = sysVersionDictDao.selectByExample(getExample(versionDictName));
        if (result.size() == 0) {
            throw new NoneGetException();
        }
        return result.get(0);
    }

    private Example getExample(String versionDictName) {
        Example example = new Example(SysVersionDict.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("versionDictName", versionDictName);
        return example;
    }

    @Override
    public List<SysVersionDict> listVersionDict() {
        List<SysVersionDict> resultList = sysVersionDictDao.selectAll();
        if (resultList.size() == 0 || null == resultList) {
            throw new NoneGetException();
        }
        return resultList;
    }


    private SysVersionDict convertToDO(Object inputObject) {
        if (null == inputObject) {
            return null;
        }
        SysVersionDict result = new SysVersionDict();
        BeanUtils.copyProperties(inputObject, result);
        return result;
    }
}
