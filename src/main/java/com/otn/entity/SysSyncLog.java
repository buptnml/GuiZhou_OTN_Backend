package com.otn.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Table(name = "sys_sync_log")
public class SysSyncLog implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 同步日志id
     */
    @Id
    @Column(name = "sync_log_id")
    private Long syncLogId;
    /**
     * 操作员名称
     */
    @Column(name = "operator_name")
    private String operatorName;
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
     * 获取同步日志id
     *
     * @return sync_log_id - 同步日志id
     */
    public Long getSyncLogId() {
        return syncLogId;
    }

    /**
     * 设置同步日志id
     *
     * @param syncLogId 同步日志id
     */
    public void setSyncLogId(Long syncLogId) {
        this.syncLogId = syncLogId;
    }

    /**
     * 获取操作员名称
     *
     * @return operator_name - 操作员名称
     */
    public String getOperatorName() {
        return operatorName;
    }

    /**
     * 设置操作员名称
     *
     * @param operatorName 操作员名称
     */
    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName == null ? null : operatorName.trim();
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
        SysSyncLog other = (SysSyncLog) that;
        return (this.getSyncLogId() == null ? other.getSyncLogId() == null : this.getSyncLogId().equals(other.getSyncLogId()))
                && (this.getOperatorName() == null ? other.getOperatorName() == null : this.getOperatorName().equals(other.getOperatorName()))
                && (this.getGmtCreate() == null ? other.getGmtCreate() == null : this.getGmtCreate().equals(other.getGmtCreate()))
                && (this.getGmtModified() == null ? other.getGmtModified() == null : this.getGmtModified().equals(other.getGmtModified()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getSyncLogId() == null) ? 0 : getSyncLogId().hashCode());
        result = prime * result + ((getOperatorName() == null) ? 0 : getOperatorName().hashCode());
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
        sb.append(", syncLogId=").append(syncLogId);
        sb.append(", operatorName=").append(operatorName);
        sb.append(", gmtCreate=").append(gmtCreate);
        sb.append(", gmtModified=").append(gmtModified);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}