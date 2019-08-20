package com.ovit.nswy.member.common.enums;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by ${huipei.x} on 2017-12-11.
 */
public enum AppStartFlagEnum {

    APP_START_FLAG_ZERO(0,"未开始"),
    APP_START_FLAG_ONE(1,"已开始");


    @Getter
    @Setter
    private int code;
    @Getter
    @Setter
    private String name;

    private AppStartFlagEnum(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public static String  appStartFlagEscape(int s){
        String info="";
        switch (s){
            case 0:info=AppStartFlagEnum.APP_START_FLAG_ZERO.getName();break;
            case 1:info=AppStartFlagEnum.APP_START_FLAG_ONE.getName();break;
        }
        return info;
    }
}
