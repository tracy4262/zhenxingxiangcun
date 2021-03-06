package com.ovit.nswy.wiki.model.request;


import io.swagger.annotations.ApiModelProperty;

/**
 *  Created by ${huipei.x} on 2017-10-16.
 */

public class BaseQuery {
    @ApiModelProperty("当前页")
    private Integer pageNum=1;
    @ApiModelProperty("当前页显示数")
    private Integer pageSize=10;

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
