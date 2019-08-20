package com.ovit.nswy.portal.model.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.math.BigDecimal;

/**
 * Created by  wangxy
 */
@Data
public class ShopOrderProductDTO {
    private  Integer id;
    private Integer commodityTypeInfoId;
    private Integer number;
    private String productPic;

    private String productName;

    private BigDecimal amount;

    private BigDecimal logisticAmount;

    private String productOutputUnit;


}
