package com.bupt.service;

import com.bupt.pojo.AmplifierDTO;

import java.util.List;

public interface DiskTypeService {
    /**
     * 获取当前版本下指定机盘类型列表
     *
     * @param versionId
     * @return
     */
    List<String> listDiskTypes(Long versionId);


    /**
     * 获取指定机盘类型下放大器类型列表
     *
     * @return
     */
    List<AmplifierDTO> listAmpsByDiskType(Long versionId, String diskTypeName);
}
