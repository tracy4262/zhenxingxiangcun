package com.ovit.nswy.member.perfectInfo.service;

import java.util.List;
import java.util.Map;

public interface PhysicalGeographyService {


    Map<String,Object> findGeographicalPosition(Map<String,Object> params);

    void saveGeographicalPosition(Map<String,Object> params);

    void updateMemberLocationName(Map<String,Object> params);


    void updateMemberLatitudeLongitudeName(Map<String,Object> params);


    void updateMemberNeighborName(Map<String,Object> params);


    void updateMemberLiveAddressName(Map<String,Object> params);

    Map<String,Object> findLandAreaInfo(Map<String,Object> params);

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

    Map<String,Object> findMineralsInfo(Map<String,Object> params);

    void updateMineralsInfoName(Map<String,Object> params);

    void saveMineralsInfo(Map<String,Object> params);

    void deleteMemberNeighbor(Map<String,Object> params);

    void deleteMemberLiveAddress(Map<String,Object> params);

    void deleteLandAreaInfo(Map<String,Object> params);

    void deleteBiologicalResources(Map<String,Object> params);

    List<Map<String,Object>> findSysMineralsDictParent(Map<String,Object> params);

    List<Map<String,Object>> findSysMineralsDict(Map<String,Object> params);



}
