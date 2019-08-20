package com.ovit.nswy.relationship.model.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by ${huipei.x} on 2017-11-22.
 */
@Data
@ApiModel(description="关系圈分组添加和修改参数类")
public class RelationshipGroupQuery {

    /**
     * 分组Id
     */
    @ApiModelProperty(value = "分组Id 【添加不必传，修改必传】")
    private Integer gruopId;

    /**
     * 用户账户
     */
    @ApiModelProperty(value = "用户账户")
    private String account;

    /**
     * f分组名称
     */
    @ApiModelProperty(value = "分组名称")
    private String gruopName;

    /**
     * 分组类型
     */
    @ApiModelProperty(value = "分组类型 ")
    private Integer groupType;

    /**
     * 父id
     */
    @ApiModelProperty(value = "父id 【添加父名称不必传，添加父分组下面的分组必传】")
    private Integer parentId;

}
