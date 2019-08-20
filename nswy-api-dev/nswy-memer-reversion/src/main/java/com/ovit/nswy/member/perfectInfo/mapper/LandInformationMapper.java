package com.ovit.nswy.member.perfectInfo.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface LandInformationMapper {

    List<Map<String,Object>> findLandInfo(Map<String, Object> params);

    List<Map<String,Object>> findSoilContent(Map<String, Object> params);

    List<Map<String,Object>> findSoilQuality(Map<String, Object> params);

    List<Map<String,Object>> findWaterQuality(Map<String, Object> params);

    void addNkyLandInfo(Map<String, Object> params);

    void addNkySoilInfo(Map<String, Object> params);

    void addNkySoilQuality(Map<String, Object> params);

    void addNkyWaterQuality(Map<String, Object> params);

    void updateLandInfo(Map<String, Object> params);

    void updateSoilContent(Map<String, Object> params);

    void updateSoilQuality(Map<String, Object> params);

    void updateWaterQuality(Map<String, Object> params);

    void deleteLandInfo(Map<String, Object> params);

    void deleteSoilContent(Map<String, Object> params);

    void deleteSoilQuality(Map<String, Object> params);

    void deleteWaterQuality(Map<String, Object> params);

    List<Map<String,Object>> findLandUse(Map<String, Object> params);

    void saveLandUse(List<Map<String, Object>> params);

    void deleteLandUse(Map<String, Object> params);

    Map<String,Object> findLandCode(Map<String, Object> params);

    void addLandCode(Map<String, Object> params);

    void updateLandCode(Map<String, Object> params);

    List<Map<String,Object>> findAddressInfo(Map<String, Object> params);

    String findAdminCode(String id);

}
