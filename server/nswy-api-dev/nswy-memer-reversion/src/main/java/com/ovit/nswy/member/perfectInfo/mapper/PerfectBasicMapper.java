package com.ovit.nswy.member.perfectInfo.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface PerfectBasicMapper {

    List<Map<String,Object>> findUserModule(Map<String, Object> params);

    List<Map<String,Object>> findSubModule(Map<String, Object> params);

    List<Map<String,Object>> findDynamicSubModule(Map<String, Object> params);

    void modifyModule(Map<String, Object> params);

    Map<String,Object> findTextPreview(Map<String,Object> params);

    void saveTextPreview(Map<String,Object> params);

    void updateTextPreview(Map<String,Object> params);

    void saveYearInfo(Map<String, Object> params);

    void deleteYearInfo(Map<String, Object> params);

    List<Map<String,Object>> findYearInfo(Map<String, Object> params);

    List<Map<String,Object>> findModuleInfo(Map<String, Object> params);

    Map<String,Object> findPropertyInfo(Map<String, Object> params);

    void insertPropertyInfo(Map<String,Object> params);

    void updatePropertyInfo(Map<String,Object> params);

    Map<String,Object> findAllTextPreviewList(Map<String, Object> params);

    void saveAllTextPreview(Map<String,Object> params);

    Map<String,Object> findUseTemplate(String account);

    Map<String,Object> findNewYearInfo(String account);

    Map<String,Object> findPrivacyInfo(Map<String,Object> params);

    Map<String,Object> findMemberName(Map<String,Object> params);

}
