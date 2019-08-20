package com.ovit.nswy.relationship.model.request;

import lombok.Data;

/**
 * Created by ${huipei.x} on 2018-1-10.
 */
@Data
public class ListLiveQuery extends BaseQuery{

    /**
     * 省
     */
    private String provinceId;


    /**
     * 相关物种
     */
    private String speciesId;


    /**
     * 相关行业
     */
    private String industryId;

    /**
     * 用户账号
     */
    private String account;

    /**
     * 市
     */
    private String cityId;

    /**
     * 区，镇
     */
    private String townId;

    /**
     * 村
     */
    private String villageId;

    /**
     * 街道
     */
    private String streetId;

    /**
     * 排序【1 正序 2 倒序】
     */
    private Integer sortType;
}
