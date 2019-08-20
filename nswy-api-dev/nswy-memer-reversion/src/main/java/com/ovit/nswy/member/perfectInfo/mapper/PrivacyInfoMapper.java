package com.ovit.nswy.member.perfectInfo.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface PrivacyInfoMapper {


    Map<String,Object> findPrivacyInfo(Map<String,Object> params);

    void insertPrivacyInfo(Map<String,Object> params);

    void updateByLoginUserAvatar(Map<String,Object> params);

    void updateMemberVirtual(Map<String,Object> params);

    void updatePrivacyInfoName(Map<String,Object> params);



}
