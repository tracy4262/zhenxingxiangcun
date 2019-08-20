package com.ovit.nswy.member.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.xphsc.json.JSONHelper;
import com.ovit.nswy.member.mapper.NameLibraryMapper;
import com.ovit.nswy.member.model.PageList;
import com.ovit.nswy.member.service.NameLibraryService;
import com.ovit.nswy.member.service.SpeciesClassService;
import com.ovit.nswy.member.web.SpeciesClassController;
import com.ovit.nswy.member.web.base.Result;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class NameLibraryServiceImpl implements NameLibraryService {

    @Autowired
    private NameLibraryMapper nameLibraryMapper;

    @Autowired
    private SpeciesClassService service;

    @Autowired
    private NameLibraryService nameLibraryService;

    @Override
    public PageInfo<Map<String,Object>> findList(Map<String,Object> params){
        PageHelper.startPage(MapUtils.getInteger(params,"pageNum"), MapUtils.getInteger(params,"pageSize"));
        List<Map<String,Object>> list = new ArrayList<>();
        if(MapUtils.getString(params,"type").equals("0")){//物种
            list= nameLibraryMapper.findWikiConllectionList(params);

        }else if(MapUtils.getString(params,"type").equals("1")){//品种
            list= nameLibraryMapper.findVarietyList(params);

        }else if(MapUtils.getString(params,"type").equals("2")){//病害
            list= nameLibraryMapper.findDiseaseList(params);

        }else if(MapUtils.getString(params,"type").equals("3")){//虫害
            list= nameLibraryMapper.findPestList(params);

        }else if(MapUtils.getString(params,"type").equals("4")){//通用商品名
            list= nameLibraryMapper.findCurrencyCommodityList(params);

        }else if(MapUtils.getString(params,"type").equals("5")){//通用服务名
            list= nameLibraryMapper.findCurrencyServiceList(params);

        }
        for(Map<String,Object> map :list){
            String image=String.valueOf(map.get("image"));
            if(StringUtils.isNotBlank(image)){
                if (String.valueOf(map.get("image")).contains("[")) {
                    map.put("image", JSONHelper.parseArray(image));
                }else{
                    String[] images = image.split(",");
                    map.put("image",images);
                }
            }else{
                map.put("image",new String[]{});

            }
        }


        return new PageInfo<>(list);
    }

    @Override
    public List<Map<String,Object>> findConllectionList(Map<String,Object> params){
        List<Map<String,Object>> list = new ArrayList<>();
        if(MapUtils.getString(params,"type").equals("0")){//物种
            list= nameLibraryMapper.findWikiConllectionList(params);

        }else if(MapUtils.getString(params,"type").equals("1")){//品种
            list= nameLibraryMapper.findVarietyList(params);

        }else if(MapUtils.getString(params,"type").equals("2")){//病害
            list= nameLibraryMapper.findDiseaseList(params);

        }else if(MapUtils.getString(params,"type").equals("3")){//虫害
            list= nameLibraryMapper.findPestList(params);

        }else if(MapUtils.getString(params,"type").equals("4")){//通用商品名
            list= nameLibraryMapper.findCurrencyCommodityList(params);

        }else if(MapUtils.getString(params,"type").equals("5")){//通用服务名
            list= nameLibraryMapper.findCurrencyServiceList(params);

        }
        return list;
    }

    @Override
    public void saveLibrary(Map<String,Object> params){
        Map<String,Object> map = new HashMap<>();
        if(MapUtils.getString(params,"type").equals("0")){
            Result result = findBySpeciesName(MapUtils.getString(params,"label"),1);
            JSONArray jsonData = (JSONArray)result.getData();
            List<Map<String, Object>> jsonList  = jsonArrayToList(jsonData);
            String ficon = MapUtils.getString(jsonList.get(0),"ficon");

            map.put("image", ficon);
            map.put("account",MapUtils.getString(params,"account"));
            map.put("libraryId",MapUtils.getString(params,"value"));
            map.put("name",MapUtils.getString(params,"label"));
            map.put("classId",MapUtils.getString(jsonList.get(0),"fclassifiedid"));
            map.put("indexid",MapUtils.getString(jsonList.get(0),"indexid"));

            result = findByClassId(MapUtils.getString(jsonList.get(0),"fclassifiedid"));
            jsonData = (JSONArray)result.getData();

            jsonList  = jsonArrayToList(jsonData);
            if( MapUtils.getString(jsonList.get(0),"parentId").equals("0")){
                map.put("className","动物/"+ MapUtils.getString(jsonList.get(0),"className"));
            }else{
                map.put("className","植物/"+ MapUtils.getString(jsonList.get(0),"className"));
            }
            List<Map<String,Object>> list= nameLibraryMapper.findWikiConllectionList(map);
            if(list.size()== 0 || list == null){
                nameLibraryMapper.saveWikiLibraryInfo(map);
            }

        }else if(MapUtils.getString(params,"type").equals("1")){//品种
            Result result = findByVarieteyName(MapUtils.getString(params,"label"),1);
            JSONArray jsonData = (JSONArray)result.getData();
            List<Map<String, Object>> jsonList  = jsonArrayToList(jsonData);
            String ficon = MapUtils.getString(jsonList.get(0),"ficon");

            map.put("image",ficon);
            map.put("account",MapUtils.getString(params,"account"));
            map.put("libraryId",MapUtils.getString(params,"value"));
            map.put("name",MapUtils.getString(params,"label"));
            map.put("classId",MapUtils.getString(jsonList.get(0),"speciesid"));
            map.put("indexid",MapUtils.getString(jsonList.get(0),"indexid"));
            List<Map<String,Object>> list = nameLibraryMapper.findVarietyList(map);
            if(list.size()== 0 || list == null){
                nameLibraryMapper.saveVarietyLibraryInfo(map);
            }


        }else if(MapUtils.getString(params,"type").equals("2")){//病害
            Result result = findByDiseaseName(MapUtils.getString(params,"label"),1);

            JSONArray jsonData = (JSONArray)result.getData();
            List<Map<String, Object>> jsonList  = jsonArrayToList(jsonData);
            String ficon = MapUtils.getString(jsonList.get(0),"fimagesrc");

            map.put("image",ficon);
            map.put("account",MapUtils.getString(params,"account"));
            map.put("libraryId",MapUtils.getString(params,"value"));
            map.put("name",MapUtils.getString(params,"label"));
            map.put("classId",MapUtils.getString(jsonList.get(0),"speciesid"));
            map.put("indexid",MapUtils.getString(jsonList.get(0),"indexid"));
            List<Map<String,Object>> list = nameLibraryMapper.findDiseaseList(map);
            if(list.size()== 0 || list == null){
                nameLibraryMapper.saveDiseaseLibraryInfo(map);
            }


        } else if(MapUtils.getString(params,"type").equals("3")){//虫害
            Result result = findByPestName(MapUtils.getString(params,"label"),1);

            JSONArray jsonData = (JSONArray)result.getData();
            List<Map<String, Object>> jsonList  = jsonArrayToList(jsonData);
            String ficon = MapUtils.getString(jsonList.get(0),"fimagesrc");

            map.put("image",ficon);
            map.put("account",MapUtils.getString(params,"account"));
            map.put("libraryId",MapUtils.getString(params,"value"));
            map.put("name",MapUtils.getString(params,"label"));
            map.put("classId",MapUtils.getString(jsonList.get(0),"speciesid"));
            map.put("indexid",MapUtils.getString(jsonList.get(0),"indexid"));
            List<Map<String,Object>> list = nameLibraryMapper.findPestList(map);
            if(list.size()== 0 || list == null){
                nameLibraryMapper.savePestLibraryInfo(map);
            }


        }else if(MapUtils.getString(params,"type").equals("4")){//通用商品名

            List<Map<String,Object>> list = nameLibraryMapper.findCurrencyCommodityList(params);
            if(list.size()== 0 || list == null){
                nameLibraryMapper.saveCurrencyCommodityInfo(params);
            }


        }else if(MapUtils.getString(params,"type").equals("5")){//通用服务名

            List<Map<String,Object>> list = nameLibraryMapper.findCurrencyServiceList(params);
            if(list.size()== 0 || list == null){
                nameLibraryMapper.saveCurrencyServiceInfo(params);
            }


        }


    }



    @Override
    public void deleteLibrary(Map<String,Object> params){
        if(MapUtils.getString(params,"type").equals("0")){//物种
            nameLibraryMapper.deleteWikiLibraryInfo(params);
        }else if(MapUtils.getString(params,"type").equals("1")){//品种
            nameLibraryMapper.deleteVarietyLibraryInfo(params);
        }else if(MapUtils.getString(params,"type").equals("2")){//病害
            nameLibraryMapper.deleteDiseaseLibraryInfo(params);
        }else if(MapUtils.getString(params,"type").equals("3")){//虫害
            nameLibraryMapper.deletePestLibraryInfo(params);
        }else if(MapUtils.getString(params,"type").equals("4")){//通用商品名
            nameLibraryMapper.deleteCurrencyCommodityLibraryInfo(params);
        }else if(MapUtils.getString(params,"type").equals("5")){//通用服务名
            nameLibraryMapper.deleteCurrencyServiceLibraryInfo(params);
        }

    }

    @Override
    public PageList<Map<String,Object>> findVarietyList(Map<String,Object> params){
        if(StringUtils.isNotBlank(MapUtils.getString(params,"character"))){
            params.put("fpinyin",MapUtils.getString(params,"character"));
        }

        if(StringUtils.isNotBlank(MapUtils.getString(params,"dictName"))){
            params.put("keywords",MapUtils.getString(params,"dictName"));
        }

        List<Map<String,Object>> list = new ArrayList<>();
        JSONObject jsonObj = (JSONObject) JSON.toJSON(params);
        jsonObj.remove("character");
        jsonObj.remove("dictName");
        jsonObj.put("pageNum", 1);
        jsonObj.put("pageSize", 10000000);
        JSONObject data = null;
        if(MapUtils.getString(params,"type").equals("1")){//品种
            data= (JSONObject) service.listSpeciesVarietey(jsonObj);
        }else if(MapUtils.getString(params,"type").equals("2")){//病害
            data = (JSONObject) service.listSpeciesDisease(jsonObj);

        }else if(MapUtils.getString(params,"type").equals("3")){//虫害
            data = (JSONObject) service.listSpeciesPest(jsonObj);
        }

        JSONArray jsonData = data.getJSONArray("data");

        List<Map<String, Object>> jsonList  = jsonArrayToList(jsonData);
        for(Map<String, Object> map :jsonList){
            Map<String,Object> variety= new HashMap<>();
            variety.put("value",MapUtils.getString(map,"fid"));
            variety.put("label",MapUtils.getString(map,"fname"));
            list.add(variety);
        }

        List<String> speciesIdList = new ArrayList<>();
        if(MapUtils.getString(params,"type").equals("1")){//品种
            List<Map<String,Object>> list1 = nameLibraryService.findConllectionList(params);
            for (Map<String,Object> map :list1) {
                speciesIdList.add(MapUtils.getString(map, "libraryId"));
            }
        }else if(MapUtils.getString(params,"type").equals("2")){//病害
            List<Map<String,Object>> list1 = nameLibraryService.findConllectionList(params);
            for (Map<String,Object> map :list1) {
                speciesIdList.add(MapUtils.getString(map, "libraryId"));
            }

        }else if(MapUtils.getString(params,"type").equals("3")){//虫害
            List<Map<String,Object>> list1 = nameLibraryService.findConllectionList(params);
            for (Map<String,Object> map :list1) {
                speciesIdList.add(MapUtils.getString(map, "libraryId"));
            }
        }

        List<Map<String, Object>> list1 = new ArrayList<>();
        Iterator<Map<String, Object>> iterator = list.iterator();
        while (iterator.hasNext()) {
            Map<String, Object> map = iterator.next();
            String speciesId = MapUtils.getString(map,"value");
            if (speciesIdList.contains(speciesId)) {
                list1.add(map);
                iterator.remove();
            }
        }
        list1.addAll(list);

        params.put("pageNum", MapUtils.getInteger(params,"pageNum"));
        params.put("pageSize", MapUtils.getInteger(params,"pageSize"));
        return PageList.pageInfoList(params,list1);
    }


    @Override
    public PageList<Map<String,Object>> findShopCurrencyCommodity(Map<String,Object> params){

        Map<String,Object> currency = new HashMap<>();
        currency.put("pageNum",1);
        currency.put("pageSize",10000000);

        if(StringUtils.isNotBlank(MapUtils.getString(params,"character"))){
            currency.put("fpinyin",MapUtils.getString(params,"character"));
        }

        if(StringUtils.isNotBlank(MapUtils.getString(params,"dictName"))){
            currency.put("keywords",MapUtils.getString(params,"dictName"));
        }
        if(StringUtils.isNotBlank(MapUtils.getString(params,"account"))){
            currency.put("account",MapUtils.getString(params,"account"));
        }
        currency.put("type",MapUtils.getString(params,"type"));
        List<Map<String,Object>> list = new ArrayList<>();

        List<Map<String, Object>> jsonList  = nameLibraryMapper.findShopCurrencyCommodity(currency);
        for(Map<String, Object> map :jsonList){
            Map<String,Object> variety= new HashMap<>();
            variety.put("commodityId",MapUtils.getString(map,"fid"));
            variety.put("commodityName",MapUtils.getString(map,"fname"));
            variety.put("industryId",MapUtils.getString(map,"industryId"));
            variety.put("productId",MapUtils.getString(map,"productId"));
            variety.put("classId",MapUtils.getString(map,"classId"));
            variety.put("image",MapUtils.getString(map,"image"));
            if(MapUtils.getString(params,"type").equals("2")){
                variety.put("leftServiceId",MapUtils.getString(map,"serviceId"));
                variety.put("serviceClassId",MapUtils.getString(map,"serviceClassId"));
                variety.put("servicePeopleName",MapUtils.getString(map,"servicePeopleName"));
            }
            list.add(variety);
        }

        List<String> speciesIdList = new ArrayList<>();
        List<Map<String,Object>> list2 = new ArrayList<>();
        if(MapUtils.getString(params,"type").equals("1")){
            params.put("type","4");//通用商品名
            List<Map<String,Object>> list1 = nameLibraryService.findConllectionList(params);
            speciesIdList = new ArrayList<>();
            for (Map<String,Object> map :list1) {
                speciesIdList.add(MapUtils.getString(map, "commodityId"));
            }

        }else if(MapUtils.getString(params,"type").equals("2")){
            params.put("type","5");//通用服务名
            List<Map<String,Object>> list1 = nameLibraryService.findConllectionList(params);
            speciesIdList = new ArrayList<>();
            for (Map<String,Object> map :list1) {
                speciesIdList.add(MapUtils.getString(map, "serviceId"));
            }

        }

        Iterator<Map<String, Object>> iterator = list.iterator();
        while (iterator.hasNext()) {
            Map<String, Object> map = iterator.next();
            String speciesId = MapUtils.getString(map,"commodityId");
            if (speciesIdList.contains(speciesId)) {
                list2.add(map);
                iterator.remove();
            }
        }
        list2.addAll(list);


        params.put("pageNum", MapUtils.getInteger(params,"pageNum"));
        params.put("pageSize", MapUtils.getInteger(params,"pageSize"));
        return PageList.pageInfoList(params,list2);
    }



    private Result findByClassId(String classId) {

        Map<String, Object> map = new HashMap<>();
        map.put("classId", classId);

        return service.findByClassId(JSON.toJSON(map));
    }


    private Result findBySpeciesName(String fname,Integer auditstatus) {

        Map<String, Object> map = new HashMap<>();
        map.put("fname", fname);
        if(!auditstatus.equals(0)){
            map.put("auditstatus", auditstatus);
        }
        return service.findBySpeciesName(JSON.toJSON(map));
    }

    private Result findByVarieteyName(String fname,Integer auditstatus) {

        Map<String, Object> map = new HashMap<>();
        map.put("fname", fname);
        if(!auditstatus.equals(0)){
            map.put("auditstatus", auditstatus);
        }
        return service.findByVarieteyName(JSON.toJSON(map));
    }

    private Result findByDiseaseName(String fname,Integer auditstatus) {

        Map<String, Object> map = new HashMap<>();
        map.put("fname", fname);
        if(!auditstatus.equals(0)){
            map.put("auditstatus", auditstatus);
        }
        return service.findByDiseaseName(JSON.toJSON(map));
    }

    private Result findByPestName(String fname,Integer auditstatus) {

        Map<String, Object> map = new HashMap<>();
        map.put("fname", fname);
        if(!auditstatus.equals(0)){
            map.put("auditstatus", auditstatus);
        }
        return service.findByPestName(JSON.toJSON(map));
    }



    private Result findVarieteyBySpeciesId(String speciesid,Integer auditstatus) {

        Map<String, Object> map = new HashMap<>();
        map.put("speciesid", speciesid);
        if(!auditstatus.equals(0)){
            map.put("auditstatus", auditstatus);
        }
        return service.findByVarieteyName(JSON.toJSON(map));
    }



    /**
     * JSONArray转为List
     */
    private List<Map<String,Object>> jsonArrayToList(JSONArray jsonArray) {

        List<Map<String,Object>> list = new ArrayList<>();
        for (Object object : jsonArray) {
            JSONObject jsonObject = (JSONObject) object;
            Map<String,Object> t = JSONObject.toJavaObject(jsonObject, Map.class);
            list.add(t);
        }
        return list;
    }

}
