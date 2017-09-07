package com.bupt.pojo;

import com.bupt.util.exception.controller.input.NullArgumentException;
import org.apache.ibatis.jdbc.Null;

public class BussinessDTO {
    Long bussinessId;
    String bussinessName;
    String mainRoute;
    String spareRoute;
    OsnrChannelDTO mainChannel;
    OsnrChannelDTO spareChannel;

    public void checkBussinessDTO(){
        if(null == bussinessName){
            throw new NullArgumentException("bussinessName");
        }
        if(null == mainRoute){
            throw new NullArgumentException("mainRoute");
        }
        if(null == mainChannel){
            throw new NullArgumentException("mainChannel");
        }else{
            mainChannel.checkChannelDTO();
        }if(null != spareRoute){
            if(null == spareChannel){
                throw new NullArgumentException("spareChannel");
            }else{
                spareChannel.checkChannelDTO();
            }
        }
    }


    public OsnrChannelDTO getMainChannel() {
        return mainChannel;
    }

    public void setMainChannel(OsnrChannelDTO mainChannel) {
        this.mainChannel = mainChannel;
    }

    public OsnrChannelDTO getSpareChannel() {
        return spareChannel;
    }

    public void setSpareChannel(OsnrChannelDTO spareChannel) {
        this.spareChannel = spareChannel;
    }

    public Long getBussinessId() {
        return bussinessId;
    }

    public void setBussinessId(Long bussinessId) {
        this.bussinessId = bussinessId;
    }

    public String getBussinessName() {
        return bussinessName;
    }

    public void setBussinessName(String bussinessName) {
        this.bussinessName = bussinessName;
    }

    public String getMainRoute() {
        return mainRoute;
    }

    public void setMainRoute(String mainRoute) {
        this.mainRoute = mainRoute;
    }

    public String getSpareRoute() {
        return spareRoute;
    }

    public void setSpareRoute(String spareRoute) {
        this.spareRoute = spareRoute;
    }
}
