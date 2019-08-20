package com.ovit.nswy.member.service.impl;


import com.ovit.nswy.member.mapper.SpeciesOfFishMapper;
import com.ovit.nswy.member.service.SpeciesOfFishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class SpeciesOfFishServiceImpl implements SpeciesOfFishService{

    @Autowired
    private SpeciesOfFishMapper speciesOfFishMapper;


    /**
     * 获取垂钓中的物种信息
     * @param speciesInfo
     * @return
     */
    @Override
    public List<Map<String, Object>> getSpeciesOfFish(Map<String, Object> speciesInfo) {

        return speciesOfFishMapper.getSpeciesOfFish(speciesInfo);

    }

    /**
     * 新增垂钓中的物种信息
     * @param speciesInfo
     */
    @Override
    public void insertSpeciesOfFish(List<Map<String, Object>> speciesInfo) {

        speciesOfFishMapper.insertSpeciesOfFish(speciesInfo);

    }

    /**
     * 删除物种信息
     * @param speciesInfo 包含用户名、物种ID
     */
    @Override
    public void deleteSpeciesOfFish(Map<String, Object> speciesInfo) {

        speciesOfFishMapper.deleteSpeciesOfFish(speciesInfo);

    }
}
