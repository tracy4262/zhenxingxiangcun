package com.ovit.nswy.relationship.mapper;



import com.ovit.nswy.frame.base.BaseMapper;
import com.ovit.nswy.relationship.model.AgencyAudit;
import com.ovit.nswy.relationship.model.response.AgencyAuditDTO;


import java.util.List;
import java.util.Map;


public interface AgencyAuditMapper extends BaseMapper<AgencyAudit> {

    List<AgencyAuditDTO> findAgencyAuditDetail(Map params);
}