package com.ovit.nswy.member.perfectInfo.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface AdministrationDivisionMapper {

    List<Map<String,Object>> findDepartmentInfo(Map<String,Object> params);

    void updateDepartmentInfoName(Map<String,Object> params);

    void saveDepartmentInfo(Map<String,Object> params);

    List<Map<String,Object>> findAdministrativeDivision(Map<String,Object> params);

    void updateAdministrativeDivisionName(Map<String,Object> params);

    void saveAdministrativeDivision(Map<String,Object> params);

    List<Map<String,Object>> findManagerialStaff(Map<String,Object> params);

    void updateManagerialStaffName(Map<String,Object> params);

    void saveManagerialStaff(Map<String,Object> params);

    void deleteDepartMentInfo(Map<String,Object> params);

    void deleteAdministrativeDivision(Map<String,Object> params);

    void deleteManagerialStaff(Map<String,Object> params);



}
