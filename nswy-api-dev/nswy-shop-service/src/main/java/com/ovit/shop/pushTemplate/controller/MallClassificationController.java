package com.ovit.shop.pushTemplate.controller;

import com.github.pagehelper.PageInfo;
import com.ovit.shop.plantInfo.service.PlantInfoService;
import com.ovit.shop.pushTemplate.service.MallClassificationService;
import com.ovit.shop.util.Result;
import com.ovit.shop.util.ResultCode;
import org.apache.commons.collections.MapUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/mallClassification")
public class MallClassificationController {

    private Logger logger = LogManager.getLogger(this.getClass());

    @Autowired
    private MallClassificationService mallClassificationService;

    /**
     * 产品分类
     */
    @RequestMapping(value = "/findMallClassification", method = RequestMethod.POST)
    public Result findMallClassification(@RequestBody Map<String,Object> params) {
        Result result = new Result();
        try {

            List<Map<String, Object>> list = mallClassificationService.findMallClassification(params);

             result.setData(list);

        } catch (Exception e) {
            logger.error("异常信息：", e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg("获取产品分类信息失败");
        }
        return result;
    }




}
