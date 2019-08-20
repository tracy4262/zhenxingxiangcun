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
import java.util.List;
import java.util.Map;

/**
 * 商品发布第二步
 */
@RestController
@RequestMapping("/pushTemplateTwo")
public class PushTemplateTwoController {

    private static final Logger logger = LogManager.getLogger(MongoDemoController.class);

    @Autowired
    private MongoTemplate mongoTemplate;


    @RequestMapping(value = "/savePush",method = RequestMethod.POST)
    public Result savePush(@RequestBody Map<String,Object> params) {

        Result result = new Result();
        try {

            //先删除，在保存
            Query deleteQuery = new Query(Criteria.where("shopPushTemplateId").is(MapUtils.getString(params,"shopPushTemplateId")));
            mongoTemplate.remove(deleteQuery, Map.class, "general_commodity_basic_info");

            String id = UuidUtil.get32UUID();
            if(StringUtils.isBlank(MapUtils.getString(params,"id"))){
                params.put("id",id);
            }
            params.put("create_time", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            mongoTemplate.save(params, "general_commodity_basic_info");
            result.setMsg("保存成功");
        }catch (Exception e){
            logger.error("异常信息：", e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg("保存商品发布第二步信息失败");
        }
        return result;
    }


    @RequestMapping(value = "/findTemplateList",method = RequestMethod.POST)
    public Result findTemplateList(@RequestBody Map<String,Object> params) {

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
            List<Map> list = mongoTemplate.find(query, Map.class, "general_commodity_basic_info");
            if(list== null|| list.size()== 0 ){
                if(MapUtils.getString(params,"templateType").equals("1")){
                    query = new Query();
                    Criteria criteriaOld =  Criteria.where("account").is(MapUtils.getString(params,"account"))
                            .and("productCategoryId").is(MapUtils.getString(params,"productCategoryId"));
                    query.addCriteria(criteriaOld);
                    query.fields().exclude("_id");
                    list = mongoTemplate.find(query, Map.class, "general_commodity_basic_info");
                }

            }

            logger.info("query:{}, result:{}", query, list);
            result.setMsg("查询数据成功");
            result.setData(list);

        }catch (Exception e){
            logger.error("异常信息：", e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg("查询商品发布第二步信息失败");
        }

        return result;
    }





}
