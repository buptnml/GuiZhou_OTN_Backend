package com.bupt.service;

import com.bupt.entity.SysVersionDict;
import com.bupt.pojo.VersionDictInfo;

import java.util.List;

public interface VersionDictService {

    /**
     * 创建新版本字典
     * @param versionDictInfo
     * @return
     */
    SysVersionDict saveVersionDict(VersionDictInfo versionDictInfo);


    /**
     * 修改版本字典信息
     * @param versionDictInfo
     * @return
     */
    SysVersionDict updateVersionDict(long versionDictId,VersionDictInfo versionDictInfo);

    /**
     * 批量删除版本字典信息
     * @param versionDictIdList
     */
    void listRemoveVersionDict(List<Long> versionDictIdList);

    /**
     * 获取版本字典信息
     */
    SysVersionDict getVersionDictByName(String versionDictName);

    /**
     * 获取全部版本字典信息
     * @return
     */
    List<SysVersionDict> listVersionDict();



}
