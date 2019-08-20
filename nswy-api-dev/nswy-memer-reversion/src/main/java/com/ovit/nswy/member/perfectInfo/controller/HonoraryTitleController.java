package com.ovit.nswy.member.perfectInfo.controller;

import com.ovit.nswy.member.perfectInfo.service.HistoryEvolutionService;
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
 * 荣誉称号
 */
@RestController
@RequestMapping("/honoraryTitle")
public class HonoraryTitleController {

    private Logger logger = LogManager.getLogger(this.getClass());

    @Autowired
    private HonoraryTitleService honoraryTitleService;




    /**
     *点击荣誉称号-荣誉称号信息获取模块属性及相应的数据信息
     *
     * parent_id 子类模块ID
     *
     * user_id
     * @param params
     * @return
     */
    @RequestMapping(value = "/findHonoraryTitle",method = RequestMethod.POST)
    public Result findHonoraryTitle(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            Map<String,Object> list = honoraryTitleService.findHonoraryTitle(params);
            result.setData(list);
        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     *修改名称-荣誉称号信息
     * @param params
     * @return
     */
    @RequestMapping(value = "/updateHonoraryTitleName",method = RequestMethod.POST)
    public Result updateHonoraryTitleName(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            honoraryTitleService.updateHonoraryTitleName(params);

        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     *插入/更新-荣誉称号信息
     * @param params
     * @return
     */
    @RequestMapping(value = "/saveHonoraryTitle",method = RequestMethod.POST)
    public Result saveHonoraryTitle(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            honoraryTitleService.saveHonoraryTitle(params);

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
            honoraryTitleService.saveTextPreview(params);

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
    @RequestMapping(value = "/deleteHonoraryTitle",method = RequestMethod.POST)
    public Result deleteHonoraryTitle(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            honoraryTitleService.deleteHonoraryTitle(params);

        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    @RequestMapping(value = "/findHonoraryTitleByAccount",method = RequestMethod.POST)
    public Result findHonoraryTitleByAccount(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            List<Map<String,Object>> list = honoraryTitleService.findHonoraryTitleByAccount(params);
            result.setData(list);
        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }


}
