package com.bupt.facade.impl;

import com.bupt.entity.ResBussiness;
import com.bupt.facade.BussinessService;
import com.bupt.facade.OSNRCalculator.Calculable;
import com.bupt.facade.OSNRService;
import com.bupt.pojo.*;
import com.bupt.service.DiskService;
import com.bupt.service.LinkService;
import com.bupt.service.NetElementService;
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

    /*考虑到设计简单的原则，输入输出功率是以字符串的形式存储在数据库的，这里需要转换
    */
    static double[][] stringTransfer(String powerString) {
        if (null == powerString || powerString.equals("[]")) {
            return new double[0][0];
        }
        if (powerString.contains("[")) {
            //对二维矩阵转换成的字符串进行操作
            String[] powerArrays = powerString.replace("]", ">").replace("[", "<").split(">, <");
            double[][] results = new double[powerArrays.length][];
            for (int i = 0; i < powerArrays.length; i++) {
                String[] powers = powerArrays[i].replace("<", "").replace(">", "").split(",");
                results[i] = new double[powers.length];
                for (int j = 0; j < results[i].length; j++) {
                    results[i][j] = Double.parseDouble(powers[j]);
                }
            }
            return results;
        } else {
            double inputPower = Double.parseDouble(powerString);
            double[][] result = new double[1][1];
            result[0][0] = inputPower;
            return result;
        }

    }

    @Override
    public List<BussinessDTO> listErrorBussiness(Long versionId) {
        return bussinessService.listBussiness(versionId).stream().filter(bussinessDTO ->
                stringTransfer(getBussiness(versionId, bussinessDTO.getBussinessId()).getMainInputPowers()).length <
                        bussinessDTO.getMainRoute().split("-").length
                        || ((null != bussinessDTO.getSpareRoute())
                        && stringTransfer(getBussiness(versionId, bussinessDTO.getBussinessId()).getSpareInputPowers
                        ()).length < bussinessDTO.getSpareRoute().split("-").length)
        ).collect(Collectors.toList());
    }

    @Override
    public List<NodeOSNRDetail> getNodeOSNRDetail(Long versionId, Long bussinessId) {
        Set<NodeOSNRDetail> results = new HashSet<>();
        ResBussiness bus = getBussiness(versionId, bussinessId);
        double[][] inputPowers = stringTransfer(bus.getMainInputPowers());
        double[][] outputPowers = stringTransfer(bus.getMainOutputPowers());
        String routeString = bus.getMainRoute();
        try {
            calculator.calculate(inputPowers, outputPowers, routeString, versionId);
        } catch (Exception e) {
            //do nothing
        }
        if (null != calculator.getNodeResults()) {
            results.addAll(calculator.getNodeResults());
        }
        if (null != bus.getSpareRoute()) {
            inputPowers = stringTransfer(bus.getSpareInputPowers());
            outputPowers = stringTransfer(bus.getSpareOutputPowers());
            routeString = bus.getSpareRoute();
            try {
                calculator.calculate(inputPowers, outputPowers, routeString, versionId);
            } catch (Exception e) {
                //do nothing
            }
            if (null != calculator.getNodeResults()) {
                results.addAll(calculator.getNodeResults());
            }
        }
        return new ArrayList<>(results);
    }

    @Override
    public List<RouteOSNRDetail> getRouteOSNRDetail(Long versionId, Long bussinessId) {
        ResBussiness bus = getBussiness(versionId, bussinessId);
        List<RouteOSNRDetail> results = new ArrayList<>();
        results.add(new RouteOSNRDetail(bus, true, getRealRouteString(bus, true)));
        if (null != bus.getSpareRoute()) {
            results.add(new RouteOSNRDetail(bus, false, getRealRouteString(bus, false)));
        }
        return results;
    }

    private String getRealRouteString(ResBussiness bus, boolean isMain) {
        String[] nodes = isMain ? bus.getMainRoute().split("-") : bus.getSpareRoute().split("-");
        StringBuilder results = new StringBuilder("");
        for (int i = 0; i < stringTransfer(isMain ? bus.getMainInputPowers() : bus.getSpareInputPowers()).length; i++) {
            results.append(nodes[i]);
            results.append("-");
        }
        return results.substring(0, results.lastIndexOf("-"));
    }

    @Override
    public List<ResultOSNRDetail> getOSNRResult(Long versionId, Long bussinessId, Boolean isMain) {
        ResBussiness bus = getBussiness(versionId, bussinessId);
        double[][] inputPowers = isMain ? stringTransfer(bus.getMainInputPowers()) : stringTransfer(bus
                .getSpareInputPowers());
        double[][] outputPowers = isMain ? stringTransfer(bus.getMainOutputPowers()) : stringTransfer(bus
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

    private List<ResultOSNRDetail> resultGenerator(Long versionId, Boolean isMain, ResBussiness bus, String
            routeString, String errorMessage, List<OSNRResult> calculatorResult) {
        List<ResultOSNRDetail> results = new LinkedList<>();
        for (int i = 0; i < routeString.split("-").length; i++) {
            if (null == calculatorResult) {
                if (null == errorMessage) {
                    errorMessage = getErrorMessage(versionId, routeString.split("-")[0], routeString.split("-")[1]);
                }
                results.add(new ResultOSNRDetail(bus, isMain, routeString.split("-")[0],
                        routeString.split("-")[i], errorMessage));
            } else {
                if (i < calculatorResult.size()) {
                    results.add(new ResultOSNRDetail(bus, isMain, calculatorResult.get(0).getNetElementName(),
                            calculatorResult.get(i)));
                } else if (null != errorMessage) {
                    results.add(new ResultOSNRDetail(bus, isMain, calculatorResult.get(0).getNetElementName(),
                            routeString.split("-")[i], errorMessage));
                } else {
                    errorMessage = getErrorMessage(versionId, routeString.split("-")[i - 1], routeString.split("-")[i]);
                    results.add(new ResultOSNRDetail(bus, isMain, calculatorResult.get(0).getNetElementName(),
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
        } else return "不满足" + nodeName2 + "中的机盘的输入范围";
    }

    private ResBussiness getBussiness(Long versionId, Long bussinessId) {
        return bussinessService.getBussiness(versionId, bussinessId);
    }


}
