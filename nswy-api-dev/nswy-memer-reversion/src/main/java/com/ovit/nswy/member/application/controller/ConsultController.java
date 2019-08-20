package com.ovit.nswy.member.application.controller;

import com.github.pagehelper.PageInfo;
import com.ovit.nswy.member.application.service.ConsultService;
import com.ovit.nswy.member.application.service.EmployService;
import com.ovit.nswy.member.util.Result;
import com.ovit.nswy.member.util.ResultCode;
import org.apache.commons.collections.MapUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *  咨询服务
 */
@RestController
@RequestMapping("/consult")
public class ConsultController {

    private Logger logger = LogManager.getLogger(this.getClass());

    @Autowired
    private ConsultService consultService;

    /**
     * 咨询服务发布第一步
     * @param params
     * @return
     */
    @RequestMapping(value = "/publishStepOne",method = RequestMethod.POST)
    public Result publishStepOne(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            consultService.publishStepOne(params);
            result.setData(MapUtils.getInteger(params, "id"));

        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     * 咨询服务发布第一步查询
     * @param id :  xxx //咨询服务id
     * @return
     */
    @RequestMapping(value = "/findStepOne",method = RequestMethod.GET)
    public Result findStepOne(@RequestParam Integer id){
        Result result = new Result();
        try {
            Map<String,Object> resultMap = new HashMap<>();
            if (id == null) {
                result.setData(resultMap);
                result.setCode(300);
                result.setMsg("无数据");
            } else {
                resultMap = consultService.findStepOne(id);
                result.setData(resultMap);
                result.setMsg("咨询发布第一步查询");
            }
        } catch (Exception e) {
            logger.error("数据异常：{}", e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     * 联系人列表：从实名认证里联系人信息中拉取
     * @param params
     * @return
     */
    @RequestMapping(value = "/contactList",method = RequestMethod.POST)
    public Result contactList(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            List<Map<String,Object>> contactList = consultService.contactList(params);
            result.setMsg("咨询服务拉取联系人信息列表查询成功");
            result.setData(contactList);
        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     * 咨询服务发布第二步
     * @param params
     * @return
     */
    @RequestMapping(value = "/publishStepTwo",method = RequestMethod.POST)
    public Result publishStepTwo(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            consultService.publishStepTwo(params);
            result.setMsg("咨询第二步发布成功");
        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     * 咨询服务发布第二步查询
     * @param
     * {
     *     id :  xxx,  //咨询服务id
     *     account : "xxx" //用户名
     * }
     * @return
     */
    @RequestMapping(value = "/findStepTwo",method = RequestMethod.POST)
    public Result findStepTwo(@RequestBody Map<String,Object> params){
        Result result = new Result();
        try {
            Map<String,Object> resultMap = consultService.findStepTwo(params);
            result.setData(resultMap);
            result.setMsg("咨询发布第二步查询");

        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     * 营业网点列表：从完善信息的营业网点表单里拉取
     * @param params
     * @return
     */
    @RequestMapping(value = "/networkStationList",method = RequestMethod.POST)
    public Result networkStationList(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            List<Map<String,Object>> list = consultService.networkStationList(params);
            result.setMsg("咨询服务拉取营业网点信息列表查询成功");
            result.setData(list);
        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     * 咨询服务发布第三步
     * @param params
     * @return
     */
    @RequestMapping(value = "/publishStepThree",method = RequestMethod.POST)
    public Result publishStepThree(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            consultService.publishStepThree(params);
            result.setMsg("咨询第三步发布成功");
        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     * 咨询服务发布第三步查询
     * @param
     * {
     *     id :  xxx,  //咨询服务id
     *     account : "xxx" //用户名
     * }
     * @return
     */
    @RequestMapping(value = "/findStepThree",method = RequestMethod.POST)
    public Result findStepThree(@RequestBody Map<String,Object> params){
        Result result = new Result();
        try {
            Map<String,Object> resultMap = consultService.findStepThree(params);
            result.setData(resultMap);
            result.setMsg("咨询发布第三步查询");

        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     * 咨询服务发布第四步
     * @param params
     * @return
     */
    @RequestMapping(value = "/publishStepFour",method = RequestMethod.POST)
    public Result publishStepFou(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            consultService.publishStepFour(params);
            result.setMsg("咨询第四步发布成功");
        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     * 咨询服务发布第四步查询
     * @param
     * {
     *     id :  xxx,  //咨询服务id
     *     account : "xxx" //用户名
     * }
     * @return
     */
    @RequestMapping(value = "/findStepFour",method = RequestMethod.POST)
    public Result findStepFour(@RequestBody Map<String,Object> params){
        Result result = new Result();
        try {
            Map<String,Object> resultMap = consultService.findStepFour(params);
            result.setData(resultMap);
            result.setMsg("咨询发布第四步查询");

        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     * 查询用户发布的咨询服务
     * @param params
     * @return
     */
    @RequestMapping(value = "/list",method = RequestMethod.POST)
    public Result list(@RequestBody Map<String,Object> params){
        Result result = new Result();
        try {
            Map<String,Object> resultMap = consultService.list(params);
            result.setData(resultMap);
            result.setMsg("咨询发布第四步查询");

        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     * 查询咨询服务详情
     * @param id
     * @return
     */
    @RequestMapping(value = "/detail",method = RequestMethod.POST)
    public Result detail(@RequestBody Map<String,Object> params){
        Result result = new Result();
        try {
            Map<String,Object> resultMap = consultService.detail(params);
            result.setData(resultMap);
            result.setMsg("咨询服务详情查询");

        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     * 咨询服务删除
     * @param id
     * @return
     */
    @RequestMapping(value = "/delete",method = RequestMethod.GET)
    public Result delete(@RequestParam Integer id){
        Result result = new Result();
        try {
            if (id == null) {
                result.setCode(300);
                result.setMsg("未指定具体咨询服务");
            } else {
                consultService.delete(id);
                result.setMsg("咨询服务删除成功");
            }
        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     * 判断是否是专家
     * @param id
     * @return
     */
    @RequestMapping(value = "/isExpert",method = RequestMethod.POST)
    public Result isExpert(@RequestBody Map<String,Object> params){
        Result result = new Result();
        try {
            Integer isExpert = consultService.isExpert(params);
            result.setData(isExpert);
            result.setMsg("查询用户是否为专家成功");
        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     * 咨询服务列表
     * @param params
     * @return
     */
    @RequestMapping(value = "/serviceList",method = RequestMethod.POST)
    public Result serviceList(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            Map<String,Object> resultMap = consultService.serviceList(params);
            result.setMsg("咨询服务列表查询成功");
            result.setData(resultMap);
        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

}
