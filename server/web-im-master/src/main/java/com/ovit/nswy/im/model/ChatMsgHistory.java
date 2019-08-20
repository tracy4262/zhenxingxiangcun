package com.ovit.nswy.im.model;

/**
 * DESCRIPTION:
 * 聊天历史记录
 * @author zouyan
 * @create 2018-01-26 下午3:23
 * created by fuck~
 **/
public class ChatMsgHistory {
    //用户id
    private String id;
    //用户名
    private String username;
    //用户头像
    private String avatar;
    //消息时间
    private long timestamp;
    //消息内容
    private String content;

    public ChatMsgHistory() {

    }

    public ChatMsgHistory(String id, String username, String avatar, String content, long timestamp) {
        this.id = id;
        this.username = username;
        this.avatar = avatar;
        this.content = content;
        this.timestamp = timestamp;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "ChatMsgHistory{" +
                "id='" + id + '\'' +
                ", username='" + username + '\'' +
                ", avatar='" + avatar + '\'' +
                ", timestamp=" + timestamp +
                ", content='" + content + '\'' +
                '}';
    }
}
