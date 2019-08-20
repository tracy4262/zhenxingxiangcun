package com.ovit.nswy.relationship.service;

import com.github.pagehelper.PageInfo;
import com.ovit.nswy.relationship.model.request.AgencyListQuery;
import com.ovit.nswy.relationship.model.request.AgencyQuery;
import com.ovit.nswy.relationship.model.response.AgencyDTO;


/**
 * Created by ${huipei.x} on 2017-11-2.
 */
public interface AgencyService {

    public PageInfo<AgencyDTO> listAgency(AgencyListQuery agencyListQuery);
    public Object saveAgency(AgencyQuery agencyQuery);
    public Object updateAgency(AgencyQuery agencyQuery);
}
