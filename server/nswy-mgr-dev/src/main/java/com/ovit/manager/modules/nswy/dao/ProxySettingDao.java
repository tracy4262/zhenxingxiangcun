package com.ovit.manager.modules.nswy.dao;

import com.ovit.manager.common.persistence.CrudDao;
import com.ovit.manager.common.persistence.annotation.MyBatisDao;
import com.ovit.manager.modules.nswy.entity.ProxyAgency;
import java.util.Map;

/**
 * Created by ${zhanlei} on 2017-8-24.
 */
@MyBatisDao
public interface ProxySettingDao extends CrudDao<ProxyAgency> {

    int deleteByPrimaryKey(Integer proxyId);

    int insert(ProxyAgency record);

    int insertSelective(ProxyAgency record);

    ProxyAgency selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Map<String,Integer> proxySetting);

    int updateByPrimaryKey(Map<String,Integer> record);
}