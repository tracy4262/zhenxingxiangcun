package com.ovit.nswy.portal.service;

import com.ovit.nswy.portal.model.request.ContoctQuery;
import com.ovit.nswy.portal.model.response.ContoctDTO;

/**
 * Created by ${huipei.x} on 2018-1-12.
 */
public interface ContoctService {

    public ContoctDTO getContoct(ContoctQuery contoctQuery);
}
