package com.ovit.nswy.wiki.model.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * Created by ${huipei.x} on 2017-10-31.
 */
@Data
@ApiModel(description="虫害添加参数类")
public class SpeciesPestQuery {
    private String fid;

    private String fname;

    private String speciesid;

    private String fimagesrc;

    private String fpinyin;

    private String fenglishname;

    private String fdamagecropsid;

    private String fmedicamentid;

    private Byte fispublic;

    private String fcreatorid;

    private Date fcreatetime;

    private String fupdatorid;

    private Date fupdatetime;

    //是否逻辑删除，0：存在 1：逻辑删除
    private Integer fisdelete;

    private String fdeleteid;

    private Date fdeletetime;

    private Integer fstate;

    private String fremarks;

    private String fhabit;

    private String fmainfeatures;

    private String fpetsregular;

    private String fprotectmethod;

    private long updatetime;

    private String indexid;

    //审核状态: 0 更新待审核,2 新增待审核,3 删除待审核,1 审核通过,4 未审核通过,5 审核完成
    private Integer auditstatus;

    private String parentid;
}
