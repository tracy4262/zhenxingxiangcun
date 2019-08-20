package com.ovit.nswy.member.adapter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class ShopAdapter extends BaseAdapter implements ShopItfService {

    @Value("${shop.fetchProductUrl}")
    private String fetch_product_url;


    /**
     * 0：定价好货 1：热门团购 2：优品竞拍 3：新品预售
     */
    @Override
    public String getFullProduct(int salesMode, int pageNo, int pageSize) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("sales_mode", salesMode);
        map.put("pageSize", pageSize);
        map.put("pageNo", pageNo);
        return doPost(fetch_product_url, map);
    }

    /**
     * 抢现货
     */
    @Override
    public String getSpotProduct(int pageNo, int pageSize) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("status", 0);
        map.put("pageSize", pageSize);
        map.put("pageNo", pageNo);
        return doPost(fetch_product_url, map);
    }

    /**
     * 可追溯
     */
    @Override
    public String getTraceabilityProduct(int pageNo, int pageSize) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("trace_type", 0);
        map.put("pageSize", pageSize);
        map.put("pageNo", pageNo);
        return doPost(fetch_product_url, map);
    }

    /**
     * 0:热销产品 1:最新产品 2: 产品推荐
     */
    @Override
    public String getRecommendProduct(int type, int pageNo, int pageSize) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("type", type);
        map.put("pageSize", pageSize);
        map.put("pageNo", pageNo);
        return doPost(fetch_product_url, map);
    }
}
