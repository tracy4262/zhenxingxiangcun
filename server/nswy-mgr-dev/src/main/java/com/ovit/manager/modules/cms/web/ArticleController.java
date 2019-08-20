package com.ovit.manager.modules.cms.web;

import com.github.xphsc.json.JSONHelper;
import com.ovit.manager.common.mapper.JsonMapper;
import com.ovit.manager.common.persistence.Page;
import com.ovit.manager.common.utils.StringUtils;
import com.ovit.manager.common.web.BaseController;
import com.ovit.manager.modules.cms.entity.*;
import com.ovit.manager.modules.cms.service.*;
import com.ovit.manager.modules.cms.utils.CmsUtils;
import com.ovit.manager.modules.cms.utils.TplUtils;
import com.ovit.manager.modules.sys.utils.UserUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 文章Controller
 *
 * @author ThinkGem
 * @version 2013-3-23
 */
@Controller
@RequestMapping(value = "${adminPath}/cms/article")
public class ArticleController extends BaseController {

    @Autowired
    private ArticleService articleService;
    //	@Autowired
//	private ArticleDataService articleDataService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private IndustrialclassifiedService industrialclassifiedService;
    @Autowired
    private FileTplService fileTplService;
    @Autowired
    private SiteService siteService;

    @ModelAttribute
    public Article get(@RequestParam(required = false) String id) {
        if (StringUtils.isNotBlank(id)) {
            return articleService.get(id);
        } else {
            return new Article();
        }
    }

    @RequiresPermissions("cms:article:view")
    @RequestMapping(value = {"list", ""})
    public String list(Article article, HttpServletRequest request, HttpServletResponse response, Model model) {
//		for (int i=0; i<10000000; i++){
//			Article a = new Article();
//			a.setCategory(new Category(article.getCategory().getId()));
//			a.setTitle("测试测试测试测试测试测试测试测试"+a.getCategory().getId());
//			a.setArticleData(new ArticleData());
//			a.getArticleData().setContent(a.getTitle());
//			articleService.save(a);
//		}
        Page<Article> page = articleService.findPage(new Page<Article>(request, response), article, true);
        model.addAttribute("page", page);
        return "modules/cms/articleList";
    }

    @RequiresPermissions("cms:article:view")
    @RequestMapping(value = "form")
    public String form(Article article, Model model) {
        // 如果当前传参有子节点，则选择取消传参选择
        if (article.getCategory() != null && StringUtils.isNotBlank(article.getCategory().getId())) {
            List<Category> list = categoryService.findByParentId(article.getCategory().getId(), Site.getCurrentSiteId());
            if (list.size() > 0) {
                article.setCategory(null);
            } else {
                article.setCategory(categoryService.get(article.getCategory().getId()));
            }
        }
        //article.setArticleData(articleDataService.get(article.getId()));

        //TODO
        if (article != null && StringUtils.isNotBlank(article.getId())) {
            ArticleData articleData = new ArticleData();
            IndustrialClassified industrialClassified = new IndustrialClassified();
            switch (Integer.parseInt(article.getCategory().getId()))    //暂时通过分类id判断
            {
                case 3:    //咨询信息
                    Information information = new Information();
                    InformationDetail informationDetail = new InformationDetail();
                    information = articleService.selectInformation(article.getId());
                    informationDetail = articleService.selectInformationDetail(information.getInformationDetailId());
                    articleData.setId(article.getId());
                    articleData.setCopyfrom(informationDetail.getSource());    //来源
                    articleData.setContent(informationDetail.getContent());    //取文章内容content
                    article.setArticleData(articleData);
                    article.setKeywords(information.getLables());    //标签 关键字
                    article.setBusinessInformation(information.getBusinessInformation());//商务咨询
                    article.setAllRange(information.getAllRange());//所有范围
                    article.setBusinessNature(information.getBusinessNature());//行业性质
                    article.setAllBusiness(information.getAllBusiness());//所有行业
                    industrialClassified = industrialclassifiedService.get(information.getIndustrialId());
                    article.setIndustrialClassified(industrialClassified);
                    article.setDocType(information.getDocType());
                    break;
                case 4:    //政策信息
                    Policy policy = new Policy();
                    PolicyDetail policyDetail = new PolicyDetail();
                    policy = articleService.selectpolicy(article.getId());
                    policyDetail = articleService.selectpolicyDetail(policy.getInformationDetailId());
                    articleData.setId(article.getId());
                    articleData.setCopyfrom(policyDetail.getSource());    //来源
                    articleData.setContent(policyDetail.getContent());    //取文章内容content
                    article.setArticleData(articleData);
                    article.setKeywords(policy.getLables());    //标签 关键字
                    article.setBusinessInformation(policy.getBusinessInformation());//商务咨询
                    article.setAllRange(policy.getAllRange());//所有范围
                    article.setBusinessNature(policy.getBusinessNature());//行业性质
                    article.setAllBusiness(policy.getAllBusiness());//所有行业
                    industrialClassified = industrialclassifiedService.get(policy.getIndustrialId());
                    article.setIndustrialClassified(industrialClassified);
                    article.setDocType(policy.getDocType());
                    break;
                case 5:    //知识信息
                    Knowledge knowledge = new Knowledge();
                    KnowledgeDetail knowledgeDetail = new KnowledgeDetail();
                    knowledge = articleService.selectknowledge(article.getId());
                    knowledgeDetail = articleService.selectknowledgeDetail(knowledge.getInformationDetailId());
                    articleData.setId(article.getId());
                    articleData.setCopyfrom(knowledgeDetail.getSource());    //来源
                    articleData.setContent(knowledgeDetail.getContent());    //取文章内容content
                    article.setArticleData(articleData);
                    article.setKeywords(knowledge.getLables());    //标签 关键字
                    article.setBusinessInformation(knowledge.getBusinessInformation());//商务咨询
                    article.setAllRange(knowledge.getAllRange());//所有范围
                    article.setBusinessNature(knowledge.getBusinessNature());//行业性质
                    article.setAllBusiness(knowledge.getAllBusiness());//所有行业
                    industrialClassified = industrialclassifiedService.get(knowledge.getIndustrialId());
                    article.setIndustrialClassified(industrialClassified);
                    article.setDocType(knowledge.getDocType());
                    break;
                default:
                    break;
            }
        }

//		if (article.getCategory()=null && StringUtils.isNotBlank(article.getCategory().getId())){
//			Category category = categoryService.get(article.getCategory().getId());
//		}
        model.addAttribute("contentViewList", getTplContent());
        model.addAttribute("article_DEFAULT_TEMPLATE", Article.DEFAULT_TEMPLATE);
        model.addAttribute("article", article);
        CmsUtils.addViewConfigAttribute(model, article.getCategory());
        return "modules/cms/articleForm";
    }

    @RequestMapping(value = "articleAuditForm")
    public String articleAuditForm(Article article, Model model) {
        model.addAttribute("Article",article);
        return "modules/cms/articleAudit";
    }

    @RequiresPermissions("cms:article:edit")
    @RequestMapping(value = "save")
    public String save(Article article, Model model, RedirectAttributes redirectAttributes) {
        if (!beanValidator(model, article)) {
            return form(article, model);
        }

        articleService.save(article);
        addMessage(redirectAttributes, "保存文章'" + StringUtils.abbr(article.getTitle(), 50) + "'成功");
        String categoryId = article.getCategory() != null ? article.getCategory().getId() : null;
        return "redirect:" + adminPath + "/cms/article/?repage&category.id=" + (categoryId != null ? categoryId : "");
    }

    @RequiresPermissions("cms:article:edit")
    @RequestMapping(value = "delete")
    public String delete(Article article, String categoryId, @RequestParam(required = false) Boolean isRe, RedirectAttributes redirectAttributes) {
        // 如果没有审核权限，则不允许删除或发布。
        if (!UserUtils.getSubject().isPermitted("cms:article:audit")) {
            addMessage(redirectAttributes, "你没有删除权限");
        }
        articleService.delete(article, isRe);
        addMessage(redirectAttributes, "删除文章成功");
        return "redirect:" + adminPath + "/cms/article/?repage&category.id=" + (categoryId != null ? categoryId : "");
    }

    @RequiresPermissions("cms:article:edit")
    @RequestMapping(value = "approve")
    public String approve(Article article, String categoryId, RedirectAttributes redirectAttributes) {
        // 如果没有审核权限，则不允许删除或发布。
       if (!UserUtils.getSubject().isPermitted("cms:article:audit")) {
            addMessage(redirectAttributes, "你没有审核权限");
        }
        articleService.approve(article);
       addMessage(redirectAttributes, "审核成功");
        return "redirect:" + adminPath + "/cms/article/?repage&category.id=" + (categoryId != null ? categoryId : "");
    }
    /**
     * 文章选择列表
     */
    @RequiresPermissions("cms:article:view")
    @RequestMapping(value = "selectList")
    public String selectList(Article article, HttpServletRequest request, HttpServletResponse response, Model model) {
        list(article, request, response, model);
        return "modules/cms/articleSelectList";
    }

    /**
     * 通过编号获取文章标题
     */
    @RequiresPermissions("cms:article:view")
    @ResponseBody
    @RequestMapping(value = "findByIds")
    public String findByIds(String ids) {
        List<Object[]> list = articleService.findByIds(ids);
        return JsonMapper.nonDefaultMapper().toJson(list);
    }

    private List<String> getTplContent() {
        List<String> tplList = fileTplService.getNameListByPrefix(siteService.get(Site.getCurrentSiteId()).getSolutionPath());
        tplList = TplUtils.tplTrim(tplList, Article.DEFAULT_TEMPLATE, "");
        return tplList;
    }
}
