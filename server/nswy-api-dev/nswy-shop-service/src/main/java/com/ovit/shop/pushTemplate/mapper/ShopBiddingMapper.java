package com.ovit.shop.pushTemplate.mapper;

import java.util.List;
import java.util.Map;

public interface ShopBiddingMapper {

    void submitMargin(Map<String, Object> params);

    void updateBidding(Map<String, Object> params);

    Integer getBiddingStatus(Map<String, Object> params);

    List<Map<String,Object>> orderList();

    List<Map<String,Object>> expireList();

    void updateBiddingOrder(int id);

    Map<String,Object> bidRecord(Map<String,Object> params);

    Integer getParticipantCount(Map<String, Object> params);

    Map<String,Object> getBidInfo(Map<String,Object> params);

    Map<String,Object> getRemainderInfo(Map<String,Object> params);

    List<Map<String, Object>> getMyMargin(Map<String,Object> params);

    Map<String,Object> memberInfo(Map<String, Object> params);

    Map<String,Object> findUseTemplate(String account);

    Integer findSalesNumber(Map<String, Object> params);

    Integer findBuyer(Map<String, Object> params);

    Integer findRate(Map<String, Object> params);

    List<Map<String,Object>> auctioning(Map<String, Object> params);

    List<Map<String,Object>> hasBid(Map<String, Object> params);

    List<Map<String,Object>> missBid(Map<String, Object> params);

    List<Map<String,Object>> upcomingAuctionDetail(Map<String, Object> params);

    Integer hasBidCount(String commodityId);

    List<Map<String,Object>> auctioningDetail(Map<String, Object> params);

    Map<String,Object> auctionRecord(Map<String,Object> params);

    Integer participantCount(Map<String, Object> params);

    void cancelOrder(int id);

    Map<String,Object> orderDetail(int id);

    String bidNum(Map<String, Object> params);

    void batchHandle(List<Map<String, Object>> params);

}
