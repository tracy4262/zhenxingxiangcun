package com.ovit.manager.modules.nswy.entity;

import com.ovit.manager.common.persistence.DataEntity;

import java.sql.Timestamp;

/**
* DESCRIPTION:
*
* @author zouyan
* @create 2018/2/8-上午11:07
* created by fuck~
**/
public class PolicyInfo extends DataEntity<PolicyInfo> {

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
    private String image_add;
    //'创建时间'
    private String createTime;
    //'评论次数'
    private String commentNum;
    //'标签'
    private String label;

    private String articleId;
    //'行业分类id'
    private String industrialId;
    //''030201''>政策法规  ''030202''>政策解读  ''030203''>通知公告 ''030204''>村务公开 ''030205''>党务公开 ''030206''>公告公示  ''030207''>政务法规\r\n''030208''>政务公开'
    private String docType;
    //'摘要'
    private String abstracts;
    //'分类'
    private String classifiedId;
    //是否显示到首页 默认0 显示 1 不显示
    private String isToPage;
    //
    private String speciesId;
    //
    private String speciesName;
    //解读专家
    private String expert;
    //'发文单位'
    private String company;
    //'发文字号'
    private String issuedNO;
    //'通用商品名'
    private String product;
    //'通用服务名'
    private String service;
    //'好友提醒'
    private String friendReminding;
    //'提醒内容'
    private String remindingContent;
    //是否发送短信 0 是 1 否
    private String isSms;
    //公开范围 0 所有人可见 1 仅好友可见 2 仅自己可见 3 仅分组可见
    private String isPublish;
    //免费' COMMENT '是否有偿
    private String componsation;
    //文章 图册 音频 视频 短信 微博
    private String columnType;
    //'颁布时间'
    private Timestamp publishTime;
    //
    private String account;
    //0：待审核；1：删除；2：已审核；3： 审核不通过
    private String approveStatus;
    //是否在无忧导航显示 0 隐藏 1 显示
    private String isNavDisplay;
    //'排序'
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

    public String getImage_add() {
        return image_add;
    }

    public void setImage_add(String image_add) {
        this.image_add = image_add;
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

    public String getExpert() {
        return expert;
    }

    public void setExpert(String expert) {
        this.expert = expert;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getIssuedNO() {
        return issuedNO;
    }

    public void setIssuedNO(String issuedNO) {
        this.issuedNO = issuedNO;
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

    public Timestamp getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(Timestamp publishTime) {
        this.publishTime = publishTime;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
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
        return "PolicyInfo{" +
                "id='" + id + '\'' +
                ", informationDetailId='" + informationDetailId + '\'' +
                ", businessInformation='" + businessInformation + '\'' +
                ", allRange='" + allRange + '\'' +
                ", businessNature='" + businessNature + '\'' +
                ", allBusiness='" + allBusiness + '\'' +
                ", title='" + title + '\'' +
                ", image_add='" + image_add + '\'' +
                ", createTime='" + createTime + '\'' +
                ", commentNum='" + commentNum + '\'' +
                ", label='" + label + '\'' +
                ", articleId='" + articleId + '\'' +
                ", industrialId='" + industrialId + '\'' +
                ", docType='" + docType + '\'' +
                ", abstracts='" + abstracts + '\'' +
                ", classifiedId='" + classifiedId + '\'' +
                ", isToPage='" + isToPage + '\'' +
                ", speciesId='" + speciesId + '\'' +
                ", speciesName='" + speciesName + '\'' +
                ", expert='" + expert + '\'' +
                ", company='" + company + '\'' +
                ", issuedNO='" + issuedNO + '\'' +
                ", product='" + product + '\'' +
                ", service='" + service + '\'' +
                ", friendReminding='" + friendReminding + '\'' +
                ", remindingContent='" + remindingContent + '\'' +
                ", isSms='" + isSms + '\'' +
                ", isPublish='" + isPublish + '\'' +
                ", componsation='" + componsation + '\'' +
                ", columnType='" + columnType + '\'' +
                ", publishTime=" + publishTime +
                ", account='" + account + '\'' +
                ", approveStatus='" + approveStatus + '\'' +
                ", isNavDisplay='" + isNavDisplay + '\'' +
                ", orderNum='" + orderNum + '\'' +
                '}';
    }
}
