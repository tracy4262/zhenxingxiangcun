package com.ovit.nswy.member.common.enums;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by ${huipei.x} on 2017-11-16.
 */
public enum MediaTypeEnum {

    MEDIA_TYPE_ONE(1,"相册"),
    MEDIA_TYPE_TWO(2,"视频"),
    MEDIA_TYPE_THREE(3,"文档");

    @Getter
    @Setter
    private int code;
    @Getter
    @Setter
    private String name;

    private MediaTypeEnum(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public static String mediaTypeEscape(int s){
        String info="";
        switch (s){
            case 1:info=MediaTypeEnum.MEDIA_TYPE_ONE.getName();break;
            case 2:info=MediaTypeEnum.MEDIA_TYPE_TWO.getName();break;
            case 3:info=MediaTypeEnum.MEDIA_TYPE_THREE.getName();break;
        }
        return info;
    }
}
