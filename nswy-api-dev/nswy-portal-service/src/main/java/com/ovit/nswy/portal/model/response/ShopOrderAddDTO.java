package com.ovit.nswy.portal.model.response;

import com.ovit.nswy.portal.model.ShopOrderProduct;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by  wangxy 发票信息
 */
@Data
public class ShopOrderAddDTO {

    private Integer id;
    private Integer type;
    private String account;
    private String sellAccount;
    private String seller;
    private String buyer;
    private String orderCode;
    private BigDecimal amount;
    private BigDecimal logisticAmount;
    private Integer invoiceType;
    private Integer invoiceMode;
    private String invoiceInfo;
    private String addressInfo;
    private Integer status;
    private String remark;
    private Date createTime;
    private Date updateTime;
    private  List<ShopOrderSellDTO> shopProducts;//商品信息
    public List<ShopOrderSellDTO> getShopProducts() {
        return shopProducts;
    }

    public void setShopProducts(List<ShopOrderSellDTO> shopProducts) {
        this.shopProducts = shopProducts;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
