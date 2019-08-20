package com.ovit.nswy.portal.mapper;

import com.ovit.nswy.portal.core.sql.RegulationOfPolicySqlProvider;
import com.ovit.nswy.portal.model.response.PolicyDTO;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;
import java.util.Map;

/**
 * Created by ${huipei.x} on 2018-1-12.
 */
public interface RegulationOfPolicyMapper {

    @SelectProvider(type = RegulationOfPolicySqlProvider.class,method = "getRegulationOfPolicySqlByMap")
    @ResultType(PolicyDTO.class)
    public List<PolicyDTO> getlist(Map params);
}
