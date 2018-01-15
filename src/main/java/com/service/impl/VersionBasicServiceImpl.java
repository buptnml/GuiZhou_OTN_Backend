package com.service.impl;

import com.dao.SysVersionDao;
import com.entity.SysVersion;
import com.pojo.VersionDTO;
import com.pojo.VersionQuery;
import com.service.VersionBasicService;
import com.util.exception.controller.result.NoneGetException;
import com.util.exception.controller.result.NoneSaveException;
import com.util.exception.controller.result.NoneUpdateException;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service("versionBasicService")
public class VersionBasicServiceImpl implements VersionBasicService {
    @Resource
    private SysVersionDao sysVersionDao;

    @Override
    public VersionDTO saveVersion(VersionQuery versionQuery) {
        if (sysVersionDao.insertSelective(convertToSysVersion(versionQuery)) > 0) {
            return DOtoDTO(sysVersionDao.selectByExample(getExample(versionQuery.getVersionName())).get(0));
        }
        throw new NoneSaveException();
    }


    @Override
    public List<VersionDTO> listVersion() {
        List<VersionDTO> resultList = sysVersionDao.selectAll().stream().sorted(Comparator.comparing
                (SysVersion::getGmtModified).reversed()).map(this::DOtoDTO).collect(Collectors.toList());
        if (resultList.size() == 0) {
            throw new NoneGetException("没有查询到版本相关记录！");
        }
        return resultList;
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
    public VersionDTO getVersion(Long versionId) {
        if (sysVersionDao.selectByPrimaryKey(versionId) == null) {
            throw new NoneGetException();
        }
        return DOtoDTO(sysVersionDao.selectByPrimaryKey(versionId));
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
}
