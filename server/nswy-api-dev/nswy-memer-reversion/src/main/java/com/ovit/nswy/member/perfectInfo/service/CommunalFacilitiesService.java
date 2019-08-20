package com.ovit.nswy.member.perfectInfo.service;

import java.util.Map;

public interface CommunalFacilitiesService {



    Map<String,Object> findTrafficOfUserInfo(Map<String, Object> params);

    void updateTrafficOfUserInfoName(Map<String, Object> params);

    void saveTrafficOfUserInfo(Map<String, Object> params);

    void deleteTrafficOfUserInfo(Map<String, Object> params);

    void updateTrafficInfoName(Map<String, Object> params);

    void saveTrafficInfo(Map<String, Object> params);

    void saveTextPreview(Map<String, Object> params);

    void saveTextPreviewTwo(Map<String, Object> params);

    void deleteTrafficInfo(Map<String, Object> params);


    Map<String,Object> findCommunicationFacilitiesInfo(Map<String, Object> params);

    void updateCommunicationFacilitiesInfoName(Map<String, Object> params);

    void saveCommunicationFacilitiesInfo(Map<String, Object> params);

    void deleteCommunicationFacilitiesInfo(Map<String, Object> params);


    Map<String,Object> findTapWaternFacilitiesInfo(Map<String, Object> params);

    void updateTapWaternFacilitiesInfoName(Map<String, Object> params);

    void saveTapWaternFacilitiesInfo(Map<String, Object> params);

    void deleteTapWaternFacilitiesInfo(Map<String, Object> params);


    Map<String,Object> findWaterConservancyFacilitiesInfo(Map<String, Object> params);

    void updateWaterConservancyFacilitiesInfoName(Map<String, Object> params);

    void saveWaterConservancyFacilitiesInfo(Map<String, Object> params);

    void deleteWaterConservancyFacilitiesInfo(Map<String, Object> params);



    Map<String,Object> findGarbageDisposaFacilitiesInfo(Map<String, Object> params);

    void updateGarbageDisposaFacilitiesInfoName(Map<String, Object> params);

    void saveGarbageDisposaFacilitiesInfo(Map<String, Object> params);

    void deleteGarbageDisposaFacilitiesInfo(Map<String, Object> params);


    Map<String,Object> findSecurityFacilitiesInfo(Map<String, Object> params);

    void updateSecurityFacilitiesInfoName(Map<String, Object> params);

    void saveSecurityFacilitiesInfo(Map<String, Object> params);

    void deleteSecurityFacilitiesInfo(Map<String, Object> params);


    Map<String,Object> findEducationFacilitiesInfo(Map<String, Object> params);

    void updateEducationFacilitiesInfoName(Map<String, Object> params);

    void saveEducationFacilitiesInfo(Map<String, Object> params);

    void deleteEducationFacilitiesInfo(Map<String, Object> params);



    Map<String,Object> findCultureFacilitiesInfo(Map<String, Object> params);

    void updateCultureFacilitiesInfoName(Map<String, Object> params);

    void saveCultureFacilitiesInfo(Map<String, Object> params);

    void deleteCultureFacilitiesInfo(Map<String, Object> params);


    Map<String,Object> findSportsFacilitiesInfo(Map<String, Object> params);

    void updateSportsFacilitiesInfoName(Map<String, Object> params);

    void saveSportsFacilitiesInfo(Map<String, Object> params);

    void deleteSportsFacilitiesInfo(Map<String, Object> params);


    Map<String,Object> findOldAgeWelfareFacilitiesInfo(Map<String, Object> params);

    void updateOldAgeWelfareFacilitiesInfoName(Map<String, Object> params);

    void saveOldAgeWelfareFacilitiesInfo(Map<String, Object> params);

    void deleteOldAgeWelfareFacilitiesInfo(Map<String, Object> params);




    Map<String,Object> findOtherFacilitiesInfo(Map<String, Object> params);

    void updateOtherFacilitiesInfoName(Map<String, Object> params);

    void saveOtherFacilitiesInfo(Map<String, Object> params);

    void deleteOtherFacilitiesInfo(Map<String, Object> params);

    void saveIotDeviceInfo(Map<String, Object> params);

    Map<String,Object> findIotDeviceInfo(Map<String, Object> params);

    void deleteIotDeviceInfo(Map<String, Object> params);

}
