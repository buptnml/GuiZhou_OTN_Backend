package com.bupt.facade;


import com.bupt.pojo.VersionQuery;
import com.bupt.pojo.VersionDTO;

import java.util.List;

/**
 * 版本管理service层
 */
public interface VersionService {
    /**
     * 创建新版本
     * @param versionQuery
     * @return
     */
    VersionDTO saveVersion(VersionQuery versionQuery);

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
     * @param versionQuery
     * @return
     */
    VersionDTO updateVersion(Long versionId,VersionQuery versionQuery);

//    /**
//     * 同步指定版本名的资源
//     * @param fromVersionName
//     */
//    void dataSynchronize(Long versionId,String fromVersionName);

}
