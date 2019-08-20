package com.ovit.nswy.portal.service.impl;

import com.github.xphsc.mutable.Integers;
import com.github.xphsc.util.ObjectUtil;
import com.github.xphsc.util.StringUtil;
import com.google.common.collect.Maps;
import com.ovit.nswy.portal.mapper.WebsiteMapper;
import com.ovit.nswy.portal.model.request.WebsiteQuery;
import com.ovit.nswy.portal.model.response.WebsiteDTO;
import com.ovit.nswy.portal.service.WebsiteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created by ${huipei.x} on 2018-1-25.
 */
@Service
public class WebsiteServiceImpl implements WebsiteService {

    @Autowired
    private WebsiteMapper websiteMapper;

    @Override
    public Object getWebsite(WebsiteQuery websiteQuery){
        Map params= Maps.newHashMap();
        if(Integers.isNotEmpty(websiteQuery.getQueryType())){
            params.put("queryType",websiteQuery.getQueryType());
        }
       if(StringUtil.isNotBlank(websiteQuery.getAccount())){
           params.put("account",websiteQuery.getAccount());
       }
        Object websiteDTO=  websiteMapper.getWebsite(params);
        if(websiteDTO instanceof WebsiteDTO){
            WebsiteDTO  Website= (WebsiteDTO) websiteDTO;
            if(Website.getStatus().equals("false")){
                Website.setName("");
            }
        }
        return ObjectUtil.isEmpty(websiteDTO)?0:websiteDTO;
    }
}
