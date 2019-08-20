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
public class ContoctSqlProvider {

    public String getContoctSqlByMap(final Map params){
        /**
         * 查询类型 1 个人 2 专家 3 企业 4 商城企业 5 机关 6 乡村
         */
        Integer queryType =null;
        if(!isEmpty(params,"queryType")){
            queryType = Maps.getInteger(params, "queryType");
        }
        String  account= Maps.getString(params, "account");
        String sql="";
        if(Integers.isNotEmpty(queryType)&&!queryType.equals(0)){
            switch (queryType){
                case 1:
                    sql=  new SQL() {{
                        SELECT("userinfo.account as loginAccount,user.display_name as legalPerson,userc.mobile,userinfo.phone,userc.email,userc.postalcode as postalCode,userinfo.coordinate_point as coordinate,userinfo.addr as addr,userc.qq as qq,userinfo.status_n as nameStatus,userinfo.status_phone as phoneStatus,userinfo.status_addr as addrStatus,userinfo.status_coord AS coordinateStatus,userc.status_2 as status ");
                        FROM("user_info as userinfo  ");
                        INNER_JOIN("user_contact as userc on userc.account=userinfo.account");
                        INNER_JOIN("login_user as user on user.login_account=userc.account");
                        if (StringUtil.isNotBlank(account)){
                            WHERE("user.login_account=#{account}");
                        }

                    }}.toString(); break;
                case 2:
                    sql=  new SQL() {{
                        SELECT("userinfo.account as loginAccount,user.display_name as legalPerson,userc.mobile,userinfo.phone,userc.email,userc.postalcode as postalCode,userinfo.coordinate_point as coordinate,userinfo.addr as addr,userc.qq as qq,userinfo.status_n as nameStatus,userinfo.status_phone as phoneStatus,userinfo.status_addr as addrStatus,userinfo.status_coord AS coordinateStatus,userc.status_2 as status ");
                        FROM("user_info as userinfo  ");
                        INNER_JOIN("user_contact as userc on userc.account=userinfo.account");
                        INNER_JOIN("login_user as user on user.login_account=userc.account");
                        if (StringUtil.isNotBlank(account)){
                            WHERE("user.login_account=#{account}");
                        }

                    }}.toString();  break;
                case 3:
                    sql=  new SQL() {{
                        SELECT("userinfo.account as loginAccount,user.display_name as legalPerson,userc.mobile,userinfo.phone,userc.email,userc.postalcode as postalCode,userinfo.coordinate_point as coordinate,userinfo.addr as addr,userc.qq as qq,userinfo.status_n as nameStatus,userinfo.status_phone as phoneStatus,userinfo.status_addr as addrStatus,userinfo.status_coord AS coordinateStatus,userc.status_2 as status ");
                        FROM("user_info as userinfo  ");
                        INNER_JOIN("user_contact as userc on userc.account=userinfo.account");
                        INNER_JOIN("login_user as user on user.login_account=userc.account");
                        if (StringUtil.isNotBlank(account)){
                            WHERE("user.login_account=#{account}");
                        }

                    }}.toString(); break;
                case 4:
                    sql=  new SQL() {{
                        SELECT("userinfo.account as loginAccount,user.display_name as legalPerson,userc.mobile,userinfo.phone,userc.email,userc.postalcode as postalCode,userinfo.coordinate_point as coordinate,userinfo.addr as addr,userc.qq as qq,userinfo.status_n as nameStatus,userinfo.status_phone as phoneStatus,userinfo.status_addr as addrStatus,userinfo.status_coord AS coordinateStatus,userc.status_2 as status ");
                        FROM("user_info as userinfo  ");
                        INNER_JOIN("user_contact as userc on userc.account=userinfo.account");
                        INNER_JOIN("login_user as user on user.login_account=userc.account");
                        if (StringUtil.isNotBlank(account)){
                            WHERE("user.login_account=#{account}");
                        }

                    }}.toString(); break;
                case 5:
                    sql=  new SQL() {{
                        SELECT("userinfo.account as loginAccount,user.display_name as legalPerson,userc.mobile,userinfo.phone,userc.email,userc.postalcode as postalCode,userinfo.coordinate_point as coordinate,userinfo.addr as addr,userc.qq as qq,userinfo.status_n as nameStatus,userinfo.status_phone as phoneStatus,userinfo.status_addr as addrStatus,userinfo.status_coord AS coordinateStatus,userc.status_2 as status ");
                        FROM("user_info as userinfo  ");
                        INNER_JOIN("user_contact as userc on userc.account=userinfo.account");
                        INNER_JOIN("login_user as user on user.login_account=userc.account");
                        if (StringUtil.isNotBlank(account)){
                            WHERE("user.login_account=#{account}");
                        }

                    }}.toString(); break;
                case 6:
                    sql=  new SQL() {{
                        SELECT("userinfo.account as loginAccount,user.display_name as legalPerson,userc.mobile,userinfo.phone,userc.email,userc.postalcode as postalCode,userinfo.coordinate_point as coordinate,userinfo.addr as addr,userc.qq as qq,userinfo.status_n as nameStatus,userinfo.status_phone as phoneStatus,userinfo.status_addr as addrStatus,userinfo.status_coord AS coordinateStatus,userc.status_2 as status ");
                        FROM("user_info as userinfo  ");
                        INNER_JOIN("user_contact as userc on userc.account=userinfo.account");
                        INNER_JOIN("login_user as user on user.login_account=userc.account");
                        if (StringUtil.isNotBlank(account)){
                            WHERE("user.login_account=#{account}");
                        }

                    }}.toString(); break; default:
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
