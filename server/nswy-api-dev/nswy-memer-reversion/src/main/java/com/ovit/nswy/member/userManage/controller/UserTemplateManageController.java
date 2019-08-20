package com.ovit.nswy.member.userManage.controller;

import com.ovit.nswy.member.userManage.service.UserTemplateManageService;
import com.ovit.nswy.member.util.Result;
import com.ovit.nswy.member.util.ResultCode;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user/userTemplateManage")
public class UserTemplateManageController {

    private Logger logger = LogManager.getLogger(this.getClass());

    @Autowired
    private UserTemplateManageService userTemplateManageService;


    /**
     * 查询模板
     */
    @RequestMapping(value = "/find", method = RequestMethod.POST)
    public Result find(@RequestBody Map<String,Object> params) {
        Result result = new Result();
        try {
            Map<String,Object> template = userTemplateManageService.find(params);
            result.setData(template);
            result.setMsg("模板查询成功");
        } catch (Exception e) {
            logger.error("处理异常：{}", e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg("查询模板信息失败");
        }
        logger.info("查询模板信息为： {}", result.getData());
        return result;
    }


    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public Result save(@RequestBody Map<String,Object> params) {
        Result result = new Result();
        try {
            userTemplateManageService.saveOrUpdate(params);
            result.setMsg("模板保存成功");
        } catch (Exception e) {
            logger.error("处理异常：{}", e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg("保存模板信息失败");
        }
        logger.info("保存模板信息为： {}", result.getData());
        return result;
    }



}
