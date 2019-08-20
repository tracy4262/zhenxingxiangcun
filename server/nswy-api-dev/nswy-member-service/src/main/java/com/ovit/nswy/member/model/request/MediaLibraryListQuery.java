package com.ovit.nswy.member.model.request;



import com.ovit.nswy.frame.common.request.BaseQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * Created by ${huipei.x} on 2017-11-16.
 */
@Data
@ToString
@ApiModel(description="媒体库查询参数类")
public class MediaLibraryListQuery extends BaseQuery {
    /**
     * 媒体库类型 1：相册，2：视频:，3：文档
     */
    @ApiModelProperty("媒体库类型{媒体库类型 1：相册，2：视频:，3：文档}")
    private Integer mediaType;

    /**
     * 用户名
     */
    @ApiModelProperty("用户名")
    private String account;

}
