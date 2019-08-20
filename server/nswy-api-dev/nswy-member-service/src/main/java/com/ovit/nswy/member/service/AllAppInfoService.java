package com.ovit.nswy.member.service;

import com.ovit.nswy.member.model.AllAppInfo;
import com.ovit.nswy.member.model.MyApp;

import java.util.List;
import java.util.Map;

public interface AllAppInfoService {

    List<AllAppInfo> selectAllAppInfo(int level);

    List<AllAppInfo> selectByAccount(Map<String, Object> appInfo);
}