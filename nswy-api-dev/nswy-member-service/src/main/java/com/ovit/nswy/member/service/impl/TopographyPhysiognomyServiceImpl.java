package com.ovit.nswy.member.service.impl;

import com.ovit.nswy.member.mapper.TopographyPhysiognomyMapper;
import com.ovit.nswy.member.service.TopographyPhysiognomyService;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 地形地貌service
 *
 * @author haoWen
 * @create 2017-12-25 15:19
 **/
@Service
public class TopographyPhysiognomyServiceImpl implements TopographyPhysiognomyService{

    @Autowired
    private TopographyPhysiognomyMapper topographyPhysiognomyMapper;

    @Override
    public Map<String, Object> queryTopographyPhysiognomy(Map<String, Object> params) {
        Map<String, Object> topographyPhysiognomyMap = topographyPhysiognomyMapper.queryTopographyPhysiognomy(params);
        if (MapUtils.isNotEmpty(topographyPhysiognomyMap)){
            String geographicPosition = MapUtils.getString(topographyPhysiognomyMap,"geographicPosition");
            String topography = MapUtils.getString(topographyPhysiognomyMap,"topography");
            String physiognomy = MapUtils.getString(topographyPhysiognomyMap,"physiognomy");
            String avgElevation = MapUtils.getString(topographyPhysiognomyMap,"avgElevation");
            StringBuffer describe = new StringBuffer();
            if (StringUtils.isNotEmpty(geographicPosition)) {
                describe.append("生产基地所在地").append(geographicPosition);
            }
            if (StringUtils.isNotEmpty(topography)) {
                describe.append("，地形属于").append(topography);
            }
            if (StringUtils.isNotEmpty(physiognomy)) {
                describe.append("，地貌属于").append(physiognomy);
            }
            if (StringUtils.isNotEmpty(avgElevation)) {
                describe.append("，平均海拔").append(avgElevation).append("米/千米");
            }
            topographyPhysiognomyMap.put("describe",describe);
        }
        return topographyPhysiognomyMap;
    }

    @Override
    public Integer saveTopographyPhysiognomy(Map<String, Object> params) {
        return topographyPhysiognomyMapper.saveTopographyPhysiognomy(params);
    }
}
