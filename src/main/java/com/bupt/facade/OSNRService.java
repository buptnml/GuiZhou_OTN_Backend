package com.bupt.facade;


import com.bupt.pojo.BussinessDTO;
import com.bupt.pojo.NodeOSNRDetail;
import com.bupt.pojo.ResultOSNRDetail;
import com.bupt.pojo.RouteOSNRDetail;

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
     * 获取OSNR计算细节数据
     *
     * @param versionId
     * @param bussinessId
     */
    List<NodeOSNRDetail> getNodeOSNRDetail(Long versionId, Long bussinessId);

    /**
     * 获取OSNR
     *
     * @param versionId
     * @param bussinessId
     * @return
     */
    List<RouteOSNRDetail> getRouteOSNRDetail(Long versionId, Long bussinessId);


    /**
     * 获取指定业务下OSNR计算结果
     *
     * @param versionId
     * @param bussinessId
     * @param isMain
     * @return
     */
    List<ResultOSNRDetail> getOSNRResult(Long versionId, Long bussinessId, Boolean isMain);
}
