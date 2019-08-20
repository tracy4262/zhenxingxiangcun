package com.ovit.nswy.member.model.response;

import com.github.xphsc.collect.Maps;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.util.Date;
import java.util.Map;

/**
 * Created by ${huipei.x} on 2017-11-16.
 */
@Data
@ApiModel(description="媒体库结果列表类")
public class MediaLibraryDTO {
    /**
     * 媒体库Id
     */
    @ApiModelProperty(value = "媒体库Id")
    private Integer mediaId;

    /**
     * 媒体库类型 1：相册，2：视频:，3：文档
     */
    @ApiModelProperty(value = "媒体库类型{媒体库类型 1：相册，2：视频:，3：文档}")
    private Integer mediaType;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private Date createTime;


    /**
     * 修改时间
     */
    @ApiModelProperty(value = "修改时间")
    private Date updateTime;

    /**
     * 媒体库名称
     */
    @ApiModelProperty(value = "媒体库名称")
    private String mediaName;

    /**
     * 用户名
     */
    private String account;
    @ApiModelProperty(value = "媒体库类型转义")
    private Map mediaTypeInfo= Maps.newHashMap();
    @ApiModelProperty(value = "媒体库路径")
    private String imageUrl;
    @ApiModelProperty(value = "统计详情数")
    private Integer countDetail;

    @ApiModelProperty(value = "描述")
    private String mediaDescribe;

    @ApiModelProperty(value = "创建人")
    private String author;

    @ApiModelProperty(value = "拍摄时间")
    private Date photoTime;

    @ApiModelProperty(value = "拍摄地点")
    private String photoAddress;

}
