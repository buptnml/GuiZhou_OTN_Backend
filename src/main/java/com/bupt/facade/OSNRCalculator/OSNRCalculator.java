package com.bupt.facade.OSNRCalculator;

import com.bupt.facade.OSNRCalculator.exceptions.OSNRResultOutOfLimitException;
import com.bupt.pojo.NodeOSNRDetail;
import com.bupt.pojo.OSNRResult;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

/**
 * OSNR计算器实现
 */
@Component
class OSNRCalculator extends AbstractCalculator {
    @Resource
    private
    InputsOutputsCalculable inputsOutputsCalculable;
    @Resource
    private OSNRResultsCalculable osnrResultsCalculable;
    private long versionId;
    private String routeString;
    private double inputPower;
    private double[][] inputPowers;
    private double[][] outputPowers;
    private List<OSNRResult> results;
    private List<NodeOSNRDetail> nodeResults;

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
        this.inputPower = inputPowers[0][0];
        this.inputPowers = inputPowers;
        this.outputPowers = outputPowers;
        this.routeString = routeString;
        this.versionId = versionId;
        results = null;
        nodeResults = null;
    }


    @Override
    void inputsOutputsCalculate() throws IllegalArgumentException {
        try {
            inputsOutputsCalculable.calculate(routeString, inputPowers[0][0], versionId);
        } finally {
            inputPowers = inputsOutputsCalculable.getInputPowers();
            outputPowers = inputsOutputsCalculable.getOutputPowers();
        }
    }

    @Override
    void OSNRCalculate() throws OSNRResultOutOfLimitException {
        results = osnrResultsCalculable.getResults(routeString, inputPowers, outputPowers);
        nodeResults = osnrResultsCalculable.getDetail();
    }

    @Override
    public List<OSNRResult> getResult() {
        return results;
    }

    public List<NodeOSNRDetail> getNodeResults() {
        return nodeResults;
    }

    @Override
    public String getInputPowersString() {
        return Arrays.deepToString(inputPowers).equals("[]") ? Double.toString(this.inputPower) : Arrays.deepToString
                (inputPowers);
    }

    @Override
    public String getOutputPowerString() {
        return Arrays.deepToString(outputPowers);
    }


}
