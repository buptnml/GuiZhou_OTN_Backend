package com.bupt.service;

import com.bupt.pojo.AmplifierCreateInfo;
import com.bupt.pojo.AmplifierDTO;

import java.util.List;

/**
 * Created by caoxiaohong on 17/9/13.
 * 放大器管理模块:service层
 */
public interface AmplifierService {
    /**
     * 修改放大器: 根据  放大器id
     * 请求路径:/amplifiers/:versionId/:amplifierId
     *
     * @param amplifierCreateInfo 放大器
     * @return
     */
    AmplifierDTO updateAmplifiers(Long versionId, Long amplifierID, AmplifierCreateInfo amplifierCreateInfo);


    /**
     * 批量删除: 根据 放大器ID
     * 请求路径:/amplifiers/:versionId/
     *
     * @param listAmpid
     * @return
     */
    boolean deleteByAmpid(Long versionId, List<Long> listAmpid);


    /**
     * 添加放大器
     *
     * @param amplifierCreateInfo
     * @return
     */
    AmplifierDTO insertAmplifier(Long versionId, AmplifierCreateInfo amplifierCreateInfo);

    /**
     * 通过Id获取放大器
     *
     * @param versionId
     * @param amplifierId
     * @return
     */
    AmplifierDTO getAmpById(Long versionId, Long amplifierId);

    /**
     * 通过放大器类型获取放大器
     *
     * @param versionId
     * @param ampName
     * @return AmplifierDTO
     */
    AmplifierDTO getAmpByName(Long versionId, String ampName);


    /**
     * 获取所有放大器
     * 请求路径:/amplifiers/:versionId/
     *
     * @param versionID
     * @return
     */
    List<AmplifierDTO> listAmplifiers(Long versionID);

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
