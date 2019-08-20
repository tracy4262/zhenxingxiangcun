package com.ovit.nswy.wiki.model.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * Created by ${huipei.x} on 2017-10-31.
 */
@Data
@ApiModel(description="病害参数类")
public class SpeciesDiseaseQuery {
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

    private String ffeature;

    private String fmainfeatures;

    private String fdiseaseregular;

    private String fprotectmethod;

    private String fcausediseasesubject;

    private String fcommonfeature;

    private String fpathologycheck;

    private String fdiagnose;

    private String fprevention;

    private long updatetime;

    private String indexid;
    /**
     * 病原学
     */
    private String etiology;
    /**
     * 流行病学特征
     */
    private String epidemiologicalfeatures;

    //审核状态: 0 更新待审核,2 新增待审核,3 删除待审核,1 审核通过,4 未审核通过,5 审核完成
    private Integer auditstatus;

    private String parentid;
}

