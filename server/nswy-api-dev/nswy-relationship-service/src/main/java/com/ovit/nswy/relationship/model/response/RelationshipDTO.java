package com.ovit.nswy.relationship.model.response;

import com.github.xphsc.collect.Lists;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * Created by ${huipei.x} on 2017-11-23.
 */
@Data
@ApiModel(description="关系圈朋友集合结果参数类")
public class RelationshipDTO {
    @ApiModelProperty(value = "关系圈朋友集合")
    private List<GroupParentTypeDTO> groupParentList= Lists.newArrayList();
    private List<RelationshipGroupDTO> grouptList= Lists.newArrayList();
}
