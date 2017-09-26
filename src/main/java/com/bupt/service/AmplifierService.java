package com.bupt.service;

import com.bupt.pojo.AmplifierDTO;

import java.util.List;

/**
 * Created by caoxiaohong on 17/9/13.
 * 放大器管理模块:service层
 */
public interface AmplifierService {
    //修改放大器 /amplifiers/:versionId/:amplifierId

    /**
     * 修改放大器: 根据  放大器id
     * @param amplifer 放大器
     * @return
     */
     AmplifierDTO updateAmplifiers(Long amplifierID,AmplifierDTO amplifer);
    //批量删除 /amplifiers/:versionId/

    /**
     * 批量删除: 根据 条目所在版本ID
     * @param vid 被删除列表
     * @return
     */
    boolean deleteByVid(Long vid);
    //添加放大器 /amplifiers/:versionId/

    /**
     * 批量删除: 根据 放大器ID
     * @param listAmpid
     * @return
     */
    boolean deleteByAmpid(List<Long> listAmpid);

    /**
     * 添加放大器
     * @param amplifer
     * @return
     */
    AmplifierDTO insertAmplifier(Long versionId,AmplifierDTO amplifer);
    //获取所有放大器 /amplifiers/:versionId/

    /**
     * 获取所有放大器
     * @param versionID
     * @return
     */
    List<AmplifierDTO> selectAmplifiers(Long versionID);
}
