package com.ovit.nswy.member.mapper;

import com.ovit.nswy.member.model.ColumnSettings;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ColumnSettingsMapper {

    int insert(List<ColumnSettings> record);

    void deleteByAccount(String account);

    void deleteByColumn(ColumnSettings record);

    ColumnSettings findColumns(ColumnSettings record);

    List<ColumnSettings> findColumnSet(ColumnSettings record);

    List<ColumnSettings> findByAccount(String account);

    List<ColumnSettings> findColumnSetting(ColumnSettings record);

    int updateByPrimaryKey(ColumnSettings record);

    void updateLabel(ColumnSettings record);

    void updateByColumn(@Param("list") List<ColumnSettings> list);

    void insertByNewVersion(ColumnSettings record);

    void saveContact(Map<String,Object> params);

    List<Map<String, Object>> findContact(Map<String,Object> params);

    void deleteContact(Map<String,Object> params);

}