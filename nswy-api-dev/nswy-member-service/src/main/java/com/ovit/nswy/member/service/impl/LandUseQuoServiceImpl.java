package com.ovit.nswy.member.service.impl;

import com.ovit.nswy.member.mapper.LandUseQuoMapper;
import com.ovit.nswy.member.service.LandUseQuoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 土地利用现状
 *
 * @author haoWen
 * @create 2017-12-26 09:33
 **/
@Service
public class LandUseQuoServiceImpl implements LandUseQuoService {
    @Autowired
    private LandUseQuoMapper landUseQuoMapper;
    @Override
    public Integer saveLandPlot(Map<String, Object> params) {
        return landUseQuoMapper.saveLandPlot(params);
    }

    @Override
    public Integer deleteLandPlot(Map<String, Object> params) {
        return landUseQuoMapper.deleteLandPlot(params);
    }

    @Override
    public List<Map<String, Object>> queryLandPlot(Map<String, Object> params) {
        return landUseQuoMapper.queryLandPlot(params);
    }


    @Override
    public void updateWaterQualityInfo(Map<String, Object> params) {
        landUseQuoMapper.updateWaterQualityInfo(params);
    }
}
