package com.ovit.nswy.member.service;

import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

public interface ScenicSpotManagementService {

    PageInfo<Map<String,Object>> findScenicSpotList(Map<String,Object> params);

    String insertScenicSpot(Map<String,Object> params);

    String updateScenicSpot(Map<String,Object> params);

    void deleteScenicSpot(Map<String,Object> params);

}
