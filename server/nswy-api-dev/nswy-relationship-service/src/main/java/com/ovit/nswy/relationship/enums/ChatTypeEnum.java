package com.ovit.nswy.relationship.enums;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by ${huipei.x} on 2018-1-4.
 */
public enum ChatTypeEnum {


    CHAT_TYPE_ONE(1,"friend"),
    CHAT_TYPE_TWO(2,"group");
    @Getter
    @Setter
    private int code;
    @Getter
    @Setter
    private String name;

    private ChatTypeEnum(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public static Integer  chatTypeEscape(String  name){
        Integer info=null;
        switch (name){
            case "friend":info=ChatTypeEnum.CHAT_TYPE_ONE.getCode();break;
            case "group":info=ChatTypeEnum.CHAT_TYPE_TWO.getCode();break ;default:
        }
        return info;
    }
}
