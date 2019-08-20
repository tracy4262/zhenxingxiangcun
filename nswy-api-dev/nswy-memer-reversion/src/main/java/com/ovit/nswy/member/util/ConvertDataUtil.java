package com.ovit.nswy.member.util;

import com.alibaba.fastjson.JSONArray;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;


public class ConvertDataUtil {

    /**
     * json转为String数组
     *
     * @param str
     * @return
     */
    public static String[] getJsonToStringArray(String str) {
        JSONArray jsonArray = JSONArray.parseArray(str);
        String[] arr = new String[jsonArray.size()];
        for (int i = 0; i < jsonArray.size(); i++) {
            arr[i] = jsonArray.getString(i);
            System.out.println(arr[i]);
        }
        return arr;
    }

    /**
     * 将String数组用分号分隔
     *
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

    /**
     * 拆分数组
     * @param params
     * @return
     */
    public static Map<String,Object> split(Map<String, Object> params, String key) {

        String data = MapUtils.getString(params, key);
        if (StringUtils.isNotBlank(data)) {
            JSONArray dataArray = JSONArray.parseArray(data);
            if (dataArray.size() == 0) {
                params.put(key, "");
            } else {
                String[] datas = ConvertDataUtil.getJsonToStringArray(data);
                String dataMerge = ConvertDataUtil.stringArrayMerge(datas);
                params.put(key, dataMerge);
            }
        } else {
            params.put(key, "");
        }

        return params;
    }

    /**
     * 合并数组
     * @param params
     * @return
     */
    public static Map<String,Object> merge(Map<String, Object> params, String key) {

        String data = MapUtils.getString(params, key);
        if (StringUtils.isBlank(data)) {
            if (MapUtils.isEmpty(params)) {
                params = new HashMap<>();
            }
            params.put(key, new String[0]);
        } else {
            String[] datas = data.split(";");
            params.put(key, datas);
        }
        return params;
    }

}

