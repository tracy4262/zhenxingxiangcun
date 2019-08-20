package com.ovit.nswy.member.mapper;

import com.ovit.nswy.member.common.sql.UserSqlProvider;
import com.ovit.nswy.member.model.LoginUser;
import com.ovit.nswy.member.model.response.FansDTO;
import com.ovit.nswy.member.model.response.MyAppDTO;
import com.ovit.nswy.member.model.response.UserDTO;
import org.apache.ibatis.annotations.*;
import java.util.List;
import java.util.Map;

/**
 * Created by ${huipei.x} on 2017-12-5.
 */
public interface UserMapper {
    @Select("select * from login_user where id not in (select id from login_user  where is_friend like CONCAT('%',#{value},'%')) and login_account!=#{account} ")
    List<LoginUser>  list(@Param("value")String value, @Param("account")String account);
    @Select("select id,account,fans_account as fansAccount from  fans where fans_account=#{account} ")
    List<FansDTO> listfollow(@Param("account")String account);
    @Update("update  myapp set start_flag=#{startFlag} where id=#{id} ")
    @ResultType(Integer.class)
    Integer updateStartFlag(@Param("startFlag")Integer startFlag,@Param("id")Integer id);

    @Select("select id,account,appId as appid, start_flag as startFlag from  myapp  where account=#{account} and level=0")
    List<MyAppDTO> listApp(@Param("account")String account);
    @SelectProvider(type = UserSqlProvider.class,method = "listfollowByMapSql")
    @ResultType(FansDTO.class)
    List<FansDTO> listfollowByMap(Map params);

    @SelectProvider(type = UserSqlProvider.class,method = "getUserByMap")
    @ResultType(LoginUser.class)
    LoginUser getUserByQuery(Map params);
    @Update("update login_user set is_friend=#{isFriend} where id=#{id}")
    @ResultType(Integer.class)
    Integer updateStatusById(@Param("isFriend")String isFriend ,@Param("id") Integer id);

    @SelectProvider(type = UserSqlProvider.class,method = "listUserByMap")
    @ResultType(UserDTO.class)
    List<UserDTO> listAllUser(Map params);

    @Select("SELECT * FROM fans WHERE account = #{account} AND fans_account = #{fansAccount}")
    List<Map<String, Object>> queryStatus(@Param("account")String account,@Param("fansAccount")String fansAccount);

    Map<String,Object> getRealName(String account);

    List<UserDTO> queryUserAndExpert(Map<String, Object> params);

    List<UserDTO> queryCorpInfo(Map<String, Object> params);

    List<UserDTO> queryGovInfo(Map<String, Object> params);

    List<UserDTO> queryAllUser(Map<String, Object> params);

    Map<String,Object> findMemberName(Map<String, Object> params);
}
