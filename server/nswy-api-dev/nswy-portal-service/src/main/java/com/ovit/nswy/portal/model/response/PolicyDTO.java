package com.ovit.nswy.portal.model.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * Created by ${huipei.x} on 2018-1-12.
 */
@Data
public class PolicyDTO {

    private Integer id;
    /**
     * 用户账号
     */
    @ApiModelProperty(value = "用户账号")
    private String account;

    /**
     * 政策法规标题
     */
    @ApiModelProperty(value = "政策法规标题")
    private String title;
    /**
     * 政策法规内容
     */
    @ApiModelProperty(value = "政策法规内容")
    private String content;
    /**
     * '政策法规地址
     */
    @ApiModelProperty(value = "政策法规地址")
    private String  addr ;
    /**
     * 政策法规描述'
     */
    @ApiModelProperty(value = "政策法规描述")
    private String  describe ;
    @ApiModelProperty(value = "时间")
    private Date createTime ;

    private String classifiedName;
    /**
     * 咨询类型
     */
    private String docType;
    /**
     * 咨询名称
     */
    private String docTypeName;

    private String columnType;

    private String informationDetailId;

    /**
     * 摘要
     */
    private String abstracts;
}
