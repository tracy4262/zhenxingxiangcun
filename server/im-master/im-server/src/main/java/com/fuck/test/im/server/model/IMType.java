package com.fuck.test.im.server.model;

/**
 * DESCRIPTION:
 *
 * @author zouyan
 * @create 2018-01-04 上午9:31
 * created by fuck~
 **/
public enum IMType {

    ON_LINE(1,"上线"),
    OFF_LINE(2, "下线"),
    SEND_MSG(3, "发送消息"),
    RECV_MSG(4, "接收消息");

    public Integer code;

    public String msg;

    private IMType(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

}
