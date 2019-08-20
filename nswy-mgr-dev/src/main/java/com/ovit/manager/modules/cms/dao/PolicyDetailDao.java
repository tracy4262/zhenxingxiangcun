package com.ovit.manager.modules.cms.dao;

import com.ovit.manager.common.persistence.CrudDao;
import com.ovit.manager.common.persistence.annotation.MyBatisDao;
import com.ovit.manager.modules.cms.entity.PolicyDetail;

/**
 * 文章DAO接口
 * @author ThinkGem
 * @version 2013-8-23
 */
@MyBatisDao
public interface PolicyDetailDao extends CrudDao<PolicyDetail> {
	
	public PolicyDetail getDetailById(Integer id);

}
