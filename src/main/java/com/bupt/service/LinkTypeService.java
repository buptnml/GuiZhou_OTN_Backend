package com.bupt.service;

import com.bupt.pojo.LinkTypeDTO;

import java.util.List;

/**
 * Created by caoxiaohong on 17/9/20.
 */
public interface LinkTypeService {

    /**
     * 修改链路类型, 根据 链路类型ID, 请求路径:/linkTypes/:versionId/:linkTypeId
     * @param linkTypeId
     * @param linkTypeDTO
     * @return
     */
    LinkTypeDTO updateByLinkTypeId(Long linkTypeId,LinkTypeDTO linkTypeDTO);


    /**
     * 批量删除链路类型, 根据 链路类型ID, 请求路径:/linkTypes/:versionId
     * @param linkTypeIds
     */
    boolean deleteByLinkTypeId(List<Long> linkTypeIds);

    /**
     * 批量删除链路类型, 根据 版本ID
     * @param versionId
     */
    boolean deleteByVersionId(Long versionId);

    /**
     * 添加链路类型 ,请求路径:/linkTypes/:versionId
     * @param versionId
     * @param linkTypeDTO
     * @return
     */
    LinkTypeDTO createLinkType(Long versionId,LinkTypeDTO linkTypeDTO);


    /**
     * 获取所有链路类型信息 ,请求路径:/linkTypes/:versionId
     *  @param versionId
     * @return
     */
    List<LinkTypeDTO> retrieveLinkTypes(Long versionId);

}
