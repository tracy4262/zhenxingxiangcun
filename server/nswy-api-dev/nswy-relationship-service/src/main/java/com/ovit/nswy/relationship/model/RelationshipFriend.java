package com.ovit.nswy.relationship.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "relationship_friend")
public class RelationshipFriend {
    /**
     * 朋友圈Id
     */
    @Id
    @Column(name = "friend_id")
    private Integer friendId;

    /**
     * 账户
     */
    private String account;

    /**
     * 对方账户
     */
    @Column(name = "from_account")
    private String fromAccount;

    /**
     * 同意类型{1,不同意，2，同意}
     */
    @Column(name = "agree_type")
    private Integer agreeType;

    /**
     * 请求消息
     */
    @Column(name = "request_message")
    private String requestMessage;

    /**
     * 添加时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 修改时间
     */
    @Column(name = "update_time")
    private Date updateTime;

    /**
     * 是否已读{1 未读，2,已读}
     */
    @Column(name = "is_read")
    private Integer isRead;

    /**
     * 分组id
     */
    @Column(name = "group_id")
    private Integer groupId;

    /**
     * 对方账号Id
     */
    @Column(name = "from_account_id")
    private String fromAccountId;

    /**
     * 获取朋友圈Id
     *
     * @return friend_id - 朋友圈Id
     */
    public Integer getFriendId() {
        return friendId;
    }

    /**
     * 设置朋友圈Id
     *
     * @param friendId 朋友圈Id
     */
    public void setFriendId(Integer friendId) {
        this.friendId = friendId;
    }

    /**
     * 获取账户
     *
     * @return account - 账户
     */
    public String getAccount() {
        return account;
    }

    /**
     * 设置账户
     *
     * @param account 账户
     */
    public void setAccount(String account) {
        this.account = account == null ? null : account.trim();
    }

    /**
     * 获取对方账户
     *
     * @return from_account - 对方账户
     */
    public String getFromAccount() {
        return fromAccount;
    }

    /**
     * 设置对方账户
     *
     * @param fromAccount 对方账户
     */
    public void setFromAccount(String fromAccount) {
        this.fromAccount = fromAccount == null ? null : fromAccount.trim();
    }

    /**
     * 获取同意类型{1,不同意，2，同意}
     *
     * @return agree_type - 同意类型{1,不同意，2，同意}
     */
    public Integer getAgreeType() {
        return agreeType;
    }

    /**
     * 设置同意类型{1,不同意，2，同意}
     *
     * @param agreeType 同意类型{1,不同意，2，同意}
     */
    public void setAgreeType(Integer agreeType) {
        this.agreeType = agreeType;
    }

    /**
     * 获取请求消息
     *
     * @return request_message - 请求消息
     */
    public String getRequestMessage() {
        return requestMessage;
    }

    /**
     * 设置请求消息
     *
     * @param requestMessage 请求消息
     */
    public void setRequestMessage(String requestMessage) {
        this.requestMessage = requestMessage == null ? null : requestMessage.trim();
    }

    /**
     * 获取添加时间
     *
     * @return create_time - 添加时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置添加时间
     *
     * @param createTime 添加时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取修改时间
     *
     * @return update_time - 修改时间
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 设置修改时间
     *
     * @param updateTime 修改时间
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 获取是否已读{1 未读，2,已读}
     *
     * @return is_read - 是否已读{1 未读，2,已读}
     */
    public Integer getIsRead() {
        return isRead;
    }

    /**
     * 设置是否已读{1 未读，2,已读}
     *
     * @param isRead 是否已读{1 未读，2,已读}
     */
    public void setIsRead(Integer isRead) {
        this.isRead = isRead;
    }

    /**
     * 获取分组id
     *
     * @return group_id - 分组id
     */
    public Integer getGroupId() {
        return groupId;
    }

    /**
     * 设置分组id
     *
     * @param groupId 分组id
     */
    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    /**
     * 获取对方账号Id
     *
     * @return from_account_id - 对方账号Id
     */
    public String getFromAccountId() {
        return fromAccountId;
    }

    /**
     * 设置对方账号Id
     *
     * @param fromAccountId 对方账号Id
     */
    public void setFromAccountId(String fromAccountId) {
        this.fromAccountId = fromAccountId;
    }
}