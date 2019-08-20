package com.ovit.shop.pushTemplate.service.impl;

import com.github.pagehelper.PageInfo;
import com.mongodb.WriteResult;
import com.ovit.shop.pushTemplate.mapper.ShopBiddingMapper;
import com.ovit.shop.pushTemplate.mapper.ShopCardMapper;
import com.ovit.shop.pushTemplate.service.PushShopCommodityService;
import com.ovit.shop.util.PageUtil;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class PushShopCommodityServiceImpl implements PushShopCommodityService {

    private Logger logger = LogManager.getLogger(this.getClass());

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    ShopBiddingMapper shopBiddingMapper;

    @Autowired
    ShopCardMapper shopCardMapper;

    /**
     * 获取定价商品列表信息
     * @param params
     * @return
     */
    @Override
    public List<Map<String, Object>> findPricing(Map<String, Object> params) {
        //拼装关联信息
        LookupOperation lookupOperation1 = LookupOperation.newLookup().
                from("push_marketing_commodity_pricing_info").//关联表名
                localField("id").//关联字段
                foreignField("pushShopCommodityId").//主表关联字段对应的次表字段
                as("table1");//查询结果集合名

        //使用$unwind展平 上面中的左连接的push_basic_commodity_info表的查询集合"table1"
        UnwindOperation unwindOperation1 = new UnwindOperation(Fields.field("$table1"));

        //拼装关联信息
        LookupOperation lookupOperation2 = LookupOperation.newLookup().
                from("push_marketing_commodity_sales_info").//关联表名
                localField("id").//关联字段
                foreignField("pushShopCommodityId").//主表关联字段对应的次表字段
                as("table2");//查询结果集合名

        UnwindOperation unwindOperation2 = new UnwindOperation(Fields.field("$table2"));


        //拼装关联信息
        LookupOperation lookupOperation4 = LookupOperation.newLookup().
                from("push_commodity_traceability_info").//关联表名
                localField("id").//关联字段
                foreignField("pushShopCommodityId").//主表关联字段对应的次表字段
                as("table4");//查询结果集合名

        UnwindOperation unwindOperation4 = new UnwindOperation(Fields.field("$table4"));

        //拼装关联信息
        LookupOperation lookupOperation5 = LookupOperation.newLookup().
                from("push_basic_commodity_info").//关联表名
                localField("id").//关联字段
                foreignField("pushShopCommodityId").//主表关联字段对应的次表字段
                as("table5");//查询结果集合名

        ;UnwindOperation unwindOperation5 = new UnwindOperation(Fields.field("$table5"));

        //拼装关联信息
        LookupOperation lookupOperation6 = LookupOperation.newLookup().
                from("push_basic_commodity_location_info").//关联表名
                localField("id").//关联字段
                foreignField("pushShopCommodityId").//主表关联字段对应的次表字段
                as("table6");//查询结果集合名

        UnwindOperation unwindOperation6 = new UnwindOperation(Fields.field("$table6"));

        Criteria criteria = Criteria.where("table1.salesWay").is("定价销售").and("table2.productStatus").ne("预定产品");
        if(StringUtils.isNotBlank(MapUtils.getString(params,"keyword"))){
            criteria.and("table5.productName").regex(".*"+MapUtils.getString(params,"keyword")+".*");
        }
        if(StringUtils.isNotBlank(MapUtils.getString(params,"productLocation"))){
            criteria.and("table6.productOrigin").regex("^.*"+MapUtils.getString(params,"productLocation")+".*$");
        }
        if(StringUtils.isNotBlank(MapUtils.getString(params,"industryType"))){
            criteria.and("industryType").is(MapUtils.getString(params,"industryType"));
        }
        if(StringUtils.isNotBlank(MapUtils.getString(params,"species"))){
            criteria.and("species").is(MapUtils.getString(params,"species"));
        }

        criteria.and("table5.storageStatus").is("3").and("table5.isDelete").is("0");

        MatchOperation matchOperation = Aggregation.match(criteria);

        //Aggregation管道操作（还可以加入$match、$project等其他管道操作，但是得注意先后顺序）
        TypedAggregation aggregation = Aggregation.newAggregation(Map.class, lookupOperation1, unwindOperation1,
                lookupOperation2, unwindOperation2, lookupOperation4, unwindOperation4,lookupOperation5, unwindOperation5,lookupOperation6, unwindOperation6, matchOperation);

        List<Map> results = mongoTemplate.aggregate(aggregation, "push_shop_commodity_info", Map.class).getMappedResults();
        //查出指定字段
        List<Map<String,Object>> pricingList = new ArrayList<>();
        for (Map<String,Object> map : results) {
            Map<String,Object> m = new HashMap<>();
            m.put("notarizationCertificate", map.get("notarizationCertificate"));
            m.put("commodityName",MapUtils.getString(MapUtils.getMap(map, "table5"), "productName"));
            m.put("id",MapUtils.getString(map, "id"));
            m.put("account",MapUtils.getString(map, "account"));
            m.put("createTime",MapUtils.getString(map, "create_time"));
            m.put("salesWay", MapUtils.getString(MapUtils.getMap(map, "table1"), "salesWay"));
            m.put("currentPrice", MapUtils.getString(MapUtils.getMap(map, "table1"), "currentPrice"));
            m.put("discountPrice", MapUtils.getString(MapUtils.getMap(map, "table1"), "discountPrice"));
            m.put("discountPeriodStr", MapUtils.getMap(map,"table1").get("discountPeriodStr"));
            m.put("productStatus",MapUtils.getMap(map,"table2").get("productStatus"));
            m.put("isRetrospect", MapUtils.getMap(map,"table4").get("isRetrospect"));
            m.put("storageStatus", MapUtils.getMap(map,"table5").get("storageStatus"));
            m.put("productLocation", MapUtils.getString(MapUtils.getMap(map, "table6"), "productOrigin"));

            params.put("commodityId",MapUtils.getString(map, "id"));
            Integer buyers = shopBiddingMapper.findBuyer(params);
            if (buyers != null) {
                m.put("buyers",buyers);
            }
            Map map1 = shopCardMapper.findAvatar(m);
            if (MapUtils.isNotEmpty(map1)) {
                m.put("name", map1.get("name"));
            }

            pricingList.add(m);
        }

        //排序
        Collections.sort(pricingList, new Comparator<Map<String,Object>>() {
            @Override
            public int compare(Map<String,Object> b1, Map<String,Object> b2) {
                return MapUtils.getString(b2,"createTime").compareTo(MapUtils.getString(b1,"createTime"));
            }
        });

        return pricingList;
    }

    /**
     * 获取竞价商品列表信息
     * @param params
     * @return
     */
    @Override
    public List<Map<String, Object>> findBidding(Map<String, Object> params) {
        //拼装关联信息
        LookupOperation lookupOperation1 = LookupOperation.newLookup().
                from("push_marketing_commodity_pricing_info").//关联表名
                localField("id").//关联字段
                foreignField("pushShopCommodityId").//主表关联字段对应的次表字段
                as("table1");//查询结果集合名

        //使用$unwind展平 上面中的左连接的push_basic_commodity_info表的查询集合"table1"
        UnwindOperation unwindOperation1 = new UnwindOperation(Fields.field("$table1"));

        //拼装关联信息
        LookupOperation lookupOperation2 = LookupOperation.newLookup().
                from("push_marketing_commodity_sales_info").//关联表名
                localField("id").//关联字段
                foreignField("pushShopCommodityId").//主表关联字段对应的次表字段
                as("table2");//查询结果集合名

        UnwindOperation unwindOperation2 = new UnwindOperation(Fields.field("$table2"));


        //拼装关联信息
        LookupOperation lookupOperation4 = LookupOperation.newLookup().
                from("push_commodity_traceability_info").//关联表名
                localField("id").//关联字段
                foreignField("pushShopCommodityId").//主表关联字段对应的次表字段
                as("table4");//查询结果集合名

        UnwindOperation unwindOperation4 = new UnwindOperation(Fields.field("$table4"));

        //拼装关联信息
        LookupOperation lookupOperation5 = LookupOperation.newLookup().
                from("push_basic_commodity_info").//关联表名
                localField("id").//关联字段
                foreignField("pushShopCommodityId").//主表关联字段对应的次表字段
                as("table5");//查询结果集合名

        ;UnwindOperation unwindOperation5 = new UnwindOperation(Fields.field("$table5"));

        //拼装关联信息
        LookupOperation lookupOperation6 = LookupOperation.newLookup().
                from("push_basic_commodity_location_info").//关联表名
                localField("id").//关联字段
                foreignField("pushShopCommodityId").//主表关联字段对应的次表字段
                as("table6");//查询结果集合名

        UnwindOperation unwindOperation6 = new UnwindOperation(Fields.field("$table6"));

        Criteria criteria = Criteria.where("table1.salesWay").is("竞价销售");
        if(StringUtils.isNotBlank(MapUtils.getString(params,"keyword"))){
            criteria.and("table5.productName").regex(".*"+MapUtils.getString(params,"keyword")+".*");
        }
        if(StringUtils.isNotBlank(MapUtils.getString(params,"productLocation"))){
            criteria.and("table6.productOrigin").regex("^.*"+MapUtils.getString(params,"productLocation")+".*$");}
        if(StringUtils.isNotBlank(MapUtils.getString(params,"industryType"))){
            criteria.and("industryType").is(MapUtils.getString(params,"industryType"));
        }
        if(StringUtils.isNotBlank(MapUtils.getString(params,"species"))){
            criteria.and("species").is(MapUtils.getString(params,"species"));
        }
        criteria.and("table5.storageStatus").is("3").and("table5.isDelete").is("0");
        MatchOperation matchOperation = Aggregation.match(criteria);

        //Aggregation管道操作（还可以加入$match、$project等其他管道操作，但是得注意先后顺序）
        TypedAggregation aggregation = Aggregation.newAggregation(Map.class, lookupOperation1, unwindOperation1,
                lookupOperation2, unwindOperation2, lookupOperation4, unwindOperation4,lookupOperation5, unwindOperation5,lookupOperation6, unwindOperation6, matchOperation);List<Map> results = mongoTemplate.aggregate(aggregation, "push_shop_commodity_info", Map.class).getMappedResults();
        //查出指定字段
        List<Map<String,Object>> pricingList = new ArrayList<>();
        for (Map<String,Object> map : results) {
            Map<String,Object> m = new HashMap<>();
            m.put("notarizationCertificate", map.get("notarizationCertificate"));
            m.put("commodityName",MapUtils.getString(MapUtils.getMap(map, "table5"), "productName"));
            m.put("id",MapUtils.getString(map, "id"));
            m.put("account",MapUtils.getString(map, "account"));
            m.put("createTime",MapUtils.getString(map, "create_time"));
            m.put("salesWay", MapUtils.getString(MapUtils.getMap(map, "table1"), "salesWay"));
            m.put("startPrice", MapUtils.getString(MapUtils.getMap(map, "table1"), "startPrice"));
            m.put("biddingStartTimeStr", MapUtils.getMap(map,"table1").get("biddingStartTimeStr"));
            m.put("biddingEndTimeStr", MapUtils.getMap(map,"table1").get("biddingEndTimeStr"));
            m.put("isRetrospect", MapUtils.getMap(map,"table4").get("isRetrospect"));
            m.put("storageStatus", MapUtils.getMap(map,"table5").get("storageStatus"));
            m.put("productLocation", MapUtils.getString(MapUtils.getMap(map, "table6"), "productOrigin"));

            params.put("commodityId",MapUtils.getString(map, "id"));
            Integer buyers = shopBiddingMapper.findBuyer(params);
            if (buyers != null) {
                m.put("buyers",buyers);
            }
            Map map1 = shopCardMapper.findAvatar(m);
            if (MapUtils.isNotEmpty(map1)) {
                m.put("name", map1.get("name"));
            }

            Integer participantCount = shopBiddingMapper.participantCount(params);
            if (participantCount != null) {
                m.put("participantCount", participantCount);
            }
            pricingList.add(m);
        }

        //排序
        Collections.sort(pricingList, new Comparator<Map<String,Object>>() {
            @Override
            public int compare(Map<String,Object> b1, Map<String,Object> b2) {
                return MapUtils.getString(b2,"createTime").compareTo(MapUtils.getString(b1,"createTime"));
            }
        });

        return pricingList;
    }

    /**
     * 获取团购商品列表信息
     * @param params
     * @return
     */
    @Override
    public List<Map<String, Object>> findGroupBuying(Map<String, Object> params) {
        //拼装关联信息
        LookupOperation lookupOperation1 = LookupOperation.newLookup().
                from("push_marketing_commodity_pricing_info").//关联表名
                localField("id").//关联字段
                foreignField("pushShopCommodityId").//主表关联字段对应的次表字段
                as("table1");//查询结果集合名

        //使用$unwind展平 上面中的左连接的push_basic_commodity_info表的查询集合"table1"
        UnwindOperation unwindOperation1 = new UnwindOperation(Fields.field("$table1"));

        //拼装关联信息
        LookupOperation lookupOperation2 = LookupOperation.newLookup().
                from("push_marketing_commodity_sales_info").//关联表名
                localField("id").//关联字段
                foreignField("pushShopCommodityId").//主表关联字段对应的次表字段
                as("table2");//查询结果集合名

        UnwindOperation unwindOperation2 = new UnwindOperation(Fields.field("$table2"));

        //拼装关联信息
        LookupOperation lookupOperation4 = LookupOperation.newLookup().
                from("push_commodity_traceability_info").//关联表名
                localField("id").//关联字段
                foreignField("pushShopCommodityId").//主表关联字段对应的次表字段
                as("table4");//查询结果集合名

        UnwindOperation unwindOperation4 = new UnwindOperation(Fields.field("$table4"));

        //拼装关联信息
        LookupOperation lookupOperation5 = LookupOperation.newLookup().
                from("push_basic_commodity_info").//关联表名
                localField("id").//关联字段
                foreignField("pushShopCommodityId").//主表关联字段对应的次表字段
                as("table5");//查询结果集合名

        ;UnwindOperation unwindOperation5 = new UnwindOperation(Fields.field("$table5"));

        //拼装关联信息
        LookupOperation lookupOperation6 = LookupOperation.newLookup().
                from("push_basic_commodity_location_info").//关联表名
                localField("id").//关联字段
                foreignField("pushShopCommodityId").//主表关联字段对应的次表字段
                as("table6");//查询结果集合名

        UnwindOperation unwindOperation6 = new UnwindOperation(Fields.field("$table6"));

        Criteria criteria = Criteria.where("table1.salesWay").is("团购销售");
        if(StringUtils.isNotBlank(MapUtils.getString(params,"keyword"))){
            criteria.and("table5.productName").regex(".*"+MapUtils.getString(params,"keyword")+".*");
        }
        if(StringUtils.isNotBlank(MapUtils.getString(params,"productLocation"))){
            criteria.and("table6.productOrigin").regex("^.*"+MapUtils.getString(params,"productLocation")+".*$");}
        if(StringUtils.isNotBlank(MapUtils.getString(params,"industryType"))){
            criteria.and("industryType").is(MapUtils.getString(params,"industryType"));
        }
        if(StringUtils.isNotBlank(MapUtils.getString(params,"species"))){
            criteria.and("species").is(MapUtils.getString(params,"species"));
        }
        criteria.and("table5.storageStatus").is("3").and("table5.isDelete").is("0");
        MatchOperation matchOperation = Aggregation.match(criteria);

        //Aggregation管道操作（还可以加入$match、$project等其他管道操作，但是得注意先后顺序）
        TypedAggregation aggregation = Aggregation.newAggregation(Map.class, lookupOperation1, unwindOperation1,
                lookupOperation2, unwindOperation2, lookupOperation4, unwindOperation4,lookupOperation5, unwindOperation5,lookupOperation6, unwindOperation6, matchOperation);List<Map> results = mongoTemplate.aggregate(aggregation, "push_shop_commodity_info", Map.class).getMappedResults();
        //查出指定字段
        List<Map<String,Object>> pricingList = new ArrayList<>();
        for (Map<String,Object> map : results) {
            Map<String,Object> m = new HashMap<>();
            m.put("notarizationCertificate", map.get("notarizationCertificate"));
            m.put("commodityName",MapUtils.getString(MapUtils.getMap(map, "table5"), "productName"));
            m.put("id",MapUtils.getString(map, "id"));
            m.put("account",MapUtils.getString(map, "account"));
            m.put("createTime",MapUtils.getString(map, "create_time"));
            m.put("salesWay", MapUtils.getString(MapUtils.getMap(map, "table1"), "salesWay"));
            m.put("groupBuyingPrice", MapUtils.getString(MapUtils.getMap(map, "table1"), "groupBuyingPrice"));
            m.put("originalPrice", MapUtils.getMap(map,"table1").get("originalPrice"));
            m.put("groupBuyingStartTimeStr", MapUtils.getMap(map,"table1").get("groupBuyingStartTimeStr"));
            m.put("groupBuyingEndTimeStr", MapUtils.getMap(map,"table1").get("groupBuyingEndTimeStr"));
            m.put("isRetrospect", MapUtils.getMap(map,"table4").get("isRetrospect"));
            m.put("storageStatus", MapUtils.getMap(map,"table5").get("storageStatus"));

            m.put("productLocation", MapUtils.getString(MapUtils.getMap(map, "table6"), "productOrigin"));

            params.put("commodityId",MapUtils.getString(map, "id"));
            Integer buyers = shopBiddingMapper.findBuyer(params);
            if (buyers != null) {
                m.put("buyers",buyers);
            }
            Map map1 = shopCardMapper.findAvatar(m);
            if (MapUtils.isNotEmpty(map1)) {
                m.put("name", map1.get("name"));
            }

            pricingList.add(m);
        }

        //排序
        Collections.sort(pricingList, new Comparator<Map<String,Object>>() {
            @Override
            public int compare(Map<String,Object> b1, Map<String,Object> b2) {
                return MapUtils.getString(b2,"createTime").compareTo(MapUtils.getString(b1,"createTime"));
            }
        });

        return pricingList;
    }

    /**
     * 获取预售商品列表信息
     * @param params
     * @return
     */
    @Override
    public List<Map<String, Object>> findPresale(Map<String, Object> params) {
        //拼装关联信息
        LookupOperation lookupOperation1 = LookupOperation.newLookup().
                from("push_marketing_commodity_pricing_info").//关联表名
                localField("id").//关联字段
                foreignField("pushShopCommodityId").//主表关联字段对应的次表字段
                as("table1");//查询结果集合名

        //使用$unwind展平 上面中的左连接的push_basic_commodity_info表的查询集合"table1"
        UnwindOperation unwindOperation1 = new UnwindOperation(Fields.field("$table1"));

        //拼装关联信息
        LookupOperation lookupOperation2 = LookupOperation.newLookup().
                from("push_marketing_commodity_sales_info").//关联表名
                localField("id").//关联字段
                foreignField("pushShopCommodityId").//主表关联字段对应的次表字段
                as("table2");//查询结果集合名

        UnwindOperation unwindOperation2 = new UnwindOperation(Fields.field("$table2"));


        //拼装关联信息
        LookupOperation lookupOperation4 = LookupOperation.newLookup().
                from("push_commodity_traceability_info").//关联表名
                localField("id").//关联字段
                foreignField("pushShopCommodityId").//主表关联字段对应的次表字段
                as("table4");//查询结果集合名

        UnwindOperation unwindOperation4 = new UnwindOperation(Fields.field("$table4"));

        //拼装关联信息
        LookupOperation lookupOperation5 = LookupOperation.newLookup().
                from("push_basic_commodity_info").//关联表名
                localField("id").//关联字段
                foreignField("pushShopCommodityId").//主表关联字段对应的次表字段
                as("table5");//查询结果集合名

        ;UnwindOperation unwindOperation5 = new UnwindOperation(Fields.field("$table5"));

        //拼装关联信息
        LookupOperation lookupOperation6 = LookupOperation.newLookup().
                from("push_basic_commodity_location_info").//关联表名
                localField("id").//关联字段
                foreignField("pushShopCommodityId").//主表关联字段对应的次表字段
                as("table6");//查询结果集合名

        UnwindOperation unwindOperation6 = new UnwindOperation(Fields.field("$table6"));

        Criteria criteria = Criteria.where("table2.productStatus").is("预定产品");
        if(StringUtils.isNotBlank(MapUtils.getString(params,"keyword"))){
            criteria.and("table5.productName").regex(".*"+MapUtils.getString(params,"keyword")+".*");
        }
        if(StringUtils.isNotBlank(MapUtils.getString(params,"productLocation"))){
            criteria.and("table6.productOrigin").regex("^.*"+MapUtils.getString(params,"productLocation")+".*$");
        }
        if(StringUtils.isNotBlank(MapUtils.getString(params,"industryType"))){
            criteria.and("industryType").is(MapUtils.getString(params,"industryType"));
        }
        if(StringUtils.isNotBlank(MapUtils.getString(params,"species"))){
            criteria.and("species").is(MapUtils.getString(params,"species"));
        }
        criteria.and("table5.storageStatus").is("3").and("table5.isDelete").is("0");
        MatchOperation matchOperation = Aggregation.match(criteria);

        //Aggregation管道操作（还可以加入$match、$project等其他管道操作，但是得注意先后顺序）
        TypedAggregation aggregation = Aggregation.newAggregation(Map.class, lookupOperation1, unwindOperation1,
                lookupOperation2, unwindOperation2,lookupOperation4, unwindOperation4,lookupOperation5, unwindOperation5,lookupOperation6, unwindOperation6, matchOperation);List<Map> results = mongoTemplate.aggregate(aggregation, "push_shop_commodity_info", Map.class).getMappedResults();
        //查出指定字段
        List<Map<String,Object>> pricingList = new ArrayList<>();
        for (Map<String,Object> map : results) {
            Map<String,Object> m = new HashMap<>();
            m.put("notarizationCertificate", map.get("notarizationCertificate"));
            m.put("commodityName",MapUtils.getString(MapUtils.getMap(map, "table5"), "productName"));
            m.put("id",MapUtils.getString(map, "id"));
            m.put("account",MapUtils.getString(map, "account"));
            m.put("createTime",MapUtils.getString(map, "create_time"));
            m.put("salesWay", "预售");
            m.put("orderPrice", MapUtils.getString(MapUtils.getMap(map, "table1"), "orderPrice"));
            m.put("depositAmount", MapUtils.getString(MapUtils.getMap(map, "table1"), "depositAmount"));
            m.put("productStatus",MapUtils.getMap(map,"table2").get("productStatus"));
            m.put("isRetrospect", MapUtils.getMap(map,"table4").get("isRetrospect"));
            m.put("storageStatus", MapUtils.getMap(map,"table5").get("storageStatus"));

            m.put("productLocation", MapUtils.getString(MapUtils.getMap(map, "table6"), "productOrigin"));

            params.put("commodityId",MapUtils.getString(map, "id"));
            Integer buyers = shopBiddingMapper.findBuyer(params);
            if (buyers != null) {
                m.put("buyers",buyers);
            }
            Map map1 = shopCardMapper.findAvatar(m);
            if (MapUtils.isNotEmpty(map1)) {
                m.put("name", map1.get("name"));
            }

            pricingList.add(m);
        }

        //排序
        Collections.sort(pricingList, new Comparator<Map<String,Object>>() {
            @Override
            public int compare(Map<String,Object> b1, Map<String,Object> b2) {
                return MapUtils.getString(b2,"createTime").compareTo(MapUtils.getString(b1,"createTime"));
            }
        });

        return pricingList;
    }

    /**
     * 获取面议商品列表信息
     * @param params
     * @return
     */
    @Override
    public List<Map<String, Object>> findFace(Map<String, Object> params) {
        //拼装关联信息
        LookupOperation lookupOperation1 = LookupOperation.newLookup().
                from("push_marketing_commodity_pricing_info").//关联表名
                localField("id").//关联字段
                foreignField("pushShopCommodityId").//主表关联字段对应的次表字段
                as("table1");//查询结果集合名

        //使用$unwind展平 上面中的左连接的push_basic_commodity_info表的查询集合"table1"
        UnwindOperation unwindOperation1 = new UnwindOperation(Fields.field("$table1"));

        //拼装关联信息
        LookupOperation lookupOperation2 = LookupOperation.newLookup().
                from("push_marketing_commodity_sales_info").//关联表名
                localField("id").//关联字段
                foreignField("pushShopCommodityId").//主表关联字段对应的次表字段
                as("table2");//查询结果集合名

        UnwindOperation unwindOperation2 = new UnwindOperation(Fields.field("$table2"));


        //拼装关联信息
        LookupOperation lookupOperation4 = LookupOperation.newLookup().
                from("push_commodity_traceability_info").//关联表名
                localField("id").//关联字段
                foreignField("pushShopCommodityId").//主表关联字段对应的次表字段
                as("table4");//查询结果集合名

        UnwindOperation unwindOperation4 = new UnwindOperation(Fields.field("$table4"));

        //拼装关联信息
        LookupOperation lookupOperation5 = LookupOperation.newLookup().
                from("push_basic_commodity_info").//关联表名
                localField("id").//关联字段
                foreignField("pushShopCommodityId").//主表关联字段对应的次表字段
                as("table5");//查询结果集合名

        ;UnwindOperation unwindOperation5 = new UnwindOperation(Fields.field("$table5"));

        //拼装关联信息
        LookupOperation lookupOperation6 = LookupOperation.newLookup().
                from("push_basic_commodity_location_info").//关联表名
                localField("id").//关联字段
                foreignField("pushShopCommodityId").//主表关联字段对应的次表字段
                as("table6");//查询结果集合名

        UnwindOperation unwindOperation6 = new UnwindOperation(Fields.field("$table6"));

        Criteria criteria = Criteria.where("table1.salesWay").is("面议");
        if(StringUtils.isNotBlank(MapUtils.getString(params,"keyword"))){
            criteria.and("table5.productName").regex(".*"+MapUtils.getString(params,"keyword")+".*");
        }
        if(StringUtils.isNotBlank(MapUtils.getString(params,"productLocation"))){
            criteria.and("table6.productOrigin").regex("^.*"+MapUtils.getString(params,"productLocation")+".*$");
        }
        if(StringUtils.isNotBlank(MapUtils.getString(params,"industryType"))){
            criteria.and("industryType").is(MapUtils.getString(params,"industryType"));
        }
        if(StringUtils.isNotBlank(MapUtils.getString(params,"species"))){
            criteria.and("species").is(MapUtils.getString(params,"species"));
        }
        criteria.and("table5.storageStatus").is("3").and("table5.isDelete").is("0");
        MatchOperation matchOperation = Aggregation.match(criteria);

        //Aggregation管道操作（还可以加入$match、$project等其他管道操作，但是得注意先后顺序）
        TypedAggregation aggregation = Aggregation.newAggregation(Map.class, lookupOperation1, unwindOperation1,
                lookupOperation2, unwindOperation2, lookupOperation4, unwindOperation4,lookupOperation5, unwindOperation5,lookupOperation6, unwindOperation6, matchOperation);
        List<Map> results = mongoTemplate.aggregate(aggregation, "push_shop_commodity_info", Map.class).getMappedResults();
        //查出指定字段
        List<Map<String,Object>> pricingList = new ArrayList<>();
        for (Map<String,Object> map : results) {
            Map<String,Object> m = new HashMap<>();
            m.put("notarizationCertificate", map.get("notarizationCertificate"));
            m.put("commodityName",MapUtils.getString(MapUtils.getMap(map, "table5"), "productName"));
            m.put("id",MapUtils.getString(map, "id"));
            m.put("account",MapUtils.getString(map, "account"));
            m.put("createTime",MapUtils.getString(map, "create_time"));
            m.put("salesWay", MapUtils.getString(MapUtils.getMap(map, "table1"), "salesWay"));
            m.put("isRetrospect", MapUtils.getMap(map,"table4").get("isRetrospect"));
            m.put("storageStatus", MapUtils.getMap(map,"table5").get("storageStatus"));

            m.put("productLocation", MapUtils.getString(MapUtils.getMap(map, "table6"), "productOrigin"));

            params.put("commodityId",MapUtils.getString(map, "id"));
            Integer buyers = shopBiddingMapper.findBuyer(params);
            if (buyers != null) {
                m.put("buyers",buyers);
            }
            Map map1 = shopCardMapper.findAvatar(m);
            if (MapUtils.isNotEmpty(map1)) {
                m.put("name", map1.get("name"));
            }

            pricingList.add(m);
        }

        //排序
        Collections.sort(pricingList, new Comparator<Map<String,Object>>() {
            @Override
            public int compare(Map<String,Object> b1, Map<String,Object> b2) {
                return MapUtils.getString(b2,"createTime").compareTo(MapUtils.getString(b1,"createTime"));
            }
        });

        return pricingList;
    }

    /**
     * 获取可追溯商品列表信息
     * @param params
     * @return
     */
    @Override
    public List<Map<String, Object>> findRetrospect(Map<String, Object> params) {
        //拼装关联信息
        LookupOperation lookupOperation1 = LookupOperation.newLookup().
                from("push_marketing_commodity_pricing_info").//关联表名
                localField("id").//关联字段
                foreignField("pushShopCommodityId").//主表关联字段对应的次表字段
                as("table1");//查询结果集合名

        //使用$unwind展平 上面中的左连接的push_basic_commodity_info表的查询集合"table1"
        UnwindOperation unwindOperation1 = new UnwindOperation(Fields.field("$table1"));

        //拼装关联信息
        LookupOperation lookupOperation2 = LookupOperation.newLookup().
                from("push_marketing_commodity_sales_info").//关联表名
                localField("id").//关联字段
                foreignField("pushShopCommodityId").//主表关联字段对应的次表字段
                as("table2");//查询结果集合名

        UnwindOperation unwindOperation2 = new UnwindOperation(Fields.field("$table2"));


        //拼装关联信息
        LookupOperation lookupOperation4 = LookupOperation.newLookup().
                from("push_commodity_traceability_info").//关联表名
                localField("id").//关联字段
                foreignField("pushShopCommodityId").//主表关联字段对应的次表字段
                as("table4");//查询结果集合名

        UnwindOperation unwindOperation4 = new UnwindOperation(Fields.field("$table4"));

        //拼装关联信息
        LookupOperation lookupOperation5 = LookupOperation.newLookup().
                from("push_basic_commodity_info").//关联表名
                localField("id").//关联字段
                foreignField("pushShopCommodityId").//主表关联字段对应的次表字段
                as("table5");//查询结果集合名

        ;UnwindOperation unwindOperation5 = new UnwindOperation(Fields.field("$table5"));

        //拼装关联信息
        LookupOperation lookupOperation6 = LookupOperation.newLookup().
                from("push_basic_commodity_location_info").//关联表名
                localField("id").//关联字段
                foreignField("pushShopCommodityId").//主表关联字段对应的次表字段
                as("table6");//查询结果集合名

        UnwindOperation unwindOperation6 = new UnwindOperation(Fields.field("$table6"));

        Criteria criteria = Criteria.where("table4.isRetrospect").is("是");

        if(StringUtils.isNotBlank(MapUtils.getString(params,"keyword"))){
            criteria.and("table5.productName").regex(".*"+MapUtils.getString(params,"keyword")+".*");
        }
        criteria.and("table5.storageStatus").is("3").and("table5.isDelete").is("0");

        MatchOperation matchOperation = Aggregation.match(criteria);

        //Aggregation管道操作（还可以加入$match、$project等其他管道操作，但是得注意先后顺序）
        TypedAggregation aggregation = Aggregation.newAggregation(Map.class, lookupOperation1, unwindOperation1,
                lookupOperation2, unwindOperation2, lookupOperation4, unwindOperation4,lookupOperation5, unwindOperation5,lookupOperation6, unwindOperation6, matchOperation);
        List<Map> results = mongoTemplate.aggregate(aggregation, "push_shop_commodity_info", Map.class).getMappedResults();
        //查出指定字段
        List<Map<String,Object>> pricingList = new ArrayList<>();
        for (Map<String,Object> map : results) {
            Map<String,Object> m = new HashMap<>();
            m.put("notarizationCertificate", map.get("notarizationCertificate"));
            m.put("commodityName",MapUtils.getString(MapUtils.getMap(map, "table5"), "productName"));
            m.put("id",MapUtils.getString(map, "id"));
            m.put("account",MapUtils.getString(map, "account"));
            m.put("createTime",MapUtils.getString(map, "create_time"));
            m.put("currentPrice", MapUtils.getString(MapUtils.getMap(map, "table1"), "currentPrice"));
            m.put("discountPrice", MapUtils.getString(MapUtils.getMap(map, "table1"), "discountPrice"));
            m.put("discountPeriodStr", MapUtils.getMap(map,"table1").get("discountPeriodStr"));
            m.put("startPrice", MapUtils.getString(MapUtils.getMap(map, "table1"), "startPrice"));
            m.put("biddingStartTimeStr", MapUtils.getMap(map,"table1").get("biddingStartTimeStr"));
            m.put("biddingEndTimeStr", MapUtils.getMap(map,"table1").get("biddingEndTimeStr"));
            m.put("groupBuyingStartTimeStr", MapUtils.getMap(map,"table1").get("groupBuyingStartTimeStr"));
            m.put("groupBuyingPrice", MapUtils.getString(MapUtils.getMap(map, "table1"), "groupBuyingPrice"));
            m.put("originalPrice", MapUtils.getMap(map,"table1").get("originalPrice"));
            m.put("groupBuyingEndTimeStr", MapUtils.getMap(map,"table1").get("groupBuyingEndTimeStr"));
            m.put("orderPrice", MapUtils.getMap(map,"table1").get("orderPrice"));
            m.put("depositAmount", MapUtils.getMap(map,"table1").get("depositAmount"));
            m.put("isRetrospect", MapUtils.getMap(map,"table4").get("isRetrospect"));
            m.put("storageStatus", MapUtils.getMap(map,"table5").get("storageStatus"));
            String salesWay = MapUtils.getString(MapUtils.getMap(map, "table1"), "salesWay");
            String productStatus = MapUtils.getString(MapUtils.getMap(map, "table2"), "productStatus");
            if (!"现货产品".equals(productStatus)) {
                salesWay = "预售";
            }
            m.put("salesWay", salesWay);

            m.put("productLocation", MapUtils.getString(MapUtils.getMap(map, "table6"), "productOrigin"));

            params.put("commodityId",MapUtils.getString(map, "id"));
            Integer buyers = shopBiddingMapper.findBuyer(params);
            if (buyers != null) {
                m.put("buyers",buyers);
            }
            Map map1 = shopCardMapper.findAvatar(m);
            if (MapUtils.isNotEmpty(map1)) {
                m.put("name", map1.get("name"));
            }

            Integer participantCount = shopBiddingMapper.participantCount(params);
            if (participantCount != null) {
                m.put("participantCount", participantCount);
            }

            pricingList.add(m);
        }

        //排序
        Collections.sort(pricingList, new Comparator<Map<String,Object>>() {
            @Override
            public int compare(Map<String,Object> b1, Map<String,Object> b2) {
                return MapUtils.getString(b2,"createTime").compareTo(MapUtils.getString(b1,"createTime"));
            }
        });

        return pricingList;
    }

    /**
     * 获取全部商品列表信息
     * @param params
     * @return
     */
    @Override
    public List<Map<String, Object>> findProduct(Map<String, Object> params) {
        //拼装关联信息
        LookupOperation lookupOperation1 = LookupOperation.newLookup().
                from("push_marketing_commodity_pricing_info").//关联表名
                localField("id").//关联字段
                foreignField("pushShopCommodityId").//主表关联字段对应的次表字段
                as("table1");//查询结果集合名

        //使用$unwind展平 上面中的左连接的push_basic_commodity_info表的查询集合"table1"
        UnwindOperation unwindOperation1 = new UnwindOperation(Fields.field("$table1"));

        //拼装关联信息
        LookupOperation lookupOperation2 = LookupOperation.newLookup().
                from("push_marketing_commodity_sales_info").//关联表名
                localField("id").//关联字段
                foreignField("pushShopCommodityId").//主表关联字段对应的次表字段
                as("table2");//查询结果集合名

        UnwindOperation unwindOperation2 = new UnwindOperation(Fields.field("$table2"));



        //拼装关联信息
        LookupOperation lookupOperation4 = LookupOperation.newLookup().
                from("push_commodity_traceability_info").//关联表名
                localField("id").//关联字段
                foreignField("pushShopCommodityId").//主表关联字段对应的次表字段
                as("table4");//查询结果集合名

        UnwindOperation unwindOperation4 = new UnwindOperation(Fields.field("$table4"));

        //拼装关联信息
        LookupOperation lookupOperation5 = LookupOperation.newLookup().
                from("push_basic_commodity_info").//关联表名
                localField("id").//关联字段
                foreignField("pushShopCommodityId").//主表关联字段对应的次表字段
                as("table5");//查询结果集合名

        UnwindOperation unwindOperation5 = new UnwindOperation(Fields.field("$table5"));

        //拼装关联信息
        LookupOperation lookupOperation6 = LookupOperation.newLookup().
                from("push_basic_commodity_location_info").//关联表名
                localField("id").//关联字段
                foreignField("pushShopCommodityId").//主表关联字段对应的次表字段
                as("table6");//查询结果集合名

        UnwindOperation unwindOperation6 = new UnwindOperation(Fields.field("$table6"));

        //拼装关联信息
        LookupOperation lookupOperation7 = LookupOperation.newLookup().
                from("push_commodity_commitment_info").//关联表名
                localField("id").//关联字段
                foreignField("pushShopCommodityId").//主表关联字段对应的次表字段
                as("table7");//查询结果集合名

        UnwindOperation unwindOperation7 = new UnwindOperation(Fields.field("$table7"));

        Criteria criteria = new Criteria();
        if(StringUtils.isNotBlank(MapUtils.getString(params,"code"))){
            criteria.and("productTypeId").regex(".*" + params.get("code") +".*");
        }
        if(StringUtils.isNotBlank(MapUtils.getString(params,"keyword"))){
            criteria.and("table5.productName").regex(".*"+MapUtils.getString(params,"keyword")+".*");
        }
        if(StringUtils.isNotBlank(MapUtils.getString(params,"retrospectType"))){
            criteria.and("table4.isRetrospect").is(MapUtils.getString(params,"retrospectType"));
        }
        if(StringUtils.isNotBlank(MapUtils.getString(params,"salesWay"))){
            criteria.and("table1.salesWay").is(MapUtils.getString(params,"salesWay"));
        }
        if(StringUtils.isNotBlank(MapUtils.getString(params,"productStatus"))){
            criteria.and("table2.productStatus").is(MapUtils.getString(params,"productStatus"));
        }
        if(StringUtils.isNotBlank(MapUtils.getString(params,"isHomeDisplay"))){
            criteria.and("table7.isHomeDisplay").is(MapUtils.getInteger(params,"isHomeDisplay"));
        }
        if(StringUtils.isNotBlank(MapUtils.getString(params,"productLocation"))){
            criteria.and("table6.productOrigin").regex("^.*"+MapUtils.getString(params,"productLocation")+".*$");
        }
        criteria.and("table5.storageStatus").is("3").and("table5.isDelete").is("0");

        MatchOperation matchOperation = Aggregation.match(criteria);

        //Aggregation管道操作（还可以加入$match、$project等其他管道操作，但是得注意先后顺序）
        TypedAggregation aggregation = Aggregation.newAggregation(Map.class, lookupOperation1, unwindOperation1,
                lookupOperation2, unwindOperation2, lookupOperation4, unwindOperation4,lookupOperation5, unwindOperation5,lookupOperation6, unwindOperation6,lookupOperation7, unwindOperation7, matchOperation);
        List<Map> results = mongoTemplate.aggregate(aggregation, "push_shop_commodity_info", Map.class).getMappedResults();
        //查出指定字段
        List<Map<String,Object>> pricingList = new ArrayList<>();
        for (Map<String,Object> map : results) {
            Map<String,Object> m = new HashMap<>();
            m.put("notarizationCertificate", map.get("notarizationCertificate"));
            m.put("commodityName",MapUtils.getString(MapUtils.getMap(map, "table5"), "productName"));
            m.put("id",MapUtils.getString(map, "id"));
            m.put("account",MapUtils.getString(map, "account"));
            m.put("createTime",MapUtils.getString(map, "create_time"));
            m.put("currentPrice", MapUtils.getString(MapUtils.getMap(map, "table1"), "currentPrice"));
            m.put("discountPrice", MapUtils.getString(MapUtils.getMap(map, "table1"), "discountPrice"));
            m.put("discountPeriodStr", MapUtils.getMap(map,"table1").get("discountPeriodStr"));
            m.put("startPrice", MapUtils.getString(MapUtils.getMap(map, "table1"), "startPrice"));
            m.put("biddingStartTimeStr", MapUtils.getMap(map,"table1").get("biddingStartTimeStr"));
            m.put("groupBuyingStartTimeStr", MapUtils.getMap(map,"table1").get("groupBuyingStartTimeStr"));
            m.put("biddingEndTimeStr", MapUtils.getMap(map,"table1").get("biddingEndTimeStr"));
            m.put("groupBuyingPrice", MapUtils.getString(MapUtils.getMap(map, "table1"), "groupBuyingPrice"));
            m.put("originalPrice", MapUtils.getMap(map,"table1").get("originalPrice"));
            m.put("groupBuyingEndTimeStr", MapUtils.getMap(map,"table1").get("groupBuyingEndTimeStr"));
            m.put("orderPrice", MapUtils.getMap(map,"table1").get("orderPrice"));
            m.put("depositAmount", MapUtils.getMap(map,"table1").get("depositAmount"));
            m.put("isRetrospect", MapUtils.getMap(map,"table4").get("isRetrospect"));
            m.put("storageStatus", MapUtils.getMap(map,"table5").get("storageStatus"));

            String salesWay = MapUtils.getString(MapUtils.getMap(map, "table1"), "salesWay");
            String productStatus = MapUtils.getString(MapUtils.getMap(map, "table2"), "productStatus");
            if (!"现货产品".equals(productStatus)) {
                salesWay = "预售";
            }
            m.put("salesWay", salesWay);

            m.put("productLocation", MapUtils.getString(MapUtils.getMap(map, "table6"), "productOrigin"));

            params.put("commodityId",MapUtils.getString(map, "id"));
            Integer buyers = shopBiddingMapper.findBuyer(params);
            if (buyers != null) {
                m.put("buyers",buyers);
            }
            Map map1 = shopCardMapper.findAvatar(m);
            if (MapUtils.isNotEmpty(map1)) {
                m.put("name", map1.get("name"));
            }

            Integer participantCount = shopBiddingMapper.participantCount(params);
            if (participantCount != null) {
                m.put("participantCount", participantCount);
            }

            pricingList.add(m);
        }

        //排序
        Collections.sort(pricingList, new Comparator<Map<String,Object>>() {
            @Override
            public int compare(Map<String,Object> b1, Map<String,Object> b2) {
                if("0".equals(MapUtils.getString(params,"sort"))){
                    return MapUtils.getString(b2,"orderNum").compareTo(MapUtils.getString(b1,"orderNum"));

                }else {
                    return MapUtils.getString(b2,"createTime").compareTo(MapUtils.getString(b1,"createTime"));
                }
            }
        });

        return pricingList;
    }

    /**
     * 后台-内容控制-商品
     * @param params
     * @return
     */
    @Override
    public List<Map<String,Object>> findProductList(Map<String, Object> params) {
        //拼装关联信息
        LookupOperation lookupOperation1 = LookupOperation.newLookup().
                from("push_marketing_commodity_pricing_info").//关联表名
                localField("id").//关联字段
                foreignField("pushShopCommodityId").//主表关联字段对应的次表字段
                as("table1");//查询结果集合名

        //使用$unwind展平 上面中的左连接的push_basic_commodity_info表的查询集合"table1"
        UnwindOperation unwindOperation1 = new UnwindOperation(Fields.field("$table1"));

        //拼装关联信息
        LookupOperation lookupOperation5 = LookupOperation.newLookup().
                from("push_basic_commodity_info").//关联表名
                localField("id").//关联字段
                foreignField("pushShopCommodityId").//主表关联字段对应的次表字段
                as("table5");//查询结果集合名

        UnwindOperation unwindOperation5 = new UnwindOperation(Fields.field("$table5"));

        //拼装关联信息
        LookupOperation lookupOperation7 = LookupOperation.newLookup().
                from("push_commodity_commitment_info").//关联表名
                localField("id").//关联字段
                foreignField("pushShopCommodityId").//主表关联字段对应的次表字段
                as("table7");//查询结果集合名

        UnwindOperation unwindOperation7 = new UnwindOperation(Fields.field("$table7"));

        Criteria criteria = new Criteria();
        if(StringUtils.isNotBlank(MapUtils.getString(params,"keyword"))){
            criteria.and("table5.productName").regex(".*"+MapUtils.getString(params,"keyword")+".*");
        }
        criteria.and("table5.storageStatus").is("3").and("table5.isDelete").is("0");

        MatchOperation matchOperation = Aggregation.match(criteria);

        //Aggregation管道操作（还可以加入$match、$project等其他管道操作，但是得注意先后顺序）
        TypedAggregation aggregation = Aggregation.newAggregation(Map.class, lookupOperation1, unwindOperation1,
                lookupOperation5, unwindOperation5,lookupOperation7, unwindOperation7, matchOperation);
        List<Map> results = mongoTemplate.aggregate(aggregation, "push_shop_commodity_info", Map.class).getMappedResults();
        //查出指定字段
        List<Map<String,Object>> pricingList = new ArrayList<>();
        for (Map<String,Object> map : results) {
            Map m = new HashMap();
            m.put("id",MapUtils.getString(map, "id"));
            m.put("productName",MapUtils.getString(map, "commodityName"));
            m.put("industryType",MapUtils.getString(map, "industryType"));
            m.put("productType",MapUtils.getString(map, "productType"));
            m.put("species",MapUtils.getString(map, "species"));
            m.put("variety",MapUtils.getString(map, "variety"));
            m.put("account",MapUtils.getString(map, "account"));
            m.put("create_time",MapUtils.getString(map, "create_time"));
            m.put("commodityName",MapUtils.getString(MapUtils.getMap(map, "table5"), "productName"));
            m.put("isNavDisplay",MapUtils.getString(MapUtils.getMap(map, "table7"), "isNavDisplay"));
            m.put("isShopDisplay",MapUtils.getString(MapUtils.getMap(map, "table7"), "isShopDisplay"));
            m.put("isHomeDisplay",MapUtils.getString(MapUtils.getMap(map, "table7"), "isHomeDisplay"));
            m.put("orderNum",MapUtils.getString(MapUtils.getMap(map, "table7"), "orderNum"));

            pricingList.add(m);
        }

        //排序
        Collections.sort(pricingList, new Comparator<Map<String,Object>>() {
            @Override
            public int compare(Map<String,Object> b1, Map<String,Object> b2) {
                return MapUtils.getString(b2,"create_time").compareTo(MapUtils.getString(b1,"create_time"));
            }
        });

        return pricingList;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void updateHomeDisplay(Map<String, Object> params) {

        //更新价格
        Query modifyQuery = new Query(Criteria.where("pushShopCommodityId").is(MapUtils.getString(params, "id")));
        params.remove("id");
        Update update = new Update();

        String isHomeDisplay = MapUtils.getString(params,"isHomeDisplay");
        String isShopDisplay = MapUtils.getString(params,"isShopDisplay");
        String isNavDisplay = MapUtils.getString(params,"isNavDisplay");
        String orderNum = MapUtils.getString(params,"orderNum");

        if(StringUtils.isNotBlank(isHomeDisplay)){
            update.set("isHomeDisplay",Integer.valueOf(isHomeDisplay));
        }
        if(StringUtils.isNotBlank(isShopDisplay)){
            update.set("isShopDisplay",Integer.valueOf(isShopDisplay));
        }
        if(StringUtils.isNotBlank(isNavDisplay)){
            update.set("isNavDisplay",Integer.valueOf(isNavDisplay));
        }
        if(StringUtils.isNotBlank(orderNum)){
            update.set("orderNum",Integer.valueOf(orderNum));
        }

        WriteResult updateWriteResult = mongoTemplate.updateFirst(modifyQuery, update, Map.class, "push_commodity_commitment_info");
        logger.debug("modifyQuery:{}, update:{}, updateWriteResult:{}", modifyQuery, update, updateWriteResult);

    }

}