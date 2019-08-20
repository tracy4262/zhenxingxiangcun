package com.ovit.nswy.member.web.fish;

import com.github.pagehelper.PageInfo;

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

import java.util.List;
import java.util.Map;

/**
 * 餐饮管理
 */
@RestController
@RequestMapping("/restaurant")
public class RestaurantManagementController {

    private Logger logger = LogManager.getLogger(this.getClass());

    @Autowired
    private RestaurantManagementService restaurantManagementService;


    @RequestMapping(value = "/findRestaurant", method = RequestMethod.POST)
    public Result findRestaurant(@RequestBody Map<String,Object> params) {
        Result result = new Result();
        try {
            PageInfo<Map<String,Object>> pageInfo =  restaurantManagementService.findRestaurant(params);
            result.setData(pageInfo);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg(e.getMessage());
        }
        return result;
    }

    @RequestMapping(value = "/insertRestaurant", method = RequestMethod.POST)
    public Result insertRestaurant(@RequestBody Map<String,Object> params) {
        Result result = new Result();
        try {
            String rest  = restaurantManagementService.insertRestaurant(params);
            if(rest.equals("error")){
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

    @RequestMapping(value = "/updateRestaurant", method = RequestMethod.POST)
    public Result updateRestaurant(@RequestBody Map<String,Object> params) {
        Result result = new Result();
        try {
            String rest  =  restaurantManagementService.updateRestaurant(params);
            if(rest.equals("error")){
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

    @RequestMapping(value = "/deleteRestaurant", method = RequestMethod.POST)
    public Result deleteRestaurant(@RequestBody Map<String,Object> params) {
        Result result = new Result();
        try {
            restaurantManagementService.deleteRestaurant(params);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg(e.getMessage());
        }
        return result;
    }

    /**
     * 菜品管理
     */
    @RequestMapping(value = "/findFood", method = RequestMethod.POST)
    public Result findFood(@RequestBody Map<String,Object> params) {
        Result result = new Result();
        try {
            PageInfo<Map<String,Object>> pageInfo =  restaurantManagementService.findFood(params);
            result.setData(pageInfo);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg(e.getMessage());
        }
        return result;
    }

    @RequestMapping(value = "/insertFood", method = RequestMethod.POST)
    public Result insertFood(@RequestBody Map<String,Object> params) {
        Result result = new Result();
        try {
            String rest  =restaurantManagementService.insertFood(params);
            if(rest.equals("error")){
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

    @RequestMapping(value = "/updateFood", method = RequestMethod.POST)
    public Result updateFood(@RequestBody Map<String,Object> params) {
        Result result = new Result();
        try {
            String rest  =restaurantManagementService.updateFood(params);
            if(rest.equals("error")){
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

    @RequestMapping(value = "/deleteFood", method = RequestMethod.POST)
    public Result deleteFood(@RequestBody Map<String,Object> params) {
        Result result = new Result();
        try {
            restaurantManagementService.deleteFood(params);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg(e.getMessage());
        }
        return result;
    }

    /**
     * 包房管理
     */
    @RequestMapping(value = "/findRoom", method = RequestMethod.POST)
    public Result findRoom(@RequestBody Map<String,Object> params) {
        Result result = new Result();
        try {
            PageInfo<Map<String,Object>> pageInfo =  restaurantManagementService.findRoom(params);
            result.setData(pageInfo);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg(e.getMessage());
        }
        return result;
    }

    @RequestMapping(value = "/insertRoom", method = RequestMethod.POST)
    public Result insertRoom(@RequestBody Map<String,Object> params) {
        Result result = new Result();
        try {
            String rest  = restaurantManagementService.insertRoom(params);
            if(rest.equals("error")){
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

    @RequestMapping(value = "/updateRoom", method = RequestMethod.POST)
    public Result updateRoom(@RequestBody Map<String,Object> params) {
        Result result = new Result();
        try {
            String rest  = restaurantManagementService.updateRoom(params);
            if(rest.equals("error")){
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

    @RequestMapping(value = "/deleteRoom", method = RequestMethod.POST)
    public Result deleteRoom(@RequestBody Map<String,Object> params) {
        Result result = new Result();
        try {
            restaurantManagementService.deleteRoom(params);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg(e.getMessage());
        }
        return result;
    }

    /**
     * 餐桌管理
     */
    @RequestMapping(value = "/findTable", method = RequestMethod.POST)
    public Result findTable(@RequestBody Map<String,Object> params) {
        Result result = new Result();
        try {
            PageInfo<Map<String,Object>> pageInfo =  restaurantManagementService.findTable(params);
            result.setData(pageInfo);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg(e.getMessage());
        }
        return result;
    }

    @RequestMapping(value = "/insertTable", method = RequestMethod.POST)
    public Result insertTable(@RequestBody Map<String,Object> params) {
        Result result = new Result();
        try {
            String rest  =restaurantManagementService.insertTable(params);
            if(rest.equals("error")){
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

    @RequestMapping(value = "/updateTable", method = RequestMethod.POST)
    public Result updateTable(@RequestBody Map<String,Object> params) {
        Result result = new Result();
        try {
            String rest  =restaurantManagementService.updateTable(params);
            if(rest.equals("error")){
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

    @RequestMapping(value = "/deleteTable", method = RequestMethod.POST)
    public Result deleteTable(@RequestBody Map<String,Object> params) {
        Result result = new Result();
        try {
            restaurantManagementService.deleteTable(params);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg(e.getMessage());
        }
        return result;
    }

    /**
     * 服务详情 餐桌
     * @param params
     * @return
     */
    @RequestMapping(value = "/findTableDetail", method = RequestMethod.POST)
    public Result findTableDetail(@RequestBody Map<String,Object> params) {
        Result result = new Result();
        try {
            List<Map<String,Object>> list =  restaurantManagementService.findTableDetail(params);
            result.setData(list);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg(e.getMessage());
        }
        return result;
    }
}
