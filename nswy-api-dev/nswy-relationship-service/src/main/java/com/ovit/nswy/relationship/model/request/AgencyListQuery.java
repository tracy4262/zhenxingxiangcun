package com.ovit.nswy.relationship.model.request;

import lombok.Data;

/**
 * Created by ${huipei.x} on 2017-11-2.
 */
@Data
public class AgencyListQuery extends BaseQuery {
    private Integer agencyType; //0 全部，1 企业，2, 机关，3 乡村
    private String agencyName;

}
