package com.ovit.nswy.wiki.model.response;


/**
 *  Created by ${huipei.x} on 2017-10-16.
 */

public class BaseDTO {
    private Integer pageNum=0;
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
