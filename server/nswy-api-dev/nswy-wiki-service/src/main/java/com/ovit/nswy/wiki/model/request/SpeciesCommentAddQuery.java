package com.ovit.nswy.wiki.model.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by ${huipei.x} on 2017-10-30.
 */
@Data
@ApiModel(description="评论添加类")
public class SpeciesCommentAddQuery {
    @ApiModelProperty(value = "物种评论Id")
    private String comment_id;
    @ApiModelProperty(value = "物种Id")
    private String species_id;
    @ApiModelProperty(value = "用户Id")
    private String user_id;
    @ApiModelProperty(value = "评论时间")
    private long comment_time;
    @ApiModelProperty(value = "修改时间")
    private long update_time;
    @ApiModelProperty(value = "评论内容")
    private String content;
    @ApiModelProperty(value = "物种名称")
    private String species_name;
    @ApiModelProperty(value = "类型")
    private String type;
}
