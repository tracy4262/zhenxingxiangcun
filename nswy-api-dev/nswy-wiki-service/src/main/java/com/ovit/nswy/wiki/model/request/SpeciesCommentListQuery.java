package com.ovit.nswy.wiki.model.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by ${huipei.x} on 2017-11-1.
 */
@Data
@ApiModel(description="评论查询类")
public class SpeciesCommentListQuery extends BaseQuery {
    @ApiModelProperty("排序{1：正序，2：倒序}")
    private Integer sortType;//{1：正序，2：倒序}
}
