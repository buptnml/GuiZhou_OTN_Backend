package com.otn.facade.OSNRCalculator;

import com.otn.pojo.OSNRNodesDetails;
import com.otn.pojo.OSNRResult;

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
    List<OSNRResult> getResults(Long versionId, String routeString, double[][] inputPowers, double[][] outputPowers);

    /**
     * 获取各个节点的输入输出功率增益噪声等信息
     *
     * @return
     */
    List<OSNRNodesDetails> getDetail();


}
