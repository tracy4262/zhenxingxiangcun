package com.ovit.nswy.member.perfectInfo.controller;

import com.ovit.nswy.member.application.service.ProductionBaseService;
import com.ovit.nswy.member.perfectInfo.service.ExpertAuthService;
import com.ovit.nswy.member.perfectInfo.service.ExpertInfoService;
import com.ovit.nswy.member.util.Result;
import com.ovit.nswy.member.util.ResultCode;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * 专家认证
 */
@RestController
@RequestMapping("/expertAuth")
public class ExpertAuthController {

    private Logger logger = LogManager.getLogger(this.getClass());

    @Autowired
    private ExpertAuthService expertAuthService;

    @Autowired
    private ProductionBaseService productionBaseService;

    /**
     * 查询专业认证信息
     * @param params
     * @return
     */
    @RequestMapping(value = "/find",method = RequestMethod.POST)
    public Result find(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            Map<String, Object> list = expertAuthService.find(params);
            result.setData(list);
        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     * 保存-专家认证信息
     * @param params
     * @return
     */
    @RequestMapping(value = "/save",method = RequestMethod.POST)
    public Result save(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            expertAuthService.save(params);
        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     * 删除-专家认证信息
     * @param params
     * @return
     */
    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    public Result delete(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            expertAuthService.delete(params);
            result.setMsg("专家认证信息删除成功");

        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     * 查询专业认证信息
     * @param params
     * @return
     */
    @RequestMapping(value = "/findExpertByAccount",method = RequestMethod.POST)
    public Result findExpertByAccount(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            //查询出templateid,yearId
            Map<String,Object> templateMap = productionBaseService.getYearIdAndTemplateId(params);

            Map<String, Object> list = expertAuthService.findExpertInfo(templateMap);
            result.setData(list);
        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

}
