package com.ovit.nswy.member.service.impl;

import com.ovit.nswy.member.mapper.FisheriesWaterQualityMapper;
import com.ovit.nswy.member.mapper.LivestockWaterQualityMapper;
import com.ovit.nswy.member.service.FisheriesWaterQualityService;
import com.ovit.nswy.member.service.LivestockWaterQualityService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class LivestockWaterQualityServiceImpl implements LivestockWaterQualityService {
    private Logger logger = LogManager.getLogger(this.getClass());

    @Autowired
    private LivestockWaterQualityMapper livestockWaterQualityMapper;

    @Override
    public Map<String, Object> query(Map<String, Object> params) {
        Map<String,Object> map = null;
        try {
            map = livestockWaterQualityMapper.query(params);
        }catch (Exception e){
            logger.error(e);
        }
        return map;
    }

    @Override
    public void update(Map<String, Object> params) {
        try {
            livestockWaterQualityMapper.update(params);
        }catch (Exception e){
            logger.error(e);
        }
    }

    @Override
    public void add(Map<String, Object> params) {
        try {
            livestockWaterQualityMapper.add(params);
        }catch (Exception e){
            logger.error(e);
        }
    }
}
