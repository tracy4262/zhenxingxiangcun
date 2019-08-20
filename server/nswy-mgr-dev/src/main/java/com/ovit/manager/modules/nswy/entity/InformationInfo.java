package com.ovit.manager.modules.nswy.entity;

import com.ovit.manager.common.persistence.DataEntity;

import java.sql.Timestamp;

/**
 * DESCRIPTION:
 *
 * @author zouyan
 * @create 2018-02-08 上午9:47
 * created by fuck~
 **/
public class InformationInfo extends DataEntity<InformationInfo> {

    private static final long serialVersionUID = 1L;
    //资讯ID
    private String id;
    //资讯明细ID
    private String informationDetailId;
    //商务资讯
    private String businessInformation;
    //所有范围
    private String allRange;
    //行业性质
    private String businessNature;
    //所有行业
    private String allBusiness;
    //资讯标题
    private String title;
    //图片地址
    private String imageAdd;
    //创建时间
    private String createTime;
    //评论次数
    private String commentNum;
    //标签
    private String label;

    private String articleId;
    //行业分类id
    private String industrialId;
    //文章 文档分类
    private String columnType;
    //栏目分类\n''020101''行业动态 ''020102''企业动态 ''020103''专家动态 ''020104''乡村动态 ''020105''群协动态 ''020106''国际动态 ''020107'' 会员动态
    private String docType;
    //资讯分类
    private String classifiedId;
    //摘要
    private String abstracts;
    //是否显示到首页 默认0 显示 1 不显示
    private String isToPage;
    //物种id
    private String speciesId;
    //物种名称
    private String speciesName;
    //通用商品名
    private String product;
    //通用服务名
    private String service;
    //审核[(0,未审核),(1,审核通过),(2,审核未通过)]
    private String examine;
    //好友提醒
    private String friendReminding;
    //提醒内容
    private String remindingContent;
    //是否发送短信 0 是 1 否
    private String isSms;
    //公开范围 0 所有人可见 1 仅好友可见 2 仅自己可见 3 仅分组可见
    private String isPublish;
    //免费' COMMENT '是否有偿
    private String componsation;
    //0：待审核；1：删除；2：已审核；3： 审核不通过
    private String approveStatus;
    //是否在无忧导航显示 0 隐藏 1 显示
    private String isNavDisplay;
    //排序
    private String orderNum;

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

    public String getColumnType() {
        return columnType;
    }

    public void setColumnType(String columnType) {
        this.columnType = columnType;
    }

    public String getDocType() {
        return docType;
    }

    public void setDocType(String docType) {
        this.docType = docType;
    }

    public String getClassifiedId() {
        return classifiedId;
    }

    public void setClassifiedId(String classifiedId) {
        this.classifiedId = classifiedId;
    }

    public String getAbstracts() {
        return abstracts;
    }

    public void setAbstracts(String abstracts) {
        this.abstracts = abstracts;
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

    public String getExamine() {
        return examine;
    }

    public void setExamine(String examine) {
        this.examine = examine;
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

    @Override
    public String toString() {
        return "InformationInfo{" +
                "id='" + id + '\'' +
                ", informationDetailId='" + informationDetailId + '\'' +
                ", businessInformation='" + businessInformation + '\'' +
                ", allRange='" + allRange + '\'' +
                ", businessNature='" + businessNature + '\'' +
                ", allBusiness='" + allBusiness + '\'' +
                ", title='" + title + '\'' +
                ", imageAdd='" + imageAdd + '\'' +
                ", createTime='" + createTime + '\'' +
                ", commentNum='" + commentNum + '\'' +
                ", label='" + label + '\'' +
                ", articleId='" + articleId + '\'' +
                ", industrialId='" + industrialId + '\'' +
                ", columnType='" + columnType + '\'' +
                ", docType='" + docType + '\'' +
                ", classifiedId='" + classifiedId + '\'' +
                ", abstracts='" + abstracts + '\'' +
                ", isToPage='" + isToPage + '\'' +
                ", speciesId='" + speciesId + '\'' +
                ", speciesName='" + speciesName + '\'' +
                ", product='" + product + '\'' +
                ", service='" + service + '\'' +
                ", examine='" + examine + '\'' +
                ", friendReminding='" + friendReminding + '\'' +
                ", remindingContent='" + remindingContent + '\'' +
                ", isSms='" + isSms + '\'' +
                ", isPublish='" + isPublish + '\'' +
                ", componsation='" + componsation + '\'' +
                ", approveStatus='" + approveStatus + '\'' +
                ", isNavDisplay='" + isNavDisplay + '\'' +
                ", orderNum='" + orderNum + '\'' +
                '}';
    }
}
