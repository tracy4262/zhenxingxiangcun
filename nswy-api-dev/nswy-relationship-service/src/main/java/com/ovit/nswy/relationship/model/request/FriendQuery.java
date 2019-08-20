package com.ovit.nswy.relationship.model.request;

import lombok.Data;

/**
 * Created by ${huipei.x} on 2017-11-3.
 */
@Data
public class FriendQuery {

    /** 我的id */
    private int userId;

    /** 好友的id */
    private int friendId;

    /** 好友所在分组的id */
    private int friendGroupId;
}
