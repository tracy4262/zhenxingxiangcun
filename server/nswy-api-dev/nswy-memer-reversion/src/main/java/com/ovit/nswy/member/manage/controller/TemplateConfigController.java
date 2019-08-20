package com.ovit.nswy.member.manage.controller;

import com.ovit.nswy.member.certification.service.LoginUserService;
import com.ovit.nswy.member.manage.service.TemplateConfigService;
import com.ovit.nswy.member.util.Result;
import com.ovit.nswy.member.util.ResultCode;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
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
@RequestMapping("/manage/templateConfig")
public class TemplateConfigController {

    private Logger logger = LogManager.getLogger(this.getClass());

    @Autowired
    private TemplateConfigService templateConfigService;

    @Autowired
    private LoginUserService loginUserService;

    /**
     * 保存模板
     * @param params
     * @return
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public Result save(@RequestBody Map<String, Object> params) {
        Result result = new Result();
        try {
            String account = MapUtils.getString(params, "account");
            String userType = MapUtils.getString(params, "userType");
            params.remove("account");
            params.remove("userType");
            List<Map<String,Object>> isExist = templateConfigService.find(params);
            if (CollectionUtils.isNotEmpty(isExist)) {
                //模板已存在
                result.setCode(300);
                result.setMsg("该模板名称已存在");

            } else {
                params.put("account", account);
                params.put("userType", userType);
                templateConfigService.save(params);
            }

        } catch (Exception e) {
            logger.error("模板保存失败: {}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg("模板保存失败");
        }
        return result;
    }

    /**
     * 查询模板
     */
    @RequestMapping(value = "/find", method = RequestMethod.POST)
    public Result find(@RequestBody(required = false) Map<String,Object> params) {
        Result result = new Result();
        try {

            List<Map<String,Object>> templateList = templateConfigService.find(params);
            if (CollectionUtils.isEmpty(templateList)) {
                templateList = new ArrayList<>();
            }
            result.setData(templateList);
            result.setMsg("模板查询成功");

        } catch (Exception e) {
            logger.error("处理异常：{}", e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg("查询模板信息失败");
        }
        logger.info("查询模板信息为： {}", result.getData());
        return result;
    }

    /**
     * 删除-模板信息
     * @param params
     * @return
     */
    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    public Result delete(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            templateConfigService.delete(params);
            result.setMsg("模板删除成功");

        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     * 查询用户类型
     */
    @RequestMapping(value = "/getUserType", method = RequestMethod.POST)
    public Result getUserType() {
        Result result = new Result();
        try {

            List<Map<String,Object>> userTypeList = templateConfigService.getUserType();
            if (CollectionUtils.isEmpty(userTypeList)) {
                userTypeList = new ArrayList<>();
            }
            result.setData(userTypeList);
            result.setMsg("用户类型查询成功");

        } catch (Exception e) {
            logger.error("处理异常：{}", e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg("查询用户类型信息失败");
        }
        logger.info("查询用户类型信息为： {}", result.getData());
        return result;
    }

    /**
     * 更新模板
     * @param params
     * @return
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Result update(@RequestBody Map<String, Object> params) {
        Result result = new Result();
        try {
            List<Map<String,Object>> isExist = templateConfigService.isExist(params);
            if (CollectionUtils.isNotEmpty(isExist)) {
                //模板已存在
                result.setCode(300);
                result.setMsg("该模板名称已存在");

            } else {
                templateConfigService.save(params);
                result.setMsg("模板编辑成功");
            }

        } catch (Exception e) {
            logger.error("模板编辑失败: {}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg("模板编辑失败");
        }
        return result;
    }
}
