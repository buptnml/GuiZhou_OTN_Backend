package com.bupt.facade.OSNRCalculator;


/**
 * 用来计算所有节点的输入输出功率接口
 * 标记性接口
 */
interface InputsOutputsCalculable {
    /**
     * 计算函数
     *
     * @param routeString
     * @param firstInput
     * @param versionId
     */
    void calculate(String routeString, double firstInput, long versionId);

    /**
     * 用来返回所有节点输入功率计算结果的函数
     *
     * @return
     */
    double[][] getInputPowers();

    /**
     * 用来返回所有节点的输出功率的函数
     *
     * @return
     */
    double[][] getOutputPowers();

}
