package com.ovit.nswy.member.mapper;


import java.util.List;
import java.util.Map;

public interface PerfectInformationMapper {

    List<Map<String,Object>> findSysUserInfo(Map<String,Object> params);

    List<Map<String,Object>> findEducation(Map<String,Object> params);

    List<Map<String,Object>> findWork(Map<String,Object> params);

    List<Map<String,Object>> findWorkTime(Map<String,Object> params);

    List<Map<String,Object>> findHonor(Map<String,Object> params);

    List<Map<String,Object>> findTeam(Map<String,Object> params);

    Map<String,Object> findFinance(Map<String,Object> params);

    List<Map<String,Object>> findDepartById(Map<String,Object> params);

    List<Map<String,Object>> findDepartList(Map<String,Object> params);

    void insertEducational(Map<String,Object> params);

    void insertWork(Map<String,Object> params);

    void insertHonor(Map<String,Object> params);

    void insertTeam(Map<String,Object> params);

    void insertFinance(Map<String,Object> params);

    void insertOrganization(Map<String,Object> params);

    void updateOrganization(Map<String,Object> params);

    void deleteDepartment(Map<String,Object> params);

    void deleteEducation(Map<String,Object> params);

    void deleteWork(Map<String,Object> params);

    void deleteHonor(Map<String,Object> params);

    void deleteTeam(Map<String,Object> params);

    void deleteFinance(Map<String,Object> params);
    /**
     * 根据account查询隐私信息
     * @param account
     * @return
     */
    Map<String,Object> getPrivacyInfoByAccount(String account);

    /**
     * 根据account删除隐私信息
     * @param account
     * @return
     */
    void deletePrivacyInfoByAccount(String account);

    /**
     * 插入隐私信息，含account
     * @param privacyInfo
     * @return
     */
    void insertPrivacyInfo(Map<String,Object> privacyInfo);

    /**
     * 更新隐私信息，含account
     * @param privacyInfo
     * @return
     */
    void updatePrivacyInfo(Map<String,Object> privacyInfo);

    /**
     * 根据account查询网络信息
     * @param account
     * @return
     */
    Map<String,Object> getNetworkInfoByAccount(String account);

    /**
     * 根据account删除网络信息
     * @param account
     * @return
     */
    void deleteNetworkInfoByAccount(String account);

    /**
     * 插入网络信息，含account
     * @param privacyInfo
     * @return
     */
    void insertNetworkInfoInfo(Map<String,Object> privacyInfo);

    /**
     * 根据account查询政治面貌信息
     * @param account
     * @return
     */
    Map<String,Object> getPolicyInfoByAccount(String account);

    /**
     * 根据account删除政治面貌信息
     * @param account
     * @return
     */
    void deletePolicyInfoByAccount(String account);

    /**
     * 插入政治面貌信息，含account
     * @param policyInfo
     * @return
     */
    void insertPolicyInfoInfo(Map<String,Object> policyInfo);

    /**
     * 根据account查询宗教信仰信息
     * @param account
     * @return
     */
    Map<String,Object> getReligionInfoByAccount(String account);

    /**
     * 根据account删除宗教信仰信息
     * @param account
     * @return
     */
    void deleteReligionInfoByAccount(String account);

    /**
     * 插入宗教信仰信息，含account
     * @param religionInfo
     * @return
     */
    void insertReligionInfoInfo(Map<String,Object> religionInfo);

    /**
     * 根据account查询经营概况信息
     * @param account
     * @return
     */
    Map<String,Object> getManageSurveyByAccount(String account);

    /**
     * 根据account删除经营概况信息
     * @param account
     * @return
     */
    void deleteManageSurveyByAccount(String account);

    /**
     * 插入经营概况信息，含account
     * @param manageSurveyInfo
     * @return
     */
    void insertManageSurveyInfo(Map<String,Object> manageSurveyInfo);

    /**
     * 根据account查询专业信息
     * @param account
     * @return
     */
    Map<String,Object> getProfessionInfoByAccount(String account);

    /**
     * 根据account删除专业信息
     * @param account
     * @return
     */
    void deleteProfessionInfoByAccount(String account);

    /**
     * 插入专业信息，含account
     * @param professionInfo
     * @return
     */
    void insertProfessionInfo(Map<String,Object> professionInfo);

    /**
     * 根据account查询专业资质信息
     * @param account
     * @return
     */
    List<Map<String,Object>> getQualificationByAccount(String account);

    /**
     * 根据account删除专业资质信息
     * @param account
     * @return
     */
    void deleteQualificationByAccount(String account);

    /**
     * 插入专业资质信息，含account
     * @param professionList
     * @return
     */
    void insertQualificationInfo(List<Map<String,Object>> professionList);

    /**
     * 根据account查询产品服务信息
     * @param account
     * @return
     */
    List<Map<String,Object>> getProductServiceByAccount(String account);

    /**
     * 根据account删除产品服务信息
     * @param account
     * @return
     */
    void deleteProductServiceByAccount(String account);

    /**
     * 插入产品服务信息，含account
     * @param productServiceList
     * @return
     */
    void insertProductService(List<Map<String,Object>> productServiceList);

    //设施资产
    List<Map<String,Object>> findFacilityAssets(Map<String,Object> params);

    void insertFacilityAssets(Map<String,Object> params);

    void deleteFacilityAssets(Map<String,Object> params);

    List<Map<String,Object>> findIntangibleAssets(Map<String,Object> params);

    void insertIntangibleAssets(Map<String,Object> params);

    void deleteIntangibleAssets(Map<String,Object> params);

    List<Map<String,Object>> findPlaceOfBusiness(Map<String,Object> params);

    void insertPlaceOfBusiness(Map<String,Object> params);

    void deletePlaceOfBusiness(Map<String,Object> params);

    List<Map<String,Object>> findCommonAssets(Map<String,Object> params);

    void insertCommonAssets(Map<String,Object> params);

    void deleteCommonAssets(Map<String,Object> params);

    Map<String,Object> findLoginUser(String account);

    List<Map<String,Object>> findOverviewInfo(Map<String,Object> params);

    void insertOverviewInfo(Map<String,Object> params);

    void deleteOverviewInfo(Map<String,Object> params);

    void updateDepartStatus(Map<String,Object> params);

    Map<String,Object> findAddrDetail(String account);

    Map<String,Object> findPrivacyInfo(Map<String,Object> params);

}