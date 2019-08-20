package com.ovit.nswy.member.perfectInfo.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface NationalReligionMapper {

    List<Map<String,Object>> find(Map<String, Object> params);

    List<Map<String,Object>> findFaction(Map<String, Object> params);

    void save(List<Map<String, Object>> params);

    void update(Map<String, Object> params);

    void updateStaff(Map<String, Object> params);

    void updateOffStaff(Map<String, Object> params);

    void delete(Map<String, Object> params);

    List<Map<String,Object>> findStaffInfo(Map<String, Object> params);

    List<Map<String,Object>> findOffStaff(Map<String, Object> params);



}
