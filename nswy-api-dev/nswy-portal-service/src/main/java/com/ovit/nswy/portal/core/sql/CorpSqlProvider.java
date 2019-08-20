package com.ovit.nswy.portal.core.sql;

import com.github.xphsc.collect.Maps;
import com.github.xphsc.mutable.Integers;
import com.github.xphsc.util.StringUtil;
import org.apache.ibatis.jdbc.SQL;

import java.util.Map;

/**
 * Created by ${huipei.x} on 2018-1-12.
 */
public class CorpSqlProvider {

    public String getCorpSqlByMap(final Map params){
        Integer userType=Maps.getInteger(params,"userType");
        String  account= Maps.getString(params, "account");
        return  new SQL() {{
            SELECT("c.account as loginAccount ,c.real_name as legalPerson, c.corp_name as corpName,c.mobile,c.phone,c.email,c.location,c.credit_code as creditCode");
            FROM("user_contact as c");
            INNER_JOIN("login_user as user on user.login_account=c.account");
            if (StringUtil.isNotBlank(account)){
                WHERE("c.account=#{account}");
            }
            if(Integers.isNotEmpty(userType)){
                AND() ;
                WHERE("user.user_type=#{userType}");
            }

        }}.toString();
    }
}
