package com.ovit.nswy.relationship.model.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by ${huipei.x} on 2017-11-23.
 */
@Data
@ApiModel(description="关系圈朋友列表参数类")
public class GetFriendRequestQuery extends BaseQuery {
    @ApiModelProperty(value = "用户账户")
    private String account ;
    @ApiModelProperty(value = "【最近历史 1】")
    private  Integer latelyType;//【最近历史 1】
}
