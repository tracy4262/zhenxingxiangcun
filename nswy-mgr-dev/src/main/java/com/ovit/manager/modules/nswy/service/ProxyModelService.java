package com.ovit.manager.modules.nswy.service;


import com.ovit.manager.common.persistence.Page;
import com.ovit.manager.common.service.CrudService;
import com.ovit.manager.modules.nswy.dao.ProxyModelDao;
import com.ovit.manager.modules.nswy.entity.FullAppSettingInfo;
import com.ovit.manager.modules.nswy.entity.ProxyModelInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class ProxyModelService extends CrudService<ProxyModelDao, ProxyModelInfo> {

    @Autowired
    private ProxyModelDao proxyModelDao;


    public Page<ProxyModelInfo> findProxyModelInfo(ProxyModelInfo proxyModelInfo, HttpServletRequest request, HttpServletResponse response) {
        return this.findPage(new Page<>(request,response),proxyModelInfo);
    }

    public List<Map<String,Object>> findFileType(Map<String,Object> params) {
        return proxyModelDao.findFileType(params);
    }

    public ProxyModelInfo findByProxyModel(String fileId) {
        return proxyModelDao.findByProxyModel(fileId);
    }

    public void saveProxyModel(Map<String,Object> params){

        proxyModelDao.saveProxyModel(params);

    }

    public void updateProxyModelInfo(Map<String,Object> params){

        proxyModelDao.updateProxyModelInfo(params);

    }

    public void deleteProxyModelInfo(String fileId){

        proxyModelDao.deleteProxyModelInfo(fileId);

    }


}
