package com.bupt.facade.OSNRCalculator.impl;

import com.bupt.facade.OSNRCalculator.InputsOutputsCalculable;
import com.bupt.facade.OSNRCalculator.OSNRResultsCalculable;
import com.bupt.facade.OSNRCalculator.exceptions.OutOfInputLimitsException;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

/**
 * OSNR计算器实现
 */
@Component
public class OSNRCalculator extends AbstractCalculator {
    @Resource
    private
    InputsOutputsCalculable inputsOutputsCalculable;
    @Resource
    private
    OSNRResultsCalculable osnrResultsCalculable;
    private long versionId;
    private String routeString;
    private double[][] inputPowers;
    private double[][] outputPowers;
    private List<OSNRResult> results;

    @Override
    /*
      所有的输入和输出要一一对应
     */
    boolean hasInputsOutputs() {
        if (null == this.outputPowers) {
            return false;
        }
        if (this.inputPowers.length == 0 || this.outputPowers.length == 0) {
            return false;
        }
        if (this.inputPowers.length != this.outputPowers.length) {
            return false;
        } else {
            for (int i = 0; i < inputPowers.length; i++) {
                if (inputPowers[i].length != outputPowers[i].length) {
                    return false;
                }
            }
            return true;
        }
    }

    @Override
    void init(double[][] inputPowers, double[][] outputPowers, String routeString, long versionId) {
        this.inputPowers = inputPowers;
        this.outputPowers = outputPowers;
        this.routeString = routeString;
        this.versionId = versionId;
    }


    void init(double inputPowers, String routeString, long versionId) {
        double[][] inputs = new double[1][1];
        inputs[0][0] = inputPowers;
        this.init(inputs, new double[0][0], routeString, versionId);
    }

    @Override
    void inputsOutputsCalculate() throws OutOfInputLimitsException {
        inputsOutputsCalculable.calculate(routeString, inputPowers[0][0], versionId);
        inputPowers = inputsOutputsCalculable.getInputPowers();
        outputPowers = inputsOutputsCalculable.getOutputPowers();
    }

    @Override
    void OSNRCalculate() {
        results = osnrResultsCalculable.getResults(routeString, inputPowers, outputPowers);
    }

    @Override
    public List<OSNRResult> getResult() {
        return results;
    }

    @Override
    public String getInputPowersString() {
        return Arrays.deepToString(inputPowers);
    }

    @Override
    public String getOutputPowerString() {
        return Arrays.deepToString(outputPowers);
    }


}
