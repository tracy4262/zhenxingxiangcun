package com.ovit.nswy.member.application.service;

import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

public interface ProductionBaseService {

    /**
     * 查询地块信息
     * @param params
     * @return
     */
    List<Map<String,Object>> landInfo(Map<String, Object> params);

    /**
     * 新增或编辑基地
     * @param params
     */
    void saveOrUpdate(Map<String, Object> params);

    /**
     * 删除基地
     * @param id
     */
    void delete(int id);

    /**
     * 生产基地列表
     * @param params
     * @return
     */
    PageInfo<Map<String, Object>> list(Map<String, Object> params);

    /**
     * 物联设施类别字典
     * @return
     */
    List<Map<String,Object>> facilitiesType();

    /**
     * 生产基地-查询基础信息
     * @param params
     * @return
     */
    Map<String,Object> findBaseInfo(Map<String, Object> params);

    /**
     * 生产基地-保存基本信息
     * @param params
     */
    void saveBaseInfo(Map<String, Object> params);

    /**
     * 查询生产基地详细信息的模块列表
     * @return
     */
    List<Map<String,Object>> findModuleInfo(Map<String,Object> params);

    /**
     * 查询用户某个大模块下的子模块
     * 通过app_id查询reversion_sys_dict得到
     * @param params
     * @return
     */
    List<Map<String,Object>> findSubModule(Map<String, Object> params);

    /**
     * 通过用户名查询用户的空气质量信息
     * @param params
     * @return
     */
    Map<String,Object> findAirQuaByAccount(Map<String, Object> params);

    /**
     * 通过account查到yearId和templateId
     * @param params
     * @return
     */
    Map<String,Object> getYearIdAndTemplateId(Map<String, Object> params);

    Map<String, Object> findAirQuaByAccountList(Map<String, Object> params);

    /**
     * 新增用户的空气质量信息
     * @param params
     * @return
     */
    void insertAirQuaByAccount(Map<String, Object> params);

    /**
     * 修改用户的空气质量信息
     * @param params
     * @return
     */
    void modifyAirQuaByAccount(Map<String, Object> params);

    /**
     * 通过用户名查询用户的地表水质量信息
     * @param params
     * @return
     */
    Map<String,Object> findSurfaceWaterQua(Map<String, Object> params);

    Map<String,Object> findSurfaceWaterQuaList(Map<String, Object> params);

    /**
     * 新增用户的地表水质量信息
     * @param params
     * @return
     */
    void insertSurfaceWaterQua(Map<String, Object> params);

    /**
     * 修改用户的地表水质量信息
     * @param params
     * @return
     */
    void modifySurfaceWaterQua(Map<String, Object> params);

    /**
     * 通过用户名查询用户的生态环境质量信息
     * @param params
     * @return
     */
    Map<String,Object> findEcologyEnvQua(Map<String, Object> params);

    Map<String, Object> findEcologyEnvQuaList(Map<String, Object> params);

    /**
     * 新增用户的生态环境质量信息
     * @param params
     * @return
     */
    void insertEcologyEnvQua(Map<String, Object> params);

    /**
     * 修改用户的生态环境质量信息
     * @param params
     * @return
     */
    void modifyEcologyEnvQua(Map<String, Object> params);

    /**
     * 通过用户名、基地ID查询地块信息
     * @param params
     * @return
     */
    Map<String,Object> findLandInfo(Map<String, Object> params);

    /**
     * 修改用户地块信息
     * @param params
     */
    void updateLandInfo(Map<String, Object> params);

    /**
     * 通过用户名查询用户的地块土壤含量信息
     * @param params
     * @return
     */
    Map<String,Object> findSoilContent(Map<String, Object> params);

    /**
     * 修改用户地块土壤含量信息
     * @param params
     */
    void updateSoilContent(Map<String, Object> params);

    /**
     * 通过用户名查询用户的地块土壤质量信息
     * @param params
     * @return
     */
    Map<String,Object> findSoilQuality(Map<String, Object> params);

    /**
     * 修改用户地块土壤质量信息
     * @param params
     */
    void updateSoilQuality(Map<String, Object> params);

    /**
     * 通过用户名查询用户的地块水质信息
     * @param params
     * @return
     */
    Map<String,Object> findWaterQuality(Map<String, Object> params);

    /**
     * 修改用户地块水质信息
     * @param params
     */
    void updateWaterQuality(Map<String, Object> params);

    /**
     * 通过用户名查询用户的土地利用现状信息
     * @param params
     * @return
     */
    Map<String,Object> findLandUse(Map<String, Object> params);

    /**
     * 修改用户的土地利用现状信息
     * @param params
     * @return
     */
    void saveLandUse(Map<String, Object> params);

    /**
     * 删除土地利用现状信息
     * @param params
     */
    void deleteLandUse(Map<String, Object> params);

    Map<String,Object> getLandType(Map<String, Object> params);

    /**
     * 保存文字预览信息
     * @param params
     */
    void saveTextPreview(Map<String, Object> params);

    Map<String,Object> findGeographicalPosition(Map<String,Object> params);

    void saveGeographicalPosition(Map<String,Object> params);

    void deleteMemberNeighbor(Map<String,Object> params);

    void deleteMemberLiveAddress(Map<String,Object> params);

    Map<String,Object> findLandAreaInfo(Map<String,Object> params);

    void saveLandAreaInfo(Map<String,Object> params);

    void deleteLandAreaInfo(Map<String,Object> params);

    Map<String,Object> findTopographicFeature(Map<String,Object> params);

    void saveTopographicFeature(Map<String,Object> params);

    Map<String,Object> findClimateInfo(Map<String,Object> params);

    void saveClimateInfo(Map<String,Object> params);

    Map<String,Object> findBiologicalResources(Map<String,Object> params);

    void saveBiologicalResources(Map<String,Object> params);

    void deleteBiologicalResources(Map<String,Object> params);

    Map<String,Object> findMineralsInfo(Map<String,Object> params);

    void saveMineralsInfo(Map<String,Object> params);

    Map<String,Object> findCommunicationFacilitiesInfo(Map<String, Object> params);

    void saveCommunicationFacilitiesInfo(Map<String, Object> params);

    void deleteCommunicationFacilitiesInfo(Map<String, Object> params);

    Map<String,Object> findTapWaternFacilitiesInfo(Map<String, Object> params);

    void saveTapWaternFacilitiesInfo(Map<String, Object> params);

    void deleteTapWaternFacilitiesInfo(Map<String, Object> params);

    Map<String,Object> findWaterConservancyFacilitiesInfo(Map<String, Object> params);

    void saveWaterConservancyFacilitiesInfo(Map<String, Object> params);

    void deleteWaterConservancyFacilitiesInfo(Map<String, Object> params);

    Map<String,Object> findGarbageDisposaFacilitiesInfo(Map<String, Object> params);

    void saveGarbageDisposaFacilitiesInfo(Map<String, Object> params);

    void deleteGarbageDisposaFacilitiesInfo(Map<String, Object> params);

    Map<String,Object> findSecurityFacilitiesInfo(Map<String, Object> params);

    void saveSecurityFacilitiesInfo(Map<String, Object> params);

    void deleteSecurityFacilitiesInfo(Map<String, Object> params);

    Map<String,Object> findEducationFacilitiesInfo(Map<String, Object> params);

    void saveEducationFacilitiesInfo(Map<String, Object> params);

    void deleteEducationFacilitiesInfo(Map<String, Object> params);

    Map<String,Object> findCultureFacilitiesInfo(Map<String, Object> params);

    void saveCultureFacilitiesInfo(Map<String, Object> params);

    void deleteCultureFacilitiesInfo(Map<String, Object> params);

    Map<String,Object> findSportsFacilitiesInfo(Map<String, Object> params);

    void saveSportsFacilitiesInfo(Map<String, Object> params);

    void deleteSportsFacilitiesInfo(Map<String, Object> params);

    Map<String,Object> findOldAgeWelfareFacilitiesInfo(Map<String, Object> params);

    void saveOldAgeWelfareFacilitiesInfo(Map<String, Object> params);

    void deleteOldAgeWelfareFacilitiesInfo(Map<String, Object> params);

    Map<String,Object> findOtherFacilitiesInfo(Map<String, Object> params);

    void saveOtherFacilitiesInfo(Map<String, Object> params);

    void deleteOtherFacilitiesInfo(Map<String, Object> params);

    void saveIotDeviceInfo(Map<String, Object> params);

    Map<String,Object> findIotDeviceInfo(Map<String, Object> params);

    void deleteIotDeviceInfo(Map<String, Object> params);

    List<Map<String,Object>> findAllTextPreviewList(Map<String,Object> params);

    void saveAllTextPreview(Map<String,Object> params);

    Map<String,Object> findTrafficOfUserInfo(Map<String, Object> params);

    void saveTrafficOfUserInfo(Map<String, Object> params);

    void deleteTrafficOfUserInfo(Map<String, Object> params);

    Map<String,Object> baseIntroduction(Map<String, Object> params);

    /**
     * 通过用户名查询用户的产业信息
     * @param params
     * @return
     */
    Map<String,Object> findIndustry(Map<String, Object> params);

    /**
     * 修改用户的产业信息
     * @param params
     * @return
     */
    void saveIndustry(Map<String, Object> params);

    /**
     * 删除产业信息
     * @param params
     */
    void deleteIndustry(Map<String, Object> params);

    /**
     * 通过用户名查询用户的种养信息
     * @param params
     * @return
     */
    Map<String,Object> findBreed(Map<String, Object> params);

    /**
     * 修改用户的种养信息
     * @param params
     * @return
     */
    void saveBreed(Map<String, Object> params);

    /**
     * 删除种养信息
     * @param params
     */
    void deleteBreed(Map<String, Object> params);

    /**
     * 通过用户名查询用户的农产品信息
     * @param params
     * @return
     */
    Map<String,Object> findFarmProduct(Map<String, Object> params);

    /**
     * 修改用户的农产品信息
     * @param params
     * @return
     */
    void saveFarmProduct(Map<String, Object> params);

    /**
     * 删除农产品信息
     * @param params
     */
    void deleteFarmProduct(Map<String, Object> params);

    /**
     * 通过用户名查询用户的工业产品信息
     * @param params
     * @return
     */
    Map<String,Object> findTechnicalProduct(Map<String, Object> params);

    /**
     * 修改用户的工业产品信息
     * @param params
     * @return
     */
    void saveTechnicalProduct(Map<String, Object> params);

    /**
     * 删除工业产品信息
     * @param params
     */
    void deleteTechnicalProduct(Map<String, Object> params);

    /**
     * 通过用户名查询用户的服务产品信息
     * @param params
     * @return
     */
    Map<String,Object> findServiceProduct(Map<String, Object> params);

    /**
     * 修改用户的服务产品信息
     * @param params
     * @return
     */
    void saveServiceProduct(Map<String, Object> params);

    /**
     * 删除服务产品信息
     * @param params
     */
    void deleteServiceProduct(Map<String, Object> params);

    /**
     * 拉取会员实名认证中的联系方式
     * @param params
     * @return
     */
    List<Map<String,Object>> contactList(Map<String,Object> params);

    /**
     * 物联设施列表：从生产基地详细信息里公共设施里的物联设施信息中拉取
     * @param params
     * @return
     */
    List<Map<String,Object>> iotList(Map<String,Object> params);

    /**
     * 查询生产基地第二步物联设施信息
     * @param params
     * @return
     */
    List<Map<String,Object>> findBaseIotDeviceInfo(Map<String,Object> params);


    /**
     * 生产基地第二步物联设施信息保存
     * @param params
     * @return
     */
    void saveBaseIotDeviceInfo(Map<String,Object> params);

    /**
     * 删除第二步物联设施信息
     * @param params
     */
    void deleteBaseIotDeviceInfo(Map<String, Object> params);

    /**
     * 撤销第二步物联设施信息
     * @param params
     * @return
     */
    void revokeBaseIotDeviceInfo(Map<String, Object> params);

    /**
     * 生产基地相册列表
     * @param params
     * @return
     */
    Map<String, Object> photoList(Map<String, Object> params);

    /**
     * 保存基地相册照片
     * @param params
     */
    void saveBasePhoto(Map<String, Object> params);

    /**
     * 完成生产基地的编辑
     * @param params
     * @return
     */
    void editComplete(Map<String, Object> params);

}
