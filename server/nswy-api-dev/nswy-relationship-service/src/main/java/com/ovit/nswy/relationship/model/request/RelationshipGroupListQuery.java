package com.ovit.nswy.relationship.model.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by ${huipei.x} on 2017-11-23.
 */
@Data
@ApiModel(description="关系圈分组列表参数类")
public class RelationshipGroupListQuery  {
    @ApiModelProperty(value = "用户账户")
    private String account;
    @ApiModelProperty(value = "父id [不传默认查询父类型下面的分组或分组下面的朋友]")
    private Integer parentId;
    @ApiModelProperty(value = "分组分级 [父分级0,第二级 1 ,第三级 2,第四级 3]")
    private Integer groupGrade;

    @Override
    public String toString() {
        return "RelationshipGroupListQuery{" +
                "account='" + account + '\'' +
                ", parentId=" + parentId +
                ", groupGrade=" + groupGrade +
                '}';
    }
}
