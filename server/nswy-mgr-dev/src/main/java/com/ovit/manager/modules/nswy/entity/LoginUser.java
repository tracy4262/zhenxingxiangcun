package com.ovit.manager.modules.nswy.entity;

import com.ovit.manager.common.persistence.DataEntity;

import java.sql.Timestamp;

/**
* DESCRIPTION:
* user_type = 4的是专家
* @author zouyan
* @create 2018/2/8-下午3:47
* created by fuck~ 
**/
public class LoginUser extends DataEntity<LoginUser> {

    private static final long serialVersionUID = 1L;

    private String id;

    private String uniqueId;

    private String loginAccount;

    private String displayName;

    private String loginPasswd;

    private Timestamp registerTime;

    private String isIdentityVerification;
    //'访客数量'
    private String tourNumber;
    //用户类型 0个人用户 1企业 3机关 4专家 5 乡村
    private String userType;
    //'签名'
    private String signature;
    //'头像'
    private String avatar;
    //
    private String isFriend;
    private String isHomeDisplay;
    private String expertType;
    private String adeptField;
    private String adeptSpecies;
    private String relatedIndustry;
    private String relatedService;

    private String isRealIdentity;

    private String isToPage;

    private  String isNavDisplay;

    private String orderNum;

    public String getIsToPage() {
        return isToPage;
    }

    public void setIsToPage(String isToPage) {
        this.isToPage = isToPage;
    }

    public String getIsNavDisplay() {
        return isNavDisplay;
    }

    public void setIsNavDisplay(String isNavDisplay) {
        this.isNavDisplay = isNavDisplay;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    public String getLoginAccount() {
        return loginAccount;
    }

    public void setLoginAccount(String loginAccount) {
        this.loginAccount = loginAccount;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getLoginPasswd() {
        return loginPasswd;
    }

    public void setLoginPasswd(String loginPasswd) {
        this.loginPasswd = loginPasswd;
    }

    public Timestamp getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(Timestamp registerTime) {
        this.registerTime = registerTime;
    }

    public String getIsIdentityVerification() {
        return isIdentityVerification;
    }

    public void setIsIdentityVerification(String isIdentityVerification) {
        this.isIdentityVerification = isIdentityVerification;
    }

    public String getTourNumber() {
        return tourNumber;
    }

    public void setTourNumber(String tourNumber) {
        this.tourNumber = tourNumber;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getIsFriend() {
        return isFriend;
    }

    public void setIsFriend(String isFriend) {
        this.isFriend = isFriend;
    }

    public String getIsHomeDisplay() {
        return isHomeDisplay;
    }

    public void setIsHomeDisplay(String isHomeDisplay) {
        this.isHomeDisplay = isHomeDisplay;
    }

    public String getExpertType() {
        return expertType;
    }

    public void setExpertType(String expertType) {
        this.expertType = expertType;
    }

    public String getAdeptField() {
        return adeptField;
    }

    public void setAdeptField(String adeptField) {
        this.adeptField = adeptField;
    }

    public String getAdeptSpecies() {
        return adeptSpecies;
    }

    public void setAdeptSpecies(String adeptSpecies) {
        this.adeptSpecies = adeptSpecies;
    }

    public String getRelatedIndustry() {
        return relatedIndustry;
    }

    public void setRelatedIndustry(String relatedIndustry) {
        this.relatedIndustry = relatedIndustry;
    }

    public String getRelatedService() {
        return relatedService;
    }

    public void setRelatedService(String relatedService) {
        this.relatedService = relatedService;
    }

    public String getIsRealIdentity() {
        return isRealIdentity;
    }

    public void setIsRealIdentity(String isRealIdentity) {
        this.isRealIdentity = isRealIdentity;
    }

    @Override
    public String toString() {
        return "LoginUser{" +
                "id='" + id + '\'' +
                ", uniqueId='" + uniqueId + '\'' +
                ", loginAccount='" + loginAccount + '\'' +
                ", displayName='" + displayName + '\'' +
                ", loginPasswd='" + loginPasswd + '\'' +
                ", registerTime=" + registerTime +
                ", isIdentityVerification='" + isIdentityVerification + '\'' +
                ", tourNumber='" + tourNumber + '\'' +
                ", userType='" + userType + '\'' +
                ", signature='" + signature + '\'' +
                ", avatar='" + avatar + '\'' +
                ", isFriend='" + isFriend + '\'' +
                ", isHomeDisplay='" + isHomeDisplay + '\'' +
                ", expertType='" + expertType + '\'' +
                ", adeptField='" + adeptField + '\'' +
                ", adeptSpecies='" + adeptSpecies + '\'' +
                ", relatedIndustry='" + relatedIndustry + '\'' +
                ", relatedService='" + relatedService + '\'' +
                '}';
    }
}
