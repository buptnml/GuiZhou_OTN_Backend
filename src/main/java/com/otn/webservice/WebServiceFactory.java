package com.otn.webservice;

import com.otn.entity.*;
import com.otn.service.LinkTypeService;
import com.otn.webservice.com.Strategy;
import com.otn.webservice.com.StrategyLocal;
import com.otn.webservice.com.StrategyRemote;
import com.otn.webservice.com.pojo.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Component
@DependsOn("WebServiceConfigInfo")
public class WebServiceFactory {
    private WebServiceConfigInfo CONFIG;
    private Strategy strategy;

    @Resource
    LinkTypeService linkTypeService;

    @Autowired
    public WebServiceFactory(WebServiceConfigInfo config) {
        this.CONFIG = config;
        strategy = config.getUSE_WEBSERVICE() ? new StrategyRemote() : new StrategyLocal();
    }


    public String uploadFile(String data) {
        return strategy.uploadFile(data);
    }

    public List<ResBussiness> listRemoteBusDataRaw() {
        List<RawBussinessData> rawList = strategy.getRawBusData();
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
            res.setVersionId(CONFIG.getBASIC_VERSION_ID());
            res.setBussinessId(null);
            res.setMainRoute(routeStringConverter(res.getMainRoute()));
            res.setMainInputPowers(powerStringConverter(res.getMainInputPowers()));
            res.setMainOutputPowers(powerStringConverter(res.getMainOutputPowers()));
            res.setMainFrequency(Double.toString(Math.random() * 100).substring(0, 4));
            res.setBussinessRate(CONFIG.getDEFAULT_BUSSINESS_RATE());
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
        return "[[" + StringUtils.replaceEach(StringUtils.trim(powerString), new String[]{"、", "无数据"}, new String[]{"]," +
                "[", "0"}) + "]]";
    }

    private String routeStringConverter(String routeString) {
        String res = StringUtils.trim(routeString);
        String[] target = {"917-信息中心、901中调", "917-信息中心-901-中调", "712-黔北电厂-701-毕节变",
                "\\b07-鸭溪变、706-遵义地调、", "\\b07-鸭溪变、706-遵义地调", "703-贵阳变、715-息烽变2-707-鸭溪变",
                "\\b701-毕节变、707-鸭溪变、712-黔北电厂\\b"};
        String[] destination = {"917-信息中心、901-中调", "917-信息中心、901-中调", "712-黔北电厂、701-毕节变", "707-鸭溪变、706-遵义地调",
                "707-鸭溪变、706-遵义地调", "703-贵阳变、715-息烽变2、707-鸭溪变", "701-毕节变、712-黔北电厂、707-鸭溪变"};
        res = StringUtils.replaceEach(res, target, destination);
        res = StringUtils.replace(res, "-", "_");
        return StringUtils.replace(res, "、", "-");
    }


    public List<ResDisk> listRemoteDiskRaw() {
        List<RawAmpData> rawList = strategy.getRawAmpData();
        return rawList.stream().distinct().map(rawData -> {
            ResDisk res = new ResDisk();
            BeanUtils.copyProperties(rawData, res);
            res.setVersionId(CONFIG.getBASIC_VERSION_ID());
            res.setSlotId(1L);
            res.setDiskName(rawData.getDiskName().replace("-", "_") + "_1槽_1盘");
            return res;
        }).collect(Collectors.toList());
    }

    public List<ResLink> listRemoteLinkRaw() {
        List<RawLinkData> rawList = strategy.getRawLinkData();
        List<RawNetElementData> ralList2 = strategy.getRawNetElementData();
        return rawList.stream().map(rawData -> {
            ResLink res = new ResLink();
            BeanUtils.copyProperties(rawData, res);
            res.setVersionId(CONFIG.getBASIC_VERSION_ID());
            res.setEndAName(res.getEndAName().trim().replace("-", "_"));
            res.setEndZName(res.getEndZName().trim().replace("-", "_"));
            res.setLinkName(res.getEndAName() + "-" + res.getEndZName());
            if (res.getLinkType().equals("")) res.setLinkType(CONFIG
                    .getDEFAULT_LINK_TYPE());
            ralList2.forEach(netElement -> {
                if (res.getEndAId().equals(netElement.getNetElementId())) res.setCircleId
                        (netElement.getCircleId());
            });
            if (res.getLinkLength() == null) res.setLinkLength(CONFIG.getDEFAULT_LINK_LENGTH());
            if (res.getLinkLoss() == null) res.setLinkLoss((float) linkTypeService.calculateLoss(100000000373L, "OPGW",
                    res.getLinkLength()));
            return res;
        }).collect(Collectors.toList());
    }

    public List<ResNetElement> listRemoteNetElementRaw() {
        List<RawNetElementData> rawList = strategy.getRawNetElementData();
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
            if (res.getNetElementType() == null) res.setNetElementType(CONFIG.getDEFAULT_NET_ELEMENT_TYPE());
            res.setVersionId(CONFIG.getBASIC_VERSION_ID());
            return res;
        }).collect(Collectors.toList());
    }


    public List<ResOsnrAmplifier> listRemoteAmpRaw() {
        List<RawAmpData> rawList = strategy.getRawAmpData();
        return rawList.stream().map(rawData -> {
            ResOsnrAmplifier res = new ResOsnrAmplifier();
            BeanUtils.copyProperties(rawData, res);
            res.setGain(rawData.getGain());
            res.setVersionId(CONFIG.getBASIC_VERSION_ID());
            return res;
        }).distinct().collect(Collectors.toList());
    }

}
