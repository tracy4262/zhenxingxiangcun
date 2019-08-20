package com.ovit.manager.modules.nswy.service;

import com.alibaba.fastjson.JSONObject;
import com.github.xphsc.http.HttpUtil;
import com.ovit.manager.common.config.Global;
import com.ovit.manager.common.persistence.Page;
import com.ovit.manager.modules.nswy.dao.ExpertApplyDao;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class PestQueryService {

    @Autowired
    private ExpertApplyDao ExpertApplyDao;

    public List<Map<String,Object>> findPestQueryList(Map<String, Object> params) {
        String url = Global.getConfig("api.url")+"/wiki/api/wiki/getPestList";

        List<Map<String,Object>> list = new ArrayList<>();
        StringBuffer jsonBody = new StringBuffer();
        jsonBody.append("{");
        if (params.get("fname") != null && params.get("fname") != ""){
            String fname = MapUtils.getString(params,"fname");
            jsonBody.append("keywords:")
                    .append('"').append(fname).append('"');
        }
        jsonBody.append("}");
        try {
            String resul = HttpUtil.doPost(url,jsonBody.toString());
            JSONObject object = JSONObject.parseObject(resul);
            list = (List<Map<String,Object>>) object.get("data");

        }catch (Exception e){
            e.printStackTrace();
        }
        return list;


    }

    public Map<String,Object> getSpeciesPestById(String indexid){
        Map<String,Object> map = new HashMap<>();
        String url = Global.getConfig("api.url")+"/wiki/api/wiki/getSpeciesPest/"+indexid;;

        try {
            String result = HttpUtil.doGet(url);
            JSONObject object = JSONObject.parseObject(result);
            map = (Map<String, Object>) object.get("data");
        }catch (Exception e){

        }
        return  map;
    }

    public List<Map<String,Object>> findSpeciesList() {

        String url = Global.getConfig("api.url")+"/wiki/api/species/getSpeciesList";

        List<Map<String,Object>> list = new ArrayList<>();
        StringBuffer jsonBody = new StringBuffer();
        jsonBody.append(",auditstatus:")
                .append(1)
                .append("}");
        try {
            String resul = HttpUtil.doPost(url,jsonBody.toString());
            JSONObject object = JSONObject.parseObject(resul);
            list = (List<Map<String,Object>>) object.get("data");

        }catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }

    public void savePest(Map<String, Object> params) {
        String url = Global.getConfig("api.url")+"/wiki/api/wiki/saveSpeciesPest";

        StringBuffer jsons = new StringBuffer();
        jsons.append("{fid:")
                .append("\"").append(UUID.randomUUID().toString().trim().replaceAll("-", "")).append("\"")
                .append(",fname:")
                .append("\"").append(MapUtils.getString(params, "pestName")).append("\"")
                .append(",fpinyin:")
                .append("\"").append(MapUtils.getString(params, "pinyin")).append("\"")
                .append(",speciesid:")
                .append("\"").append(MapUtils.getString(params, "speciesId")).append("\"")
                .append(",auditstatus:")
                .append("\"").append(1).append("\"")
                .append(",fimagesrc:")
                .append("\"").append(MapUtils.getString(params, "diseasePic")).append("\"")
                .append(",fmainfeatures:")
                .append("\"").append(MapUtils.getString(params, "mainFeatures")).append("\"")
                .append(",fhabit:")
                .append("\"").append(MapUtils.getString(params, "habit")).append("\"")
                .append(",fpetsregular:")
                .append("\"").append(MapUtils.getString(params, "petsRegular")).append("\"")
                .append(",fprotectmethod:")
                .append("\"").append(MapUtils.getString(params, "protectMethod")).append("\"")
                .append(",fremarks:")
                .append("\"").append(MapUtils.getString(params, "remark")).append("\"")
                .append(",parentid:")
                .append("\"").append(MapUtils.getString(params, "parentId")).append("\"")
                .append("}");

        try {
            HttpUtil.doPost(url, jsons.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updatePest(Map<String, Object> params) {
        String url = Global.getConfig("api.url") + "/wiki/api/wiki/updateSpeciesPestAudit";

        StringBuffer jsons = new StringBuffer();
        jsons.append("{fid:")
                .append("\"").append(UUID.randomUUID().toString().trim().replaceAll("-", "")).append("\"")
                .append(",fname:")
                .append("\"").append(MapUtils.getString(params, "pestName")).append("\"")
                .append(",fpinyin:")
                .append("\"").append(MapUtils.getString(params, "pinyin")).append("\"")
                .append(",speciesid:")
                .append("\"").append(MapUtils.getString(params, "speciesId")).append("\"")
                .append(",auditstatus:")
                .append("\"").append(1).append("\"")
                .append(",fimagesrc:")
                .append("\"").append(MapUtils.getString(params, "diseasePic")).append("\"")
                .append(",fmainfeatures:")
                .append("\"").append(MapUtils.getString(params, "mainFeatures")).append("\"")
                .append(",fhabit:")
                .append("\"").append(MapUtils.getString(params, "habit")).append("\"")
                .append(",fpetsregular:")
                .append("\"").append(MapUtils.getString(params, "petsRegular")).append("\"")
                .append(",fprotectmethod:")
                .append("\"").append(MapUtils.getString(params, "protectMethod")).append("\"")
                .append(",fremarks:")
                .append("\"").append(MapUtils.getString(params, "remark")).append("\"")
                .append(",indexid:")
                .append("\"").append(MapUtils.getString(params, "indexid")).append("\"")
                .append("}");
        try {
            HttpUtil.doPost(url, jsons.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deletePest(String indexid) {
        String url = Global.getConfig("api.url") + "/wiki/api/wiki/deletePestSpecies";
        StringBuffer jsons = new StringBuffer();
        jsons.append("{dataList:")
                .append("[")
                .append("{indexid:")
                .append("\"").append(indexid).append("\"")
                .append("}")
                .append("]")
                .append("}");
        try {
            HttpUtil.doPost(url, jsons.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
