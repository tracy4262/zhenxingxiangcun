package com.ovit.nswy.member.mapper;


import java.util.List;
import java.util.Map;

public interface PlantingGroupManagerMapper {

    Integer doesInclude(Map<String,Object> params);

    void addLoginUser(Map<String,Object> params);

    void addGroup(Map<String,Object> params);

    void addUserFullInfo(Map<String,Object> params);

    void addUserContact(Map<String,Object> params);

    void addContactInfo(Map<String,Object> params);

    void addUserInfo(Map<String,Object> params);

    void addCertification(Map<String,Object> params);

    void addFocus(Map<String,Object> params);

    void addEducation(Map<String,Object> params);

    void addWork(Map<String,Object> params);

    void addPolicial(Map<String,Object> params);

    void addRreligion(Map<String,Object> params);

    void addHonor(Map<String,Object> params);

    List<Map<String,Object>> queryGroups(Map<String,Object> params);

    void removeUser(Integer id);

    Map<String,Object> queryUserById(Map<String,Object> params);

    List<Map<String,Object>> queryMembersFamilyById(Map<String,Object> params);

    List<Map<String,Object>> queryProductOutputById(Map<String,Object> params);

    List<Map<String,Object>> queryProvideServicesById(Map<String,Object> params);

    List<Map<String,Object>> queryIntentionById(Map<String,Object> params);

    void addMembersFamily(Map<String,Object> params);

    void delMembersFamily(Map<String,Object> params);

    void updateMembersFamily(Map<String,Object> params);

    void addProductOutput(Map<String,Object> params);

    void delProductOutput(Map<String,Object> params);

    void updateProductOutput(Map<String,Object> params);

    void addProvideServices(Map<String,Object> params);

    void delProvideServices(Map<String,Object> params);

    void updateProvideServices(Map<String,Object> params);

    void addIntention(Map<String,Object> params);

    void delIntention(Map<String,Object> params);

    void updateIntention(Map<String,Object> params);

    List<Map<String,Object>> getGroups(Map<String,Object> params);

    void updateUserContact(Map<String,Object> params);

    void updateContactInfo(Map<String,Object> params);

    void updateUserInfo(Map<String,Object> params);

    void updateCertification(Map<String,Object> params);

    void updateFocus(Map<String,Object> params);

    String queryUserAccount(String realname);

    List<Map<String,Object>> getGroupIds(Map<String,Object> params);

    List<Map<String,Object>> getGroupUsers(Map<String,Object> params);

    List<Map<String,Object>> getSelectGroup(Map<String,Object> params);

    void delUser(Map<String,Object> params);

}