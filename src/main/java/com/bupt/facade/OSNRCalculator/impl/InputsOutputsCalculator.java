package com.bupt.facade.OSNRCalculator.impl;


import com.bupt.facade.OSNRCalculator.InputsOutputsCalculable;
import com.bupt.facade.OSNRCalculator.LinkLossCalculator;
import com.bupt.facade.OSNRCalculator.NetElementCalculator;
import com.bupt.facade.OSNRCalculator.exceptions.OutOfInputLimitsException;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 计算输入输出功率的实现类
 */
@Component
public class InputsOutputsCalculator implements InputsOutputsCalculable {
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

    private void init(String routeString, double firstInput) {
        this.nodes = routeString.split("-");
        this.firstInput = firstInput;
        inputPowers = new double[nodes.length][];
        outputPowers = new double[nodes.length][];
    }

    @Override
    public double[][] getInputPowers() {
        return inputPowers;
    }

    @Override
    public double[][] getOutputPowers() {
        return outputPowers;
    }

    public void calculate(String routeString, double firstInput, long versionId) throws OutOfInputLimitsException {
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
