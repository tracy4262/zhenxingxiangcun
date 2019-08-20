package com.ovit.nswy.relationship.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "friend_chat")
public class FriendChat {
    @Id
    @Column(name = "` id`")
    private Integer id;

    /**
     * 发送消息用户ID
     */
    @Column(name = "from_account")
    private Integer fromAccount;

    /**
     * 接收消息用户ID
     */
    @Column(name = "to_account")
    private Integer toAccount;

    /**
     * 消息内容
     */
    private String content;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "is_read")
    private Integer isRead;

    /**
     * 修改时间
     */
    @Column(name = "update_time")
    private Date updateTime;

    /**
     * 消息类型【1 朋友分组，2 群组】
     */
    @Column(name = "chat_type")
    private Integer chatType;

    /**
     * @return  id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取发送消息用户ID
     *
     * @return from_account - 发送消息用户ID
     */
    public Integer getFromAccount() {
        return fromAccount;
    }

    /**
     * 设置发送消息用户ID
     *
     * @param fromAccount 发送消息用户ID
     */
    public void setFromAccount(Integer fromAccount) {
        this.fromAccount = fromAccount;
    }

    /**
     * 获取接收消息用户ID
     *
     * @return to_account - 接收消息用户ID
     */
    public Integer getToAccount() {
        return toAccount;
    }

    /**
     * 设置接收消息用户ID
     *
     * @param toAccount 接收消息用户ID
     */
    public void setToAccount(Integer toAccount) {
        this.toAccount = toAccount;
    }

    /**
     * 获取消息内容
     *
     * @return content - 消息内容
     */
    public String getContent() {
        return content;
    }

    /**
     * 设置消息内容
     *
     * @param content 消息内容
     */
    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    /**
     * 获取创建时间
     *
     * @return create_time - 创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置创建时间
     *
     * @param createTime 创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * @return is_read
     */
    public Integer getIsRead() {
        return isRead;
    }

    /**
     * @param isRead
     */
    public void setIsRead(Integer isRead) {
        this.isRead = isRead;
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
     * 获取消息类型【1 朋友分组，2 群组】
     *
     * @return chat_type - 消息类型【1 朋友分组，2 群组】
     */
    public Integer getChatType() {
        return chatType;
    }

    /**
     * 设置消息类型【1 朋友分组，2 群组】
     *
     * @param chatType 消息类型【1 朋友分组，2 群组】
     */
    public void setChatType(Integer chatType) {
        this.chatType = chatType;
    }
}