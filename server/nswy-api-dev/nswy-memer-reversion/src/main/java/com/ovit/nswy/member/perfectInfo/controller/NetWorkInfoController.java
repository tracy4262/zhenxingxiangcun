package com.ovit.nswy.member.perfectInfo.controller;

import com.ovit.nswy.member.perfectInfo.service.NetWorkInfoService;
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
 * 网络信息
 */
@RestController
@RequestMapping("/netWorkInfo")
public class NetWorkInfoController {

    private Logger logger = LogManager.getLogger(this.getClass());

    @Autowired
    private NetWorkInfoService netWorkInfoService;



    /**
     * 网络信息
     *
     * parent_id 子类id
     *
     * user_id
     * @param params
     * @return
     */
    @RequestMapping(value = "/getNetworkInfo",method = RequestMethod.POST)
    public Result getNetworkInfo(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {

            Map<String,Object> list = netWorkInfoService.getNetworkInfo(params);
            result.setData(list);
        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }


    /**
     * 插入/修改-网络信息
     *
     * @param params
     * @return
     */
    @RequestMapping(value = "/insertNetworkInfo",method = RequestMethod.POST)
    public Result insertNetworkInfo(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {

          String code  =   netWorkInfoService.insertNetworkInfo(params);

          result.setCode(Integer.valueOf(code));

        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }


    /**
     * 修改-网络信息
     *
     * @param params
     * @return
     */
    @RequestMapping(value = "/updateNetworkInfoName",method = RequestMethod.POST)
    public Result updateNetworkInfoName(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {

            netWorkInfoService.updateNetworkInfoName(params);

        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }
}
