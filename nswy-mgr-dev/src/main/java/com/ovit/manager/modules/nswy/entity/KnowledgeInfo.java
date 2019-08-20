package com.ovit.manager.modules.nswy.entity;

import com.ovit.manager.common.persistence.DataEntity;

/**
 * 知识
 *
 * @author haoWen
 * @create 2018-02-07 15:38
 **/

public class KnowledgeInfo extends DataEntity<KnowledgeInfo> {
    private static final long serialVersionUID = -1L;
    private String	id;
    private String	informationDetailId;
    private String	businessInformation;
    private String	allRange;
    private String	businessNature;
    private String	allBusiness;
    private String	title;
    private String	imageAdd;
    private String	createTime;
    private String	commentNum;
    private String	label;
    private String	articleId;
    private String	industrialId;
    private String	docType;
    private String	abstracts;
    private String	classifiedId;
    private String	isToPage;
    private String	speciesId;
    private String	speciesName;
    private String	chapter;
    private String	product;
    private String	service;
    private String	friendReminding;
    private String	remindingContent;
    private String	isSms;
    private String	isPublish;
    private String	componsation;
    private String	columnType;
    private String	approveStatus;
    private String	isNavDisplay;
    private String	orderNum;
    private String editer;


    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    public String getInformationDetailId() {
        return informationDetailId;
    }

    public void setInformationDetailId(String informationDetailId) {
        this.informationDetailId = informationDetailId;
    }

    public String getBusinessInformation() {
        return businessInformation;
    }

    public void setBusinessInformation(String businessInformation) {
        this.businessInformation = businessInformation;
    }

    public String getAllRange() {
        return allRange;
    }

    public void setAllRange(String allRange) {
        this.allRange = allRange;
    }

    public String getBusinessNature() {
        return businessNature;
    }

    public void setBusinessNature(String businessNature) {
        this.businessNature = businessNature;
    }

    public String getAllBusiness() {
        return allBusiness;
    }

    public void setAllBusiness(String allBusiness) {
        this.allBusiness = allBusiness;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageAdd() {
        return imageAdd;
    }

    public void setImageAdd(String imageAdd) {
        this.imageAdd = imageAdd;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getCommentNum() {
        return commentNum;
    }

    public void setCommentNum(String commentNum) {
        this.commentNum = commentNum;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getArticleId() {
        return articleId;
    }

    public void setArticleId(String articleId) {
        this.articleId = articleId;
    }

    public String getIndustrialId() {
        return industrialId;
    }

    public void setIndustrialId(String industrialId) {
        this.industrialId = industrialId;
    }

    public String getDocType() {
        return docType;
    }

    public void setDocType(String docType) {
        this.docType = docType;
    }

    public String getAbstracts() {
        return abstracts;
    }

    public void setAbstracts(String abstracts) {
        this.abstracts = abstracts;
    }

    public String getClassifiedId() {
        return classifiedId;
    }

    public void setClassifiedId(String classifiedId) {
        this.classifiedId = classifiedId;
    }

    public String getIsToPage() {
        return isToPage;
    }

    public void setIsToPage(String isToPage) {
        this.isToPage = isToPage;
    }

    public String getSpeciesId() {
        return speciesId;
    }

    public void setSpeciesId(String speciesId) {
        this.speciesId = speciesId;
    }

    public String getSpeciesName() {
        return speciesName;
    }

    public void setSpeciesName(String speciesName) {
        this.speciesName = speciesName;
    }

    public String getChapter() {
        return chapter;
    }

    public void setChapter(String chapter) {
        this.chapter = chapter;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getFriendReminding() {
        return friendReminding;
    }

    public void setFriendReminding(String friendReminding) {
        this.friendReminding = friendReminding;
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

    public String getColumnType() {
        return columnType;
    }

    public void setColumnType(String columnType) {
        this.columnType = columnType;
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

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public String getEditer() {
        return editer;
    }

    public void setEditer(String editer) {
        this.editer = editer;
    }
}
