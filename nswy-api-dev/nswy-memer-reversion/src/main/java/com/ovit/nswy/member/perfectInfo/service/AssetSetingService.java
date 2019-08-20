package com.ovit.nswy.member.perfectInfo.service;

import java.util.List;
import java.util.Map;

public interface AssetSetingService {

    Map<String,Object> findLandUseRightInfo(Map<String, Object> params);

    void updateLandUseRightInfoName(Map<String, Object> params);

    void saveLandUseRightInfo(Map<String, Object> params);

    void deleteLandUseRightInfo(Map<String, Object> params);

    void saveTextPreview(Map<String, Object> params);


    Map<String,Object> findRightToUseHousingInfo(Map<String, Object> params);

    void updateRightToUseHousingInfoName(Map<String, Object> params);

    void saveRightToUseHousingInfo(Map<String, Object> params);

    void deleteRightToUseHousingInfo(Map<String, Object> params);




    Map<String,Object> findProductionMachineInfo(Map<String, Object> params);

    void updateProductionMachineInfoName(Map<String, Object> params);

    void saveProductionMachineInfo(Map<String, Object> params);

    void deleteProductionMachineInfo(Map<String, Object> params);



    Map<String,Object> findTransportationToolsInfo(Map<String, Object> params);

    void updateTransportationToolsInfoName(Map<String, Object> params);

    void saveTransportationToolsInfo(Map<String, Object> params);

    void deleteTransportationToolsInfo(Map<String, Object> params);


    Map<String,Object> findHouseholdAssetsInfo(Map<String, Object> params);

    void updateHouseholdAssetsInfoName(Map<String, Object> params);

    void saveHouseholdAssetsInfo(Map<String, Object> params);

    void deleteHouseholdAssetsInfo(Map<String, Object> params);


    Map<String,Object>  findFacilityAgricultureInfo(Map<String, Object> params);

    void updateFacilityAgricultureInfoName(Map<String, Object> params);

    void saveFacilityAgricultureInfo(Map<String, Object> params);

    void deleteFacilityAgricultureInfo(Map<String, Object> params);




}
