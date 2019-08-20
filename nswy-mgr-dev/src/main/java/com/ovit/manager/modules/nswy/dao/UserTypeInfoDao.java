/**
 * 
 */
package com.ovit.manager.modules.nswy.dao;

import com.ovit.manager.common.persistence.CrudDao;
import com.ovit.manager.common.persistence.annotation.MyBatisDao;
import com.ovit.manager.modules.nswy.entity.UserTypeInfo;

import java.util.List;
import java.util.Map;

/**
 * 用户类型DAO接口
 * @author ThinkGem
 * @version 2014-05-16
 */
@MyBatisDao
public interface UserTypeInfoDao extends CrudDao<UserTypeInfo> {

    List<Map<String,Object>> findUserType(Map<String,Object> params);

    void insertUserTypeInfo(UserTypeInfo userTypeInfo);

    Map<String,Object> findMaxInfoId();

    void deleteUserType(Integer infoId);
	
}
