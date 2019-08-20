package com.ovit.nswy.wiki.common.enums;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by ${huipei.x} on 2017-12-7.
 */
public enum IsprotectionEnum {

    ISPROTECTION_TYPE_ZERO(0,"否"),
    ISPROTECTION_TYPE_ONE(1,"一级保护"),
    ISPROTECTION_TYPE_TWO(2,"二级保护"),
    ISPROTECTION_TYPE_THREE(3,"地方重点保护");


    @Getter
    @Setter
    private int code;
    @Getter
    @Setter
    private String name;

    private IsprotectionEnum(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public static String IsprotectionEscape(int s){
        String info="";
        switch (s){
            case 0:info=IsprotectionEnum.ISPROTECTION_TYPE_ZERO.getName();break;
            case 1:info=IsprotectionEnum.ISPROTECTION_TYPE_ONE.getName();break;
            case 2:info=IsprotectionEnum.ISPROTECTION_TYPE_TWO.getName();break;
            case 3:info=IsprotectionEnum.ISPROTECTION_TYPE_THREE.getName();break;default:

        }
        return info;
    }
}
