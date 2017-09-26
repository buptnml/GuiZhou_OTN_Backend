package com.bupt.pojo;

import com.bupt.util.exception.controller.input.NullArgumentException;

public class ChannelCreateInfo {
    private String channelRate;
    private String channelFrequency;

    void checkOsnrChannelCreateInfo(){
        if(null == channelRate){
            throw new NullArgumentException("channelRate");
        }
        if(null == channelFrequency){
            throw new NullArgumentException("channelFrequency");
        }
        if (channelRate.equals("")) {
            throw new IllegalArgumentException("channelRate");
        }
        if(channelFrequency.equals("")){
            throw new IllegalArgumentException("channelFrequency");
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

}
