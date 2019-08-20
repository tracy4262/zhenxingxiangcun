package com.ovit.nswy.member.web.product.base;

import com.ovit.nswy.member.service.TrafficService;
import com.ovit.nswy.member.web.base.Result;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;


/**
 * 交通条件
 *
 **/
@RestController
@RequestMapping("/product-traffic")
public class TrafficController {
    private Logger logger = LogManager.getLogger(this.getClass());

    @Autowired
    private TrafficService trafficService;

    @RequestMapping(value = "/query",method = RequestMethod.POST)
    public Result query(@RequestBody Map<String,Object> params){
        logger.info("查询交通条件，{}",params);
        Result result = new Result();
        Map<String,Object> map = trafficService.query(params);
        result.setData(map);
        logger.info("查询结果:{}",map);
        return result;
    }

    @RequestMapping(value = "/detail",method = RequestMethod.POST)
    public Result update(@RequestBody Map<String,Object> params){
        logger.info("查询交通条件详情:{}",params);
        Result result = new Result();
        Map<String,Object> map = trafficService.detail(params);
        result.setData(map);
        logger.info("查询结果:{}",map);
        return result;
    }
}
