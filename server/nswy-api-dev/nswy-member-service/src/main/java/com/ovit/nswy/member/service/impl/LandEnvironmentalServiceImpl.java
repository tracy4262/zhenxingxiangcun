package com.ovit.nswy.member.service.impl;

import com.ovit.nswy.member.mapper.LandEnvironmentalMapper;
import com.ovit.nswy.member.service.LandEnvironmentalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 土壤环境指标信息
 *
 * @author haoWen
 * @create 2017-12-26 15:03
 **/
@Service
public class LandEnvironmentalServiceImpl implements LandEnvironmentalService {
    @Autowired
    private LandEnvironmentalMapper landEnvironmentalMapper;

    @Override
    public Map<String, Object> queryLandInfoEnvironmental(Map<String, Object> params) {
        return landEnvironmentalMapper.queryLandInfoEnvironmental(params);
    }

    @Override
    public Integer saveOrUpdateLandInfoEnvironmental(Map<String, Object> params) {
        return landEnvironmentalMapper.saveOrUpdateLandInfoEnvironmental(params);
    }

    @Override
    public Map<String, Object> queryIsExit(Map<String, Object> map) {
        return landEnvironmentalMapper.queryIsExit(map);
    }
}
