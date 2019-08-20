package com.ovit.nswy.member.service.impl;

import com.ovit.nswy.member.mapper.LandSoilMicroelementMapper;
import com.ovit.nswy.member.service.LandSoilMicroelementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Map;

/**
 * 土壤微量元素含量
 *
 * @author haoWen
 * @create 2017-12-26 15:27
 **/
@Service
public class LandSoilMicroelementServiceImpl implements LandSoilMicroelementService{
    @Autowired
    private LandSoilMicroelementMapper landSoilMicroelementMapper;
    @Override
    public Integer saveOrUpdateLandInfoMicroelements(Map<String, Object> params) {
        return landSoilMicroelementMapper.saveOrUpdateLandInfoMicroelements(params);
    }

    @Override
    public Map<String, Object> queryLandInfoMicroelements(Map<String, Object> params) {
        return landSoilMicroelementMapper.queryLandInfoMicroelements(params);
    }

    @Override
    public Map<String, Object> queryIsExit(Map<String, Object> map) {
        return landSoilMicroelementMapper.queryIsExit(map);
    }
}
