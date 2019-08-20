package com.ovit.nswy.member.service.impl;

import com.ovit.nswy.member.mapper.ProcessingWaterQualityMapper;
import com.ovit.nswy.member.mapper.SoilQualityMapper;
import com.ovit.nswy.member.service.ProcessingWaterQualityService;
import com.ovit.nswy.member.service.SoilQualityService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class SoilQualityServiceImpl implements SoilQualityService {
    private Logger logger = LogManager.getLogger(this.getClass());

    @Autowired
    private SoilQualityMapper soilQualityMapper;

    @Override
    public Map<String, Object> query(Map<String, Object> params) {
        Map<String,Object> map = null;
        try {
            map = soilQualityMapper.query(params);
        }catch (Exception e){
            logger.error(e);
        }
        return map;
    }

    @Override
    public void update(Map<String, Object> params) {
        try {
            soilQualityMapper.update(params);
        }catch (Exception e){
            logger.error(e);
        }
    }

    @Override
    public void add(Map<String, Object> params) {
        try {
            soilQualityMapper.add(params);
        }catch (Exception e){
            logger.error(e);
        }
    }
}
