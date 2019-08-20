package com.ovit.nswy.member.service.impl;

import com.ovit.nswy.member.mapper.LandPhysicalMapper;
import com.ovit.nswy.member.service.LandPhysicalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 土壤信息-土壤物理性状指标
 *
 * @author haoWen
 * @create 2017-12-26 14:15
 **/
@Service
public class LandPhysicalServiceImpl implements LandPhysicalService {
    @Autowired
    private LandPhysicalMapper landPhysicalMapper;
    @Override
    public Integer saveOrUpdateLandInfoPhysical(Map<String, Object> params) {
        return landPhysicalMapper.saveOrUpdateLandInfoPhysical(params);
    }

    @Override
    public Map<String, Object> queryLandInfoPhysical(Map<String, Object> params) {
        return landPhysicalMapper.queryLandInfoPhysical(params);
    }

    @Override
    public Map<String, Object> queryIsExit(Map<String, Object> map) {
        return landPhysicalMapper.queryIsExit(map);
    }
}
