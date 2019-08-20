package com.ovit.nswy.portal.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.github.pagehelper.PageHelper;
import com.ovit.nswy.portal.mapper.CurrencyCommodityMapper;
import com.ovit.nswy.portal.service.CurrencyCommodityService;
import com.ovit.nswy.portal.service.ShopCommodityTypeInfoService;
import com.ovit.nswy.portal.service.StandardService;
import com.ovit.nswy.portal.util.JsonUtils;
import com.ovit.nswy.portal.util.PageUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CurrencyCommodityServiceImpl implements CurrencyCommodityService {

    @Autowired
    private StandardService standardService;

    @Autowired
    private ShopCommodityTypeInfoService shopCommodityTypeInfoService;

    @Autowired
    private CurrencyCommodityMapper currencyCommodityMapper;

    /**
     * 获取通用商品列表信息
     * @param params
     * @return
     */
    @Override
    public List<Map<String, Object>> list(Map<String, Object> params) {
        PageUtils.initPage(params);
        List<Map<String, Object>> commodityList = standardService.findCurrencyCommodity(params);

        for (Map<String, Object> commodity : commodityList) {
            //根据物种id获取物种名称
            String speciesName = shopCommodityTypeInfoService.getSpecies(MapUtils.getString(commodity, "relatedspecies"));
            commodity.put("relatedspecies", speciesName);

            if (MapUtils.isNotEmpty(commodity)) {

                String image = MapUtils.getString(commodity, "image");
                if (StringUtils.isBlank(image)) {
                    commodity.put("image", new String[0]);
                } else {
                    String[] images = image.split(";");
                    commodity.put("image", images);
                }

                //自定义表单之商品质量信息
                if (StringUtils.isNotBlank(MapUtils.getString(commodity, "qualityCustomContent"))) {
                    JSONArray qualityArray = JSONArray.parseArray(MapUtils.getString(commodity, "qualityCustomContent"));
                    commodity.put("qualityCustomContent", qualityArray);
                } else {
                    commodity.put("qualityCustomContent", new String[0]);
                }
                //自定义表单之商品安全标准
                if (StringUtils.isNotBlank(MapUtils.getString(commodity, "safetyCustomContent"))) {
                    JSONArray safetyArray = JSONArray.parseArray(MapUtils.getString(commodity, "safetyCustomContent"));
                    commodity.put("safetyCustomContent", safetyArray);
                } else {
                    commodity.put("safetyCustomContent", new String[0]);
                }
            }
        }

        return commodityList;
    }

    /**
     * 通过id查询通用商品的信息
     * @param params
     * @return
     */
    @Override
    public Map<String, Object> findCommodityById(Map<String, Object> params) {

        Map<String, Object> commodityMap = currencyCommodityMapper.findCommodityById(params);
        if (MapUtils.isNotEmpty(commodityMap)) {
            //根据物种id获取物种名称
            String speciesName = shopCommodityTypeInfoService.getSpecies(MapUtils.getString(commodityMap, "relatedSpeciesId"));

            if (MapUtils.isNotEmpty(commodityMap)) {

                String image = MapUtils.getString(commodityMap, "image");
                if (StringUtils.isBlank(image)) {
                    commodityMap.put("image", new String[0]);
                } else {
                    String[] images = image.split(";");
                    commodityMap.put("image", images);
                }
            }
            commodityMap.put("relatedSpecies", speciesName);

            //自定义表单之商品质量信息
            if (StringUtils.isNotBlank(MapUtils.getString(commodityMap, "qualityCustomContent"))) {
                JSONArray qualityArray = JSONArray.parseArray(MapUtils.getString(commodityMap, "qualityCustomContent"));
                commodityMap.put("qualityCustomContent", qualityArray);
            } else {
                commodityMap.put("qualityCustomContent", new String[0]);
            }
            //自定义表单之商品安全标准
            if (StringUtils.isNotBlank(MapUtils.getString(commodityMap, "safetyCustomContent"))) {
                JSONArray safetyArray = JSONArray.parseArray(MapUtils.getString(commodityMap, "safetyCustomContent"));
                commodityMap.put("safetyCustomContent", safetyArray);
            } else {
                commodityMap.put("safetyCustomContent", new String[0]);
            }

            //查询得到产品大类
            Map<String,Object> param = new HashMap<>();
            param.put("productTypeId",MapUtils.getString(commodityMap,"productTypeId"));
            findMallClass(param,commodityMap);
        }

        return commodityMap;
    }

    private void findMallClass(Map<String,Object> params,Map<String,Object> commodityMap){

        Map<String,Object> map = currencyCommodityMapper.findMallClass(params);
        if(StringUtils.isNotBlank(MapUtils.getString(map,"pid"))){
            findMallClass(map,commodityMap);
        }else{

            commodityMap.put("productCategory",MapUtils.getString(map,"productCategory"));
            commodityMap.put("productCategoryId",MapUtils.getString(map,"classification_code"));
        }

    }

    /**
     * 通过id删除通用商品
     * @param params
     * @return
     */
    @Override
    public void deleteCommodityById(Map<String, Object> params) {
        currencyCommodityMapper.deleteCommodityById(params);
    }

    /**
     * 保存通用商品信息
     * @param commodityInfo
     */
    @Override
    public void insertCommodityInfo(Map<String, Object> commodityInfo) {
        String image = MapUtils.getString(commodityInfo, "image");
        JSONArray imageArray = JSONArray.parseArray(image);
        if (imageArray.size() == 0) {
            commodityInfo.put("image", "");
        } else {
            String[] images = JsonUtils.getJsonToStringArray(image);
            String imagesMerge = JsonUtils.stringArrayMerge(images);
            commodityInfo.put("image", imagesMerge);
        }
        if(!MapUtils.getString(commodityInfo,"type").equals("2")){

            //自定义表单之商品质量信息
            String qualityCustomContent = JSON.toJSONString(commodityInfo.get("qualityCustomContent"));
            commodityInfo.put("qualityCustomContent", qualityCustomContent);
            //自定义表单之商品安全标准
            String safetyCustomContent = JSON.toJSONString(commodityInfo.get("safetyCustomContent"));
            commodityInfo.put("safetyCustomContent", safetyCustomContent);
        }



        currencyCommodityMapper.insertCommodityInfo(commodityInfo);
    }

}
