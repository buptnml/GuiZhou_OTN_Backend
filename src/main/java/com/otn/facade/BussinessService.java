package com.otn.facade;


import com.otn.entity.ResBussiness;
import com.otn.pojo.BussinessCreateInfo;
import com.otn.pojo.BussinessDTO;

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

    List<BussinessDTO> listBussiness(Long versionId,String circleId);
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

    BussinessDTO updateBussiness(Long versionId, Long bussinessId, BussinessCreateInfo bussinessCreateInfo);


    /**
     * 批量删除现有业务
     */
    void listRemove(Long versionId, List<Long> bussinessIdList);

    /**
     * 删除指定版本ID下的所有条目
     * 返回值为删除的条目数量
     */
    int batchRemove(Long versionId);

    /**
     * 复制一个旧有版本Id中的内容，并将版本Id字段重命名为新Id
     */
    int batchCreate(Long baseVersionId, Long newVersionId);

    /**
     * 批量插入业务
     * 满足一定条件会使用多线程插入
     * 返回值为插入的条目数量
     *
     * @param batchList
     * @return
     */
    int batchInsert(List<ResBussiness> batchList) throws InterruptedException;

    /**
     * 更新路由中包含oldString的bussiness路由为newString，重新计算OSNR的输入输出功率
     *
     * @param versionId
     * @param oldString
     * @param newString
     */
    void updateReferBussiness(Long versionId, String oldString, String newString, boolean needRecalculate);
}
