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
public class ShopOrderSellDTO {


    private String productAccount;
    private List<ShopOrderProduct> data;//商品信息

    public List<ShopOrderProduct> getData() {
        return data;
    }

    public void setData(List<ShopOrderProduct> data) {
        this.data = data;
    }

    public String getProductAccount() {
        return productAccount;
    }

    public void setProductAccount(String productAccount) {
        this.productAccount = productAccount;
    }
}
