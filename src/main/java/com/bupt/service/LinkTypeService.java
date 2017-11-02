package com.bupt.service;

import com.bupt.pojo.LinkTypeCreateInfo;
import com.bupt.pojo.LinkTypeDTO;

import java.util.List;

/**
 * Created by caoxiaohong on 17/9/20.
 */
public interface LinkTypeService {

    /**
     * 修改链路类型, 根据 链路类型ID, 请求路径:/linkTypes/:versionId/:linkTypeId
     *
     * @param linkTypeId
     * @param linkTypeCreateInfo
     * @return
     */
    LinkTypeDTO updateByLinkTypeId(Long versionId, Long linkTypeId, LinkTypeCreateInfo linkTypeCreateInfo);


    /**
     * 批量删除链路类型, 根据 链路类型ID, 请求路径:/linkTypes/:versionId
     *
     * @param linkTypeIds
     */
    boolean deleteByLinkTypeId(Long versionId, List<Long> linkTypeIds);

    /**
     * 添加链路类型 ,请求路径:/linkTypes/:versionId
     *
     * @param versionId
     * @param linkTypeCreateInfo
     * @return
     */
    LinkTypeDTO createLinkType(Long versionId, LinkTypeCreateInfo linkTypeCreateInfo);


    /**
     * 获取所有链路类型信息 ,请求路径:/linkTypes/:versionId
     *
     * @param versionId
     * @return
     */
    List<LinkTypeDTO> listLinkTypes(Long versionId);

    /**
     * 获取指定链路类型的具体信息
     *
     * @param versionId
     * @param linkType
     * @return
     */
    LinkTypeDTO getLinkType(Long versionId, String linkType);


    /**
     * 根据ID获取链路类型
     */
    LinkTypeDTO getLinkTypeById(Long versionId, Long linkTypeId);


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
