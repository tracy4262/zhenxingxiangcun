package com.ovit.nswy.portal.core.enums;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by ${huipei.x} on 2018-1-23.
 */
public enum DocTypeEnum {

    DOC_TYPE_ONE("030201","政策法规"),
    DOC_TYPE_TWO("030202","政策解读"),
    DOC_TYPE_THREE("030203","通知公告"),
    DOC_TYPE_FORE("030204","村务公开"),
    DOC_TYPE_FIVE("030205","党务公开"),
    DOC_TYPE_SEX("030206","公告公示"),
    DOC_TYPE_SEVEN("030207","政务法规"),
    DOC_TYPE_EIGHT("030208","政务公开"),
    DOC_TYPE_NINE("030209","党务动态"),
    DOC_TYPE_TEN("030210","政务动态");


    @Getter
    @Setter
    private String code;
    @Getter
    @Setter
    private String name;

    DocTypeEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public static String  DocTypeEscape(String  s){
        String info="";
        switch (s){
            case "030201":info=DocTypeEnum.DOC_TYPE_ONE.getName();break;
            case "030202":info=DocTypeEnum.DOC_TYPE_TWO.getName();break;
            case "030203":info=DocTypeEnum.DOC_TYPE_THREE.getName();break;
            case "030204":info=DocTypeEnum.DOC_TYPE_FORE.getName();break;
            case "030205":info=DocTypeEnum.DOC_TYPE_FIVE.getName();break;
            case "030206":info=DocTypeEnum.DOC_TYPE_SEX.getName();break;
            case "030207":info=DocTypeEnum.DOC_TYPE_SEVEN.getName();break;
            case "030208":info=DocTypeEnum.DOC_TYPE_EIGHT.getName();break;
            case "030209":info=DocTypeEnum.DOC_TYPE_NINE.getName();break;
            case "030210":info=DocTypeEnum.DOC_TYPE_TEN.getName();break;
        }
        return info;
    }
}
