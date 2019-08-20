package com.ovit.manager.modules.nswy.service.dictionaryManagement;

import com.ovit.manager.common.persistence.Page;
import com.ovit.manager.common.service.CrudService;
import com.ovit.manager.modules.nswy.dao.dictionaryManagement.DictionaryPolicyDao;
import com.ovit.manager.modules.nswy.entity.dictionaryManagement.DictionaryPolicy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Service
@Transactional
public class DictionaryPolicyService extends CrudService<DictionaryPolicyDao, DictionaryPolicy> {

    @Autowired
    private DictionaryPolicyDao dictionaryPolicyDao;

    public Page<DictionaryPolicy> findList(DictionaryPolicy dictionaryPolicy, HttpServletRequest request, HttpServletResponse response) {
        return this.findPage(new Page<>(request, response), dictionaryPolicy);
    }

    public List<DictionaryPolicy> findPolicyList(DictionaryPolicy dictionaryPolicy){

        List<DictionaryPolicy> dictionaryPolicys = dictionaryPolicyDao.findList(dictionaryPolicy);

        return dictionaryPolicys ;
    }

    public void savePolicy(DictionaryPolicy dictionaryPolicy){
            dictionaryPolicyDao.savePolicy(dictionaryPolicy);
    }

    public void updatePolicy(DictionaryPolicy dictionaryPolicy){
            dictionaryPolicyDao.updatePolicy(dictionaryPolicy);
    }

    public void deletePolicy(String id){
            dictionaryPolicyDao.deletePolicy(id);
    }


}
