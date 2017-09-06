package com.bupt.service;

import com.bupt.pojo.OsnrChannelCreateInfo;
import com.bupt.pojo.OsnrChannelDTO;

import java.util.List;

/**
 * OSNR波道Service层
 * 波道表依附于光通道表而存在，因此波道表的行为均和光通道的行为相关
 */
public interface OsnrChannelService {

    /**
     * 删除指定版本中指定光通道的业务
     */
    void removeOsnrChannel(Long versionId, Long bussinessId);

    /**
     * 创建新波道
     */
    OsnrChannelDTO saveOsnrChannel(Long versionId, Long bussinessId, boolean isMain, OsnrChannelCreateInfo osnrChannelCreateInfo);

    /**
     * 修改波道信息
     */
    OsnrChannelDTO updateOsnrChannel(Long versionId, Long bussinessId, boolean isMain, OsnrChannelCreateInfo osnrChannelCreateInfo);

    /**
     * 根据版本ID和光通道Id获取对应的波道信息
     */
    OsnrChannelDTO getOnsrChannel(Long versionId, Long bussinessId, boolean isMain);


    /**
     * 删除指定版本ID下的所有条目
     */
    void batchRemove(Long versionId);

    /**
     * 复制一个旧有版本Id中的内容，并将版本Id字段重命名为新Id
     */
    void batchCreate(Long baseVersionId, Long newVersionId);
}
