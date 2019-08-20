package com.ovit.nswy.relationship.model.response;

import lombok.Data;

import java.util.Date;

/**
 * Created by ${huipei.x} on 2018-1-2.
 */
@Data
public class ImUserDTO {

    private int id;

    /** 我的真实姓名，即ucName */
    private String username;
    private String account;
    /** 在线状态 -- 在线：online;隐身：hide; 默认值为"online" */
    private String status = "online";

    /** 我的签名 */
    private String sign;

    /** 我的头像 */
    private String avatar;
    private Date time;
}
