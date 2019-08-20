package com.ovit.nswy.wiki.common.search;

import org.elasticsearch.index.query.QueryBuilder;

import java.util.List;

/**
 * Created by ${huipei.x} on 2017-10-19.
 */
public interface  ESCriterion {

    public enum Operator {
        TERM, TERMS, RANGE, FUZZY, QUERY_STRING, MISSING,MATCH,LIKE,PREFIX,RANGEOFGT,RANGEOFOFLTGT,EXITES
    }
    public enum MatchMode {
        START, END, ANYWHERE
    }
    public enum Projection {
        MAX, MIN, AVG, LENGTH, SUM, COUNT
    }
    public List<QueryBuilder> listBuilders();
}
