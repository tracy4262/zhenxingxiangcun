package com.ovit.manager.modules.nswy.dao;

import org.apache.ibatis.annotations.*;
import com.ovit.manager.common.persistence.CrudDao;
import com.ovit.manager.common.persistence.annotation.MyBatisDao;
import com.ovit.manager.modules.nswy.dto.UserDTO;
import com.ovit.manager.modules.nswy.entity.NswyUser;
import com.ovit.manager.modules.nswy.sql.UserSqlProvider;

/**
 * Created by ${huipei.x} on 2017-8-14.
 */
@MyBatisDao
public interface NswyUserDao extends CrudDao<NswyUser> {

   @Results(value ={
            @Result(column = "id", property = "UserId"),
            @Result(column = "unique_id", property = "uniqueId"),
            @Result(column = "login_account", property = "loginAccount"),
            @Result(column = "display_name", property = "displayName"),
            @Result(column = "user_type", property = "userType")

    }
    )

    NswyUser getUser(Integer userId);

   void  updateUser(NswyUser nswyUser);

    @Delete("delete from login_user where id=#{userId}")
    void delete(@Param("userId") Integer userId);

}
