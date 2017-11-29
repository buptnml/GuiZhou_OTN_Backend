package com.bupt.facade.OSNRCalculator;


import com.bupt.util.exception.controller.input.IllegalArgumentException;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 计算输入输出功率的实现类
 */
@Component
class InputsOutputsCalculator implements InputsOutputsCalculable {
    @Resource
    private
    NetElementCalculator netElementCalculator;
    @Resource
    private LinkLossCalculator linkLossCalculator;
    private String[] nodes;
    private double firstInput;
    private double lastOutput;
    private double[][] inputPowers;
    private double[][] outputPowers;
    /*size记录的是每一次计算的时候的输入输出功率对实际的长度，受各种原因影响，实际上可能会无法计算到所有节点的输入输出功率*/

    private void init(String routeString, double firstInput) {
        this.nodes = routeString.split("-");
        this.firstInput = firstInput;
        inputPowers = new double[nodes.length][];
        outputPowers = new double[nodes.length][];
        lastOutput = 0;
    }

    @Override
    public double[][] getInputPowers() {
        /*返回结果的时候要剔除实际为null的二维数组*/
        double[][] results = new double[getSize(inputPowers)][];
        for (int i = 0; i < getSize(inputPowers); i++) {
            results[i] = inputPowers[i];
        }
        return results.clone();
    }

    @Override
    public double[][] getOutputPowers() {
        /*返回结果的时候要剔除实际为null的二维数组*/
        double[][] results = new double[getSize(outputPowers)][];
        for (int i = 0; i < getSize(outputPowers); i++) {
            results[i] = outputPowers[i];
        }
        return results.clone();
    }

    private int getSize(double[][] arr) {
        int count = 0;
        for (int i = 0; i < arr.length; i++) {
            if (null == arr[i]) break;
            count++;
        }
        return count;
    }

    public void calculate(String routeString, double firstInput, long versionId) throws IllegalArgumentException {
        init(routeString, firstInput);
        for (int i = 0; i < nodes.length - 1; i++) {
            netElementCalculator.calculate(nodes[i], versionId, this.firstInput);
            setPowers(i);
            this.firstInput = this.lastOutput - linkLossCalculator.getLinkLoss(versionId, nodes[i], nodes[i + 1]);
        }
        netElementCalculator.calculate(nodes[nodes.length - 1], versionId, this.firstInput);
        setPowers(nodes.length - 1);
    }

    private void setPowers(int i) {
        this.inputPowers[i] = netElementCalculator.getInputPowers();
        this.outputPowers[i] = netElementCalculator.getOutputPowers();
        this.lastOutput = netElementCalculator.getLastOutput();
    }
}
