package com.ovit.shop.pushTemplate.controller;


import com.mongodb.WriteResult;
import com.ovit.shop.test.controller.MongoDemoController;
import com.ovit.shop.util.Result;
import com.ovit.shop.util.ResultCode;
import com.ovit.shop.util.UuidUtil;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 商品发布第三步
 */
@RestController
@RequestMapping("/pushTemplateThree")
public class PushTemplateThreeController {

    private static final Logger logger = LogManager.getLogger(MongoDemoController.class);

    @Autowired
    private MongoTemplate mongoTemplate;


    @RequestMapping(value = "/savePush",method = RequestMethod.POST)
    public Result savePush(@RequestBody Map<String,Object> params) {

        Result result = new Result();
        try {
            //先删除，在保存
            Query deleteQuery = new Query(Criteria.where("shopPushTemplateId").is(MapUtils.getString(params,"shopPushTemplateId")));
            mongoTemplate.remove(deleteQuery, Map.class, "basic_commodity_info");

            mongoTemplate.remove(deleteQuery, Map.class, "basic_commodity_intangibleAssets_info");

            mongoTemplate.remove(deleteQuery, Map.class, "basic_commodity_production_info");

            mongoTemplate.remove(deleteQuery, Map.class, "basic_commodity_place_info");

            mongoTemplate.remove(deleteQuery, Map.class, "basic_commodity_location_info");

            mongoTemplate.remove(deleteQuery, Map.class, "basic_commodity_qualification_info");

            mongoTemplate.remove(deleteQuery, Map.class, "basic_commodity_honor_info");

            mongoTemplate.remove(deleteQuery, Map.class, "basic_commodity_quality_info");

            mongoTemplate.remove(deleteQuery, Map.class, "basic_commodity_safety_standard");

            mongoTemplate.remove(deleteQuery, Map.class, "basic_international_commodity_barcode");



            //商品信息
            Map<String,Object> product = MapUtils.getMap(params,"product");
            if(MapUtils.isNotEmpty(product)){
                product.put("id",UuidUtil.get32UUID());
                product.put("shopPushTemplateId",MapUtils.getString(params,"shopPushTemplateId"));
                product.put("account",MapUtils.getString(params,"account"));
                product.put("productCategoryId",MapUtils.getString(params,"productCategoryId"));
                product.put("templateType",MapUtils.getString(params,"templateType"));
                product.put("create_time", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
                mongoTemplate.save(product, "basic_commodity_info");
            }

            //商品信息-无形资产
            Map<String,Object> intangibleAssets = MapUtils.getMap(params,"intangibleAssets");
            if(MapUtils.isNotEmpty(intangibleAssets)){
                intangibleAssets.put("id",UuidUtil.get32UUID());
                intangibleAssets.put("shopPushTemplateId",MapUtils.getString(params,"shopPushTemplateId"));
                intangibleAssets.put("account",MapUtils.getString(params,"account"));
                intangibleAssets.put("productCategoryId",MapUtils.getString(params,"productCategoryId"));
                intangibleAssets.put("templateType",MapUtils.getString(params,"templateType"));
                intangibleAssets.put("create_time", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
                mongoTemplate.save(intangibleAssets, "basic_commodity_intangibleAssets_info");
            }

            //商品生产信息
            Map<String,Object> production = MapUtils.getMap(params,"production");
            if(MapUtils.isNotEmpty(production)){
                production.put("id",UuidUtil.get32UUID());
                production.put("shopPushTemplateId",MapUtils.getString(params,"shopPushTemplateId"));
                production.put("account",MapUtils.getString(params,"account"));
                production.put("productCategoryId",MapUtils.getString(params,"productCategoryId"));
                production.put("templateType",MapUtils.getString(params,"templateType"));
                production.put("create_time", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
                mongoTemplate.save(production, "basic_commodity_production_info");
            }

            //商品产地信息
            Map<String,Object> origin = MapUtils.getMap(params,"origin");
            if(MapUtils.isNotEmpty(origin)){
                origin.put("id",UuidUtil.get32UUID());
                origin.put("shopPushTemplateId",MapUtils.getString(params,"shopPushTemplateId"));
                origin.put("account",MapUtils.getString(params,"account"));
                origin.put("productCategoryId",MapUtils.getString(params,"productCategoryId"));
                origin.put("templateType",MapUtils.getString(params,"templateType"));
                origin.put("create_time", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
                mongoTemplate.save(origin, "basic_commodity_place_info");
            }

            //商品所在地信息
            Map<String,Object> productLocation = MapUtils.getMap(params,"productLocation");
            if(MapUtils.isNotEmpty(productLocation)){
                productLocation.put("id",UuidUtil.get32UUID());
                productLocation.put("shopPushTemplateId",MapUtils.getString(params,"shopPushTemplateId"));
                productLocation.put("account",MapUtils.getString(params,"account"));
                productLocation.put("productCategoryId",MapUtils.getString(params,"productCategoryId"));
                productLocation.put("templateType",MapUtils.getString(params,"templateType"));
                productLocation.put("create_time", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
                mongoTemplate.save(productLocation, "basic_commodity_location_info");
            }

            //资质信息
            Map<String,Object> qualification = MapUtils.getMap(params,"qualification");
            if(MapUtils.isNotEmpty(qualification)){
                qualification.put("id",UuidUtil.get32UUID());
                qualification.put("shopPushTemplateId",MapUtils.getString(params,"shopPushTemplateId"));
                qualification.put("account",MapUtils.getString(params,"account"));
                qualification.put("productCategoryId",MapUtils.getString(params,"productCategoryId"));
                qualification.put("templateType",MapUtils.getString(params,"templateType"));
                qualification.put("create_time", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
                mongoTemplate.save(qualification, "basic_commodity_qualification_info");
            }


            //商品荣誉信息
            Map<String,Object> honor = MapUtils.getMap(params,"honor");
            if(MapUtils.isNotEmpty(honor)){
                honor.put("id",UuidUtil.get32UUID());
                honor.put("shopPushTemplateId",MapUtils.getString(params,"shopPushTemplateId"));
                honor.put("account",MapUtils.getString(params,"account"));
                honor.put("productCategoryId",MapUtils.getString(params,"productCategoryId"));
                honor.put("templateType",MapUtils.getString(params,"templateType"));
                honor.put("create_time", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
                mongoTemplate.save(honor, "basic_commodity_honor_info");
            }


            //商品质量信息
            Map<String,Object> quality = MapUtils.getMap(params,"quality");
            if(MapUtils.isNotEmpty(quality)){
                quality.put("id",UuidUtil.get32UUID());
                quality.put("shopPushTemplateId",MapUtils.getString(params,"shopPushTemplateId"));
                quality.put("account",MapUtils.getString(params,"account"));
                quality.put("productCategoryId",MapUtils.getString(params,"productCategoryId"));
                quality.put("templateType",MapUtils.getString(params,"templateType"));
                quality.put("create_time", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
                mongoTemplate.save(quality, "basic_commodity_quality_info");
            }


            //商品安全标准
            Map<String,Object> safety = MapUtils.getMap(params,"safety");
            if(MapUtils.isNotEmpty(safety)){
                safety.put("id",UuidUtil.get32UUID());
                safety.put("shopPushTemplateId",MapUtils.getString(params,"shopPushTemplateId"));
                safety.put("account",MapUtils.getString(params,"account"));
                safety.put("productCategoryId",MapUtils.getString(params,"productCategoryId"));
                safety.put("templateType",MapUtils.getString(params,"templateType"));
                safety.put("create_time", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
                mongoTemplate.save(safety, "basic_commodity_safety_standard");
            }

            //商品安全标准
            Map<String,Object> barcode = MapUtils.getMap(params,"barcode");
            if(MapUtils.isNotEmpty(barcode)){
                barcode.put("id",UuidUtil.get32UUID());
                barcode.put("shopPushTemplateId",MapUtils.getString(params,"shopPushTemplateId"));
                barcode.put("account",MapUtils.getString(params,"account"));
                barcode.put("productCategoryId",MapUtils.getString(params,"productCategoryId"));
                barcode.put("templateType",MapUtils.getString(params,"templateType"));
                barcode.put("create_time", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
                mongoTemplate.save(barcode, "basic_international_commodity_barcode");
            }

            result.setMsg("保存成功");
        }catch (Exception e){
            logger.error("异常信息：", e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg("保存商品发布第三步信息失败");
        }
        return result;
    }


    @RequestMapping(value = "/findTemplateList",method = RequestMethod.POST)
    public Result findTemplateList(@RequestBody(required=false) Map<String,Object> params) {

        Result result = new Result();
        try {
            Query query = new Query();

            Criteria criteria =  Criteria.where("account").is(MapUtils.getString(params,"account"));
            if(StringUtils.isNotBlank(MapUtils.getString(params,"isUse"))){
                criteria.and("isUse").is(MapUtils.getString(params,"isUse"));
            }
            if(StringUtils.isNotBlank(MapUtils.getString(params,"templateType"))){
                criteria.and("templateType").is(MapUtils.getString(params,"templateType"));
            }

            if(StringUtils.isNotBlank(MapUtils.getString(params,"templateName"))){
                criteria.and("templateName").regex(MapUtils.getString(params,"templateName"));
            }

            if(StringUtils.isNotBlank(MapUtils.getString(params,"productCategoryId"))){
                criteria.and("productCategoryId").is(MapUtils.getString(params,"productCategoryId"));
            }

            if(StringUtils.isNotBlank(MapUtils.getString(params,"shopPushTemplateId"))){
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

            List<Map> product = mongoTemplate.find(query, Map.class, "basic_commodity_info");
            if(product!=null && product.size()>0){
                map.put("product",product);
            }else{
                if(MapUtils.getString(params,"templateType").equals("1") ){
                    product = mongoTemplate.find(queryTwo, Map.class, "basic_commodity_info");
                    map.put("product",product);
                }else{
                    map.put("product",new String[]{});
                }
            }


            List<Map> intangibleAssets = mongoTemplate.find(query, Map.class, "basic_commodity_intangibleAssets_info");
            if(intangibleAssets!=null && intangibleAssets.size()>0){
                map.put("intangibleAssets",intangibleAssets);
            }else{
                if(MapUtils.getString(params,"templateType").equals("1") ){
                    intangibleAssets = mongoTemplate.find(queryTwo, Map.class, "basic_commodity_intangibleAssets_info");
                    map.put("intangibleAssets",intangibleAssets);
                }else{
                    map.put("intangibleAssets",new String[]{});
                }
            }

            List<Map> production = mongoTemplate.find(query, Map.class, "basic_commodity_production_info");
            if(production!=null && production.size()>0){
                map.put("production",production);
            }else{
                if(MapUtils.getString(params,"templateType").equals("1") ){
                    production = mongoTemplate.find(queryTwo, Map.class, "basic_commodity_production_info");
                    map.put("production",production);
                }else{
                    map.put("production",new String[]{});
                }

            }

            List<Map> origin = mongoTemplate.find(query, Map.class, "basic_commodity_place_info");
            if(origin!=null && origin.size()>0){
                map.put("origin",origin);
            }else{
                if(MapUtils.getString(params,"templateType").equals("1") ){
                    origin = mongoTemplate.find(queryTwo, Map.class, "basic_commodity_place_info");
                    map.put("origin",origin);
                }else{
                    map.put("origin",new String[]{});
                }

            }

            List<Map> honor = mongoTemplate.find(query, Map.class, "basic_commodity_honor_info");
            if(honor!=null && honor.size()>0){
                map.put("honor",honor);
            }else{
                if(MapUtils.getString(params,"templateType").equals("1") ){
                    honor = mongoTemplate.find(queryTwo, Map.class, "basic_commodity_honor_info");
                    map.put("honor",honor);
                }else{
                    map.put("honor",new String[]{});
                }

            }


            List<Map> productLocation = mongoTemplate.find(query, Map.class, "basic_commodity_location_info");
            if(productLocation!=null && productLocation.size()>0){
                map.put("productLocation",productLocation);
            }else{
                if(MapUtils.getString(params,"templateType").equals("1") ){
                    productLocation = mongoTemplate.find(queryTwo, Map.class, "basic_commodity_location_info");
                    map.put("productLocation",productLocation);
                }else{
                    map.put("productLocation",new String[]{});
                }

            }

            List<Map> qualification = mongoTemplate.find(query, Map.class, "basic_commodity_qualification_info");
            if(qualification!=null && qualification.size()>0){
                map.put("qualification",qualification);
            }else{
                if(MapUtils.getString(params,"templateType").equals("1") ){
                    qualification = mongoTemplate.find(queryTwo, Map.class, "basic_commodity_qualification_info");
                    map.put("qualification",qualification);
                }else{
                    map.put("qualification",new String[]{});
                }

            }



            List<Map> quality = mongoTemplate.find(query, Map.class, "basic_commodity_quality_info");
            if(quality!=null && quality.size()>0){
                map.put("quality",quality);
            }else{
                if(MapUtils.getString(params,"templateType").equals("1") ){
                    quality = mongoTemplate.find(queryTwo, Map.class, "basic_commodity_quality_info");
                    map.put("quality",quality);
                }else{
                    map.put("quality",new String[]{});
                }

            }

            List<Map> safety = mongoTemplate.find(query, Map.class, "basic_commodity_safety_standard");
            if(safety!=null && safety.size()>0){
                map.put("safety",safety);
            }else{
                if(MapUtils.getString(params,"templateType").equals("1") ){
                    safety = mongoTemplate.find(queryTwo, Map.class, "basic_commodity_safety_standard");
                    map.put("safety",safety);
                }else{
                    map.put("safety",new String[]{});
                }

            }

            List<Map> barcode = mongoTemplate.find(query, Map.class, "basic_international_commodity_barcode");
            if(barcode!=null && barcode.size()>0){
                map.put("barcode",barcode);
            }else{
                if(MapUtils.getString(params,"templateType").equals("1") ){
                    barcode = mongoTemplate.find(queryTwo, Map.class, "basic_international_commodity_barcode");
                    map.put("barcode",barcode);
                }else{
                    map.put("barcode",new String[]{});
                }

            }

            result.setMsg("查询数据成功");
            result.setData(map);

        }catch (Exception e){
            logger.error("异常信息：", e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg("查询商品发布模块信息失败");
        }

        return result;
    }



}
