package com.ovit.nswy.wiki.model.request;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel(description="自定义目录参数类")
public class SpeciesPropertyQuery {

    //索引id
    private String indexid;

    //关联的物种ID
    private String speciesid;

    //自定义目录的id
    private String propertyid;

    //自定义目录标题
    private String propertytitle;

    //自定义目录正文内容
    private String propertycontent;

    //自定义目录图册
    private String propertyimage;

    //更新时间
    private long updatetime;

    //更新时间
    private long createtime;

    //是否是添加目录操作
    //private String isaddcatalog;

    //是否逻辑删除，0：存在 1：逻辑删除
    private Integer fisdelete;

    //审核状态: 0 更新待审核,2 新增待审核,3 删除待审核,1 审核通过,4 未审核通过,5 审核完成
    private Integer auditstatus;

}

