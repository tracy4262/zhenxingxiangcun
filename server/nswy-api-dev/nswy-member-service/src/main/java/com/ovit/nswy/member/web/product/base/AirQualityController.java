package com.ovit.nswy.member.web.product.base;

import com.ovit.nswy.member.service.AirQualityService;
import com.ovit.nswy.member.web.base.Result;
import org.apache.commons.collections.MapUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;


/**
 * 空气质量
 *
 **/
@RestController
@RequestMapping("/product-air-quality")
public class AirQualityController {
    private Logger logger = LogManager.getLogger(this.getClass());

    @Autowired
    private AirQualityService airQualityService;

    @RequestMapping(value = "/query",method = RequestMethod.POST)
    public Result query(@RequestBody Map<String,Object> params){
        logger.info("查询空气质量，{}",params);
        Result result = new Result();
        Map<String,Object> results= airQualityService.query(params);
        result.setData(results);
        logger.info("查询结果:{}",results);
        return result;
    }

    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public Result update(@RequestBody Map<String,Object> params){
        logger.info("修改空气质量:{}",params);
        Result result = new Result();
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("productId", MapUtils.getString(params,"productId"));
        Map<String,Object> results = airQualityService.query(map);
        Map<String,Object> param = (Map<String, Object>) params.get("data");
        param.put("productId", MapUtils.getString(params,"productId"));
        if (results != null){
            airQualityService.update(param);
        }else {
            airQualityService.add(param);
        }
        return result;
    }
}
