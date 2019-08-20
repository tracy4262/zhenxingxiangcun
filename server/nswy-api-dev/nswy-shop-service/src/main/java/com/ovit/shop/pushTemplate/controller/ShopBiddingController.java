package com.ovit.shop.pushTemplate.controller;

import com.github.pagehelper.PageInfo;
import com.ovit.shop.pushTemplate.service.ShopBiddingService;
import com.ovit.shop.pushTemplate.service.ShopStorageInfoService;
import com.ovit.shop.util.PageUtil;
import com.ovit.shop.util.Result;
import com.ovit.shop.util.ResultCode;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 商品竞价
 */
@RestController
@RequestMapping("/shopBidding")
public class ShopBiddingController {

    private Logger logger = LogManager.getLogger(this.getClass());

    @Autowired
    private ShopBiddingService shopBiddingService;

    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * 买家：提交保证金页面信息查询
     */
    @RequestMapping(value = "/marginInfo", method = RequestMethod.POST)
    public Result getShopStorageList(@RequestBody Map<String,Object> params) {
        Result result = new Result();
        try {
            Map<String, Object> resultMap = shopBiddingService.marginInfo(params);
            result.setData(resultMap);
            result.setMsg("提交保证金页面信息查询成功");

        } catch (Exception e) {
            logger.error("异常信息：", e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg("提交保证金页面信息查询失败");
        }
        logger.info("提交保证金页面信息查询信息为： {}", result.getData());
        return result;
    }

    /**
     * 买家：提交保证金
     * @param params
     * @return
     */
    @RequestMapping(value = "/submitMargin", method = RequestMethod.POST)
    public Result submitMargin(@RequestBody Map<String,Object> params) {
        Result result = new Result();
        try {
            //提交保证金
            shopBiddingService.submitMargin(params);
            result.setMsg("提交保证金成功");

        } catch (Exception e) {
            logger.error("异常信息：", e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg("提交保证金失败");
        }
        return result;
    }

    /**
     * 买家：支付保证金
     * @param params
     * @return
     */
    @RequestMapping(value = "/payMargin", method = RequestMethod.POST)
    public Result payMargin(@RequestBody Map<String,Object> params) {
        Result result = new Result();
        try {
            shopBiddingService.payMargin(params);
            result.setMsg("支付保证金成功");

        } catch (Exception e) {
            logger.error("异常信息：", e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg("支付保证金失败");
        }
        return result;
    }

    /**
     * 买家：立即竞价
     * @param params
     * @return
     */
    @RequestMapping(value = "/snatch", method = RequestMethod.POST)
    public Result snatch(@RequestBody Map<String,Object> params) {
        Result result = new Result();
        try {
            //立即竞价
            shopBiddingService.snatch(params);
            result.setMsg("立即竞价成功");

        } catch (Exception e) {
            logger.error("异常信息：", e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg("立即竞价失败");
        }
        return result;
    }

    /**
     * 买家：在商品详情处，查询出价记录
     * @param params
     * @return
     */
    @RequestMapping(value = "/bidRecord", method = RequestMethod.POST)
    public Result bidRecord(@RequestBody Map<String,Object> params) {
        Result result = new Result();
        try {
            Map<String, Object> bidRecord = shopBiddingService.bidRecord(params);
            result.setData(bidRecord);
            result.setMsg("查询出价记录成功");

        } catch (Exception e) {
            logger.error("异常信息：", e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg("查询出价记录失败");
        }
        return result;
    }

    /**
     * 订单，我参与的竞拍，我的保证金
     * @param params
     * @return
     */
    @RequestMapping(value = "/getMyMargin", method = RequestMethod.POST)
    public Result getMyMargin(@RequestBody Map<String,Object> params) {
        Result result = new Result();
        try {
            List<Map<String, Object>> myMargin = shopBiddingService.myMargin(params);
            List<Map<String, Object>> myMarginResult = PageUtil.pageForList(MapUtils.getInteger(params, "num"), MapUtils.getInteger(params, "size"), myMargin);
            Map<String, Object> pageResult = new HashMap<>();
            pageResult.put("list", myMarginResult);
            pageResult.put("total", myMargin.size());
            result.setData(pageResult);
            result.setMsg("查询成功");

        } catch (Exception e) {
            logger.error("异常信息：", e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg("查询失败");
        }
        return result;
    }

    /**
     * 买家：我参与的竞拍：竞拍中
     * @param params
     * @return
     */
    @RequestMapping(value = "/auctioning", method = RequestMethod.POST)
    public Result auctioning(@RequestBody Map<String,Object> params) {
        Result result = null;
        try {
            result = new Result();
            PageInfo<Map<String, Object>> auctioning = shopBiddingService.auctioning(params);
            result.setData(auctioning);
            result.setMsg("我参与的竞拍-竞拍中的订单查询成功");

        } catch (Exception e) {
            logger.error("数据异常：{}", e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     * 买家：我参与的竞拍：已获拍
     * @param params
     * @return
     */
    @RequestMapping(value = "/hasBid", method = RequestMethod.POST)
    public Result hasBid(@RequestBody Map<String,Object> params) {
        Result result = null;
        try {
            result = new Result();
            PageInfo<Map<String, Object>> hasBid = shopBiddingService.hasBid(params);
            result.setData(hasBid);
            result.setMsg("我参与的竞拍-已获拍的订单查询成功");

        } catch (Exception e) {
            logger.error("数据异常：{}", e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     * 买家：我参与的竞拍：未获拍
     * @param params
     * @return
     */
    @RequestMapping(value = "/missBid", method = RequestMethod.POST)
    public Result missBid(@RequestBody Map<String,Object> params) {
        Result result = null;
        try {
            result = new Result();
            PageInfo<Map<String, Object>> hasBid = shopBiddingService.missBid(params);
            result.setData(hasBid);
            result.setMsg("我参与的竞拍-未获拍的订单查询成功");

        } catch (Exception e) {
            logger.error("数据异常：{}", e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     * 卖家：我发起的竞拍：即将竞拍列表
     * @param params
     * @return
     */
    @RequestMapping(value = "/launch/upcomingAuctionList", method = RequestMethod.POST)
    public Result upcomingAuctionList(@RequestBody Map<String,Object> params) {
        Result result = null;
        try {
            result = new Result();
            Map<String, Object> upcomingAuctionList = shopBiddingService.upcomingAuctionList(params);
            result.setData(upcomingAuctionList);
            result.setMsg("我发起的竞拍-即将竞拍列表查询成功");

        } catch (Exception e) {
            logger.error("数据异常：{}", e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     * 卖家：我发起的竞拍：即将竞拍详情
     * @param params
     * @return
     */
    @RequestMapping(value = "/launch/upcomingAuctionDetail", method = RequestMethod.POST)
    public Result upcomingAuctionDetail(@RequestBody Map<String,Object> params) {
        Result result = null;
        try {
            result = new Result();
            Map<String, Object> upcomingAuctionDetail = shopBiddingService.upcomingAuctionDetail(params);
            result.setData(upcomingAuctionDetail);
            result.setMsg("我发起的竞拍-即将竞拍详情查询成功");

        } catch (Exception e) {
            logger.error("数据异常：{}", e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     * 卖家：我发起的竞拍：竞拍中列表
     * @param params
     * @return
     */
    @RequestMapping(value = "/launch/auctioningList", method = RequestMethod.POST)
    public Result auctioningList(@RequestBody Map<String,Object> params) {
        Result result = null;
        try {
            result = new Result();
            Map<String, Object> auctioningList = shopBiddingService.auctioningList(params);
            result.setData(auctioningList);
            result.setMsg("我发起的竞拍-竞拍中列表查询成功");

        } catch (Exception e) {
            logger.error("数据异常：{}", e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     * 卖家：我发起的竞拍：竞拍中详情
     * @param params
     * @return
     */
    @RequestMapping(value = "/launch/auctioningDetail", method = RequestMethod.POST)
    public Result auctioningDetail(@RequestBody Map<String,Object> params) {
        Result result = null;
        try {
            result = new Result();
            Map<String, Object> auctioningDetail = shopBiddingService.auctioningDetail(params);
            result.setData(auctioningDetail);
            result.setMsg("我发起的竞拍-竞拍中详情查询成功");

        } catch (Exception e) {
            logger.error("数据异常：{}", e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     * 卖家：我发起的竞拍：竞拍结果确认列表
     * @param params
     * @return
     */
    @RequestMapping(value = "/launch/confirmList", method = RequestMethod.POST)
    public Result confirmList(@RequestBody Map<String,Object> params) {
        Result result = null;
        try {
            result = new Result();
            Map<String, Object> confirmList = shopBiddingService.confirmList(params);
            result.setData(confirmList);
            result.setMsg("我发起的竞拍-竞拍结果确认列表查询成功");

        } catch (Exception e) {
            logger.error("数据异常：{}", e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     * 卖家：我发起的竞拍：竞拍结果确认详情
     * @param params
     * @return
     */
    @RequestMapping(value = "/launch/confirmDetail", method = RequestMethod.POST)
    public Result confirmDetail(@RequestBody Map<String,Object> params) {
        Result result = null;
        try {
            result = new Result();
            Map<String, Object> confirmDetail = shopBiddingService.confirmDetail(params);
            result.setData(confirmDetail);
            result.setMsg("我发起的竞拍-竞拍结果确认列表查询成功");

        } catch (Exception e) {
            logger.error("数据异常：{}", e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     * 卖家：我发起的竞拍：即将竞拍/竞拍中，手动取消订单，即将保证金订单设置为过期状态
     * @param params
     * @return
     */
    @RequestMapping(value = "/launch/cancelOrder", method = RequestMethod.GET)
    public Result cancelOrder(@RequestParam Integer id) {
        Result result = null;
        try {
            result = new Result();
            shopBiddingService.cancelOrder(id);
            result.setMsg("卖家操作：订单取消成功");

        } catch (Exception e) {
            logger.error("数据异常：{}", e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     * 卖家：我发起的竞拍：订单详情
     * @param params
     * @return
     */
    @RequestMapping(value = "/launch/orderDetail", method = RequestMethod.GET)
    public Result orderDetail(@RequestParam Integer id) {
        Result result = null;
        try {
            result = new Result();
            Map<String,Object> orderDetail = shopBiddingService.orderDetail(id);
            result.setData(orderDetail);
            result.setMsg("卖家操作：订单详情查询成功");

        } catch (Exception e) {
            logger.error("数据异常：{}", e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     * 卖家：在竞拍结束时间到时候,处理竞拍结果
     * @param params
     * @return
     */
    @RequestMapping(value = "/launch/handle", method = RequestMethod.POST)
    public Result handle(@RequestBody List<Map<String,Object>> params) {
        Result result = new Result();
        try {
            //卖家处理竞拍结果
            shopBiddingService.handle(params);
            result.setMsg("处理竞拍成功");

        } catch (Exception e) {
            logger.error("异常信息：", e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg("处理竞拍失败");
        }
        return result;
    }

}
