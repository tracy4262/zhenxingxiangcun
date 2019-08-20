package com.ovit.nswy.member.service.impl;

import com.ovit.nswy.member.mapper.LandNutritiveMapper;
import com.ovit.nswy.member.service.LandNutritiveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 土壤营养化学指标
 *
 * @author haoWen
 * @create 2017-12-26 11:59
 **/
@Service
public class LandNutritiveServiceImpl implements LandNutritiveService {
    @Autowired
    private LandNutritiveMapper landNutritiveMapper;
    @Override
    public Integer saveOrUpdateLandInfoNutritive(Map<String, Object> params) {
        return landNutritiveMapper.saveOrUpdateLandInfoNutritive(params);
    }

    @Override
    public Map<String, Object> queryLandInfoNutritive(Map<String, Object> params) {
        return landNutritiveMapper.queryLandInfoNutritive(params);
    }

    @Override
    public Map<String, Object> queryIsExit(Map<String, Object> map) {
        return landNutritiveMapper.queryIsExit(map);
    }
}
