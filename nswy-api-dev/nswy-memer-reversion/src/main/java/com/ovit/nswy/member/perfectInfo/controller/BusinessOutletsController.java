package com.ovit.nswy.member.perfectInfo.controller;

import com.ovit.nswy.member.perfectInfo.service.BusinessOutletsService;
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

import java.util.Map;

/**
 * 营业网点
 */
@RestController
@RequestMapping("/businessOutlets")
public class BusinessOutletsController {

    private Logger logger = LogManager.getLogger(this.getClass());

    @Autowired
    private BusinessOutletsService businessOutletsService;



    /**
     * 营业网点初始化接口
     *
     * account
     * @param params
     * @return
     */
    @RequestMapping(value = "/findBusinessOutletsInfo",method = RequestMethod.POST)
    public Result findBusinessOutletsInfo(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {

            Map<String,Object> list = businessOutletsService.findBusinessOutletsInfo(params);

            result.setData(list);
        } catch (Exception e) {
            logger.error("查询营业网点数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }




    /**
     * 插入/修改-营业网点
     *
     *
     *
     * account
     * @param params
     * @return
     */
    @RequestMapping(value = "/saveOrUpdateBusinessOutlets",method = RequestMethod.POST)
    public Result saveOrUpdateBusinessOutlets(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {

            businessOutletsService.saveOrUpdateBusinessOutlets(params);

        } catch (Exception e) {
            logger.error("插入/修改营业网点数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }


    /**
     * 删除
     * @param params
     * @return
     */
    @RequestMapping(value = "/deleteBusinessOutlets",method = RequestMethod.POST)
    public Result deleteBusinessOutlets(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {

            businessOutletsService.deleteBusinessOutlets(params);

        } catch (Exception e) {
            logger.error("删除营业网点数据异常：{}",e);
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
            businessOutletsService.saveTextPreview(params);

        } catch (Exception e) {
            logger.error("插入文字预览数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }




}
