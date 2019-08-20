package com.ovit.manager.modules.nswy.service;


import com.ovit.manager.common.persistence.Page;
import com.ovit.manager.common.service.CrudService;
import com.ovit.manager.modules.nswy.dao.RealNameDao;
import com.ovit.manager.modules.nswy.entity.RealName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;



@Service
@Transactional
public class RealNameService extends CrudService<RealNameDao, RealName> {
    @Autowired
    RealNameDao realNameDao;

    public Page<RealName> findList(RealName realName, HttpServletRequest request, HttpServletResponse response) {
        return this.findPage(new Page<>(request,response),realName);
    }

    public List<RealName> findByUserId(String userId){
        return realNameDao.findByUserId(userId);
    }

    public List<RealName> findById(String realId){
        return realNameDao.findById(realId);
    }

    public void saveRealName(RealName realName){
        realNameDao.saveRealName(realName);
    }

    public void deleteRealName(String userId){
        realNameDao.deleteRealName(userId);
    }

    public void updateRealName(String userId){
        realNameDao.updateRealName(userId);
    }
}
