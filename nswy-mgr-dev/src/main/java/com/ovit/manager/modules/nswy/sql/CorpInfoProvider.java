package com.ovit.manager.modules.nswy.sql;

import org.apache.ibatis.jdbc.SQL;

/**
 * Created by ${huipei.x} on 2017-8-15.
 */
public class CorpInfoProvider {

    public String getCorpInfoByIdSql()
    {
        return new SQL() {{
            SELECT("id as corpInfoId,corp_name as corpName,legal_person as legalPerson , corp_code as corpCode,phone ,identification_card as identificationCard,create_time as createTime");
            FROM("corp_info");
            WHERE(" id=#{corpInfoId}");
        }}.toString();
    }
}
