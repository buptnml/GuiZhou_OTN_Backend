package com.bupt.service;

import com.bupt.pojo.NXAnalyseItemDTO;

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
    List<NXAnalyseItemDTO> analyseEquip(long versionId, int num);


    /**
     * 分析链路
     *
     * @param num
     * @return
     */
    List<NXAnalyseItemDTO> analyseLink(long versionId, int num);

    List<NXAnalyseItemDTO> analyseEquipAndLink(long versionId, int num);

}
