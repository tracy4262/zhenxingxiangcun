package com.ovit.nswy.member.perfectInfo.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

@Mapper
public interface NetWorkInfoMapper {

    Map<String,Object> getNetworkInfoByAccount(Map<String,Object> params);

    Map<String,Object> findNetworkInfo(Map<String,Object> params);



    void insertNetworkInfo(Map<String,Object> params);


    void updateNetworkInfoName(Map<String,Object> params);

    void updateReal(Map<String,Object> params);

    void updateLoginUserName(Map<String,Object> params);

}
