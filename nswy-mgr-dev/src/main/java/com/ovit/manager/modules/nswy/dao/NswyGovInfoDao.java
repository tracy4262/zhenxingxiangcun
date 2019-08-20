package com.ovit.manager.modules.nswy.dao;

import com.ovit.manager.common.persistence.CrudDao;
import com.ovit.manager.common.persistence.annotation.MyBatisDao;
import com.ovit.manager.modules.nswy.entity.NswyGovInfo;

/**
 * Created by ${huipei.x} on 2017-8-16.
 */
@MyBatisDao
public interface NswyGovInfoDao extends CrudDao<NswyGovInfo> {

    NswyGovInfo getByPrimaryKey(Integer govInfoId);
    void  deleteByPrimaryKey(Integer govInfoId);
    void updateByPrimaryKeySelective(NswyGovInfo nswyGovInfo);

}
