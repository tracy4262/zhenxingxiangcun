package com.ovit.nswy.wiki.model.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * Created by ${huipei.x} on 2017-11-2.
 */
@Data
@ApiModel(description="类目参数类")
public class SpeciesWikiQuery {
    @ApiModelProperty(value = "用户Id")
    private String user_id;
    @ApiModelProperty(value = "物种Id")
    private String  species_id;//物种Id
    @ApiModelProperty(value = "物种类型")
    private Integer species_type;
    @ApiModelProperty(value = "扩展类型")
    private String extend_type;//
    @ApiModelProperty(value = "类目类型")
    private Integer   catalog_type;
    @ApiModelProperty(value = "类目名称")
    private String   catalog_name;
    @ApiModelProperty(value = "版本")
    private Integer version;
    @ApiModelProperty(value = "类目内容")
    private String  catalog_content;
    private Date create_time;
    private long   update_time;

    private String indexid;

    @ApiModelProperty(value = "自定义目录id")
    private String propertyid;

    //是否逻辑删除，0：存在 1：逻辑删除
    @ApiModelProperty(value = "是否逻辑删除")
    private Integer fisdelete;

    //审核状态: 0 更新待审核,2 新增待审核,3 删除待审核,1 审核通过,4 未审核通过,5 审核完成
    @ApiModelProperty(value = "审核状态")
    private Integer auditstatus;
}
