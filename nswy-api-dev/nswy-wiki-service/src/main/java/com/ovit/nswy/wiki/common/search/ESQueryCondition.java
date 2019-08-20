package com.ovit.nswy.wiki.common.search;

import com.github.xphsc.collect.Lists;
import org.elasticsearch.index.query.QueryBuilder;

import java.util.Collection;
import java.util.List;

/**
 * Created by ${huipei.x} on 2017-10-19.
 */
public class ESQueryCondition implements ESCriterion {

    private List<QueryBuilder> list = Lists.newArrayList();


    public ESQueryCondition term(String field, Object value) {
        list.add(new ESQueryBuilder(field, value, Operator.TERM).toBuilder());
        return this;
    }

    public ESQueryCondition match(String field, Object value) {
        list.add(new ESQueryBuilder(field, value, Operator.MATCH).toBuilder());
        return this;
    }

    public ESQueryCondition like(String field, Object value) {
        list.add(new ESQueryBuilder(field, value, Operator.LIKE).toBuilder());
        return this;
    }

    public ESQueryCondition prefix(String field, Object value) {
        list.add(new ESQueryBuilder(field, value, Operator.PREFIX).toBuilder());
        return this;
    }


    public ESQueryCondition terms(String field, Collection<Object> values) {
        list.add(new ESQueryBuilder(field, values,Operator.TERMS).toBuilder());
        return this;
    }


    public ESQueryCondition fuzzy(String field, Object value) {
        list.add(new ESQueryBuilder(field, value, Operator.FUZZY).toBuilder());
        return this;
    }


    public ESQueryCondition range(String field, Object from, Object to) {
        list.add(new ESQueryBuilder(field, from, to).toBuilder());
        return this;
    }

    public ESQueryCondition rangeOfGT(String field, Object value) {
            list.add(new ESQueryBuilder(field, value,Operator.RANGEOFGT).toBuilder());
        return this;
    }

    public ESQueryCondition rangeOfLTGT(String field, Object beginValue,Object endValue) {
        list.add(new ESQueryBuilder(field, beginValue,endValue,Operator.RANGEOFGT).toBuilder());
        return this;
    }


    public ESQueryCondition exists(String field) {
        list.add(new ESQueryBuilder(field,Operator.EXITES).toBuilder());
        return this;
    }
    
    public ESQueryCondition queryString(String queryString) {
        list.add(new ESQueryBuilder(queryString, Operator.QUERY_STRING).toBuilder());
        return this;
    }

    @Override
    public List<QueryBuilder> listBuilders() {
        return list;
    }
}
