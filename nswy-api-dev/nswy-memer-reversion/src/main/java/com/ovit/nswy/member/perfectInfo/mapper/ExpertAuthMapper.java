package com.ovit.nswy.member.perfectInfo.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface ExpertAuthMapper {

    List<Map<String,Object>> find(Map<String, Object> params);

    void save(Map<String, Object> params);

    void delete(Map<String, Object> params);

    Map<String,Object> findExpertInfo(Map<String, Object> params);

}
