package com.ovit.manager.modules.nswy.service;

import com.ovit.manager.common.persistence.Page;
import com.ovit.manager.common.service.CrudService;
import com.ovit.manager.modules.nswy.dao.GeneralServiceInfoDao;
import com.ovit.manager.modules.nswy.entity.GeneralServiceInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class GeneralServiceInfoService extends CrudService<GeneralServiceInfoDao, GeneralServiceInfo> {
    @Autowired
    private GeneralServiceInfoDao generalServiceInfoDao;

    public Page<GeneralServiceInfo> findList(GeneralServiceInfo generalServiceInfo, HttpServletRequest request, HttpServletResponse response){
        return this.findPage(new Page<>(request,response),generalServiceInfo);
    }

    public List<Map<String,Object>> findGeneralService(){
        return generalServiceInfoDao.findGeneralService();
    }

    public  List<GeneralServiceInfo> findGeneralServiceList(GeneralServiceInfo generalServiceInfo){
        List<GeneralServiceInfo> generalServiceInfos = generalServiceInfoDao.findList(generalServiceInfo);
        return generalServiceInfos;
    }

    public void updateGeneralService(GeneralServiceInfo generalServiceInfo){
        generalServiceInfoDao.updateGeneralService(generalServiceInfo);
    }

    public void saveGeneralService(GeneralServiceInfo generalServiceInfo){generalServiceInfoDao.saveGeneralService(generalServiceInfo);}


    public void deleteService(Integer id){
        generalServiceInfoDao.deleteService(id);
    }
}
