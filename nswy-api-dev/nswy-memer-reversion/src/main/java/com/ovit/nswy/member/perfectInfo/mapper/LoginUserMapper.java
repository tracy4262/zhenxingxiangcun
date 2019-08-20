package com.ovit.nswy.member.perfectInfo.mapper;

import com.ovit.nswy.member.certification.model.LoginUser;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

@Mapper
public interface LoginUserMapper {

    Map<String,Object> findByLoginName(Map<String,Object> params);

    void updateIdentityFlag(Map<String,Object> params);

    void updateIsRealIdentity(Map<String,Object> params);

    void updatePassWord(Map<String,Object> params);

    LoginUser getLoginName(String loginName);

    Map<String,Object> findStep(Map<String,Object> params);

    void saveOrUpdateStep(Map<String,Object> params);

    Map<String,Object> findEnableStep(Map<String,Object> params);



}
