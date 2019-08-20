package com.ovit.manager.test.dao;

import com.ovit.manager.common.persistence.TreeDao;
import com.ovit.manager.common.persistence.annotation.MyBatisDao;
import com.ovit.manager.test.entity.TestTree;

/**
 * 树结构生成DAO接口
 * @author ThinkGem
 * @version 2015-04-06
 */
@MyBatisDao
public interface TestTreeDao extends TreeDao<TestTree> {
	
}