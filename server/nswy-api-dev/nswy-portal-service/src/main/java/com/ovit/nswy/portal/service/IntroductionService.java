package com.ovit.nswy.portal.service;

import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

public interface IntroductionService {
    Map<String,Object> getIntroduction(Map<String, Object> params);

    PageInfo<List<Map<String,Object>>> getHonorInfoOrAptitude(Map<String, Object> params);

    Map<String,Object> getGovBaseInfo(Map<String, Object> params);

    Map<String,Object> getOwnerBaseInfo(Map<String, Object> params);

    Map<String,Object> queryOwnerContact(Map<String, Object> params);

    int                                                                                                                                                                                             queryMyFollow(Map<String, Object> params);

    int queryMyFans(Map<String, Object> params);

    Map<String,Object> getSpecial(Map<String, Object> params);

    List<Map<String,Object>> queryOwnerHonor(Map<String, Object> params);
}
