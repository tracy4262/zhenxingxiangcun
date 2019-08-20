package com.ovit.shop.pushTemplate.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.mongodb.WriteResult;
import com.ovit.shop.pushTemplate.service.ShopStorageInfoService;
import org.apache.commons.collections.CollectionUtils;
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

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class ShopStorageInfoServiceImpl implements ShopStorageInfoService {

    private Logger logger = LogManager.getLogger(this.getClass());

    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * 获取商品货架信息
     * @param params
     * @return
     */
    @Override
    public List<Map<String, Object>> getShopStorageList(Map<String, Object> params) {

        //拼装关联信息
        LookupOperation lookupOperation1 = LookupOperation.newLookup().
                from("push_basic_commodity_info").//关联表名
                localField("pushShopCommodityId").//关联字段
                foreignField("pushShopCommodityId").//主表关联字段对应的次表字段
                as("table1");//查询结果集合名

        //使用$unwind展平 上面中的左连接的push_basic_commodity_info表的查询集合"table1"
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
                from("push_marketing_commodity_pricing_info").//关联表名
                localField("pushShopCommodityId").//关联字段
                foreignField("pushShopCommodityId").//主表关联字段对应的次表字段
                as("table3");//查询结果集合名

        UnwindOperation unwindOperation3 = new UnwindOperation(Fields.field("$table3"));

        //拼装关联信息
        LookupOperation lookupOperation4 = LookupOperation.newLookup().
                from("push_shop_commodity_info").//关联表名
                localField("pushShopCommodityId").//关联字段
                foreignField("id").//主表关联字段对应的次表字段
                as("table4");//查询结果集合名

        UnwindOperation unwindOperation4 = new UnwindOperation(Fields.field("$table4"));

        Criteria criteria = Criteria.where("account").is(MapUtils.getString(params, "account"));
        if (StringUtils.isNotBlank(MapUtils.getString(params, "isDelete"))) {
            criteria.and("table1.isDelete").is(MapUtils.getString(params, "isDelete"));
        }
        if (StringUtils.isNotBlank(MapUtils.getString(params, "commodityName"))) {
            criteria.and("table1.productName").regex(".*" + MapUtils.getString(params, "commodityName") + ".*");
        }
        String storageStatus = MapUtils.getString(params, "storageStatus");
        if ("0".equals(storageStatus) || "1".equals(storageStatus) || "2".equals(storageStatus) || "3".equals(storageStatus)) {
            //0：未上架、1：我下架的、2：售完下架、3：上架
            criteria.and("table1.storageStatus").is(storageStatus);
        } else if ("4".equals(storageStatus)) {
            //4：查询全部
            criteria.and("table1.storageStatus").ne("3");
        } else if ("5".equals(storageStatus)) {
            //5：查询全部的下架数据
            List<String> l = new ArrayList<>();
            l.add("1");
            l.add("2");
            criteria.and("table1.storageStatus").in(l);
        }
        if (StringUtils.isNotBlank(MapUtils.getString(params, "productTypeId"))) {
            criteria.and("table4.productTypeId").is(MapUtils.getString(params, "productTypeId"));
        }

        MatchOperation matchOperation = Aggregation.match(criteria);

        //Aggregation管道操作（还可以加入$match、$project等其他管道操作，但是得注意先后顺序）
        TypedAggregation aggregation = Aggregation.newAggregation(Map.class, lookupOperation1, unwindOperation1,
                lookupOperation2, unwindOperation2, lookupOperation3, unwindOperation3, lookupOperation4, unwindOperation4, matchOperation);

        List<Map> results = mongoTemplate.aggregate(aggregation, "push_commodity_commitment_info", Map.class).getMappedResults();
        //查出指定字段
        List<Map<String,Object>> storageList = new ArrayList<>();
        for (Map<String,Object> map : results) {
            Map<String,Object> m = new HashMap<>();
            String notarizationCertificate = JSON.toJSONString(MapUtils.getMap(map, "table4").get("notarizationCertificate"));
            if (StringUtils.isBlank(notarizationCertificate)) {
                m.put("commodityImage", new String[0]);
            } else {
                JSONArray array = JSONArray.parseArray(notarizationCertificate);
                m.put("commodityImage", array);
            }
            m.put("commodityId", MapUtils.getString(MapUtils.getMap(map, "table1"), "pushShopCommodityId"));
            m.put("commodityName", MapUtils.getString(MapUtils.getMap(map, "table1"), "productName"));
            m.put("productVbep", MapUtils.getString(MapUtils.getMap(map, "table2"), "productAvailability"));
            m.put("storageStatus", MapUtils.getString(MapUtils.getMap(map, "table1"), "storageStatus"));
            m.put("createTime", MapUtils.getString(map, "create_time"));
            m.put("deleteTime", MapUtils.getString(MapUtils.getMap(map, "table1"), "deleteTime"));
            //上架时间，需要在push_basic_commodity_info表中新增storageStatus、onSaleTime、deleteTime
            m.put("onSaleTime", MapUtils.getString(MapUtils.getMap(map, "table1"), "onSaleTime"));
            m.put("unit", MapUtils.getString(MapUtils.getMap(map, "table2"), "productAvailabilityUnits"));

            //判断定价方式
            String salesWay = MapUtils.getString(MapUtils.getMap(map, "table3"), "salesWay");
            String productStatus = MapUtils.getString(MapUtils.getMap(map, "table2"), "productStatus");
            if (!"现货产品".equals(productStatus)) {
                salesWay = "预售";
            }
            m.put("salesWay", salesWay);
            m.put("pushShopCommodityId", MapUtils.getString(MapUtils.getMap(map, "table4"), "id"));
            m.put("shopPushTemplateId", MapUtils.getString(MapUtils.getMap(map, "table4"), "shopPushTemplateId"));
            m.put("templateType", MapUtils.getString(MapUtils.getMap(map, "table4"), "templateType"));
            m.put("productCategoryId", MapUtils.getString(MapUtils.getMap(map, "table4"), "productCategoryId"));

            m.put("priceInfo", MapUtils.getMap(map, "table3"));

            storageList.add(m);
        }

        //排序
        Collections.sort(storageList, new Comparator<Map<String,Object>>() {
            @Override
            public int compare(Map<String,Object> b1, Map<String,Object> b2) {

                if ("0".equals(MapUtils.getString(params, "storageSort"))) {
                    //按照库存可售量降序
                    return MapUtils.getInteger(b2,"productVbep").compareTo(MapUtils.getInteger(b1,"productVbep"));
                } else if ("1".equals(MapUtils.getString(params, "storageSort"))) {
                    //按照库存可售量升序
                    return MapUtils.getInteger(b1,"productVbep").compareTo(MapUtils.getInteger(b2,"productVbep"));
                } else if ("1".equals(MapUtils.getString(params, "timeSort"))) {
                    //按照创建时间升序
                    return MapUtils.getString(b1,"createTime").compareTo(MapUtils.getString(b2,"createTime"));
                } else if ("0".equals(MapUtils.getString(params, "deleteTime"))) {
                    //按照删除时间降序
                    return MapUtils.getString(b2,"deleteTime").compareTo(MapUtils.getString(b1,"deleteTime"));
                } else if ("1".equals(MapUtils.getString(params, "deleteTime"))) {
                    //按照删除时间升序
                    return MapUtils.getString(b1,"deleteTime").compareTo(MapUtils.getString(b2,"deleteTime"));
                } else if ("0".equals(MapUtils.getString(params, "onSaleTime"))) {
                    //按照上架时间降序
                    return MapUtils.getString(b2,"onSaleTime").compareTo(MapUtils.getString(b1,"onSaleTime"));
                } else if ("1".equals(MapUtils.getString(params, "onSaleTime"))) {
                    //按照上架时间升序
                    return MapUtils.getString(b1,"onSaleTime").compareTo(MapUtils.getString(b2,"onSaleTime"));
                } else {
                    //默认按照创建时间降序
                    return MapUtils.getString(b2,"createTime").compareTo(MapUtils.getString(b1,"createTime"));
                }
            }
        });

        return storageList;
    }

    /**
     * 批量更新商品库存
     * @param params
     * @return
     */
    @Override
    public void batchUpdateStorage(List<Map<String, Object>> params) {
        if (CollectionUtils.isNotEmpty(params)) {
            for (Map<String,Object> map : params) {
                //修改
                Query modifyQuery = new Query(Criteria.where("account").is(MapUtils.getString(map, "account"))
                        .and("pushShopCommodityId").is(MapUtils.getString(map, "commodityId")));
                Update update = new Update();
                String storageStatus = MapUtils.getString(map, "storageStatus");
                String isDelete = MapUtils.getString(map, "isDelete");
                if (StringUtils.isNotBlank(storageStatus)) {
                    update.set("storageStatus", storageStatus);
                }
                if ("3".equals(storageStatus)) {
                    update.set("onSaleTime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
                }
                if (StringUtils.isNotBlank(isDelete)) {
                    update.set("isDelete", isDelete);
                }
                if ("1".equals(isDelete)) {
                    update.set("deleteTime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
                }
                WriteResult updateWriteResult = mongoTemplate.updateFirst(modifyQuery, update, Map.class, "push_basic_commodity_info");
                logger.debug("modifyQuery:{}, update:{}, updateWriteResult:{}", modifyQuery, update, updateWriteResult);
            }
        }
    }

    /**
     * 更新货架商品价格
     * @param params
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void updateStoragePrice(Map<String, Object> params) {

        //更新价格
        Query modifyQuery = new Query(Criteria.where("account").is(MapUtils.getString(params, "account"))
                .and("pushShopCommodityId").is(MapUtils.getString(params, "commodityId")));
        params.remove("account");
        params.remove("commodityId");
        Update update = new Update();
        for(Map.Entry<String, Object> entry : params.entrySet()) {
            update.set(entry.getKey(), entry.getValue());
        }
        WriteResult updateWriteResult = mongoTemplate.updateFirst(modifyQuery, update, Map.class, "push_marketing_commodity_pricing_info");
        logger.debug("修改价格：modifyQuery:{}, update:{}, updateWriteResult:{}", modifyQuery, update, updateWriteResult);

    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void batchDeleteCommodity(List<Map<String, Object>> params) {

        //删除商品相关信息(包含商品发布五个步骤中存储的表)
        if (CollectionUtils.isNotEmpty(params)) {
            for (Map<String,Object> map : params) {
                //先删除，在保存
                Query deleteQuery = new Query(Criteria.where("account").is(MapUtils.getString(map,"account"))
                        .and("id").is(MapUtils.getString(map,"commodityId")));
                mongoTemplate.remove(deleteQuery, Map.class, "push_shop_commodity_info");

                Query deleteCommon = new Query(Criteria.where("account").is(MapUtils.getString(map,"account"))
                        .and("pushShopCommodityId").is(MapUtils.getString(map,"commodityId")));

                mongoTemplate.remove(deleteQuery, Map.class, "push_basic_commodity_honor_info");
                mongoTemplate.remove(deleteQuery, Map.class, "push_basic_commodity_info");
                mongoTemplate.remove(deleteQuery, Map.class, "push_basic_commodity_intangibleAssets_info");
                mongoTemplate.remove(deleteQuery, Map.class, "push_basic_commodity_location_info");
                mongoTemplate.remove(deleteQuery, Map.class, "push_basic_commodity_place_info");
                mongoTemplate.remove(deleteQuery, Map.class, "push_basic_commodity_production_info");
                mongoTemplate.remove(deleteQuery, Map.class, "push_basic_commodity_qualification_info");
                mongoTemplate.remove(deleteQuery, Map.class, "push_basic_commodity_quality_info");
                mongoTemplate.remove(deleteQuery, Map.class, "push_basic_commodity_safety_standard");
                mongoTemplate.remove(deleteQuery, Map.class, "push_basic_international_commodity_barcode");
                mongoTemplate.remove(deleteQuery, Map.class, "push_commodity_commitment_info");
                mongoTemplate.remove(deleteQuery, Map.class, "push_commodity_traceability_info");
                mongoTemplate.remove(deleteQuery, Map.class, "push_marketing_commodity_after_info");
                mongoTemplate.remove(deleteQuery, Map.class, "push_marketing_commodity_delivery_info");
                mongoTemplate.remove(deleteQuery, Map.class, "push_marketing_commodity_pricing_info");
                mongoTemplate.remove(deleteQuery, Map.class, "push_marketing_commodity_sales_info");
                mongoTemplate.remove(deleteQuery, Map.class, "push_marketing_commodity_warranty_info");
            }
        }
    }

}