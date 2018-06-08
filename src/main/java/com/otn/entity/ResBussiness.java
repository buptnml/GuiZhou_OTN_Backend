package com.otn.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Table(name = "res_bussiness")
public class ResBussiness implements Serializable {
    /**
     * 业务id
     */
    @Id
    @Column(name = "bussiness_id")
    private Long bussinessId;

    /**
     * 业务名
     */
    @Column(name = "bussiness_name")
    private String bussinessName;

    /**
     * 主路由速率
     */
    @Column(name = "bussiness_rate")
    private String bussinessRate;

    /**
     * 所属的环
     */
    @Column(name = "circle_id")
    private String circleId;

    /**
     * 主路由
     */
    @Column(name = "main_route")
    private String mainRoute;

    /**
     * 主路由频点
     */
    @Column(name = "main_frequency")
    private String mainFrequency;

    /**
     * 主路由上节点的输入功率
     */
    @Column(name = "main_input_powers")
    private String mainInputPowers;

    /**
     * 主路由上节点的输出功率
     */
    @Column(name = "main_output_powers")
    private String mainOutputPowers;

    /**
     * 备用路由
     */
    @Column(name = "spare_route")
    private String spareRoute;

    /**
     * 备用路由频点
     */
    @Column(name = "spare_frequency")
    private String spareFrequency;

    /**
     * 备用路由上节点的输入功率
     */
    @Column(name = "spare_input_powers")
    private String spareInputPowers;

    /**
     * 备用路由上节点的输出功率
     */
    @Column(name = "spare_output_powers")
    private String spareOutputPowers;

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

    private static final long serialVersionUID = 1L;

    /**
     * 获取业务id
     *
     * @return bussiness_id - 业务id
     */
    public Long getBussinessId() {
        return bussinessId;
    }

    /**
     * 设置业务id
     *
     * @param bussinessId 业务id
     */
    public void setBussinessId(Long bussinessId) {
        this.bussinessId = bussinessId;
    }

    /**
     * 获取业务名
     *
     * @return bussiness_name - 业务名
     */
    public String getBussinessName() {
        return bussinessName;
    }

    /**
     * 设置业务名
     *
     * @param bussinessName 业务名
     */
    public void setBussinessName(String bussinessName) {
        this.bussinessName = bussinessName == null ? null : bussinessName.trim();
    }

    /**
     * 获取主路由速率
     *
     * @return bussiness_rate - 主路由速率
     */
    public String getBussinessRate() {
        return bussinessRate;
    }

    /**
     * 设置主路由速率
     *
     * @param bussinessRate 主路由速率
     */
    public void setBussinessRate(String bussinessRate) {
        this.bussinessRate = bussinessRate == null ? null : bussinessRate.trim();
    }

    /**
     * 获取所属的环
     *
     * @return circle_id - 所属的环
     */
    public String getCircleId() {
        return circleId;
    }

    /**
     * 设置所属的环
     *
     * @param circleId 所属的环
     */
    public void setCircleId(String circleId) {
        this.circleId = circleId == null ? null : circleId.trim();
    }

    /**
     * 获取主路由
     *
     * @return main_route - 主路由
     */
    public String getMainRoute() {
        return mainRoute;
    }

    /**
     * 设置主路由
     *
     * @param mainRoute 主路由
     */
    public void setMainRoute(String mainRoute) {
        this.mainRoute = mainRoute == null ? null : mainRoute.trim();
    }

    /**
     * 获取主路由频点
     *
     * @return main_frequency - 主路由频点
     */
    public String getMainFrequency() {
        return mainFrequency;
    }

    /**
     * 设置主路由频点
     *
     * @param mainFrequency 主路由频点
     */
    public void setMainFrequency(String mainFrequency) {
        this.mainFrequency = mainFrequency == null ? null : mainFrequency.trim();
    }

    /**
     * 获取主路由上节点的输入功率
     *
     * @return main_input_powers - 主路由上节点的输入功率
     */
    public String getMainInputPowers() {
        return mainInputPowers;
    }

    /**
     * 设置主路由上节点的输入功率
     *
     * @param mainInputPowers 主路由上节点的输入功率
     */
    public void setMainInputPowers(String mainInputPowers) {
        this.mainInputPowers = mainInputPowers == null ? null : mainInputPowers.trim();
    }

    /**
     * 获取主路由上节点的输出功率
     *
     * @return main_output_powers - 主路由上节点的输出功率
     */
    public String getMainOutputPowers() {
        return mainOutputPowers;
    }

    /**
     * 设置主路由上节点的输出功率
     *
     * @param mainOutputPowers 主路由上节点的输出功率
     */
    public void setMainOutputPowers(String mainOutputPowers) {
        this.mainOutputPowers = mainOutputPowers == null ? null : mainOutputPowers.trim();
    }

    /**
     * 获取备用路由
     *
     * @return spare_route - 备用路由
     */
    public String getSpareRoute() {
        return spareRoute;
    }

    /**
     * 设置备用路由
     *
     * @param spareRoute 备用路由
     */
    public void setSpareRoute(String spareRoute) {
        this.spareRoute = spareRoute == null ? null : spareRoute.trim();
    }

    /**
     * 获取备用路由频点
     *
     * @return spare_frequency - 备用路由频点
     */
    public String getSpareFrequency() {
        return spareFrequency;
    }

    /**
     * 设置备用路由频点
     *
     * @param spareFrequency 备用路由频点
     */
    public void setSpareFrequency(String spareFrequency) {
        this.spareFrequency = spareFrequency == null ? null : spareFrequency.trim();
    }

    /**
     * 获取备用路由上节点的输入功率
     *
     * @return spare_input_powers - 备用路由上节点的输入功率
     */
    public String getSpareInputPowers() {
        return spareInputPowers;
    }

    /**
     * 设置备用路由上节点的输入功率
     *
     * @param spareInputPowers 备用路由上节点的输入功率
     */
    public void setSpareInputPowers(String spareInputPowers) {
        this.spareInputPowers = spareInputPowers == null ? null : spareInputPowers.trim();
    }

    /**
     * 获取备用路由上节点的输出功率
     *
     * @return spare_output_powers - 备用路由上节点的输出功率
     */
    public String getSpareOutputPowers() {
        return spareOutputPowers;
    }

    /**
     * 设置备用路由上节点的输出功率
     *
     * @param spareOutputPowers 备用路由上节点的输出功率
     */
    public void setSpareOutputPowers(String spareOutputPowers) {
        this.spareOutputPowers = spareOutputPowers == null ? null : spareOutputPowers.trim();
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
        ResBussiness other = (ResBussiness) that;
        return (this.getBussinessId() == null ? other.getBussinessId() == null : this.getBussinessId().equals(other.getBussinessId()))
            && (this.getBussinessName() == null ? other.getBussinessName() == null : this.getBussinessName().equals(other.getBussinessName()))
            && (this.getBussinessRate() == null ? other.getBussinessRate() == null : this.getBussinessRate().equals(other.getBussinessRate()))
            && (this.getCircleId() == null ? other.getCircleId() == null : this.getCircleId().equals(other.getCircleId()))
            && (this.getMainRoute() == null ? other.getMainRoute() == null : this.getMainRoute().equals(other.getMainRoute()))
            && (this.getMainFrequency() == null ? other.getMainFrequency() == null : this.getMainFrequency().equals(other.getMainFrequency()))
            && (this.getMainInputPowers() == null ? other.getMainInputPowers() == null : this.getMainInputPowers().equals(other.getMainInputPowers()))
            && (this.getMainOutputPowers() == null ? other.getMainOutputPowers() == null : this.getMainOutputPowers().equals(other.getMainOutputPowers()))
            && (this.getSpareRoute() == null ? other.getSpareRoute() == null : this.getSpareRoute().equals(other.getSpareRoute()))
            && (this.getSpareFrequency() == null ? other.getSpareFrequency() == null : this.getSpareFrequency().equals(other.getSpareFrequency()))
            && (this.getSpareInputPowers() == null ? other.getSpareInputPowers() == null : this.getSpareInputPowers().equals(other.getSpareInputPowers()))
            && (this.getSpareOutputPowers() == null ? other.getSpareOutputPowers() == null : this.getSpareOutputPowers().equals(other.getSpareOutputPowers()))
            && (this.getVersionId() == null ? other.getVersionId() == null : this.getVersionId().equals(other.getVersionId()))
            && (this.getGmtCreate() == null ? other.getGmtCreate() == null : this.getGmtCreate().equals(other.getGmtCreate()))
            && (this.getGmtModified() == null ? other.getGmtModified() == null : this.getGmtModified().equals(other.getGmtModified()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getBussinessId() == null) ? 0 : getBussinessId().hashCode());
        result = prime * result + ((getBussinessName() == null) ? 0 : getBussinessName().hashCode());
        result = prime * result + ((getBussinessRate() == null) ? 0 : getBussinessRate().hashCode());
        result = prime * result + ((getCircleId() == null) ? 0 : getCircleId().hashCode());
        result = prime * result + ((getMainRoute() == null) ? 0 : getMainRoute().hashCode());
        result = prime * result + ((getMainFrequency() == null) ? 0 : getMainFrequency().hashCode());
        result = prime * result + ((getMainInputPowers() == null) ? 0 : getMainInputPowers().hashCode());
        result = prime * result + ((getMainOutputPowers() == null) ? 0 : getMainOutputPowers().hashCode());
        result = prime * result + ((getSpareRoute() == null) ? 0 : getSpareRoute().hashCode());
        result = prime * result + ((getSpareFrequency() == null) ? 0 : getSpareFrequency().hashCode());
        result = prime * result + ((getSpareInputPowers() == null) ? 0 : getSpareInputPowers().hashCode());
        result = prime * result + ((getSpareOutputPowers() == null) ? 0 : getSpareOutputPowers().hashCode());
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
        sb.append(", bussinessId=").append(bussinessId);
        sb.append(", bussinessName=").append(bussinessName);
        sb.append(", bussinessRate=").append(bussinessRate);
        sb.append(", circleId=").append(circleId);
        sb.append(", mainRoute=").append(mainRoute);
        sb.append(", mainFrequency=").append(mainFrequency);
        sb.append(", mainInputPowers=").append(mainInputPowers);
        sb.append(", mainOutputPowers=").append(mainOutputPowers);
        sb.append(", spareRoute=").append(spareRoute);
        sb.append(", spareFrequency=").append(spareFrequency);
        sb.append(", spareInputPowers=").append(spareInputPowers);
        sb.append(", spareOutputPowers=").append(spareOutputPowers);
        sb.append(", versionId=").append(versionId);
        sb.append(", gmtCreate=").append(gmtCreate);
        sb.append(", gmtModified=").append(gmtModified);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}