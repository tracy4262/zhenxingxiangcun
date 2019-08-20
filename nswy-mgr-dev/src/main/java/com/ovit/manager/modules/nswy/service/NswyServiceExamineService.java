package com.ovit.manager.modules.nswy.service;

import com.ovit.manager.common.persistence.Page;
import com.ovit.manager.common.service.CrudService;
import com.ovit.manager.modules.nswy.dao.NswyInformationExamineDao;
import com.ovit.manager.modules.nswy.dao.NswyServiceDao;
import com.ovit.manager.modules.nswy.entity.InformationInfo;
import com.ovit.manager.modules.nswy.entity.NswyServiceInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * s
 *
 * @author haoWen
 * @create 2018-03-01 10:30
 **/
@Service
@Transactional
public class NswyServiceExamineService extends CrudService<NswyServiceDao, NswyServiceInfo> {
    @Autowired
    private NswyServiceDao nswyServiceDao;

    public Page<NswyServiceInfo> findInfoList(NswyServiceInfo nswyServiceInfo, HttpServletRequest request, HttpServletResponse response) {
        Page<NswyServiceInfo> page = this.findPage(new Page<>(request,response),nswyServiceInfo);
        return  page;
    }

    public void serviceDel(Integer id) {
        Map<String, Object> map = new HashMap<>();
        map.put("id",id);
        nswyServiceDao.serviceDel(map);
    }

    public void serviceUpdateNavDisplay(Map<String, Object> map) {
        nswyServiceDao.serviceUpdateNavDisplay(map);
    }

    public void serviceSetOrderNum(Map<String, Object> map) {
        nswyServiceDao.serviceSetOrderNum(map);
    }

    public void serviceUpdateHome(Map<String, Object> map) {
        nswyServiceDao.serviceUpdateHome(map);
    }
}
