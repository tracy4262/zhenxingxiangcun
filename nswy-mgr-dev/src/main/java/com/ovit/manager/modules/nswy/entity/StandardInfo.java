package com.ovit.manager.modules.nswy.entity;

import com.ovit.manager.common.persistence.DataEntity;

/**
 * 标准
 *
 * @author haoWen
 * @create 2018-03-14 15:12
 **/

public class StandardInfo   extends DataEntity<StandardInfo> {
    private String standardId;
    private String standardDetailId;
    private String classifiedId;
    private String docType;
    private String title;
    private String abstracts;
    private String source;
    private String author;
    private String ycauthor;
    private String account;
    private String location;
    private String productId;
    private String speciesId;
    private String serviceId;
    private String industrialId;
    private String unscrambleExpert;
    private String friendEeminding;
    private String remindingContent;
    private String isSms;
    private String isPublish;
    private String componsation;
    private String approveStatus;
    private String isNavDisplay;
    private String isToPage;
    private String orderNum;
    private String label;
    private String browseNumber;
    private String isReport;
    private String createTime;
    private String releaseUnit;


    public String getStandardId() {
        return standardId;
    }

    public void setStandardId(String standardId) {
        this.standardId = standardId;
    }

    public String getStandardDetailId() {
        return standardDetailId;
    }

    public void setStandardDetailId(String standardDetailId) {
        this.standardDetailId = standardDetailId;
    }

    public String getClassifiedId() {
        return classifiedId;
    }

    public void setClassifiedId(String classifiedId) {
        this.classifiedId = classifiedId;
    }

    public String getDocType() {
        return docType;
    }

    public void setDocType(String docType) {
        this.docType = docType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAbstracts() {
        return abstracts;
    }

    public void setAbstracts(String abstracts) {
        this.abstracts = abstracts;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getYcauthor() {
        return ycauthor;
    }

    public void setYcauthor(String ycauthor) {
        this.ycauthor = ycauthor;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getSpeciesId() {
        return speciesId;
    }

    public void setSpeciesId(String speciesId) {
        this.speciesId = speciesId;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getIndustrialId() {
        return industrialId;
    }

    public void setIndustrialId(String industrialId) {
        this.industrialId = industrialId;
    }

    public String getUnscrambleExpert() {
        return unscrambleExpert;
    }

    public void setUnscrambleExpert(String unscrambleExpert) {
        this.unscrambleExpert = unscrambleExpert;
    }

    public String getFriendEeminding() {
        return friendEeminding;
    }

    public void setFriendEeminding(String friendEeminding) {
        this.friendEeminding = friendEeminding;
    }

    public String getRemindingContent() {
        return remindingContent;
    }

    public void setRemindingContent(String remindingContent) {
        this.remindingContent = remindingContent;
    }

    public String getIsSms() {
        return isSms;
    }

    public void setIsSms(String isSms) {
        this.isSms = isSms;
    }

    public String getIsPublish() {
        return isPublish;
    }

    public void setIsPublish(String isPublish) {
        this.isPublish = isPublish;
    }

    public String getComponsation() {
        return componsation;
    }

    public void setComponsation(String componsation) {
        this.componsation = componsation;
    }

    public String getApproveStatus() {
        return approveStatus;
    }

    public void setApproveStatus(String approveStatus) {
        this.approveStatus = approveStatus;
    }

    public String getIsNavDisplay() {
        return isNavDisplay;
    }

    public void setIsNavDisplay(String isNavDisplay) {
        this.isNavDisplay = isNavDisplay;
    }

    public String getIsToPage() {
        return isToPage;
    }

    public void setIsToPage(String isToPage) {
        this.isToPage = isToPage;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getBrowseNumber() {
        return browseNumber;
    }

    public void setBrowseNumber(String browseNumber) {
        this.browseNumber = browseNumber;
    }

    public String getIsReport() {
        return isReport;
    }

    public void setIsReport(String isReport) {
        this.isReport = isReport;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getReleaseUnit() {
        return releaseUnit;
    }

    public void setReleaseUnit(String releaseUnit) {
        this.releaseUnit = releaseUnit;
    }

    @Override
    public String toString() {
        return "StandardInfo{" +
                "standardId='" + standardId + '\'' +
                ", standardDetailId='" + standardDetailId + '\'' +
                ", classifiedId='" + classifiedId + '\'' +
                ", docType='" + docType + '\'' +
                ", title='" + title + '\'' +
                ", abstracts='" + abstracts + '\'' +
                ", source='" + source + '\'' +
                ", author='" + author + '\'' +
                ", ycauthor='" + ycauthor + '\'' +
                ", account='" + account + '\'' +
                ", location='" + location + '\'' +
                ", productId='" + productId + '\'' +
                ", speciesId='" + speciesId + '\'' +
                ", serviceId='" + serviceId + '\'' +
                ", industrialId='" + industrialId + '\'' +
                ", unscrambleExpert='" + unscrambleExpert + '\'' +
                ", friendEeminding='" + friendEeminding + '\'' +
                ", remindingContent='" + remindingContent + '\'' +
                ", isSms='" + isSms + '\'' +
                ", isPublish='" + isPublish + '\'' +
                ", componsation='" + componsation + '\'' +
                ", approveStatus='" + approveStatus + '\'' +
                ", isNavDisplay='" + isNavDisplay + '\'' +
                ", isToPage='" + isToPage + '\'' +
                ", orderNum='" + orderNum + '\'' +
                ", label='" + label + '\'' +
                ", browseNumber='" + browseNumber + '\'' +
                ", isReport='" + isReport + '\'' +
                ", createTime='" + createTime + '\'' +
                '}';
    }
}
