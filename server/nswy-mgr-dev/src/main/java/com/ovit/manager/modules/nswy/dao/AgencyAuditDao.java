package com.ovit.manager.modules.nswy.dao;

import com.ovit.manager.common.persistence.CrudDao;
import com.ovit.manager.common.persistence.annotation.MyBatisDao;
import com.ovit.manager.modules.nswy.entity.AgencyAudit;
@MyBatisDao
public interface AgencyAuditDao  extends CrudDao<AgencyAudit> {
    int deleteByPrimaryKey(Integer id);

    int insert(AgencyAudit record);

    int insertSelective(AgencyAudit record);

    AgencyAudit selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AgencyAudit record);

    int updateByPrimaryKey(AgencyAudit record);

}