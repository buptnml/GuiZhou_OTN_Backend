package com.otn.service;

import com.otn.entity.ResDisk;
import com.otn.pojo.DiskCreateInfo;
import com.otn.pojo.DiskDTO;

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
     * 返回值为删除的数量
     * @param versionId
     */
    int batchRemove(Long versionId);

    /**
     * 复制一个旧有版本Id中的内容，并将版本Id字段重命名为新Id
     * 返回值为创建的数量
     */
    int batchCreate(Long baseVersionId, Long newVersionId);

    /**
     * 批量插入
     * 根据数量确定是否使用多线程
     * 返回值为插入的数量
     *
     * @param batchList
     * @return
     */
    int batchInsert(List<ResDisk> batchList);
}
