package com.ovit.nswy.relationship.model.Relationship;

/**
 * 关系圈分组下好友
 *
 * @author haoWen
 * @create 2018-03-05 15:12
 **/

public class FriendList {

    private String friendId;
    private String account;
    private String fromAccount;
    private String agreeType;
    private String requestMessage;
    private String createTime;
    private String updateTime;
    private String isRead;
    private String groupId;
    private String fromAccountId;
    private String status;
    private String displayName;
    private String avatar;
    private String userType;

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getFriendId() {
        return friendId;
    }

    public void setFriendId(String friendId) {
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

    public String getAgreeType() {
        return agreeType;
    }

    public void setAgreeType(String agreeType) {
        this.agreeType = agreeType;
    }

    public String getRequestMessage() {
        return requestMessage;
    }

    public void setRequestMessage(String requestMessage) {
        this.requestMessage = requestMessage;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getIsRead() {
        return isRead;
    }

    public void setIsRead(String isRead) {
        this.isRead = isRead;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getFromAccountId() {
        return fromAccountId;
    }

    public void setFromAccountId(String fromAccountId) {
        this.fromAccountId = fromAccountId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "FriendList{" +
                "friendId='" + friendId + '\'' +
                ", account='" + account + '\'' +
                ", fromAccount='" + fromAccount + '\'' +
                ", agreeType='" + agreeType + '\'' +
                ", requestMessage='" + requestMessage + '\'' +
                ", createTime='" + createTime + '\'' +
                ", updateTime='" + updateTime + '\'' +
                ", isRead='" + isRead + '\'' +
                ", groupId='" + groupId + '\'' +
                ", fromAccountId='" + fromAccountId + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
