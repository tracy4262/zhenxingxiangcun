package com.ovit.nswy.member.userManage.service;

import java.util.List;
import java.util.Map;

public interface UserRealCertificationService {


    List<Map<String,Object>> findMemberAptitude(Map<String, Object> params);

    void updateMemberAptitude(Map<String, Object> params);

    void saveMemberAptitude(Map<String, Object> params);

    String deleteMemberAptitude(Map<String, Object> params);

    List<Map<String,Object>> findMemberContact(Map<String, Object> params);

    List<Map<String,Object>> findMemberContactOfByAptitude(Map<String, Object> params);

    void updateMemberContact(Map<String, Object> params);

    void saveMemberContact(Map<String, Object> params);

    void deleteMemberContact(Map<String, Object> params);

    List<Map<String,Object>> findMemberIdentity(Map<String, Object> params);

    void updateMemberIdentity(Map<String, Object> params);

    void saveMemberIdentity(Map<String, Object> params);

    String deleteMemberIdentity(Map<String, Object> params);

    List<Map<String,Object>> findMemberAdministrator(Map<String, Object> params);

    List<Map<String,Object>> findMemberAdministratorOfByIdentity(Map<String, Object> params);

    void updateMemberAdministrator(Map<String, Object> params);

    void saveMemberAdministrator(Map<String, Object> params);

    void deleteMemberAdministrator(Map<String, Object> params);

    String findRealCertificationEnd(Map<String, Object> params);

}
