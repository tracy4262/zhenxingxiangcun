package com.ovit.nswy.member.certification.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface ColumnSettingMapper {

    List<Map<String, Object>> findColumnSettingByAccount(Map<String,Object> params);

    void deleteSettingByAccount(Map<String,Object> params);

    void saveSettingInfo(List<Map<String,Object>> settingList);

    List<Map<String, Object>> findColumnDict(int id);

}
