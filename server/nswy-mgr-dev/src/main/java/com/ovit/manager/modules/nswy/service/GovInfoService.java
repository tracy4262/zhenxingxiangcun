package com.ovit.manager.modules.nswy.service;

import com.ovit.manager.common.persistence.Page;
import com.ovit.manager.common.service.CrudService;
import com.ovit.manager.common.utils.DateUtils;
import com.ovit.manager.modules.nswy.dao.GovInfoDao;
import com.ovit.manager.modules.nswy.dao.NswyGovInfoDao;
import com.ovit.manager.modules.nswy.entity.NswyGovInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/** 机构 service
 * Created by ${huipei.x} on 2017-8-16.
 */
@Service
@Transactional(readOnly = true)
public class GovInfoService extends CrudService<GovInfoDao, NswyGovInfo> {

    @Autowired
    private GovInfoDao govInfoDao;



    @Transactional()
    public void deleteGovInfo(Integer govInfoId){
        govInfoDao.deleteGovInfo(govInfoId);
    }

    @Transactional()
    public void govInfoUpdateNavDisplay(Map<String, Object> map) {
        govInfoDao.govInfoUpdateNavDisplay(map);
    }
    @Transactional()
    public void govInfoSetOrderNum(Map<String, Object> map) {
        govInfoDao.govInfoSetOrderNum(map);
    }
    @Transactional()
    public void govInfoUpdateHome(Map<String, Object> map) {
        govInfoDao.govInfoUpdateHome(map);
    }


    //列表页
    public Page<NswyGovInfo> findList(NswyGovInfo nswyGovInfo, HttpServletRequest request, HttpServletResponse response) {
        Page<NswyGovInfo> page = this.findPage(new Page<>(request,response),nswyGovInfo);
        return page;
    }
}
