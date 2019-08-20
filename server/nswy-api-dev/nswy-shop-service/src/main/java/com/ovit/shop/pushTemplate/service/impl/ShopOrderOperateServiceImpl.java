package com.ovit.shop.pushTemplate.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.github.xphsc.mutable.Integers;

import com.github.xphsc.util.StringUtil;
import com.ovit.shop.core.enums.NormalEnum.*;
import com.ovit.shop.pushTemplate.mapper.ShopOrderLogMapper;
import com.ovit.shop.pushTemplate.mapper.ShopOrderMapper;
import com.ovit.shop.pushTemplate.mapper.ShopOrderOperateMapper;
import com.ovit.shop.pushTemplate.mapper.ShopOrderProductMapper;
import com.ovit.shop.pushTemplate.service.ShopOrderOperateService;
import com.ovit.shop.util.ImageUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wangrongf
 */
@Service
public class ShopOrderOperateServiceImpl  implements ShopOrderOperateService {


    @Autowired
    private ShopOrderOperateMapper shopOrderOperateMapper;

    @Autowired
    private ShopOrderMapper shopOrderMapper;

    @Autowired
    private ShopOrderLogMapper shopOrderLogMapper;

    @Autowired
    private ShopOrderProductMapper shopOrderProductMapper;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public void add(Map<String,Object> params) {


        params.put("approveStatus",0);

        String picUrl = MapUtils.getString(params,"picUrl");
        if(StringUtil.isNotEmpty(picUrl)) {

            picUrl = picUrl.substring(1,picUrl.length()-1);

            params.put("picUrl", picUrl);
        }

        shopOrderOperateMapper.saveOrderOperate(params);

        Map<String,Object> orderMap = shopOrderOperateMapper.findByOrderCode(params);


        if(MapUtils.isNotEmpty(orderMap)){
            if(MapUtils.getInteger(orderMap,"fromAccount").equals(0)){
                //买，更新订单状态，更新日志信息
                if(MapUtils.getInteger(orderMap,"type")==1) {//取消
                    if(MapUtils.getString(params,"status").equals("17")){
                        UpdateOrderStatus(MapUtils.getString(orderMap,"orderCode"), MapUtils.getString(orderMap,"account"), OrderStatusType.NotPayCancel);
                        AddOrderLog(MapUtils.getString(orderMap,"orderCode"), MapUtils.getString(orderMap,"account"), OrderStatusType.NotPayCancel);
                    }else if(MapUtils.getString(params,"status").equals("11")){
                        UpdateOrderStatus(MapUtils.getString(orderMap,"orderCode"), MapUtils.getString(orderMap,"account"), OrderStatusType.CancelOver);
                        AddOrderLog(MapUtils.getString(orderMap,"orderCode"), MapUtils.getString(orderMap,"account"), OrderStatusType.CancelOver);
                    } else if(MapUtils.getString(params,"status").equals("10")){
                        UpdateOrderStatus(MapUtils.getString(orderMap,"orderCode"), MapUtils.getString(orderMap,"account"), OrderStatusType.BuyerCancel);
                        AddOrderLog(MapUtils.getString(orderMap,"orderCode"), MapUtils.getString(orderMap,"account"), OrderStatusType.BuyerCancel);
                    }
                }else{//退货
                    if(MapUtils.getString(params,"status").equals("13")){
                        UpdateOrderStatus(MapUtils.getString(orderMap,"orderCode"), MapUtils.getString(orderMap,"account"), OrderStatusType.BuyerRetreat);
                        AddOrderLog(MapUtils.getString(orderMap,"orderCode"), MapUtils.getString(orderMap,"account"), OrderStatusType.BuyerRetreat);

                    }
                    if(MapUtils.getString(params,"status").equals("14")){
                        UpdateOrderStatus(MapUtils.getString(orderMap,"orderCode"), MapUtils.getString(orderMap,"account"), OrderStatusType.RetreatOver);
                        AddOrderLog(MapUtils.getString(orderMap,"orderCode"), MapUtils.getString(orderMap,"account"), OrderStatusType.RetreatOver);

                    }
                    if(MapUtils.getString(params,"status").equals("18")){
                        UpdateOrderStatus(MapUtils.getString(orderMap,"orderCode"), MapUtils.getString(orderMap,"account"), OrderStatusType.RetreatCancel);
                        AddOrderLog(MapUtils.getString(orderMap,"orderCode"), MapUtils.getString(orderMap,"account"), OrderStatusType.RetreatCancel);

                    }

                }

                if(MapUtils.getString(params,"status").equals("16")||MapUtils.getString(params,"status").equals("17")||MapUtils.getString(params,"status").equals("14")){
                    //增加商品可售量
                    List<Map<String,Object>> list  = shopOrderProductMapper.findByOrderCode(MapUtils.getString(orderMap,"orderCode"));
                    if(CollectionUtils.isNotEmpty(list)){
                        for(Map<String,Object> map:list){
                            ClearShopCard(MapUtils.getString(map,"commodityId"), MapUtils.getInteger(map,"number"));
                        }
                    }
                }

            }else{
                //卖家取消
                if(MapUtils.getString(params,"status").equals("11")){
                    UpdateOrderStatus(MapUtils.getString(orderMap,"orderCode"), MapUtils.getString(orderMap,"account"), OrderStatusType.CancelOver);
                    AddOrderLog(MapUtils.getString(orderMap,"orderCode"), MapUtils.getString(orderMap,"account"), OrderStatusType.CancelOver);

                    //增加商品可售量
                    List<Map<String,Object>> list  = shopOrderProductMapper.findByOrderCode(MapUtils.getString(orderMap,"orderCode"));
                    if(CollectionUtils.isNotEmpty(list)){
                        for(Map<String,Object> map:list){
                            ClearShopCard(MapUtils.getString(map,"commodityId"), MapUtils.getInteger(map,"number"));
                        }
                    }
                } else if(MapUtils.getString(params,"status").equals("12")){
                    UpdateOrderStatus(MapUtils.getString(orderMap,"orderCode"), MapUtils.getString(orderMap,"account"), OrderStatusType.SellerCancel);
                    AddOrderLog(MapUtils.getString(orderMap,"orderCode"), MapUtils.getString(orderMap,"account"), OrderStatusType.SellerCancel);

                    //增加商品可售量
                    List<Map<String,Object>> list  = shopOrderProductMapper.findByOrderCode(MapUtils.getString(orderMap,"orderCode"));
                    if(CollectionUtils.isNotEmpty(list)){
                        for(Map<String,Object> map:list){
                            ClearShopCard(MapUtils.getString(map,"commodityId"), MapUtils.getInteger(map,"number"));
                        }
                    }
                }else if(MapUtils.getString(params,"status").equals("19")){
                    UpdateOrderStatus(MapUtils.getString(orderMap,"orderCode"), MapUtils.getString(orderMap,"account"), OrderStatusType.CancelCancel);
                    AddOrderLog(MapUtils.getString(orderMap,"orderCode"), MapUtils.getString(orderMap,"account"), OrderStatusType.CancelCancel);
                }


            }





        }

    }

    @Override
    public Map<String,Object> findById(Map<String, Object> map) {

        Map<String,Object> operate = shopOrderOperateMapper.findByOrderCode(map);

        String images = MapUtils.getString(operate,"picUrl");
        if(StringUtil.isNotBlank(images)){
            String[] image = images.split(",");
            operate.put("picUrl",image);
        }else{
            operate.put("picUrl",new String[]{});
        }

        return operate;
    }

    /*@Override
    public List<ShopOrderOperateDTO> list(Map<String, Object> map) {
        int type=(Integer)(map.get("type"));//是卖，还是买
        List<ShopOrderOperateDTO> list = shopOrderOperateMapper.findList(map);
        return list;
    }




    @Override
    public Integer update(ShopOrderOperate entity) {
        entity.setUpdateTime(new Date());
        Integer result=updateSelective(entity);
        return Integers.isEmpty(result)?0:result;
    }*/
    /**  添加操作日志
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

    private void UpdateOrderStatus(String orderId,String account,OrderStatusType type){
        Map<String,Object> map = new HashMap<>();
        map.put("orderCode",orderId);
        map.put("status",type.GetCode());
        shopOrderMapper.updateBuyerState(map);
    }

    private void ClearShopCard(String commodityId, Integer number) {
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
}
