package com.ovit.nswy.member.model.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * Created by ${huipei.x} on 2017-11-16.
 */

@Data
@ApiModel(description="媒体库详情添加修改参数类")
public class MediaLibraryDetailQuery {

    private Integer id;

    /**
     * 描述
     *
     */
    @ApiModelProperty(value = "描述")
    private String mediaDescribe;

    /**
     * 媒体库路径
     */
    @ApiModelProperty(value = "媒体库路径 数组[1,2]",notes = "字符串数组[1,2]")
    private String mediaUrl;

    /**
     * 媒体库Id
     */
    @ApiModelProperty(value = "媒体库Id")
    private Integer mediaId;

    /**
     * 名称
     */
    @ApiModelProperty(value = "图片名称")
    private String name;

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
