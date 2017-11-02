package com.bupt.facade.OSNRCalculator;

import com.bupt.facade.OSNRCalculator.exceptions.OutOfInputLimitsException;
import com.bupt.pojo.AmplifierDTO;
import com.bupt.pojo.DiskDTO;
import com.bupt.service.AmplifierService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;


/**
 * 机盘计算实现
 * 对于机盘内部来说，若输入功率大于机盘可承受的最大功率，按照可承受最大功率计算
 */
@Component
class DIskCalculatorImpl implements DiskCalculator {
    private double inputPower;
    private double outputPower;
    private AmplifierDTO amplifier;
    @Resource
    private AmplifierService amplifierService;

    @Override
    public double getOutputPower() {
        return outputPower;
    }

    @Override
    public double getInputPower() {
        return inputPower;
    }

    @Override
    /*放大器工作原理的关键函数，未来可能需要进一步的修改*/
    public void calculate(DiskDTO disk, double inputPower, long versionId) throws OutOfInputLimitsException {
        init(inputPower, disk, versionId);
        /*输入值如果大于放大器支持的最大输入功率
          或者小于放大器支持的最小输入功率
          均属于设计错误
          */
        if (this.inputPower > amplifier.getMaximumInputPower()) {
            throw new OutOfInputLimitsException("输入功率大于机盘" + disk.getDiskName
                    () + "能支持的最大功率！");
        }
        if (this.inputPower < amplifier.getMinimumInputPower()) {
            throw new OutOfInputLimitsException("输入功率小于机盘" + disk.getDiskName
                    () + "能支持的最小功率！");
        }
        this.inputPower = amplifier.getMaximumInputPower() > inputPower ? inputPower : amplifier.getMaximumInputPower();
        /*大于放大器放大后的结果一般按照可以做到的最大输出功率输出，
          也就是说如果放大后的功率大于放大器可以支持的最大输出功率则按照放大器的最大输出功率输出*/
        this.outputPower = this.inputPower + amplifier.getGain() > amplifier.getMaximumOutputPower() ? amplifier
                .getMaximumOutputPower() : this.inputPower + amplifier.getGain();
    }

    private void init(double inputPower, DiskDTO disk, long versionId) {
        this.inputPower = inputPower;
        amplifier = amplifierService.getAmpByName(versionId, disk.getDiskType());
    }

}
