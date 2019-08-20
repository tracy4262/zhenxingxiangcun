package com.ovit.nswy.member.perfectInfo.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface PhysicalGeographyMapper {

    List<Map<String,Object>> findPhysicalGeography(Map<String,Object> params);

    List<Map<String,Object>> findPhysicalGeographyModular(Map<String,Object> params);

    List<Map<String,Object>> findMemberLocation(Map<String,Object> params);

    void updateMemberLocationName(Map<String,Object> params);

    void saveMemberLocation(Map<String,Object> params);

    List<Map<String,Object>> findMemberLatitudeLongitude(Map<String,Object> params);

    void updateMemberLatitudeLongitudeName(Map<String,Object> params);

    void saveMemberLatitudeLongitude(Map<String,Object> params);

    List<Map<String,Object>> findMemberNeighbor(Map<String,Object> params);

    void updateMemberNeighborName(Map<String,Object> params);

    void saveMemberNeighbor(Map<String,Object> params);

    List<Map<String,Object>> findMemberLiveAddress(Map<String,Object> params);

    void updateMemberLiveAddressName(Map<String,Object> params);

    void saveMemberLiveAddress(Map<String,Object> params);

    Map<String,Object> findTextPreview(Map<String,Object> params);

    void saveTextPreview(Map<String,Object> params);

    List<Map<String,Object>> findLandAreaInfo(Map<String,Object> params);

    void updateLandAreaInfoName(Map<String,Object> params);

    void saveLandAreaInfo(Map<String,Object> params);

    Map<String,Object> findTopographicFeature(Map<String,Object> params);

    void updateTopographicFeatureName(Map<String,Object> params);

    void saveTopographicFeature(Map<String,Object> params);

    Map<String,Object> findClimateInfo(Map<String,Object> params);

    void updateClimateInfoName(Map<String,Object> params);

    void saveClimateInfo(Map<String,Object> params);

    Map<String,Object> findBiologicalResources(Map<String,Object> params);

    void updateBiologicalResourcesName(Map<String,Object> params);

    void saveBiologicalResources(Map<String,Object> params);

    List<Map<String,Object>> findMineralsInfo(Map<String,Object> params);

    void updateMineralsInfoName(Map<String,Object> params);

    void saveMineralsInfo(Map<String,Object> params);

    void deleteMemberNeighbor(Map<String,Object> params);

    void deleteMemberLiveAddress(Map<String,Object> params);

    void deleteLandAreaInfo(Map<String,Object> params);

    void deleteBiologicalResources(Map<String,Object> params);


    List<Map<String,Object>> findSysMineralsDictParent(Map<String,Object> params);

    List<Map<String,Object>> findSysMineralsDict(Map<String,Object> params);




}
