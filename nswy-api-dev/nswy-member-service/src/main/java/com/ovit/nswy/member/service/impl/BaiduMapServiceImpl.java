package com.ovit.nswy.member.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ovit.nswy.member.mapper.BaiduMapMapper;
import com.ovit.nswy.member.mapper.ProductionBaseMapper;
import com.ovit.nswy.member.service.BaiduMapService;
import com.ovit.nswy.member.service.ProductionBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 地图导航service
 *
 * @author chenqim
 * @create 2018-01-05 12:03
 **/
@Service
public class BaiduMapServiceImpl implements BaiduMapService{
    @Autowired
    private BaiduMapMapper baiduMapMapper;

    @Override
    public List<Map<String, Object>> selectAll(Map<String, Object> params) {
        return baiduMapMapper.selectAll(params);
    }
}
