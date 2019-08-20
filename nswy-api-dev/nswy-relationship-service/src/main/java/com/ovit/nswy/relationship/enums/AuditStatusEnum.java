package com.ovit.nswy.relationship.enums;

/**
 * Created by ${huipei.x} on 2017-11-4.
 */

import lombok.Getter;
import lombok.Setter;

/**
 * Created by ${huipei.x} on 2017-11-2.
 */
public enum  AuditStatusEnum {

    AUDIT_STATUS_ZERO(0,"未审核"),
    AUDIT_STATUS_ONE(1,"已审核"),
    AUDIT_STATUS_TWO(2,"审核拒绝");


    @Getter
    @Setter
    private int code;
    @Getter
    @Setter
    private String name;

    private AuditStatusEnum(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public static String  auditStatusEscape(Integer s){
        String info="";
        switch (s){
            case 0:info=AuditStatusEnum.AUDIT_STATUS_ZERO.getName();break;
            case 1:info=AuditStatusEnum.AUDIT_STATUS_ONE.getName();break;
            case 2:info=AuditStatusEnum.AUDIT_STATUS_TWO.getName();break;
        }
        return info;
    }
}
