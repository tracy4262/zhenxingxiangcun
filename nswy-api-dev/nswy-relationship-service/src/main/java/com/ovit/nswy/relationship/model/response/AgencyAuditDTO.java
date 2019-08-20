package com.ovit.nswy.relationship.model.response;

import com.google.common.collect.Maps;
import lombok.Data;

import java.util.Map;

/**
 * Created by ${huipei.x} on 2017-11-2.
 */
@Data
public class AgencyAuditDTO {

    /**
     * 代理机构类型
     */
    private Integer agencyType;

    /**
     * 被代理机构全称
     */
    private String agencyName;

    /**
     * 社会信用统一代码
     */
    private String creditCode;

    /**
     * 法定代表人姓名
     */
    private String legalPerson;
    private Integer auditStatus;
    private Integer auditId;
    private Map agencyTypeInfo= Maps.newHashMap();
    private Map auditStatusInfo= Maps.newHashMap();

}
