package com.ovit.nswy.member.perfectInfo.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface CultureSightMapper {

    List<Map<String,Object>> findSceneryByAccount(Map<String, Object> params);

    void updateSceneryName(Map<String,Object> params);

    void saveSceneryName(Map<String,Object> params);

    List<Map<String,Object>> findCulturalHeritage(Map<String, Object> params);

    void updateCulturalName(Map<String,Object> params);

    void saveCulturalName(Map<String,Object> params);

    void deleteScenery(Map<String,Object> params);

    void deleteCultural(Map<String,Object> params);

    Map<String,Object> findPropertyInfo(Map<String, Object> params);

    void insertPropertyInfo(Map<String,Object> params);

    void updatePropertyInfo(Map<String,Object> params);


}
