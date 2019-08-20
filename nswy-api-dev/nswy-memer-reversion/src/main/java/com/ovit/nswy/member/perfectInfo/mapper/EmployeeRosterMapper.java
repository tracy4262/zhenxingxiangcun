package com.ovit.nswy.member.perfectInfo.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface EmployeeRosterMapper {

    List<Map<String,Object>> findEmployeeRoster(Map<String,Object> params);

    void updateEmployeeRosterName(Map<String,Object> params);

    void saveEmployeeRoster(Map<String,Object> params);

    List<Map<String,Object>> findListOfExtraStaff(Map<String,Object> params);

    void updateListOfExtraStaffName(Map<String,Object> params);

    void saveListOfExtraStaff(Map<String,Object> params);

    void deleteEmployeeRoster(Map<String,Object> params);

    void deleteListOfExtraStaff(Map<String,Object> params);

    void insertLogin(Map<String,Object> params);






}
