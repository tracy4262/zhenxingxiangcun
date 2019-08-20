package com.ovit.nswy.member.model.response;

import com.github.xphsc.collect.Maps;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Map;

/**
 * Created by ${huipei.x} on 2017-12-11.
 */
@Data
public class ListAppDTO {
    @ApiModelProperty(value = "应用Id")
    private Integer id;
    @ApiModelProperty(value = "账号")
    private String account;
    @ApiModelProperty(value = "应用是否已开始使用 0 未开始 1已开始")
    private Integer startFlag;
    @ApiModelProperty(value = "应用是否已开始使用 0 未开始 1已开始")
    private Map startFlagInfo= Maps.newHashMap();
}
