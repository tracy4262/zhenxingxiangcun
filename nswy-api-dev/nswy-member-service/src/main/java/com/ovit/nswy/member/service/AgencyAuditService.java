package com.ovit.nswy.member.service;

import com.ovit.nswy.member.model.AgencyAudit;

public interface AgencyAuditService {
	int deleteByPrimaryKey(Integer id);

    int insert(AgencyAudit record);

    AgencyAudit selectByPrimaryKey(Integer id);

    int updateByPrimaryKey(AgencyAudit record);
}
