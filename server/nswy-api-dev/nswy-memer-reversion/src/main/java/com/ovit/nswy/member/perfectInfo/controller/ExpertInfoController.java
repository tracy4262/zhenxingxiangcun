package com.ovit.nswy.member.perfectInfo.controller;

import com.ovit.nswy.member.perfectInfo.service.ExpertInfoService;
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
 * 专业信息
 */
@RestController
@RequestMapping("/expertInfo")
public class ExpertInfoController {

    private Logger logger = LogManager.getLogger(this.getClass());

    @Autowired
    private ExpertInfoService expertInfoService;



    /**
     * 专业信息
     *
     * parent_id 子类id
     *
     * user_id
     * @param params
     * @return
     */
    @RequestMapping(value = "/findExpertInfo",method = RequestMethod.POST)
    public Result findExpertInfo(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {

            Map<String,Object> list = expertInfoService.findExpertInfo(params);

            result.setData(list);
        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }




    /**
     * 插入/修改-专业信息
     *
     * parent_id 子类id
     *
     * user_id
     * @param params
     * @return
     */
    @RequestMapping(value = "/insertExpertInfo",method = RequestMethod.POST)
    public Result insertExpertInfo(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {

            expertInfoService.insertExpertInfo(params);

        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }


    /**
     * 修改-专业信息
     *
     * parent_id 子类id
     *
     * user_id
     * @param params
     * @return
     */
    @RequestMapping(value = "/updateExpertInfoName",method = RequestMethod.POST)
    public Result updateExpertInfoName(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {

            expertInfoService.updateExpertInfoName(params);

        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }


    /**
     *插入/更新-文字预览
     * @param params
     * @return
     */
    @RequestMapping(value = "/saveTextPreview",method = RequestMethod.POST)
    public Result saveTextPreview(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            expertInfoService.saveTextPreview(params);

        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }




}
