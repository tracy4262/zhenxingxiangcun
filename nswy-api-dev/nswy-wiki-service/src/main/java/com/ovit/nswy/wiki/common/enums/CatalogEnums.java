package com.ovit.nswy.wiki.common.enums;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by ${huipei.x} on 2017-11-2.
 */

public enum CatalogEnums {

    CATALOG_ONE(1,"简介"),
    CATALOG_TWO(2,"常见病害"),
    CATALOG_THREE(3,"常见虫害"),
    CATALOG_FOUR(4,"主要品种");

    @Getter
    @Setter
    private int code;
    @Getter
    @Setter
    private String name;

    private CatalogEnums(int code, String name) {
        this.code = code;
        this.name = name;
    }


}
