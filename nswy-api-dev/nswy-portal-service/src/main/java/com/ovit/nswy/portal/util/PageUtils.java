package com.ovit.nswy.portal.util;

import com.github.pagehelper.PageHelper;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

/**
 * 数组实现分页
 *
 * @author haoWen
 * @create 2018-01-12 13:32
 **/
public class PageUtils {

    public static Map<String,Object> getPageInfo(String[] list,String num,String size){
        int pageNum = StringUtils.isEmpty(num) ? 0 : Integer.parseInt(num);
        int pageSize = StringUtils.isEmpty(size) ? 10 : Integer.parseInt(size);
        Map<String,Object> mapAll = new HashMap<>();
        List<Map<String,Object>> listMap = new ArrayList<>();
        int currIdx = (pageNum > 1 ? (pageNum -1) * pageSize : 0);
        for (int i = 0; i < pageSize && i < list.length - currIdx; i++) {
            Map<String,Object> map = new HashMap<>();
            map.put("key",list[currIdx + i]);
            listMap.add(map);
        }
        mapAll.put("totalCount",list.length);
        mapAll.put("pageNum",pageNum);
        mapAll.put("pageSize",pageSize);
        mapAll.put("list",listMap);
        return mapAll;
    }

    public static void initPage(Map<String, Object> params){
        String pageNum = MapUtils.getString(params,"pageNum");
        String pageSize = MapUtils.getString(params,"pageSize");
        int number = StringUtils.isEmpty(pageNum) ? 0 : Integer.parseInt(pageNum);
        int size = StringUtils.isEmpty(pageSize) ? 10 : Integer.parseInt(pageSize);
        PageHelper.startPage(number,size);
    }

    /**
     *
     * @param pageNo 当前页码
     * @param pageSize 页数
     * @param list  所有集合
     * @return
     * @throws Exception
     */
    public static List<Map<String, Object>> pageForList(int pageNo,int pageSize,List<Map<String, Object>> list) throws Exception{
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
}
