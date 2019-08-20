package com.ovit.manager.modules.cms.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.ovit.manager.common.config.Global;
import com.ovit.manager.common.persistence.Page;
import com.ovit.manager.common.service.CrudService;
import com.ovit.manager.common.utils.StringUtils;
import com.ovit.manager.modules.cms.dao.ArticleDao;
import com.ovit.manager.modules.cms.dao.CategoryDao;
import com.ovit.manager.modules.cms.dao.InformationDao;
import com.ovit.manager.modules.cms.dao.InformationDetailDao;
import com.ovit.manager.modules.cms.dao.KnowledgeDao;
import com.ovit.manager.modules.cms.dao.KnowledgeDetailDao;
import com.ovit.manager.modules.cms.dao.PolicyDao;
import com.ovit.manager.modules.cms.dao.PolicyDetailDao;
import com.ovit.manager.modules.cms.entity.Article;
import com.ovit.manager.modules.cms.entity.Category;
import com.ovit.manager.modules.cms.entity.Information;
import com.ovit.manager.modules.cms.entity.InformationDetail;
import com.ovit.manager.modules.cms.entity.Knowledge;
import com.ovit.manager.modules.cms.entity.KnowledgeDetail;
import com.ovit.manager.modules.cms.entity.Policy;
import com.ovit.manager.modules.cms.entity.PolicyDetail;
import com.ovit.manager.modules.sys.utils.UserUtils;

/**
 * 文章Service
 * @author ThinkGem
 * @version 2013-05-15
 */
@Service
@Transactional(readOnly = true)
public class ArticleService extends CrudService<ArticleDao, Article> {

	@Autowired
	private InformationDetailDao informationDetailDao;
	@Autowired
	private InformationDao informationDao;
	@Autowired
	private PolicyDetailDao policyDetailDao;
	@Autowired
	private PolicyDao policyDao;
	@Autowired
	private KnowledgeDetailDao knowledgeDetailDao;
	@Autowired
	private KnowledgeDao knowledgeDao;
	@Autowired
	private CategoryDao categoryDao;
	
	@Transactional(readOnly = false)
	public Page<Article> findPage(Page<Article> page, Article article, boolean isDataScopeFilter) {
		// 更新过期的权重，间隔为“6”个小时
//		Date updateExpiredWeightDate =  (Date)CacheUtils.get("updateExpiredWeightDateByArticle");
//		if (updateExpiredWeightDate == null || (updateExpiredWeightDate != null 
//				&& updateExpiredWeightDate.getTime() < new Date().getTime())){
//			dao.updateExpiredWeight(article);
//			CacheUtils.put("updateExpiredWeightDateByArticle", DateUtils.addHours(new Date(), 6));
//		}
//		DetachedCriteria dc = dao.createDetachedCriteria();
//		dc.createAlias("category", "category");
//		dc.createAlias("category.site", "category.site");
		if (article.getCategory()!=null && StringUtils.isNotBlank(article.getCategory().getId()) && !Category.isRoot(article.getCategory().getId())){
			Category category = categoryDao.get(article.getCategory().getId());
			if (category==null){
				category = new Category();
			}
			category.setParentIds(category.getId());
			category.setSite(category.getSite());
			article.setCategory(category);
		}
		else{
			article.setCategory(new Category());
		}
//		if (StringUtils.isBlank(page.getOrderBy())){
//			page.setOrderBy("a.weight,a.update_date desc");
//		}
//		return dao.find(page, dc);
	//	article.getSqlMap().put("dsf", dataScopeFilter(article.getCurrentUser(), "o", "u"));
		return super.findPage(page, article);
		
	}

	/**根据article_id查询information表信息*/
	@Transactional(readOnly = false)
	public Information selectInformation(String article_id) {
		Information information = new Information();
		information = informationDao.get(article_id);
		return information;
	}
	
	/**根据article_id查询information_detail表信息*/
	@Transactional(readOnly = false)
	public InformationDetail selectInformationDetail(Integer detail_id) {
		InformationDetail informationDetail = new InformationDetail();
		informationDetail = informationDetailDao.getDetailById(detail_id);
		return informationDetail;
	}
	
	/**根据article_id查询policy表信息*/
	@Transactional(readOnly = false)
	public Policy selectpolicy(String article_id) {
		Policy policy = new Policy();
		policy = policyDao.get(article_id);
		return policy;
	}
	
	/**根据article_id查询policy_detail表信息*/
	@Transactional(readOnly = false)
	public PolicyDetail selectpolicyDetail(Integer detail_id) {
		PolicyDetail policyDetail = new PolicyDetail();
		policyDetail = policyDetailDao.getDetailById(detail_id);
		return policyDetail;
	}
	
	/**根据article_id查询knowledge表信息*/
	@Transactional(readOnly = false)
	public Knowledge selectknowledge(String article_id) {
		Knowledge knowledge = new Knowledge();
		knowledge = knowledgeDao.get(article_id);
		return knowledge;
	}
	
	/**根据article_id查询knowledge_detail表信息*/
	@Transactional(readOnly = false)
	public KnowledgeDetail selectknowledgeDetail(Integer detail_id) {
		KnowledgeDetail knowledgeDetail = new KnowledgeDetail();
		knowledgeDetail = knowledgeDetailDao.getDetailById(detail_id);
		return knowledgeDetail;
	}
	
	/**保存文章信息*/
	@Transactional(readOnly = false)
	public void save(Article article) {
		if (article.getArticleData().getContent()!=null){
			article.getArticleData().setContent(StringEscapeUtils.unescapeHtml4(
					article.getArticleData().getContent()));
		}
		// 如果没有审核权限，则将当前内容改为待审核状态
		if (!UserUtils.getSubject().isPermitted("cms:article:audit")){
			article.setDelFlag(Article.DEL_FLAG_AUDIT);
		}
		// 如果栏目不需要审核，则将该内容设为发布状态
		if (article.getCategory()!=null&&StringUtils.isNotBlank(article.getCategory().getId())){
			Category category = categoryDao.get(article.getCategory().getId());
			if (!Global.YES.equals(category.getIsAudit())){
				article.setDelFlag(Article.DEL_FLAG_NORMAL);
			}
		}
		article.setUpdateBy(UserUtils.getUser());
		article.setUpdateDate(new Date());
		article.setCreateDate(new Date());
        if (StringUtils.isNotBlank(article.getViewConfig())){
            article.setViewConfig(StringEscapeUtils.unescapeHtml4(article.getViewConfig()));
        }
        
//        ArticleData articleData = new ArticleData();;
		if (StringUtils.isBlank(article.getId())){
			article.preInsert();
//			articleData = article.getArticleData();
//			articleData.setId(article.getId());
			//TODO 注意:设置成全局不重复id
			String uuid = UUID.randomUUID().toString().replaceAll("-", "");
			article.setId(uuid);
			dao.insert(article);	//article作为公共总表继续存在，三个信息表的article_id与article的id字段关联
			// TODO
			//articleDataDao.insert(articleData);
			switch(Integer.parseInt(article.getCategory().getId()))
			{
			case 3:	//咨询
				InformationDetail informationDetail = new InformationDetail();
				informationDetail.setTitle(article.getTitle());
				informationDetail.setCreateTime(article.getCreateDate());
				informationDetail.setSource(article.getArticleData().getCopyfrom()); //来源
				informationDetail.setBrowseNumber(article.getHits()); //评论次数
				informationDetail.setContent(article.getArticleData().getContent()); //文章内容
				informationDetail.setEditer(article.getCreateBy().getName()); //创建者 编辑
				informationDetail.setLables(article.getKeywords());
				informationDetailDao.insert(informationDetail);
				
				Information information = new Information();
				information.setArticleId(uuid);	//此id关联article表
				information.setInformationDetailId(informationDetail.getId());
				information.setTitle(informationDetail.getTitle()); //咨询标题
				information.setImageAdd(article.getImage());
				information.setCreateTime(informationDetail.getCreateTime());
				information.setCommentNum(informationDetail.getBrowseNumber()); //评论次数
				information.setLables(article.getKeywords()); //标签 关键字
				information.setBusinessInformation(article.getBusinessInformation());	//商务咨询
				information.setAllRange(article.getAllRange());	//所有范围
				information.setBusinessNature(article.getBusinessNature());	//行业性质
				information.setAllBusiness(article.getAllBusiness());	//所有行业
				information.setIndustrialId(article.getIndustrialClassified().getFid());
				information.setDocType(article.getDocType());
				informationDao.insert(information);
				break;
			case 4:	//政策
				PolicyDetail policyDetail = new PolicyDetail();
				policyDetail.setTitle(article.getTitle());
				policyDetail.setCreateTime(article.getCreateDate());
				policyDetail.setSource(article.getArticleData().getCopyfrom()); //来源
				policyDetail.setBrowseNumber(article.getHits()); //评论次数
				policyDetail.setLables(article.getKeywords());
				policyDetail.setContent(article.getArticleData().getContent()); //文章内容
				policyDetail.setEditer(article.getCreateBy().getName());	//创建者 编辑
				policyDetailDao.insert(policyDetail);
				
				Policy policy = new Policy();
				policy.setArticleId(uuid);	//此id关联article表
				policy.setInformationDetailId(policyDetail.getId());
				policy.setTitle(policyDetail.getTitle()); //咨询标题
				policy.setImageAdd(article.getImage());
				policy.setCreateTime(policyDetail.getCreateTime());
				policy.setCommentNum(policyDetail.getBrowseNumber()); //评论次数
				policy.setLables(article.getKeywords()); //标签 关键字
				policy.setBusinessInformation(article.getBusinessInformation());
				policy.setAllRange(article.getAllRange());
				policy.setBusinessNature(article.getBusinessNature());
				policy.setAllBusiness(article.getAllBusiness());
				policy.setIndustrialId(article.getIndustrialClassified().getFid());
				policy.setDocType(article.getDocType());
				policyDao.insert(policy);
				break;
			case 5:	//知识
				KnowledgeDetail knowledgeDetail = new KnowledgeDetail();
				knowledgeDetail.setTitle(article.getTitle());
				knowledgeDetail.setCreateTime(article.getCreateDate());
				knowledgeDetail.setSource(article.getArticleData().getCopyfrom()); //来源
				knowledgeDetail.setBrowseNumber(article.getHits()); //评论次数
				knowledgeDetail.setContent(article.getArticleData().getContent()); //文章内容
				knowledgeDetail.setEditer(article.getCreateBy().getName());	//创建者 编辑
				knowledgeDetail.setLables(article.getKeywords()); //标签 关键字
				knowledgeDetailDao.insert(knowledgeDetail);
				
				Knowledge knowledge = new Knowledge();
				knowledge.setArticleId(uuid);	//此id关联article表
				knowledge.setInformationDetailId(knowledgeDetail.getId());
				knowledge.setTitle(knowledgeDetail.getTitle()); //咨询标题
				knowledge.setImageAdd(article.getImage());
				knowledge.setCreateTime(knowledgeDetail.getCreateTime());
				knowledge.setCommentNum(knowledgeDetail.getBrowseNumber()); //评论次数
				knowledge.setLables(article.getKeywords()); //标签 关键字
				knowledge.setBusinessInformation(article.getBusinessInformation());
				knowledge.setAllRange(article.getAllRange());
				knowledge.setBusinessNature(article.getBusinessNature());
				knowledge.setAllBusiness(article.getAllBusiness());
				knowledge.setIndustrialId(article.getIndustrialClassified().getFid());
				knowledge.setDocType(article.getDocType());
				knowledgeDao.insert(knowledge);
				break;
			default:
				break;
			}
		}else{
			article.preUpdate();
//			articleData = article.getArticleData();
//			articleData.setId(article.getId());
			dao.update(article);
			int approveStatus = Integer.parseInt(article.getDelFlag());
			// TODO
			//articleDataDao.update(article.getArticleData());
			switch(Integer.parseInt(article.getCategory().getId()))
			{
			case 3:	//咨询
				InformationDetail informationDetail = new InformationDetail();
				Information information = new Information();
				information = this.selectInformation(article.getId());	//先查数据库原来的值
				informationDetail = this.selectInformationDetail(information.getInformationDetailId());
				
				informationDetail.setTitle(article.getTitle());
				informationDetail.setSource(article.getArticleData().getCopyfrom()); //来源
				informationDetail.setBrowseNumber(article.getHits()); //评论次数
				informationDetail.setContent(article.getArticleData().getContent()); //文章内容
				
				
				information.setInformationDetailId(informationDetail.getId());
				information.setTitle(informationDetail.getTitle()); //咨询标题
				information.setImageAdd(article.getImage());
				information.setCommentNum(informationDetail.getBrowseNumber()); //评论次数	
				information.setLables(article.getKeywords());	//标签 关键字
				information.setBusinessInformation(article.getBusinessInformation());	//商务咨询
				information.setAllRange(article.getAllRange());	//所有范围
				information.setBusinessNature(article.getBusinessNature());	//行业性质
				information.setAllBusiness(article.getAllBusiness());	//所有行业
				information.setIndustrialId(article.getIndustrialClassified().getFid());
				information.setDocType(article.getDocType());
				
				informationDetailDao.update(informationDetail);
				information.setApproveStatus(approveStatus);
				informationDao.update(information);
				break;
			case 4:	//政策
				PolicyDetail policyDetail = new PolicyDetail();
				Policy policy = new Policy();
				policy = this.selectpolicy(article.getId());	//先查数据库原来的值
				policyDetail = this.selectpolicyDetail(policy.getInformationDetailId());
				
				policyDetail.setTitle(article.getTitle());
				policyDetail.setSource(article.getArticleData().getCopyfrom()); //来源
				policyDetail.setBrowseNumber(article.getHits()); //评论次数
				policyDetail.setContent(article.getArticleData().getContent()); //文章内容
				
				
				policy.setInformationDetailId(policyDetail.getId());
				policy.setTitle(policyDetail.getTitle()); //咨询标题
				policy.setImageAdd(article.getImage());
				policy.setCommentNum(policyDetail.getBrowseNumber()); //评论次数	
				policy.setLables(article.getKeywords());	//标签 关键字
				policy.setBusinessInformation(article.getBusinessInformation());
				policy.setAllRange(article.getAllRange());
				policy.setBusinessNature(article.getBusinessNature());
				policy.setAllBusiness(article.getAllBusiness());
				policy.setIndustrialId(article.getIndustrialClassified().getFid());
				policy.setDocType(article.getDocType());
				policy.setApproveStatus(approveStatus);
				policyDetailDao.update(policyDetail);
				policyDao.update(policy);
				break;
			case 5:	//知识
				KnowledgeDetail knowledgeDetail = new KnowledgeDetail();
				Knowledge knowledge = new Knowledge();
				knowledge = this.selectknowledge(article.getId());	//先查数据库原来的值
				knowledgeDetail = this.selectknowledgeDetail(knowledge.getInformationDetailId());
				
				knowledgeDetail.setTitle(article.getTitle());
				knowledgeDetail.setSource(article.getArticleData().getCopyfrom()); //来源
				knowledgeDetail.setBrowseNumber(article.getHits()); //评论次数
				knowledgeDetail.setContent(article.getArticleData().getContent()); //文章内容
				
				knowledge.setInformationDetailId(knowledgeDetail.getId());
				knowledge.setTitle(knowledgeDetail.getTitle()); //咨询标题
				knowledge.setImageAdd(article.getImage());
				knowledge.setCommentNum(knowledgeDetail.getBrowseNumber()); //评论次数	
				knowledge.setLables(article.getKeywords());	//标签 关键字
				knowledge.setBusinessInformation(article.getBusinessInformation());
				knowledge.setAllRange(article.getAllRange());
				knowledge.setBusinessNature(article.getBusinessNature());
				knowledge.setAllBusiness(article.getAllBusiness());
				knowledge.setIndustrialId(article.getIndustrialClassified().getFid());
				knowledge.setDocType(article.getDocType());
				knowledge.setApproveStatus(approveStatus);
				knowledgeDetailDao.update(knowledgeDetail);
				knowledgeDao.update(knowledge);
				break;
			default:
				break;
			}
		}
	}
	
	@Transactional(readOnly = false)
	public void delete(Article article, Boolean isRe) {
		/**更新内容标志: DEL_FLAG_NORMAL=0 发布，DEL_FLAG_DELETE=1 删除*/
		article.setDelFlag(Article.DEL_FLAG_DELETE);
		dao.updateDelFlag(article);
//		dao.updateDelFlag(article.getId(), isRe!=null&&isRe?Article.DEL_FLAG_NORMAL:Article.DEL_FLAG_DELETE);
		// 使用下面方法，以便更新索引。
		//Article article = dao.get(id);
		//article.setDelFlag(isRe!=null&&isRe?Article.DEL_FLAG_NORMAL:Article.DEL_FLAG_DELETE);
		//dao.insert(article);
		//super.delete(article);
	}
	
	/**
	 * 通过编号获取内容标题
	 * @return new Object[]{栏目Id,文章Id,文章标题}
	 */
	public List<Object[]> findByIds(String ids) {
		if(ids == null){
			return new ArrayList<Object[]>();
		}
		List<Object[]> list = Lists.newArrayList();
		String[] idss = StringUtils.split(ids,",");
		Article e = null;
		for(int i=0;(idss.length-i)>0;i++){
			e = dao.get(idss[i]);
			list.add(new Object[]{e.getCategory().getId(),e.getId(),StringUtils.abbr(e.getTitle(),50)});
		}
		return list;
	}
	
	/**
	 * 点击数加一
	 */
	@Transactional(readOnly = false)
	public void updateHitsAddOne(String id) {
		dao.updateHitsAddOne(id);
	}
	
	/**
	 * 更新索引
	 */
	public void createIndex(){
		//dao.createIndex();
	}
	
	/**
	 * 全文检索
	 */
	//FIXME 暂不提供检索功能
	public Page<Article> search(Page<Article> page, String q, String categoryId, String beginDate, String endDate){
		
		// 设置查询条件
//		BooleanQuery query = dao.getFullTextQuery(q, "title","keywords","description","articleData.content");
//		
//		// 设置过滤条件
//		List<BooleanClause> bcList = Lists.newArrayList();
//
//		bcList.add(new BooleanClause(new TermQuery(new Term(Article.FIELD_DEL_FLAG, Article.DEL_FLAG_NORMAL)), Occur.MUST));
//		if (StringUtils.isNotBlank(categoryId)){
//			bcList.add(new BooleanClause(new TermQuery(new Term("category.ids", categoryId)), Occur.MUST));
//		}
//		
//		if (StringUtils.isNotBlank(beginDate) && StringUtils.isNotBlank(endDate)) {   
//			bcList.add(new BooleanClause(new TermRangeQuery("updateDate", beginDate.replaceAll("-", ""),
//					endDate.replaceAll("-", ""), true, true), Occur.MUST));
//		}   
		
		//BooleanQuery queryFilter = dao.getFullTextQuery((BooleanClause[])bcList.toArray(new BooleanClause[bcList.size()]));

//		System.out.println(queryFilter);
		
		// 设置排序（默认相识度排序）
		//FIXME 暂时不提供lucene检索
		//Sort sort = null;//new Sort(new SortField("updateDate", SortField.DOC, true));
		// 全文检索
		//dao.search(page, query, queryFilter, sort);
		// 关键字高亮
		//dao.keywordsHighlight(query, page.getList(), 30, "title");
		//dao.keywordsHighlight(query, page.getList(), 130, "description","articleData.content");
		
		return page;
	}

	@Transactional(readOnly = false)
	public void approve(Article article) {
		dao.updateDelFlag(article);
		String articleId = article.getId();
		String status = article.getDelFlag();
		// 修改状态
		if("3".equals(article.getCategory().getId())){
			// 资讯
			informationDao.updateStatus(article);
		}else if("4".equals(article.getCategory().getId())){
			// 政策
			policyDao.updateStatus(article);
		} else if("5".equals(article.getCategory().getId())){
			// 知识
			knowledgeDao.updateStatus(article);
		}
	}
}
