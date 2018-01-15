package com.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;

@Table(name = "sys_back_up")
public class SysBackUp implements Serializable {
    /**
     * 版本Id
     */
    @Id
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
     * 机盘备份数据
     */
    @Column(name = "disk_back_up")
    private byte[] diskBackUp;

    /**
     * 链路备份数据
     */
    @Column(name = "link_back_up")
    private byte[] linkBackUp;

    /**
     * 网元备份数据
     */
    @Column(name = "net_element_back_up")
    private byte[] netElementBackUp;

    /**
     * 链路类型备份数据
     */
    @Column(name = "osnr_link_type_back_up")
    private byte[] osnrLinkTypeBackUp;

    /**
     * 放大器备份数据
     */
    @Column(name = "osnr_amplifier_back_up")
    private byte[] osnrAmplifierBackUp;

    /**
     * 业务（光通道）备份数据
     */
    @Column(name = "bussiness_back_up")
    private byte[] bussinessBackUp;

    private static final long serialVersionUID = 1L;

    /**
     * 获取版本Id
     *
     * @return version_id - 版本Id
     */
    public Long getVersionId() {
        return versionId;
    }

    /**
     * 设置版本Id
     *
     * @param versionId 版本Id
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
     * 获取机盘备份数据
     *
     * @return disk_back_up - 机盘备份数据
     */
    public byte[] getDiskBackUp() {
        return diskBackUp;
    }

    /**
     * 设置机盘备份数据
     *
     * @param diskBackUp 机盘备份数据
     */
    public void setDiskBackUp(byte[] diskBackUp) {
        this.diskBackUp = diskBackUp;
    }

    /**
     * 获取链路备份数据
     *
     * @return link_back_up - 链路备份数据
     */
    public byte[] getLinkBackUp() {
        return linkBackUp;
    }

    /**
     * 设置链路备份数据
     *
     * @param linkBackUp 链路备份数据
     */
    public void setLinkBackUp(byte[] linkBackUp) {
        this.linkBackUp = linkBackUp;
    }

    /**
     * 获取网元备份数据
     *
     * @return net_element_back_up - 网元备份数据
     */
    public byte[] getNetElementBackUp() {
        return netElementBackUp;
    }

    /**
     * 设置网元备份数据
     *
     * @param netElementBackUp 网元备份数据
     */
    public void setNetElementBackUp(byte[] netElementBackUp) {
        this.netElementBackUp = netElementBackUp;
    }

    /**
     * 获取链路类型备份数据
     *
     * @return osnr_link_type_back_up - 链路类型备份数据
     */
    public byte[] getOsnrLinkTypeBackUp() {
        return osnrLinkTypeBackUp;
    }

    /**
     * 设置链路类型备份数据
     *
     * @param osnrLinkTypeBackUp 链路类型备份数据
     */
    public void setOsnrLinkTypeBackUp(byte[] osnrLinkTypeBackUp) {
        this.osnrLinkTypeBackUp = osnrLinkTypeBackUp;
    }

    /**
     * 获取放大器备份数据
     *
     * @return osnr_amplifier_back_up - 放大器备份数据
     */
    public byte[] getOsnrAmplifierBackUp() {
        return osnrAmplifierBackUp;
    }

    /**
     * 设置放大器备份数据
     *
     * @param osnrAmplifierBackUp 放大器备份数据
     */
    public void setOsnrAmplifierBackUp(byte[] osnrAmplifierBackUp) {
        this.osnrAmplifierBackUp = osnrAmplifierBackUp;
    }

    /**
     * 获取业务（光通道）备份数据
     *
     * @return bussiness_back_up - 业务（光通道）备份数据
     */
    public byte[] getBussinessBackUp() {
        return bussinessBackUp;
    }

    /**
     * 设置业务（光通道）备份数据
     *
     * @param bussinessBackUp 业务（光通道）备份数据
     */
    public void setBussinessBackUp(byte[] bussinessBackUp) {
        this.bussinessBackUp = bussinessBackUp;
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
        SysBackUp other = (SysBackUp) that;
        return (this.getVersionId() == null ? other.getVersionId() == null : this.getVersionId().equals(other.getVersionId()))
                && (this.getGmtCreate() == null ? other.getGmtCreate() == null : this.getGmtCreate().equals(other.getGmtCreate()))
                && (this.getGmtModified() == null ? other.getGmtModified() == null : this.getGmtModified().equals(other.getGmtModified()))
                && (Arrays.equals(this.getDiskBackUp(), other.getDiskBackUp()))
                && (Arrays.equals(this.getLinkBackUp(), other.getLinkBackUp()))
                && (Arrays.equals(this.getNetElementBackUp(), other.getNetElementBackUp()))
                && (Arrays.equals(this.getOsnrLinkTypeBackUp(), other.getOsnrLinkTypeBackUp()))
                && (Arrays.equals(this.getOsnrAmplifierBackUp(), other.getOsnrAmplifierBackUp()))
                && (Arrays.equals(this.getBussinessBackUp(), other.getBussinessBackUp()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getVersionId() == null) ? 0 : getVersionId().hashCode());
        result = prime * result + ((getGmtCreate() == null) ? 0 : getGmtCreate().hashCode());
        result = prime * result + ((getGmtModified() == null) ? 0 : getGmtModified().hashCode());
        result = prime * result + (Arrays.hashCode(getDiskBackUp()));
        result = prime * result + (Arrays.hashCode(getLinkBackUp()));
        result = prime * result + (Arrays.hashCode(getNetElementBackUp()));
        result = prime * result + (Arrays.hashCode(getOsnrLinkTypeBackUp()));
        result = prime * result + (Arrays.hashCode(getOsnrAmplifierBackUp()));
        result = prime * result + (Arrays.hashCode(getBussinessBackUp()));
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", versionId=").append(versionId);
        sb.append(", gmtCreate=").append(gmtCreate);
        sb.append(", gmtModified=").append(gmtModified);
        sb.append(", diskBackUp=").append(diskBackUp);
        sb.append(", linkBackUp=").append(linkBackUp);
        sb.append(", netElementBackUp=").append(netElementBackUp);
        sb.append(", osnrLinkTypeBackUp=").append(osnrLinkTypeBackUp);
        sb.append(", osnrAmplifierBackUp=").append(osnrAmplifierBackUp);
        sb.append(", bussinessBackUp=").append(bussinessBackUp);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}