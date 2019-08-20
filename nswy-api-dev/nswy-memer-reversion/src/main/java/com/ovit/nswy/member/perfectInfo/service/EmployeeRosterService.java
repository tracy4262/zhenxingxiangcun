package com.ovit.nswy.member.perfectInfo.service;

import java.util.List;
import java.util.Map;

public interface EmployeeRosterService {



    Map<String,Object> findEmployeeRoster(Map<String,Object> params);

    void updateEmployeeRosterName(Map<String,Object> params);

    void saveEmployeeRoster(Map<String,Object> params);

    Map<String,Object> findListOfExtraStaff(Map<String,Object> params);

    void updateListOfExtraStaffName(Map<String,Object> params);

    void saveListOfExtraStaff(Map<String,Object> params);

    void saveTextPreview(Map<String,Object> params);

    void deleteEmployeeRoster(Map<String,Object> params);

    void deleteListOfExtraStaff(Map<String,Object> params);
}
