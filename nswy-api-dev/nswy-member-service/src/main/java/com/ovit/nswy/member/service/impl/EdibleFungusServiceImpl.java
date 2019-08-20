package com.ovit.nswy.member.service.impl;

import com.ovit.nswy.member.mapper.EdibleFungusMapper;
import com.ovit.nswy.member.mapper.SoilQualityMapper;
import com.ovit.nswy.member.service.EdibleFungusService;
import com.ovit.nswy.member.service.SoilQualityService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class EdibleFungusServiceImpl implements EdibleFungusService {
    private Logger logger = LogManager.getLogger(this.getClass());

    @Autowired
    private EdibleFungusMapper edibleFungusMapper;

    @Override
    public Map<String, Object> query(Map<String, Object> params) {
        Map<String,Object> map = null;
        try {
            map = edibleFungusMapper.query(params);
        }catch (Exception e){
            logger.error(e);
        }
        return map;
    }

    @Override
    public void update(Map<String, Object> params) {
        try {
            edibleFungusMapper.update(params);
        }catch (Exception e){
            logger.error(e);
        }
    }

    @Override
    public void add(Map<String, Object> params) {
        try {
            edibleFungusMapper.add(params);
        }catch (Exception e){
            logger.error(e);
        }
    }
}
