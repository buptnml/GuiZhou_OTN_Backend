package com.bupt.pojo;

public class OsnrChannelDTO {
    Long channelId;
    Long bussinessId;
    Boolean isMain;
    String channelRate;
    String channelFrequency;
    String channelRoute;
    String inputPowers;
    String outputPowers;

    public Long getBussinessId() {
        return bussinessId;
    }

    public void setBussinessId(Long bussinessId) {
        this.bussinessId = bussinessId;
    }

    public Boolean getMain() {
        return isMain;
    }

    public void setMain(Boolean main) {
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

    public String getChannelRoute() {
        return channelRoute;
    }

    public void setChannelRoute(String channelRoute) {
        this.channelRoute = channelRoute;
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
