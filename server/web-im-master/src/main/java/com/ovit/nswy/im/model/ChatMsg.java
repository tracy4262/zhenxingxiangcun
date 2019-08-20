package com.ovit.nswy.im.model;

/**
 * DESCRIPTION:
 * 聊天消息
 * 数据示例：
 * {
     username: "纸飞机" //消息来源用户名
     ,avatar: "http://tp1.sinaimg.cn/1571889140/180/40030060651/1" //消息来源用户头像
     ,id: "100000" //消息的来源ID（如果是私聊，则是用户id，如果是群聊，则是群组id）
     ,type: "friend" //聊天窗口来源类型，从发送消息传递的to里面获取
     ,content: "嗨，你好！本消息系离线消息。" //消息内容
     ,cid: 0 //消息id，可不传。除非你要对消息进行一些操作（如撤回）
     ,mine: false //是否我发送的消息，如果为true，则会显示在右方
     ,fromid: "100000" //消息的发送者id（比如群组中的某个消息发送者），可用于自动解决浏览器多窗口时的一些问题
     ,timestamp: 1467475443306 //服务端时间戳毫秒数。注意：如果你返回的是标准的 unix 时间戳，记得要 *1000
  }
 * @author zouyan
 * @create 2018-01-25 下午3:38
 * created by fuck~
 **/
public class ChatMsg {

    //消息来源用户名(如："纸飞机")
    private final String username;
    //消息来源用户头像(如："http://tp1.sinaimg.cn/1571889140/180/40030060651/1")
    private final String avatar;
    //消息的来源ID（如果是私聊，则是用户id，如果是群聊，则是群组id）(如："100000")
    // 对应chat_log(from_id)
    private final String id;
    //聊天窗口来源类型，从发送消息传递的to里面获取(如："friend")
    private final String type;
    //消息内容(如："嗨，你好！本消息系离线消息。")
    private final String content;
    //消息id，可不传。除非你要对消息进行一些操作（如撤回）(如："0")
    private final int cid;
    //是否我发送的消息，如果为true，则会显示在右方(如："false")
    private final boolean mine;
    //消息的发送者id（比如群组中的某个消息发送者），可用于自动解决浏览器多窗口时的一些问题(如："100000")
    private final String fromid;
    //服务端时间戳毫秒数。注意：如果你返回的是标准的 unix 时间戳，记得要 *1000 (如："1467475443306")
    private final long timestamp;

    private ChatMsg(Builder builder) {
        username = builder.username;
        avatar = builder.avatar;
        id = builder.id;
        type = builder.type;
        content = builder.content;
        cid = builder.cid;
        mine = builder.mine;
        fromid = builder.fromid;
        timestamp = builder.timestamp;
    }

    public static class Builder {
        private String username;
        private String avatar;
        private String id;
        private String type;
        private String content;
        private int cid = 0;//消息id，可不传
        private boolean mine = false;//是否我发送的消息，如果为true，则会显示在右方
        private String fromid;
        private long timestamp;

        public Builder(String username, String avatar, String id, String type, String content, String fromid, long timestamp) {
            this.username = username;
            this.avatar = avatar;
            this.id = id;
            this.type = type;
            this.content = content;
            this.fromid = fromid;
            this.timestamp = timestamp;
        }

        public ChatMsg build() {
            return new ChatMsg(this);
        }

        public ChatMsg.Builder cid(int cid) {
            this.cid = cid;
            return this;
        }

        public ChatMsg.Builder mine(boolean mine) {
            this.mine = mine;
            return this;
        }

    }

    public String getUsername() {
        return username;
    }

    public String getAvatar() {
        return avatar;
    }

    public String getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public String getContent() {
        return content;
    }

    public int getCid() {
        return cid;
    }

    public boolean isMine() {
        return mine;
    }

    public String getFromid() {
        return fromid;
    }

    public long getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return "ChatMsg{" +
                "username='" + username + '\'' +
                ", avatar='" + avatar + '\'' +
                ", id='" + id + '\'' +
                ", type='" + type + '\'' +
                ", content='" + content + '\'' +
                ", cid=" + cid +
                ", mine=" + mine +
                ", fromid='" + fromid + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}
