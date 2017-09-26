package com.bupt.pojo;

/**
 * Created by caoxiaohong on 17/9/13.
 * 放大器:用作service层的传输对象
 */
public class AmplifierDTO {


    /**
     * amplifier_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '放大器id',
     `amplifier_name` varchar(100) NOT NULL DEFAULT '' COMMENT '放大器名称',
     `gain` smallint(6) NOT NULL COMMENT '放大器增益',
     `minimum_input_power` smallint(6) NOT NULL COMMENT '最小输入功率',
     `maximum_input_power` smallint(6) NOT NULL COMMENT '最大输入功率',
     `maximum_output_power` smallint(6) NOT NULL COMMENT '最大输出功率',
     `version_id` bigint(20) unsigned NOT NULL COMMENT '条目所在版本ID',
     */
    private Long amplifierID;
    private String amplifierName;
    private Short gain;
    private Short minimumInputPower;
    private Short maximumInputPower;
    private Short maximumOutputPower;



    private Long versionId;


    public void setAmplifierID(Long amplifierID) {
        this.amplifierID = amplifierID;
    }
    public Long getAmplifierID() {return amplifierID;}
    public String getAmplifierName() {
        return amplifierName;
    }

    public void setAmplifierName(String amplifierName) {
        this.amplifierName = amplifierName;
    }

    public Short getGain() {
        return gain;
    }

    public void setGain(Short gain) {
        this.gain = gain;
    }

    public Short getMinimumInputPower() {
        return minimumInputPower;
    }

    public void setMinimumInputPower(Short minimumInputPower) {
        this.minimumInputPower = minimumInputPower;
    }

    public Short getMaximumInputPower() {
        return maximumInputPower;
    }

    public void setMaximumInputPower(Short maximumInputPower) {
        this.maximumInputPower = maximumInputPower;
    }

    public Short getMaximumOutputPower() {
        return maximumOutputPower;
    }

    public void setMaximumOutputPower(Short maximumOutputPower) {
        this.maximumOutputPower = maximumOutputPower;
    }

    public Long getVersionId() {
        return versionId;
    }
    public void setVersionId(Long versionId) {
        this.versionId = versionId;
    }

    @Override
    public String toString(){
        StringBuilder sb=new StringBuilder();
        sb.append("AmplifierDTO{");
        sb.append("amplifierName='"+amplifierName+"',");
        sb.append("gain='"+gain+"',");
        sb.append("minimumInputPower='"+minimumInputPower+"',");
        sb.append("maximumInputPower='"+maximumInputPower+"',");
        sb.append("maximumOutputPower='"+maximumOutputPower+"',");
        return sb.toString();
    }
}
