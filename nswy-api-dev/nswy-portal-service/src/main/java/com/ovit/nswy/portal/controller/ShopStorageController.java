package com.ovit.nswy.portal.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ovit.nswy.portal.core.Result;
import com.ovit.nswy.portal.core.ResultCode;
import com.ovit.nswy.portal.service.*;
import com.ovit.nswy.portal.util.PageUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 商品货架管理
 */
@RestController
@RequestMapping("/shopStorage")
public class ShopStorageController {

    private Logger logger = LogManager.getLogger(this.getClass());

    @Autowired
    private ShopStorageInfoService shopStorageInfoService;

    /**
     * 获取商品货架信息
     */
    @RequestMapping(value = "/getShopStorageList", method = RequestMethod.POST)
    public Result getShopStorageList(@RequestBody(required=false) Map<String,Object> params) {
        Result result = new Result();
        try {
            //查询商品货架信息
            List<Map<String, Object>> storageList = shopStorageInfoService.getShopStorageList(params);
            List<Map<String, Object>> storageResult = PageUtils.pageForList(MapUtils.getInteger(params, "num"), MapUtils.getInteger(params, "size"), storageList);
            Map<String, Object> pageResult = new HashMap<>();
            pageResult.put("list", storageResult);
            pageResult.put("total", storageList.size());
            result.setData(pageResult);

        } catch (Exception e) {
            logger.error("异常信息：", e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg("查询商品货架信息失败");
        }
        logger.info("查询商品货架信息为： {}", result.getData());
        return result;
    }

    /**
     * 更新商品货架状态：上架-> 正在出售页面, 删除-> 放到回收站, 下架-> 到货架上
     * 可同时更新多个商品的货架状态
     */
    @RequestMapping(value = "/updateStorageStatus", method = RequestMethod.POST)
    public Result updateStorageStatus(@RequestBody(required=false) List<Map<String,Object>> params) {
        Result result = new Result();
        try {
            //更新商品货架状态
            shopStorageInfoService.batchUpdateStorage(params);
            result.setMsg("更新货架状态成功");

        } catch (Exception e) {
            logger.error("异常信息：", e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg("更新商品货架信息失败");
        }
        return result;
    }

    /**
     * 更新货架上的商品的时价、折扣价、折扣时限, 修改完后，此商品改为待审核状态
     */
    @RequestMapping(value = "/updateStoragePrice", method = RequestMethod.POST)
    public Result updateStoragePrice(@RequestBody(required=false) Map<String,Object> params) {
        Result result = new Result();
        try {
            //更新货架上的商品价格
            shopStorageInfoService.updateStoragePrice(params);
            result.setMsg("更新货架商品价格成功");

        } catch (Exception e) {
            logger.error("异常信息：", e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg("更新货架商品价格失败");
        }
        return result;
    }

    /**
     * 商品回收站永久删除商品
     * 可同时删除多个商品
     */
    @RequestMapping(value = "/deleteCommodityForever", method = RequestMethod.POST)
    public Result deleteCommodityForever(@RequestBody(required=false) List<Map<String,Object>> params) {
        Result result = new Result();
        try {
            //更新商品货架状态
            shopStorageInfoService.batchDeleteCommodity(params);
            result.setMsg("删除商品成功");

        } catch (Exception e) {
            logger.error("异常信息：", e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg("删除商品失败");
        }
        return result;
    }

}
