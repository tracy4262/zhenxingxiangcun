package com.ovit.nswy.wiki.common.enums;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by ${huipei.x} on 2017-11-20.
 */
public enum SpeciesclassEnums {

    CLASS_ZERO(0,"动物"),
    CLASS_ONE(1,"植物");


    @Getter
    @Setter
    private int code;
    @Getter
    @Setter
    private String name;

    private SpeciesclassEnums(int code, String name) {
        this.code = code;
        this.name = name;
    }
    public static String SpeciesclassTypeEscape(int s){
        String info="";
        switch (s){
            case 0:info=SpeciesclassEnums.CLASS_ZERO.getName();break;
            case 1:info=SpeciesclassEnums.CLASS_ONE.getName();break;
            default:
        }
        return info;
    }
}
