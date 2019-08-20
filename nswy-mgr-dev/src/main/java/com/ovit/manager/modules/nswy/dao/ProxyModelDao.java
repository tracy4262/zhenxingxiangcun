package com.ovit.manager.modules.nswy.dao;

import com.ovit.manager.common.persistence.CrudDao;
import com.ovit.manager.common.persistence.annotation.MyBatisDao;
import com.ovit.manager.modules.nswy.entity.ProxyModelInfo;

import java.util.List;
import java.util.Map;


@MyBatisDao
public interface ProxyModelDao extends CrudDao<ProxyModelInfo> {

    List<Map<String,Object>> findFileType(Map<String,Object> params);

    void saveProxyModel(Map<String,Object> params);

    ProxyModelInfo findByProxyModel(String fileId);

    void updateProxyModelInfo(Map<String,Object> params);

    void deleteProxyModelInfo(String fileId);

}
