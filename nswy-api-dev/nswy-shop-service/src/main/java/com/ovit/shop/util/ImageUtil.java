package com.ovit.shop.util;

import com.alibaba.fastjson.JSONArray;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

public class ImageUtil {

    /**
     * json转为String数组
     * @param str
     * @return
     */
    public static String[] getJsonToStringArray(String str) {
        JSONArray jsonArray = JSONArray.parseArray(str);
        String[] arr=new String[jsonArray.size()];
        for(int i=0;i<jsonArray.size();i++){
            arr[i]=jsonArray.getString(i);
            System.out.println(arr[i]);
        }
        return arr;
    }

    /**
     * 将String数组用分号分隔
     * @param strArray
     * @return
     */
    public static String stringArrayMerge(String[] strArray) {

        StringBuffer sb = new StringBuffer();
        for (String str : strArray) {
            sb.append(str).append(";");
        }
        String sbString = sb.toString();
        String result = sbString.substring(0, sbString.length() - 1);

        return result;
    }

    public static Map<String,Object> publicMap(String model, String name, String status){
        Map<String,Object> maps = new HashMap<>();
        maps.put("model", StringUtils.isBlank(model) ? "" : model);
        maps.put("name", StringUtils.isBlank(name) ? "" : name);
        maps.put("status", "1".equals(status) ? true : false);
        return maps;

    }
}
