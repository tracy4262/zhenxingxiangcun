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
public class ExpertSqlProvider {

    public String getExpertSqlByMap(final Map params){
      //  Integer userType=Maps.getInteger(params,"userType");
        /**
         * 查询类型 1 个人 2 专家 3 企业 4 商城企业 5 机关 6 乡村
         */
        Integer queryType =null;
        if(!isEmpty(params,"queryType")){
            queryType = Maps.getInteger(params, "queryType");
        }
        String  account= Maps.getString(params, "account");
        String sql="";
        if(Integers.isNotEmpty(queryType)){
            switch (queryType){
                case 1:
                    sql=  new SQL() {{
                        SELECT(" em.id as id ," +
                                "user.login_account as loginAccount ," +
                                "user.display_name as expertName ," +
                                "userinfo.acaTitle as position ,userinfo.acaTitle as positionName ," +
                                "userinfo.job as selfIntroduce ,user.avatar as personalPicture,userinfo.phone,ei.expert_type as expertType,ei.adept_field as adeptField");
                        FROM("expert_employ as  em");
                       // INNER_JOIN("user_contact as uc on uc.account=em.receiver_account");
                        INNER_JOIN("login_user as user on user.login_account=em.receiver_account");
                        INNER_JOIN("expert_info as ei on ei.login_account=em.receiver_account");
                        INNER_JOIN("user_info as userinfo on userinfo.account=user.login_account");
                        WHERE("em.employ_status=1");
                        if (StringUtil.isNotBlank(account)){
                            AND();
                            WHERE("em.sender_account=#{account}");
                        }


                    }}.toString(); break;
                case 2:
                    sql=  new SQL() {{
                        SELECT(" em.id as id ," +
                                "user.login_account as loginAccount ," +
                                "user.display_name as expertName ," +
                                "userinfo.acaTitle as position ,userinfo.acaTitle as positionName ," +
                                "userinfo.job as selfIntroduce ,user.avatar as personalPicture,userinfo.phone,ei.expert_type as expertType,ei.adept_field as adeptField");
                        FROM("expert_employ as  em");
                        //INNER_JOIN("user_contact as uc on uc.account=em.receiver_account");
                        INNER_JOIN("login_user as user on user.login_account=em.receiver_account");
                        INNER_JOIN("expert_info as ei on ei.login_account=em.receiver_account");
                        INNER_JOIN("user_info as userinfo on userinfo.account=user.login_account");
                        WHERE("em.employ_status=1");
                        if (StringUtil.isNotBlank(account)){
                            AND();
                            WHERE("em.sender_account=#{account}");
                        }


                    }}.toString(); break;
                case 3:
                    sql=  new SQL() {{
                        SELECT(" em.id as id ," +
                                "user.login_account as loginAccount ," +
                                "user.display_name as expertName ," +
                                "userinfo.acaTitle as position ,userinfo.acaTitle as positionName ," +
                                "userinfo.job as selfIntroduce ,user.avatar as personalPicture,userinfo.phone,ei.expert_type as expertType,ei.adept_field as adeptField");
                        FROM("expert_employ as  em");
                       // INNER_JOIN("user_contact as uc on uc.account=em.receiver_account");
                        INNER_JOIN("login_user as user on user.login_account=em.receiver_account");
                        INNER_JOIN("expert_info as ei on ei.login_account=em.receiver_account");
                        INNER_JOIN("user_info as userinfo on userinfo.account=user.login_account");
                        WHERE("em.employ_status=1");
                        if (StringUtil.isNotBlank(account)){
                            AND();
                            WHERE("em.sender_account=#{account}");
                        }


                    }}.toString(); break;
                case 4:
                    sql=  new SQL() {{
                        SELECT(" em.id as id ," +
                                "user.login_account as loginAccount ," +
                                "user.display_name as expertName ," +
                                "userinfo.acaTitle as position ,userinfo.acaTitle as positionName ," +
                                "userinfo.job as selfIntroduce ,user.avatar as personalPicture,userinfo.phone,ei.expert_type as expertType,ei.adept_field as adeptField");
                        FROM("expert_employ as  em");
                       // INNER_JOIN("user_contact as uc on uc.account=em.receiver_account");
                        INNER_JOIN("login_user as user on user.login_account=em.receiver_account");
                        INNER_JOIN("expert_info as ei on ei.login_account=em.receiver_account");
                        INNER_JOIN("user_info as userinfo on userinfo.account=user.login_account");
                        WHERE("em.employ_status=1");
                        if (StringUtil.isNotBlank(account)){
                            AND();
                            WHERE("em.sender_account=#{account}");
                        }


                    }}.toString(); break;
                case 5:
                    sql=  new SQL() {{
                        SELECT(" em.id as id ," +
                                "user.login_account as loginAccount ," +
                                "user.display_name as expertName ," +
                                "userinfo.acaTitle as position ,userinfo.acaTitle as positionName ," +
                                "userinfo.job as selfIntroduce ,user.avatar as personalPicture,userinfo.phone,ei.expert_type as expertType,ei.adept_field as adeptField");
                        FROM("expert_employ as  em");
                      //  INNER_JOIN("user_contact as uc on uc.account=em.receiver_account");
                        INNER_JOIN("login_user as user on user.login_account=em.receiver_account");
                        INNER_JOIN("expert_info as ei on ei.login_account=em.receiver_account");
                        INNER_JOIN("user_info as userinfo on userinfo.account=user.login_account");
                        WHERE("em.employ_status=1");
                        if (StringUtil.isNotBlank(account)){
                            AND();
                            WHERE("em.sender_account=#{account}");
                        }


                    }}.toString(); break;
                case 6:
                    sql=  new SQL() {{
                        SELECT(" em.id as id ," +
                                "user.login_account as loginAccount ," +
                                "user.display_name as expertName ," +
                                "userinfo.acaTitle as position ,userinfo.acaTitle as positionName ," +
                                "userinfo.job as selfIntroduce ,user.avatar as personalPicture,userinfo.phone,ei.expert_type as expertType,ei.adept_field as adeptField");
                        FROM("expert_employ as  em");
                        //INNER_JOIN("user_contact as uc on uc.account=em.receiver_account");
                        INNER_JOIN("login_user as user on user.login_account=em.receiver_account");
                        INNER_JOIN("expert_info as ei on ei.login_account=em.receiver_account");
                        INNER_JOIN("user_info as userinfo on userinfo.account=user.login_account");
                        WHERE("em.employ_status=1");
                        if (StringUtil.isNotBlank(account)){
                            AND();
                            WHERE("em.sender_account=#{account}");
                        }


                    }}.toString();break; default:
            }
        }else{
            sql=  new SQL() {{
                SELECT(" em.id as id ," +
                        "user.login_account as loginAccount ," +
                        "user.display_name as expertName ," +
                        "user.id as userId, " +
                        "userinfo.acaTitle as position ,userinfo.acaTitle as positionName ," +
                        "userinfo.job as selfIntroduce ,user.avatar as personalPicture,userinfo.phone,ei.expert_type as expertType,ei.adept_field as adeptField");
                FROM("expert_employ as  em");
               // INNER_JOIN("user_contact as uc on uc.account=em.receiver_account");
                INNER_JOIN("login_user as user on user.login_account=em.receiver_account");
                INNER_JOIN("expert_info as ei on ei.login_account=em.receiver_account");
                INNER_JOIN("user_info as userinfo on userinfo.account=user.login_account");
                WHERE("em.employ_status=1");
                if (StringUtil.isNotBlank(account)){
                    AND();
                    WHERE("em.sender_account=#{account}");
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
