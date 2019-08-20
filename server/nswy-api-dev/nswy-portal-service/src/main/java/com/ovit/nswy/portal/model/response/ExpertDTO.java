package com.ovit.nswy.portal.model.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * Created by ${huipei.x} on 2018-1-12.
 */
@Data
public class ExpertDTO {

    private Integer id;
    /**
     *账号
     */
    @ApiModelProperty(value = "账号")
    private String loginAccount;
    /**
     *专家名称
     */
    @ApiModelProperty(value = "专家名称")
    private String expertName;
    /**
     *职务
     */
    @ApiModelProperty(value = "职务")
    private String position;
    /**
     *职务名称
     */
    @ApiModelProperty(value = "职务名称")
    private String positionName;
    /**
     *个人介绍
     */
    @ApiModelProperty(value = "个人介绍")
    private String selfIntroduce;
    /**
     *个人照片
     */
    @ApiModelProperty(value = "个人照片")
    private String personalPicture;
    /**
     *擅长领域
     */
    @ApiModelProperty(value = "擅长领域")
    private String adeptField;
    /**
     * 电话
     */
    @ApiModelProperty(value = "电话")
    private String phone;

    private String expertType ;

    private String userId;


}
