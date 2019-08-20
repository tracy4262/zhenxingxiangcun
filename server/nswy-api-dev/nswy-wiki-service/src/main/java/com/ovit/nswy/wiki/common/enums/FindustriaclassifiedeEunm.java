package com.ovit.nswy.wiki.common.enums;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by ${huipei.x} on 2017-12-7.
 */
public enum FindustriaclassifiedeEunm {

    FINDUSTRIACLASSIFIEDE_TYPE_ZERO("0","全部"),
    FINDUSTRIACLASSIFIEDE_TYPE_ONE("A01","农业"),
    FINDUSTRIACLASSIFIEDE_TYPE_TWO("A02","林业"),
    FINDUSTRIACLASSIFIEDE_TYPE_THREE("A03","畜牧业"),
    FINDUSTRIACLASSIFIEDE_TYPE_FOUR("A04","水产业");

    @Getter
    @Setter
    private String code;
    @Getter
    @Setter
    private String name;

    private FindustriaclassifiedeEunm(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public static String findustriaclassifiedeTypeEscape(String s){
        String info="";
        switch (s){
            case "0":info=FindustriaclassifiedeEunm.FINDUSTRIACLASSIFIEDE_TYPE_ZERO.getName();break;
            case "A01":info=FindustriaclassifiedeEunm.FINDUSTRIACLASSIFIEDE_TYPE_ONE.getName();break;
            case "A02":info=FindustriaclassifiedeEunm.FINDUSTRIACLASSIFIEDE_TYPE_TWO.getName();break;
            case "A03":info=FindustriaclassifiedeEunm.FINDUSTRIACLASSIFIEDE_TYPE_THREE.getName();break;
            case "A04":info=FindustriaclassifiedeEunm.FINDUSTRIACLASSIFIEDE_TYPE_FOUR.getName();break; default:

        }
        return info;
    }
}
