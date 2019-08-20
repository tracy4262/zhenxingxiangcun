package com.ovit.manager.modules.nswy.entity;

import com.ovit.manager.common.persistence.DataEntity;

import java.util.Date;

/**
 * 1
 *
 * @author wangrongf
 * @create 2018-02-11 14:04
 **/

public class SysUserInfo extends DataEntity<SysUserInfo>{

    private Integer infoId;

    private String account;

    private String userType;

    private String isDisplay;

    private String privacyInformation;

    private String networkInformation;

    private String educationLive;

    private String workLive;

    private String politicalStatus;

    private String religionFaith;

    private String specialtyInformation;

    private String honourStyle;

    private String housesCondition;

    private String organization;

    private String managementTeam;

    private String surveyOperation;

    private String financeInformation;

    private String propertyInformation;

    private String productServe;

    private String specialtyCredentials;

    private Date createTime;

    private String agencyName;

    public String getAgencyName() {
        return agencyName;
    }

    public void setAgencyName(String agencyName) {
        this.agencyName = agencyName;
    }

    public Integer getInfoId() {
        return infoId;
    }

    public void setInfoId(Integer infoId) {
        this.infoId = infoId;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getIsDisplay() {
        return isDisplay;
    }

    public void setIsDisplay(String isDisplay) {
        this.isDisplay = isDisplay;
    }

    public String getPrivacyInformation() {
        return privacyInformation;
    }

    public void setPrivacyInformation(String privacyInformation) {
        this.privacyInformation = privacyInformation;
    }

    public String getNetworkInformation() {
        return networkInformation;
    }

    public void setNetworkInformation(String networkInformation) {
        this.networkInformation = networkInformation;
    }

    public String getEducationLive() {
        return educationLive;
    }

    public void setEducationLive(String educationLive) {
        this.educationLive = educationLive;
    }

    public String getWorkLive() {
        return workLive;
    }

    public void setWorkLive(String workLive) {
        this.workLive = workLive;
    }

    public String getPoliticalStatus() {
        return politicalStatus;
    }

    public void setPoliticalStatus(String politicalStatus) {
        this.politicalStatus = politicalStatus;
    }

    public String getReligionFaith() {
        return religionFaith;
    }

    public void setReligionFaith(String religionFaith) {
        this.religionFaith = religionFaith;
    }

    public String getSpecialtyInformation() {
        return specialtyInformation;
    }

    public void setSpecialtyInformation(String specialtyInformation) {
        this.specialtyInformation = specialtyInformation;
    }

    public String getHonourStyle() {
        return honourStyle;
    }

    public void setHonourStyle(String honourStyle) {
        this.honourStyle = honourStyle;
    }

    public String getHousesCondition() {
        return housesCondition;
    }

    public void setHousesCondition(String housesCondition) {
        this.housesCondition = housesCondition;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getManagementTeam() {
        return managementTeam;
    }

    public void setManagementTeam(String managementTeam) {
        this.managementTeam = managementTeam;
    }

    public String getSurveyOperation() {
        return surveyOperation;
    }

    public void setSurveyOperation(String surveyOperation) {
        this.surveyOperation = surveyOperation;
    }

    public String getFinanceInformation() {
        return financeInformation;
    }

    public void setFinanceInformation(String financeInformation) {
        this.financeInformation = financeInformation;
    }

    public String getPropertyInformation() {
        return propertyInformation;
    }

    public void setPropertyInformation(String propertyInformation) {
        this.propertyInformation = propertyInformation;
    }

    public String getProductServe() {
        return productServe;
    }

    public void setProductServe(String productServe) {
        this.productServe = productServe;
    }

    public String getSpecialtyCredentials() {
        return specialtyCredentials;
    }

    public void setSpecialtyCredentials(String specialtyCredentials) {
        this.specialtyCredentials = specialtyCredentials;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
