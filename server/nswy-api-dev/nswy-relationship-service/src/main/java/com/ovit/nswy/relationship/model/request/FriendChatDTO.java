package com.ovit.nswy.relationship.model.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by ${huipei.x} on 2018-1-2.
 */
@Data
@ApiModel(description="IM参数类")
public class FriendChatDTO {
    @ApiModelProperty(value = "账号ID")
    private Integer  fromid;
    @ApiModelProperty(value = "对方账号ID")
    private Integer  toid;
    @ApiModelProperty(value = "消息内容")
    private String  content;
    @ApiModelProperty(value = "消息类型【1，朋友 2 群组】")
    private String  type;

}
