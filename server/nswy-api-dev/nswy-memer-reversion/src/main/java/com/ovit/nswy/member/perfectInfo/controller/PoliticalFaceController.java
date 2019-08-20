package com.ovit.nswy.member.perfectInfo.controller;

import com.ovit.nswy.member.perfectInfo.service.PoliticalFaceService;
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
 * 政治面貌
 */
@RestController
@RequestMapping("/politicalFace")
public class PoliticalFaceController {

    private Logger logger = LogManager.getLogger(this.getClass());

    @Autowired
    private PoliticalFaceService politicalFaceService;




    /**
     *点击政治面貌-政治面貌信息获取模块属性及相应的数据信息
     *
     * parent_id 子类模块ID
     *
     * user_id
     * @param params
     * @return
     */
    @RequestMapping(value = "/findPoliticalFace",method = RequestMethod.POST)
    public Result findPoliticalFace(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            Map<String,Object> list = politicalFaceService.findPoliticalFace(params);
            result.setData(list);
        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     *修改名称-政治面貌信息
     * @param params
     * @return
     */
    @RequestMapping(value = "/updatePoliticalFaceName",method = RequestMethod.POST)
    public Result updatePoliticalFaceName(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            politicalFaceService.updatePoliticalFaceName(params);

        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     *插入/更新-政治面貌信息
     * @param params
     * @return
     */
    @RequestMapping(value = "/savePoliticalFace",method = RequestMethod.POST)
    public Result savePoliticalFace(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            politicalFaceService.savePoliticalFace(params);

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
            politicalFaceService.saveTextPreview(params);

        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

}
