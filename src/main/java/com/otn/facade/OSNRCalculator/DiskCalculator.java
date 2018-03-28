package com.otn.facade.OSNRCalculator;


import com.otn.facade.OSNRCalculator.exceptions.OutOfInputLimitsException;
import com.otn.pojo.DiskDTO;

/**
 * 机盘计算接口
 */
interface DiskCalculator {

    /**
     * 计算入口
     *
     * @param disk
     * @param inputPower
     * @param versionId
     */
    void calculate(DiskDTO disk, double inputPower, long versionId) throws OutOfInputLimitsException;

    /**
     * 获取计算后的输出功率
     *
     * @return
     */
    double getOutputPower();


    /**
     * 获取计算后合法的输入功率
     *
     * @return
     */
    double getInputPower();

}
