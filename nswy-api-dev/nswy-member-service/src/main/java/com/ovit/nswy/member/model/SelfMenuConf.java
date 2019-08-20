package com.ovit.nswy.member.model;

import java.io.Serializable;

/**
 * 自定义标签
 * @author Administrator
 *
 */
public class SelfMenuConf implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer id;

    private String userAccount;

    private Integer personalInfo;

    private Integer approve;

    private Integer photo;

    private Integer circle;
    /**
     * 联系方式
     * 
     */
    private Integer contactInfo;

    private Integer product;

    private Integer service;
    /**
     * 我的知识库
     */
    private Integer repository;
    /**
     * 我的政策
     * 
     */
    private Integer policy;
	/**
	 * 我的工作室
	 */
    private Integer studio;
    /**
     * 资质认证
     */
    private Integer verification;
    /**
     * 荣誉证书
     */
    private Integer certificate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount == null ? null : userAccount.trim();
    }

    public Integer getPersonalInfo() {
        return personalInfo;
    }

    public void setPersonalInfo(Integer personalInfo) {
        this.personalInfo = personalInfo;
    }

    public Integer getApprove() {
        return approve;
    }

    public void setApprove(Integer approve) {
        this.approve = approve;
    }

    public Integer getPhoto() {
        return photo;
    }

    public void setPhoto(Integer photo) {
        this.photo = photo;
    }

    public Integer getCircle() {
        return circle;
    }

    public void setCircle(Integer circle) {
        this.circle = circle;
    }

    public Integer getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(Integer contactInfo) {
        this.contactInfo = contactInfo;
    }

    public Integer getProduct() {
        return product;
    }

    public void setProduct(Integer product) {
        this.product = product;
    }

    public Integer getService() {
        return service;
    }

    public void setService(Integer service) {
        this.service = service;
    }

    public Integer getRepository() {
        return repository;
    }

    public void setRepository(Integer repository) {
        this.repository = repository;
    }

    public Integer getPolicy() {
        return policy;
    }

    public void setPolicy(Integer policy) {
        this.policy = policy;
    }

    public Integer getStudio() {
        return studio;
    }

    public void setStudio(Integer studio) {
        this.studio = studio;
    }

    public Integer getVerification() {
        return verification;
    }

    public void setVerification(Integer verification) {
        this.verification = verification;
    }

    public Integer getCertificate() {
        return certificate;
    }

    public void setCertificate(Integer certificate) {
        this.certificate = certificate;
    }
    
    @Override
	public String toString() {
		return "SelfMenuConf [id=" + id + ", userAccount=" + userAccount + ", personalInfo=" + personalInfo
				+ ", approve=" + approve + ", photo=" + photo + ", circle=" + circle + ", contactInfo=" + contactInfo
				+ ", product=" + product + ", service=" + service + ", repository=" + repository + ", policy=" + policy
				+ ", studio=" + studio + ", verification=" + verification + ", certificate=" + certificate + "]";
	}


}