package com.ovit.nswy.wiki.model.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
@ApiModel(description="物种自定义目录查询类")
public class SpeciesPropertyListQuery extends BaseQuery {

    @ApiModelProperty("用户id")
    private String userId;
    @ApiModelProperty("关键字")
    private String keywords;
    @ApiModelProperty("排序{1：正序，2：倒序}")
    private Integer sortType;
    @ApiModelProperty("物种Id")
    private String speciesId;
    @ApiModelProperty("自定义目录的id")
    private String propertyid;
    //审核状态: 0 更新待审核,2 新增待审核,3 删除待审核,1 审核通过,4 未审核通过,5 审核完成
    @ApiModelProperty("审核状态")
    private Integer auditstatus;

}
