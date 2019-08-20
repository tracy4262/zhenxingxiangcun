package com.ovit.nswy.portal.core.sql;

import com.github.xphsc.collect.Maps;
import com.github.xphsc.mutable.Integers;
import com.github.xphsc.util.ObjectUtil;
import com.github.xphsc.util.StringUtil;
import org.apache.ibatis.jdbc.SQL;

import java.util.Map;

/**
 * Created by ${huipei.x} on 2018-1-12.
 */
public class RegulationOfPolicySqlProvider {

    public String getRegulationOfPolicySqlByMap(final Map params) {
        /**
         * 查询类型 1 政策法规  2 政策解读   3 通知公告  4 村务公开 5 党务公开 6 公告公示 7 政务法规 8 政务公开
         */
        Integer policyQueryType=null;
        Map value=Maps.newHashMap();
        if(!isEmpty(params,"policyQueryType")){
            policyQueryType = Maps.getInteger(params, "policyQueryType");
            value=getParams(policyQueryType);
        }
        Integer  queryVideoType=null;
        if(!isEmpty(params,"queryVideoType")){
            queryVideoType = Maps.getInteger(params, "queryVideoType");

        }

        /**
         * 0 视频 1 音频 2 图片 3.文本',
         */
        Integer policyType = Maps.getInteger(params, "policyType");
        String account = Maps.getString(params, "account");
        String keywords="";
        if(!isEmpty(params,"keywords")){
            keywords = Maps.getString(params, "keywords");
        }
        String year="";
        if(!isEmpty(params,"year")){
            year= Maps.getString(params, "year");
        }  String label="";
        if(!isEmpty(params,"label")){
            label= Maps.getString(params, "label");
        }


        String sql = "";
        final String finalLabel = label;
        if(Integers.isNotEmpty(policyQueryType)&&!policyQueryType.equals(1)){
            final Map finalValue = value;
            final Integer finalQueryVideoType = queryVideoType;
            final String finalKeywords = keywords;
            final String finalYear = year;


            /**
             * 村务公开
             */
            if(policyQueryType.equals(4)){

                sql = new SQL() {{
                    SELECT("p.id as id,p.account as account, p.title as title,p.abstracts as content,p.classified_id as classifiedName,p.doc_type as docType,p.image_add as addr,p.abstracts,p.create_time as createTime,p.column_type as columnType,p.information_detail_id as informationDetailId ");
                    FROM("policy as p");
                    INNER_JOIN("policy_detail pd on p.information_detail_id=pd.id");
                    WHERE("p.doc_type='" + Maps.getString(finalValue, "docType") + "'");
                   /* AND();
                    WHERE("p.approve_status=2");*/
                    if (StringUtil.isNotBlank(account)) {
                        AND();
                        WHERE("pd.account=#{account}");
                    }
                    if (StringUtil.isNotBlank(finalKeywords)) {
                        AND();
                        WHERE("p.title like CONCAT('%',#{keywords},'%')");
                    }
                    if (StringUtil.isNotBlank(finalYear)) {
                        AND();
                        WHERE("date_format(p.create_time,'%Y')=#{year}");
                    }
                    if (StringUtil.isNotBlank(finalLabel)) {
                        AND();
                        WHERE("p.label=#{label}");
                    }
                    if(Integers.isNotEmpty(finalQueryVideoType)){
                        if(finalQueryVideoType.equals(0)){
                            AND();
                            WHERE("p.column_type='" + Maps.getString(finalValue, "columnType") + "'");
                            OR();
                            WHERE("p.column_type='视频'");
                        }
                        if(finalQueryVideoType.equals(1)){
                            AND();
                            WHERE("p.column_type='视频'");
                        }
                        if(finalQueryVideoType.equals(2)){
                            AND();
                            WHERE("p.column_type='" + Maps.getString(finalValue, "columnType") + "'");
                        }
                    }
                }}.toString();
            }else{
                /**
                 * 7 政务法规 8 政务公开
                 */
                if(Integers.isNotEmpty(policyType)){
                    if (policyType.equals(3)) {
                        /**
                         * 文本
                         */
                        sql = new SQL() {{
                            SELECT("p.id as id,p.account as account, p.title as title,p.abstracts as content,p.classified_id as classifiedName,p.doc_type as docType,p.image_add as addr,p.abstracts,p.information_detail_id as informationDetailId,p.create_time as createTime ");
                            FROM("policy as p");
                            WHERE("p.doc_type='" + Maps.getString(finalValue, "docType") + "'");
                            AND();
                            WHERE("p.column_type='" + Maps.getString(finalValue, "columnType") + "'");
                            /*AND();
                            WHERE("p.approve_status=2");*/
                            if (StringUtil.isNotBlank(account)) {
                                AND();
                                WHERE("p.account=#{account}");
                            }
                        }}.toString();
                    } else {
                        /**
                         * 0 视频 1 音频 2 图片
                         */
                        sql = new SQL() {{
                            SELECT("p.id as id, p.account as account,p.title as title,p.abstracts as content,p.classified_id as classifiedName,p.doc_type as docType,vm.addr as addr,p.abstracts,vm.describe,p.column_type as columnType,p.information_detail_id as informationDetailId,p.create_time as createTime ");
                            FROM("policy as p");
                            INNER_JOIN("video_img vm on vm.relative_id=p.information_detail_id");
                            WHERE("p.doc_type='" + Maps.getString(finalValue, "docType") + "'");
                            if (StringUtil.isNotBlank(account)) {
                                AND();
                                WHERE("p.account=#{account}");
                            }
                            if (Integers.isNotEmpty(policyType)) {
                                AND();
                                WHERE("vm.type=#{policyType}");
                            }


                        }}.toString();


                    }
                }
                if(Integers.isEmpty(policyType)){
                    sql = new SQL() {{
                        SELECT("p.id as id,p.account as account, p.title as title,p.abstracts as content,p.classified_id as classifiedName,p.doc_type as docType,p.image_add as addr,p.abstracts,p.column_type as columnType,p.information_detail_id as informationDetailId,p.create_time as createTime");
                        FROM("policy as p");
                        INNER_JOIN("policy_detail pd on p.information_detail_id=pd.id");
                        /*AND();
                        WHERE("p.approve_status=2");*/
                        if (StringUtil.isNotBlank(account)) {
                         //   AND();
                            WHERE("pd.account=#{account}");
                        }
                        if (StringUtil.isNotBlank(finalLabel)) {
                            if(!finalLabel.equals("全部")){
                                AND();
                                WHERE("p.label=#{label}");
                            }

                        }
                        ORDER_BY(" p.create_time desc ");
                    }}.toString();
                }

            }

        }else{
            /**
             * 政策法规
             */
            if(Integers.isNotEmpty(policyType)){
                if (policyType.equals(3)) {
                    /**
                     * 文本
                     */
                    sql = new SQL() {{
                        SELECT("p.id as id,p.account as account, p.title as title,p.abstracts as content,p.classified_id as classifiedName,p.doc_type as docType,p.image_add as addr,p.abstracts, p.column_type as columnType");
                        FROM("policy as p");
                        WHERE("p.doc_type='030201'");
                        AND();
                        WHERE("p.column_type='文章'");
                        if (StringUtil.isNotBlank(account)) {
                            AND();
                            WHERE("p.account=#{account}");
                        }
                    }}.toString();
                } else {
                    /**
                     * 0 视频 1 音频 2 图片
                     */
                    sql = new SQL() {{
                        SELECT("p.id as id, p.account as account,p.title as title,p.abstracts as content,p.classified_id as classifiedName,p.doc_type as docType,vm.addr as addr,p.abstracts,vm.describe ,p.column_type as columnType");
                        FROM("policy as p");
                        INNER_JOIN("video_img vm on vm.relative_id=p.information_detail_id");
                        WHERE("p.doc_type='030201'");

                        if (StringUtil.isNotBlank(account)) {
                            AND();
                            WHERE("p.account=#{account}");
                        }
                        if (Integers.isNotEmpty(policyType)) {
                            AND();
                            WHERE("vm.type=#{policyType}");
                        }


                    }}.toString();


                }
            }
            if(Integers.isEmpty(policyType)){
                sql = new SQL() {{
                        SELECT("p.id as id,p.account as account, p.title as title,p.abstracts as content,p.classified_id as classifiedName,p.doc_type as docType,p.image_add as addr,p.abstracts,p.column_type as columnType,p.information_detail_id as informationDetailId,p.create_time as createTime");
                    FROM("policy as p");
                    INNER_JOIN("policy_detail pd on p.information_detail_id=pd.id");
                    WHERE("1 = 1");
                    if (StringUtil.isNotBlank(account)) {
                        AND();
                        WHERE("pd.account=#{account} ");
                    }
                    if (StringUtil.isNotBlank(finalLabel)) {
                        if(!finalLabel.equals("全部")){
                            AND();
                            WHERE("p.label=#{label}");
                        }

                    }
                    ORDER_BY(" p.create_time desc ");
                }}.toString();
            }

        }

        return sql;

    }
    public static boolean isEmpty(Map map, String key) {
        if (map == null){
            return true;
        }
        if (!map.containsKey(key)) {
            return true;
        }
        return ObjectUtil.isEmpty(map.get(key));
    }

    private Map getParams(Integer policyQueryType){
        Map params=Maps.newHashMap();
        switch (policyQueryType){
            case 1:params.put("docType","030201");params.put("columnType","文章");break;
            case 2:params.put("docType","030202");params.put("columnType","文章");break;
            case 3:params.put("docType","030203");params.put("columnType","文章");break;
            case 4:params.put("docType","030204");params.put("columnType","文章");break;
            case 5:params.put("docType","030205");params.put("columnType","文章");break;
            case 6:params.put("docType","030206");params.put("columnType","文章");break;
            case 7:params.put("docType","030207");params.put("columnType","文章");break;
            case 8:params.put("docType","030208");params.put("columnType","文章");break;
            case 9:params.put("docType","030209");params.put("columnType","文章");break;
            case 10:params.put("docType","030210");params.put("columnType","文章");break;default:
        }
        return params;
    }

    private Map getvmParams(Integer policyType){
        Map params=Maps.newHashMap();
        switch (policyType){
            case 0:params.put("vmType","0");break;
            case 1:params.put("vmType","1");break;
            case 2:params.put("vmType","2");break; default:

        }
        return params;
    }

}
