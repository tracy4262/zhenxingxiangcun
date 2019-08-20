package com.ovit.nswy.relationship.model.request;

/**
 * Created by ${huipei.x} on 2017-11-4.
 */

import lombok.Data;

/**
 * Created by ${huipei.x} on 2017-11-2.
 */
@Data
public class AgencyAuditListQuery extends BaseQuery{
    private String userId;
    private Integer agencyId;

}
