package com.ovit.nswy.im.model;

/**
* DESCRIPTION:
* 消息类型
* @author zouyan
* @create 2018/1/24-上午11:30
* created by fuck~
**/
public enum EmitType {

    ON_LINE("online","上线"),
    ON_LINE_RES("onlineRes","上线响应"),
    OFF_LINE("offline", "下线"),
    FRIEND_MSG("friendMsg", "朋友消息"),
    GROUP_MSG("groupMsg", "群消息"),
    STATUS_CHANGE("statusChange", "隐身在线状态切换");

    public String type;

    public String msg;

    private EmitType(String type, String msg) {
        this.type = type;
        this.msg = msg;
    }

}
