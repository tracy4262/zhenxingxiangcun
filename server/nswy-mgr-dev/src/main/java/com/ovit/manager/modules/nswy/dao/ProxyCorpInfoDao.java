package com.ovit.manager.modules.nswy.dao;

import com.ovit.manager.common.persistence.CrudDao;
import com.ovit.manager.common.persistence.annotation.MyBatisDao;
import com.ovit.manager.modules.nswy.entity.ProxyCropInfo;


@MyBatisDao
public interface ProxyCorpInfoDao extends CrudDao<ProxyCropInfo> {
    int deleteByPrimaryKey(ProxyCropInfo record);

    int insert(ProxyCropInfo record);

    int insertSelective(ProxyCropInfo record);

    ProxyCropInfo selectByPrimaryKey(ProxyCropInfo record);

    int updateByPrimaryKeySelective(ProxyCropInfo record);

    int updateCorpInfo(ProxyCropInfo record);

    void updateLoginUser(ProxyCropInfo record);

}