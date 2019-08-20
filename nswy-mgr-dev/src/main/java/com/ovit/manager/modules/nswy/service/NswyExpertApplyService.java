package com.ovit.manager.modules.nswy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ovit.manager.common.persistence.Page;
import com.ovit.manager.common.service.CrudService;
import com.ovit.manager.common.utils.DateUtils;
import com.ovit.manager.modules.nswy.dao.ExpertApplyDao;
import com.ovit.manager.modules.nswy.dao.NswyExpertDao;
import com.ovit.manager.modules.nswy.entity.ExpertApply;
import com.ovit.manager.modules.nswy.entity.NswyExpert;

import java.util.Date;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**专家 service
 * Created by ${zhanlei} on 2017-8-16.
 */
@Service
@Transactional(readOnly = true)
public class NswyExpertApplyService extends CrudService<ExpertApplyDao, ExpertApply> {

    @Autowired
    private ExpertApplyDao ExpertApplyDao;

    @Transactional()
    public void deleteExpertApply(Integer expertId){
    	ExpertApplyDao.deleteByPrimaryKey(expertId);
    }

    @Transactional()
    public void updateExpertApply(ExpertApply nswyExpertApply){
        if (nswyExpertApply.getApproveStatus() != null){
            ExpertApplyDao.updateStatus(nswyExpertApply);
        }
    }
}
