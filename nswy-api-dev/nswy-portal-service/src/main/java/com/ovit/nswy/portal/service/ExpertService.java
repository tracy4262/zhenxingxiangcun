package com.ovit.nswy.portal.service;

import com.github.pagehelper.PageInfo;
import com.ovit.nswy.portal.model.request.ExpertQuery;
import com.ovit.nswy.portal.model.response.ContoctDTO;
import com.ovit.nswy.portal.model.response.ExpertDTO;

/**
 * Created by ${huipei.x} on 2018-1-12.
 */
public interface ExpertService {

    PageInfo<ExpertDTO> listExpert(ExpertQuery ExpertQuery);
}
