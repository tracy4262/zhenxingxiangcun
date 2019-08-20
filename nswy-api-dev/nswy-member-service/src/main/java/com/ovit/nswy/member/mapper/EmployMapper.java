package com.ovit.nswy.member.mapper;

import com.ovit.nswy.member.model.request.ExpertQuery;
import java.util.List;
import java.util.Map;

public interface EmployMapper {

    int removeEmploy(int id);

    int addExpert(Map<String, Object> map);

    Map<String, Object> findByid(int id);

    int acceptInvitation(Map<String, Object> map);

    int declineInvitation(Map<String, Object> map);

    List<Map<String, Object>> selectAll(Map<String, Object> map);

    List<Map<String, Object>> expertAllTable(Map<String, Object> map);

    List<Map<String, Object>> selectAllCompany(Map<String, Object> map);

    List<Map<String, Object>> selectExpert(ExpertQuery expertQuery);

    int resignation(Map<String, Object> map);

    void updateExpert(Map<String, Object> params);

    Map<String,Object> queryById(Map<String, Object> params);

    Map<String,Object> queryIsExist(Map<String, Object> params);

    List<Map<String,Object>> queryCorpUserNotice(Map<String, Object> map);

    List<Map<String,Object>> queryGovUserNotice(Map<String, Object> map);

    List<Map<String,Object>> queryExpertUserNotice(Map<String, Object> map);

    List<Map<String,Object>> queryCommonUserNotice(Map<String, Object> map);
}
