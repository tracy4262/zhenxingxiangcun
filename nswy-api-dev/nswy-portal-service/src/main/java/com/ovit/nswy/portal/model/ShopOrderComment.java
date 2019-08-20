package com.ovit.nswy.portal.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "shop_order_comment")
public class ShopOrderComment {
    @Id
    private Integer id;

    /**
     * 订单
     */
    @Column(name = "order_code_id")
    private String orderCodeId;

    /**
     * 商品
     */
    @Column(name = "commodity_type_info_id")
    private String commodityTypeInfoId;

    /**
     * 来源，0.卖家，1买家
     */
    @Column(name = "from_account")
    private Integer fromAccount;

    /**
     * 操作人
     */
    private String account;

    /**
     * 好评
     */
    private Integer reputation;

    /**
     * 星级
     */
    private Integer star;

    /**
     * 说明
     */
    @Column(name = "describe_info")
    private String describeInfo;

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
     * 获取订单
     *
     * @return order_code_id - 订单
     */
    public String getOrderCodeId() {
        return orderCodeId;
    }

    /**
     * 设置订单
     *
     * @param orderCodeId 订单
     */
    public void setOrderCodeId(String orderCodeId) {
        this.orderCodeId = orderCodeId == null ? null : orderCodeId.trim();
    }

    /**
     * 获取商品
     *
     * @return commodity_type_info_id - 商品
     */
    public String getCommodityTypeInfoId() {
        return commodityTypeInfoId;
    }

    public void setCommodityTypeInfoId(String commodityTypeInfoId) {
        this.commodityTypeInfoId = commodityTypeInfoId;
    }

    /**
     * 获取操作人
     *
     * @return account - 操作人
     */
    public String getAccount() {
        return account;
    }

    /**
     * 设置操作人
     *
     * @param account 操作人
     */
    public void setAccount(String account) {
        this.account = account == null ? null : account.trim();
    }



    /**
     * 获取星级
     *
     * @return star - 星级
     */
    public Integer getStar() {
        return star;
    }

    /**
     * 设置星级
     *
     * @param star 星级
     */
    public void setStar(Integer star) {
        this.star = star;
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

    public Integer getReputation() {
        return reputation;
    }

    public void setReputation(Integer reputation) {
        this.reputation = reputation;
    }

    public Integer getFromAccount() {
        return fromAccount;
    }

    public void setFromAccount(Integer fromAccount) {
        this.fromAccount = fromAccount;
    }

    public String getDescribeInfo() {
        return describeInfo;
    }

    public void setDescribeInfo(String describeInfo) {
        this.describeInfo = describeInfo;
    }
}