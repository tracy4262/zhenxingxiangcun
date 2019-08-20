package com.ovit.nswy.relationship.model.response;


import com.google.common.collect.Lists;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * Created by ${huipei.x} on 2017-11-23.
 */
@Data
@ApiModel(description="关系圈分组结果参数类")
public class RelationshipGroupDTO {

    @ApiModelProperty(value = "分组Id")
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
    @ApiModelProperty(value = "分组类型")
    private Integer groupType;
    /**
     * 分组级别
     */

    private Integer groupGrade;
    /**
     * 父id
     */
    @ApiModelProperty(value = "父id")
    private Integer parentId;

   private List<GetFriendRequestDTO> friendList= Lists.newArrayList();
    private  List<RelationshipGroupOneDTO> groupList= Lists.newArrayList();

}
