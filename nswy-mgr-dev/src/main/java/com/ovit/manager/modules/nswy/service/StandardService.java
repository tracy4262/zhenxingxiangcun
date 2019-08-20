package com.ovit.manager.modules.nswy.service;

import com.ovit.manager.common.persistence.Page;
import com.ovit.manager.common.service.CrudService;
import com.ovit.manager.modules.nswy.dao.StandardDao;
import com.ovit.manager.modules.nswy.entity.StandardInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 标准审核service
 *
 * @author haoWen
 * @create 2018-03-14 15:09
 **/
@Service
@Transactional
public class StandardService extends CrudService<StandardDao, StandardInfo> {
    @Autowired
    private StandardDao standardDao;
    public Page<StandardInfo> findProxyAgencyList(StandardInfo standardInfo, HttpServletRequest request, HttpServletResponse response) {
        return this.findPage(new Page<>(request,response),standardInfo);
    }

    public void deleteStandard(Integer standardId) {
        standardDao.deleteStandard(standardId);
    }

    public void standardAdopt(Integer standardId) {
        standardDao.standardAdopt(standardId);
    }

    public void standardRefuse(Integer standardId) {
        standardDao.standardRefuse(standardId);
    }

    public void standardUpdateHome(Map<String, Object> map) {
        standardDao.standardUpdateHome(map);
    }

    public void standardUpdateNavDisplay(Map<String, Object> map) {
        standardDao.standardUpdateNavDisplay(map);
    }

    public void standardSetOrderNum(Map<String, Object> map) {
        standardDao.standardSetOrderNum(map);
    }
}
