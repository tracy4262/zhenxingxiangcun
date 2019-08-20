package com.ovit.shop.pushTemplate.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.xphsc.mutable.Integers;
import com.ovit.nswy.frame.base.BaseService;
import com.ovit.shop.pushTemplate.mapper.ShopCardMapper;
import com.ovit.shop.pushTemplate.service.ShopCardService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wangxy
 */
@Service
public class ShopCardServiceImpl implements ShopCardService {


    @Autowired
    private ShopCardMapper shopCardMapper;

    @Autowired
    private MongoTemplate mongoTemplate;


    @Override
    public Integer add(Map<String,Object> params) {

        //先查看是否以有该商品，有修改，没有添加
        Map<String,Object> map=shopCardMapper.findList(params);
        if(MapUtils.isEmpty(map)) {
            shopCardMapper.saveShopCard(params);
            Integer result=shopCardMapper.findCount(params);
            return Integers.isEmpty(result) ? 0 : result;
        }else{
            //不等于空的情况下，判断数量是否长出
            int allCount=MapUtils.getInteger(map,"num")+ MapUtils.getInteger(params,"num");
            //查询得到产品起售量
            int pCount=0;

            Query query = new Query();

            Criteria criteria =  Criteria.where("pushShopCommodityId").is(MapUtils.getString(params,"commodityId"));

            //查询
            query.addCriteria(criteria);
            query.fields().exclude("_id");

            List<Map> sales = mongoTemplate.find(query, Map.class, "push_marketing_commodity_sales_info");
            if(CollectionUtils.isNotEmpty(sales)) {
                if(StringUtils.isNotBlank(MapUtils.getString(sales.get(0),"maximumSingleShipment"))){

                    pCount = MapUtils.getInteger(sales.get(0),"maximumSingleShipment");
                }

            }

            //比较
            if(pCount >= allCount || pCount==0) {
                params.put("id",MapUtils.getString(map,"id"));
                params.put("num",allCount);
                //更新
                shopCardMapper.updateShopCard(params);
                Integer result = shopCardMapper.findCount(params);
                return Integers.isEmpty(result) ? 0 : result;
            }else{
                return -1;
            }
        }
    }

    @Override
    public List<Map<String,Object>> product(Map<String,Object> params) {
        List<Map<String,Object>> cardlist  = new ArrayList<>();

        //查询得到分组， 根据sellerAccount分组
        List<Map<String,Object>> sellerList  = (List<Map<String,Object>>)params.get("data");
        for(Map<String,Object> seller:sellerList){
            List<Map<String,Object>> list  = shopCardMapper.findListByAccount(seller);
            for(Map<String,Object> map : list){
                Map<String,Object> accountMap = new HashMap<>();
                accountMap.put("account",MapUtils.getString(map,"sellerAccount"));

                Map<String,Object> accountList  = shopCardMapper.findAvatar(accountMap);
                seller.put("sellerName",MapUtils.getString(accountList,"name"));
                seller.put("sellerAccount",MapUtils.getString(map,"sellerAccount"));

                Query query = new Query();
                Criteria criteria =  Criteria.where("pushShopCommodityId").is(MapUtils.getString(map,"commodityId"));
                //查询
                query.addCriteria(criteria);
                query.fields().exclude("_id");
                //商品名
                List<Map> product = mongoTemplate.find(query, Map.class, "push_basic_commodity_info");
                if(product!=null && product.size()>0){

                    map.put("productName",MapUtils.getString(product.get(0),"productName"));
                    //货架状态:0:未上架,1:我下架的,2:售完下架,3:上架到正在出售页面',
                    map.put("storageStatus",MapUtils.getString(product.get(0),"storageStatus"));

                    //0:未删除，1：删除
                    map.put("isDelete",MapUtils.getString(product.get(0),"isDelete"));


                }

                Query querys = new Query();

                Criteria criterias =  Criteria.where("id").is(MapUtils.getString(map,"commodityId"));

                //查询
                querys.addCriteria(criterias);
                querys.fields().exclude("_id");

                //图片
                List<Map> shopCommodity = mongoTemplate.find(querys, Map.class, "push_shop_commodity_info");
                if(shopCommodity!=null && shopCommodity.size()>0){
                    String notarizationCertificate  = JSON.toJSONString(shopCommodity.get(0).get("notarizationCertificate"));
                    if (String.valueOf(notarizationCertificate).contains("[")) {
                        map.put("notarizationCertificate", JSONObject.parse(notarizationCertificate));
                    }


                }

                //价格
                List<Map> pricing = mongoTemplate.find(query, Map.class, "push_marketing_commodity_pricing_info");
                if(CollectionUtils.isNotEmpty(pricing)) {
                    map.put("pricing", pricing.get(0));

                }

                List<Map> sales = mongoTemplate.find(query, Map.class, "push_marketing_commodity_sales_info");
                if(CollectionUtils.isNotEmpty(sales)) {

                    map.put("productStatus", MapUtils.getString(sales.get(0),"productStatus"));// 产品状态

                    map.put("productAvailability", MapUtils.getString(sales.get(0),"productAvailability"));// 产品可售量

                    map.put("productAvailabilityUnits", MapUtils.getString(sales.get(0),"productAvailabilityUnits"));// 产品可售量单位

                    map.put("productSalesVolume", MapUtils.getString(sales.get(0),"productSalesVolume"));//产品起售量

                    map.put("maximumSingleShipment", MapUtils.getString(sales.get(0),"maximumSingleShipment"));// 单次最大供货量



                }

                query = new Query().with(new Sort(Sort.Direction.ASC, "create_time"));;

                criteria =  Criteria.where("pushShopCommodityId").is(MapUtils.getString(map,"commodityId"));

                //查询
                query.addCriteria(criteria);
                query.fields().exclude("_id");

                List<Map> delivery = mongoTemplate.find(query, Map.class, "push_marketing_commodity_delivery_info");
                if(CollectionUtils.isNotEmpty(delivery)) {
                    map.put("freight", MapUtils.getString(delivery.get(0),"freight"));

                }

            }

            seller.put("seller",list);
        }

        String sellerAccount = "";

        int j=0;
        for(int i=0;i<sellerList.size();i++){
            Map<String,Object> cardMap = new HashMap<>();
            cardMap.put("sellerAccount",MapUtils.getString(sellerList.get(i),"sellerAccount"));
            cardMap.put("sellerName",MapUtils.getString(sellerList.get(i),"sellerName"));
            List<Map<String,Object>> cards = (List<Map<String,Object>>)sellerList.get(i).get("seller");

            if(!sellerAccount.equals(MapUtils.getString(sellerList.get(i),"sellerAccount"))){
                sellerAccount = MapUtils.getString(sellerList.get(i),"sellerAccount");
                cardMap.put("seller",sellerList.get(i).get("seller"));
                j=i;
            }else{
                cards.addAll((List<Map<String,Object>>)sellerList.get(j).get("seller"));
                cardMap.put("seller",cards);
                cardlist.remove(j);

            }
            cardlist.add(cardMap);
        }
        return cardlist;
    }

    @Override
    public List<Map<String,Object>> list(Map<String,Object> params) {

        //查询得到分组， 根据sellerAccount分组
       List<Map<String,Object>> sellerList  = shopCardMapper.findListByAccountGroup(params);
       for(Map<String,Object> seller:sellerList){

           Map<String,Object> accountMap = new HashMap<>();
           accountMap.put("account",MapUtils.getString(seller,"sellerAccount"));

           Map<String,Object> accountList  = shopCardMapper.findAvatar(accountMap);
           seller.put("sellerName",MapUtils.getString(accountList,"name"));
           //根据account 查询得到购物车


           List<Map<String,Object>> list  = shopCardMapper.findListByAccount(seller);
           for(Map<String,Object> map : list){



               Query query = new Query();

               Criteria criteria =  Criteria.where("pushShopCommodityId").is(MapUtils.getString(map,"commodityId"));

               //查询
               query.addCriteria(criteria);
               query.fields().exclude("_id");

               //商品名
               List<Map> product = mongoTemplate.find(query, Map.class, "push_basic_commodity_info");
               if(product!=null && product.size()>0){

                   map.put("productName",MapUtils.getString(product.get(0),"productName"));
                   //货架状态:0:未上架,1:我下架的,2:售完下架,3:上架到正在出售页面',
                   map.put("storageStatus",MapUtils.getString(product.get(0),"storageStatus"));

                   //0:未删除，1：删除
                   map.put("isDelete",MapUtils.getString(product.get(0),"isDelete"));



               }

               Query querys = new Query();

               Criteria criterias =  Criteria.where("id").is(MapUtils.getString(map,"commodityId"));

               //查询
               querys.addCriteria(criterias);
               querys.fields().exclude("_id");

               //图片
               List<Map> shopCommodity = mongoTemplate.find(querys, Map.class, "push_shop_commodity_info");
               if(shopCommodity!=null && shopCommodity.size()>0){
                   String notarizationCertificate  = JSON.toJSONString(shopCommodity.get(0).get("notarizationCertificate"));
                   if (String.valueOf(notarizationCertificate).contains("[")) {
                       map.put("notarizationCertificate", JSONObject.parse(notarizationCertificate));
                   }


               }

               //价格
               List<Map> pricing = mongoTemplate.find(query, Map.class, "push_marketing_commodity_pricing_info");
               if(CollectionUtils.isNotEmpty(pricing)) {
                   map.put("pricing", pricing.get(0));

               }

               List<Map> sales = mongoTemplate.find(query, Map.class, "push_marketing_commodity_sales_info");
               if(CollectionUtils.isNotEmpty(sales)) {

                   map.put("productStatus", MapUtils.getString(sales.get(0),"productStatus"));// 产品状态

                   map.put("productAvailability", MapUtils.getString(sales.get(0),"productAvailability"));// 产品可售量

                   map.put("productAvailabilityUnits", MapUtils.getString(sales.get(0),"productAvailabilityUnits"));// 产品可售量单位

                   map.put("productSalesVolume", MapUtils.getString(sales.get(0),"productSalesVolume"));//产品起售量

                   map.put("maximumSingleShipment", MapUtils.getString(sales.get(0),"maximumSingleShipment"));// 单次最大供货量



               }

               query = new Query().with(new Sort(Sort.Direction.ASC, "create_time"));;

              criteria =  Criteria.where("pushShopCommodityId").is(MapUtils.getString(map,"commodityId"));

               //查询
               query.addCriteria(criteria);
               query.fields().exclude("_id");

               List<Map> delivery = mongoTemplate.find(query, Map.class, "push_marketing_commodity_delivery_info");
               if(CollectionUtils.isNotEmpty(delivery)) {
                   map.put("freight", MapUtils.getString(delivery.get(0),"freight"));

               }

           }

           seller.put("seller",list);



       }





        return sellerList;
    }

    @Override
    public List<Map<String,Object>> purchase(Map<String,Object> params) {

        List<Map<String,Object>> sellerList = new ArrayList<>();

        Map<String,Object> card = new HashMap<>();


        Map<String,Object> accountMap = new HashMap<>();
        accountMap.put("account",MapUtils.getString(params,"sellerAccount"));

        Map<String,Object> accountList  = shopCardMapper.findAvatar(accountMap);
        params.put("sellerName",MapUtils.getString(accountList,"name"));
        //根据account 查询得到购物车

        List<Map<String,Object>> list = new ArrayList<>();

        Map<String,Object> map = new HashMap<>();
        Query query = new Query();

        Criteria criteria =  Criteria.where("pushShopCommodityId").is(MapUtils.getString(params,"commodityId"));

        //查询
        query.addCriteria(criteria);
        query.fields().exclude("_id");

        //商品名
        List<Map> product = mongoTemplate.find(query, Map.class, "push_basic_commodity_info");
        if(product!=null && product.size()>0){

            map.put("productName",MapUtils.getString(product.get(0),"productName"));
            //货架状态:0:未上架,1:我下架的,2:售完下架,3:上架到正在出售页面',
            map.put("storageStatus",MapUtils.getString(product.get(0),"storageStatus"));

            //0:未删除，1：删除
            map.put("isDelete",MapUtils.getString(product.get(0),"isDelete"));



        }

        Query querys = new Query();

        Criteria criterias =  Criteria.where("id").is(MapUtils.getString(params,"commodityId"));

        //查询
        querys.addCriteria(criterias);
        querys.fields().exclude("_id");

        //图片
        List<Map> shopCommodity = mongoTemplate.find(querys, Map.class, "push_shop_commodity_info");
        if(shopCommodity!=null && shopCommodity.size()>0){
            String notarizationCertificate  = JSON.toJSONString(shopCommodity.get(0).get("notarizationCertificate"));
            if (String.valueOf(notarizationCertificate).contains("[")) {
                map.put("notarizationCertificate", JSONObject.parse(notarizationCertificate));
            }


        }

        //价格
        List<Map> pricing = mongoTemplate.find(query, Map.class, "push_marketing_commodity_pricing_info");
        if(CollectionUtils.isNotEmpty(pricing)) {
            map.put("pricing", pricing.get(0));

        }

        List<Map> sales = mongoTemplate.find(query, Map.class, "push_marketing_commodity_sales_info");
        if(CollectionUtils.isNotEmpty(sales)) {

            map.put("productStatus", MapUtils.getString(sales.get(0),"productStatus"));// 产品状态

            map.put("productAvailability", MapUtils.getString(sales.get(0),"productAvailability"));// 产品可售量

            map.put("productAvailabilityUnits", MapUtils.getString(sales.get(0),"productAvailabilityUnits"));// 产品可售量单位

            map.put("productSalesVolume", MapUtils.getString(sales.get(0),"productSalesVolume"));//产品起售量

            map.put("maximumSingleShipment", MapUtils.getString(sales.get(0),"maximumSingleShipment"));// 单次最大供货量



        }

        query = new Query().with(new Sort(Sort.Direction.ASC, "create_time"));;

        criteria =  Criteria.where("pushShopCommodityId").is(MapUtils.getString(params,"commodityId"));

        //查询
        query.addCriteria(criteria);
        query.fields().exclude("_id");

        List<Map> delivery = mongoTemplate.find(query, Map.class, "push_marketing_commodity_delivery_info");
        if(CollectionUtils.isNotEmpty(delivery)) {
            map.put("freight", MapUtils.getString(delivery.get(0),"freight"));

        }

        map.put("num",MapUtils.getString(params,"num"));

        map.put("commodityId",MapUtils.getString(params,"commodityId"));


        list.add(map);

        card.put("account",MapUtils.getString(params,"account"));
        card.put("sellerAccount",MapUtils.getString(params,"sellerAccount"));
        card.put("sellerName",MapUtils.getString(params,"sellerName"));
        card.put("seller",list);


        sellerList.add(card);

        return sellerList;
    }
    @Override
    public void update(Map<String,Object> parmas) {
        shopCardMapper.updateShopCard(parmas);
    }

    @Override
    public void delete(Map<String,Object> params) {

        shopCardMapper.deleteShopCard(params);
    }

    @Override
    public void clear(Map<String,Object> params) {

        shopCardMapper.deleteShopCardByAccount(params);
    }

/*
    @Override
    public ShopProductDTO findProduct(Integer commodityTypeInfoId) {
        return  shopCardMapper.findProductById(commodityTypeInfoId);
    }



    */
}
