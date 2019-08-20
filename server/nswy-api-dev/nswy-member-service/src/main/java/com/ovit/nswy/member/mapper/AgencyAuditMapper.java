package com.ovit.nswy.member.mapper;

import com.ovit.nswy.member.model.AgencyAudit;

public interface AgencyAuditMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AgencyAudit record);

    AgencyAudit selectByPrimaryKey(Integer id);

    int updateByPrimaryKey(AgencyAudit record);
}