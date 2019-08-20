package com.ovit.shop.pushTemplate;

import com.ovit.shop.pushTemplate.mapper.ShopBiddingMapper;
import com.ovit.shop.pushTemplate.mapper.ShopOrderMapper;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Component
public class ShopOrderSchedule {

    private Logger logger = LogManager.getLogger(this.getClass());

    private  static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Autowired
    private ShopOrderMapper shopOrderMapper;

    @Scheduled(cron="${update.expire.time}")
    public void updateOrderStatus(){
        //查询得到所有未支付的订单
        List<Map<String, Object>> list = shopOrderMapper.findShopOrderListByPay();
        String currentTime = dateFormat.format(new Date());
        if (CollectionUtils.isNotEmpty(list)) {
            for (Map<String,Object> map : list) {
                String createTime = MapUtils.getString(map, "createTime");
                try {
                    Date createDate = dateFormat.parse(createTime);

                    Calendar calendar = Calendar.getInstance(); // 得到日历
                    calendar.setTime(createDate);// 把当前时间赋给日历
                    calendar.add(Calendar.DAY_OF_MONTH, +1); // 设置为后一天
                    createDate = calendar.getTime(); // 得到后一天的时间


                    Date currentDate = dateFormat.parse(currentTime);
                    String orderCode = MapUtils.getString(map,"orderCode");
                    if(createDate.equals(currentDate) || createDate.before(currentDate)) {
                        //创建时间超过24小时
                        Map<String,Object> order = new HashMap<>();
                        order.put("status",16);
                        order.put("orderCode",orderCode);
                        shopOrderMapper.updateBuyerState(order);
                    } else {
                        logger.info("该未支付的订单id:{},对应截止时间还没有到", orderCode);
                    }
                } catch (ParseException e) {
                    logger.error("时间转换异常：", e);
                }
            }
        } else {
            logger.info("目前没有未支付的订单");
        }

    }
}
