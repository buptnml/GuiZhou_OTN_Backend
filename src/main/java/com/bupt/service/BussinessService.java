package com.bupt.service;


import com.bupt.pojo.BussinessCreateInfo;
import com.bupt.pojo.BussinessDTO;

import java.util.List;

/**
 * 光通道(业务）的Service层
 */
public interface BussinessService {
    /**
     * 获取所有业务
     */
    List<BussinessDTO> listBussiness(Long versionId);

    /**
     * 创建新的业务
     */
    BussinessDTO saveBussiness(Long versionId, BussinessCreateInfo bussinessCreateInfo);

    /**
     * 修改已有业务
     */
    BussinessDTO updateBussiness(Long versionId, Long bussinessId, BussinessCreateInfo bussinessCreateInfo);

    /**
     * 批量删除现有业务
     */
    void listRemove(Long versionId, List<Long> bussinessIdList);

    /**
     * 删除指定版本ID下的所有条目
     */
    void batchRemove(Long versionId);

    /**
     * 复制一个旧有版本Id中的内容，并将版本Id字段重命名为新Id
     */
    void batchCreate(Long baseVersionId, Long newVersionId);


}
