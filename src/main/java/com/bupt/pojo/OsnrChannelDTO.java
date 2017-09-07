package com.bupt.pojo;

import com.bupt.util.exception.controller.input.NullArgumentException;

public class OsnrChannelDTO {
    Long channelId;
    Long bussinessId;
    boolean isMain;
    String channelRate;
    String channelFrequency;
    String inputPowers;
    String outputPowers;

    public void checkChannelDTO(){
        if(null == channelRate){
            throw new NullArgumentException("channelRate");
        }
        if(null == channelFrequency){
            throw new NullArgumentException("channelFrequency");
        }
    }
    public Long getBussinessId() {
        return bussinessId;
    }

    public void setBussinessId(Long bussinessId) {
        this.bussinessId = bussinessId;
    }

    public boolean isMain() {
        return isMain;
    }

    public void setMain(boolean main) {
        isMain = main;
    }

    public Long getChannelId() {
        return channelId;
    }

    public void setChannelId(Long channelId) {
        this.channelId = channelId;
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
