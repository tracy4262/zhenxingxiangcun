package com.ovit.nswy.member.perfectInfo.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface EcoSocialDevelopMapper {

    List<Map<String,Object>> findIndustry(Map<String, Object> params);

    void saveIndustry(List<Map<String, Object>> params);

    void deleteIndustry(Map<String, Object> params);

    List<Map<String,Object>> findBreed(Map<String, Object> params);

    void saveBreed(Map<String, Object> params);

    void deleteBreed(Map<String, Object> params);

    List<Map<String,Object>> findFarmProduct(Map<String, Object> params);

    void saveFarmProduct(Map<String, Object> params);

    void deleteFarmProduct(Map<String, Object> params);

    List<Map<String,Object>> findTechnicalProduct(Map<String, Object> params);

    void saveTechnicalProduct(Map<String, Object> params);

    void deleteTechnicalProduct(Map<String, Object> params);

    List<Map<String,Object>> findServiceProduct(Map<String, Object> params);

    void saveServiceProduct(Map<String, Object> params);

    void deleteServiceProduct(Map<String, Object> params);

    List<Map<String,Object>> findByParentId(String parentId);

    List<Map<String,Object>> findEcoBasicData(Map<String, Object> params);
    //List<Map<String,Object>> findEcoBasicData(String id);

}
