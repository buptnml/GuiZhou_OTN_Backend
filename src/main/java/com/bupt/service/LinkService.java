package com.bupt.service;

import com.bupt.pojo.LinkCreateInfo;
import com.bupt.pojo.ResLinkDTO;

import java.util.List;

public interface LinkService {
    /**
     * 创建一个新的链接
     *
     * @param versionId
     * @param linkCreateInfo
     * @return
     */
    ResLinkDTO saveResLink(Long versionId, LinkCreateInfo linkCreateInfo);



    /**
     * 批量删除
     *
     * @param linkIdList
     */
    void listRemoveResLink(Long versionId, List<Long> linkIdList);

    /**
     * 更新指定数据(一条）
     *
     * @param linkCreateInfo
     * @return
     */
    ResLinkDTO updateResLink(Long versionId, Long linkId, LinkCreateInfo linkCreateInfo);

    /**
     * 获取当前版本所有链路
     *
     * @param versionId
     * @return
     */
    List<ResLinkDTO> getResLink(Long versionId);

    /**
     * 删除指定版本全本链路信息
     *
     * @param versionId
     */
    void batchRemove(Long versionId);

    /**
     * 复制一个旧有版本Id中的内容，并将版本Id字段重命名为新Id
     */
    void batchCreate(Long baseVersionId, Long newVersionId);

}
