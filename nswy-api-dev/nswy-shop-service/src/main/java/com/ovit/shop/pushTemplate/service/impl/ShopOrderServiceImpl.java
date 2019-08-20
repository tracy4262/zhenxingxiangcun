package com.ovit.shop.pushTemplate.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.xphsc.bean.BeanByRefMapper;
import com.github.xphsc.mutable.Integers;

import com.github.xphsc.util.StringUtil;
import com.mongodb.WriteResult;
import com.ovit.shop.core.enums.NormalEnum.*;
import com.ovit.shop.pushTemplate.mapper.ShopBiddingMapper;
import com.ovit.shop.pushTemplate.mapper.ShopOrderLogMapper;
import com.ovit.shop.pushTemplate.mapper.ShopOrderMapper;
import com.ovit.shop.pushTemplate.mapper.ShopOrderProductMapper;
import com.ovit.shop.pushTemplate.service.ShopOrderService;
import com.ovit.shop.util.ImageUtil;
import com.ovit.shop.util.PageList;
import com.ovit.shop.util.Utils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.omg.CORBA.OBJ_ADAPTER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by wangrongf
 */
@Service
public class ShopOrderServiceImpl implements ShopOrderService {


    private static final Logger logger = LogManager.getLogger(ShopOrderServiceImpl.class);

    @Autowired
    private ShopOrderMapper shopOrderMapper;

   @Autowired
    private ShopOrderProductMapper shopOrderProductMapper;

    @Autowired
    private MongoTemplate mongoTemplate;

     @Autowired
    private ShopOrderLogMapper shopOrderLogMapper;

    @Override
    public PageList list(Map<String, Object> map, Map<String, Object> page, String account) {

        Map<String, Object> query = new HashMap<>();
        query.put("account", account);
        if (StringUtils.isNotBlank(MapUtils.getString(map, "buyer"))) {
            query.put("buyer", MapUtils.getString(map, "buyer"));
        }
        if (StringUtils.isNotBlank(MapUtils.getString(map, "seller"))) {
            query.put("seller", MapUtils.getString(map, "seller"));
        }
        if (StringUtils.isNotBlank(MapUtils.getString(map, "startDate"))) {
            query.put("startDate", MapUtils.getString(map, "startDate"));
        }
        if (StringUtils.isNotBlank(MapUtils.getString(map, "endDate"))) {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");//*** 加一天*//*
            try {
                Date dd = df.parse(MapUtils.getString(map, "endDate"));
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(dd);
                calendar.add(Calendar.DAY_OF_MONTH, 1);//加一天
                query.put("endDate", df.format(calendar.getTime()));
            } catch (Exception ex) {

            }
        }
        String productName = MapUtils.getString(map, "productName");//商品信息
        if (StringUtils.isNotBlank(productName)) {
            query.put("productName", productName);
        }
        Integer from = MapUtils.getInteger(map, "from"); //是卖，还是买
        Integer state = MapUtils.getInteger(map, "state");//tab页状态
        Integer dealState = MapUtils.getInteger(map, "dealState", 0);
        Integer judgeState = MapUtils.getInteger(map, "judgeState", 0);
        if (state.equals(0)) {
            //目前设定，当交易状态为0的时候，
            query= getBasicQuery(query,from,dealState,judgeState);
        } else {
            query= getBasicQuery(query,state,from,dealState,judgeState);
        }

        List<Map<String,Object>> list = new ArrayList<>();
        if (StringUtils.isNotBlank(productName)) {
            if (from == ShopAccountType.Buy.ordinal()) {
                //买
                //如果有productName 先查询详细表得到数据， 循环得到订单数据
                List<Map<String,Object>> orderCodeList = shopOrderProductMapper.selectByOrderCodeBuy(query);
                for(Map<String,Object> buyMap:orderCodeList){
                    query.put("sellerAccount",MapUtils.getString(buyMap,"sellerAccount"));
                    query.put("orderCode",MapUtils.getString(buyMap,"orderCode"));
                    List<Map<String,Object>> orderlist = shopOrderMapper.findAllBuyList(query);
                    //orderlist.get(0).put("shopProducts", buyMap);
                    if(CollectionUtils.isNotEmpty(orderlist)){
                        list.add(orderlist.get(0));
                    }
                }

                //list = shopOrderMapper.findBuyList(query);
            } else {
                //卖
                //如果有productName 先查询详细表得到数据， 循环得到订单数据
                List<Map<String,Object>> orderCodeList = shopOrderProductMapper.selectExtendByOrderCode(query);
                for(Map<String,Object> buyMap:orderCodeList){
                    query.put("sellerAccount",MapUtils.getString(buyMap,"sellerAccount"));
                    query.put("orderCode",MapUtils.getString(buyMap,"orderCode"));
                    List<Map<String,Object>> orderlist = shopOrderMapper.findAllSellList(query);
                    //orderlist.get(0).put("shopProducts", buyMap);
                    if(CollectionUtils.isNotEmpty(orderlist)){
                        list.add(orderlist.get(0));
                    }

                }
                //list = shopOrderMapper.findSellList(query);
            }

        } else {
            if (from == ShopAccountType.Buy.ordinal()) {
                //买
                list = shopOrderMapper.findAllBuyList(query);
            } else {
                //卖
                list = shopOrderMapper.findAllSellList(query);
            }


        }
        if (list != null && list.size() > 0) {

            query.clear();
            if (StringUtils.isNotBlank(productName)) {
                query.put("productName", productName);
            }

            for (Map<String, Object> item : list) {
                query.put("code", MapUtils.getString(item,"orderCode"));
                query.put("sellerAccount", MapUtils.getString(item,"sellerAccount"));
                item.put("shopProducts",shopOrderProductMapper.selectExtendByOrderCode(query));

                List<Map<String,Object>> shopPList = (List<Map<String,Object>>)item.get("shopProducts");
                if(CollectionUtils.isNotEmpty(shopPList)){
                    for(Map<String, Object> shops : shopPList){
                        Query shopQuery = new Query();
                        Criteria criteria =  Criteria.where("id").is(MapUtils.getString(shops,"commodityId"));
                        //查询
                        shopQuery.addCriteria(criteria);
                        shopQuery.fields().exclude("_id");
                        //价格
                        List<Map> shop = mongoTemplate.find(shopQuery, Map.class, "push_shop_commodity_info");
                        if(CollectionUtils.isNotEmpty(shop)) {
                            shops.put("productNameId", MapUtils.getString(shop.get(0),"productNameId"));//预售价格

                        }
                        /**
                         * 根据商品id查询得到预售商品的价格
                         */
                        if(MapUtils.getString(item,"shopType").equals("1")){

                            shopQuery = new Query();
                            criteria =  Criteria.where("pushShopCommodityId").is(MapUtils.getString(shops,"commodityId"));
                            //查询
                            shopQuery.addCriteria(criteria);
                            shopQuery.fields().exclude("_id");
                            //价格
                            List<Map> pricing = mongoTemplate.find(shopQuery, Map.class, "push_marketing_commodity_pricing_info");
                            if(CollectionUtils.isNotEmpty(pricing)) {
                                shops.put("orderPrice", MapUtils.getString(pricing.get(0),"orderPrice"));//预售价格
                                shops.put("endPaymentTime", MapUtils.getObject(pricing.get(0),"endPaymentTime"));//尾款支付时间段

                            }


                        }

                        //根据竞拍编号查询保证金
                        if(MapUtils.getString(item,"shopType").equals("4")){
                            shops.put("margin",shopOrderMapper.getMargin(query));

                        }


                    }
                }


            }
        }



        for(Map<String,Object> jsonMap:list){

            if(StringUtils.isNotBlank(MapUtils.getString(jsonMap,"addressInfo"))){
                jsonMap.put("addressInfo",JSONArray.parse(MapUtils.getString(jsonMap,"addressInfo")));


            }

            if(StringUtils.isNotBlank(MapUtils.getString(jsonMap,"invoiceInfo"))){
                jsonMap.put("invoiceInfo",JSONArray.parse(MapUtils.getString(jsonMap,"invoiceInfo")));
            }

        }


        return PageList.pageInfoList(page,list);
    }

    //全部订单下，查询信息
    private  Map<String, Object>  getBasicQuery(Map<String, Object> query,Integer from,Integer dealState,Integer judgeState) {
        int status = 0;//等待发货
        int sellStatus = 0;//默认为null
        List<Integer> inStatus = null;
        if (dealState.equals(0) || (from == ShopAccountType.Sell.ordinal() && dealState.equals(4))) {
            if (judgeState.equals(1)) {
                //待评价
                if (from == ShopAccountType.Buy.ordinal()) {
                    status = OrderStatusType.TakeOver.GetCode();//等待评价
                } else {
                    sellStatus = 1;//没有评价
                    inStatus = new ArrayList<>();
                    inStatus.add(OrderStatusType.TakeOver.GetCode());
                }
            } else if (judgeState.equals(2)) {
                //已评价
                if (from == ShopAccountType.Buy.ordinal()) {
                    status = OrderStatusType.BuyerComment.GetCode();//评价
                } else {
                    sellStatus = OrderStatusType.SellerComment.GetCode();//评价
                }
            } else if (judgeState.equals(3)) {
                //双方已评
                status = OrderStatusType.BuyerComment.GetCode();//评价
                sellStatus = OrderStatusType.SellerComment.GetCode();//评价
            } else {
                if (dealState.equals(4)) {
                    //卖家，成功的订单
                    inStatus = new ArrayList<>();
                    inStatus.add(OrderStatusType.TakeOver.GetCode());
                    inStatus.add(OrderStatusType.BuyerComment.GetCode());
                }
            }
        } else {
            if (judgeState.equals(0)) {
                //代发货
                if (dealState.equals(1)) {
                    status = OrderStatusType.SendFor.GetCode();//等待发货
                } else if (dealState.equals(2)) {
                    //已发货
                    status = OrderStatusType.SendOut.GetCode();//等待收货
                } else if (dealState.equals(3)) {
                    //等待评价
                    if (from == ShopAccountType.Buy.ordinal()) {
                        status = OrderStatusType.TakeOver.GetCode();//等待评价
                    } else {
                        sellStatus = 1;//卖家没有评价
                        inStatus = new ArrayList<>();
                        inStatus.add(OrderStatusType.TakeOver.GetCode());
                        inStatus.add(OrderStatusType.BuyerComment.GetCode());
                    }
                } else if (dealState.equals(4)) {
                    //买家，已取消，无卖家
                    inStatus = new ArrayList<>();
                    //  inStatus.add(OrderStatusType.BuyerCancel.GetCode());
                    inStatus.add(OrderStatusType.CancelOver.GetCode());
                    inStatus.add(OrderStatusType.SellerCancel.GetCode());
                } else if (dealState.equals(5)) {
                    //买家，已退货，卖家，关闭的订单
                    inStatus = new ArrayList<>();
                    // inStatus.add(OrderStatusType.BuyerRetreat.GetCode());
                    inStatus.add(OrderStatusType.RetreatOver.GetCode());
                    if (from == ShopAccountType.Sell.ordinal()) {
                        // inStatus.add(OrderStatusType.BuyerCancel.GetCode());
                        inStatus.add(OrderStatusType.CancelOver.GetCode());
                        inStatus.add(OrderStatusType.SellerCancel.GetCode());
                    }
                }else if (dealState.equals(6)) {
                    //买家， 卖家，取消中
                    inStatus = new ArrayList<>();
                    inStatus.add(OrderStatusType.BuyerCancel.GetCode());//买家取消
                }
                else if (dealState.equals(7)) {
                    //买家，卖家，退货中
                    inStatus = new ArrayList<>();
                    inStatus.add(OrderStatusType.BuyerRetreat.GetCode());//买家退货

                }
                else{
                    status = 100;
                }
            }else if (judgeState.equals(1)) {
                if (dealState.equals(3)) {
                    //等待评价
                    if (from == ShopAccountType.Buy.ordinal()) {
                        status = OrderStatusType.TakeOver.GetCode();//等待评价
                    } else {
                        sellStatus = 1;//卖家没有评价
                        inStatus = new ArrayList<>();
                        inStatus.add(OrderStatusType.TakeOver.GetCode());
                        inStatus.add(OrderStatusType.BuyerComment.GetCode());
                    }
                }else{
                    status = 100;
                }

            }else {
                status = 100;
            }
        }
        if (status > 0) {
            query.put("status", status);
        }
        if (sellStatus > 0) {
            query.put("sellStatus", sellStatus);
        }
        if (inStatus != null && inStatus.size() > 0) {
            query.put("inStatus", inStatus);
        }
        return query;
    }
    private  Map<String, Object>  getBasicQuery(Map<String, Object> query,Integer state,Integer from,Integer dealState,Integer judgeState) {
        int status = 100;//这里自动过滤所有状态信息
        int sellStatus = 0;//默认为null
        List<Integer> inStatus = null;
        if (state.equals(1)) {
            //等待发货，只有交易状态为等待发货生效
            if (judgeState.equals(0) && (dealState.equals(0) ||dealState.equals(1))) {
                //status = OrderStatusType.SendFor.GetCode();//等待发货
                status = OrderStatusType.Pay.GetCode();//等待发货

            }
        } else if (state.equals(2)) {
            //等待收货，只有交易状态为等待发货生效
            if (judgeState.equals(0) && (dealState.equals(0) ||dealState.equals(2))) {
                status = OrderStatusType.SendOut.GetCode();//等待收货
            }
        } else if (state.equals(3)) {
            //等待评价，交易状态位等待评价，与成功的订单
            if ((judgeState.equals(0)||judgeState.equals(1))&&(dealState.equals(0) || dealState.equals(3))) {
                if (from == ShopAccountType.Buy.ordinal()) {
                    status = OrderStatusType.TakeOver.GetCode();//买家待评价

                } else {
                    status = 0;
                    sellStatus = 1;//没有评价
                    inStatus = new ArrayList<>();
                    inStatus.add(OrderStatusType.TakeOver.GetCode());
                    inStatus.add(OrderStatusType.BuyerComment.GetCode());
                }
            }
        } else if (state.equals(4)) {
            //成功的订单

                if (dealState.equals(0) || dealState.equals(4)) {
                    status = 0;
                    inStatus = new ArrayList<>();
                    if (from == ShopAccountType.Buy.ordinal()) {
                        status = OrderStatusType.BuyerComment.GetCode();//买家已评价
                    } else {
                        sellStatus = OrderStatusType.SellerComment.GetCode();//卖家已评价
                    }

                    if (judgeState.equals(1)) {
                        sellStatus = 1;//评价
                    } else if (judgeState.equals(2)) {
                        sellStatus = OrderStatusType.BuyerComment.GetCode();//评价
                    } else if (judgeState.equals(3)) {
                        inStatus = null;
                        status = OrderStatusType.BuyerComment.GetCode();//评价
                        sellStatus = OrderStatusType.SellerComment.GetCode();//评价
                    }
                } else if (dealState.equals(3)) {
                    //等待评价
                    if (judgeState.equals(1)) {
                        inStatus = new ArrayList<>();
                        inStatus.add(OrderStatusType.TakeOver.GetCode());
                        inStatus.add(OrderStatusType.BuyerComment.GetCode());
                        sellStatus = 1;//评价
                        status = 0;
                    }
                }

        } else if (state.equals(5)) {
            //关闭的订单

                if (judgeState.equals(0)&&(dealState.equals(0) || dealState.equals(5))) {
                    status = 0;
                    inStatus = new ArrayList<>();
                    inStatus.add(OrderStatusType.CancelOver.GetCode());
                    inStatus.add(OrderStatusType.SellerCancel.GetCode());
                    inStatus.add(OrderStatusType.RetreatOver.GetCode());
                    inStatus.add(OrderStatusType.ImmediatelyCancel.GetCode());
                    inStatus.add(OrderStatusType.NotPayCancel.GetCode());
                }

        }else if (state.equals(6)) {
            //待付款
                    status = 0;
                    inStatus = new ArrayList<>();
                    inStatus.add(OrderStatusType.SendFor.GetCode());
                    inStatus.add(OrderStatusType.RevisePrice.GetCode());
                    inStatus.add(OrderStatusType.Paying.GetCode());

        }else if (state.equals(7)) {
            //退货、退款

                status = 0;
                inStatus = new ArrayList<>();
                inStatus.add(OrderStatusType.BuyerCancel.GetCode());
                inStatus.add(OrderStatusType.BuyerRetreat.GetCode());
                inStatus.add(OrderStatusType.RetreatCancel.GetCode());
                inStatus.add(OrderStatusType.CancelCancel.GetCode());

        }

        if (status > 0) {
            query.put("status", status);
        }
        if (sellStatus > 0) {
            query.put("sellStatus", sellStatus);
        }
        if (inStatus != null && inStatus.size() > 0) {
            query.put("inStatus", inStatus);
        }
        return query;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void add(Map<String,Object> params) {
        int result = 0;
        String orderCode = "";
        if (StringUtils.isNotBlank(MapUtils.getString(params, "orderCode"))) {
            orderCode = MapUtils.getString(params, "orderCode");
            //更新竞价商品的状态
            Map<String,Object> bidMap = new HashMap<>();
            bidMap.put("orderCode", orderCode);
            bidMap.put("bidStatus", 7);
            shopOrderMapper.updateBidStatus(bidMap);
        } else {
            orderCode = ChargeOrderCode(MapUtils.getString(params,"account"));
        }
        if (orderCode != null) {

            List<Map<String,Object>> shopOrderList = (List<Map<String,Object>>)params.get("shopProducts");

            for(Map<String,Object> map : shopOrderList){
                //存入订单表
                map.put("orderCode",orderCode);
                map.put("logisticAmount",MapUtils.getString(params,"logisticAmount"));
                map.put("addressInfo",MapUtils.getString(params,"addressInfo"));
                map.put("amount",MapUtils.getString(params,"amount"));
                map.put("remark",MapUtils.getString(params,"remark"));
                map.put("account",MapUtils.getString(params,"account"));
                map.put("type",MapUtils.getString(params,"type"));
                map.put("invoiceMode",MapUtils.getString(params,"invoiceMode"));
                map.put("invoiceType",MapUtils.getString(params,"invoiceType"));
                map.put("invoiceInfo",MapUtils.getString(params,"invoiceInfo"));

                if(StringUtils.isNotBlank(MapUtils.getString(params,"shopType")))

                    map.put("shopType",MapUtils.getString(params,"shopType"));

                    if(MapUtils.getString(params,"shopType").equals("1"))

                        map.put("status",15);

                else
                    map.put("status",1);



                map.put("sellStatus",1);
                map.put("hasComplaint",0);
                shopOrderMapper.saveShopOrder(map);
                List<Map<String,Object>> orderMap = shopOrderMapper.findShopCodeByCode(orderCode);
                //判断是否查询得到
                if(CollectionUtils.isNotEmpty(orderMap)){
                    List<Map<String,Object>> products = (List<Map<String,Object>>)map.get("seller");
                    if (CollectionUtils.isNotEmpty(products)) {
                        for (Map<String,Object> item : products) {
                            Map<String,Object> orderProduct = new HashMap<>();
                            orderProduct.put("orderCodeId",orderCode);//订单号

                            orderProduct.put("commodityId",MapUtils.getString(item,"commodityId"));//商品id
                            orderProduct.put("productName",MapUtils.getString(item,"productName"));//商品名称


                            String images = MapUtils.getString(item,"notarizationCertificate");
                            if(StringUtil.isNotEmpty(images)&&!images.equals("[]")) {
                                List imageList = (List) item.get("notarizationCertificate");
                                orderProduct.put("productPic",imageList.get(0));//商品图片
                            }else{
                                orderProduct.put("productPic","");//商品图片
                            }

                            orderProduct.put("amount",MapUtils.getString(item,"productPrice"));//商品价格
                            orderProduct.put("number",MapUtils.getIntValue(item,"num"));//商品数量
                            if(StringUtils.isNotBlank(MapUtils.getString(item,"freight"))){
                                orderProduct.put("logisticAmount",MapUtils.getString(item,"freight"));//商品运费
                            }else{
                                orderProduct.put("logisticAmount",0.00);//商品运费
                            }

                            orderProduct.put("productOutputUnit",MapUtils.getString(item,"productAvailabilityUnits"));//单位

                            orderProduct.put("sellerAccount",MapUtils.getString(map,"sellerAccount"));
                            //批量添加数据
                            shopOrderProductMapper.saveShopOrderProduct(orderProduct);



                            //减少库存
                            ClearShopCard(orderCode,MapUtils.getString(item,"commodityId"),MapUtils.getString(params,"account"),MapUtils.getInteger(item,"num"),MapUtils.getInteger(params,"type"));

                        }


                    }

                }
            }

            //记录信息

            AddOrderLog(orderCode,MapUtils.getString(params,"account"),OrderStatusType.SendFor);
        }






    }

    private void ClearShopCard(String orderCode,String commodityId, String account,Integer number,Integer type) {
        try {
            if (type != null && type.intValue() == 1) {
                Map<String, Object> map = new HashMap<>();
                map.put("code", orderCode);
                map.put("account", account);
                shopOrderMapper.ClearShopCard(map);//清理购物车
            }
            Query query = new Query();

            Criteria criteria =  Criteria.where("pushShopCommodityId").is(commodityId);

            //查询
            query.addCriteria(criteria);
            query.fields().exclude("_id");
            List<Map> sales = mongoTemplate.find(query, Map.class, "push_marketing_commodity_sales_info");
            Integer productAvailability = 0;
            if(CollectionUtils.isNotEmpty(sales)) {
                productAvailability =  MapUtils.getInteger(sales.get(0),"productAvailability");// 产品可售量
                productAvailability = productAvailability - number;
                //修改
                Query modifyQuery = new Query(Criteria.where("id").is(MapUtils.getString(sales.get(0),"id")));
                Update update = new Update();
                update.set("productAvailability", productAvailability);
                mongoTemplate.updateFirst(modifyQuery, update, Map.class, "push_marketing_commodity_sales_info");

                if(productAvailability<=0){
                    //商品下架，库存为0
                    modifyQuery = new Query(Criteria.where("pushShopCommodityId").is(commodityId));
                    update = new Update();
                    update.set("storageStatus", "2");
                    mongoTemplate.updateFirst(modifyQuery, update, Map.class, "push_basic_commodity_info");

                }
            }



        } catch (Exception ex) {
            logger.debug(ex);
        }
    }


    @Override
    public Map<String,Object> selectOneDetail(Map<String,Object> params) {
        Map<String,Object> entity = shopOrderMapper.findDetailByCode(params);



        if(StringUtils.isNotBlank(MapUtils.getString(entity,"addressInfo"))){
            entity.put("addressInfo",JSONArray.parse(MapUtils.getString(entity,"addressInfo")));

        }

        if(StringUtils.isNotBlank(MapUtils.getString(entity,"invoiceInfo"))){
            entity.put("invoiceInfo",JSONArray.parse(MapUtils.getString(entity,"invoiceInfo")));
        }

        Map<String,Object> map = new HashMap<>();
        map.put("code", MapUtils.getString(params,"orderCode"));

        entity.put("shopProducts",shopOrderProductMapper.selectExtendByOrderCode(map));

        /**
         * 根据商品id查询得到预售商品的价格
         */
        if(MapUtils.getString(entity,"shopType").equals("1")){
            List<Map<String,Object>> shopPList = (List<Map<String,Object>>)entity.get("shopProducts");
            if(CollectionUtils.isNotEmpty(shopPList)){
                for(Map<String, Object> shops : shopPList){
                    Query shopQuery = new Query();
                    Criteria criteria =  Criteria.where("pushShopCommodityId").is(MapUtils.getString(shops,"commodityId"));
                    //查询
                    shopQuery.addCriteria(criteria);
                    shopQuery.fields().exclude("_id");
                    //价格
                    List<Map> pricing = mongoTemplate.find(shopQuery, Map.class, "push_marketing_commodity_pricing_info");
                    if(CollectionUtils.isNotEmpty(pricing)) {
                        shops.put("orderPrice", MapUtils.getString(pricing.get(0),"orderPrice"));//预售价格

                    }
                }
            }


        }

        //根据竞拍编号查询保证金
        if(MapUtils.getString(entity,"shopType").equals("4")){
            entity.put("margin",shopOrderMapper.getMargin(map));//预售价格

        }
        return entity;
    }

    //支付
    @Override
    public void updateState(Map<String,Object> params) {

        shopOrderMapper.updateState(params);

        if(MapUtils.getString(params,"status").equals("3")){//支付
            //如果是竞价类型，需要更新shop_bidding_list表中的status状态为8
            String shopType = shopOrderMapper.getShopTypeByOrderCode(params);
            if ("4".equals(shopType)) {
                params.put("bidStatus", 8);
                shopOrderMapper.updateBidStatus(params);
            }

            AddOrderLog(MapUtils.getString(params,"orderCode"),MapUtils.getString(params,"account"),OrderStatusType.Pay);
        }else if(MapUtils.getString(params,"status").equals("16")){//已取消
            AddOrderLog(MapUtils.getString(params,"orderCode"),MapUtils.getString(params,"account"),OrderStatusType.ImmediatelyCancel);
        }else if(MapUtils.getString(params,"status").equals("11")){//卖家同意取消
            AddOrderLog(MapUtils.getString(params,"orderCode"),MapUtils.getString(params,"account"),OrderStatusType.CancelOver);

            //增加商品可售量
            List<Map<String,Object>> list  = shopOrderProductMapper.findByOrderCode(MapUtils.getString(params,"orderCode"));
            if(CollectionUtils.isNotEmpty(list)){
                for(Map<String,Object> map:list){
                    addShopCard(MapUtils.getString(map,"commodityId"), MapUtils.getInteger(map,"number"));
                }
            }


        }else if(MapUtils.getString(params,"status").equals("4")){//发货
            AddOrderLog(MapUtils.getString(params,"orderCode"),MapUtils.getString(params,"account"),OrderStatusType.SendOut);
        }else if(MapUtils.getString(params,"status").equals("5")){//收货
            AddOrderLog(MapUtils.getString(params,"orderCode"),MapUtils.getString(params,"account"),OrderStatusType.TakeOver);
        }
        else if(MapUtils.getString(params,"status").equals("14")){//卖家确认取消
            AddOrderLog(MapUtils.getString(params,"orderCode"),MapUtils.getString(params,"account"),OrderStatusType.TakeOver);
            //增加商品可售量
            List<Map<String,Object>> list  = shopOrderProductMapper.findByOrderCode(MapUtils.getString(params,"orderCode"));
            if(CollectionUtils.isNotEmpty(list)){
                for(Map<String,Object> map:list){
                    addShopCard(MapUtils.getString(map,"commodityId"), MapUtils.getInteger(map,"number"));
                }
            }
        }


    }

    private void addShopCard(String commodityId, Integer number) {
        try {

            Query query = new Query();

            Criteria criteria =  Criteria.where("pushShopCommodityId").is(commodityId);

            //查询
            query.addCriteria(criteria);
            query.fields().exclude("_id");
            List<Map> sales = mongoTemplate.find(query, Map.class, "push_marketing_commodity_sales_info");
            Integer productAvailability = 0;
            if(CollectionUtils.isNotEmpty(sales)) {
                productAvailability =  MapUtils.getInteger(sales.get(0),"productAvailability");// 产品可售量
                productAvailability = productAvailability + number;
                //修改
                Query modifyQuery = new Query(Criteria.where("id").is(MapUtils.getString(sales.get(0),"id")));
                Update update = new Update();
                update.set("productAvailability", productAvailability);
                mongoTemplate.updateFirst(modifyQuery, update, Map.class, "push_marketing_commodity_sales_info");

            }



        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }






    /*


    @Override
    public ShopOrderDetailDTO selectOne(Integer id) {
        ShopOrderDetailDTO entity = null;
        if (Integers.isNotEmpty(id)) {
            entity = shopOrderMapper.findDetailById(id);
        }
        return entity;
    }



    @Override
    public Integer update(ShopOrderDetailDTO dto, String account) {

        ShopOrder entity = BeanByRefMapper.copyByRefMapper(dto, ShopOrder.class);
        entity.setAccount(account);
        entity.setUpdateTime(new Date());
        Integer result = updateSelective(entity);
        if (result > 0) {
            List<ShopOrderProduct> products = dto.getShopProducts();
            if (products != null) {
                for (ShopOrderProduct item : products) {
                    shopOrderProductMapper.updateByPrimaryKeySelective(item);//批量添加数据
                }
            }
            shopOrderProductMapper.insertList(products);//批量添加数据
        }
        return Integers.isEmpty(result) ? 0 : result;
    }

    @Override
    public Integer updateState(String orderCode, OrderStatusType state, String account) {
        Map<String,Object> map=new HashMap<>();
        map.put("code",orderCode);
        map.put("state",state.GetCode());
        int result = shopOrderMapper.updateState(map);//批量添加数据
        AddOrderLog(orderCode, account, state);
        *//*
        if(state==OrderStatusType.SendOut) {
            AddOrderLog(orderCode, account, OrderStatusType.SendOut);
        }else  if(state==OrderStatusType.TakeOver){
            AddOrderLog(orderCode, account, OrderStatusType.TakeOver);
        }
        *//*
        if(result>0) {
            if (state == OrderStatusType.CancelOver || state == OrderStatusType.SellerCancel || state == OrderStatusType.RetreatOver) {
                //卖卖家取消及卖家同意退货，还原库存
                shopOrderMapper.ReduceProductVbep(orderCode);
            }
        }
        return Integers.isEmpty(result) ? 0 : result;
    }

    */

    /**
     * 添加操作日志
     *
     * @param orderId
     * @param account
     * @param type
     */
    protected void AddOrderLog(String orderId, String account, OrderStatusType type) {
        Map<String,Object> map = new HashMap<>();
        map.put("orderCodeId",orderId);
        map.put("account",account);
        map.put("type",type.GetCode());
        map.put("log",type.GetDesc());
        shopOrderLogMapper.insertOrderLog(map);
    }



    private String GetOrderCode(String account) {
        String id = shopOrderMapper.findUserId(account);
        if (id != null) {
            String str = id.substring(id.length() - 4);
            return ChargeOrderCode(str);
        } else {
            return null;
        }
    }

    private String ChargeOrderCode(String id) {

        String str = Utils.builderRandomString(16);// + id;//12位随机数
        //这里需要判断随机码是否已经存在
        int count = shopOrderMapper.findOrderCount(str);
        if (count > 0) {
            return ChargeOrderCode(id);
        } else {
            return str;
        }
    }
}
