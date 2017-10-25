package com.bupt.pojo;

public class NodeOSNRDetail {
    private String nodeName;
    private double inputPower;
    private double outputPower;
    private double gain;
    private double noisePower;

    public NodeOSNRDetail(String nodeName, double inputPower, double outputPower, double gain, double noisePower) {
        this.nodeName = nodeName;
        this.inputPower = inputPower;
        this.outputPower = outputPower;
        this.gain = gain;
        this.noisePower = noisePower;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NodeOSNRDetail that = (NodeOSNRDetail) o;

        if (Double.compare(that.inputPower, inputPower) != 0) return false;
        if (Double.compare(that.outputPower, outputPower) != 0) return false;
        return Double.compare(that.gain, gain) == 0 && Double.compare(that.noisePower, noisePower) == 0 && (nodeName != null ? nodeName.equals(that.nodeName) : that.nodeName == null);
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = nodeName != null ? nodeName.hashCode() : 0;
        temp = Double.doubleToLongBits(inputPower);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(outputPower);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(gain);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(noisePower);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public double getInputPower() {
        return inputPower;
    }

    public void setInputPower(double inputPower) {
        this.inputPower = inputPower;
    }

    public double getOutputPower() {
        return outputPower;
    }

    public void setOutputPower(double outputPower) {
        this.outputPower = outputPower;
    }

    public double getGain() {
        return gain;
    }

    public void setGain(double gain) {
        this.gain = gain;
    }

    public double getNoisePower() {
        return noisePower;
    }

    public void setNoisePower(double noisePower) {
        this.noisePower = noisePower;
    }
}
