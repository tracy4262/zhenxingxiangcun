package com.ovit.nswy.im.model;

/**
* DESCRIPTION:
* 在线状态
* @author zouyan
* @create 2018/1/24-下午3:59
* created by fuck~
**/
public enum StatusType {

    ONLINE("online","在线"),
    HIDE("hide", "隐身"),
    OFFLINE("offline", "离线");

    public String type;

    public String des;

    private StatusType(String type, String des) {
        this.type = type;
        this.des = des;
    }
}
