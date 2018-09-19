package com.otn.facade.OSNRCalculator;

import com.otn.facade.OSNRCalculator.exceptions.OSNRResultOutOfLimitException;
import com.otn.facade.OSNRCalculator.exceptions.OutOfInputLimitsException;
import com.otn.pojo.OSNRNodesDetails;
import com.otn.pojo.OSNRResult;
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
    private List<OSNRNodesDetails> nodeResults;

    @Override
    /*
      所有的输入和输出要一一对应
     */
    protected boolean hasInputsOutputs() {
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
    protected void init(double[][] inputPowers, double[][] outputPowers, String routeString, long versionId) {
        this.inputPower = inputPowers[0][0];
        this.inputPowers = inputPowers;
        this.outputPowers = outputPowers;
        this.routeString = routeString;
        this.versionId = versionId;
        results = null;
        nodeResults = null;
    }


    @Override
    protected void inputsOutputsCalculate() throws IllegalArgumentException, OutOfInputLimitsException {
        try {
            inputsOutputsCalculable.calculate(routeString, inputPowers[0][0], versionId);
        } finally {
            inputPowers = inputsOutputsCalculable.getInputPowers();
            outputPowers = inputsOutputsCalculable.getOutputPowers();
        }
    }

    @Override
    protected void OSNRCalculate() throws OSNRResultOutOfLimitException {
        results = osnrResultsCalculable.getResults(this.versionId, routeString, inputPowers, outputPowers);
        nodeResults = osnrResultsCalculable.getDetail();
    }


    @Override
    public List<OSNRResult> getResult() {
        return results;
    }

    public List<OSNRNodesDetails> getNodeResults() {
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
