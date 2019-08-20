package com.ovit.manager.modules.nswy.service;

import com.ovit.manager.common.persistence.Page;
import com.ovit.manager.common.service.CrudService;
import com.ovit.manager.modules.cms.entity.Policy;
import com.ovit.manager.modules.nswy.dao.NswyPolicyExamineDao;
import com.ovit.manager.modules.nswy.entity.PolicyInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * s
 *
 * @author haoWen
 * @create 2018-03-01 10:30
 **/
@Service
@Transactional
public class NswyPolicyExamineService extends CrudService<NswyPolicyExamineDao, PolicyInfo> {
    @Autowired
    private NswyPolicyExamineDao nswyPolicyExamineDao;
    public Page<PolicyInfo> findInfoList(PolicyInfo policyInfo, HttpServletRequest request, HttpServletResponse response) {
        Page<PolicyInfo> page = this.findPage(new Page<>(request,response),policyInfo);
        return page;
    }

    public void deletePolicy(Integer policyInfoId) {
        nswyPolicyExamineDao.deletePolicy(policyInfoId);
    }

    public void policyAdopt(Integer policyInfoId) {
        nswyPolicyExamineDao.policyAdopt(policyInfoId);
    }

    public void policyRefuse(Integer policyInfoId) {
        nswyPolicyExamineDao.policyRefuse(policyInfoId);
    }

    public void policySetOrderNum(Map<String, Object> map) {
        nswyPolicyExamineDao.policySetOrderNum(map);
    }

    public void policyUpdateNavDisplay(Map<String, Object> map) {
        nswyPolicyExamineDao.policyUpdateNavDisplay(map);
    }

    public void policyUpdateHome(Map<String, Object> map) {
        nswyPolicyExamineDao.policyUpdateHome(map);
    }
}
