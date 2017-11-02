package com.bupt.facade.OSNRCalculator;

import com.bupt.facade.OSNRCalculator.exceptions.OSNRResultOutOfLimitException;
import com.bupt.pojo.NodeOSNRDetail;
import com.bupt.pojo.OSNRResult;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;

@Component
class OSNRResultsCalculator implements OSNRResultsCalculable {
    private double[][] inputPowers;
    private double[][] outputPowers;
    private double[] noisePowers;
    private String[] nodes;

    @Override
    public List<OSNRResult> getResults(String routeString, double[][] inputPowers, double[][] outputPowers) {
        this.init(inputPowers, outputPowers);
        this.calculate();
        List<OSNRResult> results = new LinkedList<>();
        nodes = routeString.split("-");
        for (int i = 0; i < noisePowers.length; i++) {
            double OSNR = this.outputPowers[i][outputPowers[i].length - 1] - noisePowers[i];
            if (OSNR > 18D) {
                /*OSNR值要大于18dB*/
                results.add(new OSNRResult(nodes[i], OSNR));
            } else {
                throw new OSNRResultOutOfLimitException();
            }
        }
        return results;
    }

    @Override
    public List<NodeOSNRDetail> getDetail() {
        List<NodeOSNRDetail> results = new LinkedList<>();
        for (int i = 0; i < noisePowers.length; i++) {
            results.add(new NodeOSNRDetail(nodes[i], inputPowers[i][outputPowers[i].length - 1],
                    outputPowers[i][outputPowers[i].length - 1], outputPowers[i][outputPowers[i].length -
                    1] - inputPowers[i][outputPowers[i].length - 1], noisePowers[i]));
        }
        return results;
    }


    /**
     * 此函数用来计算每一个网元输出功率中的噪声功率，针对每一个放大器i有
     * 已知的只有这个放大器的输入功率P_IN个输出功率P_OUT
     * <p>
     * 定义 P_IN = sigmaP_Ase + P
     * 其中sigmaP_Ase为输入功率中的前面的网元的累积噪声功率
     * P为输入功率中实际的信号功率
     * <p>
     * 再定义 alpha = (P_OUT - P_Asei) / (P_IN)
     * P_OUT指的是这个放大器计算出的输出功率
     * P_Asei = （P_OUT - P_IN) - 54  P_Asei指的是放大器i产生的内部噪声
     * <p>
     * 通过上面的几个公式可以计算出这个放大器的P_OUT由以下几个部分组成：
     * 新的噪声功率 sigmaP_Ase' = sigmaP_ase * alpha + P_Asei
     * 新的信号功率 P' = P * alpha
     * 由此可以进行下一步的计算
     * <p>
     * 注：针对每一个放大器，输入功率可能和上一级的输出功率是不同的
     * 因此输入功率（包括信号功率和噪声功率）也要由上一级的输出功率同比例缩小而来
     * 注2：单位要统一在mW下计算
     */
    private void calculate() {
        if (inputPowers.length == 0) {
            return;
        }
        //初始化
        double sigmaP_Ase_last = 0;
        double P_last = toMw(inputPowers[0][0]);
        for (int i = 0; i < inputPowers.length; i++) {
            for (int j = 0; j < inputPowers[i].length; j++) {
                double sigmaP_Ase, P, P_Asei, alpha;
                // 针对每一个放大器,第一步首先判断上一级的输出功率和这一级的输入功率是否相同
                // 如果不相同要同比例缩少输入功率中各个功率成分的大小
                if (Math.abs(sigmaP_Ase_last + P_last - toMw(inputPowers[i][j])) >= 0.000001) {
                    sigmaP_Ase = sigmaP_Ase_last * (toMw(inputPowers[i][j]) / (sigmaP_Ase_last + P_last));
                    P = P_last * (toMw(inputPowers[i][j]) / (sigmaP_Ase_last + P_last));
                } else {
                    sigmaP_Ase = sigmaP_Ase_last;
                    P = P_last;
                }
                P_Asei = toMw(outputPowers[i][j] - inputPowers[i][j] - 54);
                alpha = (toMw(outputPowers[i][j]) - P_Asei) / toMw(inputPowers[i][j]);
                sigmaP_Ase_last = sigmaP_Ase * alpha + P_Asei;
                P_last = P * alpha;
            }
            //最后噪音功率转换为dBm
            noisePowers[i] = toDBm(sigmaP_Ase_last);
        }
    }

    private double toMw(double power) {
        return Math.pow(10, power / 10);
    }

    private double toDBm(double power) {
        return 10 * Math.log(power) / Math.log(10);
    }


    private void init(double[][] inputPowers, double[][] outputPowers) {
        this.inputPowers = inputPowers;
        this.outputPowers = outputPowers;
        noisePowers = new double[inputPowers.length];
    }
}
