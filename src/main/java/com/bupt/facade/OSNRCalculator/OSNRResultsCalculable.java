package com.bupt.facade.OSNRCalculator;

import com.bupt.pojo.NodeOSNRDetail;
import com.bupt.pojo.OSNRResult;

import java.util.List;

/**
 * OSNR值的具体计算接口
 * 标记性接口
 */
interface OSNRResultsCalculable {
    /**
     * 用来计算ONSR结果并返回的函数
     *
     * @param routeString
     * @param inputPowers
     * @param outputPowers
     * @return
     */
    List<OSNRResult> getResults(String routeString, double[][] inputPowers, double[][] outputPowers);

    /**
     * 获取各个节点的输入输出功率增益噪声等信息
     *
     * @return
     */
    List<NodeOSNRDetail> getDetail();


}
