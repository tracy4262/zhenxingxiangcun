package com.ovit.nswy.member.certification.controller;

import com.ovit.nswy.member.certification.service.LoginUserService;
import com.ovit.nswy.member.util.Result;
import com.ovit.nswy.member.util.ResultCode;
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
@RequestMapping("/realStep")
public class RealStepController {

    private Logger logger = LogManager.getLogger(this.getClass());

    @Autowired
    private LoginUserService loginUserService;


    /**
     * 查询应用设置信息
     */
    @RequestMapping(value = "/findStep", method = RequestMethod.POST)
    public Result findStep(@RequestBody Map<String,Object> params) {
        Result result = new Result();
        try {

            Map<String,Object> loginStep = loginUserService.findStep(params);

            result.setData(loginStep);

        } catch (Exception e) {
            logger.error("处理异常：{}", e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg("查询应用设置信息失败");
        }
        logger.info("查询应用设置信息为： {}", result.getData());
        return result;
    }

    /**
     * 管理员侧保存步骤1的接口
     * @param params
     * @return
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public Result save(@RequestBody Map<String,Object> params) {
        Result result = new Result();
        try {

            Map<String,Object> loginMap = MapUtils.getMap(params,"loginStep");

            loginUserService.saveOrUpdateStep(loginMap);


        } catch (Exception e) {
            logger.error("处理异常：{}", e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg("查询应用设置信息失败");
        }
        logger.info("查询应用设置信息为： {}", result.getData());
        return result;
    }

    /**
     * 查询已启用模版认证步骤
     * @param params
     * @return
     */
    @RequestMapping(value = "/findEnableStep", method = RequestMethod.POST)
    public Result findEnableStep(@RequestBody Map<String,Object> params) {
        Result result = new Result();
        try {

            Map<String,Object> loginStep = loginUserService.findEnableStep(params);

            result.setData(loginStep);

        } catch (Exception e) {
            logger.error("处理异常：{}", e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg("查询已启用模版认证步骤失败");
        }
        logger.info("查询已启用模版认证步骤： {}", result.getData());
        return result;
    }





}
