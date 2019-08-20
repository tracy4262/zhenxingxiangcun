package com.ovit.nswy.wiki.model.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by ${huipei.x} on 2017-11-1.
 */
@Data
@ApiModel(description="物种分类查询类")
public class SpeciesClassListQuery {
    @ApiModelProperty(value = "分类id")
    private String classId;//
     @ApiModelProperty(value = "分类父id")
    private String parentId;
}
