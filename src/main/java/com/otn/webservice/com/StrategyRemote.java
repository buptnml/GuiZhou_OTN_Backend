package com.otn.webservice.com;

import com.otn.webservice.com.inspur.ResourceServiceDelegate;
import com.otn.webservice.com.inspur.ResourceServiceService;
import com.otn.webservice.com.pojo.RawAmpData;
import com.otn.webservice.com.pojo.RawBussinessData;
import com.otn.webservice.com.pojo.RawLinkData;
import com.otn.webservice.com.pojo.RawNetElementData;

import java.util.List;


public class StrategyRemote implements Strategy {
    static XMLConverter XML_CONVERTER = new XMLConverter();
    ResourceServiceService RS = new ResourceServiceService();
    ResourceServiceDelegate WS = RS.getResourceServicePort();

    @Override
    public List<RawLinkData> getRawLinkData() {
        return XML_CONVERTER.getData(WS.getAllEquipLink(), XMLConverter.REQUEST_TYPES.link);
    }

    @Override
    public List<RawAmpData> getRawAmpData() {
        return XML_CONVERTER.getData(WS.getAllEquipCardByAmp(), XMLConverter.REQUEST_TYPES.amp);
    }

    @Override
    public List<RawBussinessData> getRawBusData() {
        return XML_CONVERTER.getData(WS.getChannel(), XMLConverter.REQUEST_TYPES.bussiness);
    }

    @Override
    public List<RawNetElementData> getRawNetElementData() {
        return XML_CONVERTER.getData(WS.getAllEquip(), XMLConverter.REQUEST_TYPES
                .netElement);
    }
}
