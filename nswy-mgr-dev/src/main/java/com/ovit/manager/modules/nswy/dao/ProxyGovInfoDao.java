package com.ovit.manager.modules.nswy.dao;

import com.ovit.manager.common.persistence.CrudDao;
import com.ovit.manager.common.persistence.annotation.MyBatisDao;
import com.ovit.manager.modules.nswy.entity.LoginUser;
import com.ovit.manager.modules.nswy.entity.NswyGovInfo;
import com.ovit.manager.modules.nswy.entity.ProxyGovInfo;

import java.util.List;
import java.util.Map;


@MyBatisDao
public interface ProxyGovInfoDao extends CrudDao<ProxyGovInfo> {
    int deleteByPrimaryKey(ProxyGovInfo record);

    int insert(ProxyGovInfo record);

    int insertSelective(ProxyGovInfo record);

    ProxyGovInfo selectByPrimaryKey(ProxyGovInfo record);

    int updateByPrimaryKeySelective(ProxyGovInfo record);

    int updateGovInfo(ProxyGovInfo record);

    void updateLoginUser(ProxyGovInfo record);

    void insertLoginUser(LoginUser record);

    void insertNswyGovInfo(NswyGovInfo record);

    String selectGovInfoId(String loginAccount);

    void insertProxyGovInfo(ProxyGovInfo record);

    List<Map<String,Object>> findLoginAccount(String loginAccount);

    Map<String,Object> findPost(Map<String,Object> map);

    void insertPost(Map<String,Object> map);

    Map<String,Object> findTitle(Map<String,Object> map);

    void saveTitle(Map<String,Object> map);



}