package com.ovit.nswy.relationship.model.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by ${huipei.x} on 2018-1-10.
 */
@Data
public class LiveQuery {


    private Integer liveId;

    /**
     * 房间标题
     */
    @ApiModelProperty(value = "房间标题")
    private String liveName;

    /**
     * 房间描述
     */
    @ApiModelProperty(value = "房间描述")
    private String liveDescribe;

    /**
     * 房间封面
     */
    @ApiModelProperty(value = "房间封面")
    private String liveImage;



    /**
     * 用户账号
     */
    @ApiModelProperty(value = "用户账号")
    private String account;

    /**
     * 分类Id
     */
    @ApiModelProperty(value = "分类Id")
    private Integer liveCategoryId;


}
