package com.ovit.shop.pushTemplate.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.mongodb.WriteResult;
import com.ovit.shop.pushTemplate.mapper.ShopBiddingMapper;
import com.ovit.shop.pushTemplate.service.ShopBiddingService;
import com.ovit.shop.util.ConvertDataUtil;
import com.ovit.shop.util.PageUtil;
import com.ovit.shop.util.Utils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class ShopBiddingServiceImpl implements ShopBiddingService {

    private Logger logger = LogManager.getLogger(this.getClass());

    @Autowired
    private ShopBiddingMapper shopBiddingMapper;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private RedisTemplate<String, Integer> redisTemplate;

    @Value("${pay.expire.time}")
    private int expireHour;

    /**
     * 提交保证金页面信息查询
     * 通过商品ID查询商品名称、保证金金额、商品发布者的账号等信息
     * @param params
     * @return
     */
    @Override
    public Map<String, Object> marginInfo(Map<String, Object> params) {

        //拼装关联信息
        LookupOperation lookupOperation1 = LookupOperation.newLookup().
                from("push_basic_commodity_info").//关联表名
                localField("pushShopCommodityId").//关联字段
                foreignField("pushShopCommodityId").//主表关联字段对应的次表字段
                as("table1");//查询结果集合名

        UnwindOperation unwindOperation1 = new UnwindOperation(Fields.field("$table1"));

        Criteria criteria = Criteria.where("pushShopCommodityId").is(MapUtils.getString(params, "commodityId"));

        MatchOperation matchOperation = Aggregation.match(criteria);

        //Aggregation管道操作（还可以加入$match、$project等其他管道操作，但是得注意先后顺序）
        TypedAggregation aggregation = Aggregation.newAggregation(Map.class, lookupOperation1, unwindOperation1, matchOperation);

        List<Map> results = mongoTemplate.aggregate(aggregation, "push_marketing_commodity_pricing_info", Map.class).getMappedResults();
        //查出指定字段
        Map<String,Object> map = new HashMap<>();
        if (CollectionUtils.isNotEmpty(results)) {
            map.put("productName", MapUtils.getString(MapUtils.getMap(results.get(0), "table1"), "productName"));
            map.put("sellerAccount", MapUtils.getString(results.get(0), "account"));
            map.put("bond", MapUtils.getString(results.get(0), "bond"));
            map.put("startTime", MapUtils.getString(results.get(0), "biddingStartTimeStr"));
            map.put("endTime", MapUtils.getString(results.get(0), "biddingEndTimeStr"));

            Query queryImage = new Query();
            Criteria criteriaImage = Criteria.where("id").is(MapUtils.getString(params,"commodityId"));
            queryImage.addCriteria(criteriaImage);
            List<Map> image = mongoTemplate.find(queryImage, Map.class, "push_shop_commodity_info");
            if (CollectionUtils.isNotEmpty(image)) {
                List<String> imageList = (List<String>)image.get(0).get("notarizationCertificate");
                if (CollectionUtils.isNotEmpty(image)) {
                    map.put("image", imageList);
                } else {
                    map.put("image", new ArrayList<>());
                }
            }
            Query queryUnit = new Query();
            Criteria criteriaUnit = Criteria.where("pushShopCommodityId").is(MapUtils.getString(params,"commodityId"));
            queryUnit.addCriteria(criteriaUnit);
            List<Map> unit = mongoTemplate.find(queryUnit, Map.class, "push_marketing_commodity_sales_info");
            if (CollectionUtils.isNotEmpty(unit)) {
                map.put("unit", unit.get(0).get("productAvailabilityUnits"));
            }
        }
        //如果到了提交订单页面，查出剩余金额及地址信息
        if (StringUtils.isNotBlank(MapUtils.getString(params, "buyerAccount"))) {
            Map<String, Object> remainderInfo = shopBiddingMapper.getRemainderInfo(params);
            if (MapUtils.isNotEmpty(remainderInfo)) {
                remainderInfo.put("addressInfo", JSON.parseObject(MapUtils.getString(remainderInfo, "addressInfo")));
                map.putAll(remainderInfo);
            }
        }
        return map;
    }

    @Override
    public void submitMargin(Map<String, Object> params) {
        String addressInfo = JSON.toJSONString(params.get("addressInfo"));
        params.put("addressInfo", addressInfo);
        //提交保证金
        params.put("status", 1);
        //生产订单号，规则为：当前时间的年月日时分秒 + 4位随机数字
        String time = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String number = Utils.builderRandomString(4);
        String order = time.concat(number);
        params.put("order", order);
        //计算过期时间
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR, expireHour);
        String expireTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(calendar.getTime());
        params.put("expireTime", expireTime);
        //保存竞价开始时间、结束时间、商品预览图片
        String image = JSON.toJSONString(params.get("image"));
        params.put("image", image);
        shopBiddingMapper.submitMargin(params);

    }

    @Override
    public void payMargin(Map<String, Object> params) {
        params.put("status", 2);
        params.put("payTime", "1");
        shopBiddingMapper.updateBidding(params);
    }

    @Override
    public void snatch(Map<String, Object> params) {

        //判断用户是该商品的第几次出价
        String key = MapUtils.getString(params, "commodityId");
        int count = getBiddingCount(key);
        params.put("count", count);
        //竞价后更新状态为3:等待竞拍结果
        params.put("status", 3);
        shopBiddingMapper.updateBidding(params);

    }

    @Override
    public int getBiddingCount(String key) {

        long increment = redisTemplate.opsForValue().increment(key, 1);
        if (increment > Integer.MAX_VALUE) {
            redisTemplate.opsForValue().set(key, 0);
            return 0;
        } else {
            return (int) increment;
        }
    }

    @Override
    public void handle(List<Map<String, Object>> params) {

        shopBiddingMapper.batchHandle(params);
    }

    @Override
    public Map<String, Object> bidRecord(Map<String, Object> params) {
        //获取最小价格、平均价格、出价次数
        Map<String,Object> record = shopBiddingMapper.bidRecord(params);
        if (MapUtils.isEmpty(record)) {
            record = new HashMap<>();
            record.put("avgPrice", 0);
            record.put("minPrice", "0");
            record.put("participantCount", 0);
            record.put("count", 0);
            record.put("price", "0");
            record.put("number", "0");
            record.put("sum", 0);
        } else {
            //获取参与人数
            Integer participantCount = shopBiddingMapper.getParticipantCount(params);
            if (participantCount != null) {
                record.put("participantCount", participantCount);
            }
            //获取用户最后一次出价的单价与数量
            Map<String, Object> bidInfo = shopBiddingMapper.getBidInfo(params);
            if (MapUtils.isNotEmpty(bidInfo)) {
                record.putAll(bidInfo);
            } else {
                record.put("price", "0");
                record.put("number", "0");
            }
        }
        return record;
    }

    @Override
    public List<Map<String, Object>> myMargin(Map<String, Object> params) {
        List<Map<String, Object>> myMargin = shopBiddingMapper.getMyMargin(params);

        for (Map map:myMargin){
            Map<String,Object> useTemplate = shopBiddingMapper.findUseTemplate(MapUtils.getString(map, "sellerAccount"));
            map.put("templateId", MapUtils.getString(useTemplate, "templateId"));
            Map<String, Object> memberInfo = shopBiddingMapper.memberInfo(map);
            map.put("memberName",MapUtils.getString(memberInfo, "memberName"));

            map.put("startTime","");
            map.put("image","");

            Query query = new Query();
            Criteria criteria = Criteria.where("pushShopCommodityId").is(MapUtils.getString(map,"commodityId"));
            query.addCriteria(criteria);
            List<Map> pricing = mongoTemplate.find(query, Map.class, "push_marketing_commodity_pricing_info");

            Query query1 = new Query();
            Criteria criteria1 = Criteria.where("id").is(MapUtils.getString(map,"commodityId"));
            query1.addCriteria(criteria1);
            List<Map> image = mongoTemplate.find(query1, Map.class, "push_shop_commodity_info");
            if(!pricing.isEmpty()){
                map.put("startTime",MapUtils.getString(pricing.get(0),"biddingStartTime"));
            }
            if(!image.isEmpty()){
                map.put("image",image.get(0).get("notarizationCertificate"));
            }
        }
        return myMargin;
    }

    @Override
    public PageInfo<Map<String, Object>> auctioning(Map<String, Object> params) {
        //分页查询数据
        PageUtil.initPage(params);
        List<Map<String, Object>> list = shopBiddingMapper.auctioning(params);
        getImageAndName(list);
        return new PageInfo<>(list);
    }

    @Override
    public PageInfo<Map<String, Object>> hasBid(Map<String, Object> params) {
        //分页查询数据
        PageUtil.initPage(params);
        List<Map<String, Object>> list = shopBiddingMapper.hasBid(params);
        getImageAndName(list);
        return new PageInfo<>(list);
    }

    @Override
    public PageInfo<Map<String, Object>> missBid(Map<String, Object> params) {
        //分页查询数据
        PageUtil.initPage(params);
        List<Map<String, Object>> list = shopBiddingMapper.missBid(params);
        getImageAndName(list);
        return new PageInfo<>(list);
    }

    /**
     * 获取图片和商家名称
     * @param list
     * @return
     */
    private List<Map<String,Object>> getImageAndName(List<Map<String,Object>> list) {
        for (Map<String,Object> map : list) {
            //获取商品预览图片
            JSONArray image = JSONArray.parseArray(MapUtils.getString(map, "image"));
            map.put("image", image);
            //获取商家名称
            Map<String,Object> useTemplate = shopBiddingMapper.findUseTemplate(MapUtils.getString(map, "sellerAccount"));
            map.put("templateId", MapUtils.getString(useTemplate, "templateId"));
            Map<String, Object> memberInfo = shopBiddingMapper.memberInfo(map);
            map.put("memberName", MapUtils.getString(memberInfo, "memberName"));
        }
        return list;
    }

    @Override
    public Map<String, Object> upcomingAuctionList(Map<String, Object> params) {

        //拼装关联信息
        LookupOperation lookupOperation1 = LookupOperation.newLookup().
                from("push_basic_commodity_info").//关联表名
                localField("pushShopCommodityId").//关联字段
                foreignField("pushShopCommodityId").//主表关联字段对应的次表字段
                as("table1");//查询结果集合名

        UnwindOperation unwindOperation1 = new UnwindOperation(Fields.field("$table1"));

        //拼装关联信息
        LookupOperation lookupOperation2 = LookupOperation.newLookup().
                from("push_marketing_commodity_sales_info").//关联表名
                localField("pushShopCommodityId").//关联字段
                foreignField("pushShopCommodityId").//主表关联字段对应的次表字段
                as("table2");//查询结果集合名

        UnwindOperation unwindOperation2 = new UnwindOperation(Fields.field("$table2"));

        //获取当前时间，即将竞拍为：竞拍开始时间大于当前时间
        String currentTime = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date());
        Criteria criteria = Criteria.where("account").is(MapUtils.getString(params, "sellerAccount")).
                and("biddingStartTimeStr").gt(currentTime).and("table1.storageStatus").is("3").and("salesWay").is("竞价销售");
        if (StringUtils.isNotBlank(MapUtils.getString(params, "commodityName"))) {
            criteria.and("table1.productName").regex(".*" + MapUtils.getString(params, "commodityName") + ".*");
        }

        MatchOperation matchOperation = Aggregation.match(criteria);

        //分页
        int number = MapUtils.getInteger(params, "pageNum");
        int size = MapUtils.getInteger(params, "pageSize");
        //pageNumber, skip跳过多少条
        SkipOperation skip = Aggregation.skip(number>1 ? (number-1) * size : 0);
        //pageSize, limit最多显示条数
        LimitOperation limit = Aggregation.limit(size);

        //Aggregation管道操作（还可以加入$match、$project等其他管道操作，但是得注意先后顺序）
        TypedAggregation aggregation = Aggregation.newAggregation(Map.class, lookupOperation1, unwindOperation1,
                lookupOperation2, unwindOperation2, matchOperation,
                skip, limit);

        List<Map> results = mongoTemplate.aggregate(aggregation, "push_marketing_commodity_pricing_info", Map.class).getMappedResults();

        Map<String,Object> resultMap = new HashMap<>();
        List<Map<String,Object>> list = new ArrayList<>();
        for (Map<String,Object> map : results) {
            Map<String,Object> m = new HashMap<>();
            m.put("productName", MapUtils.getString(MapUtils.getMap(map, "table1"), "productName"));
            m.put("startTime", MapUtils.getString(map, "biddingStartTimeStr"));
            m.put("productVbep", MapUtils.getString(MapUtils.getMap(map, "table2"), "productAvailability"));
            m.put("unit", MapUtils.getString(MapUtils.getMap(map, "table2"), "productAvailabilityUnits"));
            m.put("commodityId", MapUtils.getString(map, "pushShopCommodityId"));
            list.add(m);
        }
        if (CollectionUtils.isNotEmpty(list)) {
            //排序
            Collections.sort(list, new Comparator<Map<String,Object>>() {
                @Override
                public int compare(Map<String,Object> b1, Map<String,Object> b2) {
                    //按照竞拍开始时间降序
                    return MapUtils.getString(b2,"startTime").compareTo(MapUtils.getString(b1,"startTime"));
                }
            });
            resultMap.put("list", list);
        } else {
            resultMap.put("list", new ArrayList<>());
        }

        //总数查询
        Aggregation counts = Aggregation.newAggregation(Map.class, lookupOperation1, unwindOperation1,
                lookupOperation2, unwindOperation2, matchOperation);
        int count = mongoTemplate.aggregate(counts, "push_marketing_commodity_pricing_info", Map.class).getMappedResults().size();
        resultMap.put("total", count);
        return resultMap;
    }

    @Override
    public Map<String, Object> upcomingAuctionDetail(Map<String, Object> params) {
        //分页查询数据
        PageUtil.initPage(params);
        Map<String,Object> resultMap = new HashMap<>();
        List<Map<String, Object>> list = shopBiddingMapper.upcomingAuctionDetail(params);
        if (CollectionUtils.isEmpty(list)) {
            list = new ArrayList<>();
        }
        resultMap.put("list", list);
        resultMap.put("total", list.size());
        //获取已确认保证金并具备竞拍资格的人数
        Integer bidCount = shopBiddingMapper.hasBidCount(MapUtils.getString(params, "commodityId"));
        resultMap.put("bidCount", bidCount);

        //查询商品名称、竞拍开始时间、起拍价、可拍数量
        //拼装关联信息
        LookupOperation lookupOperation1 = LookupOperation.newLookup().
                from("push_basic_commodity_info").//关联表名
                localField("pushShopCommodityId").//关联字段
                foreignField("pushShopCommodityId").//主表关联字段对应的次表字段
                as("table1");//查询结果集合名

        UnwindOperation unwindOperation1 = new UnwindOperation(Fields.field("$table1"));

        //拼装关联信息
        LookupOperation lookupOperation2 = LookupOperation.newLookup().
                from("push_marketing_commodity_sales_info").//关联表名
                localField("pushShopCommodityId").//关联字段
                foreignField("pushShopCommodityId").//主表关联字段对应的次表字段
                as("table2");//查询结果集合名

        UnwindOperation unwindOperation2 = new UnwindOperation(Fields.field("$table2"));

        //拼装关联信息
        LookupOperation lookupOperation3 = LookupOperation.newLookup().
                from("push_shop_commodity_info").//关联表名
                localField("pushShopCommodityId").//关联字段
                foreignField("id").//主表关联字段对应的次表字段
                as("table3");//查询结果集合名

        UnwindOperation unwindOperation3 = new UnwindOperation(Fields.field("$table3"));

        Criteria criteria = Criteria.where("pushShopCommodityId").is(MapUtils.getString(params, "commodityId"));

        MatchOperation matchOperation = Aggregation.match(criteria);

        //Aggregation管道操作（还可以加入$match、$project等其他管道操作，但是得注意先后顺序）
        TypedAggregation aggregation = Aggregation.newAggregation(Map.class, lookupOperation1, unwindOperation1,
                lookupOperation2, unwindOperation2, lookupOperation3, unwindOperation3, matchOperation);

        List<Map> results = mongoTemplate.aggregate(aggregation, "push_marketing_commodity_pricing_info", Map.class).getMappedResults();
        if (CollectionUtils.isNotEmpty(results)) {
            resultMap.put("productVbep", MapUtils.getString(MapUtils.getMap(results.get(0), "table2"), "productAvailability"));
            resultMap.put("unit", MapUtils.getString(MapUtils.getMap(results.get(0), "table2"), "productAvailabilityUnits"));
            resultMap.put("startPrice", MapUtils.getString(results.get(0), "startPrice"));
            resultMap.put("productName", MapUtils.getString(MapUtils.getMap(results.get(0), "table1"), "productName"));
            resultMap.put("startTime", MapUtils.getString(results.get(0), "biddingStartTimeStr"));
            resultMap.put("image", MapUtils.getMap(results.get(0), "table3").get("notarizationCertificate"));
        }

        return resultMap;
    }

    @Override
    public Map<String, Object> auctioningList(Map<String, Object> params) {

        //拼装关联信息
        LookupOperation lookupOperation1 = LookupOperation.newLookup().
                from("push_basic_commodity_info").//关联表名
                localField("pushShopCommodityId").//关联字段
                foreignField("pushShopCommodityId").//主表关联字段对应的次表字段
                as("table1");//查询结果集合名

        UnwindOperation unwindOperation1 = new UnwindOperation(Fields.field("$table1"));

        //拼装关联信息
        LookupOperation lookupOperation2 = LookupOperation.newLookup().
                from("push_marketing_commodity_sales_info").//关联表名
                localField("pushShopCommodityId").//关联字段
                foreignField("pushShopCommodityId").//主表关联字段对应的次表字段
                as("table2");//查询结果集合名

        UnwindOperation unwindOperation2 = new UnwindOperation(Fields.field("$table2"));

        //获取当前时间，竞拍中为：竞拍开始时间小于当前时间，并且竞价结束时间大于当前时间
        String currentTime = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date());
        Criteria criteria = Criteria.where("account").is(MapUtils.getString(params, "sellerAccount")).
                and("biddingStartTimeStr").lt(currentTime).and("biddingEndTimeStr").gt(currentTime).and("table1.storageStatus").is("3").and("salesWay").is("竞价销售");
        if (StringUtils.isNotBlank(MapUtils.getString(params, "commodityName"))) {
            criteria.and("table1.productName").regex(".*" + MapUtils.getString(params, "commodityName") + ".*");
        }

        MatchOperation matchOperation = Aggregation.match(criteria);

        //分页
        int number = MapUtils.getInteger(params, "pageNum");
        int size = MapUtils.getInteger(params, "pageSize");
        //pageNumber, skip跳过多少条
        SkipOperation skip = Aggregation.skip(number>1 ? (number-1) * size : 0);
        //pageSize, limit最多显示条数
        LimitOperation limit = Aggregation.limit(size);

        //Aggregation管道操作（还可以加入$match、$project等其他管道操作，但是得注意先后顺序）
        TypedAggregation aggregation = Aggregation.newAggregation(Map.class, lookupOperation1, unwindOperation1,
                lookupOperation2, unwindOperation2, matchOperation,
                skip, limit);

        List<Map> results = mongoTemplate.aggregate(aggregation, "push_marketing_commodity_pricing_info", Map.class).getMappedResults();

        Map<String,Object> resultMap = new HashMap<>();
        List<Map<String,Object>> list = new ArrayList<>();
        for (Map<String,Object> map : results) {
            Map<String,Object> m = new HashMap<>();
            m.put("productName", MapUtils.getString(MapUtils.getMap(map, "table1"), "productName"));
            m.put("startTime", MapUtils.getString(map, "biddingStartTimeStr"));
            m.put("endTime", MapUtils.getString(map, "biddingEndTimeStr"));
            m.put("productVbep", MapUtils.getString(MapUtils.getMap(map, "table2"), "productAvailability"));
            m.put("unit", MapUtils.getString(MapUtils.getMap(map, "table2"), "productAvailabilityUnits"));
            m.put("commodityId", MapUtils.getString(map, "pushShopCommodityId"));
            list.add(m);
        }
        if (CollectionUtils.isNotEmpty(list)) {
            //排序
            Collections.sort(list, new Comparator<Map<String,Object>>() {
                @Override
                public int compare(Map<String,Object> b1, Map<String,Object> b2) {
                    //按照竞拍开始时间降序
                    return MapUtils.getString(b2,"startTime").compareTo(MapUtils.getString(b1,"startTime"));
                }
            });
            resultMap.put("list", list);
        } else {
            resultMap.put("list", new ArrayList<>());
        }

        //总数查询
        Aggregation counts = Aggregation.newAggregation(Map.class, lookupOperation1, unwindOperation1,
                lookupOperation2, unwindOperation2, matchOperation);
        int count = mongoTemplate.aggregate(counts, "push_marketing_commodity_pricing_info", Map.class).getMappedResults().size();
        resultMap.put("total", count);
        return resultMap;
    }

    @Override
    public Map<String, Object> auctioningDetail(Map<String, Object> params) {
        //分页查询数据
        PageUtil.initPage(params);
        Map<String,Object> resultMap = new HashMap<>();
        List<Map<String, Object>> list = shopBiddingMapper.auctioningDetail(params);
        if (CollectionUtils.isEmpty(list)) {
            list = new ArrayList<>();
        }
        resultMap.put("list", list);
        resultMap.put("total", list.size());

        //查询商品名称、竞拍开始时间、起拍价、可拍数量
        //拼装关联信息
        LookupOperation lookupOperation1 = LookupOperation.newLookup().
                from("push_basic_commodity_info").//关联表名
                localField("pushShopCommodityId").//关联字段
                foreignField("pushShopCommodityId").//主表关联字段对应的次表字段
                as("table1");//查询结果集合名

        UnwindOperation unwindOperation1 = new UnwindOperation(Fields.field("$table1"));

        //拼装关联信息
        LookupOperation lookupOperation2 = LookupOperation.newLookup().
                from("push_marketing_commodity_sales_info").//关联表名
                localField("pushShopCommodityId").//关联字段
                foreignField("pushShopCommodityId").//主表关联字段对应的次表字段
                as("table2");//查询结果集合名

        UnwindOperation unwindOperation2 = new UnwindOperation(Fields.field("$table2"));

        //拼装关联信息
        LookupOperation lookupOperation3 = LookupOperation.newLookup().
                from("push_shop_commodity_info").//关联表名
                localField("pushShopCommodityId").//关联字段
                foreignField("id").//主表关联字段对应的次表字段
                as("table3");//查询结果集合名

        UnwindOperation unwindOperation3 = new UnwindOperation(Fields.field("$table3"));

        Criteria criteria = Criteria.where("pushShopCommodityId").is(MapUtils.getString(params, "commodityId"));

        MatchOperation matchOperation = Aggregation.match(criteria);

        //Aggregation管道操作（还可以加入$match、$project等其他管道操作，但是得注意先后顺序）
        TypedAggregation aggregation = Aggregation.newAggregation(Map.class, lookupOperation1, unwindOperation1,
                lookupOperation2, unwindOperation2, lookupOperation3, unwindOperation3, matchOperation);

        List<Map> results = mongoTemplate.aggregate(aggregation, "push_marketing_commodity_pricing_info", Map.class).getMappedResults();
        if (CollectionUtils.isNotEmpty(results)) {
            resultMap.put("productVbep", MapUtils.getString(MapUtils.getMap(results.get(0), "table2"), "productAvailability"));
            resultMap.put("unit", MapUtils.getString(MapUtils.getMap(results.get(0), "table2"), "productAvailabilityUnits"));
            resultMap.put("startPrice", MapUtils.getString(results.get(0), "startPrice"));
            resultMap.put("productName", MapUtils.getString(MapUtils.getMap(results.get(0), "table1"), "productName"));
            resultMap.put("startTime", MapUtils.getString(results.get(0), "biddingStartTimeStr"));
            resultMap.put("image", MapUtils.getMap(results.get(0), "table3").get("notarizationCertificate"));
        }

        //获取竞拍出价信息
        Map<String,Object> record = shopBiddingMapper.auctionRecord(params);
        if (MapUtils.isEmpty(record)) {
            record = new HashMap<>();
            record.put("minPrice", "0");
            record.put("maxPrice", "0");
            record.put("participantCount", 0);
            record.put("count", 0);
            record.put("sum", 0);
        } else {
            //获取参与人数
            Integer participantCount = shopBiddingMapper.participantCount(params);
            if (participantCount != null) {
                record.put("participantCount", participantCount);
            }
        }
        resultMap.putAll(record);

        return resultMap;
    }

    @Override
    public Map<String, Object> confirmList(Map<String, Object> params) {

        //拼装关联信息
        LookupOperation lookupOperation1 = LookupOperation.newLookup().
                from("push_basic_commodity_info").//关联表名
                localField("pushShopCommodityId").//关联字段
                foreignField("pushShopCommodityId").//主表关联字段对应的次表字段
                as("table1");//查询结果集合名

        UnwindOperation unwindOperation1 = new UnwindOperation(Fields.field("$table1"));

        //拼装关联信息
        LookupOperation lookupOperation2 = LookupOperation.newLookup().
                from("push_marketing_commodity_sales_info").//关联表名
                localField("pushShopCommodityId").//关联字段
                foreignField("pushShopCommodityId").//主表关联字段对应的次表字段
                as("table2");//查询结果集合名

        UnwindOperation unwindOperation2 = new UnwindOperation(Fields.field("$table2"));

        //获取当前时间，竞拍结果确认为：竞价结束时间小于当前时间
        String currentTime = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date());
        Criteria criteria = Criteria.where("account").is(MapUtils.getString(params, "sellerAccount")).
                and("biddingEndTimeStr").lt(currentTime).and("table1.storageStatus").is("3").and("salesWay").is("竞价销售");
        if (StringUtils.isNotBlank(MapUtils.getString(params, "commodityName"))) {
            criteria.and("table1.productName").regex(".*" + MapUtils.getString(params, "commodityName") + ".*");
        }

        MatchOperation matchOperation = Aggregation.match(criteria);

        //分页
        int number = MapUtils.getInteger(params, "pageNum");
        int size = MapUtils.getInteger(params, "pageSize");
        //pageNumber, skip跳过多少条
        SkipOperation skip = Aggregation.skip(number>1 ? (number-1) * size : 0);
        //pageSize, limit最多显示条数
        LimitOperation limit = Aggregation.limit(size);

        //Aggregation管道操作（还可以加入$match、$project等其他管道操作，但是得注意先后顺序）
        TypedAggregation aggregation = Aggregation.newAggregation(Map.class, lookupOperation1, unwindOperation1,
                lookupOperation2, unwindOperation2, matchOperation,
                skip, limit);

        List<Map> results = mongoTemplate.aggregate(aggregation, "push_marketing_commodity_pricing_info", Map.class).getMappedResults();

        Map<String,Object> resultMap = new HashMap<>();
        List<Map<String,Object>> list = new ArrayList<>();
        for (Map<String,Object> map : results) {
            Map<String,Object> m = new HashMap<>();
            m.put("productName", MapUtils.getString(MapUtils.getMap(map, "table1"), "productName"));
            m.put("startTime", MapUtils.getString(map, "biddingStartTimeStr"));
            m.put("endTime", MapUtils.getString(map, "biddingEndTimeStr"));
            m.put("productVbep", MapUtils.getString(MapUtils.getMap(map, "table2"), "productAvailability"));
            m.put("unit", MapUtils.getString(MapUtils.getMap(map, "table2"), "productAvailabilityUnits"));
            m.put("commodityId", MapUtils.getString(map, "pushShopCommodityId"));
            list.add(m);
        }
        if (CollectionUtils.isNotEmpty(list)) {
            //排序
            Collections.sort(list, new Comparator<Map<String,Object>>() {
                @Override
                public int compare(Map<String,Object> b1, Map<String,Object> b2) {
                    //按照竞拍结束时间降序
                    return MapUtils.getString(b2,"endTime").compareTo(MapUtils.getString(b1,"endTime"));
                }
            });
            resultMap.put("list", list);
        } else {
            resultMap.put("list", new ArrayList<>());
        }

        //总数查询
        Aggregation counts = Aggregation.newAggregation(Map.class, lookupOperation1, unwindOperation1,
                lookupOperation2, unwindOperation2, matchOperation);
        int count = mongoTemplate.aggregate(counts, "push_marketing_commodity_pricing_info", Map.class).getMappedResults().size();
        resultMap.put("total", count);
        return resultMap;
    }

    @Override
    public Map<String, Object> confirmDetail(Map<String, Object> params) {
        //分页查询数据
        PageUtil.initPage(params);
        Map<String,Object> resultMap = new HashMap<>();
        List<Map<String, Object>> list = shopBiddingMapper.auctioningDetail(params);
        if (CollectionUtils.isEmpty(list)) {
            list = new ArrayList<>();
        } else {
            //排序
            Collections.sort(list, new Comparator<Map<String,Object>>() {
                @Override
                public int compare(Map<String,Object> b1, Map<String,Object> b2) {
                    if ("0".equals(MapUtils.getString(params, "priceOrder"))) {
                        //按照价格降序
                        return MapUtils.getInteger(b2,"price").compareTo(MapUtils.getInteger(b1,"price"));
                    } else if ("1".equals(MapUtils.getString(params, "priceOrder"))) {
                        //按照价格升序
                        return MapUtils.getInteger(b1,"price").compareTo(MapUtils.getInteger(b2,"price"));
                    } else if ("1".equals(MapUtils.getString(params, "timeOrder"))) {
                        //按照出价时间升序
                        return MapUtils.getString(b1,"payTime").compareTo(MapUtils.getString(b2,"payTime"));
                    } else if ("0".equals(MapUtils.getString(params, "numberOrder"))) {
                        //按照竞拍数量时间降序
                        return MapUtils.getString(b2,"number").compareTo(MapUtils.getString(b1,"number"));
                    } else if ("1".equals(MapUtils.getString(params, "numberOrder"))) {
                        //按照竞拍数量升序
                        return MapUtils.getString(b1,"number").compareTo(MapUtils.getString(b2,"number"));
                    } else {
                        //默认按照出价时间降序
                        return MapUtils.getString(b2,"payTime").compareTo(MapUtils.getString(b1,"payTime"));
                    }
                }
            });
        }
        resultMap.put("list", list);
        resultMap.put("total", list.size());

        //查询商品名称、竞拍开始时间、起拍价、可拍数量
        //拼装关联信息
        LookupOperation lookupOperation1 = LookupOperation.newLookup().
                from("push_basic_commodity_info").//关联表名
                localField("pushShopCommodityId").//关联字段
                foreignField("pushShopCommodityId").//主表关联字段对应的次表字段
                as("table1");//查询结果集合名

        UnwindOperation unwindOperation1 = new UnwindOperation(Fields.field("$table1"));

        //拼装关联信息
        LookupOperation lookupOperation2 = LookupOperation.newLookup().
                from("push_marketing_commodity_sales_info").//关联表名
                localField("pushShopCommodityId").//关联字段
                foreignField("pushShopCommodityId").//主表关联字段对应的次表字段
                as("table2");//查询结果集合名

        UnwindOperation unwindOperation2 = new UnwindOperation(Fields.field("$table2"));

        //拼装关联信息
        LookupOperation lookupOperation3 = LookupOperation.newLookup().
                from("push_shop_commodity_info").//关联表名
                localField("pushShopCommodityId").//关联字段
                foreignField("id").//主表关联字段对应的次表字段
                as("table3");//查询结果集合名

        UnwindOperation unwindOperation3 = new UnwindOperation(Fields.field("$table3"));

        Criteria criteria = Criteria.where("pushShopCommodityId").is(MapUtils.getString(params, "commodityId"));

        MatchOperation matchOperation = Aggregation.match(criteria);

        //Aggregation管道操作（还可以加入$match、$project等其他管道操作，但是得注意先后顺序）
        TypedAggregation aggregation = Aggregation.newAggregation(Map.class, lookupOperation1, unwindOperation1,
                lookupOperation2, unwindOperation2, lookupOperation3, unwindOperation3, matchOperation);

        List<Map> results = mongoTemplate.aggregate(aggregation, "push_marketing_commodity_pricing_info", Map.class).getMappedResults();
        String productVbep = "";
        if (CollectionUtils.isNotEmpty(results)) {
            productVbep = MapUtils.getString(MapUtils.getMap(results.get(0), "table2"), "productAvailability");
            resultMap.put("productVbep", productVbep);
            resultMap.put("unit", MapUtils.getString(MapUtils.getMap(results.get(0), "table2"), "productAvailabilityUnits"));
            resultMap.put("startPrice", MapUtils.getString(results.get(0), "startPrice"));
            resultMap.put("productName", MapUtils.getString(MapUtils.getMap(results.get(0), "table1"), "productName"));
            resultMap.put("startTime", MapUtils.getString(results.get(0), "biddingStartTimeStr"));
            resultMap.put("image", MapUtils.getMap(results.get(0), "table3").get("notarizationCertificate"));
        }

        //获取竞拍出价信息
        Map<String,Object> record = shopBiddingMapper.auctionRecord(params);
        if (MapUtils.isEmpty(record)) {
            record = new HashMap<>();
            record.put("minPrice", "0");
            record.put("maxPrice", "0");
            record.put("participantCount", 0);
            record.put("count", 0);
            record.put("sum", 0);
            record.put("remainNum", 0);
        } else {
            //获取参与人数
            Integer participantCount = shopBiddingMapper.participantCount(params);
            if (participantCount != null) {
                record.put("participantCount", participantCount);
            }
            //剩余可售量
            String bidNum = shopBiddingMapper.bidNum(params);
            if (StringUtils.isNotBlank(bidNum)) {
                BigDecimal num1 = new BigDecimal(productVbep);
                BigDecimal num2 = new BigDecimal(bidNum);
                BigDecimal remainNum = num1.subtract(num2);
                record.put("remainNum", remainNum);
            } else {
                record.put("remainNum", productVbep);
            }
        }
        resultMap.putAll(record);

        return resultMap;
    }

    @Override
    public void cancelOrder(int id) {
        shopBiddingMapper.cancelOrder(id);
    }

    @Override
    public Map<String, Object> orderDetail(int id) {
        Map<String, Object> orderDetail = shopBiddingMapper.orderDetail(id);
        if (MapUtils.isNotEmpty(orderDetail)) {
            orderDetail.put("addressInfo", JSON.parseObject(MapUtils.getString(orderDetail, "addressInfo")));
            //获取商品预览图片
            JSONArray image = JSONArray.parseArray(MapUtils.getString(orderDetail, "image"));
            orderDetail.put("image", image);
        } else {
            orderDetail = new HashMap<>();
        }
        return orderDetail;
    }


}