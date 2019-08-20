package com.ovit.manager.modules.nswy.entity;

import com.ovit.manager.common.persistence.DataEntity;

import java.util.Date;

public class AgencyAudit extends DataEntity<AgencyAudit> {
    private Integer agencyAuditId;
    private String type;

    private String name;

    private String account;

    private String agencyer;

    private String creditCode;

    private String legalName;

    private String phone;

    private String registerAddress;

    private String agentAgreementPictureList;

    private String businessLicensePictureList;

    private String agentIdentityCardPictureList;

    private String legalIdentityCardPictureList;

    private String auditStatus;

    private Date createTime;

    private Integer userType;

    private String agencyName;

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public Integer getAgencyAuditId() {
        return agencyAuditId;
    }

    public void setAgencyAuditId(Integer agencyAuditId) {
        this.agencyAuditId = agencyAuditId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account == null ? null : account.trim();
    }

    public String getAgencyer() {
        return agencyer;
    }

    public void setAgencyer(String agencyer) {
        this.agencyer = agencyer == null ? null : agencyer.trim();
    }

    public String getCreditCode() {
        return creditCode;
    }

    public void setCreditCode(String creditCode) {
        this.creditCode = creditCode == null ? null : creditCode.trim();
    }

    public String getLegalName() {
        return legalName;
    }

    public void setLegalName(String legalName) {
        this.legalName = legalName == null ? null : legalName.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getRegisterAddress() {
        return registerAddress;
    }

    public void setRegisterAddress(String registerAddress) {
        this.registerAddress = registerAddress == null ? null : registerAddress.trim();
    }

    public String getAgentAgreementPictureList() {
        return agentAgreementPictureList;
    }

    public void setAgentAgreementPictureList(String agentAgreementPictureList) {
        this.agentAgreementPictureList = agentAgreementPictureList == null ? null : agentAgreementPictureList.trim();
    }

    public String getBusinessLicensePictureList() {
        return businessLicensePictureList;
    }

    public void setBusinessLicensePictureList(String businessLicensePictureList) {
        this.businessLicensePictureList = businessLicensePictureList == null ? null : businessLicensePictureList.trim();
    }

    public String getAgentIdentityCardPictureList() {
        return agentIdentityCardPictureList;
    }

    public void setAgentIdentityCardPictureList(String agentIdentityCardPictureList) {
        this.agentIdentityCardPictureList = agentIdentityCardPictureList == null ? null : agentIdentityCardPictureList.trim();
    }

    public String getLegalIdentityCardPictureList() {
        return legalIdentityCardPictureList;
    }

    public void setLegalIdentityCardPictureList(String legalIdentityCardPictureList) {
        this.legalIdentityCardPictureList = legalIdentityCardPictureList == null ? null : legalIdentityCardPictureList.trim();
    }

    public String getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(String auditStatus) {
        this.auditStatus = auditStatus == null ? null : auditStatus.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getAgencyName() {
        return agencyName;
    }

    public void setAgencyName(String agencyName) {
        this.agencyName = agencyName;
    }

    @Override
    public String toString() {
        return "AgencyAudit{" +
                "agencyAuditId=" + agencyAuditId +
                ", type='" + type + '\'' +
                ", name='" + name + '\'' +
                ", account='" + account + '\'' +
                ", agencyer='" + agencyer + '\'' +
                ", creditCode='" + creditCode + '\'' +
                ", legalName='" + legalName + '\'' +
                ", phone='" + phone + '\'' +
                ", registerAddress='" + registerAddress + '\'' +
                ", agentAgreementPictureList='" + agentAgreementPictureList + '\'' +
                ", businessLicensePictureList='" + businessLicensePictureList + '\'' +
                ", agentIdentityCardPictureList='" + agentIdentityCardPictureList + '\'' +
                ", legalIdentityCardPictureList='" + legalIdentityCardPictureList + '\'' +
                ", auditStatus='" + auditStatus + '\'' +
                ", createTime=" + createTime +
                ", userType=" + userType +
                ", agencyName='" + agencyName + '\'' +
                '}';
    }
}