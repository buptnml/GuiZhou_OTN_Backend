package com.bupt.service;

import com.bupt.pojo.DiskCreateInfo;
import com.bupt.pojo.DiskDTO;

import java.util.List;

/**
 * 机盘的Service层
 */
public interface DiskService {

    /**
     * 获取指定版本中指定网元的全部机盘信息
     *
     * @param versionId
     * @return
     */
    List<DiskDTO> listDiskByNetElement(Long versionId, Long netElementId);

    /**
     * 获取指定类型的机盘
     */
    List<DiskDTO> listDiskByType(Long versionId, String diskType);

    /**
     * 创建机盘信息
     *
     * @param versionId
     * @param diskCreateInfo
     * @return
     */
    DiskDTO saveDisk(Long versionId, Long netElementId, DiskCreateInfo diskCreateInfo);

    /**
     * 更新机盘信息
     *
     * @param versionId
     * @param diskCreateInfo
     * @return
     */
    DiskDTO updateDisk(Long versionId, Long netElementId, Long diskId, DiskCreateInfo diskCreateInfo);

    /**
     * 批量删除指定版本指定网元中的机盘信息
     *
     * @param versionId
     * @param netElementId
     * @param diskIdList
     */
    void listRemove(Long versionId, Long netElementId, List<Long> diskIdList);

    /**
     * 删除指定版本中的所有机盘信息
     *
     * @param versionId
     */
    void batchRemove(Long versionId);

    /**
     * 复制一个旧有版本Id中的内容，并将版本Id字段重命名为新Id
     */
    void batchCreate(Long baseVersionId, Long newVersionId);
}
