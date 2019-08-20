package com.ovit.shop.pushTemplate.controller;


import com.netflix.hystrix.contrib.javanica.utils.CommonUtils;
import com.ovit.shop.inventory.service.BasicSettingService;
import com.ovit.shop.pushTemplate.mapper.CommodityMarketingMapper;
import com.ovit.shop.pushTemplate.mapper.MyRecommendMapper;
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
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 发布商品
 */
@RestController
@RequestMapping("/pushShopInfo")
public class PushShopInfoController {

    private static final Logger logger = LogManager.getLogger(PushShopInfoController.class);

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private CommodityMarketingMapper commodityMarketingMapper;

    @Autowired
    private BasicSettingService basicSettingService;

    @Autowired
    private MyRecommendMapper myRecommendMapper;


    @RequestMapping(value = "/saveShop",method = RequestMethod.POST)
    public Result savePush(@RequestBody Map<String,Object> params) {

        Result result = new Result();
        try {

            Query query = new Query();

            Criteria criteria =  Criteria.where("account").is(MapUtils.getString(params,"account")).and("productNameId").is(MapUtils.getInteger(params,"productNameId"));

            //查询
            query.addCriteria(criteria);
            query.fields().exclude("_id");
            List<Map> list = mongoTemplate.find(query, Map.class, "push_shop_commodity_info");
            String id = "";
            if(CollectionUtils.isNotEmpty(list)){
                id = MapUtils.getString(list.get(0),"id");
            }else{
                id= UuidUtil.get32UUID();

            }
            params.put("id",id);

            Query deleteQuery = new Query(Criteria.where("id").is(MapUtils.getString(params,"id")));
            mongoTemplate.remove(deleteQuery, Map.class, "push_shop_commodity_info");

            params.put("create_time", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            mongoTemplate.save(params, "push_shop_commodity_info");

            Map<String,Object> map = new HashMap<>();
            map.put("id",MapUtils.getString(params,"id"));
            result.setData(map);
            result.setMsg("保存成功");
        }catch (Exception e){
            logger.error("异常信息：", e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg("保存发布商品信息失败");
        }
        return result;
    }

    @RequestMapping(value = "/findPushShopInfo",method = RequestMethod.POST)
    public Result findPushShopInfo(@RequestBody(required=false) Map<String,Object> params) {
        Result result = new Result();
        try {
            Query query = new Query();

            Criteria criteria =  Criteria.where("id").is(MapUtils.getString(params,"id"));

            //查询
            query.addCriteria(criteria);
            query.fields().exclude("_id");
            List<Map> list = mongoTemplate.find(query, Map.class, "push_shop_commodity_info");


            logger.info("query:{}, result:{}", query, list);
            result.setMsg("查询数据成功");
            result.setData(list);

        }catch (Exception e){
            logger.error("异常信息：", e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg("查询发布商品信息失败");
        }

        return result;
    }


    @RequestMapping(value = "/findPushShopList",method = RequestMethod.POST)
    public Result findTemplateList(@RequestBody(required=false) Map<String,Object> params) {

        Result result = new Result();
        try {
            Query query = new Query();

            Criteria criteria =  Criteria.where("templateType").is("1");

            if(StringUtils.isNotBlank(MapUtils.getString(params,"commodityName"))){
                criteria.and("commodityName").regex(MapUtils.getString(params,"commodityName"));
            }

            //查询
            query.addCriteria(criteria);
            query.fields().exclude("_id");
            List<Map> list = mongoTemplate.find(query, Map.class, "shop_push_template");

            if(list == null || list.size() == 0){
                query = new Query();
                Criteria criteriaTy =  Criteria.where("templateType").is("0");
                if(StringUtils.isNotBlank(MapUtils.getString(params,"productCategoryId"))){
                    criteriaTy.and("productCategoryId").is(MapUtils.getString(params,"productCategoryId"));
                }
                query.addCriteria(criteriaTy);
                query.fields().exclude("_id");
                list = mongoTemplate.find(query, Map.class, "shop_push_template");


            }
            logger.info("query:{}, result:{}", query, list);
            result.setMsg("查询数据成功");
            result.setData(list);

        }catch (Exception e){
            logger.error("异常信息：", e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg("查询商品模块信息失败");
        }

        return result;
    }




    @RequestMapping(value = "/findPushBasicCommodityList",method = RequestMethod.POST)
    public Result findPushBasicCommodityList(@RequestBody(required=false) Map<String,Object> params) {

        Result result = new Result();
        try {
            Query query = new Query();

            Criteria criteria =  Criteria.where("pushShopCommodityId").is(MapUtils.getString(params,"pushShopCommodityId"));


            //查询
            query.addCriteria(criteria);
            query.fields().exclude("_id");
            Map<String,Object> map = new HashMap<>();

            List<Map> product = mongoTemplate.find(query, Map.class, "push_basic_commodity_info");
            if(product!=null && product.size()>0){
                map.put("product",product);
            }


            List<Map> intangibleAssets = mongoTemplate.find(query, Map.class, "push_basic_commodity_intangibleAssets_info");
            if(intangibleAssets!=null && intangibleAssets.size()>0){
                map.put("intangibleAssets",intangibleAssets);
            }

            List<Map> production = mongoTemplate.find(query, Map.class, "push_basic_commodity_production_info");
            if(production!=null && production.size()>0){
                map.put("production",production);
            }

            List<Map> origin = mongoTemplate.find(query, Map.class, "push_basic_commodity_place_info");
            if(origin!=null && origin.size()>0){
                map.put("origin",origin);
            }

            List<Map> honor = mongoTemplate.find(query, Map.class, "push_basic_commodity_honor_info");
            if(honor!=null && honor.size()>0){
                map.put("honor",honor);
            }

            List<Map> productLocation = mongoTemplate.find(query, Map.class, "push_basic_commodity_location_info");
            if(productLocation!=null && productLocation.size()>0){
                map.put("productLocation",productLocation);
            }


            List<Map> qualification = mongoTemplate.find(query, Map.class, "push_basic_commodity_qualification_info");
            if(qualification!=null && qualification.size()>0){
                map.put("qualification",qualification);
            }

            List<Map> quality = mongoTemplate.find(query, Map.class, "push_basic_commodity_quality_info");
            if(quality!=null && quality.size()>0){
                map.put("quality",quality);
            }

            List<Map> safety = mongoTemplate.find(query, Map.class, "push_basic_commodity_safety_standard");
            if(safety!=null && safety.size()>0){
                map.put("safety",safety);
            }

            List<Map> barcode = mongoTemplate.find(query, Map.class, "push_basic_international_commodity_barcode");
            if(barcode!=null && barcode.size()>0){
                map.put("barcode",barcode);
            }

            //如果查询到发布商品基本信息没有数据，就查询模版商品基本信息
            if(MapUtils.isEmpty(map)){
                query = new Query();

                Criteria criteriaold =  Criteria.where("shopPushTemplateId").is(MapUtils.getString(params,"shopPushTemplateId"));

                //查询
                query.addCriteria(criteriaold);
                query.fields().exclude("_id");

                product = mongoTemplate.find(query, Map.class, "basic_commodity_info");
                if(product!=null && product.size()>0){
                    map.put("product",product);
                }else{
                    map.put("product",new String[]{});
                }


                intangibleAssets = mongoTemplate.find(query, Map.class, "basic_commodity_intangibleAssets_info");
                if(intangibleAssets!=null && intangibleAssets.size()>0){
                    map.put("intangibleAssets",intangibleAssets);
                }else{
                    map.put("intangibleAssets",new String[]{});
                }

                production = mongoTemplate.find(query, Map.class, "basic_commodity_production_info");
                if(production!=null && production.size()>0){
                    map.put("production",production);
                }else{
                    map.put("production",new String[]{});

                }

                origin = mongoTemplate.find(query, Map.class, "basic_commodity_place_info");
                if(origin!=null && origin.size()>0){
                    map.put("origin",origin);
                }else{
                    map.put("origin",new String[]{});

                }

                honor = mongoTemplate.find(query, Map.class, "basic_commodity_honor_info");
                if(honor!=null && honor.size()>0){
                    map.put("honor",honor);
                }else{
                    map.put("honor",new String[]{});

                }

                productLocation = mongoTemplate.find(query, Map.class, "basic_commodity_location_info");
                if(productLocation!=null && productLocation.size()>0){
                    map.put("productLocation",productLocation);
                }else{
                    map.put("productLocation",new String[]{});

                }

                qualification = mongoTemplate.find(query, Map.class, "basic_commodity_qualification_info");
                if(qualification!=null && qualification.size()>0){
                    map.put("qualification",qualification);
                }else{
                    map.put("qualification",new String[]{});

                }


                quality = mongoTemplate.find(query, Map.class, "basic_commodity_quality_info");
                if(quality!=null && quality.size()>0){
                    map.put("quality",quality);
                }else{
                    map.put("quality",new String[]{});

                }

                safety = mongoTemplate.find(query, Map.class, "basic_commodity_safety_standard");
                if(safety!=null && safety.size()>0){
                    map.put("safety",safety);
                }else{
                    map.put("safety",new String[]{});

                }

                barcode = mongoTemplate.find(query, Map.class, "basic_international_commodity_barcode");
                if(barcode!=null && barcode.size()>0){
                    map.put("barcode",barcode);
                }else{
                    map.put("barcode",new String[]{});

                }
            }

            logger.info("query:{}, result:{}", query, map);
            result.setMsg("查询数据成功");
            result.setData(map);

        }catch (Exception e){
            logger.error("异常信息：", e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg("查询商品模块信息失败");
        }

        return result;
    }


    @RequestMapping(value = "/savePushBasicCommodity",method = RequestMethod.POST)
    public Result savePushBasicCommodity(@RequestBody Map<String,Object> params) {

        Result result = new Result();
        try {
            //先删除，在保存
            Query deleteQuery = new Query(Criteria.where("pushShopCommodityId").is(MapUtils.getString(params,"pushShopCommodityId")));
            mongoTemplate.remove(deleteQuery, Map.class, "push_basic_commodity_info");

            mongoTemplate.remove(deleteQuery, Map.class, "push_basic_commodity_intangibleAssets_info");

            mongoTemplate.remove(deleteQuery, Map.class, "push_basic_commodity_production_info");

            mongoTemplate.remove(deleteQuery, Map.class, "push_basic_commodity_place_info");

            mongoTemplate.remove(deleteQuery, Map.class, "push_basic_commodity_location_info");

            mongoTemplate.remove(deleteQuery, Map.class, "push_basic_commodity_qualification_info");

            mongoTemplate.remove(deleteQuery, Map.class, "push_basic_commodity_honor_info");

            mongoTemplate.remove(deleteQuery, Map.class, "push_basic_commodity_quality_info");

            mongoTemplate.remove(deleteQuery, Map.class, "push_basic_commodity_safety_standard");

            mongoTemplate.remove(deleteQuery, Map.class, "push_basic_international_commodity_barcode");

            //商品信息
            Map<String,Object> product = MapUtils.getMap(params,"product");
            if(MapUtils.isNotEmpty(product)){
                product.put("id",UuidUtil.get32UUID());
                product.put("shopPushTemplateId",MapUtils.getString(params,"shopPushTemplateId"));
                product.put("account",MapUtils.getString(params,"account"));
                product.put("productCategoryId",MapUtils.getString(params,"productCategoryId"));
                product.put("templateType",MapUtils.getString(params,"templateType"));
                product.put("pushShopCommodityId",MapUtils.getString(params,"pushShopCommodityId"));
                product.put("create_time", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
                //默认存入库存相关的2个字段：storageStatus、isDelete
                product.put("storageStatus", "0");
                product.put("isDelete", "0");
                mongoTemplate.save(product, "push_basic_commodity_info");
            }

            //商品信息-无形资产
            Map<String,Object> intangibleAssets = MapUtils.getMap(params,"intangibleAssets");
            if(MapUtils.isNotEmpty(intangibleAssets)){
                intangibleAssets.put("id",UuidUtil.get32UUID());
                intangibleAssets.put("shopPushTemplateId",MapUtils.getString(params,"shopPushTemplateId"));
                intangibleAssets.put("account",MapUtils.getString(params,"account"));
                intangibleAssets.put("productCategoryId",MapUtils.getString(params,"productCategoryId"));
                intangibleAssets.put("pushShopCommodityId",MapUtils.getString(params,"pushShopCommodityId"));
                intangibleAssets.put("templateType",MapUtils.getString(params,"templateType"));
                intangibleAssets.put("create_time", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
                mongoTemplate.save(intangibleAssets, "push_basic_commodity_intangibleAssets_info");
            }

            //商品生产信息
            Map<String,Object> production = MapUtils.getMap(params,"production");
            if(MapUtils.isNotEmpty(production)){
                production.put("id",UuidUtil.get32UUID());
                production.put("shopPushTemplateId",MapUtils.getString(params,"shopPushTemplateId"));
                production.put("account",MapUtils.getString(params,"account"));
                production.put("productCategoryId",MapUtils.getString(params,"productCategoryId"));
                production.put("templateType",MapUtils.getString(params,"templateType"));
                production.put("pushShopCommodityId",MapUtils.getString(params,"pushShopCommodityId"));
                production.put("create_time", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
                mongoTemplate.save(production, "push_basic_commodity_production_info");
            }

            //商品产地信息
            Map<String,Object> origin = MapUtils.getMap(params,"origin");
            if(MapUtils.isNotEmpty(origin)){
                origin.put("id",UuidUtil.get32UUID());
                origin.put("shopPushTemplateId",MapUtils.getString(params,"shopPushTemplateId"));
                origin.put("account",MapUtils.getString(params,"account"));
                origin.put("productCategoryId",MapUtils.getString(params,"productCategoryId"));
                origin.put("templateType",MapUtils.getString(params,"templateType"));
                origin.put("pushShopCommodityId",MapUtils.getString(params,"pushShopCommodityId"));
                origin.put("create_time", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
                mongoTemplate.save(origin, "push_basic_commodity_place_info");
            }

            //商品所在地信息
            Map<String,Object> productLocation = MapUtils.getMap(params,"productLocation");
            if(MapUtils.isNotEmpty(productLocation)){
                productLocation.put("id",UuidUtil.get32UUID());
                productLocation.put("shopPushTemplateId",MapUtils.getString(params,"shopPushTemplateId"));
                productLocation.put("account",MapUtils.getString(params,"account"));
                productLocation.put("productCategoryId",MapUtils.getString(params,"productCategoryId"));
                productLocation.put("pushShopCommodityId",MapUtils.getString(params,"pushShopCommodityId"));
                productLocation.put("templateType",MapUtils.getString(params,"templateType"));
                productLocation.put("create_time", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
                mongoTemplate.save(productLocation, "push_basic_commodity_location_info");
            }

            //资质信息
            Map<String,Object> qualification = MapUtils.getMap(params,"qualification");
            if(MapUtils.isNotEmpty(qualification)){
                qualification.put("id",UuidUtil.get32UUID());
                qualification.put("shopPushTemplateId",MapUtils.getString(params,"shopPushTemplateId"));
                qualification.put("account",MapUtils.getString(params,"account"));
                qualification.put("productCategoryId",MapUtils.getString(params,"productCategoryId"));
                qualification.put("pushShopCommodityId",MapUtils.getString(params,"pushShopCommodityId"));
                qualification.put("templateType",MapUtils.getString(params,"templateType"));
                qualification.put("create_time", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
                mongoTemplate.save(qualification, "push_basic_commodity_qualification_info");
            }



            //商品荣誉信息
            Map<String,Object> honor = MapUtils.getMap(params,"honor");
            if(MapUtils.isNotEmpty(honor)){
                honor.put("id",UuidUtil.get32UUID());
                honor.put("shopPushTemplateId",MapUtils.getString(params,"shopPushTemplateId"));
                honor.put("account",MapUtils.getString(params,"account"));
                honor.put("productCategoryId",MapUtils.getString(params,"productCategoryId"));
                honor.put("templateType",MapUtils.getString(params,"templateType"));
                honor.put("pushShopCommodityId",MapUtils.getString(params,"pushShopCommodityId"));
                honor.put("create_time", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
                mongoTemplate.save(honor, "push_basic_commodity_honor_info");
            }


            //商品质量信息
            Map<String,Object> quality = MapUtils.getMap(params,"quality");
            if(MapUtils.isNotEmpty(quality)){
                quality.put("id",UuidUtil.get32UUID());
                quality.put("shopPushTemplateId",MapUtils.getString(params,"shopPushTemplateId"));
                quality.put("account",MapUtils.getString(params,"account"));
                quality.put("productCategoryId",MapUtils.getString(params,"productCategoryId"));
                quality.put("templateType",MapUtils.getString(params,"templateType"));
                quality.put("pushShopCommodityId",MapUtils.getString(params,"pushShopCommodityId"));
                quality.put("create_time", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
                mongoTemplate.save(quality, "push_basic_commodity_quality_info");
            }


            //商品安全标准
            Map<String,Object> safety = MapUtils.getMap(params,"safety");
            if(MapUtils.isNotEmpty(safety)){
                safety.put("id",UuidUtil.get32UUID());
                safety.put("shopPushTemplateId",MapUtils.getString(params,"shopPushTemplateId"));
                safety.put("account",MapUtils.getString(params,"account"));
                safety.put("productCategoryId",MapUtils.getString(params,"productCategoryId"));
                safety.put("templateType",MapUtils.getString(params,"templateType"));
                safety.put("pushShopCommodityId",MapUtils.getString(params,"pushShopCommodityId"));
                safety.put("create_time", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
                mongoTemplate.save(safety, "push_basic_commodity_safety_standard");
            }

            //商品安全标准
            Map<String,Object> barcode = MapUtils.getMap(params,"barcode");
            if(MapUtils.isNotEmpty(barcode)){
                barcode.put("id",UuidUtil.get32UUID());
                barcode.put("shopPushTemplateId",MapUtils.getString(params,"shopPushTemplateId"));
                barcode.put("account",MapUtils.getString(params,"account"));
                barcode.put("productCategoryId",MapUtils.getString(params,"productCategoryId"));
                barcode.put("templateType",MapUtils.getString(params,"templateType"));
                barcode.put("pushShopCommodityId",MapUtils.getString(params,"pushShopCommodityId"));
                barcode.put("create_time", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
                mongoTemplate.save(barcode, "push_basic_international_commodity_barcode");
            }

            result.setMsg("保存成功");
        }catch (Exception e){
            logger.error("异常信息：", e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg("保存商品发布第三步信息失败");
        }
        return result;
    }

    @RequestMapping(value = "/findPushMarketing",method = RequestMethod.POST)
    public Result findPushMarketing(@RequestBody(required=false) Map<String,Object> params) {

        Result result = new Result();
        try {
            Query query = new Query();

            Criteria criteria =  Criteria.where("pushShopCommodityId").is(MapUtils.getString(params,"pushShopCommodityId"));

            //查询
            query.addCriteria(criteria);
            query.fields().exclude("_id");
            Map<String,Object> map = new HashMap<>();

            String salesTitle = "商品销售信息";
            String warrantyTitle = "商品质保信息";
            String pricingTitle = "商品定价信息";
            String deliveryTitle = "商品发货信息";
            String afterSalesTitle = "商品售后信息";

            String productCategoryId = MapUtils.getString(params,"productCategoryId");
            //判断是否是无形产品
            boolean flag = !"CP09".equals(productCategoryId);

            List<Map> sales = mongoTemplate.find(query, Map.class, "push_marketing_commodity_sales_info");
            if(CollectionUtils.isNotEmpty(sales)) {
                map.put("sales", sales.get(0));
                salesTitle = MapUtils.getString(sales.get(0), "title");
            }

            if (flag) {
                List<Map> warranty = mongoTemplate.find(query, Map.class, "push_marketing_commodity_warranty_info");
                if(CollectionUtils.isNotEmpty(warranty)) {
                    map.put("warranty", warranty.get(0));
                    warrantyTitle = MapUtils.getString(warranty.get(0), "title");
                }
            }

            List<Map> pricing = mongoTemplate.find(query, Map.class, "push_marketing_commodity_pricing_info");
            if(CollectionUtils.isNotEmpty(pricing)) {
                map.put("pricing", pricing.get(0));
                pricingTitle = MapUtils.getString(pricing.get(0), "title");
            }

            if (flag) {
                List<Map> delivery = mongoTemplate.find(query, Map.class, "push_marketing_commodity_delivery_info");
                if(CollectionUtils.isNotEmpty(delivery)) {
                    map.put("delivery", delivery);
                    deliveryTitle = MapUtils.getString(delivery.get(0), "title");
                }
            }

            List<Map> afterSales = mongoTemplate.find(query, Map.class, "push_marketing_commodity_after_info");
            if(CollectionUtils.isNotEmpty(afterSales)) {
                map.put("afterSales", afterSales.get(0));
                afterSalesTitle = MapUtils.getString(afterSales.get(0), "title");
            }

            //如果查询到发布商品营销信息没有数据，就查询模版商品基本信息
            if(MapUtils.isEmpty(map)) {

                query = new Query();

                Criteria criteriaold =  Criteria.where("shopPushTemplateId").is(MapUtils.getString(params,"shopPushTemplateId"));

                //查询
                query.addCriteria(criteriaold);
                query.fields().exclude("_id");

                String pushSalesTitle = "商品销售信息";
                String pushWarrantyTitle = "商品质保信息";
                String pushPricingTitle = "商品定价信息";
                String pushDeliveryTitle = "商品发货信息";
                String pushAfterSalesTitle = "商品售后信息";

                //从库存管理表中获取商品销售信息中的产品可售量、产品可售量单位
                //首先获取发布第一步push_shop_commodity_info中的productNameId
                int productNameId = 0;
                Query queryProduct = new Query(Criteria.where("id").is(MapUtils.getString(params,"pushShopCommodityId")));
                //显示哪些字段 不设置默认显示全部
                queryProduct.fields().include("productNameId");
                List<Map> productList = mongoTemplate.find(queryProduct, Map.class, "push_shop_commodity_info");
                if (CollectionUtils.isNotEmpty(productList)) {
                    productNameId = MapUtils.getInteger(productList.get(0), "productNameId");
                }
                Map<String, Object> storeInfo = new HashMap<>();
                if (productNameId != 0) {
                    storeInfo = basicSettingService.findStoreInfo(productNameId);
                    if (MapUtils.isNotEmpty(storeInfo)) {
                        storeInfo.put("productAvailability", MapUtils.getString(storeInfo, "productAvailability"));
                    } else {
                        storeInfo = new HashMap<>();
                    }
                }

                sales = mongoTemplate.find(query, Map.class, "marketing_commodity_sales_info");
                if(CollectionUtils.isNotEmpty(sales)) {
                    //获取库存中的库存量，赋值给productAvailability(产品可售量)
                    sales.get(0).putAll(storeInfo);
                    map.put("sales", sales.get(0));
                    pushSalesTitle = MapUtils.getString(sales.get(0), "title");
                } else {
                    map.put("sales", storeInfo);
                }

                if (flag) {
                    List<Map> warranty = mongoTemplate.find(query, Map.class, "marketing_commodity_warranty_info");
                    if(CollectionUtils.isNotEmpty(warranty)) {
                        map.put("warranty", warranty.get(0));
                        pushWarrantyTitle = MapUtils.getString(warranty.get(0), "title");
                    }
                }

                pricing = mongoTemplate.find(query, Map.class, "marketing_commodity_pricing_info");
                if(CollectionUtils.isNotEmpty(pricing)) {
                    map.put("pricing", pricing.get(0));
                    pushPricingTitle = MapUtils.getString(pricing.get(0), "title");
                }

                if (flag) {
                    List<Map> delivery = mongoTemplate.find(query, Map.class, "marketing_commodity_delivery_info");
                    if(CollectionUtils.isNotEmpty(delivery)) {
                        map.put("delivery", delivery);
                        pushDeliveryTitle = MapUtils.getString(delivery.get(0), "title");
                    }
                }

                afterSales = mongoTemplate.find(query, Map.class, "marketing_commodity_after_info");
                if(CollectionUtils.isNotEmpty(afterSales)) {
                    map.put("afterSales", afterSales.get(0));
                    pushAfterSalesTitle = MapUtils.getString(afterSales.get(0), "title");
                }

                //标题
                List<Map<String,Object>> tabsData = new ArrayList<>();
                Map<String,Object> salesMap = new HashMap<>();
                salesMap.put("name", "sales");
                salesMap.put("title", pushSalesTitle);
                tabsData.add(salesMap);

                Map<String,Object> pricingMap = new HashMap<>();
                pricingMap.put("name", "pricing");
                pricingMap.put("title", pushPricingTitle);
                tabsData.add(pricingMap);

                Map<String,Object> afterSalesMap = new HashMap<>();
                afterSalesMap.put("name", "afterSales");
                afterSalesMap.put("title", pushAfterSalesTitle);
                tabsData.add(afterSalesMap);

                if (flag) {
                    Map<String,Object> warrantyMap = new HashMap<>();
                    warrantyMap.put("name", "warranty");
                    warrantyMap.put("title", pushWarrantyTitle);
                    tabsData.add(warrantyMap);

                    Map<String,Object> deliveryMap = new HashMap<>();
                    deliveryMap.put("name", "delivery");
                    deliveryMap.put("title", pushDeliveryTitle);
                    tabsData.add(deliveryMap);
                }

            } else {
                //标题
                List<Map<String,Object>> tabsData = new ArrayList<>();
                Map<String,Object> salesMap = new HashMap<>();
                salesMap.put("name", "sales");
                salesMap.put("title", salesTitle);
                tabsData.add(salesMap);

                Map<String,Object> pricingMap = new HashMap<>();
                pricingMap.put("name", "pricing");
                pricingMap.put("title", pricingTitle);
                tabsData.add(pricingMap);

                Map<String,Object> afterSalesMap = new HashMap<>();
                afterSalesMap.put("name", "afterSales");
                afterSalesMap.put("title", afterSalesTitle);
                tabsData.add(afterSalesMap);

                if (flag) {
                    Map<String,Object> warrantyMap = new HashMap<>();
                    warrantyMap.put("name", "warranty");
                    warrantyMap.put("title", warrantyTitle);
                    tabsData.add(warrantyMap);

                    Map<String,Object> deliveryMap = new HashMap<>();
                    deliveryMap.put("name", "delivery");
                    deliveryMap.put("title", deliveryTitle);
                    tabsData.add(deliveryMap);

                }
            }

            logger.info("query:{}, result:{}", query, map);
            result.setMsg("查询数据成功");
            result.setData(map);

        }catch (Exception e){
            logger.error("异常信息：", e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg("查询商品模块信息失败");
        }

        return result;
    }

    @RequestMapping(value = "/savePushMarketing",method = RequestMethod.POST)
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
            Query deleteQuery = new Query(Criteria.where("pushShopCommodityId").is(MapUtils.getString(params,"pushShopCommodityId")));

            mongoTemplate.remove(deleteQuery, Map.class, "push_marketing_commodity_sales_info");
            mongoTemplate.remove(deleteQuery, Map.class, "push_marketing_commodity_pricing_info");
            mongoTemplate.remove(deleteQuery, Map.class, "push_marketing_commodity_after_info");

            if (flag) {
                mongoTemplate.remove(deleteQuery, Map.class, "push_marketing_commodity_warranty_info");
                mongoTemplate.remove(deleteQuery, Map.class, "push_marketing_commodity_delivery_info");
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
                sales.put("pushShopCommodityId", MapUtils.getString(params,"pushShopCommodityId"));
                sales.put("create_time", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
                mongoTemplate.save(sales, "push_marketing_commodity_sales_info");
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
                    warranty.put("pushShopCommodityId", MapUtils.getString(params,"pushShopCommodityId"));
                    warranty.put("create_time", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
                    mongoTemplate.save(warranty, "push_marketing_commodity_warranty_info");
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
                pricing.put("pushShopCommodityId", MapUtils.getString(params,"pushShopCommodityId"));
                pricing.put("create_time", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
                mongoTemplate.save(pricing, "push_marketing_commodity_pricing_info");
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
                        delivery.put("pushShopCommodityId", MapUtils.getString(params,"pushShopCommodityId"));
                        delivery.put("create_time", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
                        mongoTemplate.save(delivery, "push_marketing_commodity_delivery_info");

                        Map<String,Object> commonMap = new HashMap<>();
                        commonMap.put("account", MapUtils.getString(params, "account"));
                        commonMap.put("marketingId", id);
                        commonMap.put("type", 1);
                        //保存营业网点信息
                        List<Map<String, Object>> list = (List<Map<String, Object>>) delivery.get("networkStation");
                        if (CollectionUtils.isNotEmpty(list)) {
                            List<Map<String, Object>> networkList = new ArrayList<>();
                            for (Map<String,Object> map : list) {
                                map.put("networkType", map.get("networkType").toString());
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
                afterSales.put("pushShopCommodityId", MapUtils.getString(params,"pushShopCommodityId"));
                afterSales.put("create_time", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
                mongoTemplate.save(afterSales, "push_marketing_commodity_after_info");

                Map<String,Object> commonMap = new HashMap<>();
                commonMap.put("account", MapUtils.getString(params, "account"));
                commonMap.put("marketingId", id);
                commonMap.put("type", 2);
                //保存营业网点信息
                List<Map<String, Object>> list = (List<Map<String, Object>>) afterSales.get("networkStation");
                if (CollectionUtils.isNotEmpty(list)) {
                    List<Map<String, Object>> networkList = new ArrayList<>();
                    for (Map<String,Object> map : list) {
                        map.put("networkType", map.get("networkType").toString());
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

    @RequestMapping(value = "/findPushTrace",method = RequestMethod.POST)
    public Result findPushTrace(@RequestBody(required=false) Map<String,Object> params) {

        Result result = new Result();
        try {
            Query query = new Query();

            Criteria criteria =  Criteria.where("pushShopCommodityId").is(MapUtils.getString(params,"pushShopCommodityId"));
            //查询
            query.addCriteria(criteria);
            query.fields().exclude("_id");

            Map<String,Object> map = new HashMap<>();

            List<Map> info = mongoTemplate.find(query, Map.class, "push_commodity_traceability_info");
            if(CollectionUtils.isNotEmpty(info)) {
                map.put("info", info.get(0));
            }
            if (MapUtils.isEmpty(map)) {
                query = new Query();
                Criteria criteriaold =  Criteria.where("shopPushTemplateId").is(MapUtils.getString(params,"shopPushTemplateId"));
                //查询
                query.addCriteria(criteriaold);
                query.fields().exclude("_id");

                info = mongoTemplate.find(query, Map.class, "commodity_traceability_info");
                if(CollectionUtils.isNotEmpty(info)) {
                    map.put("info", info.get(0));
                }
            }
            result.setMsg("查询数据成功");
            result.setData(map);

        } catch (Exception e) {
            logger.error("异常信息：", e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg("查询发布的商品追溯与防伪信息失败");
        }

        return result;
    }

    @RequestMapping(value = "/savePushTrace",method = RequestMethod.POST)
    @Transactional(propagation = Propagation.REQUIRED)
    public Result savePushTrace(@RequestBody Map<String,Object> params) {

        Result result = new Result();
        try {

            //先删除，在保存
            Query deleteQuery = new Query(Criteria.where("pushShopCommodityId").is(MapUtils.getString(params,"pushShopCommodityId")));

            mongoTemplate.remove(deleteQuery, Map.class, "push_commodity_traceability_info");

            Map<String,Object> info = MapUtils.getMap(params,"info");
            if(MapUtils.isNotEmpty(info)) {
                info.put("id", UuidUtil.get32UUID());
                info.put("shopPushTemplateId", MapUtils.getString(params,"shopPushTemplateId"));
                info.put("account", MapUtils.getString(params,"account"));
                info.put("productCategoryId", MapUtils.getString(params,"productCategoryId"));
                info.put("templateType", MapUtils.getString(params,"templateType"));
                info.put("pushShopCommodityId", MapUtils.getString(params,"pushShopCommodityId"));
                info.put("create_time", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
                mongoTemplate.save(info, "push_commodity_traceability_info");
            }

            result.setMsg("保存成功");

        } catch (Exception e) {
            logger.error("异常信息：", e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg("发布商品追溯与防伪信息失败");
        }
        return result;
    }

    @RequestMapping(value = "/findPushCommitment",method = RequestMethod.POST)
    public Result findPushCommitment(@RequestBody(required=false) Map<String,Object> params) {

        Result result = new Result();
        try {
            Query query = new Query();

            Criteria criteria =  Criteria.where("pushShopCommodityId").is(MapUtils.getString(params,"pushShopCommodityId"));

            //查询
            query.addCriteria(criteria);
            query.fields().exclude("_id");

            Map<String,Object> map = new HashMap<>();

            List<Map> info = mongoTemplate.find(query, Map.class, "push_commodity_commitment_info");
            if(CollectionUtils.isNotEmpty(info)) {
                map.put("info", info.get(0));
            }
            if (MapUtils.isEmpty(map)) {
                query = new Query();
                Criteria criteriaold =  Criteria.where("shopPushTemplateId").is(MapUtils.getString(params,"shopPushTemplateId"));
                //查询
                query.addCriteria(criteriaold);
                query.fields().exclude("_id");

                info = mongoTemplate.find(query, Map.class, "commodity_commitment_info");
                if(CollectionUtils.isNotEmpty(info)) {
                    map.put("info", info.get(0));
                }
            }
            result.setMsg("查询数据成功");
            result.setData(map);

        } catch (Exception e) {
            logger.error("异常信息：", e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg("查询商品承诺信息失败");
        }

        return result;
    }

    @RequestMapping(value = "/savePushCommitment",method = RequestMethod.POST)
    @Transactional(propagation = Propagation.REQUIRED)
    public Result savePushCommitment(@RequestBody Map<String,Object> params) {

        Result result = new Result();
        try {

            //先删除，在保存
            Query deleteQuery = new Query(Criteria.where("pushShopCommodityId").is(MapUtils.getString(params,"pushShopCommodityId")));

            mongoTemplate.remove(deleteQuery, Map.class, "push_commodity_commitment_info");

            Map<String,Object> info = MapUtils.getMap(params,"info");
            if(MapUtils.isNotEmpty(info)) {
                info.put("id", UuidUtil.get32UUID());
                info.put("isNavDisplay",0);
                info.put("isShopDisplay",0);
                info.put("isHomeDisplay",0);
                //是否完成字段，用于判断1-5步是否可以跳转
                info.put("isComplete", "1");
                info.put("shopPushTemplateId", MapUtils.getString(params,"shopPushTemplateId"));
                info.put("account", MapUtils.getString(params,"account"));
                info.put("productCategoryId", MapUtils.getString(params,"productCategoryId"));
                info.put("templateType", MapUtils.getString(params,"templateType"));
                info.put("pushShopCommodityId", MapUtils.getString(params,"pushShopCommodityId"));
                info.put("create_time", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
                mongoTemplate.save(info, "push_commodity_commitment_info");
            }

            //发布完的产品, 默认为已推荐
            Map<String,Object> recommend = new HashMap<>();
            recommend.put("account", MapUtils.getString(params, "account"));
            recommend.put("id", MapUtils.getString(params, "pushShopCommodityId"));
            recommend.put("type", 4);
            //判断是否已推荐
            Map<String, Object> hadRecommend = myRecommendMapper.isHadRecommend(recommend);
            if (MapUtils.isEmpty(hadRecommend)) {
                myRecommendMapper.singleSaveRecommend(recommend);
            }

            result.setMsg("保存成功");

        } catch (Exception e) {
            logger.error("异常信息：", e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg("发布商品承诺信息失败");
        }
        return result;
    }

    @RequestMapping(value = "/pushIsComplete",method = RequestMethod.POST)
    public Result pushIsComplete(@RequestBody Map<String,Object> params) {

        Result result = new Result();
        try {
            Map<String,Object> resultMap = new HashMap<>();
            Query queryComplete = new Query();
            Criteria criteriaComplete =  Criteria.where("account").is(MapUtils.getString(params,"account")).
                    and("pushShopCommodityId").is(MapUtils.getString(params,"goodsId"));
            queryComplete.addCriteria(criteriaComplete);
            queryComplete.fields().include("isComplete");
            List<Map> info = mongoTemplate.find(queryComplete, Map.class, "push_commodity_commitment_info");
            if(CollectionUtils.isNotEmpty(info)) {
                resultMap.put("isComplete", "1");
            } else {
                resultMap.put("isComplete", "0");
            }
            result.setMsg("查询该商品是否发布完成");
            result.setData(resultMap);

        } catch (Exception e) {
            logger.error("异常信息：", e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg("查询该商品是否发布完成失败");
        }
        return result;
    }


}
