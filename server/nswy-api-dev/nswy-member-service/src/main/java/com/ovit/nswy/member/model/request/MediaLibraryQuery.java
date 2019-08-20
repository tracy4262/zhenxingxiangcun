package com.ovit.nswy.member.model.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import java.util.Date;

/**
 * Created by ${huipei.x} on 2017-11-16.
 */
@Data
@ApiModel(description="媒体库添加参数类")
public class MediaLibraryQuery {

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
    private Date createTime;

    /**
     * 媒体库路径
     */
    @ApiModelProperty(value = "媒体库路径")
    private String imageUrl;

    /**
     * 修改时间
     */

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

    @ApiModelProperty(value = "描述")
    private String mediaDescribe;


    @ApiModelProperty(value = "创建人")
    private String author;

    /**
     * 拍摄时间
     */
    @ApiModelProperty(value = "拍摄时间")
    private Date photoTime;

    /**
     * 拍摄地址
     */
    @ApiModelProperty(value = "拍摄地址")
    private String photoAddress;

}
