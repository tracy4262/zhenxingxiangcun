package com.ovit.nswy.member.schedule;

import com.ovit.nswy.member.application.mapper.EmployMapper;
import org.apache.commons.collections.MapUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Component
public class ConsultOrderSchedule {

    private Logger logger = LogManager.getLogger(this.getClass());

    private  static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Autowired
    private EmployMapper employMapper;

    @Scheduled(cron="${update.consult.time}")
    public void updateOrderStatus(){
        //查询状态未结束的咨询订单
        List<Map<String, Object>> list = employMapper.orderList();
        String currentTime = dateFormat.format(new Date());
        for (Map<String,Object> map : list) {
            String endTime = MapUtils.getString(map, "endTime");
            try {
                Date endDate = dateFormat.parse(endTime);
                Date currentDate = dateFormat.parse(currentTime);
                int id = MapUtils.getInteger(map, "id");
                if(endDate.equals(currentDate) || endDate.before(currentDate)) {
                    //截止时间已到, 更新订单状态
                    employMapper.updateConsultOrder(id);
                } else {
                    logger.info("该订单id:{},对应的订单的截止时间还没有到", id);
                }
            } catch (ParseException e) {
                logger.error("时间转换异常：", e);
            }

        }
    }


    /**
     * 更新订单状态，除咨询外的所有订单
     */
    @Scheduled(cron="${update.consult.time}")
    public void updateAllOrderStatus(){
        //查询未付款的订单
        List<Map<String, Object>> list = employMapper.findOrderList();
        String currentTime = dateFormat.format(new Date());
        for (Map<String,Object> map : list) {
            String createTime = MapUtils.getString(map, "createTime");
            try {
                //获取订单的时间， +1，与当前时间比较，如果当前时间大于createDate,则将订单状态改为7
                Date createDate = dateFormat.parse(createTime);
                Calendar date = Calendar.getInstance();
                date.setTime(createDate);
                date.add(Calendar.DAY_OF_MONTH, 1);
                createDate = date.getTime();

                Date currentDate = dateFormat.parse(currentTime);
                int id = MapUtils.getInteger(map, "id");
                if(currentDate.after(createDate)) {
                    employMapper.updateServiceOrder(id);
                } else {
                    logger.info("该订单id:{},对应的订单还未超过24小时！", id);
                }
            } catch (ParseException e) {
                logger.error("时间转换异常：", e);
            }

        }
    }
}
