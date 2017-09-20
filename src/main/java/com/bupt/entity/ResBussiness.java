package com.bupt.entity;

import com.bupt.pojo.OsnrChannelCreateInfo;

import java.util.Date;
import javax.persistence.*;

@Table(name = "res_bussiness")
public class ResBussiness {
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
     * 主路由
     */
    @Column(name = "main_route")
    private String mainRoute;

    /**
     * 主路由速率
     */
    @Column(name = "main_rate")
    private String mainRate;

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
     * 备用路由速率
     */
    @Column(name = "spare_rate")
    private String spareRate;

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
     * 获取主路由速率
     *
     * @return main_rate - 主路由速率
     */
    public String getMainRate() {
        return mainRate;
    }

    /**
     * 设置主路由速率
     *
     * @param mainRate 主路由速率
     */
    public void setMainRate(String mainRate) {
        this.mainRate = mainRate == null ? null : mainRate.trim();
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
     * 获取备用路由速率
     *
     * @return spare_rate - 备用路由速率
     */
    public String getSpareRate() {
        return spareRate;
    }

    /**
     * 设置备用路由速率
     *
     * @param spareRate 备用路由速率
     */
    public void setSpareRate(String spareRate) {
        this.spareRate = spareRate == null ? null : spareRate.trim();
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
}