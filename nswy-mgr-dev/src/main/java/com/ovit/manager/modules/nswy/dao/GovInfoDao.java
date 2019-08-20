package com.ovit.manager.modules.nswy.dao;

import com.ovit.manager.common.persistence.CrudDao;
import com.ovit.manager.common.persistence.annotation.MyBatisDao;
import com.ovit.manager.modules.nswy.entity.NswyGovInfo;

import java.util.Map;

/**
 * Created by ${huipei.x} on 2017-8-16.
 */
@MyBatisDao
public interface GovInfoDao extends CrudDao<NswyGovInfo> {

    void deleteGovInfo(Integer govInfoId);

    void govInfoUpdateNavDisplay(Map<String, Object> map);

    void govInfoSetOrderNum(Map<String, Object> map);

    void govInfoUpdateHome(Map<String, Object> map);


}
