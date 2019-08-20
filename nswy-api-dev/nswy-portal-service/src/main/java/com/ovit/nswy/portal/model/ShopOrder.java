package com.ovit.nswy.portal.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.*;
import javax.persistence.*;

@Table(name = "shop_order")
public class ShopOrder {
    @Id
    private Integer id;

    /**
     * 订单编号
     */
    @Column(name = "order_code")
    private String orderCode;


    /**
     * 买家
     */
    private String account;
    /**
     * 卖家
     */
    @Column(name = "sell_account")
    private String sellAccount;
    /**
     * 商品金额
     */
    private BigDecimal amount;

    /**
     * 运费
     */
    @Column(name = "logistic_amount")
    private BigDecimal logisticAmount;

    /**
     * 发票类型，如果是0，个人，1，增值发票
     */
    @Column(name = "invoice_type")
    private Integer invoiceType;


    /**
     * 开盘方式，0，电子发票
     */
    @Column(name = "invoice_mode")
    private Integer invoiceMode;



    /**
     * 发票
     */
    @Column(name = "invoice_info")
    private String invoiceInfo;
    /**
     * 地址
     */
    @Column(name = "address_info")
    private String addressInfo;

    /**
     * 状态，0，草稿状态，1.提交状态，2.付款成功，8，取消状态，9，退货状态
     */
    private Integer status;

    @Column(name = "sell_status")
    private Integer sellStatus;

    @Column(name = "has_complaint")
    private Integer hasComplaint;


    /**
     * 备注信息
     */
    private String remark;

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
     * 获取订单编号
     *
     * @return order_code - 订单编号
     */
    public String getOrderCode() {
        return orderCode;
    }

    /**
     * 设置订单编号
     *
     * @param orderCode 订单编号
     */
    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode == null ? null : orderCode.trim();
    }

    /**
     * 获取买家
     *
     * @return account - 买家
     */
    public String getAccount() {
        return account;
    }

    /**
     * 设置买家
     *
     * @param account 买家
     */
    public void setAccount(String account) {
        this.account = account == null ? null : account.trim();
    }

    /**
     * 获取商品金额
     *
     * @return amount - 商品金额
     */
    public BigDecimal getAmount() {
        return amount;
    }

    /**
     * 设置商品金额
     *
     * @param amount 商品金额
     */
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    /**
     * 获取运费
     *
     * @return logistic_amount - 运费
     */
    public BigDecimal getLogisticAmount() {
        return logisticAmount;
    }

    /**
     * 设置运费
     *
     * @param logisticAmount 运费
     */
    public void setLogisticAmount(BigDecimal logisticAmount) {
        this.logisticAmount = logisticAmount;
    }

    /**
     * 获取发票类型，如果是0，个人，1，增值发票
     *
     * @return invoice_type - 发票类型，如果是0，个人，1，增值发票
     */
    public Integer getInvoiceType() {
        return invoiceType;
    }

    /**
     * 设置发票类型，如果是0，个人，1，增值发票
     *
     * @param invoiceType 发票类型，如果是0，个人，1，增值发票
     */
    public void setInvoiceType(Integer invoiceType) {
        this.invoiceType = invoiceType;
    }

    public String getInvoiceInfo() {
        return invoiceInfo;
    }

    public void setInvoiceInfo(String invoiceInfo) {
        this.invoiceInfo = invoiceInfo;
    }
    /**
     * 获取地址
     *
     * @return address_info - 地址
     */
    public String getAddressInfo() {
        return addressInfo;
    }

    /**
     * 设置地址
     *
     * @param addressInfo 地址
     */
    public void setAddressInfo(String addressInfo) {
        this.addressInfo = addressInfo == null ? null : addressInfo.trim();
    }

    /**
     * 获取状态，0，草稿状态，1.提交状态，2.付款成功，8，取消状态，9，退货状态
     *
     * @return status - 状态，0，草稿状态，1.提交状态，2.付款成功，8，取消状态，9，退货状态
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置状态，0，草稿状态，1.提交状态，2.付款成功，8，取消状态，9，退货状态
     *
     * @param status 状态，0，草稿状态，1.提交状态，2.付款成功，8，取消状态，9，退货状态
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 获取备注信息
     *
     * @return remark - 备注信息
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 设置备注信息
     *
     * @param remark 备注信息
     */
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
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


    public String getSellAccount() {
        return sellAccount;
    }

    public void setSellAccount(String sellAccount) {
        this.sellAccount = sellAccount;
    }

    public Integer getInvoiceMode() {
        return invoiceMode;
    }

    public void setInvoiceMode(Integer invoiceMode) {
        this.invoiceMode = invoiceMode;
    }

    public Integer getSellStatus() {
        return sellStatus;
    }

    public void setSellStatus(Integer sellStatus) {
        this.sellStatus = sellStatus;
    }

    public Integer getHasComplaint() {
        return hasComplaint;
    }

    public void setHasComplaint(Integer hasComplaint) {
        this.hasComplaint = hasComplaint;
    }
}