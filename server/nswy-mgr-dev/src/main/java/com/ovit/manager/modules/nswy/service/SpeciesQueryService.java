package com.ovit.manager.modules.nswy.service;

import com.alibaba.fastjson.JSONObject;
import com.github.xphsc.http.HttpUtil;
import com.ovit.manager.common.config.Global;

import com.ovit.manager.modules.nswy.dao.ExpertApplyDao;

import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SpeciesQueryService {

    @Autowired
    private ExpertApplyDao ExpertApplyDao;

    public List<Map<String,Object>> getSpeciesById(String speciesid){
        List<Map<String,Object>> list = new ArrayList<>();

        String url = Global.getConfig("api.url")+"/wiki/api/species/findBySpeciesName";
        StringBuffer jsonBody = new StringBuffer();
        jsonBody.append("{speciesid:")
                .append("\"").append(speciesid).append("\"")
                .append("}");
        try {
            String result = HttpUtil.doPost(url, jsonBody.toString());
            JSONObject object = JSONObject.parseObject(result);
            list  = (List<Map<String,Object>>) object.get("data");

        }catch (Exception e){
            e.printStackTrace();
        }
        return  list;
    }

    public List<Map<String,Object>> findSpeciesList(Map<String, Object> params) {

        String url = Global.getConfig("api.url")+"/wiki/speciesclass/findSpeciesList";

        List<Map<String,Object>> list = new ArrayList<>();
        try {
            String resul = HttpUtil.doGet(url);
            JSONObject object = JSONObject.parseObject(resul);
            Map<String,Object> speicesList = (Map<String,Object>) object.get("data");
            list.addAll((List<Map<String,Object>>)speicesList.get("list"));
        }catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }

    public void saveSpeciesClass(Map<String, Object> params){
        String url = Global.getConfig("api.url")+"/wiki/speciesclass/insertSpeciesclass";

        StringBuffer jsons = new StringBuffer();
        jsons.append("{classId:")
                .append("\"").append(MapUtils.getString(params,"speciesCode")).append("\"")
                .append(",className:")
                .append("\"").append(MapUtils.getString(params,"speciesName")).append("\"")
                .append(",parentId:")
                .append("\"").append(MapUtils.getString(params,"pid")).append("\"")
                .append("}");
        try {
            HttpUtil.doPost(url,jsons.toString());
        }catch (Exception e){
            e.printStackTrace();
        }


    }

    public void saveSpecies(Map<String, Object> params){
        String url = Global.getConfig("api.url")+"/wiki/api/species/insertSpecies";

        StringBuffer jsons = new StringBuffer();
        jsons.append("{fclassifiedid:")
                .append("\"").append(MapUtils.getString(params,"pid")).append("\"")
                .append(",fname:")
                .append("\"").append(MapUtils.getString(params,"speciesName")).append("\"")
                .append(",fcode:")
                .append("\"").append(MapUtils.getString(params,"speciesCode")).append("\"")
                .append(",speciesid:")
                .append("\"").append(UUID.randomUUID().toString().trim().replaceAll("-", "")).append("\"")
                .append(",fpinyin:")
                .append("\"").append(MapUtils.getString(params,"pinyin")).append("\"")
                .append(",speciesVulgo:")
                .append("\"").append(MapUtils.getString(params,"speciesVulgo")).append("\"")
                .append(",otherClassify:")
                .append("\"").append(MapUtils.getString(params,"otherClassify")).append("\"")
                .append(",findustriaclassifiedid:")
                .append("\"").append(MapUtils.getString(params,"findustriaclassifiedid")).append("\"")
                .append(",fisprotection:")
                .append("\"").append(MapUtils.getString(params,"fisprotection")).append("\"")
                .append(",majorProduct:")
                .append("\"").append(MapUtils.getString(params,"majorProduct")).append("\"")
                .append(",fshapefeatureid:")
                .append("\"").append(MapUtils.getString(params,"fshapefeatureid")).append("\"")
                .append(",fremarks:")
                .append("\"").append(MapUtils.getString(params,"remark")).append("\"")
                .append(",auditstatus:")
                .append(1)
                .append(",fisdelete:")
                .append(0)
                .append(",fstate:")
                .append(1)
                .append("}");
        try {
            HttpUtil.doPost(url,jsons.toString());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void updateSpecies(Map<String, Object> params){
        String url = Global.getConfig("api.url")+"/wiki/api/species/updateSpeciesAudit";

        StringBuffer jsons = new StringBuffer();
        jsons.append("{fname:")
                .append("\"").append(MapUtils.getString(params,"speciesName")).append("\"")
                .append(",indexid:")
                .append("\"").append(MapUtils.getString(params,"indexid")).append("\"")
                .append(",fcode:")
                .append("\"").append(MapUtils.getString(params,"speciesCode")).append("\"")
                .append(",fpinyin:")
                .append("\"").append(MapUtils.getString(params,"pinyin")).append("\"")
                .append(",speciesVulgo:")
                .append("\"").append(MapUtils.getString(params,"speciesVulgo")).append("\"")
                .append(",otherClassify:")
                .append("\"").append(MapUtils.getString(params,"otherClassify")).append("\"")
                .append(",fclassifiedid:")
                .append("\"").append(MapUtils.getString(params,"fclassifiedid")).append("\"")
                .append(",findustriaclassifiedid:")
                .append("\"").append(MapUtils.getString(params,"findustriaclassifiedid")).append("\"")
                .append(",fisprotection:")
                .append("\"").append(MapUtils.getString(params,"fisprotection")).append("\"")
                .append(",majorProduct:")
                .append("\"").append(MapUtils.getString(params,"majorProduct")).append("\"")
                .append(",fshapefeatureid:")
                .append("\"").append(MapUtils.getString(params,"fshapefeatureid")).append("\"")
                .append(",fremarks:")
                .append("\"").append(MapUtils.getString(params,"remark")).append("\"")
                .append("}");
        try {
            HttpUtil.doPost(url,jsons.toString());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void deleteSpecies(String indexid) {
        String url = Global.getConfig("api.url") + "/wiki/api/species/deleteWikiSpecies";
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

    public void deleteSpeciesClass(String indexid) {
        String url = Global.getConfig("api.url") + "/wiki/api/species/deleteSpeciesclass/"+indexid;

        try {
            HttpUtil.doGet(url);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }




}
