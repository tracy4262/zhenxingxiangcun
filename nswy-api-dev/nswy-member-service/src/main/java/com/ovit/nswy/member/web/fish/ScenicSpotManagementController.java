package com.ovit.nswy.member.web.fish;

import com.github.pagehelper.PageInfo;
import com.ovit.nswy.member.service.ScenicSpotManagementService;
import com.ovit.nswy.member.web.base.Result;
import com.ovit.nswy.member.web.base.ResultCode;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 景区管理
 */
@RestController
@RequestMapping("/scenicSpot")
public class ScenicSpotManagementController {

    private Logger logger = LogManager.getLogger(this.getClass());

    @Autowired
    private ScenicSpotManagementService scenicSpotManagementService;


    @RequestMapping(value = "/findScenicSpot", method = RequestMethod.POST)
    public Result findScenicSpot(@RequestBody Map<String,Object> params) {
        Result result = new Result();
        try {
            PageInfo<Map<String,Object>> pageInfo =  scenicSpotManagementService.findScenicSpotList(params);
            result.setData(pageInfo);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg(e.getMessage());
        }
        return result;
    }

    @RequestMapping(value = "/insertScenicSpot", method = RequestMethod.POST)
    public Result insertScenicSpot(@RequestBody Map<String,Object> params) {
        Result result = new Result();
        try {
            String scenicSpot  = scenicSpotManagementService.insertScenicSpot(params);
            if(scenicSpot.equals("error")){
                result.setCode(300);
            }

        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg(e.getMessage());
        }
        return result;
    }

    @RequestMapping(value = "/updateScenicSpot", method = RequestMethod.POST)
    public Result updateScenicSpot(@RequestBody Map<String,Object> params) {
        Result result = new Result();
        try {
            String scenicSpot  = scenicSpotManagementService.updateScenicSpot(params);
            if(scenicSpot.equals("error")){
                result.setCode(300);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg(e.getMessage());
        }
        return result;
    }

    @RequestMapping(value = "/deleteScenicSpot", method = RequestMethod.POST)
    public Result deleteScenicSpot(@RequestBody Map<String,Object> params) {
        Result result = new Result();
        try {
            scenicSpotManagementService.deleteScenicSpot(params);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg(e.getMessage());
        }
        return result;
    }
}
