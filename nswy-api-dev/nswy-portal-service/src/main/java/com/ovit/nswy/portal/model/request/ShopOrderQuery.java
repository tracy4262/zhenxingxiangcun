package com.ovit.nswy.portal.model.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by ${huipei.x} on 2018-1-12.
 */
@Data
public class ShopOrderQuery {

    /**
     * 用户类型，0，买家，1，卖家
     */
    @ApiModelProperty(value = "用户类型，0，买家，1，卖家")
    private Integer type;

    /**
     *  状态，0，带发货，1，待收货，2，待评价
     */

    private Integer status;

    /**
     *  商品名
     */
    private  String productName;

    /**
     *  卖家家名称
     */
    private  String seller;

    /**
     *  买家名称
     */
    private  String buyer;

    private  String startDate;
    private  String endDate;
}
