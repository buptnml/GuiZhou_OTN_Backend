package com.bupt.facade.OSNRCalculator;

/**
 * 计算网元中输入和输出功率的接口
 * 网元的职责是做一层封装，对上面的算法来说，只要知道网元的粒度就可以
 * 这层封装可以保证可以适应未来的数据结构变化（如没有机盘数据）
 */
interface NetElementCalculator {

    /**
     * 计算函数
     *
     * @param netElementName
     * @param versionId
     * @param firstInput
     */
    void calculate(String netElementName, long versionId, double firstInput);

    /**
     * 计算并获取网元的输入功率
     *
     * @return
     */
    double[] getInputPowers();

    /**
     * 计算并获取网元的输出功率（包括内部的机盘等节点）
     *
     * @return
     */
    double[] getOutputPowers();


    /**
     * 返回网元内部放大完后最后输出的输出功率
     *
     * @return
     */
    double getLastOutput();
}
