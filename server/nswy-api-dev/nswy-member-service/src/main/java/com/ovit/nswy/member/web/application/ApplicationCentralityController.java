package com.ovit.nswy.member.web.application;

import com.ovit.nswy.member.service.ApplicationCentralityService;
import com.ovit.nswy.member.web.base.Result;
import com.ovit.nswy.member.web.base.ResultCode;
import org.apache.commons.collections.MapUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;


/**
 * 应用中心
 */
@RestController
@RequestMapping("/applicationCentrality")
public class ApplicationCentralityController {


    private Logger logger = LogManager.getLogger(this.getClass());


    /**
     * 查询应用
     */
    @Autowired
    private ApplicationCentralityService applicationCentralityService;

    @RequestMapping(value = "/findList",method = RequestMethod.POST)
    public Result findList(@RequestBody Map<String,Object> params){
        Result result = new Result();
        try{
            List<Map<String,Object>> list = applicationCentralityService.findList(params);
            result.setData(list);
        }catch (Exception e){
            logger.error("查询应用中心失败：{}", e);
            result.setCode(ResultCode.FAIL);
            return result;
        }
        return result;
    }

    /**
     * 查询用户类型
     * @param params
     * @return
     */
    @RequestMapping(value = "/findUserTypeList",method = RequestMethod.POST)
    public Result findUserTypeList(@RequestBody Map<String,Object> params){
        Result result = new Result();
        try{
            List<Map<String,Object>> list = applicationCentralityService.findUserTypeList(params);
            result.setData(list);
        }catch (Exception e){
            logger.error("查询适用对象信息失败：{}", e);
            result.setCode(ResultCode.FAIL);
            return result;
        }
        return result;
    }

    /**
     * 查询服务类型
     * @param params
     * @return
     */
    @RequestMapping(value = "/findServiceTypeList",method = RequestMethod.POST)
    public Result findServiceTypeList(@RequestBody Map<String,Object> params){
        Result result = new Result();
        try{
            List<Map<String,Object>> list = applicationCentralityService.findServiceTypeList(params);
            result.setData(list);
        }catch (Exception e){
            logger.error("查询服务类型信息失败：{}", e);
            result.setCode(ResultCode.FAIL);
            return result;
        }
        return result;
    }

    /**
     * 保存/取消应用
     * @param params
     * @return
     */
    @RequestMapping(value = "/saveOrCancelAppInfo", method = RequestMethod.POST)
    public Result saveOrCancelAppInfo(@RequestBody Map<String, Object> params) {
        Result result = new Result();
        try {
            //0：取消，1：保存
            String type = MapUtils.getString(params,"type");
            String appId = MapUtils.getString(params,"appId");
            if ("0".equals(type)) {
                //取消应用,应用的使用人数通过触发器更新,触发器名称为APP_CANCEL_AFTER_DELETE
                applicationCentralityService.cancelAppSettings(params);
                //用户类型为管理员，不需要减1，其余需要
                applicationCentralityService.cancelNumber(appId);
                logger.info("应用设置信息取消成功");

            } else if("1".equals(type)) {
                //保存应用,应用的使用人数通过触发器更新,触发器名称为APP_ADD_AFTER_INSERT
                applicationCentralityService.saveAppSettings(params);
                //用户类型为管理员，不需要加1，其余需要
                applicationCentralityService.addNumber(appId);
                logger.info("应用设置信息保存成功");
            }
        } catch (Exception e) {
            logger.error("应用设置信息保存或取消失败: {}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg("保存或取消应用设置信息失败");
        }
        return result;
    }

    /**
     * 查询应用简介
     * @param params
     * @return
     */
    @RequestMapping(value = "/findAppAbstract",method = RequestMethod.POST)
    public Result findAppAbstract(@RequestBody Map<String,Object> params){
        Result result = new Result();
        try{
            List<Map<String,Object>> list = applicationCentralityService.findAppAbstract(params);
            result.setData(list);
        }catch (Exception e){
            logger.error("查询应用简介失败：{}", e);
            result.setCode(ResultCode.FAIL);
            return result;
        }
        return result;
    }

}
