package com.ovit.nswy.member.application.mapper;

import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import java.util.Map;

@Mapper
public interface ProductionBaseMapper {

    int save(Map<String, Object> params);

    void update(Map<String, Object> params);

    void delete(int id);

    Map<String,Object> find(Map<String, Object> params);

    List<Map<String,Object>> list(Map<String, Object> params);

    List<Map<String,Object>> facilitiesType();

    List<Map<String,Object>> findFacilities(Map<String, Object> params);

    String getFacilitiesType(int id);

    void batchSaveFacilities(List<Map<String,Object>> list);

    void deleteFacilities(Map<String, Object> params);

    void deleteContactInfo(Map<String, Object> params);

    void batchSaveContact(List<Map<String,Object>> list);

    List<Map<String,Object>> findContactInfo(Map<String, Object> params);

    //List<Map<String,Object>> findMemberLocation(Map<String,Object> params);

    void syncMemberLocation(Map<String,Object> params);

    //List<Map<String,Object>> findMemberLatitudeLongitude(Map<String,Object> params);

    void syncMemberLatitudeLongitude(Map<String,Object> params);

    //List<Map<String,Object>> findMemberNeighbor(Map<String,Object> params);

    void syncMemberNeighbor(Map<String,Object> params);

    //List<Map<String,Object>> findMemberLiveAddress(Map<String,Object> params);

    void syncMemberLiveAddress(Map<String,Object> params);

    //List<Map<String,Object>> findLandAreaInfo(Map<String,Object> params);

    void syncLandAreaInfo(Map<String,Object> params);

    //Map<String,Object> findTopographicFeature(Map<String,Object> params);

    void syncTopographicFeature(Map<String,Object> params);

    //Map<String,Object> findClimateInfo(Map<String,Object> params);

    void syncClimateInfo(Map<String,Object> params);

    //Map<String,Object> findBiologicalResources(Map<String,Object> params);

    void syncBiologicalResources(Map<String,Object> params);

    //List<Map<String,Object>> findMineralsInfo(Map<String,Object> params);

    void syncMineralsInfo(Map<String,Object> params);

    //List<Map<String,Object>> findLandInfo(Map<String, Object> params);

    void syncLandInfo(Map<String, Object> params);

    //List<Map<String,Object>> findSoilContent(Map<String, Object> params);

    void syncSoilContent(Map<String, Object> params);

    //List<Map<String,Object>> findSoilQuality(Map<String, Object> params);

    void syncSoilQuality(Map<String, Object> params);

    //List<Map<String,Object>> findWaterQuality(Map<String, Object> params);

    void syncWaterQuality(Map<String, Object> params);

    //List<Map<String,Object>> findLandUse(Map<String, Object> params);

    void syncLandUse(Map<String, Object> params);

    //Map<String,Object> findAirQuaByAccount(Map<String, Object> params);

    void syncAirQuaByAccount(Map<String, Object> params);

    //Map<String,Object> findEcologyEnvQua(Map<String, Object> params);

    void syncEcologyEnvQua(Map<String, Object> params);

    //Map<String,Object> findSurfaceWaterQua(Map<String, Object> params);

    void syncSurfaceWaterQua(Map<String, Object> params);

    //List<Map<String,Object>> findSecurityFacilitiesInfo(Map<String, Object> params);

    void syncSecurityFacilitiesInfo(Map<String, Object> params);

    //List<Map<String,Object>> findEducationFacilitiesInfo(Map<String, Object> params);

    void syncEducationFacilitiesInfo(Map<String, Object> params);

    //List<Map<String,Object>> findOldAgeWelfareFacilitiesInfo(Map<String, Object> params);

    void syncOldAgeWelfareFacilitiesInfo(Map<String, Object> params);

    //List<Map<String,Object>> findOtherFacilitiesInfo(Map<String, Object> params);

    void syncOtherFacilitiesInfo(Map<String, Object> params);

    //List<Map<String,Object>> findGarbageDisposaFacilitiesInfo(Map<String, Object> params);

    void syncGarbageDisposaFacilitiesInfo(Map<String, Object> params);

    //List<Map<String,Object>> findWaterConservancyFacilitiesInfo(Map<String, Object> params);

    void syncWaterConservancyFacilitiesInfo(Map<String, Object> params);

    //List<Map<String,Object>> findCultureFacilitiesInfo(Map<String, Object> params);

    void syncCultureFacilitiesInfo(Map<String, Object> params);

    //List<Map<String,Object>> findTapWaternFacilitiesInfo(Map<String, Object> params);

    void syncTapWaternFacilitiesInfo(Map<String, Object> params);

    //List<Map<String,Object>> findSportsFacilitiesInfo(Map<String, Object> params);

    void syncSportsFacilitiesInfo(Map<String, Object> params);

    //List<Map<String,Object>> findCommunicationFacilitiesInfo(Map<String, Object> params);

    void syncCommunicationFacilitiesInfo(Map<String, Object> params);

    Map<String,Object> findTextPreview(Map<String,Object> params);

    void syncTextPreview(Map<String, Object> params);

    void syncTrafficInfo(Map<String, Object> params);

    void syncIndustryInfo(Map<String, Object> params);

    void syncBreedInfo(Map<String, Object> params);

    void syncFarmProduct(Map<String, Object> params);

    void syncTechnicalProduct(Map<String, Object> params);

    void syncServiceProduct(Map<String, Object> params);

    void syncIotdeviceInfo(Map<String, Object> params);

    Map<String,Object> findAirQuaByAccount(Map<String, Object> params);

    List<Map<String,Object>> findModuleInfo(Map<String, Object> params);

    void insertAirQuaByAccount(Map<String, Object> params);

    void saveTextPreview(Map<String,Object> params);

    void modifyAirQuaByAccount(Map<String, Object> params);

    void updateTextPreview(Map<String,Object> params);

    Map<String,Object> findSurfaceWaterQua(Map<String, Object> params);

    void insertSurfaceWaterQua(Map<String, Object> params);

    void modifySurfaceWaterQua(Map<String, Object> params);

    Map<String,Object> findEcologyEnvQua(Map<String, Object> params);

    void insertEcologyEnvQua(Map<String, Object> params);

    void modifyEcologyEnvQua(Map<String, Object> params);

    List<Map<String,Object>> findLandInfo(Map<String, Object> params);

    void updateLandInfo(Map<String, Object> params);

    List<Map<String,Object>> findSoilContent(Map<String, Object> params);

    void updateSoilContent(Map<String, Object> params);

    List<Map<String,Object>> findSoilQuality(Map<String, Object> params);

    void updateSoilQuality(Map<String, Object> params);

    List<Map<String,Object>> findWaterQuality(Map<String, Object> params);

    void updateWaterQuality(Map<String, Object> params);

    List<Map<String,Object>> findLandUse(Map<String, Object> params);

    void saveLandUse(List<Map<String, Object>> params);

    void deleteLandUse(Map<String, Object> params);

    Map<String,Object> physicalTextPreview(Map<String,Object> params);

    List<Map<String,Object>> findMemberLocation(Map<String,Object> params);

    List<Map<String,Object>> findMemberLatitudeLongitude(Map<String,Object> params);

    List<Map<String,Object>> findMemberNeighbor(Map<String,Object> params);

    List<Map<String,Object>> findMemberLiveAddress(Map<String,Object> params);

    void saveMemberLocation(Map<String,Object> params);

    void saveMemberLatitudeLongitude(Map<String,Object> params);

    void saveMemberNeighbor(Map<String,Object> params);

    void saveMemberLiveAddress(Map<String,Object> params);

    void deleteMemberNeighbor(Map<String,Object> params);

    void deleteMemberLiveAddress(Map<String,Object> params);

    void saveBaseTextPreview(Map<String,Object> params);

    List<Map<String,Object>> findLandAreaInfo(Map<String,Object> params);

    void saveLandAreaInfo(Map<String,Object> params);

    void deleteLandAreaInfo(Map<String,Object> params);

    Map<String,Object> findTopographicFeature(Map<String,Object> params);

    void saveTopographicFeature(Map<String,Object> params);

    Map<String,Object> findClimateInfo(Map<String,Object> params);

    void saveClimateInfo(Map<String,Object> params);

    Map<String,Object> findBiologicalResources(Map<String,Object> params);

    void saveBiologicalResources(Map<String,Object> params);

    void deleteBiologicalResources(Map<String,Object> params);

    List<Map<String,Object>> findMineralsInfo(Map<String,Object> params);

    void saveMineralsInfo(Map<String,Object> params);

    Map<String,Object> communalTextPreview(Map<String, Object> params);

    Map<String,Object> findPropertyInfo(Map<String, Object> params);

    List<Map<String,Object>> findCommunicationFacilitiesInfo(Map<String, Object> params);

    void saveCommunicationFacilitiesInfo(Map<String, Object> params);

    void deleteCommunicationFacilitiesInfo(Map<String, Object> params);

    List<Map<String,Object>> findTapWaternFacilitiesInfo(Map<String, Object> params);

    void saveTapWaternFacilitiesInfo(Map<String, Object> params);

    void deleteTapWaternFacilitiesInfo(Map<String, Object> params);

    List<Map<String,Object>> findWaterConservancyFacilitiesInfo(Map<String, Object> params);

    void saveWaterConservancyFacilitiesInfo(Map<String, Object> params);

    void deleteWaterConservancyFacilitiesInfo(Map<String, Object> params);

    List<Map<String,Object>> findGarbageDisposaFacilitiesInfo(Map<String, Object> params);

    void saveGarbageDisposaFacilitiesInfo(Map<String, Object> params);

    void deleteGarbageDisposaFacilitiesInfo(Map<String, Object> params);

    List<Map<String,Object>> findSecurityFacilitiesInfo(Map<String, Object> params);

    void saveSecurityFacilitiesInfo(Map<String, Object> params);

    void deleteSecurityFacilitiesInfo(Map<String, Object> params);

    List<Map<String,Object>> findEducationFacilitiesInfo(Map<String, Object> params);

    void saveEducationFacilitiesInfo(Map<String, Object> params);

    void deleteEducationFacilitiesInfo(Map<String, Object> params);

    List<Map<String,Object>> findCultureFacilitiesInfo(Map<String, Object> params);

    void saveCultureFacilitiesInfo(Map<String, Object> params);

    void deleteCultureFacilitiesInfo(Map<String, Object> params);

    List<Map<String,Object>> findSportsFacilitiesInfo(Map<String, Object> params);

    void saveSportsFacilitiesInfo(Map<String, Object> params);

    void deleteSportsFacilitiesInfo(Map<String, Object> params);

    List<Map<String,Object>> findOldAgeWelfareFacilitiesInfo(Map<String, Object> params);

    void saveOldAgeWelfareFacilitiesInfo(Map<String, Object> params);

    void deleteOldAgeWelfareFacilitiesInfo(Map<String, Object> params);

    List<Map<String,Object>> findOtherFacilitiesInfo(Map<String, Object> params);

    void saveOtherFacilitiesInfo(Map<String, Object> params);

    void deleteOtherFacilitiesInfo(Map<String, Object> params);

    void saveIotDeviceInfo(Map<String, Object> params);

    void updateIotDeviceInfo(Map<String, Object> params);

    List<Map<String,Object>> findIotDeviceInfo(Map<String, Object> params);

    void deleteIotDeviceInfo(Map<String, Object> params);

    Map<String,Object> findAllTextPreviewList(Map<String, Object> params);

    void saveAllTextPreview(Map<String,Object> params);

    List<Map<String,Object>> findTrafficOfUserInfo(Map<String, Object> params);

    void saveTrafficOfUserInfo(Map<String, Object> params);

    void deleteTrafficOfUserInfo(Map<String, Object> params);

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

    List<Map<String,Object>> contactList(Map<String, Object> params);

    List<Map<String,Object>> iotList(Map<String, Object> params);

    List<Map<String,Object>> findBaseIotDeviceInfo(Map<String, Object> params);

    void saveBaseIotDeviceInfo(List<Map<String, Object>> list);

    void deleteIotMapping(Map<String,Object> params);

    void deleteIotDevice(Map<String,Object> params);

    void deleteIotMappingByIotId(Map<String,Object> params);

    List<Map<String,Object>> photoList(Map<String, Object> params);

    void deleteBasePhoto(Map<String, Object> params);

    void batchSavePhoto(List<Map<String,Object>> list);

    Map<String,Object> findNickName(Map<String, Object> params);

    void editComplete(Map<String, Object> params);

}
