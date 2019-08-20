package com.ovit.nswy.member.common.sql;

import com.github.xphsc.collect.Maps;
import com.github.xphsc.mutable.Integers;
import com.github.xphsc.util.ObjectUtil;
import com.github.xphsc.util.StringUtil;
import com.ovit.nswy.frame.common.util.RegexUtil;
import org.apache.commons.collections.MapUtils;
import org.apache.ibatis.jdbc.SQL;

import java.util.Map;

/**
 * Created by ${huipei.x} on 2017-12-28.
 */
public class UserSqlProvider {

    public String listfollowByMapSql(final Map params)
     {
         String   fromAccount=null;
         String   account=null;
        final Integer  followType= Maps.getInteger(params, "followType");
         if(!isEmpty(params,"account")){
           account= Maps.getString(params, "account");
         }
         if(!isEmpty(params,"fromAccount")){
             fromAccount= Maps.getString(params, "fromAccount");
         }
        String sql="";
        if(followType.equals(1)){
            sql= new SQL() {{
                SELECT("t1.id, t1.account,t1.fans_account as fansAccount,ifnull(ifnull(t2.corp_name,t3.gov_name),t4.display_name) AS realName");
                FROM("fans t1");
                LEFT_OUTER_JOIN("corp_info t2 ON t1.account = t2.login_account");
                LEFT_OUTER_JOIN("gov_info t3 ON t1.account = t3.login_account");
                LEFT_OUTER_JOIN("login_user t4 ON t1.account = t4.login_account");
                WHERE("t1.fans_account = #{account}");

            }}.toString();
        }
        if(followType.equals(2)){
            sql= new SQL() {{
                SELECT("t1.id, t1.account,t1.fans_account as fansAccount,ifnull(ifnull(t2.corp_name,t3.gov_name),t4.display_name) AS realName");
                FROM("fans t1");
                LEFT_OUTER_JOIN("corp_info t2 ON t1.account = t2.login_account");
                LEFT_OUTER_JOIN("gov_info t3 ON t1.account = t3.login_account");
                LEFT_OUTER_JOIN("login_user t4 ON t1.account = t4.login_account");
                WHERE("t1.fans_account = #{account}");
                WHERE("t1.account = #{fromAccount}");

            }}.toString();
        }
         return sql;
    }


    public String getUserByMap(final Map params){
        final Integer  queryType= Maps.getInteger(params, "queryType");
        Integer Id=null;
        String   account="";
        if(!isEmpty(params,"account")){
            account= Maps.getString(params, "account");
        }
        if(!isEmpty(params,"Id")){
            Id= Maps.getInteger(params, "Id");
        }
        String sql="";
        if(queryType.equals(1)){
            sql= new SQL() {{
                SELECT(" * ");
                FROM("login_user");
                WHERE("login_account = #{account}");

            }}.toString();
        }

        if(queryType.equals(2)){
            sql= new SQL() {{
                SELECT(" * ");
                FROM("login_user");
                WHERE("id = #{Id}");

            }}.toString();
        }
        return sql;
    }

    public String listUserByMap(final Map params) {
        Integer userType = null;
        if (!isEmpty(params, "userType")) {
            userType = Maps.getInteger(params, "userType");
        }
        String loginAccount = MapUtils.getString(params,"login_account");
        String account = "";
        if (!isEmpty(params, "account")) {
            account = Maps.getString(params, "account");
        }
        String keywords = "";
        if (!isEmpty(params, "keywords")) {
            keywords = Maps.getString(params, "keywords");
        }
        String areas = "";
        if (!isEmpty(params, "areas")) {
            areas = Maps.getString(params, "areas");
        }
        String species = "";
        if (!isEmpty(params, "species")) {
            species = Maps.getString(params, "species");
        }
        String industrys = "";
        if (!isEmpty(params, "industrys")) {
            industrys = Maps.getString(params, "industrys");
        }
        String products = "";
        if (!isEmpty(params, "products")) {
            products = Maps.getString(params, "products");
        }
        String services = "";
        if (!isEmpty(params, "services")) {
            services = Maps.getString(params, "services");
        }
        String departments = "";
        if (!isEmpty(params, "departments")) {
            departments = Maps.getString(params, "departments");
        }
        Integer followType =null;
        if (!isEmpty(params, "followType")) {
            followType = Maps.getInteger(params, "followType");
        }
        String sql = "";
        final String finalAccount = account;
        final String finalKeywords = keywords;
        final String finalSpecies = species;
        final String finalProducts = products;
        final String finalServices = services;
        final String finalAreas = areas;
        final String finalDepartments = departments;
        final String finalIndustrys = industrys;

        if (Integers.isNotEmpty(userType)) {
            switch (userType) {
                case 0://userType为0查询个人和专家
                    sql= new SQL() {{
                        SELECT(" u.id as id,u.unique_id as  uniqueId,u.login_account as  loginAccount,u.display_name as displayName,u.register_time as registerTime,u.is_Identity_verification as isIdentityVerification,u.user_type as userType,signature as signature,u.avatar as avatar ");
                        FROM("login_user as u");
                        INNER_JOIN("user_info as c on c.account=u.login_account");
                        WHERE("(u.user_type = 0 or u.user_type = 4) and u.login_account not in(select account from fans where fans_account = #{loginAccount})");
                        if(StringUtil.isNotBlank(finalAccount)){
                           AND();
                            WHERE("u.id not in (select id from login_user  where is_friend like CONCAT('%',#{account},'%'))") ;
                            AND();
                            WHERE("u.login_account!=#{account}");
                        }
                        if(StringUtil.isNotBlank(finalKeywords)){
                            if (RegexUtil.regChinese(finalKeywords) == true){
                                AND();
                                WHERE("u.display_name like CONCAT('%',#{keywords},'%')");
                            }else{
                                AND();
                                WHERE("u.login_account like CONCAT('%',#{keywords},'%')");
                            }

                        }
                        if(StringUtil.isNotBlank(finalSpecies)){
                                AND();
                                WHERE("c.species like CONCAT('%',#{species},'%')");
                        }
                        if(StringUtil.isNotBlank(finalAreas)){
                            AND();
                            WHERE("c.addr like CONCAT('%',#{areas},'%')");
                        }
                    }}.toString();
                    break;
                case 1://企业
                    sql= new SQL() {{
                        SELECT(" u.id as id,u.unique_id as  uniqueId,u.login_account as  loginAccount,u.display_name as displayName,u.register_time as registerTime,u.is_Identity_verification as isIdentityVerification,u.user_type as userType,u.signature as signature,u.avatar as avatar,user.corp_name as organizationName ");
                        FROM("login_user u");
                        LEFT_OUTER_JOIN("corp_info as user on user.login_account=u.login_account");
                        WHERE("u.user_type = 1 and user.approve_status = 1  and u.login_account not in(select account from fans where fans_account = #{loginAccount})");
                        if(StringUtil.isNotBlank(finalAccount)){
                         AND();
                            WHERE("u.id not in (select id from login_user  where is_friend like CONCAT('%',#{account},'%'))") ;
                           AND();
                            WHERE("u.login_account!=#{account}");
                        }
                        if(StringUtil.isNotBlank(finalKeywords)){
                            if (RegexUtil.regChinese(finalKeywords) == true){
                                AND();
                                WHERE("u.display_name like CONCAT('%',#{keywords},'%')");
                               }else{
                                AND();
                                WHERE("u.login_account like CONCAT('%',#{keywords},'%')");
                            }

                        }

                        if(StringUtil.isNotBlank(finalSpecies)){
                                AND();
                                WHERE("user.related_species like CONCAT('%',#{species},'%')");
                        }
                        if(StringUtil.isNotBlank(finalProducts)){
                            AND();
                            WHERE("user.related_product like CONCAT('%',#{species},'%')");
                        }
                        if(StringUtil.isNotBlank(finalServices)){
                            AND();
                            WHERE("user.related_service like CONCAT('%',#{species},'%')");
                        }
                        if(StringUtil.isNotBlank(finalAreas)){
                            AND();
                            WHERE("user.location like CONCAT('%',#{areas},'%')");
                        }
                        if(StringUtil.isNotBlank(finalIndustrys)){
                            AND();
                            WHERE("user.industry_type like CONCAT('%',#{industrys},'%')");
                        }


                    }}.toString();
                    break;
                case 3://userType为3查询机关乡村
                    sql= new SQL() {{
                        SELECT(" u.id as id,u.unique_id as  uniqueId,u.login_account as  loginAccount,u.display_name as displayName,u.register_time as registerTime,u.is_Identity_verification as isIdentityVerification,u.user_type as userType,u.signature as signature,u.avatar as avatar ,g.gov_name as organizationName");
                        FROM("login_user u");
                        LEFT_OUTER_JOIN("user_info as userinfo on userinfo.account=u.login_account");
                        LEFT_OUTER_JOIN("gov_info as g on g.login_account=u.login_account");
                        WHERE("(u.user_type = 3 or u.user_type = 5) and g.approve_status = 1 and u.login_account not in(select account from fans where fans_account = #{loginAccount})");
                        if(StringUtil.isNotBlank(finalAccount)){
                            AND();
                            WHERE("u.id not in (select id from login_user  where is_friend like CONCAT('%',#{account},'%'))") ;
                            AND();
                            WHERE("u.login_account!=#{account}");
                        }
                        if(StringUtil.isNotBlank(finalKeywords)){
                            if (RegexUtil.regChinese(finalKeywords) == true){
                                AND();
                                WHERE("u.display_name like CONCAT('%',#{keywords},'%')");
                            }else{
                                AND();
                                WHERE("u.login_account like CONCAT('%',#{keywords},'%')");
                            }

                        }
                        if(StringUtil.isNotBlank(finalAreas)){
                            AND();
                            WHERE("userinfo.addr like CONCAT('%',#{areas},'%')");
                        }
                        if(StringUtil.isNotBlank(finalDepartments)){
                            AND();
                            WHERE("g.gov_type like CONCAT('%',#{departments},'%')");
                        }
                    }}.toString();
                    break;
                case 4://专家
                    sql= new SQL() {{
                        SELECT(" u.id as id,u.unique_id as  uniqueId,u.login_account as  loginAccount,u.display_name as displayName,u.register_time as registerTime,u.is_Identity_verification as isIdentityVerification,u.user_type as userType,signature as signature,u.avatar as avatar ");
                        FROM("login_user as u");
                        LEFT_OUTER_JOIN("user_info as c on c.account=u.login_account");
                        LEFT_OUTER_JOIN("expert_info t ON u.login_account = t.login_account");
                        WHERE("(u.user_type = 0 or u.user_type = 4) and t1.approve_status = 1 and u.login_account not in(select account from fans where fans_account = #{loginAccount})");
                        if(StringUtil.isNotBlank(finalAccount)){
                           AND();
                            WHERE("u.id not in (select id from login_user  where is_friend like CONCAT('%',#{account},'%'))") ;
                            AND();
                            WHERE("u.login_account!=#{account}");
                        }
                        if(StringUtil.isNotBlank(finalKeywords)){
                            if (RegexUtil.regChinese(finalKeywords) == true){
                                AND();
                                WHERE("u.display_name like CONCAT('%',#{keywords},'%')");
                            }else{
                                AND();
                                WHERE("u.login_account like CONCAT('%',#{keywords},'%')");
                            }

                        }
                        if(StringUtil.isNotBlank(finalSpecies)){
                                AND();
                                WHERE("c.species like CONCAT('%',#{species},'%')");
                        }
                        if(StringUtil.isNotBlank(finalAreas)){
                            AND();
                            WHERE("c.addr like CONCAT('%',#{areas},'%')");
                        }
                    }}.toString();
                    break;
               /* case 5://乡村
                    sql= new SQL() {{
                        SELECT(" u.id as id,u.unique_id as  uniqueId,u.login_account as  loginAccount,u.display_name as displayName,u.register_time as registerTime,u.is_Identity_verification as isIdentityVerification,u.user_type as userType,u.signature as signature,u.avatar as avatar ");
                        FROM("login_user u");
                        LEFT_OUTER_JOIN("user_info as userinfo on userinfo.account=u.login_account");
                        LEFT_OUTER_JOIN("gov_info as g on g.login_account=u.login_account");
                        WHERE("u.user_type = 5");
                        if(StringUtil.isNotBlank(finalAccount)){
                            AND();
                            WHERE("u.id not in (select id from login_user  where is_friend like CONCAT('%',#{account},'%'))") ;
                            AND();
                            WHERE("u.login_account!=#{account}");
                        }
                        if(StringUtil.isNotBlank(finalKeywords)){
                            if (RegexUtil.regChinese(finalKeywords) == true){
                                AND();
                                WHERE("u.display_name like CONCAT('%',#{keywords},'%')");
                            }else{
                                AND();
                                WHERE("u.login_account like CONCAT('%',#{keywords},'%')");
                            }

                        }
                        if(StringUtil.isNotBlank(finalAreas)){
                            AND();
                            WHERE("userinfo.addr like CONCAT('%',#{areas},'%')");
                        }
                        if(StringUtil.isNotBlank(finalDepartments)){
                            AND();
                            WHERE("g.gov_type like CONCAT('%',#{departments},'%')");
                        }
                    }}.toString();
                    break;*/
            }

        }else{
            sql= new SQL() {{
                SELECT(" u.id as id,u.unique_id as  uniqueId,u.login_account as  loginAccount,u.display_name as displayName,u.register_time as registerTime,u.is_Identity_verification as isIdentityVerification,u.user_type as userType,u.signature as signature,u.avatar as avatar ");
                FROM("login_user u");
                LEFT_OUTER_JOIN("user_info as c on c.account=u.login_account");
                WHERE("u.login_account not in(select account from fans where fans_account = #{loginAccount})");
                if(StringUtil.isBlank(finalSpecies)&&StringUtil.isNotBlank(finalAreas)){
                    AND();
                    WHERE("c.addr like CONCAT('%',#{areas},'%')");
                }
                if(StringUtil.isBlank(finalAreas)&&StringUtil.isNotBlank(finalSpecies)){
                    AND();
                    WHERE("c.species like CONCAT('%',#{species},'%')");
                }
                if(StringUtil.isNotBlank(finalAreas)&&StringUtil.isNotBlank(finalSpecies)){
                    AND();
                    WHERE("c.addr like CONCAT('%',#{areas},'%')");
                    AND();
                    WHERE("c.species like CONCAT('%',#{species},'%')");
                }
                if(StringUtil.isNotBlank(finalAccount)){
                    AND();
                    WHERE("u.id not in (select id from login_user  where is_friend like CONCAT('%',#{account},'%'))") ;
                    AND();
                    WHERE("u.login_account !=#{account}");
                }
                if(StringUtil.isNotBlank(finalKeywords)){
                    if (RegexUtil.regChinese(finalKeywords) == true){
                        AND();
                        WHERE("u.display_name like CONCAT('%',#{keywords},'%')");
                    }else{
                        AND();
                        WHERE("u.login_account like CONCAT('%',#{keywords},'%')");
                    }

                }
            }}.toString();
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
}
