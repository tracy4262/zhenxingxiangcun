package com.ovit.nswy.portal.model.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by  wangxy
 */
@Data
public class ShopProductDTO {
    private  String seller;
    private Integer commodityTypeInfoId;

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    private Integer num;
    private String productAccount;

    private String productPic;

    private  String productName;

    private  BigDecimal productPrice;

    private BigDecimal productOldPrice;

    private  String productPriceInfo;

    private BigDecimal productLogisticAmount;

    private String productOutputUnit;

}
