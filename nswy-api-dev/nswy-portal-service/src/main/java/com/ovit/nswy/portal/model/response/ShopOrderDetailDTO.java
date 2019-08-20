package com.ovit.nswy.portal.model.response;

import com.ovit.nswy.portal.model.ShopInvoicePersonal;
import com.ovit.nswy.portal.model.ShopInvoiceTax;
import com.ovit.nswy.portal.model.ShopOrderProduct;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by  wangxy 发票信息
 */
@Data
public class ShopOrderDetailDTO {

    private Integer id;
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
    private Integer sellStatus;
    private Integer hasComplaint;
    private String remark;
    private Date createTime;
    private Date updateTime;
    private  List<ShopOrderProduct> shopProducts;//商品信息
    public List<ShopOrderProduct> getShopProducts() {
        return shopProducts;
    }

    public void setShopProducts(List<ShopOrderProduct> shopProducts) {
        this.shopProducts = shopProducts;
    }
}
