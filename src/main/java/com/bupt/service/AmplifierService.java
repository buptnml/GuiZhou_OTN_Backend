package com.bupt.service;

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
     * @param amplifer 放大器
     * @return
     */
     AmplifierDTO updateAmplifiers(Long amplifierID,AmplifierDTO amplifer);


    /**
     * 批量删除: 根据 放大器ID
     * 请求路径:/amplifiers/:versionId/
     * @param listAmpid
     * @return
     */
    boolean deleteByAmpid(Long versionId,List<Long> listAmpid);


    /**
     * 添加放大器
     * @param amplifer
     * @return
     */
    AmplifierDTO insertAmplifier(Long versionId,AmplifierDTO amplifer);


    /**
     * 获取所有放大器
     * 请求路径:/amplifiers/:versionId/
     * @param versionID
     * @return
     */
    List<AmplifierDTO> selectAmplifiers(Long versionID);
}
