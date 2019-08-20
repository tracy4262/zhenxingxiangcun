package com.ovit.nswy.wiki.model.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * Created by ${huipei.x} on 2017-10-26.
 */
@Data
@ApiModel(description="物种添加或修改参数类")
public class SpeciesAddQuery {
    private String fid;
    private String fcode;
    @ApiModelProperty(value = "物种分类Id")
    private String fclassifiedid;
    @ApiModelProperty(value = "产业分类")
    private String findustriaclassifiedid;
    @ApiModelProperty(value = "图片")
    private String ficon;
    @ApiModelProperty(value = "图片地址")
    private String fimage;
    @ApiModelProperty(value = "物种名称")
    private String fname;
    @ApiModelProperty(value = "物种全拼")
    private String fpinyin;
    @ApiModelProperty(value = "创建者")
    private String fcreatorid;
    @ApiModelProperty(value = "创建时间")
    private Date fcreatetime;
    private Integer fstate;
    @ApiModelProperty(value = "其他物种分类")
    private String fouthclassifiedid;
    @ApiModelProperty(value = "俗名名称ID")
    private String ftrivialid;
    @ApiModelProperty(value = "常见害虫")
    private String fpestsid;
    @ApiModelProperty(value = "常见病害")
    private String fdiseaseid;
    private String finitialproductid;
     @ApiModelProperty(value = "形状特征")
    private String fshapefeatureid;
    @ApiModelProperty(value = "备注")
    private String fremarks;
    @ApiModelProperty(value = "是否删除")
    private Integer fisdelete;
    @ApiModelProperty(value = "修改者")
    private String fupdatorid;
    @ApiModelProperty(value = "删除者")
    private String fdeleteid;
    private  Integer fisattention;
    @ApiModelProperty(value = "是否保护")
    private String fisprotection;
    @ApiModelProperty(value = "修改时间")
    private long updatetime;
    @ApiModelProperty(value = "点赞数")
    private Integer likedcount;
    @ApiModelProperty(value = "物种Id")
    private String speciesid;
    @ApiModelProperty(value = "索引Id [修改必传]")
    private String indexid;
    @ApiModelProperty(value = "审核状态【0 更新待审核,2 新增待审核,3 删除待审核,1 审核通过,4 未审核通过,5 审核完成】")
    private Integer auditstatus;
    /**
     * 描述
     */
    private String  describe;
    /**
     *描述
     */
    private String image;

    @ApiModelProperty(value = "物种俗称")
    private String speciesVulgo;
    @ApiModelProperty(value = "其他分类")
    private String otherClassify;
    @ApiModelProperty(value = "主要产品")
    private String majorProduct;
    @ApiModelProperty(value = "物种图册")
    private String speciesAtlas;


    @Override
    public String toString() {
        return "SpeciesAddQuery{" +
                "fid='" + fid + '\'' +
                ", fcode='" + fcode + '\'' +
                ", fclassifiedid='" + fclassifiedid + '\'' +
                ", findustriaclassifiedid='" + findustriaclassifiedid + '\'' +
                ", ficon='" + ficon + '\'' +
                ", fimage='" + fimage + '\'' +
                ", fname='" + fname + '\'' +
                ", fpinyin='" + fpinyin + '\'' +
                ", fcreatorid='" + fcreatorid + '\'' +
                ", fcreatetime=" + fcreatetime +
                ", fstate=" + fstate +
                ", fouthclassifiedid='" + fouthclassifiedid + '\'' +
                ", ftrivialid='" + ftrivialid + '\'' +
                ", fpestsid='" + fpestsid + '\'' +
                ", fdiseaseid='" + fdiseaseid + '\'' +
                ", finitialproductid='" + finitialproductid + '\'' +
                ", fshapefeatureid='" + fshapefeatureid + '\'' +
                ", fremarks='" + fremarks + '\'' +
                ", fisdelete=" + fisdelete +
                ", fupdatorid='" + fupdatorid + '\'' +
                ", fdeleteid='" + fdeleteid + '\'' +
                ", fisattention=" + fisattention +
                ", fisprotection='" + fisprotection + '\'' +
                ", updatetime=" + updatetime +
                ", likedcount=" + likedcount +
                ", speciesid='" + speciesid + '\'' +
                ", indexid='" + indexid + '\'' +
                ", auditstatus=" + auditstatus +
                ", describe='" + describe + '\'' +
                ", image='" + image + '\'' +
                '}';
    }
}
