package com.ovit.manager.modules.nswy.dao;

import org.apache.ibatis.annotations.*;
import com.ovit.manager.common.persistence.CrudDao;
import com.ovit.manager.common.persistence.annotation.MyBatisDao;
import com.ovit.manager.modules.nswy.entity.NswyCorpInfo;
import java.util.Map;


/**
 * Created by ${huipei.x} on 2017-8-15.
 */
@MyBatisDao
public interface NswyCorpInfoDao extends CrudDao<NswyCorpInfo> {

    @Results(value ={
            @Result(column = "id", property = "corpInfoId"),
            @Result(column = "corp_name", property = "corpName"),
            @Result(column = "legal_person", property = "legalPerson"),
            @Result(column = "corp_code", property = "corpCode"),
            @Result(column = "phone", property = "phone"),
            @Result(column = "identification_card", property = "identificationCard"),
            @Result(column = "create_time", property = "createTime")
    }
    )
    @Select("select * from corp_info where id=#{corpInfoId}")
    NswyCorpInfo getCorpInfo(Integer corpInfoId);

    @Delete("delete from corp_info where id=#{corpInfoId}")
    void delete(@Param("corpInfoId") Integer corpInfoId);

    void updateByPrimaryKeySelective(NswyCorpInfo nswyCorpInfo);

    void updateUserType(String login_account);

    void updateHomeDisplay(Map<String, Object> map);
}
