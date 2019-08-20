package com.ovit.nswy.member.service;

import com.github.pagehelper.PageInfo;
import com.ovit.nswy.member.model.request.ExpertQuery;

import java.util.List;
import java.util.Map;

public interface EmployService {

    int removeEmploy(int id);

    Map<String, Object> findByid(int id);

    int addExpert(Map<String, Object> expert_employ);

    int acceptInvitation(Map<String, Object> map);

    int declineInvitation(Map<String, Object> map);

    PageInfo<Map<String, Object>> selectAll(Map<String, Object> map);

    PageInfo<Map<String, Object>> expertAllTable(Map<String, Object> map);

    PageInfo<List<Map<String, Object>>> selectAllInform(Map<String, Object> map);

    PageInfo<List<Map<String, Object>>> selectAllCompany(Map<String, Object> map);

    PageInfo<List<Map<String, Object>>> findExpert(ExpertQuery expertQuery);

    int resignation(Map<String, Object> map);

    Map<String, Object> queryIsExist(Map<String, Object> params);
    Map<String, Object> queryById(Map<String, Object> params);
    void updateExpert(Map<String, Object> params);
}
