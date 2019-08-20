package com.ovit.nswy.member.certification.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface RealCertificationMapper {

    Map<String,Object> findList(Map<String,Object> params);

    Map<String,Object> findByLoginUser(Map<String,Object> params);

    Integer findCount(Map<String,Object> params);

    void updateLoginUser(Map<String,Object> params);

    void saveRealCertification(Map<String,Object> params);

    void deleteRealCertification(Map<String,Object> params);

    //会员资质信息
    List<Map<String,Object>> findMemberAptitude(Map<String,Object> params);

    void updateMemberAptitude(Map<String,Object> params);

    void saveMemberAptitude(Map<String,Object> params);

    void deleteMemberAptitude(Map<String,Object> params);

    //会员联系方式
    List<Map<String,Object>> findMemberContact(Map<String,Object> params);

    List<Map<String,Object>> findMemberContactOfByAptitude(Map<String,Object> params);

    void updateMemberContact(Map<String,Object> params);

    void saveMemberContact(Map<String,Object> params);

    void deleteMemberContact(Map<String,Object> params);

    //法人/个人身份
    List<Map<String,Object>> findMemberIdentity(Map<String,Object> params);

    void updateMemberIdentity(Map<String,Object> params);

    void saveMemberIdentity(Map<String,Object> params);

    void deleteMemberIdentity(Map<String,Object> params);


    List<Map<String,Object>> findMemberAdministrator(Map<String,Object> params);

    List<Map<String,Object>> findMemberAdministratorOfByIdentity(Map<String,Object> params);

    void updateMemberAdministrator(Map<String,Object> params);

    void saveMemberAdministrator(Map<String,Object> params);

    void deleteMemberAdministrator(Map<String,Object> params);

    List<Map<String,Object>> neighborList(Map<String,Object> params);

    List<Map<String,Object>> getMemberType(Map<String,Object> params);

}
