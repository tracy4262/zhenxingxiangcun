package com.ovit.manager.modules.nswy.service;

import com.ovit.manager.common.persistence.Page;
import com.ovit.manager.common.service.CrudService;
import com.ovit.manager.modules.nswy.dao.FullAppSettingDao;
import com.ovit.manager.modules.nswy.dao.UnitDao;
import com.ovit.manager.modules.nswy.entity.FullAppSettingInfo;
import com.ovit.manager.modules.nswy.entity.UnitInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 *
 *
 * @author wangrongf
 * @create 2018-02-09 10:35
 **/
@Service
@Transactional
public class UnitService extends CrudService<UnitDao, UnitInfo> {

    @Autowired
    private UnitDao unitDao;

    public Page<UnitInfo> findFullAppSetting(UnitInfo unitInfo, HttpServletRequest request, HttpServletResponse response) {
        return this.findPage(new Page<>(request,response),unitInfo);
    }

    public List<Map<String,Object>> findUnitType(){
        return unitDao.findUnitType();
    }

    public  List<UnitInfo> findUnitList(UnitInfo unitInfo){

        List<UnitInfo> unitinfos = unitDao.findList(unitInfo);

        return unitinfos ;
    }

    public void updateUnit(UnitInfo unitInfo){
        unitDao.updateUnit(unitInfo);
    }

    public void saveUnit(UnitInfo unitInfo){
        unitDao.saveUnit(unitInfo);
    }

    public void deleteUnit(Integer unitId){
        unitDao.deleteUnit(unitId);
    }

}
