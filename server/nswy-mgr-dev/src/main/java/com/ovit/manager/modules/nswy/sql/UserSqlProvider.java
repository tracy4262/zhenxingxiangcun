package com.ovit.manager.modules.nswy.sql;

import org.apache.ibatis.jdbc.SQL;

/**
 * Created by ${huipei.x} on 2017-8-14.
 */
public class UserSqlProvider {

    public String findUserSql()
    {
        return new SQL() {{
            SELECT("id as UserId,unique_id as uniqueId,login_account as loginAccount , display_name as displayName,user_type AS userType");
            FROM("login_user");
        }}.toString();
    }
}
