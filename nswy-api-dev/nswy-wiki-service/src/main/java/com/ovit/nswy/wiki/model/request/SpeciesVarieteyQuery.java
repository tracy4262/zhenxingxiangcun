package com.ovit.nswy.wiki.model.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * Created by ${huipei.x} on 2017-10-31.
 */
@Data
@ApiModel(description="物种品种添加参数类")
public class SpeciesVarieteyQuery {
    private String fid;

    private String speciesid;

    private String fname;

    private String fpinyin;

    private String ficon;

    private String fvarietyapprcertificationicon;

    private String fvarietyowner;

    private String fvarietyapprnum;

    private String fvarietykind;

    private String fbreedingunit;

    private String fcreatorid;

    private Date fcreatetime;

    private String fupdatorid;

    private Date fupdatetime;

    //是否逻辑删除，0：存在 1：逻辑删除
    private Integer fisdelete;

    private String fdeleteid;

    private Date fdeletetime;

    private Integer fstate;

    private Byte fisattention;

    private Integer fisprotection;

    private Byte fistransgene;

    private Date fapplydate;

    private Date fapplyannouncedate;

    private Date fauthdate;

    private Date fauthannouncedate;

    private String fvarietyapprdate;

    private String fapplynumber;

    private String fapplyannouncenumber;

    private String fauthnumber;
    private String fauthannouncenumber;
    private String fvarietyapprunit;

    private String fareateststate;

    private String fvarietyorigin;

    private String fsuiteplatearea;

    private String fvarietyrecommand;

    private String fremarks;

    private String fgrowpeople;

    private String ffeature;

    private String foutput;

    private String fgrowteachology;

    private String fmarketsituation;

    private String image;

    private long updatetime;
    private String indexid;

    //审核状态: 0 更新待审核,2 新增待审核,3 删除待审核,1 审核通过,4 未审核通过,5 审核完成
    private Integer auditstatus;

    private String parentid;

}
