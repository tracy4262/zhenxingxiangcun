package com.ovit.nswy.portal.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by ${huipei.x} on 2018-2-5.
 */
public interface IndustrialMapper {
    @Select(" select dict_name  as name from system_dict WHERE  dict_id in (${ids}) ")
    @ResultType(String.class)
    List<String> findByIds(@Param("ids") String ids);

}
