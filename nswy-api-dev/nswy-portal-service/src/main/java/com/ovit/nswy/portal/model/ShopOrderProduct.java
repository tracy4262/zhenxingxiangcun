package com.ovit.nswy.portal.model;

import sun.security.krb5.internal.crypto.Des;

import javax.persistence.*;
import java.math.BigDecimal;

@Table(name = "shop_order_product")
public class ShopOrderProduct {
    @Id
    private Integer id;

    /**
     * 订单号
     */
    @Column(name = "order_code_id")
    private String orderCodeId;

    /**
     * 商品
     */
    @Column(name = "commodity_type_info_id")
    private Integer commodityTypeInfoId;


    /**ShopOrderProduct
     * 商品名称
     */
    @Column(name = "product_name")
    private String productName;

    /**
     * 商品缩略图
     */
    @Column(name = "product_pic")
    private String productPic;

    /**
     * 金额
     */
    private BigDecimal amount;

    /**
     * 数量
     */
    private Integer number;

    //单位
    @Column(name = "product_output_unit")
    private String productOutputUnit;

    /**
     * 运费
     */
    @Column(name = "logistic_amount")
    private BigDecimal logisticAmount;

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
     * 获取订单号
     *
     * @return order_code_id - 订单号
     */
    public String getOrderCodeId() {
        return orderCodeId;
    }

    /**
     * 设置订单号
     *
     * @param orderCodeId 订单号
     */
    public void setOrderCodeId(String orderCodeId) {
        this.orderCodeId = orderCodeId == null ? null : orderCodeId.trim();
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
        this.commodityTypeInfoId =   commodityTypeInfoId;
    }

    /**
     * 获取金额
     *
     * @return amount - 金额
     */
    public BigDecimal getAmount() {
        return amount;
    }

    /**
     * 设置金额
     *
     * @param amount 金额
     */
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    /**
     * 获取数量
     *
     * @return number - 数量
     */
    public Integer getNumber() {
        return number;
    }

    /**
     * 设置数量
     *
     * @param number 数量
     */
    public void setNumber(Integer number) {
        this.number = number;
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

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductPic() {
        return productPic;
    }

    public void setProductPic(String productPic) {
        this.productPic = productPic;
    }

    public String getProductOutputUnit() {
        return productOutputUnit;
    }

    public void setProductOutputUnit(String productOutputUnit) {
        this.productOutputUnit = productOutputUnit;
    }
}