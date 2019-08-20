package com.ovit.nswy.member.service.impl;

import com.ovit.nswy.member.mapper.WaterConditionMapper;
import com.ovit.nswy.member.service.WaterConditionService;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Map;

/**
 * 水利条件
 *
 * @author haoWen
 * @create 2017-12-27 11:18
 **/
@Service
public class WaterConditionServiceImpl implements WaterConditionService {

    @Autowired
    private WaterConditionMapper waterConditionMapper;

    @Override
    public int save(Map<String, Object> params) {
        return waterConditionMapper.save(params);
    }

    @Override
    public Map<String, Object> query(Map<String, Object> params) {
        Map<String, Object> waterConditionMap = waterConditionMapper.query(params);
        if (MapUtils.isNotEmpty(waterConditionMap)) {
            //用文字描述电力条件
            StringBuffer describe = new StringBuffer();
            describe.append("基地生活用水来自（")
                    .append(MapUtils.getString(waterConditionMap,"domesticWater").equals("T") ? "自来水" : "井水")
                    .append("），（")
                    .append(MapUtils.getString(waterConditionMap,"convenient").equals("Y") ? "方便" : "不方便")
                    .append("），（")
                    .append(MapUtils.getString(waterConditionMap,"safe").equals("Y") ? "安全" : "不安全")
                    .append("），");
            String flood = MapUtils.getString(waterConditionMap,"flood");
            if (flood.equals("N")){
                describe.append("不");
            } else if (flood.equals("L")){
                describe.append("很少");
            } else if (flood.equals("O")){
                describe.append("经常");
            }
            describe .append("发生洪灾，");
            String drought = MapUtils.getString(waterConditionMap,"drought");
            if (drought.equals("N")){
                describe.append("不");
            } else if (drought.equals("L")){
                describe.append("很少");
            } else {
                describe.append("经常");
            }
            describe.append("发生旱灾。");
            waterConditionMap.put("describe",describe);
        }
        return waterConditionMap;
    }
}
