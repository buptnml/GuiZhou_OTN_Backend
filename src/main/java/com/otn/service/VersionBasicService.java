package com.otn.service;

import com.otn.pojo.VersionDTO;
import com.otn.pojo.VersionQuery;

import java.util.List;


/**
 * 版本Service的基础部分，
 * 主要做一些不太复杂的工作
 */
public interface VersionBasicService {
    /**
     * 创建新版本
     *
     * @param versionQuery
     * @return
     */
    VersionDTO saveVersion(VersionQuery versionQuery);


    /**
     * 获取所有版本
     *
     * @return
     */
    List<VersionDTO> listVersion();

    /**
     * 修改版本设置
     *
     * @param versionQuery
     * @return
     */
    VersionDTO updateVersion(Long versionId, VersionQuery versionQuery);

    /**
     * 返回简单版本信息
     *
     * @param versionId
     * @return
     */
    VersionDTO getVersion(Long versionId);
}
