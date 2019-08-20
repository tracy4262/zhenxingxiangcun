package com.ovit.nswy.relationship.model.request;

import lombok.Data;

/**
 * Created by ${huipei.x} on 2017-11-2.
 */
@Data
public class AgencyAuditQuery {

    /**
     * 审核人
     */
    private String auditor;

    /**
     * 代理机构Id
     */
    private Integer agencyId;

    /**
     * 审核状态 0未审核  1已审核 2审核拒绝
     */
    private Integer auditStatus;

}
