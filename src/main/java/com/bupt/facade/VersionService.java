package com.bupt.facade;


import com.bupt.pojo.VersionCreateInfo;
import com.bupt.pojo.VersionDTO;

import java.io.IOException;
import java.util.List;

/**
 * 版本管理service层
 */
public interface VersionService {
    /**
     * 创建新版本
     * @param versionCreateInfo
     * @return
     */
    VersionDTO saveVersion(VersionCreateInfo versionCreateInfo) throws IOException, ClassNotFoundException;

    /**
     * 批量删除版本
     * @param versionIdList
     */
    void listRemoveVersion(List<Long> versionIdList) throws IOException, ClassNotFoundException;

    /**
     * 获取所有版本
     * @return
     */
    List<VersionDTO> listVersion() throws IOException, ClassNotFoundException;

    /**
     * 根据版本Id打开新版本
     * @param versionId
     * @return
     */
    VersionDTO getVersion(Long versionId) throws IOException, ClassNotFoundException;

    /**
     * 修改版本设置
     * @param versionDTO
     * @return
     */
    VersionDTO updateVersion(VersionDTO versionDTO) throws IOException, ClassNotFoundException;
}
