package com.bupt.pojo;

public class NodeOSNRDetail {
    private String nodeName;
    private Double inputPower;
    private Double outputPower;
    private Double gain;
    private Double noisePower;

    public NodeOSNRDetail(String nodeName, double inputPower, double outputPower, double gain, double noisePower) {
        this.nodeName = nodeName;
        this.inputPower = inputPower;
        this.outputPower = outputPower;
        this.gain = gain;
        this.noisePower = noisePower;
    }

    public NodeOSNRDetail() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NodeOSNRDetail that = (NodeOSNRDetail) o;

        return Double.compare(that.inputPower, inputPower) == 0 && Double.compare(that.outputPower, outputPower) == 0 && Double.compare(that.gain, gain) == 0 && Double.compare(that.noisePower, noisePower) == 0 && (nodeName != null ? nodeName.equals(that.nodeName) : that.nodeName == null);
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

    public Double getInputPower() {
        return inputPower;
    }

    public void setInputPower(Double inputPower) {
        this.inputPower = inputPower;
    }

    public Double getOutputPower() {
        return outputPower;
    }

    public void setOutputPower(Double outputPower) {
        this.outputPower = outputPower;
    }

    public Double getGain() {
        return gain;
    }

    public void setGain(Double gain) {
        this.gain = gain;
    }

    public Double getNoisePower() {
        return noisePower;
    }

    public void setNoisePower(Double noisePower) {
        this.noisePower = noisePower;
    }
}
