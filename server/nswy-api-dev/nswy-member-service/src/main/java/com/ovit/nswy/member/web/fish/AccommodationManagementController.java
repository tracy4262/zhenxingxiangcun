package com.ovit.nswy.member.web.fish;

import com.github.pagehelper.PageInfo;
import com.ovit.nswy.member.service.AccommodationManagementService;
import com.ovit.nswy.member.service.RestaurantManagementService;
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
 * 住宿管理
 */
@RestController
@RequestMapping("/accommodation")
public class AccommodationManagementController {

    private Logger logger = LogManager.getLogger(this.getClass());

    @Autowired
    private AccommodationManagementService accommodationManagementService;


    @RequestMapping(value = "/findRoomClass", method = RequestMethod.POST)
    public Result findRoomClass(@RequestBody Map<String,Object> params) {
        Result result = new Result();
        try {
            PageInfo<Map<String,Object>> pageInfo =  accommodationManagementService.findRoomClass(params);
            result.setData(pageInfo);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg(e.getMessage());
        }
        return result;
    }

    @RequestMapping(value = "/insertRoomClass", method = RequestMethod.POST)
    public Result insertRoomClass(@RequestBody Map<String,Object> params) {
        Result result = new Result();
        try {

            String accommodation  = accommodationManagementService.insertRoomClass(params);
            if(accommodation.equals("error")){
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

    @RequestMapping(value = "/updateRoomClass", method = RequestMethod.POST)
    public Result updateRoomClass(@RequestBody Map<String,Object> params) {
        Result result = new Result();
        try {
            String accommodation  = accommodationManagementService.updateRoomClass(params);
            if(accommodation.equals("error")){
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

    @RequestMapping(value = "/deleteRoomClass", method = RequestMethod.POST)
    public Result deleteRoomClass(@RequestBody Map<String,Object> params) {
        Result result = new Result();
        try {
            accommodationManagementService.deleteRoomClass(params);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg(e.getMessage());
        }
        return result;
    }

    /**
     * 房间列表
     */
    @RequestMapping(value = "/findRoomList", method = RequestMethod.POST)
    public Result findRoomList(@RequestBody Map<String,Object> params) {
        Result result = new Result();
        try {
            PageInfo<Map<String,Object>> pageInfo =  accommodationManagementService.findRoomList(params);
            result.setData(pageInfo);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg(e.getMessage());
        }
        return result;
    }

    @RequestMapping(value = "/insertRoomList", method = RequestMethod.POST)
    public Result insertRoomList(@RequestBody Map<String,Object> params) {
        Result result = new Result();
        try {
            String accommodation  = accommodationManagementService.insertRoomList(params);
            if(accommodation.equals("error")){
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

    @RequestMapping(value = "/updateRoomList", method = RequestMethod.POST)
    public Result updateRoomList(@RequestBody Map<String,Object> params) {
        Result result = new Result();
        try {
            String accommodation  = accommodationManagementService.updateRoomList(params);
            if(accommodation.equals("error")){
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

    @RequestMapping(value = "/deleteRoomList", method = RequestMethod.POST)
    public Result deleteRoomList(@RequestBody Map<String,Object> params) {
        Result result = new Result();
        try {
            accommodationManagementService.deleteRoomList(params);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg(e.getMessage());
        }
        return result;
    }




}
