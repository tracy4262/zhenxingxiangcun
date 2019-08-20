package com.ovit.nswy.portal.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.ovit.nswy.portal.mapper.UpMarketingBasicInfoMapper;
import com.ovit.nswy.portal.service.UpMarketingBasicInfoService;
import com.ovit.nswy.portal.util.JsonUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class UpMarketingBasicInfoServiceImpl implements UpMarketingBasicInfoService {

    @Autowired
    private UpMarketingBasicInfoMapper upMarketingBasicInfoMapper;

    @Override
    public void insertCommodityMarketingInfo(Map<String, Object> marketingInfo) {
        upMarketingBasicInfoMapper.insertCommodityMarketingInfo(marketingInfo);
    }

    @Override
    public Map<String, Object> getCommodityMarketingInfo(Map<String, Object> queryInfo) {
        return upMarketingBasicInfoMapper.getCommodityMarketingInfo(queryInfo);
    }

    @Override
    public void insertAssuranceInfo(Map<String, Object> assuranceInfo) {
        upMarketingBasicInfoMapper.insertAssuranceInfo(assuranceInfo);
    }

    @Override
    public Map<String, Object> getAssuranceInfo(Map<String, Object> queryInfo) {
        return upMarketingBasicInfoMapper.getAssuranceInfo(queryInfo);
    }

    @Override
    public void insertPricingInfo(Map<String, Object> pricingInfo) {
        upMarketingBasicInfoMapper.insertPricingInfo(pricingInfo);
    }

    @Override
    public Map<String, Object> getPricingInfo(Map<String, Object> queryInfo) {
        return upMarketingBasicInfoMapper.getPricingInfo(queryInfo);
    }

    @Override
    public void insertDeliveryInfo(Map<String, Object> deliveryInfo) {
        upMarketingBasicInfoMapper.insertDeliveryInfo(deliveryInfo);
    }

    @Override
    public Map<String, Object> getDeliveryInfo(Map<String, Object> queryInfo) {
        return upMarketingBasicInfoMapper.getDeliveryInfo(queryInfo);
    }

    @Override
    public void insertAfterSalesInfo(Map<String, Object> afterSalesInfo) {
        upMarketingBasicInfoMapper.insertAfterSalesInfo(afterSalesInfo);
    }

    @Override
    public Map<String, Object> getAfterSalesInfo(Map<String, Object> queryInfo) {
        return upMarketingBasicInfoMapper.getAfterSalesInfo(queryInfo);
    }

    @Override
    public void insertTraceableInfo(Map<String, Object> traceableInfo) {

        String productionEnvironment = MapUtils.getString(traceableInfo, "productionEnvironment");
        JSONArray environmentArray = JSONArray.parseArray(productionEnvironment);
        if (environmentArray.size() == 0) {
            traceableInfo.put("productionEnvironment", "");
        } else {
            String[] environments = JsonUtils.getJsonToStringArray(productionEnvironment);
            String environmentsMerge = JsonUtils.stringArrayMerge(environments);
            traceableInfo.put("productionEnvironment", environmentsMerge);
        }

        upMarketingBasicInfoMapper.insertTraceableInfo(traceableInfo);
    }

    @Override
    public Map<String, Object> getTraceableInfo(Map<String, Object> queryInfo) {
        Map<String,Object> traceableInfo = upMarketingBasicInfoMapper.findTraceableInfo(queryInfo);

        if (MapUtils.isNotEmpty(traceableInfo)) {

            String productionEnvironment = MapUtils.getString(traceableInfo, "productionEnvironment");
            if (StringUtils.isBlank(productionEnvironment)) {
                traceableInfo.put("productionEnvironment", new String[0]);
            } else {
                String[] environments = productionEnvironment.split(";");
                traceableInfo.put("productionEnvironment", environments);
            }
        }

        return traceableInfo;
    }

    @Override
    public void insertCommitmentInfo(Map<String, Object> commitmentInfo) {
        upMarketingBasicInfoMapper.insertCommitmentInfo(commitmentInfo);
    }

    @Override
    public Map<String, Object> getCommitmentInfo(Map<String, Object> queryInfo) {
        return upMarketingBasicInfoMapper.getCommitmentInfo(queryInfo);
    }

}
