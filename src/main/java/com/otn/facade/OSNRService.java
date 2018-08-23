package com.otn.facade;


import com.otn.facade.OSNRCalculator.exceptions.OutOfInputLimitsException;
import com.otn.pojo.*;

import java.util.List;

/**
 * OSNRService层
 */
public interface OSNRService {


    /**
     * 获取OSNR计算不合格的业务列表
     *
     * @return
     */
    List<BussinessDTO> listErrorBussiness(Long versionId);


    /**
     * 获取OSNR计算不合格的业务列表
     *
     * @return
     */
    List<BussinessDTO> listErrorBussiness(Long versionId, String circleId);

    /**
     * 获取OSNR计算细节数据
     *
     * @param versionId
     * @param bussinessId
     */
    List<OSNRNodesDetails> getNodeOSNRDetail(Long versionId, Long bussinessId);

    /**
     * 获取OSNR
     *
     * @param versionId
     * @param bussinessId
     * @return
     */
    List<OSNRGeneralInfo> getRouteOSNRDetail(Long versionId, Long bussinessId);


    /**
     * 获取指定业务下OSNR计算结果
     *
     * @param versionId
     * @param bussinessId
     * @param isMain
     * @return
     */
    List<OSNRDetailInfo> getOSNRResult(Long versionId, Long bussinessId, Boolean isMain);

    /**
     * 给定一个路由，计算是否满足OSNR条件
     *
     * @param versionId
     * @param osnrLegalCheckRequest
     * @return
     */
    void OSNRLegalCheck(Long versionId, OSNRLegalCheckRequest osnrLegalCheckRequest) throws OutOfInputLimitsException;
}
