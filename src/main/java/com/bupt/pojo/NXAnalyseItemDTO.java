package com.bupt.pojo;

import java.util.List;

/**
 * Created by zhangminchao on 2017/10/23.
 * <p>
 * N-X分析结果DTO
 */

public class NXAnalyseItemDTO {

    // 故障点的名称 —— 设备名或者复用段名
    private String itemName;


    // 影响的业务列表
    private List<String> affectBussiness;

    // 可恢复的业务列表
    private List<String> recoveryBussiness;

    // 恢复率
    private String recoveryRate;


    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public List<String> getRecoveryBussiness() {
        return recoveryBussiness;
    }

    public void setRecoveryBussiness(List<String> recoveryBussiness) {
        this.recoveryBussiness = recoveryBussiness;
    }

    public List<String> getAffectBussiness() {
        return affectBussiness;
    }

    public void setAffectBussiness(List<String> affectBussiness) {
        this.affectBussiness = affectBussiness;
    }

    public String getRecoveryRate() {
        return recoveryRate;
    }

    public void setRecoveryRate(String recoveryRate) {
        this.recoveryRate = recoveryRate;
    }
}
