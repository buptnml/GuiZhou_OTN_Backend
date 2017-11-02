package com.bupt.facade;


import com.bupt.entity.ResBussiness;
import com.bupt.pojo.BussinessCreateInfo;
import com.bupt.pojo.BussinessDTO;

import java.util.List;

/**
 * 光通道(业务）的Service层
 */
public interface
BussinessService {
    /**
     * 获取所有业务
     */
    List<BussinessDTO> listBussiness(Long versionId);

    /**
     * 获取单个业务信息
     *
     * @param versionId
     * @param bussinessId
     * @return
     */
    ResBussiness getBussiness(Long versionId, Long bussinessId);

    /**
     * 创建新的业务
     */
    BussinessDTO saveBussiness(Long versionId, BussinessCreateInfo bussinessCreateInfo);


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


    /**
     * 更新路由中包含oldString的bussiness路由为newString，重新计算OSNR的输入输出功率
     *
     * @param versionId
     * @param oldString
     * @param newString
     */
    void updateReferBussiness(Long versionId, String oldString, String newString);
}
