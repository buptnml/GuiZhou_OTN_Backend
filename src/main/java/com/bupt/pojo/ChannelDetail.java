package com.bupt.pojo;

import com.bupt.entity.ResBussiness;
import com.bupt.util.exception.controller.input.NullArgumentException;

public class ChannelDetail {
    String channelRate;
    String channelFrequency;
    String inputPowers;
    String outputPowers;

    public ChannelDetail() {
    }

    public ChannelDetail(ResBussiness resBussiness, boolean isMain) {
        this.channelRate = isMain ? resBussiness.getMainRate() : resBussiness.getSpareRate();
        this.channelFrequency = isMain ? resBussiness.getMainFrequency() : resBussiness.getSpareFrequency();
        this.inputPowers = isMain ? resBussiness.getMainInputPowers() : resBussiness.getSpareInputPowers();
        this.outputPowers = isMain ? resBussiness.getMainOutputPowers() : resBussiness.getSpareOutputPowers();
    }

    public void checkChannelDTO() {
        if (null == channelRate) {
            throw new NullArgumentException("channelRate");
        }
        if (null == channelFrequency) {
            throw new NullArgumentException("channelFrequency");
        }
    }

    public String getChannelRate() {
        return channelRate;
    }

    public void setChannelRate(String channelRate) {
        this.channelRate = channelRate;
    }

    public String getChannelFrequency() {
        return channelFrequency;
    }

    public void setChannelFrequency(String channelFrequency) {
        this.channelFrequency = channelFrequency;
    }


    public String getInputPowers() {
        return inputPowers;
    }

    public void setInputPowers(String inputPowers) {
        this.inputPowers = inputPowers;
    }

    public String getOutputPowers() {
        return outputPowers;
    }

    public void setOutputPowers(String outputPowers) {
        this.outputPowers = outputPowers;
    }
}
