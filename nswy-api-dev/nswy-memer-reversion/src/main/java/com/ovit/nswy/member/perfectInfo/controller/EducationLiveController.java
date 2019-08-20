package com.ovit.nswy.member.perfectInfo.controller;

import com.ovit.nswy.member.perfectInfo.service.EducationLiveService;
import com.ovit.nswy.member.perfectInfo.service.HonoraryTitleService;
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
 * 教育经历
 */
@RestController
@RequestMapping("/educationLive")
public class EducationLiveController {

    private Logger logger = LogManager.getLogger(this.getClass());

    @Autowired
    private EducationLiveService educationLiveService;




    /**
     *点击教育经历-教育经历信息获取模块属性及相应的数据信息
     *
     * parent_id 子类模块ID
     *
     * user_id
     * @param params
     * @return
     */
    @RequestMapping(value = "/findEducationLive",method = RequestMethod.POST)
    public Result findEducationLive(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            Map<String,Object> list = educationLiveService.findEducationLive(params);
            result.setData(list);
        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     *修改名称-教育经历信息
     * @param params
     * @return
     */
    @RequestMapping(value = "/updateEducationalLiveName",method = RequestMethod.POST)
    public Result updateEducationalLiveName(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            educationLiveService.updateEducationalLiveName(params);

        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     *插入/更新-教育经历信息
     * @param params
     * @return
     */
    @RequestMapping(value = "/saveEducationLive",method = RequestMethod.POST)
    public Result saveEducationLive(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            educationLiveService.saveEducationLive(params);

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
            educationLiveService.saveTextPreview(params);

        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     *删除
     * @param params
     * @return
     */
    @RequestMapping(value = "/deleteEducationalExperience",method = RequestMethod.POST)
    public Result deleteEducationalExperience(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            educationLiveService.deleteEducationalExperience(params);

        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

}
