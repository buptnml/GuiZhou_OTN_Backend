package com.otn.facade.impl;

import com.otn.entity.ResBussiness;
import com.otn.facade.BussinessService;
import com.otn.facade.OSNRCalculator.Calculable;
import com.otn.facade.OSNRService;
import com.otn.facade.util.BussinessPowerStringTransfer;
import com.otn.pojo.*;
import com.otn.service.DiskService;
import com.otn.service.LinkService;
import com.otn.service.NetElementService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

@Service("OSNRService")
class OSNRServiceImpl implements OSNRService {
    @Resource
    private Calculable calculator;
    @Resource
    private BussinessService bussinessService;
    @Resource
    private LinkService linkService;
    @Resource
    private DiskService diskService;
    @Resource
    private NetElementService netElementService;

    @Override
    public List<BussinessDTO> listErrorBussiness(Long versionId) {
        return bussinessService.listBussiness(versionId).parallelStream().filter(bussinessDTO ->
                (BussinessPowerStringTransfer.stringTransfer(getBussiness(versionId, bussinessDTO.getBussinessId())
                        .getMainInputPowers()).length < bussinessDTO.getMainRoute().split("-").length)
                        || ((null != bussinessDTO.getSpareRoute())
                        && BussinessPowerStringTransfer.stringTransfer(getBussiness(versionId, bussinessDTO.getBussinessId()).getSpareInputPowers
                        ()).length < bussinessDTO.getSpareRoute().split("-").length)
        ).collect(Collectors.toList());
    }

    @Override
    public List<BussinessDTO> listErrorBussiness(Long versionId, String circleId) {
        return listErrorBussiness(versionId).parallelStream().filter(bus -> bus.getCircleId().equals(circleId)).collect
                (Collectors.toList());
    }

    @Override
    //获取所有OSNR计算出来的设备的增益，输入，输出，内部噪声等等信息，主备路由一起计算，计算出来多少返回多少，没有顺序
    public List<OSNRNodesDetails> getNodeOSNRDetail(Long versionId, Long bussinessId) {
        Set<OSNRNodesDetails> results = new HashSet<>();
        ResBussiness bus = getBussiness(versionId, bussinessId);
        results.addAll(getNodeResults(
                BussinessPowerStringTransfer.stringTransfer(bus.getMainInputPowers()),
                BussinessPowerStringTransfer.stringTransfer(bus.getMainOutputPowers()),
                bus.getMainRoute(),
                versionId));
        if (null != bus.getSpareRoute()) {
            results.addAll(getNodeResults(
                    BussinessPowerStringTransfer.stringTransfer(bus.getSpareInputPowers()),
                    BussinessPowerStringTransfer.stringTransfer(bus.getSpareOutputPowers()),
                    bus.getSpareRoute(),
                    versionId));

        }
        return new ArrayList<>(results);
    }

    private List<OSNRNodesDetails> getNodeResults(double[][] inputPowers, double[][] outputPowers, String routeString,
                                                  Long versionId) {
        try {
            calculator.calculate(inputPowers, outputPowers, routeString, versionId);
        } catch (Exception ignore) {  //do nothing
        }
        return calculator.getNodeResults() == null ? new ArrayList<>() : calculator.getNodeResults();
    }

    @Override
    public List<OSNRGeneralInfo> getRouteOSNRDetail(Long versionId, Long bussinessId) {
        ResBussiness bus = getBussiness(versionId, bussinessId);
        List<OSNRGeneralInfo> results = new ArrayList<>();
        results.add(new OSNRGeneralInfo(bus, true, getRealRouteString(bus, true, getOSNRResult(versionId,
                bussinessId, true))));
        if (null != bus.getSpareRoute()) {
            results.add(new OSNRGeneralInfo(bus, false, getRealRouteString(bus, false, getOSNRResult(versionId,
                    bussinessId, false))));
        }
        return results;
    }

    private String getRealRouteString(ResBussiness bus, boolean isMain, List<OSNRDetailInfo> details) {
        String[] nodes = isMain ? bus.getMainRoute().split("-") : bus.getSpareRoute().split("-");
        StringBuilder results = new StringBuilder("");
        for (int i = 0; i < BussinessPowerStringTransfer.stringTransfer(isMain ? bus.getMainInputPowers() : bus.getSpareInputPowers()).length; i++) {
            if (!details.get(i).getResult().contains("小于18dB")) {
                results.append(nodes[i]);
                results.append("-");
            }
        }
        return results.length() == 0 ? "" : results.substring(0, results.lastIndexOf("-"));
    }

    @Override
    public List<OSNRDetailInfo> getOSNRResult(Long versionId, Long bussinessId, Boolean isMain) {
        ResBussiness bus = getBussiness(versionId, bussinessId);
        double[][] inputPowers = isMain ? BussinessPowerStringTransfer.stringTransfer(bus.getMainInputPowers()) : BussinessPowerStringTransfer.stringTransfer(bus
                .getSpareInputPowers());
        double[][] outputPowers = isMain ? BussinessPowerStringTransfer.stringTransfer(bus.getMainOutputPowers()) : BussinessPowerStringTransfer.stringTransfer(bus
                .getSpareOutputPowers());
        String routeString = isMain ? bus.getMainRoute() : bus.getSpareRoute();
        String errorMessage = null;
        try {
            calculator.calculate(inputPowers, outputPowers, routeString, versionId);
        } catch (Exception e) {
            errorMessage = e.getMessage();
        }
        return resultGenerator(versionId, isMain, bus, routeString, errorMessage, calculator.getResult());
    }

    @Override
    public synchronized void OSNRLegalCheck(Long versionId, OSNRLegalCheckRequest osnrLegalCheckRequest) {
        calculator.calculate(osnrLegalCheckRequest.getInputPower(), osnrLegalCheckRequest.getRouteString(), versionId);
    }


    private List<OSNRDetailInfo> resultGenerator(Long versionId, Boolean isMain, ResBussiness bus, String
            routeString, String errorMessage, List<OSNRResult> calculatorResult) {
        List<OSNRDetailInfo> results = new LinkedList<>();
        for (int i = 0; i < routeString.split("-").length; i++) {
            if (null == calculatorResult) {
                if (null == errorMessage) {
                    errorMessage = getErrorMessage(versionId, routeString.split("-")[0], routeString.split("-")[1]);
                }
                results.add(new OSNRDetailInfo(bus, isMain, routeString.split("-")[0],
                        routeString.split("-")[i], errorMessage));
            } else {
                if (i < calculatorResult.size()) {
                    results.add(new OSNRDetailInfo(bus, isMain, calculatorResult.get(0).getNetElementName(),
                            calculatorResult.get(i)));
                } else if (null != errorMessage) {
                    results.add(new OSNRDetailInfo(bus, isMain, calculatorResult.get(0).getNetElementName(),
                            routeString.split("-")[i], errorMessage));
                } else {
                    errorMessage = getErrorMessage(versionId, routeString.split("-")[i - 1], routeString.split("-")[i]);
                    results.add(new OSNRDetailInfo(bus, isMain, calculatorResult.get(0).getNetElementName(),
                            routeString.split("-")[i], errorMessage));
                }
            }
        }
        return results;
    }

    private String getErrorMessage(Long versionId, String nodeName1, String nodeName2) {
        if (null == linkService.getLinkByNodes(versionId, nodeName1, nodeName2)) {
            return "光路中断";
        } else if (diskService.listDiskByNetElement(versionId, netElementService.getNetElement(versionId, nodeName1)
                .getNetElementId()).size() == 0) {
            return nodeName1 + "中没有机盘";
        } else return "不满足" + nodeName2 + "中的放大器的输入范围";
    }

    private ResBussiness getBussiness(Long versionId, Long bussinessId) {
        return bussinessService.getBussiness(versionId, bussinessId);
    }


}
