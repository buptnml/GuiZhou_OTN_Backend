package com.otn.service.impl;

import com.otn.dao.ResBussinessDao;
import com.otn.dao.ResLinkDao;
import com.otn.dao.ResNetElementDao;
import com.otn.entity.ResBussiness;
import com.otn.entity.ResLink;
import com.otn.entity.ResNetElement;
import com.otn.pojo.NXAnalyseItemDTO;
import com.otn.service.NXAnalyseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by zhangminchao on 2017/10/23.
 */

@Service
class NXAnalyseServiceImpl implements NXAnalyseService {

    @Autowired
    private ResNetElementDao netElementDao;

    @Autowired
    private ResLinkDao linkDao;

    @Autowired
    private ResBussinessDao bussinessDao;

    //用来标记分析所有设备链路时的circleId取值
    private static final String ALLGRAPH = "全图";
    /**
     * 分析所有的设备
     * @param versionId
     * @param num 故障数量，1或者2
     * @param circleId
     * @return
     */
    @Override
    public List<NXAnalyseItemDTO> analyseEquip(long versionId, int num, String circleId) {
        //筛选设备和业务时，因为可能是分析某一个环，也可能是分析全图，故在circleId上添加‘全图’字段用来标记。
        List<String> netElements = getNetElemetName(versionId, circleId);
        List<ResBussiness> bussiness = getBusiness(versionId, circleId);

        if(num == 2){
            netElements = getTwoEles(netElements, netElements);
        }
        return analyseElements(netElements, bussiness);
    }
    /**
     * 分析选定的设备
     * @param versionId
     * @param circleId
     * @param equips
     * @return
     */
    @Override
    public List<NXAnalyseItemDTO> analyseSomeEquip(long versionId, String circleId, List<String> equips){
        List<ResBussiness> bussiness = getBusiness(versionId, circleId);

        return analyseElements(equips, bussiness);
    }


    /**
     * 分析所有链接
     * @param versionId
     * @param num
     * @param circleId
     * @return
     */
    @Override
    public List<NXAnalyseItemDTO> analyseLink(long versionId, int num, String circleId) {
        List<String> links = getLinkName(versionId, circleId);
        List<ResBussiness> bussiness = getBusiness(versionId, circleId);

        if(num == 2){
            links = getTwoEles(links, links);
        }
        return analyseElements(links, bussiness);
    }

    @Override
    public List<NXAnalyseItemDTO> analyseSomeLink(long versionId, String circleId, List<String> elenemts){
        List<ResBussiness> bussiness = getBusiness(versionId,circleId);

        return analyseElements(elenemts, bussiness);
    }

    /**
     * 分析所有设备和链接的组合
     * @param versionId
     * @param num
     * @param circleId
     * @return
     */
    @Override
    public List<NXAnalyseItemDTO> analyseEquipAndLink(long versionId, int num, String circleId) {
        List<String> links = getLinkName(versionId, circleId);
        List<ResBussiness> bussinesses = getBusiness(versionId, circleId);
        List<String> netElements = getNetElemetName(versionId, circleId);

        List<String> elements = getTwoEles(links, netElements);
        return analyseElements(elements, bussinesses);
    }

    @Override
    public List<NXAnalyseItemDTO> analyseSomeEquipAndLink(long versionId, String circleId, List<String> elements){
        List<ResBussiness> bussinesses = getBusiness(versionId, circleId);

        return analyseElements(elements, bussinesses);
    }

    private List<NXAnalyseItemDTO> analyseElements(List<String> eles, List<ResBussiness> bussinesses){
        List<NXAnalyseItemDTO> result = new LinkedList<>();
        for(String ele : eles){
            List<String> strs = new ArrayList<>();
            String[] elements = ele.contains("~") ? ele.split("~") : new String[]{ele};
            for(String s : elements){
                if(s.contains("-")){
                    String[] tmp = s.split("-");
                    strs.add(tmp[1]+"-"+tmp[0]);
                }
                strs.add(s);
            }
            NXAnalyseItemDTO nxAnalyseItem = analyse(bussinesses, strs);
            nxAnalyseItem.setItemName(elements.length==2?(elements[0]+","+elements[1]): elements[0]);
            result.add(nxAnalyseItem);
        }
        result=filterData(result);
        return result;
    }

    private List<NXAnalyseItemDTO> filterData(List<NXAnalyseItemDTO> list) {
        if(list.size()>0){
            for (NXAnalyseItemDTO item:list){
                item.setAffectBussiness(item.getAffectBussiness().stream().filter(s -> s.indexOf("Client")>=0||s.indexOf("ODU")>=0).collect(Collectors.toList()));
                item.setRecoveryBussiness(item.getRecoveryBussiness().stream().filter(s -> s.indexOf("Client")>=0||s.indexOf("ODU")>=0).collect(Collectors.toList()));
                DecimalFormat decimalFormat = new DecimalFormat("0.00%");
                if (item.getAffectBussiness().size() == 0)
                    item.setRecoveryRate("100.00%");
                else {
                    double recoveryRate = (double) item.getRecoveryBussiness().size() / item.getAffectBussiness().size();
                    item.setRecoveryRate(decimalFormat.format(recoveryRate));
                }
            }
        }
        return list;
    }

    private List<String> getTwoEles(List<String> eles1, List<String> eles2){
        List<String> elements = new ArrayList<>();
        for(String ele : eles1){
            for(String ele2 : eles2){
                if(ele.equals(ele2)){
                    break;
                }
                elements.add(ele+"~"+ele2);
            }
        }
        return elements;
    }

    private NXAnalyseItemDTO analyse(List<ResBussiness> resBussinesses,List<String> items) {
        NXAnalyseItemDTO nxAnalyseItem = new NXAnalyseItemDTO();
        //DecimalFormat decimalFormat = new DecimalFormat("0.00%");

        List<String> affectBusiness = new ArrayList<>();
        List<String> recoveryBusiness = new ArrayList<>();
        nxAnalyseItem.setAffectBussiness(affectBusiness);
        nxAnalyseItem.setRecoveryBussiness(recoveryBusiness);
        //遍历每个业务
        for (ResBussiness resBussiness : resBussinesses) {
            String mainRoute = resBussiness.getMainRoute();
            String spareRoute = resBussiness.getSpareRoute();
            boolean affectFlag = false;
            boolean recFlag = true;
//            要是主路由里找到某一个故障设备或链路则业务会受影响
            for (String item : items) {
                if (mainRoute.contains(item)) {
                    affectBusiness.add(resBussiness.getBussinessName());
                    affectFlag = true;
                    break;
                }
            }
//            在主路由里找到某个故障设备或链路后备用路由不存在
//            或者备用路由里也有某个故障设备或链路，则判定为不可恢复
            if (affectFlag) {
                for (String item : items) {
                    if (spareRoute == null || spareRoute.contains(item)) {
                        recFlag = false;
                        break;
                    }
                }
                if (recFlag) recoveryBusiness.add(resBussiness.getBussinessName());
            }
        }
//        计算恢复率
//        if (affectBusiness.size() == 0)
//            nxAnalyseItem.setRecoveryRate("100.00%");
//        else {
//            double recoveryRate = (double) recoveryBusiness.size() / affectBusiness.size();
//            nxAnalyseItem.setRecoveryRate(decimalFormat.format(recoveryRate));
//        }
        return nxAnalyseItem;
    }

    private List<ResBussiness> getBusiness(long versionId, String circleId){
        return bussinessDao.selectByExample(getExampleByVersion(versionId, ResBussiness.class))
                .stream().filter(bus ->circleId.equals(ALLGRAPH) || bus.getCircleId().equals(circleId))
                .collect(Collectors.toList());
    }
    private List<String> getLinkName(long versionId, String circleId){
        return linkDao.selectByExample(getExampleByVersion(versionId, ResLink.class))
                .stream().filter(link ->circleId.equals(ALLGRAPH) || link.getCircleId().equals(circleId))
                .map(ResLink::getLinkName).collect(Collectors.toList());
    }
    private List<String> getNetElemetName(long versionId, String circleId){
        return netElementDao.selectByExample(getExampleByVersion(versionId, ResNetElement.class))
                .stream().filter(netElement ->circleId.equals(ALLGRAPH) || netElement.getCircleId().equals(circleId))
                .map(ResNetElement::getNetElementName).collect(Collectors.toList());
    }
    private Example getExampleByVersion(Long versionId, Class<?> className) {
        Example example = new Example(className);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("versionId", versionId);
        return example;
    }
}
