package com.otn.facade.OSNRCalculator;

import com.otn.pojo.OSNRNodesDetails;
import com.otn.pojo.OSNRResult;

import java.util.List;

/**
 * OSNR算法接口
 * 标记性接口，实现了这个接口的类均具有计算OSNR结果的能力
 * 这是整个OSNR计算包中唯一对外开放的接口
 */
public interface Calculable {
    /**
     * 规定计算流程的函数
     */
    void calculate(double[][] inputPowers, double[][] outputPowers, String routeString, long versionId) throws IllegalArgumentException;

    /**
     * 计算函数
     *
     * @param inputPower
     * @param routeString
     * @param versionId
     */
    void calculate(double inputPower, String routeString, long versionId);

    /**
     * 获取计算结果的接口
     */
    List<OSNRResult> getResult();

    /**
     * 获取计算完毕后的输入值字符串
     *
     * @return
     */
    String getInputPowersString();


    /**
     * 获取计算完毕后的输出值字符串
     */
    String getOutputPowerString();


    List<OSNRNodesDetails> getNodeResults();


}


