package com.ovit.nswy.relationship.model.response;

import lombok.Data;

/**
 * Created by ${huipei.x} on 2017-11-2.
 */
@Data
public class FriendGroupDTO {
    private Integer id;
    /**
     * 登录名
     */

    private String loginAccount;

    /**
     * 好友分组名称
     */
    private String groupName;
}
