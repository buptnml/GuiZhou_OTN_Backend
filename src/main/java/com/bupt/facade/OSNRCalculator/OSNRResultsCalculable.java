package com.bupt.facade.OSNRCalculator;

import com.bupt.facade.OSNRCalculator.impl.OSNRResult;

import java.util.List;

/**
 * OSNR值的具体计算接口
 * 标记性接口
 */
public interface OSNRResultsCalculable {
    /**
     * 用来计算ONSR结果并返回的函数
     *
     * @param routeString
     * @param inputPowers
     * @param outputPowers
     * @return
     */
    List<OSNRResult> getResults(String routeString, double[][] inputPowers, double[][] outputPowers);

}
