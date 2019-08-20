package com.ovit.nswy.member.util;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.collections.MapUtils;

import java.util.HashMap;
import java.util.Map;

public class BaiDuUtil {

    /**
     * 谷歌坐标转百度坐标
     * @param coordinate
     * @return
     */
    public static Map<String,Object> googleConvertBaidu(Map<String,Object> coordinate) {
        //东经 lng
        String lng = MapUtils.getString(coordinate, "lng");
        //北纬 lat
        String lat = MapUtils.getString(coordinate, "lat");

        String url = "http://api.map.baidu.com/ag/coord/convert?from=0&to=4&x=" + lng + "&y=" + lat;
        JSONObject result = JSONObject.parseObject(OkHttpUtil.getInstance().excuteHttp(url, "", HttpMethod.GET));

        String convertLng = new String(Base64.decodeBase64(result.get("x").toString().getBytes()));
        String convertLat = new String(Base64.decodeBase64(result.get("y").toString().getBytes()));

        Map<String,Object> resultMap = new HashMap<>();
        resultMap.put("convertLng", convertLng);
        resultMap.put("convertLat", convertLat);

        return resultMap;
    }

}
