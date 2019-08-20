package com.ovit.manager.modules.cms.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ovit.manager.common.service.TreeService;
import com.ovit.manager.modules.cms.dao.IndustrialClassifiedDao;
import com.ovit.manager.modules.cms.entity.IndustrialClassified;
import com.ovit.manager.modules.sys.entity.User;
import com.ovit.manager.modules.sys.utils.UserUtils;

/**
 * 行业分类Service
 * @author ThinkGem
 * @version 2013-5-31
 */
@Service
@Transactional(readOnly = true)
public class IndustrialclassifiedService extends TreeService<IndustrialClassifiedDao, IndustrialClassified> {

	public static final String CACHE_INDUSTRIALCLASSIFIED_LIST = "industrialClassifiedList";
	
//	private Category entity = new Category();
	
	public List<IndustrialClassified> findAllList(boolean isCurrentSite, String fcategory){
		
//		List<IndustrialClassified> list = (List<IndustrialClassified>)UserUtils.getCache(CACHE_INDUSTRIALCLASSIFIED_LIST);
		
		//TODO
		IndustrialClassified industrialClassified = new IndustrialClassified();
		industrialClassified.setFcategory(fcategory);
		List<IndustrialClassified> list = dao.findAllList(industrialClassified);
		
		if (list == null){
			User user = UserUtils.getUser();
			industrialClassified.getSqlMap().put("dsf", dataScopeFilter(user, "o", "u"));
			industrialClassified.setParent(new IndustrialClassified());
			list = dao.findList(industrialClassified);

			//放入session中
			UserUtils.putCache(CACHE_INDUSTRIALCLASSIFIED_LIST, list);
		}
		
		return list;
	}

//	public List<Category> findByParentId(String parentId, String siteId){
//		Category parent = new Category();
//		parent.setId(parentId);
//		entity.setParent(parent);
//		Site site = new Site();
//		site.setId(siteId);
//		entity.setSite(site);
//		return dao.findByParentIdAndSiteId(entity);
//	}
	
//	public Page<Category> find(Page<Category> page, Category category) {
//		category.setPage(page);
//		category.setInMenu(Global.SHOW);
//		page.setList(dao.findModule(category));
//		return page;
//	}
	
//	@Transactional(readOnly = false)
//	public void save(Category category) {
//		category.setSite(new Site(Site.getCurrentSiteId()));
//		if (StringUtils.isNotBlank(category.getViewConfig())){
//            category.setViewConfig(StringEscapeUtils.unescapeHtml4(category.getViewConfig()));
//        }
//		super.save(category);
//		UserUtils.removeCache(CACHE_CATEGORY_LIST);
//		CmsUtils.removeCache("mainNavList_"+category.getSite().getId());
//	}
//	
//	@Transactional(readOnly = false)
//	public void delete(Category category) {
//		super.delete(category);
//		UserUtils.removeCache(CACHE_CATEGORY_LIST);
//		CmsUtils.removeCache("mainNavList_"+category.getSite().getId());
//	}
	
	/**
	 * 通过编号获取栏目列表
	 */
//	public List<Category> findByIds(String ids) {
//		List<Category> list = Lists.newArrayList();
//		String[] idss = StringUtils.split(ids,",");
//		if (idss.length>0){
//			for(String id : idss){
//				Category e = dao.get(id);
//				if(null != e){
//					//System.out.println("e.id:"+e.getId()+",e.name:"+e.getName());
//					list.add(e);
//				}
//				//list.add(dao.get(id));
//				
//			}
//		}
//		return list;
//	}
	
}
