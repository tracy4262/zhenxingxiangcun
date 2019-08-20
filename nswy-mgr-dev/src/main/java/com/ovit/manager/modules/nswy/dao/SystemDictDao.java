package com.ovit.manager.modules.nswy.dao;

import com.ovit.manager.common.persistence.CrudDao;
import com.ovit.manager.common.persistence.annotation.MyBatisDao;
import com.ovit.manager.modules.nswy.entity.SystemDictInfo;
import java.util.List;

@MyBatisDao
public interface SystemDictDao extends CrudDao<SystemDictInfo> {
    List<String> queryByDictId(List<String> strings);
}
