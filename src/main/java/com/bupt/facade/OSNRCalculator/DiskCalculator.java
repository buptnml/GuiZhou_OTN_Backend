package com.bupt.facade.OSNRCalculator;


import com.bupt.pojo.DiskDTO;

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
    void calculate(DiskDTO disk, double inputPower, long versionId);

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
