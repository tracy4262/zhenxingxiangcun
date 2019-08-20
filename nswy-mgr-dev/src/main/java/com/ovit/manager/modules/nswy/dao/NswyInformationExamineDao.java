package com.ovit.manager.modules.nswy.dao;

import com.ovit.manager.common.persistence.CrudDao;
import com.ovit.manager.common.persistence.annotation.MyBatisDao;
import com.ovit.manager.modules.nswy.entity.InformationInfo;

import java.util.Map;

@MyBatisDao
public interface NswyInformationExamineDao extends CrudDao<InformationInfo> {
    void deleteInformation(Integer informationInfoId);

    void informationAdopt(Integer informationInfoId);

    void informationRefuse(Integer informationInfoId);

    void informationUpdateNavDisplay(Map<String, Object> map);

    void informationSetOrderNum(Map<String, Object> map);

    void informationUpdateHome(Map<String, Object> map);
}
