package com.ovit.nswy.portal.model.response;

import com.ovit.nswy.portal.model.ShopOrderProduct;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by  wangxy
 */
@Data
public class ShopOrderDTO {
    private Integer id;
    private String orderCode;
    private String account;
    private String sellAccount;
    private  String seller;
    private  String buyer;
    private Integer num;
    private Date createTime;
    private BigDecimal amount;
    private BigDecimal logisticAmount;
    private Integer status;
    private Integer sellStatus;
    private Integer hasComplaint;

    public List<ShopOrderProductDTO> getShopProducts() {
        return shopProducts;
    }

    public void setShopProducts(List<ShopOrderProductDTO> shopProducts) {
        this.shopProducts = shopProducts;
    }

    private  List<ShopOrderProductDTO> shopProducts;

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }
}
