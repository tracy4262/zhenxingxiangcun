package com.ovit.nswy.relationship.model.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * Created by ${huipei.x} on 2017-12-5.
 */
@Data
@ApiModel(description="关系圈朋友删除参数类")
public class DeleteFriendQuery {
@ApiModelProperty(value = "朋友圈Id ")
 private Integer friendId;
    @ApiModelProperty(value = "朋友圈数组Id  \"friendIds\":[1,2]  ")
    private List<Object> friendIds;
}
