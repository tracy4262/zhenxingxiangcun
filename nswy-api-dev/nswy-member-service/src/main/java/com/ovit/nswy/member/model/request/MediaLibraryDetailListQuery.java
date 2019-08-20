package com.ovit.nswy.member.model.request;

import com.ovit.nswy.frame.common.request.BaseQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * Created by ${huipei.x} on 2017-11-16.
 */
@Data
@ApiModel(description="媒体库详情查询参数类")
public class MediaLibraryDetailListQuery extends BaseQuery{

    /**
     * 媒体库Id
     */
    @ApiModelProperty("媒体库Id")
    private Integer mediaId;

    /**
     * 账号
     */
    @ApiModelProperty("账号")
    private String account;
}
