package com.ovit.nswy.portal.controller;

import com.github.pagehelper.PageInfo;
import com.ovit.nswy.portal.core.Result;
import com.ovit.nswy.portal.core.ResultCode;
import com.ovit.nswy.portal.service.CurrencyCommodityService;
import com.ovit.nswy.portal.service.StandardService;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 通用商品名/通用服务名管理
 */
@RestController
@RequestMapping("/currencyCommodity")
public class CurrencyCommodityController {

    private Logger logger = LogManager.getLogger(this.getClass());

    @Autowired
    private CurrencyCommodityService currencyCommodityService;

    @Autowired
    private StandardService standardService;

    /**
     * 获取通用商品列表信息
     */
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public Result list(@RequestBody(required=false) Map<String,Object> params) {
        Result result = new Result();
        try {
            //type:1,查通用商品， type:2，查通用服务
            //params.put("type", 1);
            List<Map<String, Object>> commodityList = currencyCommodityService.list(params);
            PageInfo commodityPage = new PageInfo(commodityList);
            result.setData(commodityPage);

        } catch (Exception e) {
            logger.error("异常信息：", e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg("获取通用商品列表信息失败");
        }
        return result;
    }

    /**
     * 通过id查询通用商品的信息
     */
    @RequestMapping(value = "/findCommodityById", method = RequestMethod.POST)
    public Result findCommodityById(@RequestBody(required=false) Map<String,Object> params) {
        Result result = new Result();
        try {

            Map<String, Object> commodityMap = currencyCommodityService.findCommodityById(params);
            result.setData(commodityMap);

        } catch (Exception e) {
            logger.error("异常信息：", e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg("查询通用商品的信息失败");
        }
        return result;
    }

    /**
     * 通过id删除通用商品
     */
    @RequestMapping(value = "/deleteCommodityById", method = RequestMethod.POST)
    public Result deleteCommodityById(@RequestBody(required=false) Map<String,Object> params) {
        Result result = new Result();
        try {
            //通过id删除通用商品
            currencyCommodityService.deleteCommodityById(params);
            result.setMsg("删除通用商品成功");

        } catch (Exception e) {
            logger.error("异常信息：", e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg("删除通用商品失败");
        }
        return result;
    }

    @RequestMapping(value = "/deleteCommodity", method = RequestMethod.POST)
    public Result deleteCommodity(@RequestBody(required=false) Map<String,Object> params) {
        Result result = new Result();
        try {
            List<Map<String,Object>> list = (List<Map<String,Object>>)params.get("dataList");
            for(Map<String,Object> map :list){
                //通过id删除通用商品
                currencyCommodityService.deleteCommodityById(map);
            }

            result.setMsg("删除通用商品成功");

        } catch (Exception e) {
            logger.error("异常信息：", e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg("删除通用商品失败");
        }
        return result;
    }

    /**
     * 保存通用商品信息，存在则更新
     */
    @RequestMapping(value = "/saveOrUpdate", method = RequestMethod.POST)
    public Result saveOrUpdate(@RequestBody(required=false) Map<String,Object> params) {
        Result result = new Result();
        try {
            //保存通用商品信息
            if (StringUtils.isNotBlank(MapUtils.getString(params, "id"))) {
                Map<String, Object> hisCommodityInfo = currencyCommodityService.findCommodityById(params);
                if (MapUtils.isNotEmpty(hisCommodityInfo)) {
                    params.put("id", MapUtils.getInteger(hisCommodityInfo, "id"));
                    params.put("createTime", hisCommodityInfo.get("createTime"));
                }
            } else {
                params.put("createTime", new Date());
            }

            //保存或者更新时，都将审核状态改为待审核
            params.put("aduitStatus", "0");
            //若审核不通过，重新保存时将拒绝理由清空
            params.put("refuseReason", "");
            //type:1,查通用商品， type:2，查通用服务
            //params.put("type", 1);
            currencyCommodityService.insertCommodityInfo(params);

        } catch (Exception e) {
            logger.error("异常信息：", e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg("保存通用商品信息失败");
        }
        return result;
    }

    /**
     * 新增通用商品、通用服务时，查看名称是否重复
     */
    @RequestMapping(value = "/isExist", method = RequestMethod.POST)
    public Result isExist(@RequestBody(required=false) Map<String,Object> params) {
        Result result = new Result();
        try {
            //type:1,查通用商品， type:2，查通用服务
            Boolean isExist = standardService.isExist(params);
            result.setData(isExist);

        } catch (Exception e) {
            logger.error("异常信息：", e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg("获取通用商品列表信息失败");
        }
        return result;
    }

}
