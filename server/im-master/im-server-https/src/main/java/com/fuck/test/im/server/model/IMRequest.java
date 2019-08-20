package com.fuck.test.im.server.model;

/**
 * DESCRIPTION:
 *
 * @author zouyan
 * @create 2018-01-03 下午4:52
 * created by fuck~
 **/
public class IMRequest {

    //直播间房间号
    private String roomId;
    //用户id
    private String userId;
    //用户名
    private String userName;
    //发送的消息内容
    private String message;
    //行为类型（如：登录、发送消息）
    private Integer type;

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "IMRequest{" +
                "roomId='" + roomId + '\'' +
                ", userId='" + userId + '\'' +
                ", userName='" + userName + '\'' +
                ", message='" + message + '\'' +
                ", type=" + type +
                '}';
    }
}
