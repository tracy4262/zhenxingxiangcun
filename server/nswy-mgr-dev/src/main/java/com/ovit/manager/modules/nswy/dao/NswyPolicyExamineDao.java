package com.ovit.manager.modules.nswy.dao;

import com.ovit.manager.common.persistence.CrudDao;
import com.ovit.manager.common.persistence.annotation.MyBatisDao;
import com.ovit.manager.modules.nswy.entity.PolicyInfo;

import java.util.Map;

@MyBatisDao
public interface NswyPolicyExamineDao extends CrudDao<PolicyInfo> {
    void deletePolicy(Integer policyInfoId);

    void policyAdopt(Integer policyInfoId);

    void policyRefuse(Integer policyInfoId);

    void policySetOrderNum(Map<String, Object> map);

    void policyUpdateNavDisplay(Map<String, Object> map);

    void policyUpdateHome(Map<String, Object> map);
}
