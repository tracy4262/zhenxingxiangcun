package com.ovit.manager.modules.nswy.dao;

import com.ovit.manager.common.persistence.CrudDao;
import com.ovit.manager.common.persistence.annotation.MyBatisDao;
import com.ovit.manager.modules.nswy.entity.ProxyUserInfo;


@MyBatisDao
public interface ProxyUserInfoDao extends CrudDao<ProxyUserInfo> {

    int deleteByPrimaryKey(Integer id);

    int insert(ProxyUserInfo record);

    int insertSelective(ProxyUserInfo record);

    ProxyUserInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ProxyUserInfo record);

    void updateAuditUser(ProxyUserInfo record);

    void deleteAuditByPrimaryKey(Integer id);
}