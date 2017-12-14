package com.bupt.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Table(name = "res_osnr_amplifier")
public class ResOsnrAmplifier implements Serializable {
    /**
     * 放大器id
     */
    @Id
    @Column(name = "amplifier_id")
    private Long amplifierId;

    private static final long serialVersionUID = 1L;

    /**
     * 放大器名称
     */
    @Column(name = "amplifier_name")
    private String amplifierName;

    /**
     * 放大器增益
     */
    private Short gain;

    /**
     * 最小输入功率
     */
    @Column(name = "minimum_input_power")
    private Short minimumInputPower;

    /**
     * 最大输入功率
     */
    @Column(name = "maximum_input_power")
    private Short maximumInputPower;

    /**
     * 最大输出功率
     */
    @Column(name = "maximum_output_power")
    private Short maximumOutputPower;

    /**
     * 条目所在版本ID
     */
    @Column(name = "version_id")
    private Long versionId;

    /**
     * 创建时间
     */
    @Column(name = "gmt_create")
    private Date gmtCreate;

    /**
     * 最后修改时间
     */
    @Column(name = "gmt_modified")
    private Date gmtModified;
    /**
     * 放大器所在机盘名称
     */
    @Column(name = "disk_type")
    private String diskType;

    /**
     * 获取放大器id
     *
     * @return amplifier_id - 放大器id
     */
    public Long getAmplifierId() {
        return amplifierId;
    }

    /**
     * 设置放大器id
     *
     * @param amplifierId 放大器id
     */
    public void setAmplifierId(Long amplifierId) {
        this.amplifierId = amplifierId;
    }

    /**
     * 获取放大器所在机盘名称
     *
     * @return disk_type - 放大器所在机盘名称
     */
    public String getDiskType() {
        return diskType;
    }

    /**
     * 设置放大器所在机盘名称
     *
     * @param diskType 放大器所在机盘名称
     */
    public void setDiskType(String diskType) {
        this.diskType = diskType == null ? null : diskType.trim();
    }

    /**
     * 获取放大器名称
     *
     * @return amplifier_name - 放大器名称
     */
    public String getAmplifierName() {
        return amplifierName;
    }

    /**
     * 设置放大器名称
     *
     * @param amplifierName 放大器名称
     */
    public void setAmplifierName(String amplifierName) {
        this.amplifierName = amplifierName == null ? null : amplifierName.trim();
    }

    /**
     * 获取放大器增益
     *
     * @return gain - 放大器增益
     */
    public Short getGain() {
        return gain;
    }

    /**
     * 设置放大器增益
     *
     * @param gain 放大器增益
     */
    public void setGain(Short gain) {
        this.gain = gain;
    }

    /**
     * 获取最小输入功率
     *
     * @return minimum_input_power - 最小输入功率
     */
    public Short getMinimumInputPower() {
        return minimumInputPower;
    }

    /**
     * 设置最小输入功率
     *
     * @param minimumInputPower 最小输入功率
     */
    public void setMinimumInputPower(Short minimumInputPower) {
        this.minimumInputPower = minimumInputPower;
    }

    /**
     * 获取最大输入功率
     *
     * @return maximum_input_power - 最大输入功率
     */
    public Short getMaximumInputPower() {
        return maximumInputPower;
    }

    /**
     * 设置最大输入功率
     *
     * @param maximumInputPower 最大输入功率
     */
    public void setMaximumInputPower(Short maximumInputPower) {
        this.maximumInputPower = maximumInputPower;
    }

    /**
     * 获取最大输出功率
     *
     * @return maximum_output_power - 最大输出功率
     */
    public Short getMaximumOutputPower() {
        return maximumOutputPower;
    }

    /**
     * 设置最大输出功率
     *
     * @param maximumOutputPower 最大输出功率
     */
    public void setMaximumOutputPower(Short maximumOutputPower) {
        this.maximumOutputPower = maximumOutputPower;
    }

    /**
     * 获取条目所在版本ID
     *
     * @return version_id - 条目所在版本ID
     */
    public Long getVersionId() {
        return versionId;
    }

    /**
     * 设置条目所在版本ID
     *
     * @param versionId 条目所在版本ID
     */
    public void setVersionId(Long versionId) {
        this.versionId = versionId;
    }

    /**
     * 获取创建时间
     *
     * @return gmt_create - 创建时间
     */
    public Date getGmtCreate() {
        return gmtCreate;
    }

    /**
     * 设置创建时间
     *
     * @param gmtCreate 创建时间
     */
    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    /**
     * 获取最后修改时间
     *
     * @return gmt_modified - 最后修改时间
     */
    public Date getGmtModified() {
        return gmtModified;
    }

    /**
     * 设置最后修改时间
     *
     * @param gmtModified 最后修改时间
     */
    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        ResOsnrAmplifier other = (ResOsnrAmplifier) that;
        return (this.getAmplifierId() == null ? other.getAmplifierId() == null : this.getAmplifierId().equals(other.getAmplifierId()))
                && (this.getDiskType() == null ? other.getDiskType() == null : this.getDiskType().equals(other.getDiskType()))
                && (this.getAmplifierName() == null ? other.getAmplifierName() == null : this.getAmplifierName().equals(other.getAmplifierName()))
                && (this.getGain() == null ? other.getGain() == null : this.getGain().equals(other.getGain()))
                && (this.getMinimumInputPower() == null ? other.getMinimumInputPower() == null : this.getMinimumInputPower().equals(other.getMinimumInputPower()))
                && (this.getMaximumInputPower() == null ? other.getMaximumInputPower() == null : this.getMaximumInputPower().equals(other.getMaximumInputPower()))
                && (this.getMaximumOutputPower() == null ? other.getMaximumOutputPower() == null : this.getMaximumOutputPower().equals(other.getMaximumOutputPower()))
                && (this.getVersionId() == null ? other.getVersionId() == null : this.getVersionId().equals(other.getVersionId()))
                && (this.getGmtCreate() == null ? other.getGmtCreate() == null : this.getGmtCreate().equals(other.getGmtCreate()))
                && (this.getGmtModified() == null ? other.getGmtModified() == null : this.getGmtModified().equals(other.getGmtModified()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getAmplifierId() == null) ? 0 : getAmplifierId().hashCode());
        result = prime * result + ((getDiskType() == null) ? 0 : getDiskType().hashCode());
        result = prime * result + ((getAmplifierName() == null) ? 0 : getAmplifierName().hashCode());
        result = prime * result + ((getGain() == null) ? 0 : getGain().hashCode());
        result = prime * result + ((getMinimumInputPower() == null) ? 0 : getMinimumInputPower().hashCode());
        result = prime * result + ((getMaximumInputPower() == null) ? 0 : getMaximumInputPower().hashCode());
        result = prime * result + ((getMaximumOutputPower() == null) ? 0 : getMaximumOutputPower().hashCode());
        result = prime * result + ((getVersionId() == null) ? 0 : getVersionId().hashCode());
        result = prime * result + ((getGmtCreate() == null) ? 0 : getGmtCreate().hashCode());
        result = prime * result + ((getGmtModified() == null) ? 0 : getGmtModified().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", amplifierId=").append(amplifierId);
        sb.append(", diskType=").append(diskType);
        sb.append(", amplifierName=").append(amplifierName);
        sb.append(", gain=").append(gain);
        sb.append(", minimumInputPower=").append(minimumInputPower);
        sb.append(", maximumInputPower=").append(maximumInputPower);
        sb.append(", maximumOutputPower=").append(maximumOutputPower);
        sb.append(", versionId=").append(versionId);
        sb.append(", gmtCreate=").append(gmtCreate);
        sb.append(", gmtModified=").append(gmtModified);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}