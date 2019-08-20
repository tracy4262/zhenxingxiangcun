package com.ovit.nswy.portal.core.sql;
import com.github.xphsc.collect.Maps;
import com.github.xphsc.mutable.Integers;
import com.github.xphsc.util.ObjectUtil;
import com.github.xphsc.util.StringUtil;
import org.apache.ibatis.jdbc.SQL;
import java.util.Map;

/**
 * Created by ${huipei.x} on 2018-1-25.
 */
public class WebsiteSettingSqlProvider {

    public String getWebsiteSqlByMap(final Map params){
        /**
         * 查询类型 1 个人 2 专家 3 企业 4 商城企业 5 机关 6 乡村
         */

        Integer queryType =null;
        if(!isEmpty(params,"queryType")){
            queryType = Maps.getInteger(params, "queryType");
        }
        String  account= Maps.getString(params, "account");

        String sql="";
        if(Integers.isNotEmpty(queryType)) {
            switch (queryType) {
                case 1:
                    sql = new SQL() {{
                        SELECT(" w.id as id ,w.status as status,w.position as position," +
                                "w.logo as logo ,w.banner as banner," +
                                "w.name as name ," +
                                "w.summary as summary ,w.introduce as introduce,userinfo.phone as phone,user.mobile,w.status as status  ");
                        FROM("user_website as w");
                        INNER_JOIN("user_contact as user on user.account=w.account ");
                        INNER_JOIN("user_info as userinfo on userinfo.account=w.account  ");
                        if (StringUtil.isNotBlank(account)) {
                            WHERE("w.account=#{account}");
                        }
                    }}.toString();
                    break;
                case 2:
                    sql = new SQL() {{
                        SELECT(" w.id as id ,w.status as status,w.position as position," +
                                "w.logo as logo ,w.banner as banner," +
                                "w.name as name ," +
                                "w.summary as summary ,w.introduce as introduce,userinfo.phone as phone,user.mobile,w.status as status ");
                        FROM("user_website as w");
                        INNER_JOIN("user_contact as user on user.account=w.account");
                        INNER_JOIN("user_info as userinfo on userinfo.account=w.account  ");
                        if (StringUtil.isNotBlank(account)) {
                            WHERE("w.account=#{account}");
                        }
                    }}.toString();
                    break;
                case 3:
                    sql = new SQL() {{
                        SELECT(" w.id as id ,w.status as status,w.position as position," +
                                "w.logo as logo ,w.banner as banner," +
                                "w.name as name ," +
                                "w.summary as summary ,w.introduce as introduce,userinfo.phone as phone,user.mobile,w.status as status ");
                        FROM("user_website as w");
                        INNER_JOIN("user_contact as user on user.account=w.account");
                        INNER_JOIN("user_info as userinfo on userinfo.account=w.account  ");
                        if (StringUtil.isNotBlank(account)) {
                            WHERE("w.account=#{account}");
                        }


                    }}.toString();
                    break;
                case 4:
                    sql = new SQL() {{
                        SELECT(" w.id as id ,w.status as status,w.position as position," +
                                "w.logo as logo ,w.banner as banner," +
                                "w.name as name ," +
                                "w.summary as summary ,w.introduce as introduce,userinfo.phone as phone,user.mobile,w.status as status ");
                        FROM("user_website as w");
                        INNER_JOIN("user_contact as user on user.account=w.account");
                        INNER_JOIN("user_info as userinfo on userinfo.account=w.account  ");
                        if (StringUtil.isNotBlank(account)) {
                            WHERE("w.account=#{account}");
                        }


                    }}.toString();
                    break;
                case 5:
                    sql = new SQL() {{
                        SELECT(" w.id as id ,w.status as status,w.position as position," +
                                "w.logo as logo ,w.banner as banner," +
                                "w.name as name ," +
                                "w.summary as summary ,w.introduce as introduce,userinfo.phone as phone,user.mobile,w.status as status ");
                        FROM("user_website as w");
                        INNER_JOIN("user_contact as user on user.account=w.account");
                        INNER_JOIN("user_info as userinfo on userinfo.account=w.account  ");
                     //   INNER_JOIN("gov_info as g on g.login_account=user.account");
                        if (StringUtil.isNotBlank(account)) {
                            WHERE("w.account=#{account}");
                        }


                    }}.toString();
                    break;
                case 6:
                    sql = new SQL() {{
                        SELECT(" w.id as id ,w.status as status,w.position as position," +
                                "w.logo as logo ,w.banner as banner," +
                                "w.name as name ," +
                                "w.summary as summary ,w.introduce as introduce,userinfo.phone as phone,user.mobile,w.status as status ");
                        FROM("user_website as w");
                        INNER_JOIN("user_contact as user on user.account=w.account");
                        INNER_JOIN("user_info as userinfo on userinfo.account=w.account  ");
                     //   INNER_JOIN("gov_info as g on g.login_account=user.account");
                        if (StringUtil.isNotBlank(account)) {
                            WHERE("w.account=#{account}");
                        }


                    }}.toString();
                    break;
                default:
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


}
