package com.otn.webservice.com;

import com.otn.webservice.com.inspur.ResourceServiceDelegate;
import com.otn.webservice.com.inspur.ResourceServiceService;
import com.otn.webservice.com.pojo.RawAmpData;
import com.otn.webservice.com.pojo.RawBussinessData;
import com.otn.webservice.com.pojo.RawLinkData;
import com.otn.webservice.com.pojo.RawNetElementData;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;


public class StrategyRemote implements Strategy {
    private static XMLConverter XML_CONVERTER = new XMLConverter();
    private static volatile Future<ResourceServiceDelegate> WS = (Future<ResourceServiceDelegate>) Executors
            .newSingleThreadExecutor().submit(new FutureTask<ResourceServiceDelegate>(() -> new ResourceServiceService()
                    .getResourceServicePort()));



    @Override
    public List<RawLinkData> getRawLinkData() {
        try {
            return XML_CONVERTER.getData(WS.get().getAllEquipLink(), XMLConverter.REQUEST_TYPES.link);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<RawAmpData> getRawAmpData() {
        try {
            return XML_CONVERTER.getData(WS.get().getAllEquipCardByAmp(), XMLConverter.REQUEST_TYPES.amp);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<RawBussinessData> getRawBusData() {
        try {
            return XML_CONVERTER.getData(WS.get().getChannel(), XMLConverter.REQUEST_TYPES.bussiness);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<RawNetElementData> getRawNetElementData() {
        try {
            return XML_CONVERTER.getData(WS.get().getAllEquip(), XMLConverter.REQUEST_TYPES
                    .netElement);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return null;

    }

}
