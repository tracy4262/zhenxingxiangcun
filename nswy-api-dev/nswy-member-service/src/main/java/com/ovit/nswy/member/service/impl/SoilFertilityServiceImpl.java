package com.ovit.nswy.member.service.impl;

import com.ovit.nswy.member.mapper.SoilFertilityMapper;
import com.ovit.nswy.member.service.SoilFertilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Map;

/**
 * 土壤肥力
 *
 * @author haoWen
 * @create 2017-12-27 11:43
 **/
@Service
public class SoilFertilityServiceImpl implements SoilFertilityService {

    @Autowired
    private SoilFertilityMapper soilFertilityMapper;
    @Override
    public Map<String, Object> query(Map<String, Object> params) {
        return soilFertilityMapper.query(params);
    }

    @Override
    public int save(Map<String, Object> params) {
        return soilFertilityMapper.save(params);
    }
}
