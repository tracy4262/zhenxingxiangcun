package com.ovit.nswy.member.perfectInfo.controller;

import com.ovit.nswy.member.perfectInfo.service.PrivacyInfoService;
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
 * 隐私信息
 */
@RestController
@RequestMapping("/privacyInfo")
public class PrivacyInfoController {

    private Logger logger = LogManager.getLogger(this.getClass());

    @Autowired
    private PrivacyInfoService privacyInfoService;




    /**
     * 隐私信息
     *
     * parent_id 子类id
     *
     * user_id
     * @param params
     * @return
     */
    @RequestMapping(value = "/findPrivacyInfo",method = RequestMethod.POST)
    public Result findPrivacyInfo(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {

            Map<String,Object> list = privacyInfoService.findPrivacyInfo(params);

            result.setData(list);
        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }




    /**
     * 插入/修改-隐私信息
     *
     * parent_id 子类id
     *
     * user_id
     * @param params
     * @return
     */
    @RequestMapping(value = "/insertPrivacyInfo",method = RequestMethod.POST)
    public Result insertPrivacyInfo(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {

           privacyInfoService.insertPrivacyInfo(params);

        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }


    /**
     * 修改-隐私信息
     *
     * parent_id 子类id
     *
     * user_id
     * @param params
     * @return
     */
    @RequestMapping(value = "/updatePrivacyInfoName",method = RequestMethod.POST)
    public Result updatePrivacyInfoName(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {

            privacyInfoService.updatePrivacyInfoName(params);

        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }




}
