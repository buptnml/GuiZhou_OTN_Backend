package com.otn.webservice.com;

import com.otn.webservice.com.pojo.RawAmpData;
import com.otn.webservice.com.pojo.RawBussinessData;
import com.otn.webservice.com.pojo.RawLinkData;
import com.otn.webservice.com.pojo.RawNetElementData;

import java.util.List;


public class StrategyLocal implements Strategy {
    static XMLConverter XML_CONVERTER = new XMLConverter();
    String filepath = Thread.currentThread().getContextClassLoader().getResource("").getPath();

    @Override
    public List<RawLinkData> getRawLinkData() {
        return XML_CONVERTER.getData(filepath + "testSamples/link.txt", XMLConverter
                .REQUEST_TYPES.link, "file");
    }

    @Override
    public List<RawAmpData> getRawAmpData() {
        return XML_CONVERTER.getData(filepath + "testSamples/amplifier.txt", XMLConverter
                .REQUEST_TYPES.amp, "file");
    }

    @Override
    public List<RawBussinessData> getRawBusData() {
        return XML_CONVERTER.getData(filepath + "testSamples/bussiness.txt", XMLConverter
                .REQUEST_TYPES.bussiness, "file");
    }

    @Override
    public List<RawNetElementData> getRawNetElementData() {
        return XML_CONVERTER.getData(filepath + "testSamples/netElement.txt", XMLConverter
                .REQUEST_TYPES
                .netElement, "file");
    }
}
