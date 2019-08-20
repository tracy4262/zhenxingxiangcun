package com.ovit.nswy.wiki.model.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by ${huipei.x} on 2017-12-4.
 */
@Data
@ApiModel(description="物种详情参数类")
public class SpeciesDetailQuery {
    @ApiModelProperty("索引id")
    private String indexid;
    @ApiModelProperty("物种名称")
    private String name;

}
