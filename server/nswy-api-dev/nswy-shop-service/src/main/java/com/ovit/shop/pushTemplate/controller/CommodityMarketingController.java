package com.ovit.shop.pushTemplate.controller;


import com.mongodb.WriteResult;
import com.ovit.shop.pushTemplate.mapper.CommodityMarketingMapper;
import com.ovit.shop.pushTemplate.service.CommodityMarketingService;
import com.ovit.shop.test.controller.MongoDemoController;
import com.ovit.shop.util.ConvertDataUtil;
import com.ovit.shop.util.Result;
import com.ovit.shop.util.ResultCode;
import com.ovit.shop.util.UuidUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/commodityMarketing")
public class CommodityMarketingController {

    private static final Logger logger = LogManager.getLogger(CommodityMarketingController.class);

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private CommodityMarketingService commodityMarketingService;

    @Autowired
    private CommodityMarketingMapper commodityMarketingMapper;


    @RequestMapping(value = "/save",method = RequestMethod.POST)
    @Transactional(propagation = Propagation.REQUIRED)
    public Result save(@RequestBody Map<String,Object> params) {

        Result result = new Result();
        try {
            //获取title
            List<Map<String,Object>> titleList = (List<Map<String, Object>>) params.get("tabsData");
            String salesTitle = "";
            String warrantyTitle = "";
            String pricingTitle = "";
            String deliveryTitle = "";
            String afterSalesTitle = "";

            String productCategoryId = MapUtils.getString(params,"productCategoryId");
            //判断是否是无形产品
            boolean flag = !"CP09".equals(productCategoryId);
            if (flag) {
                salesTitle = MapUtils.getString(titleList.get(0), "title");
                warrantyTitle = MapUtils.getString(titleList.get(1), "title");
                pricingTitle = MapUtils.getString(titleList.get(2), "title");
                deliveryTitle = MapUtils.getString(titleList.get(3), "title");
                afterSalesTitle = MapUtils.getString(titleList.get(4), "title");
            } else {
                salesTitle = MapUtils.getString(titleList.get(0), "title");
                pricingTitle = MapUtils.getString(titleList.get(1), "title");
                afterSalesTitle = MapUtils.getString(titleList.get(2), "title");
            }

            //先删除，在保存
            Query deleteQuery = new Query(Criteria.where("shopPushTemplateId").is(MapUtils.getString(params,"shopPushTemplateId")));

            mongoTemplate.remove(deleteQuery, Map.class, "marketing_commodity_sales_info");
            mongoTemplate.remove(deleteQuery, Map.class, "marketing_commodity_pricing_info");
            mongoTemplate.remove(deleteQuery, Map.class, "marketing_commodity_after_info");

            if (flag) {
                mongoTemplate.remove(deleteQuery, Map.class, "marketing_commodity_warranty_info");
                mongoTemplate.remove(deleteQuery, Map.class, "marketing_commodity_delivery_info");
            }

            //商品销售信息
            Map<String,Object> sales = MapUtils.getMap(params,"sales");
            if(MapUtils.isNotEmpty(sales)) {
                sales.put("id", UuidUtil.get32UUID());
                sales.put("title", salesTitle);
                sales.put("shopPushTemplateId", MapUtils.getString(params,"shopPushTemplateId"));
                sales.put("account", MapUtils.getString(params,"account"));
                sales.put("productCategoryId", MapUtils.getString(params,"productCategoryId"));
                sales.put("templateType", MapUtils.getString(params,"templateType"));
                sales.put("create_time", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
                mongoTemplate.save(sales, "marketing_commodity_sales_info");
            }
            if (flag) {
                //商品质保信息
                Map<String,Object> warranty = MapUtils.getMap(params,"warranty");
                if(MapUtils.isNotEmpty(warranty)){
                    warranty.put("id", UuidUtil.get32UUID());
                    warranty.put("title", warrantyTitle);
                    warranty.put("shopPushTemplateId", MapUtils.getString(params,"shopPushTemplateId"));
                    warranty.put("account", MapUtils.getString(params,"account"));
                    warranty.put("productCategoryId", MapUtils.getString(params,"productCategoryId"));
                    warranty.put("templateType", MapUtils.getString(params,"templateType"));
                    warranty.put("create_time", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
                    mongoTemplate.save(warranty, "marketing_commodity_warranty_info");
                }
            }

            //商品定价信息
            Map<String,Object> pricing = MapUtils.getMap(params,"pricing");
            if (MapUtils.isNotEmpty(pricing)) {
                pricing.put("id", UuidUtil.get32UUID());
                pricing.put("title", pricingTitle);
                pricing.put("shopPushTemplateId", MapUtils.getString(params,"shopPushTemplateId"));
                pricing.put("account", MapUtils.getString(params,"account"));
                pricing.put("productCategoryId", MapUtils.getString(params,"productCategoryId"));
                pricing.put("templateType", MapUtils.getString(params,"templateType"));
                pricing.put("create_time", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
                mongoTemplate.save(pricing, "marketing_commodity_pricing_info");
            }

            if (flag) {
                //商品发货信息
                List<Map<String,Object>> deliveryList = (List<Map<String, Object>>) params.get("delivery");
                if (CollectionUtils.isNotEmpty(deliveryList)) {
                    for (Map<String,Object> delivery : deliveryList) {
                        String id = UuidUtil.get32UUID();
                        delivery.put("id", id);
                        delivery.put("title", deliveryTitle);
                        delivery.put("shopPushTemplateId", MapUtils.getString(params,"shopPushTemplateId"));
                        delivery.put("account", MapUtils.getString(params,"account"));
                        delivery.put("productCategoryId", MapUtils.getString(params,"productCategoryId"));
                        delivery.put("templateType", MapUtils.getString(params,"templateType"));
                        delivery.put("create_time", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
                        mongoTemplate.save(delivery, "marketing_commodity_delivery_info");

                        Map<String,Object> commonMap = new HashMap<>();
                        commonMap.put("account", MapUtils.getString(params, "account"));
                        commonMap.put("marketingId", id);
                        commonMap.put("type", 1);
                        //保存营业网点信息
                        List<Map<String, Object>> list = (List<Map<String, Object>>) delivery.get("networkStation");
                        if (CollectionUtils.isNotEmpty(list)) {
                            List<Map<String, Object>> networkList = new ArrayList<>();
                            for (Map<String,Object> map : list) {
                                Map<String,Object> networkMap = new HashMap<>();
                                networkMap.putAll(commonMap);
                                networkMap.putAll(map);
                                networkList.add(networkMap);
                            }
                            //先删除原有数据
                            commodityMarketingMapper.deleteNetworkInfo(commonMap);
                            //批量保存联系人信息
                            commodityMarketingMapper.batchSaveNetwork(networkList);
                        } else {
                            //先删除原有数据
                            commodityMarketingMapper.deleteNetworkInfo(commonMap);
                        }
                    }
                }
            }

            //产品售后信息
            Map<String,Object> afterSales = MapUtils.getMap(params,"afterSales");
            if(MapUtils.isNotEmpty(afterSales)) {
                String id = UuidUtil.get32UUID();
                afterSales.put("id", id);
                afterSales.put("title", afterSalesTitle);
                afterSales.put("shopPushTemplateId", MapUtils.getString(params,"shopPushTemplateId"));
                afterSales.put("account", MapUtils.getString(params,"account"));
                afterSales.put("productCategoryId", MapUtils.getString(params,"productCategoryId"));
                afterSales.put("templateType", MapUtils.getString(params,"templateType"));
                afterSales.put("create_time", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
                mongoTemplate.save(afterSales, "marketing_commodity_after_info");

                Map<String,Object> commonMap = new HashMap<>();
                commonMap.put("account", MapUtils.getString(params, "account"));
                commonMap.put("marketingId", id);
                commonMap.put("type", 2);
                //保存营业网点信息
                List<Map<String, Object>> list = (List<Map<String, Object>>) afterSales.get("networkStation");
                if (CollectionUtils.isNotEmpty(list)) {
                    List<Map<String, Object>> networkList = new ArrayList<>();
                    for (Map<String,Object> map : list) {
                        Map<String,Object> networkMap = new HashMap<>();
                        networkMap.putAll(commonMap);
                        networkMap.putAll(map);
                        networkList.add(networkMap);
                    }
                    //先删除原有数据
                    commodityMarketingMapper.deleteNetworkInfo(commonMap);
                    //批量保存联系人信息
                    commodityMarketingMapper.batchSaveNetwork(networkList);
                } else {
                    //先删除原有数据
                    commodityMarketingMapper.deleteNetworkInfo(commonMap);
                }
            }

            result.setMsg("保存成功");

        } catch (Exception e) {
            logger.error("异常信息：", e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg("保存商品发布第四步信息失败");
        }
        return result;
    }


    @RequestMapping(value = "/find",method = RequestMethod.POST)
    public Result find(@RequestBody(required=false) Map<String,Object> params) {

        Result result = new Result();
        try {
            Query query = new Query();

            Criteria criteria =  Criteria.where("account").is(MapUtils.getString(params,"account"));
            if(StringUtils.isNotBlank(MapUtils.getString(params,"isUse"))) {
                criteria.and("isUse").is(MapUtils.getString(params,"isUse"));
            }
            if(StringUtils.isNotBlank(MapUtils.getString(params,"templateType"))) {
                criteria.and("templateType").is(MapUtils.getString(params,"templateType"));
            }

            if(StringUtils.isNotBlank(MapUtils.getString(params,"templateName"))) {
                criteria.and("templateName").regex(MapUtils.getString(params,"templateName"));
            }

            if(StringUtils.isNotBlank(MapUtils.getString(params,"productCategoryId"))) {
                criteria.and("productCategoryId").is(MapUtils.getString(params,"productCategoryId"));
            }

            if(StringUtils.isNotBlank(MapUtils.getString(params,"shopPushTemplateId"))) {
                criteria.and("shopPushTemplateId").is(MapUtils.getString(params,"shopPushTemplateId"));
            }

            //查询
            query.addCriteria(criteria);
            query.fields().exclude("_id");

            Query queryTwo = new Query();
            Criteria criteriaOld =  Criteria.where("account").is(MapUtils.getString(params,"account"))
                    .and("productCategoryId").is(MapUtils.getString(params,"productCategoryId"))
                    .and("templateType").is("0");
            queryTwo.addCriteria(criteriaOld);
            queryTwo.fields().exclude("_id");

            Map<String,Object> map = new HashMap<>();

            String salesTitle = "商品销售信息";
            String warrantyTitle = "商品质保信息";
            String pricingTitle = "商品定价信息";
            String deliveryTitle = "商品发货信息";
            String afterSalesTitle = "商品售后信息";

            String productCategoryId = MapUtils.getString(params,"productCategoryId");
            //判断是否是无形产品
            boolean flag = !"CP09".equals(productCategoryId);

            List<Map> sales = mongoTemplate.find(query, Map.class, "marketing_commodity_sales_info");
            if(CollectionUtils.isNotEmpty(sales)) {
                map.put("sales", sales.get(0));
                salesTitle = MapUtils.getString(sales.get(0), "title");
            } else {
                if(MapUtils.getString(params,"templateType").equals("1")) {
                    sales = mongoTemplate.find(queryTwo, Map.class, "marketing_commodity_sales_info");
                    if (CollectionUtils.isNotEmpty(sales)) {
                        salesTitle = MapUtils.getString(sales.get(0), "title");
                        map.put("sales", sales.get(0));
                    }
                }
            }

            if (flag) {
                List<Map> warranty = mongoTemplate.find(query, Map.class, "marketing_commodity_warranty_info");
                if(CollectionUtils.isNotEmpty(warranty)) {
                    map.put("warranty", warranty.get(0));
                    warrantyTitle = MapUtils.getString(warranty.get(0), "title");
                } else {
                    if(MapUtils.getString(params,"templateType").equals("1")) {
                        warranty = mongoTemplate.find(queryTwo, Map.class, "marketing_commodity_warranty_info");
                        if (CollectionUtils.isNotEmpty(warranty)) {
                            warrantyTitle = MapUtils.getString(warranty.get(0), "title");
                            map.put("warranty", warranty.get(0));
                        }
                    }
                }
            }

            List<Map> pricing = mongoTemplate.find(query, Map.class, "marketing_commodity_pricing_info");
            if(CollectionUtils.isNotEmpty(pricing)) {
                map.put("pricing", pricing.get(0));
                pricingTitle = MapUtils.getString(pricing.get(0), "title");
            } else {
                if(MapUtils.getString(params,"templateType").equals("1")) {
                    pricing = mongoTemplate.find(queryTwo, Map.class, "marketing_commodity_pricing_info");
                    if (CollectionUtils.isNotEmpty(pricing)) {
                        pricingTitle = MapUtils.getString(pricing.get(0), "title");
                        map.put("pricing", pricing.get(0));
                    }
                }
            }

            if (flag) {
                List<Map> delivery = mongoTemplate.find(query, Map.class, "marketing_commodity_delivery_info");
                if(CollectionUtils.isNotEmpty(delivery)) {
                    map.put("delivery", delivery);
                    deliveryTitle = MapUtils.getString(delivery.get(0), "title");
                } else {
                    if(MapUtils.getString(params,"templateType").equals("1")) {
                        delivery = mongoTemplate.find(queryTwo, Map.class, "marketing_commodity_delivery_info");
                        if (CollectionUtils.isNotEmpty(delivery)) {
                            deliveryTitle = MapUtils.getString(delivery.get(0), "title");
                            map.put("delivery", delivery);
                        }
                    }
                }
            }

            List<Map> afterSales = mongoTemplate.find(query, Map.class, "marketing_commodity_after_info");
            if(CollectionUtils.isNotEmpty(afterSales)) {
                map.put("afterSales", afterSales.get(0));
                afterSalesTitle = MapUtils.getString(afterSales.get(0), "title");
            } else {
                if(MapUtils.getString(params,"templateType").equals("1") ){
                    afterSales = mongoTemplate.find(queryTwo, Map.class, "marketing_commodity_after_info");
                    if (CollectionUtils.isNotEmpty(afterSales)) {
                        map.put("afterSales", afterSales.get(0));
                        afterSalesTitle = MapUtils.getString(afterSales.get(0), "title");
                    }
                }
            }

            //标题
            List<Map<String,Object>> tabsData = new ArrayList<>();
            Map<String,Object> salesMap = new HashMap<>();
            salesMap.put("name", "sales");
            salesMap.put("title", salesTitle);
            tabsData.add(salesMap);

            if (flag) {
                Map<String,Object> warrantyMap = new HashMap<>();
                warrantyMap.put("name", "warranty");
                warrantyMap.put("title", warrantyTitle);
                tabsData.add(warrantyMap);
            }

            Map<String,Object> pricingMap = new HashMap<>();
            pricingMap.put("name", "pricing");
            pricingMap.put("title", pricingTitle);
            tabsData.add(pricingMap);

            if (flag) {
                Map<String,Object> deliveryMap = new HashMap<>();
                deliveryMap.put("name", "delivery");
                deliveryMap.put("title", deliveryTitle);
                tabsData.add(deliveryMap);
            }

            Map<String,Object> afterSalesMap = new HashMap<>();
            afterSalesMap.put("name", "afterSales");
            afterSalesMap.put("title", afterSalesTitle);
            tabsData.add(afterSalesMap);

            map.put("tabsData", tabsData);
            result.setMsg("查询数据成功");
            result.setData(map);

        } catch (Exception e) {
            logger.error("异常信息：", e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg("查询商品发布模块信息失败");
        }

        return result;
    }

    /**
     * 营业网点列表：从完善信息的营业网点表单里拉取
     * @param params
     * @return
     */
    @RequestMapping(value = "/networkStationList",method = RequestMethod.POST)
    public Result networkStationList(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            List<Map<String,Object>> list = commodityMarketingService.networkStationList(params);
            result.setMsg("拉取营业网点信息列表查询成功");
            result.setData(list);
        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }


}
