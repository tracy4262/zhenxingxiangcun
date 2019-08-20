package com.ovit.shop.pushTemplate.controller;

import com.github.pagehelper.PageInfo;
import com.ovit.shop.pushTemplate.service.PushShopCommodityService;
import com.ovit.shop.test.controller.MongoDemoController;
import com.ovit.shop.util.PageUtil;
import com.ovit.shop.util.Result;
import com.ovit.shop.util.ResultCode;
import org.apache.commons.collections.MapUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/pushShopCommodity")
public class PushShopCommodity {
    private static final Logger logger = LogManager.getLogger(MongoDemoController.class);

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private PushShopCommodityService pushShopCommodityService;

    /**
     * 定价商品
     * @param params
     * @return
     */
    @RequestMapping(value = "/findPricing", method = RequestMethod.POST)
    public Result getShopStorageList(@RequestBody(required=false) Map<String,Object> params) {
        Result result = new Result();
        try {
            //查询商品货架信息
            Integer pageSize = MapUtils.getInteger(params, "size");
            int size = (pageSize == null) ? 10 : pageSize;
            Integer number = MapUtils.getInteger(params, "num");
            int num = (number == null) ? 1 : number;
            params.remove("num");
            params.remove("size");

            List<Map<String, Object>> storageList = pushShopCommodityService.findPricing(params);
            List<Map<String, Object>> storageResult = PageUtil.pageForList(num, size, storageList);
            Map<String, Object> pageResult = new HashMap<>();
            pageResult.put("list", storageResult);
            pageResult.put("total", storageList.size());
            result.setData(pageResult);

        } catch (Exception e) {
            logger.error("异常信息：", e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg("查询失败");
        }
        logger.info("查询列表信息为： {}", result.getData());
        return result;
    }

    /**
     * 竞价商品
     * @param params
     * @return
     */
    @RequestMapping(value = "/findBidding", method = RequestMethod.POST)
    public Result findBidding(@RequestBody(required=false) Map<String,Object> params) {
        Result result = new Result();
        try {
            //查询商品货架信息
            Integer pageSize = MapUtils.getInteger(params, "size");
            int size = (pageSize == null) ? 10 : pageSize;
            Integer number = MapUtils.getInteger(params, "num");
            int num = (number == null) ? 1 : number;
            params.remove("num");
            params.remove("size");

            List<Map<String, Object>> storageList = pushShopCommodityService.findBidding(params);
            List<Map<String, Object>> storageResult = PageUtil.pageForList(num, size, storageList);
            Map<String, Object> pageResult = new HashMap<>();
            pageResult.put("list", storageResult);
            pageResult.put("total", storageList.size());
            result.setData(pageResult);

        } catch (Exception e) {
            logger.error("异常信息：", e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg("查询失败");
        }
        logger.info("查询列表信息为： {}", result.getData());
        return result;
    }

    /**
     * 团购商品
     * @param params
     * @return
     */
    @RequestMapping(value = "/findGroupBuying", method = RequestMethod.POST)
    public Result findGroupBuying(@RequestBody(required=false) Map<String,Object> params) {
        Result result = new Result();
        try {
            //查询商品货架信息
            Integer pageSize = MapUtils.getInteger(params, "size");
            int size = (pageSize == null) ? 10 : pageSize;
            Integer number = MapUtils.getInteger(params, "num");
            int num = (number == null) ? 1 : number;
            params.remove("num");
            params.remove("size");

            List<Map<String, Object>> storageList = pushShopCommodityService.findGroupBuying(params);
            List<Map<String, Object>> storageResult = PageUtil.pageForList(num, size, storageList);
            Map<String, Object> pageResult = new HashMap<>();
            pageResult.put("list", storageResult);
            pageResult.put("total", storageList.size());
            result.setData(pageResult);

        } catch (Exception e) {
            logger.error("异常信息：", e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg("查询失败");
        }
        logger.info("查询列表信息为： {}", result.getData());
        return result;
    }

    /**
     * 预售商品
     * @param params
     * @return
     */
    @RequestMapping(value = "/findPresale", method = RequestMethod.POST)
    public Result findPresale(@RequestBody(required=false) Map<String,Object> params) {
        Result result = new Result();
        try {
            //查询商品货架信息
            Integer pageSize = MapUtils.getInteger(params, "size");
            int size = (pageSize == null) ? 10 : pageSize;
            Integer number = MapUtils.getInteger(params, "num");
            int num = (number == null) ? 1 : number;
            params.remove("num");
            params.remove("size");

            List<Map<String, Object>> storageList = pushShopCommodityService.findPresale(params);
            List<Map<String, Object>> storageResult = PageUtil.pageForList(num, size, storageList);
            Map<String, Object> pageResult = new HashMap<>();
            pageResult.put("list", storageResult);
            pageResult.put("total", storageList.size());
            result.setData(pageResult);

        } catch (Exception e) {
            logger.error("异常信息：", e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg("查询失败");
        }
        logger.info("查询列表信息为： {}", result.getData());
        return result;
    }

    /**
     * 面议商品
     * @param params
     * @return
     */
    @RequestMapping(value = "/findFace", method = RequestMethod.POST)
    public Result findFace(@RequestBody(required=false) Map<String,Object> params) {
        Result result = new Result();
        try {
            //查询商品货架信息
            Integer pageSize = MapUtils.getInteger(params, "size");
            int size = (pageSize == null) ? 10 : pageSize;
            Integer number = MapUtils.getInteger(params, "num");
            int num = (number == null) ? 1 : number;
            params.remove("num");
            params.remove("size");

            List<Map<String, Object>> storageList = pushShopCommodityService.findFace(params);
            List<Map<String, Object>> storageResult = PageUtil.pageForList(num, size, storageList);
            Map<String, Object> pageResult = new HashMap<>();
            pageResult.put("list", storageResult);
            pageResult.put("total", storageList.size());
            result.setData(pageResult);

        } catch (Exception e) {
            logger.error("异常信息：", e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg("查询失败");
        }
        logger.info("查询列表信息为： {}", result.getData());
        return result;
    }

    /**
     * 可追溯商品
     * @param params
     * @return
     */
    @RequestMapping(value = "/findRetrospect", method = RequestMethod.POST)
    public Result findRetrospect(@RequestBody(required=false) Map<String,Object> params) {
        Result result = new Result();
        try {
            //查询商品货架信息
            Integer pageSize = MapUtils.getInteger(params, "size");
            int size = (pageSize == null) ? 10 : pageSize;
            Integer number = MapUtils.getInteger(params, "num");
            int num = (number == null) ? 1 : number;
            params.remove("num");
            params.remove("size");

            List<Map<String, Object>> storageList = pushShopCommodityService.findRetrospect(params);
            List<Map<String, Object>> storageResult = PageUtil.pageForList(num, size, storageList);
            Map<String, Object> pageResult = new HashMap<>();
            pageResult.put("list", storageResult);
            pageResult.put("total", storageList.size());
            result.setData(pageResult);

        } catch (Exception e) {
            logger.error("异常信息：", e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg("查询失败");
        }
        logger.info("查询列表信息为： {}", result.getData());
        return result;
    }

    /**
     * 全部商品
     * @param params
     * @return
     */
    @RequestMapping(value = "/findProduct", method = RequestMethod.POST)
    public Result findProduct(@RequestBody(required=false) Map<String,Object> params) {
        Result result = new Result();
        try {
            //查询商品货架信息
            Integer pageSize = MapUtils.getInteger(params, "size");
            int size = (pageSize == null) ? 10 : pageSize;
            Integer number = MapUtils.getInteger(params, "num");
            int num = (number == null) ? 1 : number;
            params.remove("num");
            params.remove("size");

            List<Map<String, Object>> storageList = pushShopCommodityService.findProduct(params);
            List<Map<String, Object>> storageResult = PageUtil.pageForList(num, size, storageList);
            Map<String, Object> pageResult = new HashMap<>();
            pageResult.put("list", storageResult);
            pageResult.put("total", storageList.size());
            result.setData(pageResult);

        } catch (Exception e) {
            logger.error("异常信息：", e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg("查询失败");
        }
        logger.info("查询列表信息为： {}", result.getData());
        return result;
    }

    /**
     * 我的推荐-产品
     * @param params
     * @return
     */
    @RequestMapping(value = "/findRecommendProduct", method = RequestMethod.POST)
    public Result findRecommendProduct(@RequestBody(required=false) Map<String,Object> params) {
        Result result = new Result();
        try {
            List<Map<String, Object>> storageList = pushShopCommodityService.findProduct(params);
            Map<String, Object> pageResult = new HashMap<>();
            pageResult.put("list", storageList);
            pageResult.put("total", storageList.size());
            result.setData(pageResult);
        } catch (Exception e) {
            logger.error("异常信息：", e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg("查询失败");
        }
        logger.info("查询列表信息为： {}", result.getData());
        return result;
    }

    /**
     * 后台-内容控制-商品
     * @param params
     * @return
     */
    @RequestMapping(value = "/findProductList", method = RequestMethod.POST)
    public Result findProductList(@RequestBody(required=false) Map<String,Object> params) {
        Result result = new Result();
        try {
            List<Map<String,Object>> productList = pushShopCommodityService.findProductList(params);
            List<Map<String, Object>> storageResult = PageUtil.pageForList(MapUtils.getInteger(params, "pageNum"), MapUtils.getInteger(params, "pageSize"), productList);
            Map<String, Object> pageResult = new HashMap<>();
            pageResult.put("list", storageResult);
            pageResult.put("total", productList.size());
            result.setData(pageResult);
            result.setMsg("查询成功");
        } catch (Exception e) {
            logger.error("数据异常：{}", e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        logger.info("查询列表信息为： {}", result.getData());
        return result;
    }

    /**
     * 更新是否显示到首页、导航、商城
     */
    @RequestMapping(value = "/updateHomeDisplay", method = RequestMethod.POST)
    public Result updateHomeDisplay(@RequestBody(required=false) Map<String,Object> params) {
        Result result = new Result();
        try {
            //更新货架上的商品价格
            pushShopCommodityService.updateHomeDisplay(params);
            result.setMsg("更新货架商品价格成功");

        } catch (Exception e) {
            logger.error("异常信息：", e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg("更新货架商品价格失败");
        }
        return result;
    }
}
