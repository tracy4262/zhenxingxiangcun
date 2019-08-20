package com.fuck.test.im.server.model;

import java.util.HashMap;
import java.util.Map;

/**
 * DESCRIPTION:
 * 直播间聊天室内response
 * @author zouyan
 * @create 2018-01-03 下午4:52
 * created by fuck~
 **/
public class IMResponse {

    //用户名
    private final String userName;
    //是否成功
    private final boolean isSucc;
    //行为类型（如：接收消息、上线、下线）
    private final Integer type;
    //消息内容
    private final String message;
    //聊天室人员在线人员集合 key:userId value:userName
    private final Map<String, String> onlines;

    private IMResponse(Builder builder) {
        userName = builder.userName;
        isSucc = builder.isSucc;
        type = builder.type;
        message = builder.message;
        onlines = builder.onlines;
    }

    public static class Builder {
        private String userName;
        private boolean isSucc;
        private Integer type;
        private String message;
        private Map<String, String> onlines = new HashMap<String, String>();

        public Builder() {

        }

        public IMResponse build() {
            return new IMResponse(this);
        }

        public IMResponse.Builder userName(String userName) {
            this.userName = userName;
            return this;
        }

        public IMResponse.Builder isSucc(boolean isSucc) {
            this.isSucc = isSucc;
            return this;
        }

        public IMResponse.Builder type(Integer type) {
            this.type = type;
            return this;
        }

        public IMResponse.Builder message(String message) {
            this.message = message;
            return this;
        }

        public IMResponse.Builder onlines(Map<String, String> onlines) {
            this.onlines = onlines;
            return this;
        }
    }

    public String getUserName() {
        return userName;
    }

    public boolean isSucc() {
        return isSucc;
    }

    public Integer getType() {
        return type;
    }

    public String getMessage() {
        return message;
    }

    public Map<String, String> getOnlines() {
        return onlines;
    }

    @Override
    public String toString() {
        return "IMResponse{" +
                "userName='" + userName + '\'' +
                ", isSucc=" + isSucc +
                ", type='" + type + '\'' +
                ", message='" + message + '\'' +
                ", onlines=" + onlines +
                '}';
    }
}
