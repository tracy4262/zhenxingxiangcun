package com.ovit.nswy.member.perfectInfo.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface LandSituationMapper {

    List<Map<String,Object>> find(Map<String, Object> params);

    void save(List<Map<String, Object>> params);

    void delete(Map<String, Object> params);

    List<Map<String,Object>> dict(Map<String, Object> params);

}
