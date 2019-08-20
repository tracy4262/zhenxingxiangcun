package com.ovit.nswy.member.model.request;

import lombok.Data;

/**
 * Created by ${huipei.x} on 2017-12-28.
 */
@Data
public class ListFollowQuery {

    private Integer  followType;
    private String   account;
    private String   fromAccount;
}
