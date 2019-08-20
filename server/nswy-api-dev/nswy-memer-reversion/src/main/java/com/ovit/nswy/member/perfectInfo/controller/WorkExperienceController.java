package com.ovit.nswy.member.perfectInfo.controller;

import com.ovit.nswy.member.perfectInfo.service.HonoraryTitleService;
import com.ovit.nswy.member.perfectInfo.service.WorkExperienceService;
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
 * 工作经历
 */
@RestController
@RequestMapping("/workExperience")
public class WorkExperienceController {

    private Logger logger = LogManager.getLogger(this.getClass());

    @Autowired
    private WorkExperienceService workExperienceService;



    /**
     *点击工作经历-工作经历信息获取模块属性及相应的数据信息
     *
     * parent_id 子类模块ID
     *
     * user_id
     * @param params
     * @return
     */
    @RequestMapping(value = "/findWorkExperience",method = RequestMethod.POST)
    public Result findWorkExperience(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            Map<String,Object> list = workExperienceService.findWorkExperience(params);
            result.setData(list);
        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     *修改名称-工作经历信息
     * @param params
     * @return
     */
    @RequestMapping(value = "/updateWorkExperienceName",method = RequestMethod.POST)
    public Result updateWorkExperienceName(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            workExperienceService.updateWorkExperienceName(params);

        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     *插入/更新-工作经历信息
     * @param params
     * @return
     */
    @RequestMapping(value = "/saveWorkExperience",method = RequestMethod.POST)
    public Result saveWorkExperience(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            workExperienceService.saveWorkExperience(params);

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
            workExperienceService.saveTextPreview(params);

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
    @RequestMapping(value = "/deleteWorkExperience",method = RequestMethod.POST)
    public Result deleteWorkExperience(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            workExperienceService.deleteWorkExperience(params);

        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }
}
