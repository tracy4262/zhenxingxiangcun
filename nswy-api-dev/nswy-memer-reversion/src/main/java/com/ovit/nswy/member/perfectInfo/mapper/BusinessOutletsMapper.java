package com.ovit.nswy.member.perfectInfo.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface BusinessOutletsMapper {

    List<Map<String,Object>> findBusinessOutletsInfo(Map<String, Object> params);

    void saveOrUpdateBusinessOutlets(Map<String, Object> params);

    void deleteBusinessOutlets(Map<String, Object> params);

}
