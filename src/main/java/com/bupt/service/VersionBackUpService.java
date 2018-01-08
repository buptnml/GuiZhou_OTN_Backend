package com.bupt.service;

public interface VersionBackUpService {

    /**
     * 创建一个新的备份条目
     *
     * @param versionId
     */
    void initBackUp(Long versionId);

    /**
     * 删除一个备份条目
     *
     * @param versionId
     */
    void removeBackUp(Long versionId);

    /**
     * 保存备份
     *
     * @param versionId
     */
    void saveBackUp(Long versionId);

    /**
     * 恢复某个版本的备份
     *
     * @param versionId
     */
    void restoreBackUp(Long versionId);

}
