package com.ovit.nswy.portal.controller;

import com.github.pagehelper.PageInfo;
import com.ovit.nswy.portal.core.Result;
import com.ovit.nswy.portal.service.StandardService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 门户标准
 *
 * @author haoWen
 * @create 2018-03-15 14:59
 **/
@RestController
@RequestMapping("/standard")
public class StandardController {
    private Logger logger = LogManager.getLogger(this.getClass());

    @Autowired
    private StandardService standardService;

    @RequestMapping(value = "standard-list",method = RequestMethod.POST)
    public Result standardList(@RequestBody Map<String,Object> params){
        Result result = new Result();
        logger.debug("门户查询标准，参数：{}",params);
        PageInfo<Map<String,Object>> standardList = standardService.queryStandardList(params);
        result.setData(standardList);
        return result;
    }
}
