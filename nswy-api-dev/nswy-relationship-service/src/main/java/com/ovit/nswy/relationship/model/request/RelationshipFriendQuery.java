package com.ovit.nswy.relationship.model.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * Created by ${huipei.x} on 2017-11-23.
 */
@Data
@ApiModel(description="关系圈朋友添加和修改参数类")
public class RelationshipFriendQuery {
    /**
     * 朋友圈Id
     */
    @ApiModelProperty(value = "朋友圈Id 【添加不必传，修改必传】")
    private Integer friendId;

    /**
     * 账号
     */
    @ApiModelProperty(value = "账号")
    private String account;

    /**
     * 对方账号
     */
    @ApiModelProperty(value = "对方账号")
    private String fromAccount;

    /**
     * 同意类型
     */
    @ApiModelProperty(value = "同意类型 【忽略 1 同意 2】")
    private Integer agreeType;

    /**
     * 请求消息
     */
    @ApiModelProperty(value = "请求消息")
    private String requestMessage;

    /**
     * 分组id
     */
    @ApiModelProperty(value = "分组id [分组下的分组Id]")
    private Integer groupId;

    /**
     * 对方账号Id
     */
    @ApiModelProperty(value = "对方账号Id")
    private String fromAccountId;
    /**
     * 一键邀请好友
     */
    @ApiModelProperty(value = "一键邀请好友【'fromAccount'：'1'】")
    private String frindList;
}
