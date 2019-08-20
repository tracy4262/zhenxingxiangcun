package com.ovit.nswy.portal.model.request;

import com.ovit.nswy.frame.common.request.BaseQuery;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by ${huipei.x} on 2018-1-12.
 */
@Data
public class ExpertQuery extends BaseQuery {

    private String  account;
    /**
     * 用户类型 [0个人用户 1企业 2政府 3机关 4专家 5 乡村]
     */
    @ApiModelProperty(value = "用户类型 [0个人用户 1企业 2政府 3机关 4专家 5 乡村]")
    private Integer userType;

    /**
     * 查询类型 1 个人 2 专家 3 企业 4 商城企业 5 机关 6 乡村
     */

    private Integer queryType;

}


