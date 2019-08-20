package com.ovit.nswy.member.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ovit.nswy.member.mapper.AirQualityMapper;
import com.ovit.nswy.member.mapper.CardMapper;
import com.ovit.nswy.member.model.Card;
import com.ovit.nswy.member.service.AirQualityService;
import com.ovit.nswy.member.service.CardService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class AirQualityServiceImpl implements AirQualityService {
    private Logger logger = LogManager.getLogger(this.getClass());

    @Autowired
    private AirQualityMapper airQualityMapper;

    @Override
    public Map<String, Object> query(Map<String, Object> params) {
        Map<String,Object> map = null;
        try {
            map = airQualityMapper.query(params);
        }catch (Exception e){
            logger.error(e);
        }
        return map;
    }

    @Override
    public void update(Map<String, Object> params) {
        try {
            airQualityMapper.update(params);
        }catch (Exception e){
            logger.error(e);
        }
    }

    @Override
    public void add(Map<String, Object> params) {
        try {
            airQualityMapper.add(params);
        }catch (Exception e){
            logger.error(e);
        }
    }
}
