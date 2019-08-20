package com.ovit.manager.modules.cms.dao;

import com.ovit.manager.common.persistence.TreeDao;
import com.ovit.manager.common.persistence.annotation.MyBatisDao;
import com.ovit.manager.modules.cms.entity.IndustrialClassified;

/**
 * 行业分类DAO接口
 * @author ThinkGem
 * @version 2013-8-23
 */
@MyBatisDao
public interface IndustrialClassifiedDao extends TreeDao<IndustrialClassified>{
	
//	public List<IndustrialClassified> findModule(IndustrialClassified industrialClassified);
	
//	public List<Category> findByParentIdsLike(Category category);
//	{
//		return find("from Category where parentIds like :p1", new Parameter(parentIds));
//	}
	
//	public List<IndustrialClassified> findByParentId(String parentId, String isMenu);
//	{
//		return find("from Category where delFlag=:p1 and parent.id=:p2 and inMenu=:p3 order by site.id, sort", 
//				new Parameter(Category.DEL_FLAG_NORMAL, parentId, isMenu));
//	}

//	public List<IndustrialClassified> findByParentIdAndSiteId(IndustrialClassified entity);
	
//	public List<Map<String, Object>> findStats(String sql);
//	{
//		return find("from Category where delFlag=:p1 and parent.id=:p2 and site.id=:p3 order by site.id, sort", 
//				new Parameter(Category.DEL_FLAG_NORMAL, parentId, siteId));
//	}

	

}