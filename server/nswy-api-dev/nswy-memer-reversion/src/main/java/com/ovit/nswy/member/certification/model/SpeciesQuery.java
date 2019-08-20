package com.ovit.nswy.member.certification.model;

import com.ovit.nswy.frame.common.request.BaseQuery;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 物种
 *
 * @author haoWen
 * @create 2018-01-22 17:32
 **/

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

    private String fcode;

}
