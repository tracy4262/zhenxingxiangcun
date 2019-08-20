package com.ovit.shop.pushTemplate.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ovit.shop.pushTemplate.mapper.CommodityMarketingMapper;
import com.ovit.shop.pushTemplate.mapper.ShopBiddingMapper;
import com.ovit.shop.util.Result;
import com.ovit.shop.util.ResultCode;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;

import org.apache.commons.collections.MapUtils;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.Map;

@RestController
@RequestMapping("/commodityDetail")
public class CommodityDetailController {

    private static final Logger logger = LogManager.getLogger(PushShopInfoController.class);

    @Autowired
    private MongoTemplate mongoTemplate;
    
    @Autowired
    private CommodityMarketingMapper commodityMarketingMapper;

    @Autowired
    private ShopBiddingMapper shopBiddingMapper;

    @RequestMapping(value = "/findCommodityDetailFirst",method = RequestMethod.POST)
    public Result findCommodityDetailFirst(@RequestBody(required=false) Map<String,Object> params) {

        Result result = new Result();
        try {


            Query query = new Query();

            Criteria criteria =  Criteria.where("id").is(MapUtils.getString(params,"pushShopCommodityId"));

            //查询
            query.addCriteria(criteria);
            query.fields().exclude("_id");
            Map<String,Object> map = new HashMap<>();

            List<Map> shopCommodity = mongoTemplate.find(query, Map.class, "push_shop_commodity_info");
            if(shopCommodity!=null && shopCommodity.size()>0){
                map.put("commodity",shopCommodity);

            }

            logger.info("query:{}, result:{}", query, map);
            result.setMsg("查询数据成功");
            result.setData(map);

        }catch (Exception e){
            logger.error("异常信息：", e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg("查询通用商品基本信息失败");
        }

        return result;
    }

    @RequestMapping(value = "/findCommodityDetailTwo",method = RequestMethod.POST)
    public Result findCommodityDetailTwo(@RequestBody(required=false) Map<String,Object> params) {

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

            logger.info("query:{}, result:{}", query, map);
            result.setMsg("查询数据成功");
            result.setData(map);

        }catch (Exception e){
            logger.error("异常信息：", e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg("查询商品基本信息失败");
        }

        return result;
    }


    @RequestMapping(value = "/findCommodityDetailThree",method = RequestMethod.POST)
    public Result findCommodityDetailThree(@RequestBody(required=false) Map<String,Object> params) {

        Result result = new Result();
        try {


            Query query = new Query();

            Criteria criteria =  Criteria.where("pushShopCommodityId").is(MapUtils.getString(params,"pushShopCommodityId"));

            //查询
            query.addCriteria(criteria);
            query.fields().exclude("_id");
            Map<String,Object> map = new HashMap<>();

            String productCategoryId = MapUtils.getString(params,"productCategoryId");
            //判断是否是无形产品
            boolean flag = !"CP09".equals(productCategoryId);

            List<Map> sales = mongoTemplate.find(query, Map.class, "push_marketing_commodity_sales_info");
            if(CollectionUtils.isNotEmpty(sales)) {
                map.put("sales", sales.get(0));
            }

            if (flag) {
                List<Map> warranty = mongoTemplate.find(query, Map.class, "push_marketing_commodity_warranty_info");
                if(CollectionUtils.isNotEmpty(warranty)) {
                    map.put("warranty", warranty.get(0));
                }
            }

            List<Map> pricing = mongoTemplate.find(query, Map.class, "push_marketing_commodity_pricing_info");
            if(CollectionUtils.isNotEmpty(pricing)) {
                map.put("pricing", pricing.get(0));

            }

            if (flag) {
                List<Map> delivery = mongoTemplate.find(query, Map.class, "push_marketing_commodity_delivery_info");
                if(CollectionUtils.isNotEmpty(delivery)) {
                    map.put("delivery", delivery);

                }
            }

            List<Map> afterSales = mongoTemplate.find(query, Map.class, "push_marketing_commodity_after_info");
            if(CollectionUtils.isNotEmpty(afterSales)) {
                map.put("afterSales", afterSales.get(0));

            }

            logger.info("query:{}, result:{}", query, map);
            result.setMsg("查询数据成功");
            result.setData(map);

        }catch (Exception e){
            logger.error("异常信息：", e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg("查询商品营销基本信息失败");
        }

        return result;
    }

    @RequestMapping(value = "/findCommodityDetailFour",method = RequestMethod.POST)
    public Result findCommodityDetailFour(@RequestBody(required=false) Map<String,Object> params) {

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

            result.setMsg("查询数据成功");
            result.setData(map);

        } catch (Exception e) {
            logger.error("异常信息：", e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg("查询发布的商品追溯与防伪信息失败");
        }

        return result;
    }

    @RequestMapping(value = "/findCommodityDetailFive",method = RequestMethod.POST)
    public Result findCommodityDetailFive(@RequestBody(required=false) Map<String,Object> params) {

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

            result.setMsg("查询数据成功");
            result.setData(map);

        } catch (Exception e) {
            logger.error("异常信息：", e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg("查询商品承诺信息失败");
        }

        return result;
    }

    @RequestMapping(value = "/findCommodityDetailInfo",method = RequestMethod.POST)
    public Result findCommodityDetailInfo(@RequestBody(required=false) Map<String,Object> params) {

        Result result = new Result();
        try {
            Query query = new Query();

            Criteria criteria =  Criteria.where("pushShopCommodityId").is(MapUtils.getString(params,"pushShopCommodityId"));

            //查询
            query.addCriteria(criteria);
            query.fields().exclude("_id");

            Map<String,Object> map = new HashMap<>();

            //商品名
            List<Map> product = mongoTemplate.find(query, Map.class, "push_basic_commodity_info");
            if(product!=null && product.size()>0){
                map.put("productName",MapUtils.getString(product.get(0),"productName"));
            }

            Query querys = new Query();

            Criteria criterias =  Criteria.where("id").is(MapUtils.getString(params,"pushShopCommodityId"));

            //查询
            querys.addCriteria(criterias);
            querys.fields().exclude("_id");

            //图片
            List<Map> shopCommodity = mongoTemplate.find(querys, Map.class, "push_shop_commodity_info");
            if(shopCommodity!=null && shopCommodity.size()>0){
                String notarizationCertificate  = JSON.toJSONString(shopCommodity.get(0).get("notarizationCertificate"));
                if (String.valueOf(notarizationCertificate).contains("[")) {
                    map.put("notarizationCertificate", JSONObject.parse(notarizationCertificate));
                }


            }

            //商品产地信息
            List<Map> placeLocation = mongoTemplate.find(query, Map.class, "push_basic_commodity_place_info");
            if(placeLocation!=null && placeLocation.size()>0){
                map.put("productOrigin",MapUtils.getString(placeLocation.get(0),"productOrigin"));
                map.put("productOriginAddress",MapUtils.getString(placeLocation.get(0),"productOriginAddress"));
                map.put("addrDetail",MapUtils.getString(placeLocation.get(0),"addrDetail"));

            }

            //产地所在地信息
            List<Map> productLocation = mongoTemplate.find(query, Map.class, "push_basic_commodity_location_info");
            if(productLocation!=null && productLocation.size()>0){
                map.put("productLocation",MapUtils.getString(productLocation.get(0),"productOrigin"));
                map.put("productLocationAddress",MapUtils.getString(productLocation.get(0),"productOriginAddress"));
                map.put("productAddrDetail",MapUtils.getString(productLocation.get(0),"addrDetail"));

            }

            List<Map> sales = mongoTemplate.find(query, Map.class, "push_marketing_commodity_sales_info");
            if(CollectionUtils.isNotEmpty(sales)) {

                map.put("productStatus", MapUtils.getString(sales.get(0),"productStatus"));// 产品状态

                map.put("productAvailability", MapUtils.getString(sales.get(0),"productAvailability"));// 产品可售量

                map.put("productAvailabilityUnits", MapUtils.getString(sales.get(0),"productAvailabilityUnits"));// 产品可售量单位

                map.put("productSalesVolume", MapUtils.getString(sales.get(0),"productSalesVolume"));//产品起售量

                map.put("maximumSingleShipment", MapUtils.getString(sales.get(0),"maximumSingleShipment"));// 单次最大供货量



            }


            //价格
            List<Map> pricing = mongoTemplate.find(query, Map.class, "push_marketing_commodity_pricing_info");
            if(CollectionUtils.isNotEmpty(pricing)) {
                map.put("pricing", pricing.get(0));

            }

            //判断是否是无形产品
            boolean flag = !"CP09".equals(MapUtils.getString(product.get(0),"productCategoryId"));


            //发货方式
            if (flag) {
                List<Map> delivery = mongoTemplate.find(query, Map.class, "push_marketing_commodity_delivery_info");
                if(CollectionUtils.isNotEmpty(delivery)) {

                    map.put("delivery", delivery);

                }
            }

            List<Map> info = mongoTemplate.find(query, Map.class, "push_commodity_traceability_info");
            if(CollectionUtils.isNotEmpty(info)) {
                map.put("isRetrospect", MapUtils.getString(info.get(0),"isRetrospect"));
                String productionBase = MapUtils.getString(info.get(0), "productionBase");
                if (productionBase != null) {
                    String baseName = commodityMarketingMapper.getBaseName(productionBase);
                    map.put("productionBaseName", baseName);
                }
                map.put("productionBase", productionBase);
                map.put("securityInformation", MapUtils.getString(info.get(0),"securityInformation"));
                map.put("antiFake", MapUtils.getString(info.get(0),"antiFake"));
            }

            //获取该用户对该商品的竞价状态
            Map<String,Object> bidding = new HashMap<>();
            bidding.put("account", MapUtils.getString(params,"account"));
            bidding.put("commodityId", MapUtils.getString(params,"pushShopCommodityId"));
            Integer biddingStatus = shopBiddingMapper.getBiddingStatus(bidding);
            if (biddingStatus != null) {
                map.put("biddingStatus", biddingStatus);
            } else {
                map.put("biddingStatus", 0);
            }

            Integer salesNumber = shopBiddingMapper.findSalesNumber(bidding);
            map.put("salesNumber",salesNumber);

            Integer participantCount = shopBiddingMapper.participantCount(bidding);
            if (participantCount != null) {
                map.put("participantCount", participantCount);
            }

            Integer rate = shopBiddingMapper.findRate(params);
            if(rate!=null){
                map.put("rate",rate);
            }else {
                map.put("rate",0);
            }


            result.setMsg("查询数据成功");
            result.setData(map);

        } catch (Exception e) {
            logger.error("异常信息：", e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg("查询商品详细信息失败");
        }

        return result;
    }

    @RequestMapping(value = "/findRelatedCommodity",method = RequestMethod.POST)
    public Result findRelatedCommodity(@RequestBody(required=false) Map<String,Object> params) {

        Result result = new Result();
        List<Map<String,Object>> list = new ArrayList<>();
        try {

            Query query = new Query();
            Criteria criteria =  Criteria.where("id").is(MapUtils.getString(params,"pushShopCommodityId"));

            //查询
            query.addCriteria(criteria);
            query.fields().exclude("_id");

            //商品
            String commodityName = "";
            List<Map> product = mongoTemplate.find(query, Map.class, "push_shop_commodity_info");
            if(product!=null && product.size()>0){
                commodityName = MapUtils.getString(product.get(0),"commodityName");
            }

            Query querys = new Query();
            Criteria criterias = Criteria.where("commodityName").is(commodityName);

            //查询
            querys.addCriteria(criterias);
            querys.fields().exclude("_id").exclude("productName");

            //关联商品
            List<Map> shopCommodity = mongoTemplate.find(querys, Map.class, "push_shop_commodity_info");
            if(shopCommodity!=null && shopCommodity.size()>0){
                for(Map map:shopCommodity){
                    Query query1 = new Query();
                    Criteria criteria1 =  Criteria.where("pushShopCommodityId").is(MapUtils.getString(map,"id"));
                    query1.addCriteria(criteria1);

                    //商品名
                    List<Map> productName = mongoTemplate.find(query1, Map.class, "push_basic_commodity_info");
                    if(productName!=null && productName.size()>0){
                        map.put("productName",MapUtils.getString(productName.get(0),"productName"));

                    }

                    //图片
                    String notarizationCertificate  = JSON.toJSONString(map.get("notarizationCertificate"));
                    if (String.valueOf(notarizationCertificate).contains("[")) {
                        map.put("notarizationCertificate", JSONObject.parse(notarizationCertificate));
                    }

                    //价钱
                    List<Map> pricing = mongoTemplate.find(query1, Map.class, "push_marketing_commodity_pricing_info");
                    if(CollectionUtils.isNotEmpty(pricing)) {
                        map.put("pricing", pricing.get(0));
                    }

                    //定价or预售
                    List<Map> productStatus = mongoTemplate.find(query1, Map.class, "push_marketing_commodity_sales_info");
                    if(CollectionUtils.isNotEmpty(productStatus)) {
                        map.put("productStatus", MapUtils.getString(productStatus.get(0),"productStatus"));
                    }

                    params.put("commodityId",MapUtils.getString(map,"id"));
                    //已售
                    Integer salesNumber = shopBiddingMapper.findSalesNumber(params);
                    map.put("salesNumber",salesNumber);

                    Integer buyers = shopBiddingMapper.findBuyer(params);
                    if (buyers != null) {
                        map.put("buyers",buyers);
                    }

                    if(!MapUtils.getString(params,"pushShopCommodityId").equals(MapUtils.getString(map,"id"))
                            &&MapUtils.getString(productName.get(0),"storageStatus").equals("3")
                            &&MapUtils.getString(productName.get(0),"isDelete").equals("0")
                    ){
                        list.add(map);
                    }

                    //排序
                    Collections.sort(list, new Comparator<Map<String,Object>>() {
                        @Override
                        public int compare(Map<String,Object> b1, Map<String,Object> b2) {
                            return MapUtils.getInteger(b2,"salesNumber").compareTo(MapUtils.getInteger(b1,"salesNumber"));

                        }
                    });
                }

            }

            result.setMsg("查询数据成功");
            result.setData(list);

        } catch (Exception e) {
            logger.error("异常信息：", e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg("查询商品承诺信息失败");
        }

        return result;
    }

}
