package com.ovit.nswy.wiki.common.search;


import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;

import java.util.Collection;


/**
 * Created by ${huipei.x} on 2017-10-19.
 */
public class ESQueryBuilder {
    private String fieldName;       //属性名
    private Object value;           //对应值
    private Collection<Object> values;           //对应值
    private ESCriterion.Operator operator;      //计算符
    private Object from;
    private Object to;
    private Object beginValue;
    private Object endValue;
    protected ESQueryBuilder(String fieldName, Object value, ESCriterion.Operator operator) {
        this.fieldName = fieldName;
        this.value = value;
        this.operator = operator;
    }

    protected ESQueryBuilder(String value, ESCriterion.Operator operator) {
        this.value = value;
        this.operator = operator;
    }

    protected ESQueryBuilder(String fieldName, Collection<Object> values) {
        this.fieldName = fieldName;
        this.values = values;
        this.operator = ESCriterion.Operator.TERMS;
    }

    protected ESQueryBuilder(String fieldName, Object from, Object to) {
        this.fieldName = fieldName;
        this.from = from;
        this.to = to;
        this.operator = ESCriterion.Operator.RANGE;
    }

    protected ESQueryBuilder(String fieldName, Object beginValue,Object endValue, ESCriterion.Operator operator) {
        this.fieldName = fieldName;
        this.beginValue=beginValue;
        this.endValue=endValue;
        this.operator = operator;
    }
    public QueryBuilder toBuilder() {
       QueryBuilder qb = null;
        switch (operator) {
            case TERM:
                qb = QueryBuilders.termQuery(fieldName, value);
                break;
            case TERMS:
                qb = QueryBuilders.termsQuery(fieldName, values);
                break;
            case RANGE:
                qb = QueryBuilders.rangeQuery(fieldName).from(from).to(to).includeLower(true).includeUpper(true);
                break;
            case FUZZY:
                qb = QueryBuilders.fuzzyQuery(fieldName, value);
                break;
            case MATCH:
                qb = QueryBuilders.matchQuery(fieldName, value);
                break;
            case  LIKE:
                qb=QueryBuilders.wildcardQuery(fieldName, value.toString());
                break;
            case  PREFIX:
                qb=QueryBuilders.prefixQuery(fieldName, value.toString());
                break;
            case RANGEOFGT:
                    qb=QueryBuilders.rangeQuery(fieldName).gt(value);
                break;
            case RANGEOFOFLTGT:
                    qb=QueryBuilders.rangeQuery(fieldName).lt(beginValue).gt(endValue);

                break;
            case EXITES:
                qb=QueryBuilders.existsQuery(fieldName);

                break;
            case QUERY_STRING:
                qb = QueryBuilders.queryStringQuery(value.toString());
                break;
               default:
        }


        return qb;
    }
}
