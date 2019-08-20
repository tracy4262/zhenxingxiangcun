package com.ovit.nswy.relationship.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "live_category")
public class LiveCategory {
    /**
     * 房间分类
     */
    @Id
    @Column(name = "live_category_id")
    @GeneratedValue(generator = "JDBC")
    private Integer liveCategoryId;

    /**
     * 省
     */
    @Column(name = "province_id")
    private String provinceId;

    /**
     * 地区
     */
    @Column(name = "live_area")
    private String liveArea;

    @Column(name = "live_species")
    private String liveSpecies;

    /**
     * 相关物种
     */
    @Column(name = "species_id")
    private String speciesId;

    @Column(name = "live_industry")
    private String liveIndustry;

    /**
     * 相关行业
     */
    @Column(name = "industry_id")
    private String industryId;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 修改时间
     */
    @Column(name = "update_time")
    private Date updateTime;

    /**
     * 用户账号
     */
    private String account;

    /**
     * 市
     */
    @Column(name = "city_id")
    private String cityId;

    /**
     * 区，镇
     */
    @Column(name = "town_id")
    private String townId;

    /**
     * 县，区
     */
    @Column(name = "county_id")
    private String countyId;

    /**
     * 街道
     */
    @Column(name = "street_id")
    private String streetId;

    /**
     * 获取房间分类
     *
     * @return live_category_id - 房间分类
     */
    public Integer getLiveCategoryId() {
        return liveCategoryId;
    }

    /**
     * 设置房间分类
     *
     * @param liveCategoryId 房间分类
     */
    public void setLiveCategoryId(Integer liveCategoryId) {
        this.liveCategoryId = liveCategoryId;
    }

    /**
     * 获取省
     *
     * @return province_id - 省
     */
    public String getProvinceId() {
        return provinceId;
    }

    /**
     * 设置省
     *
     * @param provinceId 省
     */
    public void setProvinceId(String provinceId) {
        this.provinceId = provinceId == null ? null : provinceId.trim();
    }

    /**
     * 获取地区
     *
     * @return live_area - 地区
     */
    public String getLiveArea() {
        return liveArea;
    }

    /**
     * 设置地区
     *
     * @param liveArea 地区
     */
    public void setLiveArea(String liveArea) {
        this.liveArea = liveArea == null ? null : liveArea.trim();
    }

    /**
     * @return live_species
     */
    public String getLiveSpecies() {
        return liveSpecies;
    }

    /**
     * @param liveSpecies
     */
    public void setLiveSpecies(String liveSpecies) {
        this.liveSpecies = liveSpecies == null ? null : liveSpecies.trim();
    }

    /**
     * 获取相关物种
     *
     * @return species_id - 相关物种
     */
    public String getSpeciesId() {
        return speciesId;
    }

    /**
     * 设置相关物种
     *
     * @param speciesId 相关物种
     */
    public void setSpeciesId(String speciesId) {
        this.speciesId = speciesId == null ? null : speciesId.trim();
    }

    /**
     * @return live_industry
     */
    public String getLiveIndustry() {
        return liveIndustry;
    }

    /**
     * @param liveIndustry
     */
    public void setLiveIndustry(String liveIndustry) {
        this.liveIndustry = liveIndustry == null ? null : liveIndustry.trim();
    }

    /**
     * 获取相关行业
     *
     * @return industry_id - 相关行业
     */
    public String getIndustryId() {
        return industryId;
    }

    /**
     * 设置相关行业
     *
     * @param industryId 相关行业
     */
    public void setIndustryId(String industryId) {
        this.industryId = industryId == null ? null : industryId.trim();
    }

    /**
     * 获取创建时间
     *
     * @return create_time - 创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置创建时间
     *
     * @param createTime 创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取修改时间
     *
     * @return update_time - 修改时间
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 设置修改时间
     *
     * @param updateTime 修改时间
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 获取用户账号
     *
     * @return account - 用户账号
     */
    public String getAccount() {
        return account;
    }

    /**
     * 设置用户账号
     *
     * @param account 用户账号
     */
    public void setAccount(String account) {
        this.account = account == null ? null : account.trim();
    }

    /**
     * 获取市
     *
     * @return city_id - 市
     */
    public String getCityId() {
        return cityId;
    }

    /**
     * 设置市
     *
     * @param cityId 市
     */
    public void setCityId(String cityId) {
        this.cityId = cityId == null ? null : cityId.trim();
    }

    /**
     * 获取区，镇
     *
     * @return town_id - 区，镇
     */
    public String getTownId() {
        return townId;
    }

    /**
     * 设置区，镇
     *
     * @param townId 区，镇
     */
    public void setTownId(String townId) {
        this.townId = townId == null ? null : townId.trim();
    }

    /**
     * 获取县，区
     *
     * @return county_id - 县，区
     */
    public String getCountyId() {
        return countyId;
    }

    /**
     * 设置县，区
     *
     * @param countyId 县，区
     */
    public void setCountyId(String countyId) {
        this.countyId = countyId == null ? null : countyId.trim();
    }

    /**
     * 获取街道
     *
     * @return street_id - 街道
     */
    public String getStreetId() {
        return streetId;
    }

    /**
     * 设置街道
     *
     * @param streetId 街道
     */
    public void setStreetId(String streetId) {
        this.streetId = streetId == null ? null : streetId.trim();
    }
}