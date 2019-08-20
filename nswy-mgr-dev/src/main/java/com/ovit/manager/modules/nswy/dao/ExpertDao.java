package com.ovit.manager.modules.nswy.dao;

import com.ovit.manager.common.persistence.CrudDao;
import com.ovit.manager.common.persistence.annotation.MyBatisDao;
import com.ovit.manager.modules.nswy.entity.LoginUser;

import java.util.Map;

@MyBatisDao
public interface ExpertDao extends CrudDao<LoginUser> {

    int deleteByPrimaryKey(Integer id);

    void expertUpdateNavDisplay(Map<String, Object> map);

    void expertSetOrderNum(Map<String, Object> map);

    void expertUpdateHome(Map<String, Object> map);
}
