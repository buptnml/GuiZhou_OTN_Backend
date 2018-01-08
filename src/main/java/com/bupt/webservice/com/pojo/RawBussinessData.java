package com.bupt.webservice.com.pojo;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;


@XmlAccessorType(XmlAccessType.FIELD)
public class RawBussinessData {
    @XmlElement(name = "channel_id")
    private Long bussinessId;
    @XmlElement(name = "channel_name")
    private String bussinessName;
    @XmlElement(name = "main_route")
    private String mainRoute;
    @XmlElement(name = "main_opl_in")
    private String mainInputPowers;
    @XmlElement(name = "main_opl_out")
    private String mainOutputPowers;
    @XmlElement(name = "spare_route")
    private String spareRoute;
    @XmlElement(name = "spare_opl_in")
    private String spareInputPowers;
    @XmlElement(name = "spare_opl_out")
    private String spareOutputPowers;


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

    public String getMainInputPowers() {
        return mainInputPowers;
    }

    public void setMainInputPowers(String mainInputPowers) {
        this.mainInputPowers = mainInputPowers;
    }

    public String getMainOutputPowers() {
        return mainOutputPowers;
    }

    public void setMainOutputPowers(String mainOutputPowers) {
        this.mainOutputPowers = mainOutputPowers;
    }

    public String getSpareRoute() {
        return spareRoute;
    }

    public void setSpareRoute(String spareRoute) {
        this.spareRoute = spareRoute;
    }

    public String getSpareInputPowers() {
        return spareInputPowers;
    }

    public void setSpareInputPowers(String spareInputPowers) {
        this.spareInputPowers = spareInputPowers;
    }

    public String getSpareOutputPowers() {
        return spareOutputPowers;
    }

    public void setSpareOutputPowers(String spareOutputPowers) {
        this.spareOutputPowers = spareOutputPowers;
    }


}
