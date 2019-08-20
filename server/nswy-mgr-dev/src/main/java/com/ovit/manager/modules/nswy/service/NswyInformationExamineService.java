package com.ovit.manager.modules.nswy.service;

import com.ovit.manager.common.persistence.Page;
import com.ovit.manager.common.service.CrudService;
import com.ovit.manager.modules.nswy.dao.NswyInformationExamineDao;
import com.ovit.manager.modules.nswy.entity.InformationInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * s
 *
 * @author haoWen
 * @create 2018-03-01 10:30
 **/
@Service
@Transactional
public class NswyInformationExamineService extends CrudService<NswyInformationExamineDao, InformationInfo> {
    @Autowired
    private NswyInformationExamineDao nswyInformationExamineDao;
    public Page<InformationInfo> findInfoList(InformationInfo informationInfo, HttpServletRequest request, HttpServletResponse response) {
        Page<InformationInfo> page = this.findPage(new Page<>(request,response),informationInfo);
        return  page;
    }

    public void deleteInformation(Integer informationInfoId) {
        nswyInformationExamineDao.deleteInformation(informationInfoId);
    }

    public void informationAdopt(Integer informationInfoId) {
        nswyInformationExamineDao.informationAdopt(informationInfoId);
    }

    public void informationRefuse(Integer informationInfoId) {
        nswyInformationExamineDao.informationRefuse(informationInfoId);
    }

    public void informationUpdateNavDisplay(Map<String, Object> map) {
        nswyInformationExamineDao.informationUpdateNavDisplay(map);
    }

    public void informationSetOrderNum(Map<String, Object> map) {
        nswyInformationExamineDao.informationSetOrderNum(map);
    }

    public void informationUpdateHome(Map<String, Object> map) {
        nswyInformationExamineDao.informationUpdateHome(map);
    }
}
