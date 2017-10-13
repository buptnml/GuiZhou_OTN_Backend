package com.bupt.facade.OSNRCalculator.impl;

import com.bupt.facade.OSNRCalculator.DiskCalculator;
import com.bupt.facade.OSNRCalculator.NetElementCalculator;
import com.bupt.facade.OSNRCalculator.exceptions.OutOfInputLimitsException;
import com.bupt.pojo.DiskDTO;
import com.bupt.service.DiskService;
import com.bupt.service.NetElementService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
public class NetElementCalculatorImpl implements NetElementCalculator {
    private List<DiskDTO> disks;
    private long versionId;
    private double[] inputPowers;
    private double[] outputPowers;
    @Resource
    private NetElementService netElementService;
    @Resource
    private DiskService diskService;
    @Resource
    private DiskCalculator diskCalculator;

    @Override
    public double[] getInputPowers() {
        return inputPowers;
    }

    @Override
    public double[] getOutputPowers() {
        return outputPowers;
    }

    @Override
    public double getLastOutput() {
        return outputPowers[outputPowers.length - 1];
    }

    @Override
    public void calculate(String netElementName, long versionId, double firstInput) throws OutOfInputLimitsException {
        init(netElementName, versionId, firstInput);
        for (int i = 0; i < this.disks.size() - 1; i++) {
            setPowers(i);
            //网元之间的机盘是没有功率损耗的，上一级的输出功率直接作为下一级的输入功率
            this.inputPowers[i + 1] = this.outputPowers[i];
        }
        setPowers(this.disks.size() - 1);
    }

    private void init(String netElementName, long versionId, double firstInput) {
        this.versionId = versionId;
        this.disks = diskService.listDiskByNetElement(versionId, netElementService.getNetElement(versionId, netElementName)
                .getNetElementId());
        this.inputPowers = new double[this.disks.size()];
        this.outputPowers = new double[this.disks.size()];
        this.inputPowers[0] = firstInput;
    }


    private void setPowers(int i) throws OutOfInputLimitsException {
        diskCalculator.calculate(this.disks.get(i), this.inputPowers[i], this.versionId);
        /*考虑实际情况，输入功率可能会有所变动，需要重新赋值*/
        this.inputPowers[i] = diskCalculator.getInputPower();
        this.outputPowers[i] = diskCalculator.getOutputPower();
    }


}
