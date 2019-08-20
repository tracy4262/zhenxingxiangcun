package com.ovit.shop.plantInfo.service;

import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

public interface PlantInfoService {

    List<Map<String,Object>> findPlantYearInfo(Map<String, Object> params);

    void savePlantYearInfo(Map<String, Object> params);

    void deletePlantYearInfo(Map<String, Object> params);

    PageInfo<Map<String,Object>> findPlantSpeciesInfo(Map<String, Object> params);

    void savePlantSpeciesInfo(Map<String, Object> params);

    void deletePlantSpeciesInfo(Map<String, Object> params);

    PageInfo<Map<String,Object>> findPlantProductionInfo(Map<String, Object> params);

    List<Map<String,Object>> findPlantProductionNumber(Map<String, Object> params);


    List<Map<String,Object>> findProductionBase(Map<String, Object> params);

    void saveOrUpdatePlantProduction(Map<String, Object> params);

    void deletePlantProductionInfo(Map<String, Object> params);

    PageInfo<Map<String,Object>> findPlantOutputInfo(Map<String, Object> params);

    void saveOrUpdatePlantOutputInfo(Map<String, Object> params);

    void deletePlantOutputInfo(Map<String, Object> params);

    List<Map<String,Object>> findProductionBaseOfOutput(Map<String, Object> params);

    List<Map<String,Object>> findPlantTitleInfo(Map<String, Object> params);

    PageInfo<Map<String,Object>> findPlantProductionPlanInfo(Map<String, Object> params);

    void saveOrUpdateProductionPlanInfo(Map<String, Object> params);

    void deletePlantProductionPlanInfo(Map<String, Object> params);

    List<Map<String,Object>> findPlantCustomInfo(Map<String, Object> params);

    void saveOrUpdatePlantCustomInfo(Map<String, Object> params);

    void deletePlantCustomInfo(Map<String, Object> params);

    List<Map<String,Object>> findAllTextPreviewList(Map<String,Object> params);

    void saveOrUpdateAllTextPreview(Map<String, Object> params);

    List<Map<String,Object>> findHarvestProductInfo(Map<String, Object> params);

}
