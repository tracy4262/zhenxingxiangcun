package com.ovit.nswy.member.service.impl;

import com.ovit.nswy.member.mapper.AgencyAuditMapper;
import com.ovit.nswy.member.model.AgencyAudit;
import com.ovit.nswy.member.service.AgencyAuditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AgencyAuditServiceImpl implements AgencyAuditService {

    @Autowired
    private AgencyAuditMapper agencyAudit;

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return agencyAudit.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(AgencyAudit record) {
        return agencyAudit.insert(record);
    }

    @Override
    public AgencyAudit selectByPrimaryKey(Integer id) {
        return agencyAudit.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKey(AgencyAudit record) {
        return agencyAudit.updateByPrimaryKey(record);
    }

}
