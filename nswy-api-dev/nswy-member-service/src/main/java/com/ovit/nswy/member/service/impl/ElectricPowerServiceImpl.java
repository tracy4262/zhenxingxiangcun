package com.ovit.nswy.member.service.impl;

import com.ovit.nswy.member.mapper.ElectricPowerMapper;
import com.ovit.nswy.member.service.ElectricPowerService;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Map;

/**
 * 电力条件
 *
 * @author haoWen
 * @create 2017-12-27 10:58
 **/
@Service
public class ElectricPowerServiceImpl implements ElectricPowerService {
    @Autowired
    private ElectricPowerMapper electricPowerMapper;
    @Override
    public int save(Map<String, Object> params) {
        return electricPowerMapper.saveOrUpdate(params);
    }

    @Override
    public Map<String, Object> query(Map<String, Object> params) {
        Map<String, Object> electricPowerMap = electricPowerMapper.query(params);
        //用文字描述电力条件
        if (MapUtils.isNotEmpty(electricPowerMap)) {
            StringBuffer describe = new StringBuffer();
            String transformerSubstation = MapUtils.getString(electricPowerMap,"transformerSubstation");
            String maxPowerSupply = MapUtils.getString(electricPowerMap,"maxPowerSupply");
            String distributionTransformCapacity = MapUtils.getString(electricPowerMap,"distributionTransformCapacity");
            String electricalLoad = MapUtils.getString(electricPowerMap,"electricalLoad");
            String electricalPrice = MapUtils.getString(electricPowerMap,"electricalPrice");
            if (StringUtils.isNotEmpty(transformerSubstation)) {
                describe.append("拥有").append(transformerSubstation).append("KV变电站");
            }
            if (StringUtils.isNotEmpty(maxPowerSupply)) {
                describe.append("，最大供电量").append(maxPowerSupply).append("mw");
            }
            if (StringUtils.isNotEmpty(distributionTransformCapacity)) {
                describe.append("，配变容量").append(distributionTransformCapacity).append("MVA");
            }
            if (StringUtils.isNotEmpty(electricalLoad)) {
                describe.append("，用电负荷").append(electricalLoad).append("mw");
            }if (StringUtils.isNotEmpty(electricalPrice)) {
                describe.append("，用电单价").append(electricalPrice).append("元/度");
            }
            electricPowerMap.put("describe",describe);
        }
        return electricPowerMap;
    }
}
