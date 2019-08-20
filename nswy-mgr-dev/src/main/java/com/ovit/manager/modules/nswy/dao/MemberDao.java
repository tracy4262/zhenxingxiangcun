package com.ovit.manager.modules.nswy.dao;

import com.ovit.manager.common.persistence.annotation.MyBatisDao;

import java.util.List;
import java.util.Map;


@MyBatisDao
public interface MemberDao{
    List<Map<String,Object>> findRealMember(Map<String,Object> map);

    String findMemberType(String id);

    String findRelatedSpecies(Map<String,Object> map);

    String findRelatedProduct(Map<String,Object> map);

    String findRelatedService(Map<String,Object> map);

    String findRelatedIndustry(Map<String,Object> map);

    void memberSetOrderNum(Map<String, Object> map);

    void memberUpdateHome(Map<String, Object> map);
}
