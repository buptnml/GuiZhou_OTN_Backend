package com.bupt.facade.OSNRCalculator;

import com.bupt.facade.OSNRCalculator.exceptions.DiskNotFoundException;
import com.bupt.facade.OSNRCalculator.exceptions.NetElementNotFoundException;
import com.bupt.pojo.DiskDTO;
import com.bupt.service.DiskService;
import com.bupt.service.NetElementService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
class NetElementCalculatorImpl implements NetElementCalculator {
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
    public void calculate(String netElementName, long versionId, double firstInput) throws IllegalArgumentException {
        init(netElementName, versionId, firstInput);
        for (int i = 0; i < this.disks.size() - 1; i++) {
            diskCalculator.calculate(this.disks.get(i), this.inputPowers[i], this.versionId);
            setPowers(i);
            //网元之间的机盘是没有功率损耗的，上一级的输出功率直接作为下一级的输入功率
            this.inputPowers[i + 1] = this.outputPowers[i];
        }
        diskCalculator.calculate(this.disks.get(this.disks.size() - 1), this.inputPowers[this.disks.size() - 1],
                this.versionId);
        setPowers(this.disks.size() - 1);
    }

    private void init(String netElementName, long versionId, double firstInput) {
        this.versionId = versionId;
        if (null == netElementService.getNetElement(versionId, netElementName)) {
            throw new NetElementNotFoundException(netElementName);
        }
        this.disks = diskService.listDiskByNetElement(versionId, netElementService.getNetElement(versionId, netElementName)
                .getNetElementId());
        if (disks.size() == 0) {
            throw new DiskNotFoundException(netElementName);
        }
        this.inputPowers = new double[this.disks.size()];
        this.outputPowers = new double[this.disks.size()];
        this.inputPowers[0] = firstInput;
    }


    private void setPowers(int i) {
        /*考虑实际情况，输入功率可能会有所变动，需要重新赋值*/
        this.inputPowers[i] = diskCalculator.getInputPower();
        this.outputPowers[i] = diskCalculator.getOutputPower();
    }


}
