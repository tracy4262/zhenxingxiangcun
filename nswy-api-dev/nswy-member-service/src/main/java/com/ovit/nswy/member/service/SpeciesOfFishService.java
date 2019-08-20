package com.ovit.nswy.member.service;

import java.util.List;
import java.util.Map;


public interface SpeciesOfFishService {

    /**
     * 获取垂钓中的物种信息
     * @param speciesInfo
     * @return
     */
    List<Map<String,Object>> getSpeciesOfFish(Map<String, Object> speciesInfo);

    /**
     * 新增垂钓中的物种信息
     * @param speciesInfo
     */
    void insertSpeciesOfFish(List<Map<String, Object>> speciesInfo);

    /**
     * 删除物种信息
     * @param speciesInfo 包含用户名、物种ID
     */
    void deleteSpeciesOfFish(Map<String,Object> speciesInfo);
}
