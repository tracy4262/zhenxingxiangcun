package com.ovit.shop.test.controller;

import com.mongodb.WriteResult;
import com.ovit.shop.util.Result;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/mongoTest")
public class MongoDemoController {

    private static final Logger logger = LogManager.getLogger(MongoDemoController.class);

    @Autowired
    private MongoTemplate mongoTemplate;

    @RequestMapping("/save")
    public Result save() {

        Result result = new Result();
        //新增
        Map<String, Object> addMap = new HashMap<String, Object>();
        addMap.put("name", "test");
        addMap.put("order", 1);
        addMap.put("create_time", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        mongoTemplate.save(addMap, "marker");
        result.setMsg("保存成功");
        return result;
    }

    @RequestMapping("/find")
    public Result find() {

        Result result = new Result();
        //查询
        Query query = new Query(Criteria.where("name").is("test")).with(new Sort(Sort.Direction.DESC, "order"));
        //显示哪些字段 不设置默认显示全部
        query.fields().include("name").include("order");
        List<Map> list = mongoTemplate.find(query, Map.class, "marker");
        logger.info("query:{}, result:{}", query, list);
        result.setMsg("查询数据成功");
        result.setData(list);
        return result;
    }

    @RequestMapping("/update")
    public Result update() {

        Result result = new Result();
        //修改
        Query modifyQuery = new Query(Criteria.where("order").is(1));
        Update update = new Update().set("name", "abc");
        WriteResult updateWriteResult = mongoTemplate.updateFirst(modifyQuery, update, Map.class, "marker");
        logger.info("modifyQuery:{}, update:{}, updateWriteResult:{}", modifyQuery, update, updateWriteResult);
        result.setMsg("修改数据成功");
        return result;
    }

    @RequestMapping("/delete")
    public Result delete() {

        Result result = new Result();
        //删除
        Query deleteQuery = new Query(Criteria.where("order").is(1));
        WriteResult deleteWriteResult = mongoTemplate.remove(deleteQuery, Map.class, "marker");
        logger.info("deleteQuery:{}, deleteWriteResult:{}", deleteQuery, deleteWriteResult);
        result.setMsg("删除数据成功");
        return result;
    }

}
