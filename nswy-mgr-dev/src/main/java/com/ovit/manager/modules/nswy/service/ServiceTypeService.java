/*
package com.ovit.manager.modules.nswy.service;*/
package com.ovit.manager.modules.nswy.service;

import com.ovit.manager.common.persistence.Page;
import com.ovit.manager.common.service.CrudService;
import com.ovit.manager.modules.nswy.dao.ServiceTypeDao;
import com.ovit.manager.modules.nswy.entity.ServiceTypeInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class ServiceTypeService extends CrudService<ServiceTypeDao, ServiceTypeInfo> {
    @Autowired
    private ServiceTypeDao serviceTypeDao;

    public Page<ServiceTypeInfo> findFullAppSetting(ServiceTypeInfo serviceTypeInfo, HttpServletRequest request, HttpServletResponse response){
        return this.findPage(new Page<>(request,response),serviceTypeInfo);
    }

    public List<Map<String,Object>> findServiceType(){
        return serviceTypeDao.findServiceType();
    }

    public  List<ServiceTypeInfo> findServiceTypeList(ServiceTypeInfo serviceTypeInfo){

        List<ServiceTypeInfo> serviceTypeInfos = serviceTypeDao.findList(serviceTypeInfo);

        return serviceTypeInfos;
    }

    public void updateServiceType(ServiceTypeInfo serviceTypeInfo){
        serviceTypeDao.updateServiceType(serviceTypeInfo);
    }

    public void saveServiceType(ServiceTypeInfo serviceTypetInfo){
        serviceTypeDao.saveServiceType(serviceTypetInfo);
    }

    public void deleteServiceType(String serviceId){
        serviceTypeDao.deleteServiceType(serviceId);
    }

}


