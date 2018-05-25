package com.otn.service;

import com.otn.pojo.NXAnalyseItemDTO;

import java.util.List;

/**
 * Created by zhangminchao on 2017/10/23.
 * <p>
 * NX分析的Service
 */

public interface NXAnalyseService {

    /**
     * 分析设备
     *
     * @param num 故障数量，1或者2
     * @return
     */
    List<NXAnalyseItemDTO> analyseEquip(long versionId, int num, String circleId);


    /**
     * 分析链路
     *
     * @param num
     * @return
     */
    List<NXAnalyseItemDTO> analyseLink(long versionId, int num, String circleId);

    List<NXAnalyseItemDTO> analyseEquipAndLink(long versionId, int num, String circleId);

    List<NXAnalyseItemDTO> analyseSomeEquip(long versionId, String circleId, List<String> elements);

    List<NXAnalyseItemDTO> analyseSomeLink(long versionId, String circleId, List<String> links);

    List<NXAnalyseItemDTO> analyseSomeEquipAndLink(long versionId,  String circleId, List<String> elements);

}
