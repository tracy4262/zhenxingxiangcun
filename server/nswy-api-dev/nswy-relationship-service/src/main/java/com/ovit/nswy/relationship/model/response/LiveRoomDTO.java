package com.ovit.nswy.relationship.model.response;

import com.google.common.collect.Maps;
import lombok.Data;

import java.util.Date;
import java.util.Map;

/**
 * Created by ${huipei.x} on 2018-1-10.
 */
@Data
public class LiveRoomDTO {

    private Integer liveId;

    /**
     * 房间标题
     */
    private String liveName;

    /**
     * 房间描述
     */
    private String liveDescribe;

    /**
     * 房间封面
     */
    private String liveImage;

    /**
     * 创建时间
     */

    private Date createTime;


    /**
     * 用户账号
     */
    private String account;

    /**
     * 用户名称
     */
    private String userName;
    /**
     * 直播状态转义
     */
    private Map liveStatusInfo= Maps.newHashMap();

}
