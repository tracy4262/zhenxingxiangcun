package com.ovit.nswy.member.model.request;

import lombok.Data;

/**
 * Created by ${huipei.x} on 2018-1-3.
 */
@Data
public class GetUserQuery {

    private int queryType;
    private int Id;
    private String account;
}
