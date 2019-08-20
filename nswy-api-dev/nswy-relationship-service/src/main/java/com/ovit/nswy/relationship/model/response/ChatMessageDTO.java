package com.ovit.nswy.relationship.model.response;

import lombok.Data;

import java.util.Date;

/**
 * Created by ${huipei.x} on 2018-1-4.
 */
@Data
public class ChatMessageDTO {

    /** 用户ID */
    private int id;

    /** 用户名 */
    private String username;

    /** 头像 */
    private String avatar;

    /** 聊天内容 */
    private String content;

    /** 字符串类型的时间 */
    private Date time;

    /** 时间 */
    private long timestamp;

    public ChatMessageDTO() {
        super();
    }

    public ChatMessageDTO(int id, String username, String avatar, String content, Date time, long timestamp) {
        super();
        this.id = id;
        this.username = username;
        this.avatar = avatar;
        this.content = content;
        this.time = time;
        this.timestamp = timestamp;
    }

}
