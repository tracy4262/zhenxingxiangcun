package com.ovit.nswy.portal.mapper;


import com.ovit.nswy.portal.core.sql.ExpertSqlProvider;
import com.ovit.nswy.portal.model.response.ExpertDTO;
import org.apache.ibatis.annotations.*;
import java.util.List;
import java.util.Map;

/**
 * Created by ${huipei.x} on 2018-1-12.
 */
@Mapper
public interface ExpertMapper  {
    @SelectProvider(type = ExpertSqlProvider.class,method = "getExpertSqlByMap")
    @ResultType(ExpertDTO.class)
     List<ExpertDTO> getlist(Map params);
}
