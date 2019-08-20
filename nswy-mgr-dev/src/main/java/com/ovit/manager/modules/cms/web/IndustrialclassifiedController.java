package com.ovit.manager.modules.cms.web;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.ovit.manager.common.utils.StringUtils;
import com.ovit.manager.common.web.BaseController;
import com.ovit.manager.modules.cms.entity.IndustrialClassified;
import com.ovit.manager.modules.cms.service.IndustrialclassifiedService;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * 行业分类Controller
 * @author ThinkGem
 * @version 2017-8-22
 */
@Controller
@RequestMapping(value = "${adminPath}/cms/industrialclassified")
public class IndustrialclassifiedController extends BaseController {

	@Autowired
	private IndustrialclassifiedService industrialClassifiedService;
//    @Autowired
//   	private FileTplService fileTplService;
//    @Autowired
//   	private SiteService siteService;
	
//	@ModelAttribute("category")
//	public Category get(@RequestParam(required=false) String id) {
//		if (StringUtils.isNotBlank(id)){
//			return categoryService.get(id);
//		}else{
//			return new Category();
//		}
//	}

//	@RequiresPermissions("cms:category:view")
//	@RequestMapping(value = {"list", ""})
//	public String list(Model model) {
//		List<Category> list = Lists.newArrayList();
//		List<Category> sourcelist = categoryService.findByUser(true, null);
//		Category.sortList(list, sourcelist, "1");
//        model.addAttribute("list", list);
//		return "modules/cms/categoryList";
//	}

//	@RequiresPermissions("cms:category:view")
//	@RequestMapping(value = "form")
//	public String form(Category category, Model model) {
//		if (category.getParent()==null||category.getParent().getId()==null){
//			category.setParent(new Category("1"));
//		}
//		Category parent = categoryService.get(category.getParent().getId());
//		category.setParent(parent);
//		if (category.getOffice()==null||category.getOffice().getId()==null){
//			category.setOffice(parent.getOffice());
//		}
//        model.addAttribute("listViewList",getTplContent(Category.DEFAULT_TEMPLATE));
//        model.addAttribute("category_DEFAULT_TEMPLATE",Category.DEFAULT_TEMPLATE);
//        model.addAttribute("contentViewList",getTplContent(Article.DEFAULT_TEMPLATE));
//        model.addAttribute("article_DEFAULT_TEMPLATE",Article.DEFAULT_TEMPLATE);
//		model.addAttribute("office", category.getOffice());
//		model.addAttribute("category", category);
//		return "modules/cms/categoryForm";
//	}
	
//	@RequiresPermissions("cms:category:edit")
//	@RequestMapping(value = "save")
//	public String save(Category category, Model model, RedirectAttributes redirectAttributes) {
//		if(Global.isDemoMode()){
//			addMessage(redirectAttributes, "演示模式，不允许操作！");
//			return "redirect:" + adminPath + "/cms/category/";
//		}
//		if (!beanValidator(model, category)){
//			return form(category, model);
//		}
//		categoryService.save(category);
//		addMessage(redirectAttributes, "保存栏目'" + category.getName() + "'成功");
//		return "redirect:" + adminPath + "/cms/category/";
//	}
	
//	@RequiresPermissions("cms:category:edit")
//	@RequestMapping(value = "delete")
//	public String delete(Category category, RedirectAttributes redirectAttributes) {
//		if(Global.isDemoMode()){
//			addMessage(redirectAttributes, "演示模式，不允许操作！");
//			return "redirect:" + adminPath + "/cms/category/";
//		}
//		if (Category.isRoot(category.getId())){
//			addMessage(redirectAttributes, "删除栏目失败, 不允许删除顶级栏目或编号为空");
//		}else{
//			categoryService.delete(category);
//			addMessage(redirectAttributes, "删除栏目成功");
//		}
//		return "redirect:" + adminPath + "/cms/category/";
//	}

//	/**
//	 * 批量修改栏目排序
//	 */
//	@RequiresPermissions("cms:category:edit")
//	@RequestMapping(value = "updateSort")
//	public String updateSort(String[] ids, Integer[] sorts, RedirectAttributes redirectAttributes) {
//    	int len = ids.length;
//    	Category[] entitys = new Category[len];
//    	for (int i = 0; i < len; i++) {
//    		entitys[i] = categoryService.get(ids[i]);
//    		entitys[i].setSort(sorts[i]);
//    		categoryService.save(entitys[i]);
//    	}
//    	addMessage(redirectAttributes, "保存栏目排序成功!");
//		return "redirect:" + adminPath + "/cms/category/";
//	}
	
	@RequiresUser
	@ResponseBody
	@RequestMapping(value = "treeData")
	public List<Map<String, Object>> treeData(@RequestParam(required=false) String fcategory, @RequestParam(required=false) String extId, HttpServletResponse response) {
		response.setContentType("application/json; charset=UTF-8");
		List<Map<String, Object>> mapList = Lists.newArrayList();
		List<IndustrialClassified> list = industrialClassifiedService.findAllList(true, fcategory);
		for (int i=0; i<list.size(); i++){
			IndustrialClassified e = list.get(i);
//			if (extId == null || (extId!=null && !extId.equals(e.getFid()) && e.getParentIds().indexOf(","+extId+",")==-1)){
				Map<String, Object> map = Maps.newHashMap();
				map.put("id", e.getFid());
				
			if(fcategory != null || StringUtils.isNotBlank(fcategory))
			{
				if(fcategory.equals("3"))	//资讯
					map.put("pId", e.getFpid()!=null?e.getFpid():"A");
				else if(fcategory.equals("4"))	//政策
					map.put("pId", e.getFpid()!=null?e.getFpid():"B");
				else if(fcategory.equals("5"))	//知识
					map.put("pId", e.getFpid()!=null?e.getFpid():"C");
					
				map.put("name", e.getFname());
				//map.put("module", e.getModule());
				map.put("module", "article");
				map.put("fcategory", e.getFcategory());
				mapList.add(map);
			}
		}
		return mapList;
	}

//    private List<String> getTplContent(String prefix) {
//   		List<String> tplList = fileTplService.getNameListByPrefix(siteService.get(Site.getCurrentSiteId()).getSolutionPath());
//   		tplList = TplUtils.tplTrim(tplList, prefix, "");
//   		return tplList;
//   	}
}
