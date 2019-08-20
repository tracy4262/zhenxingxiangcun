package com.ovit.nswy.member.model.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * Created by ${huipei.x} on 2017-11-16.
 */
@Data
@ApiModel(description="媒体库结果详情列表类")
public class MediaLibraryDetaiDTO {

    /**
     * 媒体库详情id
     */
    @ApiModelProperty(value = "媒体库详情id")
    private Integer id;

    /**
     * 描述
     */
    @ApiModelProperty(value = "描述")
    private String mediaDescribe;

    /**
     * 媒体库路径
     */
    @ApiModelProperty(value = "媒体库路径")
    private String mediaUrl;

    /**
     * 媒体库Id
     */
    @ApiModelProperty(value = "媒体库Id")
    private Integer mediaId;

    /**
     * 名称
     */
    @ApiModelProperty(value = "名称")
    private String name;

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

    private String time;

    @ApiModelProperty(value = "创建人")
    private String author;

    /**
     * 修改时间
     */
    @ApiModelProperty(value = "拍摄时间")
    private Date photoTime;


    @ApiModelProperty(value = "拍摄地址")
    private String photoAddress;

}
