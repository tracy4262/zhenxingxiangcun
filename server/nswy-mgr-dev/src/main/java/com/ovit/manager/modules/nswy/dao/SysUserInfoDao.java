package com.ovit.manager.modules.nswy.dao;

import com.ovit.manager.common.persistence.CrudDao;
import com.ovit.manager.common.persistence.annotation.MyBatisDao;
import com.ovit.manager.modules.nswy.entity.SysUserInfo;
import com.ovit.manager.modules.nswy.entity.SystemDictInfo;

import java.util.List;
import java.util.Map;

@MyBatisDao
public interface SysUserInfoDao extends CrudDao<SysUserInfo> {

    void insertSysUserInfo(Map<String,Object> map);

    List<Map<String,Object>> findCount();

    List<Map<String,Object>> findLoginUser(Map<String,Object> map);

    void deleteSysUserInfo(Map<String,Object> map);

    int updateUserInfo(Map<String,Object> map);

    List<Map<String,Object>> findSysUserInfo(Map<String,Object> map);



}
