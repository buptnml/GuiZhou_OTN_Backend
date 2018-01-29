package com.otn.service;

import com.otn.pojo.NetElementCreateInfo;
import com.otn.pojo.NetElementDTO;

import java.util.List;

public interface NetElementService {

    /**
     * 添加网元
     *
     * @param netElementCreateInfo
     * @return
     */
    NetElementDTO saveNetElement(Long versionId, NetElementCreateInfo netElementCreateInfo);

    /**
     * 批量删除指定id的网元
     *
     * @param netElementIdList
     */
    void listRemoveNetElement(Long versionId, List<Long> netElementIdList);

    /**
     * 查询当前版本所有网元信息
     *
     * @param versionId
     * @return
     */
    List<NetElementDTO> listNetElement(Long versionId);


    /**
     * 更新/修改网元信息
     *
     * @param netElementCreateInfo
     * @return
     */
    NetElementDTO updateNetElement(Long versionId, Long netElementId, NetElementCreateInfo netElementCreateInfo);

    /**
     * 删除指定版本ID的所有网元
     *
     * @param versionId
     */
    void batchRemove(long versionId);


    /**
     * 通过netElementId查询网元信息
     *
     * @param netElementId
     * @return
     */
    NetElementDTO getNetElement(Long versionId, long netElementId);

    /**
     * 通过netElementName查询网元信息
     *
     * @param netElementName
     * @return
     */
    NetElementDTO getNetElement(Long versionId, String netElementName);


    /**
     * 复制一个旧有版本Id中的内容，并将版本Id字段重命名为新Id
     */
    void batchCreate(Long baseVersionId, Long newVersionId);

}



