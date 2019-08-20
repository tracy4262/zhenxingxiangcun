package com.ovit.nswy.member.util;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;

import java.security.Key;
import java.util.List;
import java.util.Map;

/**
 * json转字符串
 *
 * @author haoWen
 * @create 2018-01-19 13:42
 **/

public class JsonToStringUtils {
    /**
     * json转换为string
     * @param map
     * @param key
     * @return
     */
    public static String initJson(Map<String,Object> map,String key){
        String valueOfMap = MapUtils.getString(map,key);
        List<String> list = JSON.parseArray(valueOfMap,String.class);
        String values = StringUtils.join(list.toArray(),",");
        return values;
    }

    public  static Map<String,Object> convertByKeyArray(Map<String,Object> map,List<String> keyList){
        keyList.stream().forEach(key ->{
            String valueOfMap = MapUtils.getString(map,key);
            if (StringUtils.isEmpty(valueOfMap)) {
                map.put(key,"");
            } else {
                List<String> valueList = JSON.parseArray(valueOfMap,String.class);
                map.put(key,StringUtils.join(valueList.toArray(),","));
            }


        });
        return map;
    }
    public static String initJson2(Map<String,Object> map,String key){
        String valueOfMap = MapUtils.getString(map,key);
        List<String> list = JSON.parseArray(valueOfMap,String.class);
        String values = StringUtils.join(list.toArray(),"/");
        return values;
    }


    /**
     * 对象转JSON
     * @param obj
     * @return json字符串
     */
    public static String Obj2JsonString(Object obj){
        String json = null;
        ObjectMapper mapper = new ObjectMapper();
        try {
            json = mapper.writeValueAsString(obj);
        }catch (Exception e){
            e.printStackTrace();
        }
        return json;
    }
}
