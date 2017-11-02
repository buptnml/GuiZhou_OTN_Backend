package com.bupt.service.impl;

import com.bupt.dao.SysVersionDao;
import com.bupt.dao.SysVersionDictDao;
import com.bupt.entity.SysVersion;
import com.bupt.entity.SysVersionDict;
import com.bupt.pojo.VersionDictCreateInfo;
import com.bupt.pojo.VersionDictDTO;
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
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service("versionDictService")
class VersionDictServiceImpl implements VersionDictService {
    @Resource
    private SysVersionDictDao sysVersionDictDao;
    @Resource
    private SysVersionDao sysVersionDao;

    @Override
    public VersionDictDTO saveVersionDict(VersionDictCreateInfo versionDictCreateInfo) {
        if (sysVersionDictDao.insertSelective(convertToDO(versionDictCreateInfo)) > 0) {
            return this.getVersionDictByName(versionDictCreateInfo.getVersionDictName());
        }
        throw new NoneSaveException();
    }

    @Override
    public VersionDictDTO updateVersionDict(long versionDictId, VersionDictCreateInfo versionDictCreateInfo) {
        updateVersionInfo(versionDictId, versionDictCreateInfo.getVersionDictName());
        SysVersionDict updateInfo = convertToDO(versionDictCreateInfo);
        updateInfo.setVersionDictId(versionDictId);
        if (sysVersionDictDao.updateByPrimaryKeySelective(updateInfo) > 0) {
            return convertToDTO(sysVersionDictDao.selectByPrimaryKey(versionDictId));
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
        for (Long key : versionDictIdList) {
            updateVersionInfo(key, "基础字典");
            if (sysVersionDictDao.deleteByPrimaryKey(key) == 0) {
                throw new NoneRemoveException();
            }
        }
    }

    @Override
    public VersionDictDTO getVersionDictByName(String versionDictName) {
        List<SysVersionDict> result = sysVersionDictDao.selectByExample(getExample(versionDictName));
        if (result.size() == 0) {
            throw new NoneGetException("versionDictName");
        }
        return convertToDTO(result.get(0));
    }

    private Example getExample(String versionDictName) {
        Example example = new Example(SysVersionDict.class);

        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("versionDictName", versionDictName);
        return example;
    }

    @Override
    public List<VersionDictDTO> listVersionDict() {
        List<VersionDictDTO> resultList = sysVersionDictDao.selectAll().stream().sorted(Comparator.comparing
                (SysVersionDict::getGmtModified).reversed()).map(this::convertToDTO).collect(Collectors.toList());
        if (resultList.size() == 0) {
            throw new NoneGetException("没有查询到版本字典相关记录！");
        }
        return resultList;
    }


    private VersionDictDTO convertToDTO(Object inputObject) {
        if (null == inputObject) {
            return null;
        }
        VersionDictDTO result = new VersionDictDTO();
        BeanUtils.copyProperties(inputObject, result);
        return result;
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
