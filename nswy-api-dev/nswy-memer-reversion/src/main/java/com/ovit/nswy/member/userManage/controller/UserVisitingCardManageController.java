package com.ovit.nswy.member.userManage.controller;

import com.ovit.nswy.member.userManage.service.UserVisitingCardManageService;
import com.ovit.nswy.member.util.Result;
import com.ovit.nswy.member.util.ResultCode;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/uesr/visitingCard")
public class UserVisitingCardManageController {

    private Logger logger = LogManager.getLogger(this.getClass());


    @Autowired
    private UserVisitingCardManageService userVisitingCardManageService;

    @RequestMapping(value = "/findVisitingCard",method = RequestMethod.POST)
    public Result findVisitingCard(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            Map<String,Object> map  =  userVisitingCardManageService.findVisitingCard(params);
            result.setData(map);
        } catch (Exception e) {
            logger.error("查询名片管理数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    @RequestMapping(value = "/updateVisitingCard",method = RequestMethod.POST)
    public Result updateVisitingCard(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            userVisitingCardManageService.updateVisitingCard(params);

        } catch (Exception e) {
            logger.error("更新名片管理数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }


    /**
     * 根据资质认证-会员名称查询 联系人，法人等信息
     * @param params
     * @return
     */
    @RequestMapping(value = "/findPhoneVisitingCard",method = RequestMethod.POST)
    public Result findPhoneVisitingCard(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            Map<String,Object> map  =  userVisitingCardManageService.findPhoneVisitingCard(params);
            result.setData(map);
        } catch (Exception e) {
            logger.error("根据会员名称扫码数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }


}
