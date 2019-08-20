package com.ovit.nswy.portal.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "shop_card")
public class ShopCard {
    @Id
    private Integer id;

    /**
     * 买家
     */
    private String account;

    /**
     * 商品
     */
    @Column(name = "commodity_type_info_id")
    private Integer commodityTypeInfoId;

    /**
     * 数量
     */
    private Integer num;

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
     * 获取商品
     *
     * @return commodity_type_info_id - 商品
     */
    public Integer getCommodityTypeInfoId() {
        return commodityTypeInfoId;
    }

    /**
     * 设置商品
     *
     * @param commodityTypeInfoId 商品
     */
    public void setCommodityTypeInfoId(Integer commodityTypeInfoId) {
        this.commodityTypeInfoId = commodityTypeInfoId;
    }

    /**
     * 获取数量
     *
     * @return num - 数量
     */
    public Integer getNum() {
        return num;
    }

    /**
     * 设置数量
     *
     * @param num 数量
     */
    public void setNum(Integer num) {
        this.num = num;
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