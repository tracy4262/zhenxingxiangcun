package com.ovit.nswy.portal.mapper;

import com.ovit.nswy.portal.core.sql.ContoctSqlProvider;
import com.ovit.nswy.portal.core.sql.CorpSqlProvider;
import com.ovit.nswy.portal.model.response.ContoctDTO;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.SelectProvider;
import java.util.Map;

/**
 * Created by ${huipei.x} on 2018-1-14.
 */
public interface ContoctMapper {

    @SelectProvider(type = ContoctSqlProvider.class,method = "getContoctSqlByMap")
    @ResultType(ContoctDTO.class)
    ContoctDTO getContoct(Map params);


}
