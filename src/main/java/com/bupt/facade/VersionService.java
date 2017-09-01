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
    VersionDTO saveVersion(VersionCreateInfo versionCreateInfo);

    /**
     * 批量删除版本
     * @param versionIdList
     */
    void listRemoveVersion(List<Long> versionIdList);

    /**
     * 获取所有版本
     * @return
     */
    List<VersionDTO> listVersion();

    /**
     * 根据版本Id打开新版本
     * @param versionId
     * @return
     */
    VersionDTO getVersion(Long versionId);

    /**
     * 修改版本设置
     * @param versionDTO
     * @return
     */
    VersionDTO updateVersion(Long versionId,VersionDTO versionDTO);
}
