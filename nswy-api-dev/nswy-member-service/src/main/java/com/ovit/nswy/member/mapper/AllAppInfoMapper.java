package com.ovit.nswy.member.mapper;

import com.ovit.nswy.member.model.AllAppInfo;

import java.util.List;
import java.util.Map;

public interface AllAppInfoMapper {

    List<AllAppInfo> selectAllAppInfo(int level);

    List<AllAppInfo> selectByAccount(Map<String, Object> appInfo);

}