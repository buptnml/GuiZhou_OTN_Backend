package com.bupt.service;


import com.bupt.pojo.VersionDictCreateInfo;
import com.bupt.pojo.VersionDictDTO;

import java.util.List;

public interface VersionDictService {

    /**
     * 创建新版本字典
     *
     * @param versionDictCreateInfo
     * @return VersionDictDTO
     */
    VersionDictDTO saveVersionDict(VersionDictCreateInfo versionDictCreateInfo);


    /**
     * 修改版本字典信息
     *
     * @param versionDictCreateInfo
     * @return VersionDictDTO
     */
    VersionDictDTO updateVersionDict(long versionDictId, VersionDictCreateInfo versionDictCreateInfo);

    /**
     * 批量删除版本字典信息
     *
     * @param versionDictIdList
     */
    void listRemoveVersionDict(List<Long> versionDictIdList);

    /**
     * 获取版本字典信息
     */
    VersionDictDTO getVersionDictByName(String versionDictName);

    /**
     * 获取全部版本字典信息
     *
     * @return List<VersionDictDTO>
     */
    List<VersionDictDTO> listVersionDict();


}
