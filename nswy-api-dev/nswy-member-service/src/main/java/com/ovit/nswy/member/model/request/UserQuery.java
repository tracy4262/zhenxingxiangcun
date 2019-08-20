package com.ovit.nswy.member.model.request;

import com.ovit.nswy.frame.common.request.BaseQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by ${huipei.x} on 2017-12-5.
 */
@Data
@ApiModel(description="用户列表参数类")
public class UserQuery extends BaseQuery {

    @ApiModelProperty(value = "用户类型 【 0 个人用户  1 企业 2政府 3机关4专家】")
   private Integer userType;
    @ApiModelProperty("关键字[账号，用户名]")
    private String keywords;
    @ApiModelProperty("用户账号")
   private String account;
    /**
     * 相关物种
     */
    private String species;
    /**
     * 地理位置
     */
    private String areas;
    /**
     * 相关产品
     */
    private String products;
    /**
     * 相关服务
     */
    private String services;
    /**
     * 相关行业
     */
    private String industrys;
    /**
     * 职能部门
     */
    private String  departments;
 /**
  *  关注类型 1 添加关注
  */
    private Integer  followType;
}
