package com.ovit.nswy.member.model;

import java.util.Date;

public class MemberIntroduce {
    private Integer id;

    private Integer memberIntroduceDetailId;

    private String businessInformation;

    private String allRange;

    private String businessNature;

    private String allBusiness;

    private String title;

    private String imageAdd;

    private Date createTime;

    private Integer commentNum;

    private String label;

    private String articleId;

    private String industrialId;

    private String docType;

    private String day;

    private String columnType;

    private String classifiedId;

    private String abstracts;

    private Integer isToPage;

    private String speciesId;

    private String speciesName;

    private String product;

    private String service;

    private String examine;

    private String friendReminding;

    private String remindingContent;

    private Integer isSms;

    private Integer isPublish;

    private String componsation;
    private Integer commentTotal;

    public Integer getCommentTotal() {
        return commentTotal;
    }

    public void setCommentTotal(Integer commentTotal) {
        this.commentTotal = commentTotal;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    private String author;

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMemberIntroduceDetailId() {
        return memberIntroduceDetailId;
    }

    public void setMemberIntroduceDetailId(Integer memberIntroduceDetailId) {
        this.memberIntroduceDetailId = memberIntroduceDetailId;
    }

    public String getBusinessInformation() {
        return businessInformation;
    }

    public void setBusinessInformation(String businessInformation) {
        this.businessInformation = businessInformation == null ? null : businessInformation.trim();
    }

    public String getAllRange() {
        return allRange;
    }

    public void setAllRange(String allRange) {
        this.allRange = allRange == null ? null : allRange.trim();
    }

    public String getBusinessNature() {
        return businessNature;
    }

    public void setBusinessNature(String businessNature) {
        this.businessNature = businessNature == null ? null : businessNature.trim();
    }

    public String getAllBusiness() {
        return allBusiness;
    }

    public void setAllBusiness(String allBusiness) {
        this.allBusiness = allBusiness == null ? null : allBusiness.trim();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getImageAdd() {
        return imageAdd;
    }

    public void setImageAdd(String imageAdd) {
        this.imageAdd = imageAdd == null ? null : imageAdd.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getCommentNum() {
        return commentNum;
    }

    public void setCommentNum(Integer commentNum) {
        this.commentNum = commentNum;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label == null ? null : label.trim();
    }

    public String getArticleId() {
        return articleId;
    }

    public void setArticleId(String articleId) {
        this.articleId = articleId == null ? null : articleId.trim();
    }

    public String getIndustrialId() {
        return industrialId;
    }

    public void setIndustrialId(String industrialId) {
        this.industrialId = industrialId == null ? null : industrialId.trim();
    }

    public String getDocType() {
        return docType;
    }

    public void setDocType(String docType) {
        this.docType = docType == null ? null : docType.trim();
    }

    public String getColumnType() {
        return columnType;
    }

    public void setColumnType(String columnType) {
        this.columnType = columnType == null ? null : columnType.trim();
    }

    public String getClassifiedId() {
        return classifiedId;
    }

    public void setClassifiedId(String classifiedId) {
        this.classifiedId = classifiedId == null ? null : classifiedId.trim();
    }

    public String getAbstracts() {
        return abstracts;
    }

    public void setAbstracts(String abstracts) {
        this.abstracts = abstracts == null ? null : abstracts.trim();
    }

    public Integer getIsToPage() {
        return isToPage;
    }

    public void setIsToPage(Integer isToPage) {
        this.isToPage = isToPage;
    }

    public String getSpeciesId() {
        return speciesId;
    }

    public void setSpeciesId(String speciesId) {
        this.speciesId = speciesId == null ? null : speciesId.trim();
    }

    public String getSpeciesName() {
        return speciesName;
    }

    public void setSpeciesName(String speciesName) {
        this.speciesName = speciesName == null ? null : speciesName.trim();
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product == null ? null : product.trim();
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service == null ? null : service.trim();
    }

    public String getExamine() {
        return examine;
    }

    public void setExamine(String examine) {
        this.examine = examine == null ? null : examine.trim();
    }

    public String getFriendReminding() {
        return friendReminding;
    }

    public void setFriendReminding(String friendReminding) {
        this.friendReminding = friendReminding == null ? null : friendReminding.trim();
    }

    public String getRemindingContent() {
        return remindingContent;
    }

    public void setRemindingContent(String remindingContent) {
        this.remindingContent = remindingContent == null ? null : remindingContent.trim();
    }

    public Integer getIsSms() {
        return isSms;
    }

    public void setIsSms(Integer isSms) {
        this.isSms = isSms;
    }

    public Integer getIsPublish() {
        return isPublish;
    }

    public void setIsPublish(Integer isPublish) {
        this.isPublish = isPublish;
    }

    public String getComponsation() {
        return componsation;
    }

    public void setComponsation(String componsation) {
        this.componsation = componsation == null ? null : componsation.trim();
    }

    @Override
    public String toString() {
        return "Information{" +
                "id=" + id +
                ", memberIntroduceDetailId=" + memberIntroduceDetailId +
                ", businessInformation='" + businessInformation + '\'' +
                ", allRange='" + allRange + '\'' +
                ", businessNature='" + businessNature + '\'' +
                ", allBusiness='" + allBusiness + '\'' +
                ", title='" + title + '\'' +
                ", imageAdd='" + imageAdd + '\'' +
                ", createTime=" + createTime +
                ", commentNum=" + commentNum +
                ", label='" + label + '\'' +
                ", articleId='" + articleId + '\'' +
                ", industrialId='" + industrialId + '\'' +
                ", docType='" + docType + '\'' +
                ", day='" + day + '\'' +
                ", columnType='" + columnType + '\'' +
                ", classifiedId='" + classifiedId + '\'' +
                ", abstracts='" + abstracts + '\'' +
                ", isToPage=" + isToPage +
                ", speciesId='" + speciesId + '\'' +
                ", speciesName='" + speciesName + '\'' +
                ", product='" + product + '\'' +
                ", service='" + service + '\'' +
                ", examine='" + examine + '\'' +
                ", friendReminding='" + friendReminding + '\'' +
                ", remindingContent='" + remindingContent + '\'' +
                ", isSms=" + isSms +
                ", isPublish=" + isPublish +
                ", componsation='" + componsation + '\'' +
                ", commentTotal=" + commentTotal +
                ", author='" + author + '\'' +
                '}';
    }
}