package com.ovit.nswy.portal.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "shop_complaint")
public class ShopComplaint {
    @Id
    private Integer id;

    /**
     * 订单
     */
    @Column(name = "order_code_id")
    private String orderCodeId;

    /**
     * 投诉对象
     */
    @Column(name = "seller_account")
    private String sellerAccount;

    /**
     * 买家
     */
    @Column(name = "buyer_account")
    private String buyerAccount;

    /**
     * 理由
     */
    private String reason;

    /**
     * 说明
     */
    @Column(name = "describe_info")
    private String describeInfo;

    /**
     * 手机
     */
    private String mobile;

    /**
     * 图片信息
     */
    @Column(name = "pic_url")
    private String picUrl;

    /**
     * 审核状态 0 待审核，1审核通过 ，2审核未通过
     */
    @Column(name = "approve_status")
    private Integer approveStatus;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "update_time")
    private Date updateTime;

    /**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取投诉对象
     *
     * @return seller_account - 投诉对象
     */
    public String getSellerAccount() {
        return sellerAccount;
    }

    /**
     * 设置投诉对象
     *
     * @param sellerAccount 投诉对象
     */
    public void setSellerAccount(String sellerAccount) {
        this.sellerAccount = sellerAccount == null ? null : sellerAccount.trim();
    }

    /**
     * 获取买家
     *
     * @return buyer_account - 买家
     */
    public String getBuyerAccount() {
        return buyerAccount;
    }

    /**
     * 设置买家
     *
     * @param buyerAccount 买家
     */
    public void setBuyerAccount(String buyerAccount) {
        this.buyerAccount = buyerAccount == null ? null : buyerAccount.trim();
    }

    /**
     * 获取理由
     *
     * @return reason - 理由
     */
    public String getReason() {
        return reason;
    }

    /**
     * 设置理由
     *
     * @param reason 理由
     */
    public void setReason(String reason) {
        this.reason = reason == null ? null : reason.trim();
    }


    /**
     * 获取手机
     *
     * @return mobile - 手机
     */
    public String getMobile() {
        return mobile;
    }

    /**
     * 设置手机
     *
     * @param mobile 手机
     */
    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    /**
     * 获取图片信息
     *
     * @return pic_url - 图片信息
     */
    public String getPicUrl() {
        return picUrl;
    }

    /**
     * 设置图片信息
     *
     * @param picUrl 图片信息
     */
    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl == null ? null : picUrl.trim();
    }

    /**
     * 获取审核状态 0 待审核，1审核通过 ，2审核未通过
     *
     * @return approve_status - 审核状态 0 待审核，1审核通过 ，2审核未通过
     */
    public Integer getApproveStatus() {
        return approveStatus;
    }

    /**
     * 设置审核状态 0 待审核，1审核通过 ，2审核未通过
     *
     * @param approveStatus 审核状态 0 待审核，1审核通过 ，2审核未通过
     */
    public void setApproveStatus(Integer approveStatus) {
        this.approveStatus = approveStatus;
    }

    /**
     * @return create_time
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * @param createTime
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * @return update_time
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * @param updateTime
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getDescribeInfo() {
        return describeInfo;
    }

    public void setDescribeInfo(String describeInfo) {
        this.describeInfo = describeInfo;
    }

    public String getOrderCodeId() {
        return orderCodeId;
    }

    public void setOrderCodeId(String orderCodeId) {
        this.orderCodeId = orderCodeId;
    }
}