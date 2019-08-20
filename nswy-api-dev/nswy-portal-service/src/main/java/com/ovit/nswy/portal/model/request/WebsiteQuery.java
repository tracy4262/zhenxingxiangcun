package com.ovit.nswy.portal.model.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by ${huipei.x} on 2018-1-25.
 */
@Data
public class WebsiteQuery {
    /**
     * 查询类型 1 个人 2 专家 3 企业 4 商城企业 5 机关 6 乡村
     */
    @ApiModelProperty(value = "查询类型 1 个人 2 专家 3 企业 4 商城企业 5 机关 6 乡村")
    private  Integer queryType;
    private String account;
}
