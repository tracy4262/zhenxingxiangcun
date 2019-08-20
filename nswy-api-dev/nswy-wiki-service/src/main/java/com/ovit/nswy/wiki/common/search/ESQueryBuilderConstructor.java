package com.ovit.nswy.wiki.common.search;

import com.github.xphsc.collect.Lists;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;

import java.util.List;

/**
 * Created by ${huipei.x} on 2017-10-19.
 */
public class ESQueryBuilderConstructor {

    private int size = Integer.MAX_VALUE;
    private int from = 0;
    private String asc;
    private String desc;

    //查询条件容器
    private List<ESCriterion> mustCriterions =Lists.newArrayList();
    private List<ESCriterion> shouldCriterions = Lists.newArrayList();
    private List<ESCriterion> mustNotCriterions = Lists.newArrayList();

    //构造builder
    public QueryBuilder listBuilders() {
        int count = mustCriterions.size() + shouldCriterions.size() + mustNotCriterions.size();
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        final QueryBuilder[] queryBuilder = {null};
        if (count >= 1) {
            //must容器
            if (Lists.isNotEmpty(mustCriterions)) {
                mustCriterions.forEach(criterion ->{
                            criterion.listBuilders().forEach(builder -> {
                                queryBuilder[0] = boolQueryBuilder.must(builder);
                            });
                        });
            }

            //should容器
            if (Lists.isNotEmpty(shouldCriterions)) {
                shouldCriterions.forEach(criterion->{
                    criterion.listBuilders().forEach(builder->{
                        queryBuilder[0] = boolQueryBuilder.should(builder);
                    });
                });
            }

            //must not 容器
            if (Lists.isNotEmpty(mustNotCriterions)) {
                mustNotCriterions.forEach(criterion->{
                    criterion.listBuilders().forEach(builder->{
                        queryBuilder[0] = boolQueryBuilder.mustNot(builder);
                    });
                });
            }
            return queryBuilder[0];
        } else {
            return null;
        }
    }

    /**
     * 增加简单条件表达式
     */
    public ESQueryBuilderConstructor must(ESCriterion criterion){
        if(criterion!=null){
            mustCriterions.add(criterion);
        }
        return this;
    }
    /**
     * 增加简单条件表达式
     */
    public ESQueryBuilderConstructor should(ESCriterion criterion){
        if(criterion!=null){
            shouldCriterions.add(criterion);
        }
        return this;
    }
    /**
     * 增加简单条件表达式
     */
    public ESQueryBuilderConstructor mustNot(ESCriterion criterion){
        if(criterion!=null){
            mustNotCriterions.add(criterion);
        }
        return this;
    }


    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getAsc() {
        return asc;
    }

    public void setAsc(String asc) {
        this.asc = asc;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getFrom() {
        return from;
    }

    public void setFrom(int from) {
        this.from = from;
    }

}
