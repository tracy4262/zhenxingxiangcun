package com.ovit.nswy.portal.service;

import com.ovit.nswy.portal.model.request.WebsiteQuery;
import com.ovit.nswy.portal.model.response.WebsiteDTO;

/**
 * Created by ${huipei.x} on 2018-1-25.
 */
public interface WebsiteService {

    public Object getWebsite(WebsiteQuery websiteQuery);
}
