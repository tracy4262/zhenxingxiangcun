package com.ovit.nswy.wiki.model.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by ${huipei.x} on 2017-11-3.
 */
@Data
public class SpeciesCatalogQuery extends BaseQuery {
    @ApiModelProperty("用户id")
    private String userId;
    @ApiModelProperty(value = "物种Id")
    private String species_id;//物种Id;
    @ApiModelProperty(value = "自定义目录id")
    private String propertyid;
    //审核状态: 0 更新待审核,2 新增待审核,3 删除待审核,1 审核通过,4 未审核通过,5 审核完成
    @ApiModelProperty("审核状态")
    private Integer auditstatus;
}
