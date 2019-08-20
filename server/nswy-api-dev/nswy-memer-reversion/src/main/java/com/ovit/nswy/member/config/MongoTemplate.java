package com.ovit.nswy.member.config;

import com.mongodb.BasicDBObject;
import com.mongodb.Block;
import com.mongodb.client.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * DESCRIPTION:
 *
 * @see <a href="https://github.com/mongodb/mongo-java-driver/blob/master/driver/src/examples/primer/QueryPrimer.java">QueryPrimer examples</a>
 * @see <a href="http://farenda.com/mongodb/mongodb-filtering-sorting-and-projections/">MongoDB 3 – filtering, sorting and projections</a>
 **/
@Component
public class MongoTemplate {

    private static final Logger logger = LogManager.getLogger(MongoTemplate.class);

    @Resource
    private MongoDatabase mongoDatabase;

    /**
     * 根据表名称返回可用的自增id
     * 查询表结果为空时_id为0
     * 反之_id为集合内最大_id+1
     * @param collectionName 表名称
     * @return
     */
    public int increaseId(String collectionName) {
        //初始化_id为0
        int increaseId = 0;
        MongoCollection<Document> mongoCollection = mongoDatabase.getCollection(collectionName);
        //倒序查询最后一条记录
        FindIterable<Document> findIterable = mongoCollection.find().sort(new BasicDBObject("$natural",-1)).limit(1);
        //获取游标
        MongoCursor<Document> mongoCursor = findIterable.iterator();
        //查询结果为空,设置_id值为0,
        //反之,设置_id为集合内最大的_id+1
        if (mongoCursor.hasNext()) {
            increaseId =  Integer.parseInt(mongoCursor.next().get("_id").toString()) + 1;
        }
        logger.debug("collectionName:{}, increaseId:{}", collectionName, increaseId);
        return increaseId;
    }

    /**
     * 根据传入集合名、过滤条件查询相关记录 按照指定字段排序
     * 返回List<Map<String, Object>>
     * 调用示例：
     * filteringOperator("social", Filters.or(Filters.eq("name", "WOJ"),Filters.eq("name", "RHS")), Sorts.descending("﻿create_time"))
     * 模糊查询
     * filteringOperator("publish", Filters.regex("content",  java.util.regex.Pattern.compile("发布")), Sorts.descending("﻿create_time"))
     * 起止时间查询 大于等于 小于等于
     * filteringOperator("publish", Filters.and(Filters.gte("create_time", "2018-04-23 00:00:00"),Filters.lte("create_time", "2018-04-24 00:00:00")), Sorts.descending("﻿create_time"))
     * @param collectionName
     * @param filter
     * @param sort
     * @return
     */
    public List<Map<String, Object>> filteringOperator(String collectionName, Bson filter, Bson sort) {
        FindIterable<Document> findIterable = mongoDatabase.getCollection(collectionName)
                .find(filter)
                .sort(sort);
        List<Map<String, Object>> resultList = new ArrayList<Map<String ,Object>>();
        for (Document document : findIterable) {
            Map<String, Object> result = new HashMap<String, Object>();
            result.putAll(document);
            resultList.add(result);
        }
        logger.trace("filteringOperator collectionName:{}, resultList:{}", collectionName, resultList);
        logger.debug("filteringOperator collectionName:{}, resultList.size:{}", collectionName, resultList.size());
        return resultList;
    }

    /**
     * 根据传入集合名查询全表记录 按照指定字段排序
     * 返回List<Map<String, Object>>
     * 调用示例：
     * findAll("social", Sorts.descending("﻿create_time"))
     * @param collectionName
     * @param sort
     * @return
     */
    public List<Map<String, Object>> findAll(String collectionName, Bson sort) {
        FindIterable<Document> findIterable = mongoDatabase.getCollection(collectionName)
                .find()
                .sort(sort);
        List<Map<String, Object>> resultList = new ArrayList<Map<String ,Object>>();
        for (Document document : findIterable) {
            Map<String, Object> result = new HashMap<String, Object>();
            result.putAll(document);
            resultList.add(result);
        }
        logger.trace("findAll collectionName:{}, resultList:{}", collectionName, resultList);
        logger.debug("findAll collectionName:{}, resultList.size:{}", collectionName, resultList.size());
        return resultList;
    }

    /**
     * 根据传入集合名、过滤条件查询指定的相关单条记录
     * 返回Map<String, Object>
     * 调用示例：
     * filteringOperator("social", Filters.or(Filters.eq("name", "WOJ"),Filters.eq("name", "RHS")))
     * 模糊查询
     * filteringOperator("publish", Filters.regex("content",  java.util.regex.Pattern.compile("发布")))
     * 起止时间查询 大于等于 小于等于
     * filteringOperator("publish", Filters.and(Filters.gte("create_time", "2018-04-23 00:00:00"),Filters.lte("create_time", "2018-04-24 00:00:00")))
     * @param collectionName
     * @param filter
     * @return
     */
    public Map<String, Object> filteringFirstOperator(String collectionName, Bson filter) {
        Document document = mongoDatabase.getCollection(collectionName)
                .find(filter)
                .first();
        Map<String, Object> result = new HashMap<String, Object>();
        if(null==document || document.size()==0){
            return null;
        }
        result.putAll(document);
        logger.trace("filteringFirstOperator collectionName:{}, result:{}", collectionName, result);
        logger.debug("filteringFirstOperator collectionName:{}", collectionName);
        return result;
    }

    /**
     * 根据表名插入单条数据
     * 调用示例：
     * insertOne("social", new Document("username","wh").append("age", "22").append("location", new Document("city", "hangzhou").append("x", 100).append("y","200")))
     * @param collectionName
     * @param document
     */
    public void insertOne(String collectionName, Document document) {
        MongoCollection<Document> mongoCollection = mongoDatabase.getCollection(collectionName);
        mongoCollection.insertOne(document);
        logger.debug("insertOne collectionName:{}, document:{}", collectionName, document);
    }

    /**
     * 根据表名删除多条数据
     * 调用示例:
     * deleteMany("social", Filters.or(Filters.eq("name", "WOJ"),Filters.eq("name", "RHS")))
     * filter:
     * Filters.or(Filters.eq("name", "WOJ"),Filters.eq("name", "RHS"))
     * logicalAnd: new Document("cuisine", "Italian").append("address.zipcode", "10075")<br>
     * logicalOr: new Document("$or", asList(new Document("cuisine", "Italian"), new Document("address.zipcode", "10075")))<br>
     * @param collectionName
     * @param filter
     */
    public void deleteMany(String collectionName, Bson filter) {
        MongoCollection<Document> mongoCollection = mongoDatabase.getCollection(collectionName);
        mongoCollection.deleteMany(filter);
        logger.debug("deleteMany collectionName:{}, filter:{}", collectionName, filter);
    }

    /**
     * 根据表名更新数据
     * 调用示例:
     * updateOne("social", Filters.eq("name", "WOJ"), new Document("$set", new Document("i", 110)))
     * @param collectionName
     * @param filter
     * @param update
     */
    public void updateOne(String collectionName, Bson filter, Bson update) {
        MongoCollection<Document> mongoCollection = mongoDatabase.getCollection(collectionName);
        mongoCollection.updateOne(filter, update);
        logger.debug("updateOne collectionName:{}, filter:{}, update:{}", collectionName, filter, update);
    }

    /**
     * 统计总数
     *
     * @param collectionName 表名称
     * @return
     */
    public long count(String collectionName) {
        return mongoDatabase.getCollection(collectionName).count();
    }

    /**
     * 带条件统计总数
     *
     * @param collectionName 表名称
     * @param filter 查询条件
     * @return
     */
    public long countByFilter(String collectionName, Bson filter) {
        if( null == filter ){
            return count(collectionName);
        }else {
            return mongoDatabase.getCollection(collectionName).count(filter);
        }
    }

    /**
     * 字段排序取限定条数记录
     * 调用示例：
     * sorting("social", 3, Sorts.descending("score"))
     * 返回数据示例：
     * Name: TYH, score: 962
     Name: QGV, score: 814
     Name: WOJ, score: 686
     * @param collectionName 表名称
     * @param limit 读取的记录条数
     * @param sort 查询的排序条件
     */
    public List<Map<String, Object>> sorting(String collectionName, int limit, Bson sort) {
        FindIterable<Document> sortingDocument = mongoDatabase.getCollection(collectionName)
                .find()
                .limit(limit)
                .sort(sort);
        logger.debug("collectionName:{}, limit:{}, sort:{}, result:{}", collectionName, limit, sort, sortingDocument);

        final List<Map<String, Object>> resultList = new ArrayList<Map<String ,Object>>();
        sortingDocument.forEach(new Block<Document>() {
            @Override
            public void apply(final Document document) {
                if (logger.isTraceEnabled()) {
                    logger.trace("document: {]", document.toJson());
                }
                Map<String, Object> result = new HashMap<String, Object>();
                result.putAll(document);
                resultList.add(result);
            }
        });
        logger.trace("result: {}", resultList);
        logger.debug("result size: {}", resultList.size());
        return resultList;
    }

    public void insert(String collectionName, List<Document> documents) {
        MongoCollection<Document> collection = mongoDatabase.getCollection(collectionName);
        collection.insertMany(documents);

        logger.debug("collectionName:{}, documentst:{}", collectionName, documents);
    }

    /**
     * 字段排序取限定条数且限定字段记录
     * 调用示例：
     * projection("social", 3, Sorts.descending("score"), Projections.include("score"))
     * 返回示例：
     * Name: null, score: 962
     * @param collectionName 表名称
     * @param limit 读取的记录条数
     * @param sort 查询的排序条件
     * @param projection 返回字段限定
     */
    private void projection(String collectionName, int limit, Bson sort, Bson projection) {
        FindIterable<Document> projectDocument = mongoDatabase.getCollection(collectionName)
                .find()
                .limit(limit)
                .sort(sort)
                .projection(projection);
        logger.debug("collectionName:{}, limit:{}, sort:{}, projection:{}, result:{}", collectionName, limit, sort, projection, projectDocument);
    }

    /**
     * 统计总数
     *
     * @param collectionName 表名称
     * @param filter logicalAnd: new Document("cuisine", "Italian").append("address.zipcode", "10075")<br>
     *               logicalOr: new Document("$or", asList(new Document("cuisine", "Italian"), new Document("address.zipcode", "10075")))<br>
     * @return
     */
    public long count(String collectionName, Bson filter) {
        return mongoDatabase.getCollection(collectionName).count(filter);
    }


    /**
     * 分页查询
     *
     * @param collectionName
     * @param filter  logicalAnd: new Document("cuisine", "Italian").append("address.zipcode", "10075")<br>
     *               logicalOr: new Document("$or", asList(new Document("cuisine", "Italian"), new Document("address.zipcode", "10075")))<br>
     * @param sort Sorts.descending("score")
     * @param pageIndex 设置为1开头，第1...n页
     * @param pageSize
     * @return
     */
    public List<Map<String, Object>> query(String collectionName, Bson filter, Bson sort, int pageIndex, int pageSize) {
        if( null == filter ){
            return query(collectionName,sort,pageIndex,pageSize);
        }else{
            logger.debug("collection: {}, filter: {}, sort: {}, pageIndex: {}, pageSize: {}", collectionName, filter, sort, pageIndex, pageSize);

            FindIterable<Document> iterable = mongoDatabase.getCollection(collectionName)
                    .find(filter)
                    .sort(sort)
                    .skip((pageIndex - 1) * pageSize)
                    .limit(pageSize);
            final List<Map<String, Object>> resultList = new ArrayList<Map<String ,Object>>();
            iterable.forEach(new Block<Document>() {
                @Override
                public void apply(final Document document) {
                    if (logger.isTraceEnabled()) {
                        logger.trace("document: {]", document.toJson());
                    }
                    Map<String, Object> result = new HashMap<String, Object>();
                    result.putAll(document);
                    resultList.add(result);
                }
            });
            logger.trace("result: {}", resultList);
            logger.debug("result size: {}", resultList.size());
            return resultList;
        }
    }

    /**
     * 分页查询
     *
     * @param collectionName
     * @param sort Sorts.descending("score")
     * @param pageIndex 设置为1开头，第1...n页
     * @param pageSize
     * @return
     */
    public List<Map<String, Object>> query(String collectionName, Bson sort, int pageIndex, int pageSize) {
        logger.debug("collection: {}, sort: {}, pageIndex: {}, pageSize: {}", collectionName, sort, pageIndex, pageSize);

        FindIterable<Document> iterable = mongoDatabase.getCollection(collectionName)
                .find()
                .sort(sort)
                .skip((pageIndex - 1) * pageSize)
                .limit(pageSize);
        final List<Map<String, Object>> resultList = new ArrayList<Map<String ,Object>>();

        iterable.forEach(new Block<Document>() {
            @Override
            public void apply(final Document document) {
                if (logger.isTraceEnabled()) {
                    logger.trace("document: {]", document.toJson());
                }
                Map<String, Object> result = new HashMap<String, Object>();
                result.putAll(document);
                resultList.add(result);
            }
        });
        logger.trace("result: {}", resultList);
        logger.debug("result size: {}", resultList.size());
        return resultList;
    }

    /**
     * 有where条件，有排序的查询全部
     *
     * @param collectionName
     * @param filter logicalAnd: new Document("cuisine", "Italian").append("address.zipcode", "10075")<br>
     *               logicalOr: new Document("$or", asList(new Document("cuisine", "Italian"), new Document("address.zipcode", "10075")))<br>
     * @param sort Sorts.descending("score")
     * @return
     */
    public List<Map<String, Object>> query(String collectionName, Bson filter, Bson sort) {
        logger.debug("collection: {}, filter: {}, sort: {}", collectionName, filter, sort);

        FindIterable<Document> iterable = mongoDatabase.getCollection(collectionName)
                .find(filter)
                .sort(sort);
        final List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();

        iterable.forEach(new Block<Document>() {
            @Override
            public void apply(final Document document) {
                if (logger.isTraceEnabled()) {
                    logger.trace("document: {]", document.toJson());
                }
                Map<String, Object> result = new HashMap<String, Object>();
                result.putAll(document);
                resultList.add(result);
            }
        });
        logger.trace("result: {}", resultList);
        logger.debug("result size: {}", resultList.size());
        return resultList;
    }

    /**
     * groupby集合统计
     *
     * @param collectionName 表名称
     * @param pipeline <pre>
     * import static java.util.Arrays.asList;
     *  asList(
     *    new Document("$match", new Document("borough", "Queens").append("cuisine", "Brazilian")),//查询条件(可选)
     *    new Document("$group", new Document("_id", "$address.zipcode").append("totalnum", new Document("$sum", 1)))) //groupby<pre>
     * @return
     */
    public Map<String, Object> group(String collectionName, List<? extends Bson> pipeline) {
        logger.debug("collection: {}, pipeline: {}", collectionName, pipeline);
        AggregateIterable<Document> iterable = mongoDatabase.getCollection(collectionName).aggregate(pipeline);

        final Map<String, Object> resultMap = new HashMap<String ,Object>();

        iterable.forEach(new Block<Document>() {
            @Override
            public void apply(final Document document) {
                if (logger.isTraceEnabled()) {
                    logger.trace("document: {]", document.toJson());
                }
                resultMap.putAll(document);
            }
        });
        logger.debug("result: {}", resultMap);
        return resultMap;
    }

    /**
     * 聚合查询
     * where groupby 统计
     * 命令行示例：
     * db.getCollection('publish').aggregate([
     {$match:  {$or: [{mobile:"13659875634"}, {mobile:"13554413665"}]}},
     {$group : {_id : "$mobile", num_tutorial : {$sum : 1}}}
     ])
     * 调用示例:
     * aggregate(asList(Aggregates.match(Filters.or(Filters.eq("mobile", "13659875634"), Filters.eq("mobile","13554413665"))),Aggregates.group("$mobile", Accumulators.sum("total", 1L))))
     * aggregate(Collections.singletonList(Aggregates.group("$mobile", Accumulators.sum("total", 1L))))
     *
     * table join
     * 命令行示例
     * db.getCollection('publish').aggregate([
     {
     $lookup:
     {
     from: "publish_evaluate",//需要关联的表
     localField: "_id",//主表需要关联的键
     foreignField: "publish_id",//关联表的matching key
     as: "evaluate_docs"//对应的外键集合的数据 可能一对多
     }
     },
     { $match : { mobile : "13659875634"} },
     { $skip : 0 },
     { $limit : 10 },
     { $sort : { create_time : -1 } }
     ﻿  //{ $group: { _id: null, count: { $sum: 1 } } }
     ])
     * 调用示例:
     * aggregate(asList(Aggregates.lookup("publish_evaluate", "_id", "publish_id", "evaluate_docs"),
     Aggregates.match(Filters.eq("mobile", "13659875634")),
     Aggregates.skip(0),
     Aggregates.limit(10),
     Aggregates.sort(Sorts.descending("create_time"))))
     *
     * 命令行示例
     * ﻿db.getCollection('publish').aggregate([
     {$project : { mobile: "$mobile" , create_time : {$substr: ["$create_time", 0, 10] }}},
     {$group :
     {
     _id : { mobile: "$mobile" , create_time: "$create_time"},
     num_tutorial : {$sum : 1}
     }
     }
     ])

     * 调用示例:
     * aggregate(asList(
     new Document("$project", new Document("mobile", 1).append("create_time", new Document("$substr", Arrays.asList("$create_time", 0, 10)))),
     Aggregates.group(new Document("mobile", "$mobile").append("create_time", "$create_time"), Accumulators.sum("total", 1L))
     ))

     命令行事例
     db.user.aggregate(
     [
     { $project: { nickName: 1, total:
     { $add: [ "$point_qr_code", "$point_publish","$point_succ" ] } } }
     ]
     )
     调用事例


     * @param collectionName
     * @param bsonList
     */
    public List<Map<String, Object>> aggregate(String collectionName, List<Bson> bsonList) {
        MongoCollection<Document> mongoCollection = mongoDatabase.getCollection(collectionName);
        AggregateIterable<Document> aggregateIterable = mongoCollection.aggregate(bsonList);
        List<Map<String, Object>> resultList = new ArrayList<Map<String ,Object>>();
        for (Document document : aggregateIterable) {
            Map<String, Object> result = new HashMap<String, Object>();
            result.putAll(document);
            resultList.add(result);
        }
        logger.trace("aggregate collectionName:{}, resultList:{}", collectionName, resultList);
        logger.debug("aggregate collectionName:{}, resultList.size:{}", collectionName, resultList.size());
        return resultList;
    }

    /**
     * 聚合查询统计总数
     *
     * table join
     * 命令行示例
     * db.getCollection('publish').aggregate([
     {
     $lookup:
     {
     from: "publish_evaluate",//需要关联的表
     localField: "_id",//主表需要关联的键
     foreignField: "publish_id",//关联表的matching key
     as: "evaluate_docs"//对应的外键集合的数据 可能一对多
     }
     },
     { $match : { mobile : "13659875634"} },
     ﻿{ $group: { _id: null, count: { $sum: 1 } } }
     ])
     * 调用示例:
     * aggregate(asList(Aggregates.lookup("publish_evaluate", "_id", "publish_id", "evaluate_docs"),
     Aggregates.match(Filters.eq("mobile", "13659875634")),
     Aggregates.group(null, Accumulators.sum("total", 1L))))
     * @param collectionName 表名称
     * @param bsonList 查询条件
     * @return
     */
    public long countByAggregate(String collectionName, List<Bson> bsonList) {
        MongoCollection<Document> mongoCollection = mongoDatabase.getCollection(collectionName);
        AggregateIterable<Document> aggregateIterable = mongoCollection.aggregate(bsonList);
        long counts = aggregateIterable.first().getLong("total");
        logger.debug("aggregate collectionName:{}, counts:{}", collectionName, counts);
        return counts;
    }
}
