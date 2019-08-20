package com.ovit.nswy.frame.common.util;

import com.github.pagehelper.PageInfo;

/**
 * Created by ${huipei.x} on 2017-11-16.
 */
public  class PageInfoHelper {

    public static  PageInfo getPageInfo(PageInfo orig,PageInfo target){
        target.setTotal(orig.getTotal());
        target.setPages(orig.getPages());
        target.setPageNum(orig.getPageNum());
        target.setPageSize(orig.getPageSize());
        return  target;
    }
}
