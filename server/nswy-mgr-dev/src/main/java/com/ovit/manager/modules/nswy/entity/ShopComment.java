package com.ovit.manager.modules.nswy.entity;

import com.ovit.manager.common.persistence.DataEntity;

import java.util.Date;

public class ShopComment extends DataEntity<ShopComment> {


    private Integer commentId;

    private String  orderCodeId;

    private String commodityTypeInfoId;

    private String commodityName;

    private String source;

    private String  account;

    private String displayName;

    private String star;

    private String describeInfo;

    private String  picUrl;

    private String approveStatus;

    private Date createTime;

    private Date updateTime;

    public String getCommodityName() {
        return commodityName;
    }

    public void setCommodityName(String commodityName) {
        this.commodityName = commodityName;
    }

    public Integer getCommentId() {
        return commentId;
    }

    public void setCommentId(Integer commentId) {
        this.commentId = commentId;
    }

    public String getOrderCodeId() {
        return orderCodeId;
    }

    public void setOrderCodeId(String orderCodeId) {
        this.orderCodeId = orderCodeId;
    }

    public String getCommodityTypeInfoId() {
        return commodityTypeInfoId;
    }

    public void setCommodityTypeInfoId(String commodityTypeInfoId) {
        this.commodityTypeInfoId = commodityTypeInfoId;
    }


    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getStar() {
        return star;
    }

    public void setStar(String star) {
        this.star = star;
    }

    public String getDescribeInfo() {
        return describeInfo;
    }

    public void setDescribeInfo(String describeInfo) {
        this.describeInfo = describeInfo;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getApproveStatus() {
        return approveStatus;
    }

    public void setApproveStatus(String approveStatus) {
        this.approveStatus = approveStatus;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
