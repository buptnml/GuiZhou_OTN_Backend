package com.bupt.facade.OSNRCalculator;

import com.bupt.pojo.NodeOSNRDetail;
import com.bupt.pojo.OSNRResult;

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
    void calculate(double[][] inputPowers, double[][] outputPowers, String routeString, long versionId);


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


    List<NodeOSNRDetail> getNodeResults();


}


