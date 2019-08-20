package com.ovit.shop.pushTemplate.service;

import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

public interface ShopBiddingService {

    /**
     * 提交保证金页面信息查询
     * @param params
     * @return
     */
    Map<String,Object> marginInfo(Map<String, Object> params);

    /**
     * 提交保证金
     * @param params
     */
    void submitMargin(Map<String, Object> params);

    /**
     * 支付保证金
     * @param params
     */
    void payMargin(Map<String, Object> params);

    /**
     * 立即竞价
     * @param params
     */
    void snatch(Map<String, Object> params);

    /**
     * 获取某商品被竞价的次数
     * @param key
     * @return
     */
    int getBiddingCount(String key);

    /**
     * 处理竞拍结果
     * @param params
     */
    void handle(List<Map<String, Object>> params);

    /**
     * 查询出价记录
     * @param params
     * @return
     */
    Map<String,Object> bidRecord(Map<String, Object> params);


    /**
     * 查询我的保证金
     * @param params
     * @return
     */
    List myMargin(Map<String, Object> params);

    /**
     * 买家：我参与的竞拍：竞拍中
     * @param params
     * @return
     */
    PageInfo<Map<String, Object>> auctioning(Map<String, Object> params);

    /**
     * 买家：我参与的竞拍：已获拍
     * @param params
     * @return
     */
    PageInfo<Map<String, Object>> hasBid(Map<String, Object> params);

    /**
     * 买家：我参与的竞拍：未获拍
     * @param params
     * @return
     */
    PageInfo<Map<String, Object>> missBid(Map<String, Object> params);

    /**
     * 卖家：我发起的竞拍：即将竞拍列表
     * @param params
     * @return
     */
    Map<String,Object> upcomingAuctionList(Map<String, Object> params);

    /**
     * 卖家：我发起的竞拍：即将竞拍详情
     * @param params
     * @return
     */
    Map<String, Object> upcomingAuctionDetail(Map<String, Object> params);

    /**
     * 卖家：我发起的竞拍：竞拍中列表
     * @param params
     * @return
     */
    Map<String,Object> auctioningList(Map<String, Object> params);

    /**
     * 卖家：我发起的竞拍：竞拍中详情
     * @param params
     * @return
     */
    Map<String, Object> auctioningDetail(Map<String, Object> params);

    /**
     * 卖家：我发起的竞拍：竞拍结果确认列表
     * @param params
     * @return
     */
    Map<String,Object> confirmList(Map<String, Object> params);

    /**
     * 卖家：我发起的竞拍：竞拍结果确认详情
     * @param params
     * @return
     */
    Map<String,Object> confirmDetail(Map<String, Object> params);

    /**
     * 卖家：我发起的竞拍：即将竞拍/竞拍中，手动取消订单，即将保证金订单设置为过期状态
     * @param id
     */
    void cancelOrder(int id);

    /**
     * 卖家：我发起的竞拍：订单详情
     * @param id
     * @return
     */
    Map<String,Object> orderDetail(int id);

}
