package com.ovit.nswy.relationship.model.response;

import com.github.xphsc.collect.Maps;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.Map;

/**
 * Created by ${huipei.x} on 2017-11-23.
 */
@Data
@ApiModel(description="关系圈朋友结果参数类")
public class GetFriendRequestDTO {

    /**
     * 朋友圈Id
     */
    @ApiModelProperty(value = "朋友圈Id")
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
     * 同意类型{1,不同意，2，同意}
     */
    @ApiModelProperty(value = "同意类型{1,不同意，2，同意}")
    private Integer agreeType;

    /**
     * 请求消息
     */
    @ApiModelProperty(value = "请求消息")
    private String requestMessage;

    /**
     * 添加时间
     */
    @ApiModelProperty(value = "添加时间")
    private Date createTime;
    @ApiModelProperty(value = "对方账号Id")
    private String fromAccountId;
    private String avatar;
    private Map followInfo= Maps.newHashMap();
    private Map mutualGollowInfo=Maps.newHashMap();
    private String displayName;

    public Integer getFriendId() {
        return friendId;
    }

    public void setFriendId(Integer friendId) {
        this.friendId = friendId;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getFromAccount() {
        return fromAccount;
    }

    public void setFromAccount(String fromAccount) {
        this.fromAccount = fromAccount;
    }

    public Integer getAgreeType() {
        return agreeType;
    }

    public void setAgreeType(Integer agreeType) {
        this.agreeType = agreeType;
    }

    public String getRequestMessage() {
        return requestMessage;
    }

    public void setRequestMessage(String requestMessage) {
        this.requestMessage = requestMessage;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getFromAccountId() {
        return fromAccountId;
    }

    public void setFromAccountId(String fromAccountId) {
        this.fromAccountId = fromAccountId;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Map getFollowInfo() {
        return followInfo;
    }

    public void setFollowInfo(Map followInfo) {
        this.followInfo = followInfo;
    }

    public Map getMutualGollowInfo() {
        return mutualGollowInfo;
    }

    public void setMutualGollowInfo(Map mutualGollowInfo) {
        this.mutualGollowInfo = mutualGollowInfo;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

}
