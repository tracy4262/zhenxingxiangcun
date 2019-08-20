package com.ovit.nswy.member.web.product.base;

import com.ovit.nswy.member.service.EdibleFungusService;
import com.ovit.nswy.member.service.SoilQualityService;
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
 * 食用菌栽培基质
 *
 **/
@RestController
@RequestMapping("/product-edible-fungus")
public class EdibleFungusController {
    private Logger logger = LogManager.getLogger(this.getClass());

    @Autowired
    private EdibleFungusService edibleFungusService;

    @RequestMapping(value = "/query",method = RequestMethod.POST)
    public Result query(@RequestBody Map<String,Object> params){
        logger.info("查询食用菌栽培基质，{}",params);
        Result result = new Result();
        Map<String,Object> map = edibleFungusService.query(params);
        result.setData(map);
        logger.info("查询结果:{}",map);
        return result;
    }

    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public Result update(@RequestBody Map<String,Object> params){
        logger.info("修改食用菌栽培基质:{}",params);
        Result result = new Result();
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("productId", MapUtils.getString(params,"productId"));
        Map<String,Object> results = edibleFungusService.query(params);
        Map<String,Object> param = (Map<String, Object>) params.get("data");
        param.put("productId", MapUtils.getString(params,"productId"));
        if (results != null){
            edibleFungusService.update(param);
        }else {
            edibleFungusService.add(param);
        }
        return result;
    }
}
