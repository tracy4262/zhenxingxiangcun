package com.ovit.nswy.member.model.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * Created by ${huipei.x} on 2017-12-11.
 */
@Data
@ApiModel(description="应用结果列表类")
public class MyAppDTO {

    @ApiModelProperty(value = "应用Id")
    private Integer id;
    @ApiModelProperty(value = "账号")
    private String account;
    @ApiModelProperty(value = "我的关注对象")
    private String appid;
    @ApiModelProperty(value = "应用类型 0 基本应用 1 高级应用")
    private Integer level;

    private Date createTime;
    @ApiModelProperty(value = "应用是否已开始使用 0 未开始 1已开始")
    private Integer startFlag;
}
