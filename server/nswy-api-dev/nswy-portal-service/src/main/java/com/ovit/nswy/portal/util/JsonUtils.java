package com.ovit.nswy.portal.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.ovit.nswy.portal.model.Sort;
import com.ovit.nswy.portal.model.children;
import org.apache.commons.collections.CollectionUtils;

import java.util.List;

public class JsonUtils {

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
}
