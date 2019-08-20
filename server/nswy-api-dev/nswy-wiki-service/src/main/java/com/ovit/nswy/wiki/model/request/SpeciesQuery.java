package com.ovit.nswy.wiki.model.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * Created by ${huipei.x} on 2017-10-20.
 */

@ApiModel(description="物种查询参数类")
@Data
public class SpeciesQuery extends BaseQuery {
    @ApiModelProperty("物种分类id")
    private String  fclassifiedid;
    @ApiModelProperty("物种id")
    private String speciesid;
    @ApiModelProperty("物种拼音")
    private String fpinyin;
    @ApiModelProperty("物种名称")
    private String  fname;
    @ApiModelProperty("[1:最近更新,2：最近历史]")
    private  Integer latelyType;//[1:最近更新,2：最近历史]
    @ApiModelProperty("用户id")
    private String userId;
    private List<Object> speciesIds;
    @ApiModelProperty("关键字")
    private String keywords;
    @ApiModelProperty("排序{1：正序，2：倒序}")
    private Integer sortType;//{1：正序，2：倒序}
    @ApiModelProperty("{1：编辑，2：关注}")
    private Integer seeType;//{1：编辑，2：关注}
    @ApiModelProperty("产业分类")
    private String findustriaclassifiedid;
    @ApiModelProperty(value = "审核状态{0：未审核，1:审核通过, 2:未审核通过}")
    private Integer auditStatus;
    /**
     *
     */
    private String fcode;
    @ApiModelProperty("是否查相关词条")
    private String isLemma;

    @ApiModelProperty(value = "索引Id")
    private String indexid;

    @ApiModelProperty(value = "创建者")
    private String fcreatorid;
}
