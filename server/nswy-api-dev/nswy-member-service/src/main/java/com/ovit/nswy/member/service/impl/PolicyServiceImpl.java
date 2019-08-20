package com.ovit.nswy.member.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ovit.nswy.member.mapper.*;
import com.ovit.nswy.member.model.*;
import com.ovit.nswy.member.service.LoginUserService;
import com.ovit.nswy.member.service.PerfectInformationService;
import com.ovit.nswy.member.service.PolicyService;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PolicyServiceImpl implements PolicyService {

    @Autowired
    private PolicyMapper policyMapper;
    @Autowired
    private PolicyDetailMapper policyDetailMapper;
    @Autowired
    private PolicyCommentMapper policyCommentMapper;
    @Autowired
    private KnowledgeMapper knowledgeMapper;

    @Autowired
    private InfoCommentMapper infoCommentMapper;

    @Autowired
    private LoginUserService loginUserService;
    @Autowired
    private PerfectInformationService perfectInformationService;

    @Override
    public List<PolicyDetail> newpolicy() {
        return policyDetailMapper.newpolicy();
    }

    @Override
    public PageInfo<Policy> findPolicy(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Policy> list = policyMapper.findPolicy();
        return new PageInfo<Policy>(list);
    }

    @Override
    public List<Map<String,Object>> findPolicyDetail(String id) {
        return policyDetailMapper.findPolicyDetail(Integer.valueOf(id));
    }

    @Override
    public PageInfo<Policy> findPolicyTitle(int pageNum, Policy policy, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Policy> list = policyMapper.findPolicyTitle(policy);
        return new PageInfo<>(list);
    }

    @Override
    public PageInfo<PolicyComment> findPolicyComment(int pageNum, int pageSize, Integer informationDetailId) {
        PageHelper.startPage(pageNum, pageSize);
        PolicyComment info = new PolicyComment();
        info.setInformationDetailId(informationDetailId);
        List<PolicyComment> policyComment = policyCommentMapper.findPolicyComment(info);
        TreeUtil util = new TreeUtil(policyComment);
        List<PolicyComment> lists = util.createTreeList();
        return new PageInfo<PolicyComment>(lists);
    }

    @Override
    public Integer findPolicyCommentCount(Integer id) {
        PolicyComment info = new PolicyComment();
        info.setInformationDetailId(id);
        List<PolicyComment> policyComment = policyCommentMapper.findPolicyComment(info);
        return policyComment.size();
    }

    @Override
    public PageInfo<PolicyComment> findMyComment(int pageNum, int pageSize, PolicyComment info) {
        PageHelper.startPage(pageNum, pageSize);
        List<PolicyComment> policyComment = policyCommentMapper.findMyComment(info);
        TreeUtil util = new TreeUtil(policyComment);
        List<PolicyComment> lists = util.createTreeList();
        return new PageInfo<PolicyComment>(lists);
    }

    @Override
    public PolicyComment update(PolicyComment info) {
        List<PolicyComment> policyComment = policyCommentMapper.findPolicyCommentId(info);
        if (policyComment.size() > 0) {
            PolicyComment record = policyComment.get(0);
            record.setThumbUpNum(record.getThumbUpNum() + 1);
            policyCommentMapper.updateThumbUpNum(record);
            return record;
        }
        return null;
    }

    @Override
    public void insert(PolicyComment info) {
        policyCommentMapper.insert(info);
    }

    @Override
    public int insertPolicyDetail(PolicyDetail info) {
        return policyDetailMapper.insert(info);
    }

    @Override
    public int insertPolicy(Policy policy) {
        // TODO
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        policy.setArticleId(uuid);
        Article article = new Article(4);
        article.setId(uuid);
        article.setAuthor("1");
        article.setCreateDate(new Date());
        article.setUpdateDate(new Date());
        article.setTitle(policy.getTitle());
        article.setKeywords(policy.getLabel());
        article.setDocType(policy.getDocType());
        knowledgeMapper.insertArticle(article);
        return policyMapper.insert(policy);
    }

    @Override
    public PolicyDetail upvote(Integer id) {
        PolicyDetail policyDetail = policyDetailMapper.selectByPrimaryKey(id);
        if (policyDetail != null) {
            policyDetail.setThumbUpNum(policyDetail.getThumbUpNum() + 1);
            policyDetailMapper.updateThumbUpNum(policyDetail);
            return policyDetail;
        }
        return null;
    }

    @Override
    public PageInfo<Policy> findIndexPolicy(int pageNum, int pageSize, String docType) {
        PageHelper.startPage(pageNum, pageSize);
        Policy policy = new Policy();
        policy.setDocType(docType);
        List<Policy> list = policyMapper.findIndexPolicy(policy);
        return new PageInfo<Policy>(list);
    }

    @Override
    public PageInfo<PolicyComment> replylist(int pageNum, int pageSize, Map<String, String> map) {
        PageHelper.startPage(pageNum, 10);
        List<PolicyComment> list = policyCommentMapper.replylist(map);
        return new PageInfo<PolicyComment>(list);
    }

    @Override
    public PageInfo<PolicyComment> replyToOther(int pageNum, int pageSize, Integer postId) {
        PageHelper.startPage(pageNum, 10);
        List<PolicyComment> list = policyCommentMapper.replyToOther(postId);
        return new PageInfo<PolicyComment>(list);
    }

    @Override
    public PageInfo<PolicyDetail> findBySpeciesName(Map<String,Object> map, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<PolicyDetail> policyDetailList = policyDetailMapper.findBySpeciesName(map);
        for (int i=0; i < policyDetailList.size(); i++) {
            PolicyDetail info = policyDetailList.get(i);
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
        return new PageInfo<PolicyDetail>(policyDetailList);
    }

    @Override
    public void listCommentCounts(Map<String, Object> map) {
        List<PolicyDetail> infoComments  = (List<PolicyDetail>) map.get("lists");
        for(PolicyDetail info : infoComments){
            map.put("information_detail_id",info.getId());
            List<InfoComment> list  = infoCommentMapper.listCommentDetail(map);
            info.setPostNum(String.valueOf(list.size()));
        }

    }

    @Override
    public List<PolicyComment> findPolicyComments(Map<String, Object> map) {
        return policyCommentMapper.findPolicyComments(map);
    }

    @Override
    public List<PolicyComment> listComment(Map<String, Object> map) {
        return policyCommentMapper.listComment(map);
    }

    class TreeUtil {
        private List<PolicyComment> treeNodeList = new ArrayList<PolicyComment>();

        public TreeUtil(List<PolicyComment> list) {
            for (PolicyComment comment : list) {
                if (null == comment.getPostId()) {
                    comment.setPostId(0);
                }
                treeNodeList.add(comment);
            }
        }

        public List<PolicyComment> createTreeList() {
            PolicyComment root = generateTreeNode(0);
            return root.getList();
        }

        /**
         * @param nodeId
         * @return
         */
        private PolicyComment getNodeById(int nodeId) {
            PolicyComment treeNode = new PolicyComment();
            for (PolicyComment item : treeNodeList) {
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
        private List<PolicyComment> getChildrenNodeById(int nodeId) {
            List<PolicyComment> childrenTreeNode = new ArrayList<PolicyComment>();
            for (PolicyComment item : treeNodeList) {
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
        private PolicyComment generateTreeNode(int rootId) {
            PolicyComment root = this.getNodeById(rootId);
            List<PolicyComment> childrenTreeNode = this.getChildrenNodeById(rootId);
            for (PolicyComment item : childrenTreeNode) {
                PolicyComment node = this.generateTreeNode(item.getId());
                if (null == root.getList()) {
                    List<PolicyComment> children = new ArrayList<PolicyComment>();
                    root.setList(children);
                }
                root.getList().add(node);
            }
            return root;
        }
    }

    @Override
    public List<Map<String, Object>> getBriefNews() {
        return policyMapper.getBriefNews();
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
        PolicyDetail temp = policyDetailMapper.selectByPrimaryKey(id-1);
        if(null != temp){
            return id-1;
        }
        return getUpPage(id-1);
    }

    int getNextPage(int id) {
        if (id  > getMaxPage()) {
            return -1;
        }
        PolicyDetail temp = policyDetailMapper.selectByPrimaryKey(id+1);
        if(null != temp){
            return  id+1;
        }
        return getNextPage(id+1);
    }

    int getMinPage() {
        return policyDetailMapper.selectMinId();
    }

    int getMaxPage() {
        return policyDetailMapper.selectMaxId();
    }

    /**
     * 相关政策
     * @param map
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public PageInfo<PolicyDetail> relevantPolicy(Map<String, Object> map, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<PolicyDetail> policyDetailList = policyDetailMapper.relevantPolicy(map);
        for (int i=0; i < policyDetailList.size(); i++) {
            PolicyDetail info = policyDetailList.get(i);
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
        return new PageInfo<PolicyDetail>(policyDetailList);
    }

}