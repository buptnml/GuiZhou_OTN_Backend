package com.webservice;

import com.entity.*;
import com.webservice.com.XMLConverter;
import com.webservice.com.pojo.*;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class WebServiceDataFactory {
    static XMLConverter XML_CONVERTER = new XMLConverter();
    String filepath = Thread.currentThread().getContextClassLoader().getResource("").getPath();

//        static ResourceServiceService RS = new ResourceServiceService();
//        static ResourceServiceDelegate WS = RS.getResourceServicePort();
@Resource
private WebServiceConfigInfo webServiceConfigInfo;

    public List<ResBussiness> listRemoteBusData() {
//        List<RawBussinessData> rawList = XML_CONVERTER.getData(WS.getChannel(),XMLConverter.REQUEST_TYPES.bussiness);
        System.out.println("listRemoteBusData:" + filepath + "testSamples/bussiness.txt");
        List<RawBussinessData> rawList = XML_CONVERTER.getData(filepath + "testSamples/bussiness.txt", XMLConverter
                .REQUEST_TYPES.bussiness, "file");
        return rawList.parallelStream().distinct().map(
                rawData -> {
                    if (rawData.getSpareRoute().equals("")) {
                        rawData.setSpareRoute(null);
                        rawData.setSpareInputPowers(null);
                        rawData.setSpareOutputPowers(null);
                    }
                    return rawData;
                }).map(rawData -> {
            ResBussiness res = new ResBussiness();
            BeanUtils.copyProperties(rawData, res);
            res.setVersionId(webServiceConfigInfo.getBASIC_VERSION_ID());
            res.setMainRoute(routeStringConverter(res.getMainRoute()));
            res.setMainInputPowers(powerStringConverter(res.getMainInputPowers()));
            res.setMainOutputPowers(powerStringConverter(res.getMainOutputPowers()));
            res.setMainFrequency(Double.toString(Math.random() * 100).substring(0, 4));
            res.setBussinessRate(webServiceConfigInfo.getDEFAULT_BUSSINESS_RATE());
            if (res.getSpareRoute() != null) {
                res.setSpareRoute(routeStringConverter(res.getSpareRoute()));
                res.setSpareInputPowers(powerStringConverter(res.getSpareInputPowers()));
                res.setSpareOutputPowers(powerStringConverter(res.getSpareOutputPowers()));
                res.setSpareFrequency(Double.toString(Math.random() * 100).substring(0, 4));
            }
            return res;
        }).collect(Collectors.toList());
    }


    private String powerStringConverter(String powerString) {
        if (powerString.equals("")) return "[[0]]";
        return "[[" + powerString.trim().replace("、", "],[").replace("无数据", "0") + "]]";
    }

    private String routeStringConverter(String routeString) {
        return routeString.trim().
                replace("917-信息中心、901中调", "917-信息中心、901-中调").
                replace("917-信息中心-901-中调", "917-信息中心、901-中调").
                replace("712-黔北电厂-701-毕节变", "712-黔北电厂、701-毕节变").
                replaceAll("\\b07-鸭溪变、706-遵义地调、", "707-鸭溪变、706-遵义地调").
                replaceAll("\\b07-鸭溪变、706-遵义地调", "707-鸭溪变、706-遵义地调").
                replace("703-贵阳变、715-息烽变2-707-鸭溪变", "703-贵阳变、715-息烽变2、707-鸭溪变").
                replaceAll("\\b701-毕节变、707-鸭溪变、712-黔北电厂\\b", "701-毕节变、712-黔北电厂、707-鸭溪变")
                .replace("-", "_").replace("、", "-");
    }

    public List<ResDisk> listRemoteDisk() {
//        List<RawAmpData> rawList = XML_CONVERTER.getData(WS.getAllEquipCardByAmp(),XMLConverter.REQUEST_TYPES.amp);
        System.out.println("listRemoteDisk:" + filepath + "testSamples/amplifier.txt");
        List<RawAmpData> rawList = XML_CONVERTER.getData(filepath + "testSamples/amplifier.txt", XMLConverter
                .REQUEST_TYPES.amp, "file");
        return rawList.stream().distinct().map(rawData -> {
            ResDisk res = new ResDisk();
            BeanUtils.copyProperties(rawData, res);
            res.setVersionId(webServiceConfigInfo.getBASIC_VERSION_ID());
            res.setSlotId(1L);
            res.setDiskName(rawData.getDiskName().replace("-", "_") + "_1槽_1盘");
            return res;
        }).collect(Collectors.toList());
    }

    public List<ResLink> listRemoteLink() {
//        List<RawLinkData> rawList = XML_CONVERTER.getData(WS.getAllEquipLink(),XMLConverter.REQUEST_TYPES.link);
        System.out.println("listRemoteLink:" + filepath + "testSamples/link.txt");
        List<RawLinkData> rawList = XML_CONVERTER.getData(filepath + "testSamples/link.txt", XMLConverter
                        .REQUEST_TYPES.link,
                "file");
        return rawList.stream().distinct().map(rawData -> {
            ResLink res = new ResLink();
            BeanUtils.copyProperties(rawData, res);
            res.setVersionId(webServiceConfigInfo.getBASIC_VERSION_ID());
            res.setEndAName(res.getEndAName().trim().replace("-", "_"));
            res.setEndZName(res.getEndZName().trim().replace("-", "_"));
            res.setLinkName(res.getEndAName() + "-" + res.getEndZName());
            if (res.getLinkType().equals("")) res.setLinkType(webServiceConfigInfo
                    .getDEFAULT_LINK_TYPE());
            if (res.getLinkLoss() == null) res.setLinkLoss(webServiceConfigInfo.getDEFAULT_LINK_LOSS());
            if (res.getLinkLength() == null) res.setLinkLength(webServiceConfigInfo.getDEFAULT_LINK_LENGTH());
            return res;
        }).collect(Collectors.toList());
    }

    public List<ResNetElement> listRemoteNetElement() {
//        List<RawNetElementData> rawList = XML_CONVERTER.getData(WS.getAllEquip(), XMLConverter.REQUEST_TYPES
//                .netElement);
        System.out.println("listRemoteNetElement:" + filepath + "testSamples/netElement.txt");
        List<RawNetElementData> rawList = XML_CONVERTER.getData(filepath + "testSamples/netElement.txt", XMLConverter
                .REQUEST_TYPES
                .netElement, "file");
        final Float minX = Math.abs(rawList.stream().min(Comparator.comparing(rawData -> rawData
                .getCoordinateX())).get().getCoordinateX());
        final Float minY = Math.abs(rawList.stream().min(Comparator.comparing(rawData -> rawData
                .getCoordinateY())).get().getCoordinateY());
        return rawList.stream().distinct().map(rawData -> {
            //正规化
            rawData.setCoordinateX(rawData.getCoordinateX() + minX);
            rawData.setCoordinateY(rawData.getCoordinateY() + minY);
            rawData.setNetElementName(rawData.getNetElementName().replace("-", "_"));
            return rawData;
        }).map(rawData -> {
            //格式转化
            ResNetElement res = new ResNetElement();
            BeanUtils.copyProperties(rawData, res);
            res.setNetElementType(webServiceConfigInfo.getDEFAULT_NET_ELEMENT_TYPE());
            res.setVersionId(webServiceConfigInfo.getBASIC_VERSION_ID());
            return res;
        }).collect(Collectors.toList());
    }

    public List<ResOsnrAmplifier> listRemoteAmp() {
//        List<RawAmpData> rawList = XML_CONVERTER.getData(WS.getAllEquipCardByAmp(),XMLConverter.REQUEST_TYPES.amp);
        System.out.println("listRemoteAmp:" + filepath + "testSamples\\amplifier.txt");
        List<RawAmpData> rawList = XML_CONVERTER.getData(filepath + "testSamples\\amplifier.txt", XMLConverter
                .REQUEST_TYPES
                .amp, "file");
        return rawList.stream().map(rawData -> {
            ResOsnrAmplifier res = new ResOsnrAmplifier();
            BeanUtils.copyProperties(rawData, res);
            res.setGain(rawData.getGain());
            res.setVersionId(webServiceConfigInfo.getBASIC_VERSION_ID());
            return res;
        }).distinct().collect(Collectors.toList());
    }

}
