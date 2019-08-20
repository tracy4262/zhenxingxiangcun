package com.ovit.nswy.portal.model.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by ${huipei.x} on 2018-1-12.
 */
@Data
public class ShopOrderCommentQuery {

    /**
     * 用户类型，0，买家，1，卖家
     */
    @ApiModelProperty(value = "用户类型，0，买家，1，卖家")
    private Integer type;

    /**
     *  商品名
     */
    private  String orderCode;
    /**
     *  商品名
     */
    private  String productCode;


}
