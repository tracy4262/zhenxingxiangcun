package com.ovit.nswy.member.web.product.base;

import com.ovit.nswy.member.service.FisheriesWaterQualityService;
import com.ovit.nswy.member.service.WaterQualityService;
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
 * 渔业水质
 *
 **/
@RestController
@RequestMapping("/product-fisheries-water-quality")
public class FisheriesWaterQualityController {
    private Logger logger = LogManager.getLogger(this.getClass());

    @Autowired
    private FisheriesWaterQualityService fisheriesWaterQualityService;

    @RequestMapping(value = "/query",method = RequestMethod.POST)
    public Result query(@RequestBody Map<String,Object> params){
        logger.info("查询渔业水质，{}",params);
        Result result = new Result();
        Map<String,Object> map = fisheriesWaterQualityService.query(params);
        result.setData(map);
        logger.info("查询结果:{}",map);
        return result;
    }

    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public Result update(@RequestBody Map<String,Object> params){
        logger.info("修改渔业水质:{}",params);
        Result result = new Result();
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("productId", MapUtils.getString(params,"productId"));
        Map<String,Object> results = fisheriesWaterQualityService.query(params);
        Map<String,Object> param = (Map<String, Object>) params.get("data");
        param.put("productId", MapUtils.getString(params,"productId"));
        if (results != null){
            fisheriesWaterQualityService.update(param);
        }else {
            fisheriesWaterQualityService.add(param);
        }
        return result;
    }
}
