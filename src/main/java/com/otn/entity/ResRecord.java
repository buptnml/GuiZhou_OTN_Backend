package com.otn.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Table(name = "res_record")
public class ResRecord implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 记录id
     */
    @Id
    @Column(name = "record_id")
    private Long recordId;
    /**
     * 检修对象id
     */
    private String target;
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
     * 中断业务
     */
    @Column(name = "interrupt_bus")
    private String interruptBus;
    /**
     * 受影响业务
     */
    @Column(name = "affect_bus")
    private String affectBus;

    /**
     * 获取记录id
     *
     * @return record_id - 记录id
     */
    public Long getRecordId() {
        return recordId;
    }

    /**
     * 设置记录id
     *
     * @param recordId 记录id
     */
    public void setRecordId(Long recordId) {
        this.recordId = recordId;
    }

    /**
     * 获取检修对象id
     *
     * @return target - 检修对象id
     */
    public String getTarget() {
        return target;
    }

    /**
     * 设置检修对象id
     *
     * @param target 检修对象id
     */
    public void setTarget(String target) {
        this.target = target == null ? null : target.trim();
    }

    /**
     * @return version_id
     */
    public Long getVersionId() {
        return versionId;
    }

    /**
     * @param versionId
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

    /**
     * 获取中断业务
     *
     * @return interrupt_bus - 中断业务
     */
    public String getInterruptBus() {
        return interruptBus;
    }

    /**
     * 设置中断业务
     *
     * @param interruptBus 中断业务
     */
    public void setInterruptBus(String interruptBus) {
        this.interruptBus = interruptBus == null ? null : interruptBus.trim();
    }

    /**
     * 获取受影响业务
     *
     * @return affect_bus - 受影响业务
     */
    public String getAffectBus() {
        return affectBus;
    }

    /**
     * 设置受影响业务
     *
     * @param affectBus 受影响业务
     */
    public void setAffectBus(String affectBus) {
        this.affectBus = affectBus == null ? null : affectBus.trim();
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
        ResRecord other = (ResRecord) that;
        return (this.getRecordId() == null ? other.getRecordId() == null : this.getRecordId().equals(other.getRecordId()))
                && (this.getTarget() == null ? other.getTarget() == null : this.getTarget().equals(other.getTarget()))
                && (this.getVersionId() == null ? other.getVersionId() == null : this.getVersionId().equals(other.getVersionId()))
                && (this.getGmtCreate() == null ? other.getGmtCreate() == null : this.getGmtCreate().equals(other.getGmtCreate()))
                && (this.getGmtModified() == null ? other.getGmtModified() == null : this.getGmtModified().equals(other.getGmtModified()))
                && (this.getInterruptBus() == null ? other.getInterruptBus() == null : this.getInterruptBus().equals(other.getInterruptBus()))
                && (this.getAffectBus() == null ? other.getAffectBus() == null : this.getAffectBus().equals(other.getAffectBus()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getRecordId() == null) ? 0 : getRecordId().hashCode());
        result = prime * result + ((getTarget() == null) ? 0 : getTarget().hashCode());
        result = prime * result + ((getVersionId() == null) ? 0 : getVersionId().hashCode());
        result = prime * result + ((getGmtCreate() == null) ? 0 : getGmtCreate().hashCode());
        result = prime * result + ((getGmtModified() == null) ? 0 : getGmtModified().hashCode());
        result = prime * result + ((getInterruptBus() == null) ? 0 : getInterruptBus().hashCode());
        result = prime * result + ((getAffectBus() == null) ? 0 : getAffectBus().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", recordId=").append(recordId);
        sb.append(", target=").append(target);
        sb.append(", versionId=").append(versionId);
        sb.append(", gmtCreate=").append(gmtCreate);
        sb.append(", gmtModified=").append(gmtModified);
        sb.append(", interruptBus=").append(interruptBus);
        sb.append(", affectBus=").append(affectBus);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}