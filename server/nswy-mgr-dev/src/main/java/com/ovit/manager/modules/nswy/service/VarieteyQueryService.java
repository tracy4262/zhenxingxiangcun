package com.ovit.manager.modules.nswy.service;

import com.alibaba.fastjson.JSONObject;
import com.github.xphsc.http.HttpUtil;
import com.ovit.manager.common.config.Global;
import com.ovit.manager.common.persistence.Page;
import com.ovit.manager.modules.nswy.dao.ExpertApplyDao;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class VarieteyQueryService {

    @Autowired
    private ExpertApplyDao ExpertApplyDao;

    public List<Map<String,Object>> findVarieteyQueryList(Map<String, Object> params) {
        String url = Global.getConfig("api.url")+"/wiki/api/wiki/getVarieteyList";

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

    public Map<String,Object> getSpeciesVarieteyById(String indexid){
        Map<String,Object> map = new HashMap<>();
        String url = Global.getConfig("api.url")+"/wiki/api/wiki/getSpeciesVarietey/"+indexid;;

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

    public void saveVarietey(Map<String, Object> params) {
        String url = Global.getConfig("api.url")+"/wiki/api/wiki/saveSpeciesVarietey";

        StringBuffer jsons = new StringBuffer();
        jsons.append("{fid:")
                .append("\"").append(UUID.randomUUID().toString().trim().replaceAll("-", "")).append("\"")
                .append(",fname:")
                .append("\"").append(MapUtils.getString(params, "varietyName")).append("\"")
                .append(",fpinyin:")
                .append("\"").append(MapUtils.getString(params, "pinyin")).append("\"")
                .append(",speciesid:")
                .append("\"").append(MapUtils.getString(params, "speciesId")).append("\"")
                .append(",auditstatus:")
                .append("\"").append(1).append("\"")
                .append(",fimagesrc:")
                .append("\"").append(MapUtils.getString(params, "diseasePic")).append("\"")
                .append(",fvarietykind:")
                .append("\"").append(MapUtils.getString(params, "fvarietykind")).append("\"")
                .append(",fbreedingunit:")
                .append("\"").append(MapUtils.getString(params, "fbreedingunit")).append("\"")
                .append(",fistransgene:")
                .append("\"").append(MapUtils.getString(params, "fistransgene")).append("\"")
                .append(",fapplydate:")
                .append("\"").append(MapUtils.getString(params, "fapplydate")).append("\"")
                .append(",fapplynumber:")
                .append("\"").append(MapUtils.getString(params, "fapplynumber")).append("\"")
                .append(",fapplyannouncedate:")
                .append("\"").append(MapUtils.getString(params, "fapplyannouncedate")).append("\"")
                .append(",fapplyannouncenumber:")
                .append("\"").append(MapUtils.getString(params, "fapplyannouncenumber")).append("\"")
                .append(",fauthdate:")
                .append("\"").append(MapUtils.getString(params, "fauthdate")).append("\"")
                .append(",fauthnumber:")
                .append("\"").append(MapUtils.getString(params, "fauthnumber")).append("\"")
                .append(",fauthannouncedate:")
                .append("\"").append(MapUtils.getString(params, "fauthannouncedate")).append("\"")
                .append(",fauthannouncenumber:")
                .append("\"").append(MapUtils.getString(params, "fauthannouncenumber")).append("\"")
                .append(",fgrowpeople:")
                .append("\"").append(MapUtils.getString(params, "fgrowpeople")).append("\"")
                .append(",fvarietyowner:")
                .append("\"").append(MapUtils.getString(params, "fvarietyowner")).append("\"")
                .append(",fvarietyapprnum:")
                .append("\"").append(MapUtils.getString(params, "fvarietyapprnum")).append("\"")
                .append(",fvarietyapprdate:")
                .append("\"").append(MapUtils.getString(params, "fvarietyapprdate")).append("\"")
                .append(",foutput:")
                .append("\"").append(MapUtils.getString(params, "foutput")).append("\"")
                .append(",fgrowteachology:")
                .append("\"").append(MapUtils.getString(params, "fgrowteachology")).append("\"")
                .append(",fsuiteplatearea:")
                .append("\"").append(MapUtils.getString(params, "fsuiteplatearea")).append("\"")
                .append(",fmarketsituation:")
                .append("\"").append(MapUtils.getString(params, "fmarketsituation")).append("\"")
                .append(",fvarietyorigin:")
                .append("\"").append(MapUtils.getString(params, "fvarietyorigin")).append("\"")
                .append(",fsuiteplatearea:")
                .append("\"").append(MapUtils.getString(params, "fsuiteplatearea")).append("\"")
                .append(",fmarketsituation:")
                .append("\"").append(MapUtils.getString(params, "fmarketsituation")).append("\"")
                .append(",fvarietyorigin:")
                .append("\"").append(MapUtils.getString(params, "fvarietyorigin")).append("\"")
                .append(",fvarietyapprunit:")
                .append("\"").append(MapUtils.getString(params, "fvarietyapprunit")).append("\"")
                .append(",ffeature:")
                .append("\"").append(MapUtils.getString(params, "ffeature")).append("\"")
                .append(",parentid:")
                .append("\"").append(MapUtils.getString(params, "parentId")).append("\"")
                .append("}");

        try {
            HttpUtil.doPost(url, jsons.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateVarietey(Map<String, Object> params) {
        String url = Global.getConfig("api.url") + "/wiki/api/wiki/updateSpeciesVarieteyAudit";

        StringBuffer jsons = new StringBuffer();
        jsons.append("{fid:")
                .append("\"").append(UUID.randomUUID().toString().trim().replaceAll("-", "")).append("\"")
                .append(",fname:")
                .append("\"").append(MapUtils.getString(params, "varietyName")).append("\"")
                .append(",fpinyin:")
                .append("\"").append(MapUtils.getString(params, "pinyin")).append("\"")
                .append(",speciesid:")
                .append("\"").append(MapUtils.getString(params, "speciesId")).append("\"")
                .append(",auditstatus:")
                .append("\"").append(1).append("\"")
                .append(",fimagesrc:")
                .append("\"").append(MapUtils.getString(params, "diseasePic")).append("\"")
                .append(",fvarietykind:")
                .append("\"").append(MapUtils.getString(params, "fvarietykind")).append("\"")
                .append(",fbreedingunit:")
                .append("\"").append(MapUtils.getString(params, "fbreedingunit")).append("\"")
                .append(",fistransgene:")
                .append("\"").append(MapUtils.getString(params, "fistransgene")).append("\"")
                .append(",fapplydate:")
                .append("\"").append(MapUtils.getString(params, "fapplydate")).append("\"")
                .append(",fapplynumber:")
                .append("\"").append(MapUtils.getString(params, "fapplynumber")).append("\"")
                .append(",fapplyannouncedate:")
                .append("\"").append(MapUtils.getString(params, "fapplyannouncedate")).append("\"")
                .append(",fapplyannouncenumber:")
                .append("\"").append(MapUtils.getString(params, "fapplyannouncenumber")).append("\"")
                .append(",fauthdate:")
                .append("\"").append(MapUtils.getString(params, "fauthdate")).append("\"")
                .append(",fauthnumber:")
                .append("\"").append(MapUtils.getString(params, "fauthnumber")).append("\"")
                .append(",fauthannouncedate:")
                .append("\"").append(MapUtils.getString(params, "fauthannouncedate")).append("\"")
                .append(",fauthannouncenumber:")
                .append("\"").append(MapUtils.getString(params, "fauthannouncenumber")).append("\"")
                .append(",fgrowpeople:")
                .append("\"").append(MapUtils.getString(params, "fgrowpeople")).append("\"")
                .append(",fvarietyowner:")
                .append("\"").append(MapUtils.getString(params, "fvarietyowner")).append("\"")
                .append(",fvarietyapprnum:")
                .append("\"").append(MapUtils.getString(params, "fvarietyapprnum")).append("\"")
                .append(",fvarietyapprdate:")
                .append("\"").append(MapUtils.getString(params, "fvarietyapprdate")).append("\"")
                .append(",foutput:")
                .append("\"").append(MapUtils.getString(params, "foutput")).append("\"")
                .append(",fgrowteachology:")
                .append("\"").append(MapUtils.getString(params, "fgrowteachology")).append("\"")
                .append(",fsuiteplatearea:")
                .append("\"").append(MapUtils.getString(params, "fsuiteplatearea")).append("\"")
                .append(",fmarketsituation:")
                .append("\"").append(MapUtils.getString(params, "fmarketsituation")).append("\"")
                .append(",fvarietyorigin:")
                .append("\"").append(MapUtils.getString(params, "fvarietyorigin")).append("\"")
                .append(",fsuiteplatearea:")
                .append("\"").append(MapUtils.getString(params, "fsuiteplatearea")).append("\"")
                .append(",fmarketsituation:")
                .append("\"").append(MapUtils.getString(params, "fmarketsituation")).append("\"")
                .append(",fvarietyorigin:")
                .append("\"").append(MapUtils.getString(params, "fvarietyorigin")).append("\"")
                .append(",fvarietyapprunit:")
                .append("\"").append(MapUtils.getString(params, "fvarietyapprunit")).append("\"")
                .append(",ffeature:")
                .append("\"").append(MapUtils.getString(params, "ffeature")).append("\"")
                .append(",indexid:")
                .append("\"").append(MapUtils.getString(params, "indexid")).append("\"")
                .append("}");

        try {
            HttpUtil.doPost(url, jsons.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteVarietey(String indexid) {
        String url = Global.getConfig("api.url") + "/wiki/api/wiki/deleteVarietySpecies";
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
