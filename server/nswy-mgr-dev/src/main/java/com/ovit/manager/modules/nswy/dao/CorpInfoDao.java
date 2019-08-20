package com.ovit.manager.modules.nswy.dao;

import com.ovit.manager.common.persistence.CrudDao;
import com.ovit.manager.common.persistence.annotation.MyBatisDao;
import com.ovit.manager.modules.nswy.entity.NswyCorpInfo;
import org.apache.ibatis.annotations.*;

import java.util.Map;


/**
 * Created by ${huipei.x} on 2017-8-15.
 */
@MyBatisDao
public interface CorpInfoDao extends CrudDao<NswyCorpInfo> {

    void deleteCorpInfo(Integer informationInfoId);

    void corpInfoUpdateNavDisplay(Map<String, Object> map);

    void corpInfoSetOrderNum(Map<String, Object> map);

    void corpInfoUpdateHome(Map<String, Object> map);

}
