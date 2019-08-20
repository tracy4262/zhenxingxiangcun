package com.ovit.shop.schedule;

import com.ovit.shop.pushTemplate.mapper.ShopBiddingMapper;
import com.ovit.shop.pushTemplate.mapper.ShopOrderMapper;
import com.ovit.shop.pushTemplate.mapper.ShopOrderProductMapper;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Component
public class ShopBiddingSchedule {

    private Logger logger = LogManager.getLogger(this.getClass());

    private  static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Autowired
    private ShopBiddingMapper shopBiddingMapper;

    @Autowired
    private ShopOrderMapper shopOrderMapper;

    @Autowired
    private ShopOrderProductMapper shopOrderProductMapper;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Scheduled(cron="${update.expire.time}")
    public void updateOrderStatus(){
        //查询提交保证金但2小时未支付的订单
        List<Map<String, Object>> list = shopBiddingMapper.orderList();
        String currentTime = dateFormat.format(new Date());
        if (CollectionUtils.isNotEmpty(list)) {
            for (Map<String,Object> map : list) {
                String endTime = MapUtils.getString(map, "expireTime");
                try {
                    Date endDate = dateFormat.parse(endTime);
                    Date currentDate = dateFormat.parse(currentTime);
                    int id = MapUtils.getInteger(map, "id");
                    if(endDate.equals(currentDate) || endDate.before(currentDate)) {
                        //截止时间已到, 更新订单状态为已过期
                        shopBiddingMapper.updateBiddingOrder(id);
                    } else {
                        logger.info("该未支付的保证金订单id:{},对应截止时间还没有到", id);
                    }
                } catch (ParseException e) {
                    logger.error("时间转换异常：", e);
                }
            }
        } else {
            logger.info("目前没有未支付的保证金订单");
        }

    }

    @Scheduled(cron="${update.expire.time}")
    public void updateExpireOrder(){
        //截止时间到了但未支付的订单
        List<Map<String, Object>> list = shopBiddingMapper.expireList();
        if (CollectionUtils.isNotEmpty(list)) {
            for (Map<String,Object> map : list) {
                try {
                    int id = MapUtils.getInteger(map, "id");
                    //更新订单状态为已过期
                    shopBiddingMapper.updateBiddingOrder(id);
                } catch (Exception e) {
                    logger.error("更新异常：", e);
                }
            }
        } else {
            logger.info("截止时间到了但未支付的订单");
        }

    }

    @Scheduled(cron="${update.expire.time}")
    public void updatePresaleOrder(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        //尾款截止时间到了但未支付的订单
        Map<String, Object> query = new HashMap<>();
        query.put("shopType",1);
        List<Map<String, Object>> list = shopOrderMapper.findAllBuyList(query);
        for (Map<String, Object> item : list){
            query.put("code", MapUtils.getString(item,"orderCode"));
            query.put("sellerAccount", MapUtils.getString(item,"sellerAccount"));
            item.put("shopProducts",shopOrderProductMapper.selectExtendByOrderCode(query));

            List<Map<String,Object>> shopPList = (List<Map<String,Object>>)item.get("shopProducts");
            if(CollectionUtils.isNotEmpty(shopPList)){
                for(Map<String, Object> shops : shopPList){
                    /**
                     * 根据商品id查询得到预售商品
                     */

                        Query shopQuery = new Query();
                        Criteria  criteria =  Criteria.where("pushShopCommodityId").is(MapUtils.getString(shops,"commodityId"));
                        //查询
                        shopQuery.addCriteria(criteria);
                        shopQuery.fields().exclude("_id");
                        //价格
                        List<Map> pricing = mongoTemplate.find(shopQuery, Map.class, "push_marketing_commodity_pricing_info");
                        if(CollectionUtils.isNotEmpty(pricing)) {
                            shops.put("endPaymentTime", MapUtils.getObject(pricing.get(0),"endPaymentTime"));//尾款支付时间段
                            List<String> endPaymentTime = (List)MapUtils.getObject(pricing.get(0),"endPaymentTime");
                            try {
                                String currentTime = sdf.format(new Date());
                                Date endDate = sdf.parse(endPaymentTime.get(1));
                                Date currentDate = sdf.parse(currentTime);
                                if(endDate.before(currentDate) && (MapUtils.getString(item,"status").equals("1")||MapUtils.getString(item,"status").equals("2")||MapUtils.getString(item,"status").equals("15"))){
                                    item.put("status",16);
                                    shopOrderMapper.updateState(item);
                                }
                            } catch (ParseException e) {
                                logger.error("error:{},{}", endPaymentTime.get(1), e);
                            }
                        }
                    }

            } else {
                logger.info("截止时间到了但未支付的订单");
            }

        }
    }
}
