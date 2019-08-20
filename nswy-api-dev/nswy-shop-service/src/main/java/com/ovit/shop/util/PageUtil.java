package com.ovit.shop.util;

import com.github.pagehelper.PageHelper;
import org.apache.commons.collections.MapUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PageUtil {

    /**
     *
     * @param pageNo 当前页码
     * @param pageSize 页数
     * @param list  所有集合
     * @return
     * @throws Exception
     */
    public static List<Map<String, Object>> pageForList(int pageNo, int pageSize, List<Map<String, Object>> list) throws Exception{
        List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
        if(list != null && list.size() > 0){
            int allCount = list.size();
            int pageCount = (allCount + pageSize-1) / pageSize;
            if(pageNo >= pageCount){
                pageNo = pageCount;
            }
            int start = (pageNo-1) * pageSize;
            int end = pageNo * pageSize;
            if(end >= allCount){
                end = allCount;
            }
            for(int i = start; i < end; i ++){
                result.add(list.get(i));
            }
        }
        return (result != null && result.size() > 0) ? result : null;
    }

    public static void initPage(Map<String, Object> params){

        Integer num = MapUtils.getInteger(params, "pageNum");
        int pageNum = (num == null) ? 1 : num;
        Integer size = MapUtils.getInteger(params, "pageSize");
        int pageSize = (size == null) ? 10 : size;
        PageHelper.startPage(pageNum, pageSize);

    }

}

