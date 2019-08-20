package com.ovit.nswy.wiki.model.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by ${huipei.x} on 2017-11-1.
 */
@Data
@ApiModel(description="虫害查询参数类")
public class SpeciesPestListQuery  extends BaseQuery{
    @ApiModelProperty("用户id")
    private String userId;
    @ApiModelProperty("关键字")
    private String keywords;
    @ApiModelProperty("排序{1：正序，2：倒序}")
    private Integer sortType;//{1：正序，2：倒序}
    @ApiModelProperty("物种Id")
    private String speciesId;
    //审核状态: 0 更新待审核,2 新增待审核,3 删除待审核,1 审核通过,4 未审核通过,5 审核完成
    @ApiModelProperty("审核状态")
    private Integer auditstatus;
    @ApiModelProperty("虫害id")
    private String fid;
    @ApiModelProperty("校验名称是否重复")
    private String validName;
    @ApiModelProperty("虫害名称")
    private String fname;
    @ApiModelProperty(value = "索引Id")
    private String indexid;
}
