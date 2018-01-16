package com.otn.facade;


import com.otn.pojo.VersionDTO;
import com.otn.pojo.VersionDTOWithVersionDictDTO;
import com.otn.pojo.VersionQuery;

import java.util.List;

/**
 * 版本管理service层
 */
public interface VersionConcreteService {
    /**
     * 创建新版本
     *
     * @param versionQuery
     * @return
     */
    VersionDTO saveVersion(VersionQuery versionQuery);

    /**
     * 批量删除版本
     *
     * @param versionIdList
     */
    void listRemoveVersion(List<Long> versionIdList);

    /**
     * 获取所有版本
     *
     * @return
     */
    List<VersionDTO> listVersion();

    /**
     * 根据版本Id打开指定版本
     *
     * @param versionId
     * @return
     */
    VersionDTOWithVersionDictDTO getVersion(Long versionId);

    /**
     * 修改版本设置
     *
     * @param versionQuery
     * @return
     */
    VersionDTO updateVersion(Long versionId, VersionQuery versionQuery);


    /**
     * 同步数据
     */
    void dataSynchronize(Long fromVersionId, Long toVersionId);


}
