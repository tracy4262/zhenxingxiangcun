package com.ovit.nswy.member.perfectInfo.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface AssetSettingMapper {

  List<Map<String,Object>> findLandUseRightInfo(Map<String, Object> params);

  void updateLandUseRightInfoName(Map<String, Object> params);

  void saveLandUseRightInfo(Map<String, Object> params);

  void deleteLandUseRightInfo(Map<String, Object> params);



  List<Map<String,Object>> findRightToUseHousingInfo(Map<String, Object> params);

  void updateRightToUseHousingInfoName(Map<String, Object> params);

  void saveRightToUseHousingInfo(Map<String, Object> params);

  void deleteRightToUseHousingInfo(Map<String, Object> params);


  List<Map<String,Object>> findProductionMachineInfo(Map<String, Object> params);

  void updateProductionMachineInfoName(Map<String, Object> params);

  void saveProductionMachineInfo(Map<String, Object> params);

  void deleteProductionMachineInfo(Map<String, Object> params);



  List<Map<String,Object>> findTransportationToolsInfo(Map<String, Object> params);

  void updateTransportationToolsInfoName(Map<String, Object> params);

  void saveTransportationToolsInfo(Map<String, Object> params);

  void deleteTransportationToolsInfo(Map<String, Object> params);


  List<Map<String,Object>> findHouseholdAssetsInfo(Map<String, Object> params);

  void updateHouseholdAssetsInfoName(Map<String, Object> params);

  void saveHouseholdAssetsInfo(Map<String, Object> params);

  void deleteHouseholdAssetsInfo(Map<String, Object> params);

  List<Map<String,Object>> findFacilityAgricultureInfo(Map<String, Object> params);

  void updateFacilityAgricultureInfoName(Map<String, Object> params);

  void saveFacilityAgricultureInfo(Map<String, Object> params);

  void deleteFacilityAgricultureInfo(Map<String, Object> params);


}
