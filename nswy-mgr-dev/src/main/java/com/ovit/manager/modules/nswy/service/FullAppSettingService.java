package com.ovit.manager.modules.nswy.service;

import com.ovit.manager.common.persistence.Page;
import com.ovit.manager.common.service.CrudService;
import com.ovit.manager.modules.nswy.dao.FullAppSettingDao;
import com.ovit.manager.modules.nswy.dao.ReportManangeDao;
import com.ovit.manager.modules.nswy.entity.FullAppSettingInfo;
import com.ovit.manager.modules.nswy.entity.ReportManageInfo;
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
public class FullAppSettingService extends CrudService<FullAppSettingDao, FullAppSettingInfo> {

    @Autowired
    private FullAppSettingDao fullAppSettingDao;

    public Page<FullAppSettingInfo> findFullAppSetting(FullAppSettingInfo fullAppSettingInfo, HttpServletRequest request, HttpServletResponse response) {
        return this.findPage(new Page<>(request,response),fullAppSettingInfo);
    }

    public FullAppSettingInfo findByAppStting(String appSettingId) {
        return fullAppSettingDao.findByAppSetting(appSettingId);
    }


    public void updateFullAppSetting(Map<String, Object> map){
        fullAppSettingDao.updateFullAppSetting(map);
    }

}
