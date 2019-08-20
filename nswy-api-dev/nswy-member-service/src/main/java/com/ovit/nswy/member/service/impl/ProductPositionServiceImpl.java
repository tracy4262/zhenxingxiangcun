package com.ovit.nswy.member.service.impl;

import com.ovit.nswy.member.mapper.ProductPositionMapper;
import com.ovit.nswy.member.service.ProductPositionService;
import com.ovit.nswy.member.service.ProductionBaseService;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * 地理位置service
 *
 * @author haoWen
 * @create 2017-12-25 15:19
 **/
@Service
public class ProductPositionServiceImpl implements ProductPositionService {

    @Autowired
    private ProductPositionMapper productPositionMapper;

    @Autowired
    private ProductionBaseService productionBaseService;


    @Override
    public Map<String, Object> queryGeographicalPosition(Map<String, Object> params) {

        Map<String, Object> geographicalPositionMap = geographicalPositionMap = productPositionMapper.queryGeographicalPosition(params);
        //拼接前端展示的汇总描述
        StringBuffer describe = new StringBuffer();
        if (MapUtils.isNotEmpty(geographicalPositionMap)){
            String geographicPosition = MapUtils.getString(geographicalPositionMap,"geographicPosition");
            String eastCoordinate = MapUtils.getString(geographicalPositionMap,"eastCoordinate");
            String westCoordinate = MapUtils.getString(geographicalPositionMap,"westCoordinate");
            String southCoordinate = MapUtils.getString(geographicalPositionMap,"southCoordinate");
            String northCoordinate = MapUtils.getString(geographicalPositionMap,"northCoordinate");
            String centerCoordinate = MapUtils.getString(geographicalPositionMap,"centerCoordinate");
            String eastWestLength = MapUtils.getString(geographicalPositionMap,"eastWestLength");
            String southNorthLength = MapUtils.getString(geographicalPositionMap,"southNorthLength");
            String landArea = MapUtils.getString(geographicalPositionMap,"landArea");

            if (StringUtils.isNotEmpty(geographicPosition)) {
                describe.append("生产基地位于").append(geographicPosition);
            }
            if (StringUtils.isNotEmpty(eastCoordinate)) {
                describe.append("，最东坐标为：").append(eastCoordinate);
            }
            if (StringUtils.isNotEmpty(westCoordinate)) {
                describe.append("，最西点坐标").append(westCoordinate);
            }
            if (StringUtils.isNotEmpty(southCoordinate)) {
                describe.append("，最南点坐标").append(southCoordinate);
            }
            if (StringUtils.isNotEmpty(northCoordinate)) {
                describe.append("，最北点坐标").append(northCoordinate);
            }
            if (StringUtils.isNotEmpty(centerCoordinate)) {
                describe.append("，中间点坐标点").append(centerCoordinate);
            }
            if (StringUtils.isNotEmpty(eastWestLength)) {
                describe.append("，东西长").append(eastWestLength).append("米");
            }
            if (StringUtils.isNotEmpty(southNorthLength)) {
                describe.append("，南北宽").append(southNorthLength).append("米");
            }
            if (StringUtils.isNotEmpty(landArea)) {
                describe.append("，总土地面积").append(landArea).append("平方米");
            }
            geographicalPositionMap.put("describe",describe);
        }
        return geographicalPositionMap;
    }

    @Override
    public Integer saveGeographicalPosition(Map<String, Object> params) {
        int save = productPositionMapper.saveGeographicalPosition(params);
        productionBaseService.update(params);
        return save;
    }
}
