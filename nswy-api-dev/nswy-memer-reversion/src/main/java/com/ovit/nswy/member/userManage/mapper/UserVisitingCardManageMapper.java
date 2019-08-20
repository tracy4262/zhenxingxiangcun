package com.ovit.nswy.member.userManage.mapper;



import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface UserVisitingCardManageMapper {

    Map<String,Object> findRegisterInfoByaccount(Map<String, Object> params);

    Map<String,Object> findByLoginUser(Map<String, Object> params);


    void saveRegisterInfo(Map<String, Object> params);

    void updateRegisterInfo(Map<String, Object> params);


    //会员资质信息
    List<Map<String,Object>> findMemberAptitude(Map<String,Object> params);

    void updateMemberAptitude(Map<String,Object> params);


    //会员联系方式
    List<Map<String,Object>> findMemberContact(Map<String,Object> params);



    void updateMemberContact(Map<String,Object> params);


    //法人/个人身份
    List<Map<String,Object>> findMemberIdentity(Map<String,Object> params);

    void updateMemberIdentity(Map<String,Object> params);

    List<Map<String,Object>> findMemberAdministrator(Map<String,Object> params);

    void updateMemberAdministrator(Map<String,Object> params);




}
