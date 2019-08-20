package com.ovit.nswy.member.model;

import java.io.Serializable;

/**
 * 访问控制
 * @author Administrator
 *
 */
public class SelfPrivateConf implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer id;

    private String userAccount;

    private Integer viewInfo;

    private Integer viewVisitor;

    private Integer circleReminder;

    private String ignoreDynamics;

    private String publicInfo;

    private Integer personalInfo;

    private Integer approve;

    private Integer photo;

    private Integer circle;

    private Integer contactInfo;

    private Integer product;

    private Integer service;

    private Integer repository;

    private Integer policy;

    private Integer studio;

    private Integer verification;

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

    public Integer getViewInfo() {
        return viewInfo;
    }

    public void setViewInfo(Integer viewInfo) {
        this.viewInfo = viewInfo;
    }

    public Integer getViewVisitor() {
        return viewVisitor;
    }

    public void setViewVisitor(Integer viewVisitor) {
        this.viewVisitor = viewVisitor;
    }

    public Integer getCircleReminder() {
        return circleReminder;
    }

    public void setCircleReminder(Integer circleReminder) {
        this.circleReminder = circleReminder;
    }

    public String getIgnoreDynamics() {
        return ignoreDynamics;
    }

    public void setIgnoreDynamics(String ignoreDynamics) {
        this.ignoreDynamics = ignoreDynamics == null ? null : ignoreDynamics.trim();
    }

    public String getPublicInfo() {
        return publicInfo;
    }

    public void setPublicInfo(String publicInfo) {
        this.publicInfo = publicInfo == null ? null : publicInfo.trim();
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
}