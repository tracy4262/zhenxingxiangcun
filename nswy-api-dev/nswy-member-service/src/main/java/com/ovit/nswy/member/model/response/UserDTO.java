package com.ovit.nswy.member.model.response;

import com.google.common.collect.Maps;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.Map;

/**
 * Created by ${huipei.x} on 2017-12-5.
 */
@Data
@ApiModel(description="用户列表参数类")
public class UserDTO {

    @ApiModelProperty(value = "用户Id")
    private Integer id;

    private String uniqueId;
    @ApiModelProperty(value = "登录账号")
    private String loginAccount;
    @ApiModelProperty(value = "昵称")
    private String displayName;
    @ApiModelProperty(value = "登录密码")
    private String loginPasswd;
    @ApiModelProperty(value = "注册时间")
    private Date registerTime;

    private String isIdentityVerification;

    private Integer tourNumber;
    @ApiModelProperty(value = "用户类型")
    private Integer userType;

    private String signaTure;
    @ApiModelProperty(value = "头像")
    private String avatar;
    private String follow;
    @ApiModelProperty(value = "关注【1 已关注，2未关注】")

    private Map followInfo= Maps.newHashMap();

    private String location;

}
