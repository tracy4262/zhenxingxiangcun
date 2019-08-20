package com.ovit.nswy.member.service;


import com.github.pagehelper.PageInfo;
import com.ovit.nswy.member.model.PageList;

import java.util.List;
import java.util.Map;

/**
 * 企业认证第五步-完善信息
 */

public interface PerfectInformationService {

     Map<String, Object> findPerfectInformation(Map<String, Object> params);

     void insertPerfectInformation(Map<String, Object> params);

     Map<String, Object> findSysUserInfo(Map<String, Object> params);

     void insertPrivacyInfo(String account, Map<String, Object> privacyInfo);

     void insertNetworkInfo(String account, Map<String, Object> networkInfo);

     void insertPolicyInfo(String account, Map<String, Object> policyInfo);

     void insertReligionInfo(String account, Map<String, Object> religionInfo);

     void insertManageSurveyInfo(String account, Map<String, Object> manageSurveyInfo);

     void insertProfessionInfo(String account, Map<String, Object> professionInfo);

     void insertProfessionalQualification(String account, List<Map<String, Object>> qualificationList);

     void insertProductService(String account, List<Map<String, Object>> productServiceList);

     Map<String, Object> getPrivacyInfo(String account);

     Map<String, Object> getNetworkInfo(String account);

     Map<String, Object> getPolicyInfo(String account);

     Map<String, Object> getReligionInfo(String account);

     Map<String, Object> getManageSurveyInfo(String account);

     Map<String, Object> getProfessionInfo(String account);

     List<Map<String, Object>> getProfessionalQualification(String account);

     List<Map<String, Object>> getProductService(String account);

     List<Map<String, Object>> findDepartment(Map<String, Object> params);

     List<Map<String, Object>> findDepartById(Map<String, Object> params);

     void insertOrganization(Map<String, Object> params);

     void updateOrganization(Map<String, Object> params);

     void deleteDepartment(Map<String, Object> params);

     Map<String, Object> getModulesByAccount(String account);

     Map<String, Object> findGateway(Map<String, Object> params);

     Map<String, Object> findWorkTime(Map<String, Object> params);

     List<Map<String, Object>> findFacilityAssets(Map<String, Object> params);

     void insertFacilityAssets(Map<String, Object> params);

     List<Map<String, Object>> findIntangibleAssets(Map<String, Object> params);

     void insertIntangibleAssets(Map<String, Object> params);

     List<Map<String, Object>> findPlaceOfBusiness(Map<String, Object> params);

     void insertPlaceOfBusiness(Map<String, Object> params);

     List<Map<String, Object>> findCommonAssets(Map<String, Object> params);

     void insertCommonAssets(Map<String, Object> params);

     PageList findHonorInfo(Map<String, Object> params);

     PageList findQualification(Map<String, Object> params);

     Map<String, Object> findLoginUrl(String account);

     List<Map<String, Object>> findOverviewInfo(Map<String, Object> params);

     void insertOverviewInfo(Map<String, Object> params);

     void updatePrivacyInfo(Map<String, Object> privacyInfo);

     void updateDepartStatus(Map<String, Object> params);

     Map<String, Object> findPrivacyInfo(Map<String, Object> params);

}
