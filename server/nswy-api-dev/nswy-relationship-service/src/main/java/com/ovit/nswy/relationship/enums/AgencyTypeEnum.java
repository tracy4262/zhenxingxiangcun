package com.ovit.nswy.relationship.enums;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by ${huipei.x} on 2017-11-2.
 */
public enum AgencyTypeEnum {

    AGENCY_TYPE_ONE(1,"企业"),
    AGENCY_TYPE_TWO(2,"机关服务大厅"),
    AGENCY_TYPE_THREE(3,"乡村服务站");

    @Getter
    @Setter
    private int code;
    @Getter
    @Setter
    private String name;

    private AgencyTypeEnum(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public static String agencyTypeEscape(int s){
        String info="";
        switch (s){
            case 1:info=AgencyTypeEnum.AGENCY_TYPE_ONE.getName();break;
            case 2:info=AgencyTypeEnum.AGENCY_TYPE_TWO.getName();break;
            case 3:info=AgencyTypeEnum.AGENCY_TYPE_THREE.getName();break;
        }
        return info;
    }
}
