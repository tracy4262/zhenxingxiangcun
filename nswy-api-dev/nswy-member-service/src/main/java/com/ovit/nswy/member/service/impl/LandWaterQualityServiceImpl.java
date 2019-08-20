package com.ovit.nswy.member.service.impl;

import com.ovit.nswy.member.mapper.LandWaterQualityMapper;
import com.ovit.nswy.member.service.LandWaterQualityService;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Map;

/**
 * 地块水质信息
 *
 * @author haoWen
 * @create 2017-12-26 16:25
 **/
@Service
public class LandWaterQualityServiceImpl implements LandWaterQualityService {
    @Autowired
    private LandWaterQualityMapper landWaterQualityMapper;
    @Override
    public Integer saveOrUpdateLandWaterQuality(Map<String, Object> params) {
        return landWaterQualityMapper.saveOrUpdateLandWaterQuality(params);
    }

    @Override
    public Map<String, Object> queryLandWaterQuality(Map<String, Object> params) {
        Map<String, Object> map = landWaterQualityMapper.queryLandWaterQuality(params);
        if (MapUtils.isNotEmpty(map)) {
            String waterPHStatus = null;
            StringBuffer describe = new StringBuffer("水质质量");
            String waterPH = MapUtils.getString(map,"waterPH");
            if (StringUtils.isNotEmpty(waterPH)){
                double doubleWaterPH = Double.parseDouble(waterPH);
                if (doubleWaterPH < 6.8 && doubleWaterPH > 8.5){
                    waterPHStatus = "劣于";
                } else if (doubleWaterPH > 6.8 && doubleWaterPH <8.5) {
                    waterPHStatus = "达到";
                } else {
                    waterPHStatus = "优于";
                }
            }
            String cyanideStatus = checkDoubleStatus(MapUtils.getString(map,"cyanide"),0.05);
            String fluorideStatus = checkDoubleStatus(MapUtils.getString(map,"fluoride"),1.5);
            String chlorideStatus = checkDoubleStatus(MapUtils.getString(map,"chloride"),250);
            String arsenicStatus = checkDoubleStatus(MapUtils.getString(map,"arsenic"),0.05);
            String mercuryStatus = checkDoubleStatus(MapUtils.getString(map,"mercury"),0.001);
            String leadStatus = checkDoubleStatus(MapUtils.getString(map,"lead"),0.05);
            String cadmiumStatus = checkDoubleStatus(MapUtils.getString(map,"cadmium"),0.01);
            String chromiumStatus = checkDoubleStatus(MapUtils.getString(map,"chromium"),0.05);
            String fecalColiformStatus = checkDoubleStatus(MapUtils.getString(map,"fecalColiform"),10);

            if (
                    "达到".equals(waterPHStatus)
                    && "达到".equals(cyanideStatus)
                    && "达到".equals(fluorideStatus)
                    && "达到".equals(chlorideStatus)
                    && "达到".equals(arsenicStatus)
                    && "达到".equals(mercuryStatus)
                    && "达到".equals(leadStatus)
                    && "达到".equals(cadmiumStatus)
                    && "达到".equals(chromiumStatus)
                    && "达到".equals(fecalColiformStatus)
                    ){
                describe.append("达到");
            }else if (
                    "劣于".equals(waterPHStatus)
                    && "劣于".equals(cyanideStatus)
                    && "劣于".equals(fluorideStatus)
                    && "劣于".equals(chlorideStatus)
                    && "劣于".equals(arsenicStatus)
                    && "劣于".equals(mercuryStatus)
                    && "劣于".equals(leadStatus)
                    && "劣于".equals(cadmiumStatus)
                    && "劣于".equals(chromiumStatus)
                    && "劣于".equals(fecalColiformStatus)
                    ) {
                describe.append("劣于");
            } else {
                describe.append("优于");
            }

            describe.append("国家加工用水水质基本控制项目标准值(GB5084-2005)");
            map.put("describe",describe);
        }
        return map;
    }

    private String checkDoubleStatus(String params,double number){
        String status = "";
        if (StringUtils.isNotEmpty(params)) {
            if (StringUtils.isNotEmpty(params)){
                double parseDouble = Double.parseDouble(params);
                if (parseDouble < number){
                    status = "劣于";
                } else if (parseDouble > number) {
                    status = "达到";
                } else {
                    status = "优于";
                }
            }
        }
        return status;
    }
}