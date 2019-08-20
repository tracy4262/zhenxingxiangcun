package com.ovit.nswy.member.service.impl;

import com.ovit.nswy.member.mapper.LandWaterQualityReportMapper;
import com.ovit.nswy.member.service.LandWaterQualityReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 水质信息检测报告service
 *
 * @author haoWen
 * @create 2018-01-04 16:52
 **/
@Service
public class LandWaterQualityReportServiceImpl implements LandWaterQualityReportService {

    @Autowired
    private LandWaterQualityReportMapper landWaterQualityReportMapper;
    @Override
    public int uploadReport(Map<String, Object> map) {
        return landWaterQualityReportMapper.uploadReport(map);
    }

    @Override
    public List<Map<String,Object>> query(Map<String, Object> params) {
        return landWaterQualityReportMapper.query(params);
    }

    @Override
    public void delete(Map<String, Object> map) {
        landWaterQualityReportMapper.delete(map);
    }
}
