package com.bupt.service.impl;

import com.bupt.dao.ResBussinessDao;
import com.bupt.dao.ResLinkDao;
import com.bupt.dao.ResNetElementDao;
import com.bupt.entity.ResBussiness;
import com.bupt.entity.ResLink;
import com.bupt.entity.ResNetElement;
import com.bupt.pojo.NXAnalyseItemDTO;
import com.bupt.service.NXAnalyseService;
import com.bupt.util.exception.controller.result.NoneGetException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

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

    @Override
    public List<NXAnalyseItemDTO> analyseEquip(long versionId, int num) {

        List<ResNetElement> netElements = netElementDao.selectByExample(getExampleByVersion(versionId, ResNetElement.class));
        List<ResBussiness> bussiness = bussinessDao.selectByExample(getExampleByVersion(versionId, ResBussiness.class));

        List<NXAnalyseItemDTO> result = new LinkedList<>();
        if (netElements.size() == 0 || bussiness.size() == 0) return null;

        if (1 == num) {
            for (ResNetElement netElement : netElements) {

                NXAnalyseItemDTO nxAnalyseItem = analyse(bussiness, new String[]{netElement.getNetElementName()});

                result.add(nxAnalyseItem);
            }

        } else if (num == 2) {
            for (int i = 0; i < netElements.size(); i++) {
                for (int j = i + 1; j < netElements.size(); j++) {

                    String eleA = netElements.get(i).getNetElementName();
                    String eleB = netElements.get(j).getNetElementName();

                    NXAnalyseItemDTO nxAnalyseItem = analyse(bussiness, new String[]{eleA, eleB});

                    result.add(nxAnalyseItem);
                }
            }

        }
        return result;
    }

    @Override
    public List<NXAnalyseItemDTO> analyseLink(long versionId, int num) {
        List<ResLink> links = linkDao.selectByExample(getExampleByVersion(versionId, ResLink.class));
        List<ResBussiness> bussiness = bussinessDao.selectByExample(getExampleByVersion(versionId, ResBussiness.class));
        if (links.size() == 0 || bussiness.size() == 0)
            throw new NoneGetException();
        // 结果
        List<NXAnalyseItemDTO> result = new LinkedList<>();

        if (num == 1) {
            for (ResLink link : links) {
                //                链路的起止点在路由中不能确定，故有两种情况
                String path1 = link.getEndAName() + "-" + link.getEndZName();
                String path2 = link.getEndZName() + "-" + link.getEndAName();
                NXAnalyseItemDTO nxAnalyseItem = analyse(bussiness, new String[]{path1, path2});
                nxAnalyseItem.setItemName(link.getLinkName());

                result.add(nxAnalyseItem);
            }
        } else if (num == 2) {
            for (int i = 0; i < links.size(); i++) {
                for (int j = i + 1; j < links.size(); j++) {
                    String pathA1 = links.get(i).getEndAName() + "-" + links.get(i).getEndZName();
                    String pathA2 = links.get(i).getEndZName() + "-" + links.get(i).getEndAName();
                    String pathZ1 = links.get(j).getEndAName() + "-" + links.get(j).getEndZName();
                    String pathZ2 = links.get(j).getEndZName() + "-" + links.get(j).getEndAName();
                    NXAnalyseItemDTO nxAnalyseItem = analyse(bussiness, new String[]{pathA1, pathA2, pathZ1, pathZ2});

                    nxAnalyseItem.setItemName(pathA1 + "," + pathZ1);
                    result.add(nxAnalyseItem);

                }
            }
        }

        return result;
    }

    @Override
    public List<NXAnalyseItemDTO> analyseEquipAndLink(long versionId, int num) {
        List<ResLink> links = linkDao.selectByExample(getExampleByVersion(versionId, ResLink.class));
        List<ResBussiness> bussinesses = bussinessDao.selectByExample(getExampleByVersion(versionId, ResBussiness.class));
        List<ResNetElement> netElements = netElementDao.selectByExample(getExampleByVersion(versionId, ResNetElement.class));
        if (netElements.size() == 0 || bussinesses.size() == 0 || links.size() == 0) return null;
        // 结果
        List<NXAnalyseItemDTO> result = new LinkedList<>();
        for (ResNetElement netElement : netElements) {
            for (ResLink link : links) {
//                链路的起止点在路由中不能确定，故有两种情况
                String pathA = link.getEndAName() + "-" + link.getEndZName();
                String pathZ = link.getEndZName() + "-" + link.getEndAName();
                NXAnalyseItemDTO nxAnalyseItem = analyse(bussinesses, new String[]{netElement.getNetElementName(), pathA, pathZ});

                nxAnalyseItem.setItemName(netElement.getNetElementName() + "," + pathA);
                result.add(nxAnalyseItem);
            }
        }
        return result;
    }

    private NXAnalyseItemDTO analyse(List<ResBussiness> resBussinesses, String items[]) {
        NXAnalyseItemDTO nxAnalyseItem = new NXAnalyseItemDTO();

        DecimalFormat decimalFormat = new DecimalFormat("0.00%");

        List<String> affectBusiness = new ArrayList<>();
        List<String> recoveryBusiness = new ArrayList<>();
        nxAnalyseItem.setAffectBussiness(affectBusiness);
        nxAnalyseItem.setRecoveryBussiness(recoveryBusiness);

        //设置故障名称
        String itemName = items[0];
        for (int i = 1; i < items.length; i++) {
            itemName = itemName + "," + items[i];
        }
        nxAnalyseItem.setItemName(itemName);

        //遍历每个业务
        for (ResBussiness resBussiness : resBussinesses) {
            String mainRoute = resBussiness.getMainRoute();
            String spareRoute = resBussiness.getSpareRoute();
            boolean affectFlag = false;
            boolean recFlag = true;
//            要是主路由里找到某一个故障设备或链路则业务会受影响
            for (String item : items) {
                if (mainRoute.indexOf(item) != -1) {
                    affectBusiness.add(resBussiness.getBussinessName());
                    affectFlag = true;
                    break;
                }
            }
//            在主路由里找到某个故障设备或链路后备用路由不存在
//            或者备用路由里也有某个故障设备或链路，则判定为不可恢复
            if (affectFlag) {
                for (String item : items) {
                    if (spareRoute == null || spareRoute.indexOf(item) != -1) {
                        recFlag = false;
                        break;
                    }
                }
                if (recFlag) recoveryBusiness.add(resBussiness.getBussinessName());
            }

        }
//        计算恢复率
        if (affectBusiness.size() == 0)
            nxAnalyseItem.setRecoveryRate("100.00%");
        else {
            double recoveryRate = (double) recoveryBusiness.size() / affectBusiness.size();
            nxAnalyseItem.setRecoveryRate(decimalFormat.format(recoveryRate));
        }
        return nxAnalyseItem;
    }

    private Example getExampleByVersion(Long versionId, Class<?> className) {
        Example example = new Example(className);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("versionId", versionId);

        return example;
    }

}
