package com.bupt.pojo;

import com.bupt.entity.ResBussiness;
import com.bupt.util.exception.controller.input.NullArgumentException;

public class BussinessDTO {
    private Long bussinessId;
    private String bussinessName;
    private String mainRoute;
    private String spareRoute;
    private ChannelDTO mainChannel;
    private ChannelDTO spareChannel;

    public BussinessDTO() {
    }

    public BussinessDTO(ResBussiness bussiness) {
        this.bussinessId = bussiness.getBussinessId();
        this.bussinessName = bussiness.getBussinessName();
        this.mainRoute = bussiness.getMainRoute();
        this.spareRoute = bussiness.getSpareRoute();
    }

    public void checkBussinessDTO() {
        if (null == bussinessName) {
            throw new NullArgumentException("bussinessName");
        }
        if (null == mainRoute) {
            throw new NullArgumentException("mainRoute");
        }
        if (null == mainChannel) {
            throw new NullArgumentException("mainChannel");
        } else {
            mainChannel.checkChannelDTO();
        }
        if (null != spareRoute) {
            if (null == spareChannel) {
                throw new NullArgumentException("spareChannel");
            } else {
                spareChannel.checkChannelDTO();
            }
        }
    }

    public ChannelDTO getMainChannel() {
        return mainChannel;
    }

    public void setMainChannel(ChannelDTO mainChannel) {
        this.mainChannel = mainChannel;
    }

    public ChannelDTO getSpareChannel() {
        return spareChannel;
    }

    public void setSpareChannel(ChannelDTO spareChannel) {
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
