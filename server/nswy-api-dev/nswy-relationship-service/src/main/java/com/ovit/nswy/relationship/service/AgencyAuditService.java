package com.ovit.nswy.relationship.service;


import com.github.pagehelper.PageInfo;
import com.ovit.nswy.relationship.model.request.AgencyAuditListQuery;
import com.ovit.nswy.relationship.model.request.AgencyAuditQuery;
import com.ovit.nswy.relationship.model.response.AgencyAuditDTO;


/**
 * Created by ${huipei.x} on 2017-11-2.
 */
public interface AgencyAuditService {

    public PageInfo<AgencyAuditDTO> listAgencyAudit(AgencyAuditListQuery agencyAuditListQuery);
    public Integer  saveAgencyAudit(AgencyAuditQuery agencyAuditQuery);
    public Integer updateAuditStatus(Integer auditId, Integer auditStatus);
}
