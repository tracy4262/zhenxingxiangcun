package com.ovit.nswy.portal.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "shop_order_log")
public class ShopOrderLog {
    @Id
    private Integer id;

    /**
     * 订单
     */
    @Column(name = "order_code_id")
    private String orderCodeId;

    /**
     * 操作人
     */
    private String account;

    /**
     * 操作状态
     */
    private Integer type;

    private String log;

    @Column(name = "create_time")
    private Date createTime;

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
        this.orderCodeId = orderCodeId;
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
     * 获取操作状态
     *
     * @return type - 操作状态
     */
    public Integer getType() {
        return type;
    }

    /**
     * 设置操作状态
     *
     * @param type 操作状态
     */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * @return log
     */
    public String getLog() {
        return log;
    }

    /**
     * @param log
     */
    public void setLog(String log) {
        this.log = log == null ? null : log.trim();
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
}