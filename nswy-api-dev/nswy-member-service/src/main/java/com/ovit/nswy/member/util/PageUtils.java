package com.ovit.nswy.member.util;

import com.github.pagehelper.PageHelper;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

/**
 * 分页设置
 *
 * @author haoWen
 * @create 2018-01-19 14:29
 **/

public class PageUtils {

    public static void initPage(Map<String, Object> params){
        String pageNum = StringUtils.defaultString(MapUtils.getString(params,"pageNum"),"1");
        String pageSize = StringUtils.defaultString(MapUtils.getString(params,"pageSize"),"10");
        PageHelper.startPage(Integer.parseInt(pageNum),Integer.parseInt(pageSize));
    }
}
