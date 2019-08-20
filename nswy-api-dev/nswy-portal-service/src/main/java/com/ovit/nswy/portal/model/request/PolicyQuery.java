package com.ovit.nswy.portal.model.request;

import com.ovit.nswy.frame.common.request.BaseQuery;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by ${huipei.x} on 2018-1-12.
 */
@Data
public class PolicyQuery extends BaseQuery {

    /**
     * 用户类型 [0个人用户 1企业 2政府 3机关 4专家 5 乡村]
     */
    @ApiModelProperty(value = "用户类型 [0个人用户 1企业 2政府 3机关 4专家 5 乡村]")
    private Integer userType;
    /**
     * 0 视频 1 音频 2 图片 3.文本',
     */
    @ApiModelProperty(value = "政策法规 【0 视频 1 音频 2 图片 3 文本】")
    private Integer policyType;
    @ApiModelProperty(value = "用户账号")
    private String  account;
    /**
     * 查询类型 1 政策法规  2 政策解读   3 通知公告  4 村务公开 7 政务法规 8 政务公开
     */
    @ApiModelProperty(value = "查询类型 1 政策法规  2 政策解读   3 通知公告  4 村务公开 5 党务公开 6 公告公示 7 政务法规 8 政务公开")
    private Integer policyQueryType;
    /**
     * 关键字
     */
    @ApiModelProperty(value = "关键字")
    private String keywords;
    /**
     * 年
     */
    @ApiModelProperty(value = "年")
    private String   year;
    /**
     * 查询视频类型
     */
    @ApiModelProperty(value = "查询视频类型 0 全部 是 1 否  ")
    private Integer queryVideoType;

    private String label;

}
