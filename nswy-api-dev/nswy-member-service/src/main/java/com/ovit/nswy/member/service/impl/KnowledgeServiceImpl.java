package com.ovit.nswy.member.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ovit.nswy.member.mapper.InfoCommentMapper;
import com.ovit.nswy.member.mapper.KnowledgeCommentMapper;
import com.ovit.nswy.member.mapper.KnowledgeDetailMapper;
import com.ovit.nswy.member.mapper.KnowledgeMapper;
import com.ovit.nswy.member.model.*;
import com.ovit.nswy.member.service.KnowledgeService;
import com.ovit.nswy.member.service.LoginUserService;
import com.ovit.nswy.member.service.PerfectInformationService;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class KnowledgeServiceImpl implements KnowledgeService {

    @Autowired
    private KnowledgeMapper knowledgeMapper;
    @Autowired
    private KnowledgeDetailMapper knowledgeDetailMapper;
    @Autowired
    private KnowledgeCommentMapper knowledgeCommentMapper;
    @Autowired
    private InfoCommentMapper infoCommentMapper;
    @Autowired
    private LoginUserService loginUserService;
    @Autowired
    private PerfectInformationService perfectInformationService;

    @Override
    public PageInfo<Knowledge> findKnowLedge(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Knowledge> list = knowledgeMapper.findKnowLedge();
        return new PageInfo<Knowledge>(list);
    }

    @Override
    public List<Map<String,Object>> findKnowLedgeDetail(String id) {
        return knowledgeDetailMapper.findKnowLedgeDetail(Integer.valueOf(id));
    }

    @Override
    public PageInfo<Knowledge> findKnowLedgeTitle(int pageNum, int pageSize, Knowledge knowledge) {
        PageHelper.startPage(pageNum, pageSize);
        List<Knowledge> list = knowledgeMapper.findKnowLedgeTitle(knowledge);
        return new PageInfo<>(list);

    }

    @Override
    public PageInfo<KnowledgeComment> findKnowledgeComment(int pageNum, int pageSize, Integer id) {
        PageHelper.startPage(pageNum, pageSize);
        KnowledgeComment info = new KnowledgeComment();
        info.setInformationDetailId(id);
        List<KnowledgeComment> policyComment = knowledgeCommentMapper.findKnowledgeComment(info);
        TreeUtil util = new TreeUtil(policyComment);
        List<KnowledgeComment> lists = util.createTreeList();
        return new PageInfo<KnowledgeComment>(lists);
    }

    @Override
    public Integer findKnowledgeCommentCount(Integer id) {
        KnowledgeComment info = new KnowledgeComment();
        info.setInformationDetailId(id);
        List<KnowledgeComment> policyComment = knowledgeCommentMapper.findKnowledgeComment(info);
        return policyComment.size();
    }

    @Override
    public PageInfo<KnowledgeComment> findMyComment(int pageNum, int pageSize, KnowledgeComment info) {
        PageHelper.startPage(pageNum, pageSize);
        List<KnowledgeComment> policyComment = knowledgeCommentMapper.findMyComment(info);
        TreeUtil util = new TreeUtil(policyComment);
        List<KnowledgeComment> lists = util.createTreeList();
        return new PageInfo<KnowledgeComment>(lists);
    }

    @Override
    public KnowledgeComment update(KnowledgeComment info) {
        List<KnowledgeComment> policyComment = knowledgeCommentMapper.findKnowledgeCommentId(info);
        if (policyComment.size() > 0) {
            KnowledgeComment record = policyComment.get(0);
            record.setThumbUpNum(record.getThumbUpNum() + 1);
            knowledgeCommentMapper.updateThumbUpNum(record);
            return record;
        }
        return null;
    }

    @Override
    public void insert(KnowledgeComment info) {
        knowledgeCommentMapper.insert(info);
    }

    @Override
    public KnowledgeDetail upvote(Integer id) {
        KnowledgeDetail infor = knowledgeDetailMapper.selectByPrimaryKey(id);
        if (infor != null) {
            infor.setThumbUpNum(infor.getThumbUpNum() + 1);
            knowledgeDetailMapper.updateThumbUpNum(infor);
            return infor;
        }
        return null;
    }

    @Override
    public List<Map<String, Object>> findShow() {
        return knowledgeMapper.findShow();
    }

    @Override
    public PageInfo<KnowledgeComment> replylist(int pageNum, int pageSize, Map<String, String> map) {
        PageHelper.startPage(pageNum, 10);
        List<KnowledgeComment> list = knowledgeCommentMapper.replylist(map);
        return new PageInfo<KnowledgeComment>(list);
    }

    @Override
    public void insertKnowledge(Knowledge knowledge) {
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        knowledge.setArticleId(uuid);
        knowledgeMapper.insert(knowledge);
        Article article = new Article(5);
        article.setId(uuid);
        article.setAuthor("1");
        article.setCreateDate(new Date());
        article.setUpdateDate(new Date());
        article.setTitle(knowledge.getTitle());
        article.setKeywords(knowledge.getLabel());
        article.setDocType(knowledge.getDocType());
        knowledgeMapper.insertArticle(article);
    }

    @Override
    public PageInfo<KnowledgeComment> replyToOther(int pageNum, int pageSize, Integer postId) {
        PageHelper.startPage(pageNum, 10);
        List<KnowledgeComment> list=knowledgeCommentMapper.replyToOther(postId);
        return new PageInfo<KnowledgeComment>(list);
    }

    @Override
    public PageInfo<KnowledgeDetail> findBySpeciesName(Map<String,Object> map, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<KnowledgeDetail> knowledgeDetailList = knowledgeDetailMapper.findBySpeciesName(map);
        for (int i=0; i < knowledgeDetailList.size(); i++) {
            KnowledgeDetail info = knowledgeDetailList.get(i);
            String account = info.getAccount();
            Map<String,Object> useTemplate = loginUserService.findUseTemplate(account);
            if (MapUtils.isEmpty(useTemplate)) {
                info.setAvatar("");
            } else {
                Map<String,Object> yearInfo = loginUserService.findYearInfo(account);
                useTemplate.put("yearId", MapUtils.getString(yearInfo, "id"));
                Map<String,Object> privacyInfo = perfectInformationService.findPrivacyInfo(useTemplate);
                String image = MapUtils.getString(privacyInfo, "image");
                info.setAvatar(image);
            }
        }
        return new PageInfo<KnowledgeDetail>(knowledgeDetailList);
    }

    @Override
    public List<KnowledgeDetail> newknowledge() {
        return knowledgeDetailMapper.newknowledge();
    }

    @Override
    public int insertKnowledgeDetail(KnowledgeDetail info) {
        return knowledgeDetailMapper.insert(info);
    }

    @Override
    public List<KnowledgeComment> findKnowledgeComments(Map<String, Object> map) {
        return knowledgeCommentMapper.findKnowledgeComments(map);
    }

    @Override
    public List<KnowledgeComment> listComment(Map<String, Object> map) {
        return knowledgeCommentMapper.listComment(map);
    }

    class TreeUtil {
        private List<KnowledgeComment> treeNodeList = new ArrayList<KnowledgeComment>();

        public TreeUtil(List<KnowledgeComment> list) {
            for (KnowledgeComment comment : list) {
                if (null == comment.getPostId()) {
                    comment.setPostId(0);
                }
                treeNodeList.add(comment);
            }
        }

        public List<KnowledgeComment> createTreeList() {
            KnowledgeComment root = generateTreeNode(0);
            return root.getList();
        }

        /**
         * @param nodeId
         * @return
         */
        private KnowledgeComment getNodeById(int nodeId) {
            KnowledgeComment treeNode = new KnowledgeComment();
            for (KnowledgeComment item : treeNodeList) {
                if (item.getId() == nodeId) {
                    treeNode = item;
                    break;
                }
            }
            return treeNode;
        }

        /**
         * @param nodeId
         * @return
         */
        private List<KnowledgeComment> getChildrenNodeById(int nodeId) {
            List<KnowledgeComment> childrenTreeNode = new ArrayList<KnowledgeComment>();
            for (KnowledgeComment item : treeNodeList) {
                if (item.getPostId().intValue() == nodeId) {
                    childrenTreeNode.add(item);
                }
            }
            return childrenTreeNode;
        }

        /**
         * 递归生成Tree结构数据
         *
         * @param rootId
         * @return
         */
        private KnowledgeComment generateTreeNode(int rootId) {
            KnowledgeComment root = this.getNodeById(rootId);
            List<KnowledgeComment> childrenTreeNode = this.getChildrenNodeById(rootId);
            for (KnowledgeComment item : childrenTreeNode) {
                KnowledgeComment node = this.generateTreeNode(item.getId());
                if (null == root.getList()) {
                    List<KnowledgeComment> children = new ArrayList<KnowledgeComment>();
                    root.setList(children);
                }
                root.getList().add(node);
            }
            return root;
        }
    }
    @Override
    public void listCommentCounts(Map<String, Object> map) {
        List<KnowledgeDetail> infoComments  = (List<KnowledgeDetail>) map.get("lists");
        for(KnowledgeDetail info : infoComments){
            map.put("information_detail_id",info.getId());
            List<InfoComment> list  = infoCommentMapper.listCommentDetail(map);
            info.setPostNum(String.valueOf(list.size()));
        }

    }

    @Override
    public List<Map<String, Object>> getBriefNews() {
        return knowledgeMapper.getBriefNews();
    }

    @Override
    public Map<String, Object> findUpAndNextPage(int id) {
        int upPage = getUpPage(id);
        int nextPage = getNextPage(id);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("upPage", upPage);
        map.put("nextPage", nextPage);
        return map;
    }
    int getUpPage(int id) {
        if (id  < getMinPage()) {
            return -1;
        }
        KnowledgeDetail temp = knowledgeDetailMapper.selectByPrimaryKey(id-1);
        if(null != temp){
            return id-1;
        }
        return getUpPage(id-1);
    }

    int getNextPage(int id) {
        if (id  > getMaxPage()) {
            return -1;
        }
        KnowledgeDetail temp = knowledgeDetailMapper.selectByPrimaryKey(id+1);
        if(null != temp){
            return  id+1;
        }
        return getNextPage(id+1);
    }

    int getMinPage() {
        return knowledgeDetailMapper.selectMinId();
    }

    int getMaxPage() {
        return knowledgeDetailMapper.selectMaxId();
    }

    /**
     * 相关知识
     * @param map
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public PageInfo<KnowledgeDetail> relevantKnowledge(Map<String, Object> map, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<KnowledgeDetail> knowledgeDetailList = knowledgeDetailMapper.relevantKnowledge(map);
        for (int i=0; i < knowledgeDetailList.size(); i++) {
            KnowledgeDetail info = knowledgeDetailList.get(i);
            String account = info.getAccount();
            Map<String,Object> useTemplate = loginUserService.findUseTemplate(account);
            if (MapUtils.isEmpty(useTemplate)) {
                info.setAvatar("");
            } else {
                Map<String,Object> yearInfo = loginUserService.findYearInfo(account);
                useTemplate.put("yearId", MapUtils.getString(yearInfo, "id"));
                Map<String,Object> privacyInfo = perfectInformationService.findPrivacyInfo(useTemplate);
                String image = MapUtils.getString(privacyInfo, "image");
                info.setAvatar(image);
            }
        }
        return new PageInfo<KnowledgeDetail>(knowledgeDetailList);
    }

}
