package com.ovit.nswy.relationship.model.response;

import com.google.common.collect.Lists;
import com.ovit.nswy.relationship.model.RelationshipFriend;
import com.ovit.nswy.relationship.model.RelationshipGroup;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * Created by ${huipei.x} on 2017-11-23.
 */
@Data
@ApiModel(description="关系圈父分组参数类")
public class GroupParentTypeDTO {
    @ApiModelProperty(value = "父分组Id")
    private Integer gruopId;
    @ApiModelProperty(value = "父分组名称")
    private String gruopName;

    /**
     * 分组类型
     */
    @ApiModelProperty(value = "分组类型")
    private Integer groupType;

    List<RelationshipGroupDTO> groupList= Lists.newArrayList();
    private List<GetFriendRequestDTO> friendList= Lists.newArrayList();

}
