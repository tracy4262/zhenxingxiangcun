package com.ovit.shop.pushTemplate.controller;

import com.ovit.shop.util.Result;
import com.ovit.shop.util.ResultCode;
import com.ovit.shop.util.UuidUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/commodityCommitment")
public class CommodityCommitmentController {

    private static final Logger logger = LogManager.getLogger(CommodityCommitmentController.class);

    @Autowired
    private MongoTemplate mongoTemplate;


    @RequestMapping(value = "/save",method = RequestMethod.POST)
    @Transactional(propagation = Propagation.REQUIRED)
    public Result save(@RequestBody Map<String,Object> params) {

        Result result = new Result();
        try {

            //先删除，在保存
            Query deleteQuery = new Query(Criteria.where("shopPushTemplateId").is(MapUtils.getString(params,"shopPushTemplateId")));

            mongoTemplate.remove(deleteQuery, Map.class, "commodity_commitment_info");

            Map<String,Object> info = MapUtils.getMap(params,"info");
            if(MapUtils.isNotEmpty(info)) {
                info.put("id", UuidUtil.get32UUID());
                info.put("shopPushTemplateId", MapUtils.getString(params,"shopPushTemplateId"));
                info.put("account", MapUtils.getString(params,"account"));
                info.put("productCategoryId", MapUtils.getString(params,"productCategoryId"));
                info.put("templateType", MapUtils.getString(params,"templateType"));
                info.put("create_time", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
                mongoTemplate.save(info, "commodity_commitment_info");
            }

            result.setMsg("保存成功");

        } catch (Exception e) {
            logger.error("异常信息：", e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg("保存商品发布第六步信息失败");
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

            List<Map> info = mongoTemplate.find(query, Map.class, "commodity_commitment_info");
            if(CollectionUtils.isNotEmpty(info)) {
                map.put("info", info.get(0));
            } else {
                if(MapUtils.getString(params,"templateType").equals("1")) {
                    info = mongoTemplate.find(queryTwo, Map.class, "commodity_commitment_info");
                    if (CollectionUtils.isNotEmpty(info)) {
                        map.put("info", info.get(0));
                    }
                }
            }
            result.setMsg("查询数据成功");
            result.setData(map);

        } catch (Exception e) {
            logger.error("异常信息：", e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg("查询商品发布模块信息失败");
        }

        return result;
    }

}
