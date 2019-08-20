package com.ovit.nswy.member.application.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface MyRecommendMapper {

    List<Map<String,Object>> commonServiceList(Map<String, Object> params);

    List<Map<String,Object>> recommendCommonServiceList(Map<String, Object> params);

    List<Map<String,Object>> consultList(Map<String, Object> params);

    List<Map<String,Object>> recommendConsultList(Map<String, Object> params);

    List<Map<String,Object>> findFishTimeChargeInfo(Map<String, Object> params);

    List<Map<String,Object>> findServiceVarietyInfo(Map<String, Object> params);

    List<Map<String,Object>> findProductManagementService(Map<String, Object> params);

    List<Map<String,Object>> findServiceJoinInfo(Map<String, Object> params);

    void batchSaveRecommend(List<Map<String,Object>> list);

    Map<String,Object> isHadRecommend(Map<String,Object> params);

    void singleSaveRecommend(Map<String,Object> params);

    void batchCancelRecommend(List<Map<String,Object>> list);

    List<Map<String,Object>> baseList(Map<String, Object> params);

    List<Map<String,Object>> recommendBaseList(Map<String, Object> params);

    List<Map<String,Object>> expertList(Map<String, Object> params);

    List<Map<String,Object>> recommendExpertList(Map<String, Object> params);

    Map<String,Object> memberInfo(Map<String, Object> params);

    Integer productList(Map<String, Object> params);

    String findMemberName(Map<String, Object> params);
}
