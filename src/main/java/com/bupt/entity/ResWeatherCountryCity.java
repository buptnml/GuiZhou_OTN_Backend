package com.bupt.entity;

import java.util.Date;
import javax.persistence.*;

@Table(name = "res_weather_country_city")
public class ResWeatherCountryCity {
    /**
     * 国家id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 国家名称
     */
    @Column(name = "country_name")
    private String countryName;

    /**
     * 城市名称
     */
    @Column(name = "city_name")
    private String cityName;

    /**
     * 插入时间
     */
    @Column(name = "gmt_create")
    private Date gmtCreate;

    /**
     * 创建时间
     */
    @Column(name = "gmt_modified")
    private Date gmtModified;

    /**
     * 获取国家id
     *
     * @return id - 国家id
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置国家id
     *
     * @param id 国家id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取国家名称
     *
     * @return country_name - 国家名称
     */
    public String getCountryName() {
        return countryName;
    }

    /**
     * 设置国家名称
     *
     * @param countryName 国家名称
     */
    public void setCountryName(String countryName) {
        this.countryName = countryName == null ? null : countryName.trim();
    }

    /**
     * 获取城市名称
     *
     * @return city_name - 城市名称
     */
    public String getCityName() {
        return cityName;
    }

    /**
     * 设置城市名称
     *
     * @param cityName 城市名称
     */
    public void setCityName(String cityName) {
        this.cityName = cityName == null ? null : cityName.trim();
    }

    /**
     * 获取插入时间
     *
     * @return gmt_create - 插入时间
     */
    public Date getGmtCreate() {
        return gmtCreate;
    }

    /**
     * 设置插入时间
     *
     * @param gmtCreate 插入时间
     */
    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    /**
     * 获取创建时间
     *
     * @return gmt_modified - 创建时间
     */
    public Date getGmtModified() {
        return gmtModified;
    }

    /**
     * 设置创建时间
     *
     * @param gmtModified 创建时间
     */
    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }
}