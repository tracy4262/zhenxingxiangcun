package com.ovit.manager.modules.nswy.entity;

import com.ovit.manager.common.persistence.DataEntity;

import java.util.Date;

/**
 * 应用管理
 *
 * @author haoWen
 * @create 2018-02-09 10:36
 **/
public class FullAppSettingInfo extends DataEntity<FullAppSettingInfo> {
    private String appSettingId;

    private String appName;

    private String level;

    private String cost;

    private String applicationAbstract;

    private String recommend;

    private String logo;

    private String logoName;

    private String image;

    private String imageName;

    private String number;

    private String userType;

    private String userTypeName;

    public String getAppSettingId() {
        return appSettingId;
    }

    public void setAppSettingId(String appSettingId) {
        this.appSettingId = appSettingId;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getApplicationAbstract() {
        return applicationAbstract;
    }

    public void setApplicationAbstract(String applicationAbstract) {
        this.applicationAbstract = applicationAbstract;
    }

    public String getRecommend() {
        return recommend;
    }

    public void setRecommend(String recommend) {
        this.recommend = recommend;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getUserTypeName() {
        return userTypeName;
    }

    public void setUserTypeName(String userTypeName) {
        this.userTypeName = userTypeName;
    }

    public String getLogoName() {
        return logoName;
    }

    public void setLogoName(String logoName) {
        this.logoName = logoName;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }
}
