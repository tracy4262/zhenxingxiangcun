package com.ovit.manager.modules.nswy.entity;

import java.util.Date;

import com.ovit.manager.common.persistence.DataEntity;

public class NswyExpert extends DataEntity<NswyExpert> {

    private Integer expertId;

    private String expertName;

    private String loginAccount;

    private String expertType;

    private String adeptSpecies;

    private Integer approveStatus;

    private String personalPhoto;

    private String qualificationCertificate;

    private String adeptField;

    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPersonalPhoto() {
        return personalPhoto;
    }

    public void setPersonalPhoto(String personalPhoto) {
        this.personalPhoto = personalPhoto;
    }

    public String getQualificationCertificate() {
        return qualificationCertificate;
    }

    public void setQualificationCertificate(String qualificationCertificate) {
        this.qualificationCertificate = qualificationCertificate;
    }

    public String getAdeptField() {
        return adeptField;
    }

    public void setAdeptField(String adeptField) {
        this.adeptField = adeptField;
    }

    public Integer getExpertId() {
        return expertId;
    }

    public void setExpertId(Integer expertId) {
        this.expertId = expertId;
    }

    public String getExpertName() {
        return expertName;
    }

    public void setExpertName(String expertName) {
        this.expertName = expertName;
    }

    public String getLoginAccount() {
        return loginAccount;
    }

    public void setLoginAccount(String loginAccount) {
        this.loginAccount = loginAccount;
    }

    public String getExpertType() {
        return expertType;
    }

    public void setExpertType(String expertType) {
        this.expertType = expertType;
    }

    public String getAdeptSpecies() {
        return adeptSpecies;
    }

    public void setAdeptSpecies(String adeptSpecies) {
        this.adeptSpecies = adeptSpecies;
    }

    public Integer getApproveStatus() {
        return approveStatus;
    }

    public void setApproveStatus(Integer approveStatus) {
        this.approveStatus = approveStatus;
    }

}