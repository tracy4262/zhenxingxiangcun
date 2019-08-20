package com.ovit.manager.modules.nswy.service;

import com.ovit.manager.common.persistence.Page;
import com.ovit.manager.common.service.CrudService;
import com.ovit.manager.modules.nswy.dao.CommodityInfoDao;
import com.ovit.manager.modules.nswy.entity.CommodityInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class CommodityService extends CrudService<CommodityInfoDao, CommodityInfo> {
    @Autowired
    private CommodityInfoDao commodityInfoDao;

    public Page<CommodityInfo> findList(CommodityInfo commodityInfo, HttpServletRequest request, HttpServletResponse response){
        return this.findPage(new Page<>(request,response),commodityInfo);
    }

    public List<Map<String,Object>> findCommodity(){
        return commodityInfoDao.findCommodity();
    }

    public  List<CommodityInfo> findCommodityList(CommodityInfo commodityInfo){
        List<CommodityInfo> commodityInfos = commodityInfoDao.findList(commodityInfo);
        return commodityInfos;
    }

    public void saveCommodity(CommodityInfo commodityInfo){commodityInfoDao.saveCommodity(commodityInfo);}

    public void updateCommodity(CommodityInfo commodityInfo){
        commodityInfoDao.updateCommodity(commodityInfo);
    }

    public void deleteByPrimaryKey(Integer id){
        commodityInfoDao.deleteByPrimaryKey(id);
    }
}
