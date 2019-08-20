package com.ovit.nswy.member.service.impl;

import com.ovit.nswy.member.mapper.LandBiologicalMapper;
import com.ovit.nswy.member.service.LandBiologicalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 土壤生物学指标
 *
 * @author haoWen
 * @create 2017-12-26 14:40
 **/
@Service
public class LandBiologicalServiceImpl implements LandBiologicalService {
    @Autowired
    private LandBiologicalMapper landBiologicalMapper;
    @Override
    public Integer saveOrUpdateLandInfoBiological(Map<String, Object> params) {
        return landBiologicalMapper.saveOrUpdateLandInfoBiological(params);
    }

    @Override
    public Map<String, Object> queryLandInfoBiological(Map<String, Object> params) {
        return landBiologicalMapper.queryLandInfoBiological(params);
    }

    @Override
    public Map<String, Object> queryIsExit(Map<String, Object> map) {
        return landBiologicalMapper.queryIsExit(map);
    }
}
