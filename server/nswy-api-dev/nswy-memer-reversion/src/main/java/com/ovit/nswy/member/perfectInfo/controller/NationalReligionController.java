package com.ovit.nswy.member.perfectInfo.controller;

import com.ovit.nswy.member.perfectInfo.service.NationalReligionService;
import com.ovit.nswy.member.perfectInfo.service.WantBuyService;
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
 * 民族宗教信息
 */
@RestController
@RequestMapping("/nationalReligion")
public class NationalReligionController {

    private Logger logger = LogManager.getLogger(this.getClass());

    @Autowired
    private NationalReligionService nationalReligionService;

    /**
     * 民族宗教信息查询
     * @param params
     * @return
     */
    @RequestMapping(value = "/find", method = RequestMethod.POST)
    public Result find(@RequestBody Map<String, Object> params) {
        Result result = new Result();
        try {
            Map<String, Object> religion = nationalReligionService.find(params);
            result.setMsg("民族宗教信息查询成功");
            result.setData(religion);
        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     * 保存-民族宗教信息
     * @param params
     * @return
     */
    @RequestMapping(value = "/save",method = RequestMethod.POST)
    public Result save(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            nationalReligionService.save(params);
            result.setMsg("民族宗教信息保存成功");

        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     * 更新-民族宗教信息
     * @param params
     * @return
     */
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public Result update(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            nationalReligionService.update(params);
            result.setMsg("求民族宗教信息更新成功");

        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     * 查询该用户、年度文件下所有的信息
     * 不分页、用于导出数据
     * @param params
     * @return
     */
    @RequestMapping(value = "/findAll", method = RequestMethod.POST)
    public Result findAll(@RequestBody Map<String, Object> params) {
        Result result = new Result();
        try {
            List<Map<String, Object>> religion = nationalReligionService.findAll(params);
            result.setMsg("民族宗教信息列表查询成功");
            result.setData(religion);
        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

}
