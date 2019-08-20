package com.ovit.nswy.member.mapper;

import com.ovit.nswy.member.model.PolicyDetail;

import java.util.List;
import java.util.Map;

public interface PolicyDetailMapper {

    PolicyDetail selectByPrimaryKey(Integer id);

    List<PolicyDetail> findColumnPolicy(PolicyDetail record);

    List<PolicyDetail> findPolicyDetailLeftPolicy(PolicyDetail record);

    void updateThumbUpNum(PolicyDetail record);

    int insert(PolicyDetail record);

    List<PolicyDetail> findBySpeciesName(Map<String,Object> map);

    List<PolicyDetail> newpolicy();

    List<Map<String,Object>> findPolicyDetail(Integer id);

    int selectMinId();

    int selectMaxId();

    List<PolicyDetail> relevantPolicy(Map<String,Object> map);
}