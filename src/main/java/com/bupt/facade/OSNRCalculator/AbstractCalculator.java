package com.bupt.facade.OSNRCalculator;


import com.bupt.facade.OSNRCalculator.exceptions.OutOfInputLimitsException;

/**
 * OSNR计算抽象类
 * 主要用来实现模板方法设计模式
 */
abstract class AbstractCalculator implements Calculable {
    /**
     * OSNR的计算需要两个阶段
     * PHASE1：计算OSNR所经过的所有网元（如果有更细粒度的需要细化到最细粒度，如机盘）的输入输出功率
     * PHASE2：计算OSNR所经过的所有的网元的OSNR值
     * 其中，第一个阶段是可以跳过的（如果给定了输入和输出功率）
     */
    @Override
    public final void calculate(double[][] inputPowers, double[][] outputPowers, String routeString, long versionId) {
        init(inputPowers, outputPowers, routeString, versionId);
        if (!hasInputsOutputs()) {
            inputsOutputsCalculate();
        }
        OSNRCalculate();
    }

    /**
     * 指定一个虚拟初始化函数
     *
     * @param inputPowers
     * @param outputPowers
     * @param routeString
     * @param versionId
     */
    abstract void init(double[][] inputPowers, double[][] outputPowers, String routeString, long versionId);

    /**
     * 用来计算各个网元的输入和输出
     */
    abstract void inputsOutputsCalculate() throws OutOfInputLimitsException;

    /**
     * 用来计算各个节点的OSNR值
     */
    abstract void OSNRCalculate();


    /**
     * 判断是否有OSNR计算需要的各个节点的输入和输出的函数
     * 继承类应当是可以覆写这个函数的
     */
    boolean hasInputsOutputs() {
        return false;
    }
}
