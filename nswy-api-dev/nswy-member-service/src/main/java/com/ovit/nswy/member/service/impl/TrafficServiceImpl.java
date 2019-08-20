package com.ovit.nswy.member.service.impl;

import com.ovit.nswy.member.mapper.SoilQualityMapper;
import com.ovit.nswy.member.mapper.TrafficMapper;
import com.ovit.nswy.member.service.SoilQualityService;
import com.ovit.nswy.member.service.TrafficService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class TrafficServiceImpl implements TrafficService {
    private Logger logger = LogManager.getLogger(this.getClass());

    @Autowired
    private TrafficMapper trafficMapper;

    @Override
    public Map<String, Object> query(Map<String, Object> params) {
        Map<String,Object> map = null;
        try {
            map = trafficMapper.query(params);
        }catch (Exception e){
            logger.error(e);
        }
        return map;
    }

    @Override
    public Map<String,Object> detail(Map<String, Object> params) {
        Map<String,Object> map = null;
        try {
            map = trafficMapper.detail(params);
        }catch (Exception e){
            logger.error(e);
        }
        return map;
    }
}
