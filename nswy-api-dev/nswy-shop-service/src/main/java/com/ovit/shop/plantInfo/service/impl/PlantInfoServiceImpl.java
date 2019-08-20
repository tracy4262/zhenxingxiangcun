package com.ovit.shop.plantInfo.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;
import com.ovit.shop.plantInfo.mapper.PlantInfoMapper;
import com.ovit.shop.plantInfo.service.PlantInfoService;
import com.ovit.shop.species.service.SpeciesClassService;
import com.ovit.shop.util.ImageUtil;
import com.ovit.shop.util.Result;
import com.ovit.shop.util.UuidUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.omg.CORBA.OBJ_ADAPTER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PlantInfoServiceImpl implements PlantInfoService {


    @Autowired
    private PlantInfoMapper plantInfoMapper;

    @Autowired
    private SpeciesClassService speciesClassService;

    @Override
    public List<Map<String,Object>> findPlantYearInfo(Map<String, Object> params){

        return plantInfoMapper.findPlantYearInfo(params);

    }

    @Override
    public void savePlantYearInfo(Map<String, Object> params){
        List<Map<String,Object>> list = plantInfoMapper.findPlantYearInfo(params);
        if(list == null || list.size() == 0){
            params.put("id", UuidUtil.get32UUID());
            plantInfoMapper.savePlantYearInfo(params);
        }

    }

    @Override
    public void deletePlantYearInfo(Map<String, Object> params){
        plantInfoMapper.deletePlantYearInfo(params);
    }

    @Override
    public PageInfo<Map<String,Object>> findPlantSpeciesInfo(Map<String, Object> params){
        PageHelper.startPage(MapUtils.getInteger(params,"pageNum"), MapUtils.getInteger(params,"pageSize"));
        List<Map<String,Object>> list  =  plantInfoMapper.findPlantSpeciesInfo(params);
        for(Map<String,Object> map :list){
            String image=String.valueOf(map.get("image"));
            if(StringUtils.isNotBlank(image)){
                if (String.valueOf(map.get("image")).contains("[")) {
                    map.put("image", JSONObject.parseArray(image));
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
    public void savePlantSpeciesInfo(Map<String, Object> params){
        params.put("speciesId",MapUtils.getString(params,"value"));
        List<Map<String,Object>> list  =  plantInfoMapper.findPlantSpeciesInfo(params);
        if(list.size() ==0 || list ==null){
            params.put("id",UuidUtil.get32UUID());
            Object result = findBySpeciesName(MapUtils.getString(params,"label"),1);

            //JSONObject jsonObject= JSONObject.parseObject(result.toString());
            //JSONArray classArray = JSONArray.parseArray(jsonObject.get("data").toString());
            //List<Map<String, Object>> jsonList  = jsonArrayToList(classArray);

            Map<String,Object> maps = new ObjectMapper().convertValue(result, Map.class);

            List<Map<String, Object>> jsonList = (List<Map<String, Object>>) maps.get("data");


            String ficon = MapUtils.getString(jsonList.get(0),"ficon");
            params.put("image", ficon);

            params.put("speciesName",MapUtils.getString(params,"label"));
            plantInfoMapper.savePlantSpeciesInfo(params);
        }


    }

    @Override
    public void deletePlantSpeciesInfo(Map<String, Object> params){
        plantInfoMapper.deletePlantSpeciesInfo(params);
    }



    @Override
    public PageInfo<Map<String,Object>> findPlantProductionInfo(Map<String, Object> params){
        PageHelper.startPage(MapUtils.getInteger(params,"pageNum"), MapUtils.getInteger(params,"pageSize"));
        List<Map<String,Object>> list  =  plantInfoMapper.findPlantProductionInfo(params);
        for(Map<String,Object> map :list){
            String baseId=MapUtils.getString(map,"baseId");
            if(StringUtils.isNotBlank(baseId)){
                String[] base = baseId.split(";");
                map.put("baseId",base);
                String baseName = "";
                String land = "";
                for(int i=0;i<base.length;i++){
                    Map<String,Object> baseMap = new HashMap<>();
                    baseMap.put("baseId",base[i]);
                    List<Map<String,Object>> baseList =  plantInfoMapper.findProductionBase(baseMap);
                    baseName +=MapUtils.getString(baseList.get(0),"baseName")+",";
                    land +=MapUtils.getString(baseList.get(0),"land")+",";
                }
                baseName = baseName.substring(0,baseName.length()-1);
                String[] baseNames = baseName.split(",");
                land = land.substring(0,land.length()-1);
                String[] lands = land.split(",");
                map.put("baseName",baseNames);
                map.put("land",lands);
            }else{
                map.put("baseId",new String[]{});
                map.put("baseName",new String[]{});
                map.put("land",new String[]{});
            }

        }

        return new PageInfo<>(list);

    }

    @Override
    public List<Map<String,Object>> findProductionBase(Map<String, Object> params){

        List<Map<String,Object>> list  =  plantInfoMapper.findProductionBase(params);


        return  list;

    }




    @Override
    public List<Map<String,Object>> findPlantProductionNumber(Map<String, Object> params){

        List<Map<String,Object>> list  =  plantInfoMapper.findPlantProductionInfo(params);


        return list;

    }


    @Override
    public void saveOrUpdatePlantProduction(Map<String, Object> params){

        if(StringUtils.isBlank(MapUtils.getString(params,"serialNumber"))){

            Map<String,Object> map = new HashMap<>();
            map.put("account",MapUtils.getString(params,"account"));
            map.put("yearId",MapUtils.getString(params,"yearId"));

            List<Map<String,Object>> list  =  plantInfoMapper.findPlantProductionInfo(map);
            String id = "";
            if(list.size()>0){
                id = String.valueOf(list.size()+1);
            }else{
                id = "1";
            }

            List<Map<String,Object>> yearList = plantInfoMapper.findPlantYearInfo(params);

            String fileName = MapUtils.getString(yearList.get(0),"fileName");

            fileName = fileName.substring(0,4);

            String serialNumber = "SC"+fileName+id;

            params.put("serialNumber",serialNumber);
        }

        String baseId = MapUtils.getString(params,"baseId");
        if(StringUtil.isNotEmpty(baseId)) {
            JSONArray base = JSONArray.parseArray(baseId);
            if (base.size() == 0) {
                params.put("baseId", "");
            } else {
                String[] members = ImageUtil.getJsonToStringArray(baseId);
                String memberMerge = ImageUtil.stringArrayMerge(members);
                params.put("baseId", memberMerge);
            }
        }else{
            params.put("baseId", "");
        }


        plantInfoMapper.saveOrUpdatePlantProduction(params);


    }

    @Override
    public void deletePlantProductionInfo(Map<String, Object> params){
        plantInfoMapper.deletePlantProductionInfo(params);
    }



    @Override
    public PageInfo<Map<String,Object>> findPlantOutputInfo(Map<String, Object> params){
        PageHelper.startPage(MapUtils.getInteger(params,"pageNum"), MapUtils.getInteger(params,"pageSize"));
        List<Map<String,Object>> list  =  plantInfoMapper.findPlantOutputInfo(params);
        for(Map<String,Object> map :list){
            String baseId=MapUtils.getString(map,"baseId");
            if(StringUtils.isNotBlank(baseId)){
                String[] base = baseId.split(";");
                map.put("baseId",base);
                String baseName = "";
                String land = "";
                for(int i=0;i<base.length;i++){
                    Map<String,Object> baseMap = new HashMap<>();
                    baseMap.put("baseId",base[i]);
                    List<Map<String,Object>> baseList =  plantInfoMapper.findProductionBase(baseMap);
                    baseName +=MapUtils.getString(baseList.get(0),"baseName")+",";
                    land +=MapUtils.getString(baseList.get(0),"land")+",";
                }
                baseName = baseName.substring(0,baseName.length()-1);
                String[] baseNames = baseName.split(",");
                land = land.substring(0,land.length()-1);
                String[] lands = land.split(",");
                map.put("baseName",baseNames);
                map.put("land",lands);
            }else{
                map.put("baseId",new String[]{});
                map.put("baseName",new String[]{});
                map.put("land",new String[]{});
            }

        }

        return new PageInfo<>(list);

    }


    @Override
    public List<Map<String,Object>> findProductionBaseOfOutput(Map<String, Object> params){

        List<Map<String,Object>> list  =  plantInfoMapper.findPlantOutputInfo(params);


        return  list;

    }


    @Override
    public void saveOrUpdatePlantOutputInfo(Map<String, Object> params){
        if(StringUtils.isNotBlank(MapUtils.getString(params,"id"))){
            plantInfoMapper.saveOrUpdatePlantOutputInfo(params);
        }else{
            List<Map<String,Object>> list  =  plantInfoMapper.findPlantOutputInfo(params);
            if(list.size() ==0 || list == null){
                plantInfoMapper.saveOrUpdatePlantOutputInfo(params);
            }
        }



    }

    @Override
    public void deletePlantOutputInfo(Map<String, Object> params){
        plantInfoMapper.deletePlantOutputInfo(params);
    }


    @Override
    public List<Map<String,Object>> findPlantTitleInfo(Map<String, Object> params){

        List<Map<String,Object>> list  =  plantInfoMapper.findPlantTitleInfo(params);


        return  list;

    }

    @Override
    public PageInfo<Map<String,Object>> findPlantProductionPlanInfo(Map<String, Object> params){
        PageHelper.startPage(MapUtils.getInteger(params,"pageNum"), MapUtils.getInteger(params,"pageSize"));
        List<Map<String,Object>> list  =  new ArrayList<>();
        if(MapUtils.getString(params,"type").equals("1")){//播种
            list = plantInfoMapper.findPlantSowInfo(params);
        }else if(MapUtils.getString(params,"type").equals("2")){//施肥
            list = plantInfoMapper.findPlantFertilizeInfo(params);
        }else if(MapUtils.getString(params,"type").equals("3")){//施药
            list = plantInfoMapper.findPlantMedicineInfo(params);
        }else if(MapUtils.getString(params,"type").equals("4")){//调产
            list = plantInfoMapper.findPlantResearchInfo(params);
        }else if(MapUtils.getString(params,"type").equals("5")){//收获
            list = plantInfoMapper.findPlantHarvestInfo(params);
        }
        for(Map<String,Object> map :list){
            String baseId=MapUtils.getString(map,"baseId");
            if(StringUtils.isNotBlank(baseId)){
                String[] base = baseId.split(";");
                map.put("baseId",base);
                String baseName = "";
                String land = "";
                for(int i=0;i<base.length;i++){
                    Map<String,Object> baseMap = new HashMap<>();
                    baseMap.put("baseId",base[i]);
                    List<Map<String,Object>> baseList =  plantInfoMapper.findProductionBase(baseMap);
                    baseName +=MapUtils.getString(baseList.get(0),"baseName")+",";
                    land +=MapUtils.getString(baseList.get(0),"land")+",";
                }
                baseName = baseName.substring(0,baseName.length()-1);
                String[] baseNames = baseName.split(",");
                land = land.substring(0,land.length()-1);
                String[] lands = land.split(",");
                map.put("baseName",baseNames);
                map.put("land",lands);
            }else{
                map.put("baseId",new String[]{});
                map.put("baseName",new String[]{});
                map.put("land",new String[]{});
            }
            map.put("plantId",MapUtils.getString(map,"id"));
            map.put("plantParentId",MapUtils.getString(params,"plantParentId"));
            List<Map<String,Object>> textList = plantInfoMapper.findTextPreviewInfo(map);
            if(textList.size()>0){
                map.put("preview",MapUtils.getString(textList.get(0),"textPreview"));
                map.put("previewId",MapUtils.getString(textList.get(0),"previewId"));
            }else{
                map.put("preview","");
                map.put("previewId","");
            }

            if(MapUtils.getString(params,"type").equals("4")){
                map.put("productNameId",MapUtils.getInteger(map,"productNameId"));
            }else if(MapUtils.getString(params,"type").equals("5")){
                map.put("productNameId",MapUtils.getInteger(map,"productNameId"));
                String harvestPlot = MapUtils.getString(map,"harvestPlot");
                if(StringUtils.isNotBlank(harvestPlot)){
                    String[] plot = harvestPlot.split(";");
                    map.put("harvestPlot",plot);
                }else{
                    map.put("harvestPlot",new String[]{});
                }
            }


        }

        return new PageInfo<>(list);

    }




    @Override
    public void saveOrUpdateProductionPlanInfo(Map<String, Object> params){
        String uuid = UuidUtil.get32UUID();
        Map<String,Object> text = new HashMap<>();
        if(MapUtils.getString(params,"id").equals("")){
            params.put("id",uuid);
        }else{
            uuid = MapUtils.getString(params,"id");
            text.put("id",MapUtils.getString(params,"previewId"));
        }
        if(MapUtils.getString(params,"type").equals("1")){

            plantInfoMapper.saveOrUpdatePlantSowInfo(params);

        }else if(MapUtils.getString(params,"type").equals("2")){

            plantInfoMapper.saveOrUpdatePlantFertilizeInfo(params);

        }else if(MapUtils.getString(params,"type").equals("3")){

            plantInfoMapper.saveOrUpdatePlantMedicineInfo(params);

        }else if(MapUtils.getString(params,"type").equals("4")){

            plantInfoMapper.saveOrUpdatePlantResearchInfo(params);

        }else if(MapUtils.getString(params,"type").equals("5")){
            String harvestPlot = new String();

            List lists = (List)params.get("harvestPlot");
            for(int i =0;i<lists.size();i++){
                harvestPlot += lists.get(0)+";";
            }
            harvestPlot=harvestPlot.substring(0,harvestPlot.length()-1);
            params.put("harvestPlot", harvestPlot);

            plantInfoMapper.saveOrUpdatePlantHarvestInfo(params);

        }

        text.put("account",MapUtils.getString(params,"account"));
        text.put("yearId",MapUtils.getString(params,"yearId"));
        text.put("plantId",uuid);
        text.put("plantParentId",MapUtils.getString(params,"plantParentId"));
        text.put("wikiId",MapUtils.getString(params,"wikiId"));
        text.put("textPreview",MapUtils.getString(params,"preview"));
        text.put("serialNumber",MapUtils.getString(params,"serialNumber"));
        plantInfoMapper.saveOrUpdateTextPreviewInfo(text);


    }

    @Override
    public void deletePlantProductionPlanInfo(Map<String, Object> params){
        if(MapUtils.getString(params,"type").equals("1")){//播种

            plantInfoMapper.deletePlantSowInfo(params);

        }else if(MapUtils.getString(params,"type").equals("2")){

            plantInfoMapper.deletePlantFertilizeInfo(params);

        }else if(MapUtils.getString(params,"type").equals("3")){

            plantInfoMapper.deletePlantMedicineInfo(params);

        }else if(MapUtils.getString(params,"type").equals("4")){

            plantInfoMapper.deletePlantResearchInfo(params);

        }else if(MapUtils.getString(params,"type").equals("5")){

            plantInfoMapper.deletePlantHarvestInfo(params);

        }

    }

    @Override
    public List<Map<String,Object>> findHarvestProductInfo(Map<String, Object> params){

        List<Map<String,Object>> list  =  plantInfoMapper.findPlantOutputInfo(params);
        for(Map<String,Object> map :list){

            if(MapUtils.getString(map,"rewardType").equals("一次性收货")){

                List<Map<String,Object>> plantInfo = plantInfoMapper.findPlantHarvestInfo(params);
                if(plantInfo.size()>0){
                    map.put("isHarvest",1);//已收获
                }else{
                    map.put("isHarvest",0);//未收获
                }
            }


        }


        return  list;

    }



    @Override
    public List<Map<String,Object>> findPlantCustomInfo(Map<String, Object> params){

        List<Map<String,Object>> list  =  plantInfoMapper.findPlantCustomInfo(params);
        for(Map<String,Object> map : list){
            JSONArray json = JSONArray.parseArray(MapUtils.getString(map,"custom"));
            map.put("custom",json);
        }

        return  list;

    }


    @Override
    public void saveOrUpdatePlantCustomInfo(Map<String, Object> params){
        String uuid = UuidUtil.get32UUID();

        if(MapUtils.getString(params,"id").equals("")){
            params.put("id",uuid);
        }else{
            uuid = MapUtils.getString(params,"id");

        }

        String custom  = JSON.toJSONString(params.get("custom"));
        params.put("custom", custom);
        plantInfoMapper.saveOrUpdatePlantCustomInfo(params);

    }

    @Override
    public void deletePlantCustomInfo(Map<String, Object> params){
        plantInfoMapper.deletePlantCustomInfo(params);
    }

    @Override
    public List<Map<String,Object>> findAllTextPreviewList(Map<String,Object> params){

        List<Map<String,Object>> allTextPreviewList = new ArrayList<>();

        String account = MapUtils.getString(params, "account");
        //根据account查询得到该用户下所有的模块
        List<Map<String,Object>> list  =  plantInfoMapper.findPlantTitleInfo(params);

        for(Map<String,Object> modelInfo:list){

            //获取 模块id 模块名字

            Map<String,Object> previewListMap = new HashMap<>();


            previewListMap.put("plantParentId",MapUtils.getString(modelInfo,"id"));

            previewListMap.put("name",MapUtils.getString(modelInfo,"name"));


            params.put("plantParentId",MapUtils.getString(modelInfo,"id"));


            List<Map<String,Object>>  previewList= plantInfoMapper.findAllTextPreviewList(params);

            if(previewList.size()>0){
                previewListMap.put("id",MapUtils.getString(previewList.get(0),"previewId"));
                previewListMap.put("textPreview",previewList);
            }else{
                List<Map<String,Object>> previewLists = plantInfoMapper.findTextPreviewInfo(params);
                previewListMap.put("textPreview",previewLists);
            }

            allTextPreviewList.add(previewListMap);

        }



        return allTextPreviewList;
    }

    @Override
    public void saveOrUpdateAllTextPreview(Map<String, Object> params){

        plantInfoMapper.saveOrUpdateAllTextPreview(params);


    }



    private Object findBySpeciesName(String fname, Integer auditstatus) {

        Map<String, Object> map = new HashMap<>();
        map.put("fname", fname);
        if(!auditstatus.equals(0)){
            map.put("auditstatus", auditstatus);
        }
        return speciesClassService.findBySpeciesName(JSON.toJSON(map));

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
