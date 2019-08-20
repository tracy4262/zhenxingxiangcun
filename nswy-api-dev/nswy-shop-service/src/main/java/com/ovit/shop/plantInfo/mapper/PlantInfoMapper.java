package com.ovit.shop.plantInfo.mapper;

import java.util.List;
import java.util.Map;

public interface PlantInfoMapper {
    List<Map<String,Object>> findPlantYearInfo(Map<String, Object> params);

    void savePlantYearInfo(Map<String, Object> params);

    void deletePlantYearInfo(Map<String, Object> params);

    List<Map<String,Object>> findPlantSpeciesInfo(Map<String, Object> params);

    void savePlantSpeciesInfo(Map<String, Object> params);

    void deletePlantSpeciesInfo(Map<String, Object> params);

    List<Map<String,Object>> findPlantProductionInfo(Map<String, Object> params);


    List<Map<String,Object>> findProductionBase(Map<String, Object> params);

    Map<String,Object> findMaxId(Map<String, Object> params);

    void saveOrUpdatePlantProduction(Map<String, Object> params);

    void deletePlantProductionInfo(Map<String, Object> params);

    List<Map<String,Object>> findPlantOutputInfo(Map<String, Object> params);

    void saveOrUpdatePlantOutputInfo(Map<String, Object> params);

    void deletePlantOutputInfo(Map<String, Object> params);

    List<Map<String,Object>> findPlantTitleInfo(Map<String, Object> params);


    List<Map<String,Object>> findPlantSowInfo(Map<String, Object> params);

    void saveOrUpdatePlantSowInfo(Map<String, Object> params);

    void deletePlantSowInfo(Map<String, Object> params);

    List<Map<String,Object>> findTextPreviewInfo(Map<String, Object> params);

    void saveOrUpdateTextPreviewInfo(Map<String, Object> params);

    List<Map<String,Object>> findAllTextPreviewList(Map<String, Object> params);

    void saveOrUpdateAllTextPreview(Map<String, Object> params);

    List<Map<String,Object>> findPlantFertilizeInfo(Map<String, Object> params);

    void saveOrUpdatePlantFertilizeInfo(Map<String, Object> params);

    void deletePlantFertilizeInfo(Map<String, Object> params);

    List<Map<String,Object>> findPlantMedicineInfo(Map<String, Object> params);

    void saveOrUpdatePlantMedicineInfo(Map<String, Object> params);

    void deletePlantMedicineInfo(Map<String, Object> params);

    List<Map<String,Object>> findPlantResearchInfo(Map<String, Object> params);

    void saveOrUpdatePlantResearchInfo(Map<String, Object> params);

    void deletePlantResearchInfo(Map<String, Object> params);

    List<Map<String,Object>> findPlantHarvestInfo(Map<String, Object> params);

    void saveOrUpdatePlantHarvestInfo(Map<String, Object> params);

    void deletePlantHarvestInfo(Map<String, Object> params);


    List<Map<String,Object>> findPlantCustomInfo(Map<String, Object> params);

    void saveOrUpdatePlantCustomInfo(Map<String, Object> params);

    void deletePlantCustomInfo(Map<String, Object> params);

}
