package com.ovit.nswy.portal.model.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by  wangxy
 */
@Data
public class ShopCardDTO {
    private Integer id;
    private String account;
    private  String seller;
    private Integer commodityTypeInfoId;
    private Integer num;
    private String storageStatus;
    private String isDelete;
    private Date createTime;
    private String productAccount;
    private String productOutputUnit;


    @ApiModelProperty(value = "商品图片")
    private String productPic;

    @ApiModelProperty(value = "商品名称")
    private  String productName;

    @ApiModelProperty(value = "商品当前单价")
    private  BigDecimal productPrice;


    @ApiModelProperty(value = "商品时价单价")
    private BigDecimal productOldPrice;

    @ApiModelProperty(value = "商品价格信息")
    private  String productPriceInfo;

    @ApiModelProperty(value = "库存数")
    private  Integer productNum;

    @ApiModelProperty(value = "最大限制数量")
    private  Integer maxNum;

    @ApiModelProperty(value = "最小数量")
    private  Integer minNum;

    private BigDecimal productLogisticAmount;

}
