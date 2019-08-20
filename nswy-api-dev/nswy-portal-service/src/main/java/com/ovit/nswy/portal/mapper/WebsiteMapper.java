package com.ovit.nswy.portal.mapper;

import com.ovit.nswy.portal.core.sql.WebsiteSettingSqlProvider;
import com.ovit.nswy.portal.model.response.WebsiteDTO;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.Map;

/**
 * Created by ${huipei.x} on 2018-1-25.
 */
public interface WebsiteMapper {

    @SelectProvider(type = WebsiteSettingSqlProvider.class,method = "getWebsiteSqlByMap")
    @ResultType(WebsiteDTO.class)
    public WebsiteDTO getWebsite(Map params);

}
