package com.ovit.shop.pushTemplate.controller;

import com.alibaba.fastjson.JSON;

import com.ovit.nswy.frame.common.Response;
import com.ovit.shop.pushTemplate.service.ShopCardService;
import com.ovit.shop.util.Result;
import com.ovit.shop.util.ResultCode;
import com.sun.org.apache.xerces.internal.xs.datatypes.ObjectList;
import io.swagger.annotations.*;
import org.apache.commons.collections.MapUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.omg.CORBA.OBJ_ADAPTER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created by wangrongf
 */
@RestController
@RequestMapping("/shopCard")
public class ShopCardController {

    @Autowired
    private ShopCardService shopCardService;

    private Logger logger = LogManager.getLogger(this.getClass());


    /**
     * 购物车列表接口
     * @param params
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public Result list(@RequestBody Map<String,Object> params) {
        Result result = new Result();
        try {

            List<Map<String, Object>> list = shopCardService.list(params);
            result.setData(list);


        } catch (Exception e) {
            logger.error("异常信息：", e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg("查询购物车列表错误");
        }
        return result;
    }

    @RequestMapping(value = "/product", method = RequestMethod.POST)
    public Result product(@RequestBody Map<String,Object> params) {
        Result result = new Result();
        try {

            List<Map<String, Object>> list = shopCardService.product(params);
            result.setData(list);


        } catch (Exception e) {
            logger.error("异常信息：", e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg("查询购物车列表错误");
        }
        return result;
    }

    /**
     * 立即购买
     * @param params
     * @return
     */
    @RequestMapping(value = "/purchase", method = RequestMethod.POST)
    public Result purchase(@RequestBody Map<String,Object> params) {
        Result result = new Result();
        try {

            List<Map<String, Object>> list = shopCardService.purchase(params);
            result.setData(list);


        } catch (Exception e) {
            logger.error("异常信息：", e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg("查询购物车列表错误");
        }
        return result;
    }




    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Result add(@RequestBody Map<String,Object> params) {
        Result result = new Result();
        try {

            Integer results = shopCardService.add(params);

            if(results>=0) {

            }else{
                if(results==-1) {
                    result.setCode(300);
                    result.setMsg("本次购买量超过商品单次最大购买量，请调整购买数量");
                }else{
                    result.setMsg("添加商品错误");
                }
            }

        } catch (Exception e) {
            logger.error("异常信息：", e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg("添加商品错误");
        }
        return result;
    }

    /**
     * 购物车修改接口
     * @param params
     * @return
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Result update(@RequestBody Map<String,Object> params) {
        Result result = new Result();
        try {

            shopCardService.update(params);



        } catch (Exception e) {
            logger.error("异常信息：", e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg("修改购物车商品数量错误");
        }
        return result;
    }

    /**
     * 购物车删除接口
     * @param params
     * @return
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public Result delete(@RequestBody Map<String,Object> params) {
        Result result = new Result();
        try {

            List<Map<String,Object>> list = (List<Map<String, Object>>)params.get("delIds");

            for(Map<String,Object> map : list){
                map.put("account",MapUtils.getString(params,"account"));
                shopCardService.delete(map);
            }

        } catch (Exception e) {
            logger.error("异常信息：", e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg("删除购物车商品错误");
        }
        return result;
    }

    /**
     * 购物车清空接口
     * @param params
     * @return
     */
    @RequestMapping(value = "/clear", method = RequestMethod.POST)
    public Result clear(@RequestBody Map<String,Object> params) {

        Result result = new Result();
        try {

            shopCardService.clear(params);

        } catch (Exception e) {
            logger.error("异常信息：", e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg("清空购物车商品错误");
        }
        return result;
    }

}
