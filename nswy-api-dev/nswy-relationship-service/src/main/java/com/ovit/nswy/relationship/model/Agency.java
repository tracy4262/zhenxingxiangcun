package com.ovit.nswy.relationship.model;

import javax.persistence.Column;
import javax.persistence.Id;
import java.util.Date;

public class Agency {
    /**
     * 代理机构Id
     */
    @Id
    @Column(name = "agency_id")
    private Integer agencyId;

    /**
     * 代理机构类型
     */
    @Column(name = "agency_type")
    private Integer agencyType;

    /**
     * 被代理机构全称
     */
    @Column(name = "agency_name")
    private String agencyName;

    /**
     * 社会信用统一代码
     */
    @Column(name = "credit_code")
    private String creditCode;

    /**
     * 法定代表人姓名
     */
    @Column(name = "legal_person")
    private String legalPerson;

    /**
     * 机构联系方式
     */
    @Column(name = "contact_way")
    private String contactWay;

    /**
     * 机构注册地址
     */
    @Column(name = "registered_address")
    private String registeredAddress;

    /**
     * 代理协议图片
     */
    @Column(name = "agreement_image")
    private String agreementImage;

    /**
     * 营业执照图片
     */
    @Column(name = "license_image")
    private String licenseImage;

    /**
     * 法人代表身份证
     */
    @Column(name = "`legal _idcard_image`")
    private String legalIdcardImage;

    /**
     * 代理人身份证
     */
    @Column(name = "`agent _idcard_image`")
    private String agentIdcardImage;

    /**
     * 被代理账号
     */
    @Column(name = "agent_account")
    private String agentAccount;

    @Column(name = "create_time")
    private Date createTime;

    /**
     * 修改时间
     */
    @Column(name = "update_time")
    private Date updateTime;

    /**
     * 获取代理机构Id
     *
     * @return agency_id - 代理机构Id
     */
    public Integer getAgencyId() {
        return agencyId;
    }

    /**
     * 设置代理机构Id
     *
     * @param agencyId 代理机构Id
     */
    public void setAgencyId(Integer agencyId) {
        this.agencyId = agencyId;
    }

    /**
     * 获取代理机构类型
     *
     * @return agency_type - 代理机构类型
     */
    public Integer getAgencyType() {
        return agencyType;
    }

    /**
     * 设置代理机构类型
     *
     * @param agencyType 代理机构类型
     */
    public void setAgencyType(Integer agencyType) {
        this.agencyType = agencyType;
    }

    /**
     * 获取被代理机构全称
     *
     * @return agency_name - 被代理机构全称
     */
    public String getAgencyName() {
        return agencyName;
    }

    /**
     * 设置被代理机构全称
     *
     * @param agencyName 被代理机构全称
     */
    public void setAgencyName(String agencyName) {
        this.agencyName = agencyName == null ? null : agencyName.trim();
    }

    /**
     * 获取社会信用统一代码
     *
     * @return credit_code - 社会信用统一代码
     */
    public String getCreditCode() {
        return creditCode;
    }

    /**
     * 设置社会信用统一代码
     *
     * @param creditCode 社会信用统一代码
     */
    public void setCreditCode(String creditCode) {
        this.creditCode = creditCode == null ? null : creditCode.trim();
    }

    /**
     * 获取法定代表人姓名
     *
     * @return legal_person - 法定代表人姓名
     */
    public String getLegalPerson() {
        return legalPerson;
    }

    /**
     * 设置法定代表人姓名
     *
     * @param legalPerson 法定代表人姓名
     */
    public void setLegalPerson(String legalPerson) {
        this.legalPerson = legalPerson == null ? null : legalPerson.trim();
    }

    /**
     * 获取机构联系方式
     *
     * @return contact_way - 机构联系方式
     */
    public String getContactWay() {
        return contactWay;
    }

    /**
     * 设置机构联系方式
     *
     * @param contactWay 机构联系方式
     */
    public void setContactWay(String contactWay) {
        this.contactWay = contactWay == null ? null : contactWay.trim();
    }

    /**
     * 获取机构注册地址
     *
     * @return registered_address - 机构注册地址
     */
    public String getRegisteredAddress() {
        return registeredAddress;
    }

    /**
     * 设置机构注册地址
     *
     * @param registeredAddress 机构注册地址
     */
    public void setRegisteredAddress(String registeredAddress) {
        this.registeredAddress = registeredAddress == null ? null : registeredAddress.trim();
    }

    /**
     * 获取代理协议图片
     *
     * @return agreement_image - 代理协议图片
     */
    public String getAgreementImage() {
        return agreementImage;
    }

    /**
     * 设置代理协议图片
     *
     * @param agreementImage 代理协议图片
     */
    public void setAgreementImage(String agreementImage) {
        this.agreementImage = agreementImage == null ? null : agreementImage.trim();
    }

    /**
     * 获取营业执照图片
     *
     * @return license_image - 营业执照图片
     */
    public String getLicenseImage() {
        return licenseImage;
    }

    /**
     * 设置营业执照图片
     *
     * @param licenseImage 营业执照图片
     */
    public void setLicenseImage(String licenseImage) {
        this.licenseImage = licenseImage == null ? null : licenseImage.trim();
    }

    /**
     * 获取法人代表身份证
     *
     * @return legal _idcard_image - 法人代表身份证
     */
    public String getLegalIdcardImage() {
        return legalIdcardImage;
    }

    /**
     * 设置法人代表身份证
     *
     * @param legalIdcardImage 法人代表身份证
     */
    public void setLegalIdcardImage(String legalIdcardImage) {
        this.legalIdcardImage = legalIdcardImage == null ? null : legalIdcardImage.trim();
    }

    /**
     * 获取代理人身份证
     *
     * @return agent _idcard_image - 代理人身份证
     */
    public String getAgentIdcardImage() {
        return agentIdcardImage;
    }

    /**
     * 设置代理人身份证
     *
     * @param agentIdcardImage 代理人身份证
     */
    public void setAgentIdcardImage(String agentIdcardImage) {
        this.agentIdcardImage = agentIdcardImage == null ? null : agentIdcardImage.trim();
    }

    /**
     * 获取被代理账号
     *
     * @return agent_account - 被代理账号
     */
    public String getAgentAccount() {
        return agentAccount;
    }

    /**
     * 设置被代理账号
     *
     * @param agentAccount 被代理账号
     */
    public void setAgentAccount(String agentAccount) {
        this.agentAccount = agentAccount == null ? null : agentAccount.trim();
    }

    /**
     * @return create_time
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * @param createTime
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
}