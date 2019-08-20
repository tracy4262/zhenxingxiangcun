package com.ovit.nswy.member.service.impl;

import com.ovit.nswy.member.mapper.AirQualityMapper;
import com.ovit.nswy.member.mapper.WaterQualityMapper;
import com.ovit.nswy.member.service.AirQualityService;
import com.ovit.nswy.member.service.WaterQualityService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class WaterQualityServiceImpl implements WaterQualityService {
    private Logger logger = LogManager.getLogger(this.getClass());

    @Autowired
    private WaterQualityMapper waterQualityMapper;

    @Override
    public Map<String, Object> query(Map<String, Object> params) {
        Map<String,Object> map = null;
        try {
            map = waterQualityMapper.query(params);
        }catch (Exception e){
            logger.error(e);
        }
        return map;
    }

    @Override
    public void update(Map<String, Object> params) {
        try {
            waterQualityMapper.update(params);
        }catch (Exception e){
            logger.error(e);
        }
    }

    @Override
    public void add(Map<String, Object> params) {
        try {
            waterQualityMapper.add(params);
        }catch (Exception e){
            logger.error(e);
        }
    }
}
