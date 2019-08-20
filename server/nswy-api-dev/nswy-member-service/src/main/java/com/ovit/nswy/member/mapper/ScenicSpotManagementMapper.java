package com.ovit.nswy.member.mapper;

import java.util.List;
import java.util.Map;

public interface ScenicSpotManagementMapper {



    List<Map<String,Object>> findScenicSpotList(Map<String, Object> params);

    List<Map<String,Object>> findList(Map<String, Object> params);

    Integer findListCount(Map<String, Object> params);

    void insertScenicSpot(Map<String, Object> params);

    void updateScenicSpot(Map<String, Object> params);

    void deleteScenicSpot(Map<String, Object> params);
}
