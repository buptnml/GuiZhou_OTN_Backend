package com.bupt.service;

import com.bupt.entity.ResLink;
import com.bupt.pojo.LinkCreateInfo;
import com.bupt.pojo.LinkDTO;

import java.util.List;

public interface LinkService {
    /**
     * 创建一个新的链接
     *
     * @param versionId
     * @param linkCreateInfo
     * @return
     */
    LinkDTO saveResLink(Long versionId, LinkCreateInfo linkCreateInfo);


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
    LinkDTO updateResLink(Long versionId, Long linkId, LinkCreateInfo linkCreateInfo);

    /**
     * 获取当前版本所有链路
     *
     * @param versionId
     * @return
     */
    List<LinkDTO> getResLink(Long versionId);

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

    /**
     * 指定两点的网元，获取链路
     * 如果两点之间存在多条的链路，会随机选择一条返回
     *
     * @param node1Name
     * @param node2Name
     * @return
     */
    LinkDTO getLinkByNodes(Long versionId, String node1Name, String node2Name);

    /**
     * 删除断点为指定netElementId的节点
     *
     * @param versionId
     * @param netElementId
     */
    List<ResLink> getReferLink(Long versionId, Long netElementId);


}
