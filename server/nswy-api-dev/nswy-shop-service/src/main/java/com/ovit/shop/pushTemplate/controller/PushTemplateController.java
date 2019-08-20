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
import java.util.*;

/**
 * 商品发布第一步
 */
@RestController
@RequestMapping("/pushTemplate")
public class PushTemplateController {

    private static final Logger logger = LogManager.getLogger(MongoDemoController.class);

    @Autowired
    private MongoTemplate mongoTemplate;


    @RequestMapping(value = "/savePush",method = RequestMethod.POST)
    public Result savePush(@RequestBody Map<String,Object> params) {

        Result result = new Result();
        try {
            String id = UuidUtil.get32UUID();
            params.put("id",id);
            params.put("create_time", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            mongoTemplate.save(params, "shop_push_template");
            result.setMsg("保存成功");
        }catch (Exception e){
            logger.error("异常信息：", e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg("保存商品发布模块信息失败");
        }
        return result;
    }


    @RequestMapping(value = "/findTemplateList",method = RequestMethod.POST)
    public Result findTemplateList(@RequestBody(required=false) Map<String,Object> params) {

        Result result = new Result();
        try {
            Query query = new Query().with(new Sort(Sort.Direction.DESC, "create_time"));

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

            //查询
            query.addCriteria(criteria);
            query.fields().exclude("_id");
            List<Map> list = mongoTemplate.find(query, Map.class, "shop_push_template");
            logger.info("query:{}, result:{}", query, list);
            result.setMsg("查询数据成功");
            result.setData(list);

        }catch (Exception e){
            logger.error("异常信息：", e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg("查询商品发布模块信息失败");
        }

        return result;
    }

    @RequestMapping(value = "/updateTemplate",method = RequestMethod.POST)
    public Result updateTemplate(@RequestBody Map<String,Object> params) {

        Result result = new Result();
        try {
            //修改
            Query modifyQuery = new Query(Criteria.where("id").is(MapUtils.getString(params,"id")));
            Update update = new Update();
            if(StringUtils.isNotBlank(MapUtils.getString(params,"isUse"))){
                update.set("isUse", MapUtils.getString(params,"isUse"));
            }

            if(StringUtils.isNotBlank(MapUtils.getString(params,"introduction"))){
                update.set("introduction", MapUtils.getString(params,"introduction"));
            }

            if(StringUtils.isNotBlank(MapUtils.getString(params,"templateName"))){
                update.set("templateName", MapUtils.getString(params,"templateName"));
            }

            WriteResult updateWriteResult = mongoTemplate.updateFirst(modifyQuery, update, Map.class, "shop_push_template");
            logger.info("modifyQuery:{}, update:{}, updateWriteResult:{}", modifyQuery, update, updateWriteResult);
            result.setMsg("修改数据成功");

        }catch (Exception e){
            logger.error("异常信息：", e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg("查询商品发布模块信息失败");
        }

        return result;
    }


    @RequestMapping(value = "/saveTemplateStep",method = RequestMethod.POST)
    public Result saveTemplateStep(@RequestBody Map<String,Object> params) {

        Result result = new Result();
        try {

            //先去查询原先使用的是哪个模版， 然后将其修改为 未使用， 在进行修改
            Query query = new Query().with(new Sort(Sort.Direction.DESC, "create_time"));

            Criteria criteria =  Criteria.where("account").is(MapUtils.getString(params,"account")).and("isUse").is(MapUtils.getString(params,"isUse"));
            //查询
            query.addCriteria(criteria);
            query.fields().exclude("_id");
            List<Map> list = mongoTemplate.find(query, Map.class, "shop_push_template");
            if(list!=null && list.size()>0){
                Map<String,Object> map = list.get(0);
                Query modify= new Query(Criteria.where("id").is(MapUtils.getString(map,"id")));
                mongoTemplate.updateFirst(modify, new Update().set("isUse", "0"), Map.class, "shop_push_template");
            }

            //修改
            Query modifyQuery = new Query(Criteria.where("id").is(MapUtils.getString(params,"id")));
            Update update = new Update();
            if(StringUtils.isNotBlank(MapUtils.getString(params,"isUse"))){
                update.set("isUse", MapUtils.getString(params,"isUse"));
            }

            WriteResult updateWriteResult = mongoTemplate.updateFirst(modifyQuery, update, Map.class, "shop_push_template");
            logger.info("modifyQuery:{}, update:{}, updateWriteResult:{}", modifyQuery, update, updateWriteResult);
            result.setMsg("修改数据成功");

        }catch (Exception e){
            logger.error("异常信息：", e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg("查询商品发布模块信息失败");
        }

        return result;
    }


    @RequestMapping(value = "/deleteTemplate",method = RequestMethod.POST)
    public Result deleteTemplate(@RequestBody Map<String,Object> params) {

        Result result = new Result();
        try {
            //删除
            Query deleteQuery = new Query(Criteria.where("id").is(MapUtils.getString(params,"id")));
            WriteResult deleteWriteResult = mongoTemplate.remove(deleteQuery, Map.class, "shop_push_template");
            logger.info("deleteQuery:{}, deleteWriteResult:{}", deleteQuery, deleteWriteResult);
            result.setMsg("删除数据成功");
        }catch (Exception e){
            logger.error("异常信息：", e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg("查询商品发布模块信息失败");
        }
        return result;
    }

}
