package com.otn.webservice.com.pojo;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "data")
//@XmlSeeAlso(TestOrg.class)
public class WrappedXml { // 泛化, 聚合


    private List<RawLinkData> linkDataList;
    private List<RawAmpData> ampDataList;
    private List<RawBussinessData> bussinessDataList;
    private List<RawNetElementData> netElementDataList;

    @XmlElement(name = "card")
    public List<RawAmpData> getAmpDataList() {
        return ampDataList;
    }

    public void setAmpDataList(List<RawAmpData> ampDataList) {
        this.ampDataList = ampDataList;
    }

    @XmlElement(name = "channel")
    public List<RawBussinessData> getBussinessDataList() {
        return bussinessDataList;
    }

    public void setBussinessDataList(List<RawBussinessData> bussinessDataList) {
        this.bussinessDataList = bussinessDataList;
    }

    @XmlElement(name = "equip")
    public List<RawNetElementData> getNetElementDataList() {
        return netElementDataList;
    }

    public void setNetElementDataList(List<RawNetElementData> netElementDataList) {
        this.netElementDataList = netElementDataList;
    }

    @XmlElement(name = "link")
    public List<RawLinkData> getlinkDataList() {
        return linkDataList;
    }


    public void setlinkDataList(List<RawLinkData> linkDataList) {
        this.linkDataList = linkDataList;
    }


}
