package com.ovit.manager.modules.nswy.entity;

import com.ovit.manager.common.persistence.DataEntity;

public class ProxyUserInfo extends DataEntity<ProxyUserInfo> {
    private Integer proxyUserInfoId;
    private String account;
    private String proxyAccount;
    private String proxyTemplate;
    private String uploadProtocol;
    private String cacelProxyTemplate;
    private String cacelUploadProtocol;
    private Integer type;
    private Integer status;
    private String auditOpinion;
    private String uploadPdf;
    private String cacelUploadPdf;
    private String cancelReason;
    private String otherReason;

    public String getCancelReason() {
        return cancelReason;
    }

    public void setCancelReason(String cancelReason) {
        this.cancelReason = cancelReason;
    }

    public String getOtherReason() {
        return otherReason;
    }

    public void setOtherReason(String otherReason) {
        this.otherReason = otherReason;
    }

    public Integer getProxyUserInfoId() {
        return proxyUserInfoId;
    }

    public void setProxyUserInfoId(Integer proxyUserInfoId) {
        this.proxyUserInfoId = proxyUserInfoId;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getProxyAccount() {
        return proxyAccount;
    }

    public void setProxyAccount(String proxyAccount) {
        this.proxyAccount = proxyAccount;
    }

    public String getProxyTemplate() {
        return proxyTemplate;
    }

    public void setProxyTemplate(String proxyTemplate) {
        this.proxyTemplate = proxyTemplate;
    }

    public String getUploadProtocol() {
        return uploadProtocol;
    }

    public void setUploadProtocol(String uploadProtocol) {
        this.uploadProtocol = uploadProtocol;
    }

    public String getCacelProxyTemplate() {
        return cacelProxyTemplate;
    }

    public void setCacelProxyTemplate(String cacelProxyTemplate) {
        this.cacelProxyTemplate = cacelProxyTemplate;
    }

    public String getCacelUploadProtocol() {
        return cacelUploadProtocol;
    }

    public void setCacelUploadProtocol(String cacelUploadProtocol) {
        this.cacelUploadProtocol = cacelUploadProtocol;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getAuditOpinion() {
        return auditOpinion;
    }

    public void setAuditOpinion(String auditOpinion) {
        this.auditOpinion = auditOpinion;
    }

    public String getUploadPdf() {
        return uploadPdf;
    }

    public void setUploadPdf(String uploadPdf) {
        this.uploadPdf = uploadPdf;
    }

    public String getCacelUploadPdf() {
        return cacelUploadPdf;
    }

    public void setCacelUploadPdf(String cacelUploadPdf) {
        this.cacelUploadPdf = cacelUploadPdf;
    }
}
