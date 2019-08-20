package com.ovit.nswy.portal.service;

import com.github.pagehelper.PageInfo;
import com.ovit.nswy.portal.model.request.PolicyQuery;
import com.ovit.nswy.portal.model.response.PolicyDTO;

/**
 * Created by ${huipei.x} on 2018-1-12.
 */
public interface RegulationOfPolicyService {
    public PageInfo<PolicyDTO>      listPolicy(PolicyQuery policyQuery);
}
