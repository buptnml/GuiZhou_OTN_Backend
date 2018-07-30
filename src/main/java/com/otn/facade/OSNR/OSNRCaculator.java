package com.otn.facade.OSNR;

import com.otn.facade.OSNRCalculator.Calculable;
import com.otn.facade.OSNRCalculator.exceptions.LinkNotFoundException;
import com.otn.facade.OSNRCalculator.exceptions.NetElementNotFoundException;
import com.otn.facade.OSNRCalculator.exceptions.OutOfInputLimitsException;
import com.otn.pojo.*;
import com.otn.service.*;

import javax.annotation.Resource;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.List;

public class OSNRCaculator implements Calculable {
    private static DecimalFormat df = new DecimalFormat("0.00");
    double[] OSNRResults;
    private double[][] inputPowers;
    private double[][] outputPowers;
    private double inputPower;
    @Resource
    private LinkService linkService;
    @Resource
    private LinkTypeService linkTypeService;
    @Resource
    private NetElementService netElementService;
    @Resource
    private DiskService diskService;
    @Resource
    private AmplifierService amplifierService;

    @Override
    public void calculate(double[][] inputPowers, double[][] outputPowers, String routeString, long versionId) {
        if (inputPowers.length == 0) {
            return;
        }
        double P_ASE_LAST = 0;
        String[] nodes = routeString.split("-");
        OSNRResults = new double[nodes.length];
        double OSNR_last = Double.MAX_VALUE;
        for (int i = 0; i < inputPowers.length; i++) {
            double P_ASE, inputPower, outputPower;
            inputPower = inputPowers[i][0];
            outputPower = outputPowers[i][outputPowers[i].length - 1];
            if (inputPower == outputPower) {
                OSNRResults[i] = OSNR_last;
            } else {
                P_ASE = toDBm(toMw(P_ASE_LAST) + toMw(outputPower - inputPower - 51));
                OSNRResults[i] = outputPower - P_ASE;
            }
            OSNR_last = OSNRResults[i];
            if (i != inputPowers.length - 1)
                P_ASE_LAST = P_ASE_LAST - getLinkLoss(versionId, nodes[i], nodes[i + 1]);
        }
    }

    private double toMw(double power) {
        return Math.pow(10, power / 10);
    }

    private double toDBm(double power) {
        return 10 * Math.log(power) / Math.log(10);
    }

    @Override
    public void calculate(double inputPower, String routeString, long versionId) {
        //todo
    }

    private double getPase(double pin, double pout) {
        return pout - pin - 54;
    }

    private NodeCalResults getNodeResults(Long versionId, String netElementName, double inputPower) {
        NetElementDTO node = netElementService.getNetElement(versionId, netElementName);
        double[] inputs, outputs;
        if (null == node) {
            throw new NetElementNotFoundException(netElementName);
        }
        boolean OLA_MARK = node.getNetElementType().contains("OLA");
        if (OLA_MARK) {
            //OLA设备内部没有放大，输出等于输入
            inputs = new double[]{inputPower};
            outputs = new double[]{inputPower};
        } else {
            List<DiskDTO> disks = diskService.listDiskByNetElement(versionId, node.getNetElementId());
            inputs = new double[disks.size()];
            outputs = new double[disks.size()];
            for (int i = 0; i < disks.size(); i++) {
                DiskInputAndOutput diskRes = null;
                try {
                    diskRes = getDiskOutput(disks.get(i), inputPower, versionId);
                } catch (OutOfInputLimitsException e) {
                    return new NodeCalResults(e.getMessage(), inputs[0], null);
                }
                inputs[i] = diskRes.getInput();
                outputs[i] = diskRes.getOutput();
                inputPower = diskRes.getOutput();
            }
        }
        return new NodeCalResults(inputs[0], outputs[outputs.length - 1]);
    }

    private DiskInputAndOutput getDiskOutput(DiskDTO disk, double inputPower, long versionId) throws OutOfInputLimitsException {
        AmplifierDTO amp = amplifierService.getAmpByName(versionId, disk.getAmplifierName());
        if (inputPower < amp.getMinimumInputPower()) {
            throw new OutOfInputLimitsException("输入功率" + df.format(inputPower) + ",小于机盘" + disk.getDiskName() + "能支持的最小功率！");
        }
        inputPower = amp.getMaximumInputPower() > inputPower ? inputPower : amp.getMaximumInputPower();
        /*大于放大器放大后的结果一般按照可以做到的最大输出功率输出，
          也就是说如果放大后的功率大于放大器可以支持的最大输出功率则按照放大器的最大输出功率输出*/
        return new DiskInputAndOutput(inputPower, inputPower + amp.getGain());
    }

    private double getLinkLoss(long versionId, String node1, String node2) {
        LinkDTO link = linkService.getLinkByNodes(versionId, node1, node2);
        if (null == link) {
            throw new LinkNotFoundException(node1, node2);
        }
        if (link.getLinkLoss() != 0) {
            return link.getLinkLoss();
        }
        return linkTypeService.calculateLoss(versionId, link.getLinkType(), link.getLinkLength());
    }

    @Override
    public List<OSNRResult> getResult() {
        //todo
        return null;
    }

    @Override
    public String getInputPowersString() {
        return Arrays.deepToString(inputPowers);
    }

    @Override
    public String getOutputPowerString() {
        return Arrays.deepToString(outputPowers);
    }

    @Override
    public List<OSNRNodesDetails> getNodeResults() {
        //todo
        return null;
    }

    private class NodeCalResults {
        String errorMessage;
        private double input;
        private Double output;

        public NodeCalResults(double input, Double output) {
            this.input = input;
            this.output = output;
        }

        public NodeCalResults(String errorMessage, double input, Double output) {
            this.errorMessage = errorMessage;
            this.input = input;
            this.output = output;
        }
    }

    private class DiskInputAndOutput {
        private double input;
        private double output;

        public DiskInputAndOutput(double input, double output) {
            this.input = input;
            this.output = output;
        }

        public double getInput() {
            return input;
        }

        public void setInput(double input) {
            this.input = input;
        }

        public double getOutput() {
            return output;
        }

        public void setOutput(double output) {
            this.output = output;
        }
    }
}
