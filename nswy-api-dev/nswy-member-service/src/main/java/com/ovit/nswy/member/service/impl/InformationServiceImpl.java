package com.ovit.nswy.member.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;
import com.ovit.nswy.member.mapper.*;
import com.ovit.nswy.member.model.*;
import com.ovit.nswy.member.service.InformationService;
import com.ovit.nswy.member.service.LoginUserService;
import com.ovit.nswy.member.service.PerfectInformationService;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;

@Service(value = "inForMationService")
public class InformationServiceImpl implements InformationService {

    @Autowired
    private InformationMapper informationMapper;
    @Autowired
    private InformationDetailMapper informationDetailMapper;
    @Autowired
    private InfoCommentMapper infoCommentMapper;
    @Autowired
    private CorpInfoMapper corpInfoMapper;
    @Autowired
    private ExpertInfoMapper expertInfoMapper;
    @Autowired
    private PolicyMapper policyMapper;
    @Autowired
    private KnowledgeMapper knowledgeMapper;
    @Autowired
    private LoginUserMapper loginUserMapper;
    @Autowired
    private StandardMapper standardMapper;
    @Autowired
    private LoginUserService loginUserService;
    @Autowired
    private PerfectInformationService perfectInformationService;

    @Override
    public PageInfo<Information> findInForMation(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Information> list = informationMapper.findInForMation();
        return new PageInfo<Information>(list);
    }

    @Override
    public List<Map<String,Object>> findInForMationDetail(String id) {
        return informationDetailMapper.findInformationDetail(Integer.valueOf(id));
    }

    @Override
    public PageInfo<Information> findInformationTitle(int pageNum, int pageSize, Information info) {
        PageHelper.startPage(pageNum, pageSize);
        List<Information> list = informationMapper.findInformationTitle(info);
        return new PageInfo<>(list);
    }

    @Override
    public PageInfo<InfoComment> findInfoComment(int pageNum, int pageSize, Integer informationDetailId) {
        PageHelper.startPage(pageNum, pageSize);
        InfoComment info = new InfoComment();
        info.setInformationDetailId(informationDetailId);
        List<InfoComment> infoComment = infoCommentMapper.findInfoComment(info);
        TreeUtil util = new TreeUtil(infoComment);
        List<InfoComment> lists = util.createTreeList();
        return new PageInfo<InfoComment>(lists);
    }

    @Override
    public PageInfo<InfoComment> findMyInfoComment(int pageNum, int pageSize, Map<String, Object> map) {
        PageHelper.startPage(pageNum, pageSize);
        List<InfoComment> infoComment = infoCommentMapper.findMyInfoComment(map);
        TreeUtil util = new TreeUtil(infoComment);
        List<InfoComment> lists = util.createTreeList();
        return new PageInfo<InfoComment>(lists);
    }

    @Override
    public InfoComment update(InfoComment info) {
        List<InfoComment> infoComment = infoCommentMapper.findInfoCommentId(info);
        if (!infoComment.isEmpty()) {
            InfoComment record = infoComment.get(0);
            record.setThumbUpNum(record.getThumbUpNum() + 1);
            infoCommentMapper.updateThumbUpNum(record);
            return record;
        }
        return null;
    }

    @Override
    public void insert(InfoComment info) {
        infoCommentMapper.insert(info);
    }

    @Override
    public void insertInformationDetail(InformationDetail info) {
        informationDetailMapper.insert(info);
    }

    @Override
    public void insertInfo(Information info) {
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        info.setArticleId(uuid);
        informationMapper.insert(info);
        Article article = new Article(3);
        article.setId(uuid);
        article.setAuthor("1");
        article.setCreateDate(new Date());
        article.setUpdateDate(new Date());
        article.setTitle(info.getTitle());
        article.setKeywords(info.getLabel());
        article.setDocType(info.getDocType());
        knowledgeMapper.insertArticle(article);
    }

    @Override
    public Integer findInfoCommentCount(Integer informationDetailId) {
        InfoComment info = new InfoComment();
        info.setInformationDetailId(informationDetailId);
        List<InfoComment> infoComment = infoCommentMapper.findInfoComment(info);
        return infoComment.size();
    }

    @Override
    public List<InfoComment> findMyComment(int pageNum, int pageSize, InfoComment info) {
        PageHelper.startPage(pageNum, pageSize);
        List<InfoComment> infoComment = infoCommentMapper.findMyComment(info);
        TreeUtil util = new TreeUtil(infoComment);
        List<InfoComment> lists = util.createTreeList();
        return lists;
    }

    @Override
    public InformationDetail upvote(Integer id) {
        InformationDetail infor = informationDetailMapper.selectByPrimaryKey(id);
        if (infor != null) {
            infor.setThumbUpNum(infor.getThumbUpNum() + 1);
            informationDetailMapper.updateThumbUpNum(infor);
            return infor;
        }
        return null;
    }

    @Override
    public void updateBrowse(Map<String, Object> map) {
        informationDetailMapper.updateBrowse(map);
    }

    @Override
    public PageInfo<InformationDetail> findByLabel(Map<String,Object> map, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<InformationDetail> lists = informationDetailMapper.findByLabel(map);
        for (int i=0; i < lists.size(); i++) {
            InformationDetail info = lists.get(i);
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
        return new PageInfo<InformationDetail>(lists);
    }

    @Override
    public PageInfo<InfoComment> replylist(int pageNum, int pageSize, Map<String, String> map) {
        PageHelper.startPage(pageNum, 10);
        List<InfoComment> list = infoCommentMapper.replylist(map);
        return new PageInfo<InfoComment>(list);
    }

    @Override
    public PageInfo<InfoComment> replyToOther(int pageNum, int pageSize, Integer postId) {
        PageHelper.startPage(pageNum, 10);
        List<InfoComment> list = infoCommentMapper.replyToOther(postId);
        return new PageInfo<InfoComment>(list);
    }

    @Override
    public List<InformationDetail> newinfo() {
        return informationDetailMapper.newinfo();
    }

    /**
     * 查找上一下一页
     * @param id
     * @return
     */
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
        InformationDetail temp = informationDetailMapper.selectByPrimaryKey(id-1);
        if(null != temp){
            return id-1;
        }
        return getUpPage(id-1);
    }

    int getNextPage(int id) {
        if (id  > getMaxPage()) {
            return -1;
        }
        InformationDetail temp = informationDetailMapper.selectByPrimaryKey(id+1);
        if(null != temp){
            return  id+1;
        }
        return getNextPage(id+1);
    }

    int getMinPage() {
        return informationDetailMapper.selectMinId();
    }

    int getMaxPage() {
        return informationDetailMapper.selectMaxId();
    }
    @Override
    public List<Information> find(String docType) {
        return informationMapper.find(docType);
    }

    @Override
    public List<InfoComment> findInfoComments(Map<String, Object> map) {
        return infoCommentMapper.findInfoComments(map);
    }

    @Override
    public List<InfoComment> listComment(Map<String, Object> map) {
        List<InfoComment> infoComments = infoCommentMapper.listComment(map);
        for(InfoComment info : infoComments){
            map.put("information_detail_id",info.getInformationDetailId());
            List<InfoComment> list  = infoCommentMapper.listCommentDetail(map);
            info.setPostNum(list.size());
        }

        return infoComments;
    }

    @Override
    public void listCommentCounts(Map<String, Object> map) {
        List<InformationDetail> infoComments  = (List<InformationDetail>) map.get("lists");
        for(InformationDetail info : infoComments){
            map.put("information_detail_id",info.getId());
            List<InfoComment> list  = infoCommentMapper.listCommentDetail(map);
            info.setPostNum(String.valueOf(list.size()));
        }

    }

    @Override
    public PageInfo<Map<String,Object>> findComment(Map<String, Object> params) {
        PageHelper.startPage(MapUtils.getInteger(params,"pageNum"), 10);
        String type = MapUtils.getString(params,"type");
        if(type.equals("information")){
            params.put("TABLE_NAME","info_comment");
        }else if(type.equals("knowledge")){
            params.put("TABLE_NAME","knowledge_comment");
        }else if(type.equals("policy")){
            params.put("TABLE_NAME","policy_comment");
        }
        String account = MapUtils.getString(params,"account");
        if(account.equals("")){
            params.put("account","unfinded");
        }
        Map<String,Object> maps = new HashMap<String,Object>();
        List<Map<String,Object>> list = infoCommentMapper.findComment(params);
        for(int i=0;i<list.size();i++){
            Map<String,Object> info = list.get(i);
            String userAccount  = String.valueOf(info.get("user_account"));
            if(StringUtil.isNotEmpty(account)){
                if(account.equals(userAccount)){
                    info.put("isReply",true);
                }else{
                    info.put("isReply",false);
                }
            }else{
                info.put("isReply",false);
            }
            info.put("replyBoxShow",false);
            maps.put("account",userAccount);
            List<Map<String, Object>> loginusers= loginUserMapper.listLoginUser(maps);
            info.put("author",loginusers.get(0));
            Map<String,Object> replyfindmap = new HashMap<String,Object>();
            replyfindmap.put("id",info.get("id").toString());
            if(type.equals("information")){
                replyfindmap.put("TABLE_NAME","info_comment");
            }else if(type.equals("knowledge")){
                replyfindmap.put("TABLE_NAME","knowledge_comment");
            }else if(type.equals("policy")){
                replyfindmap.put("TABLE_NAME","policy_comment");
            }
            List<Map<String, Object>> replyAuthor  = infoCommentMapper.findCommentHF(replyfindmap);
            if(replyAuthor.size()>0){
                for(int j=0;j<replyAuthor.size();j++){
                    Map<String,Object> replymap = new HashMap<String,Object>();
                    Map<String,Object> reply = replyAuthor.get(j);
                    String accounts = String.valueOf(reply.get("user_account"));
                    if(StringUtil.isNotEmpty(account)){
                        if(account.equals(accounts)){
                            info.put("isReply",true);
                        }else{
                            info.put("isReply",false);
                        }
                    }else{
                        info.put("isReply",false);
                    }
                    reply.put("replyBoxShow",false);
                    replymap.put("account",accounts);
                    List<Map<String, Object>> loginuser= loginUserMapper.listLoginUser(replymap);
                    reply.put("author",loginuser.get(0));
                    Map<String,Object> replyfindmaps = new HashMap<String,Object>();
                    replyfindmaps.put("id",reply.get("id").toString());
                    if(type.equals("information")){
                        replyfindmaps.put("TABLE_NAME","info_comment");
                    }else if(type.equals("knowledge")){
                        replyfindmaps.put("TABLE_NAME","knowledge_comment");
                    }else if(type.equals("policy")){
                        replyfindmaps.put("TABLE_NAME","policy_comment");
                    }
                    List<Map<String, Object>> replyAuthors  = infoCommentMapper.findCommentHFDetail(replyfindmaps);
                    reply.put("replyAuthor",replyAuthors.get(0));
                    list.add(reply);
                }
            }

        }
        return new PageInfo<Map<String,Object>>(list);
    }

    @Override
    public void deleteMyComment(Map<String, Object> params){
        String type = MapUtils.getString(params,"type");
        if(type.equals("information")){
            params.put("TABLE_NAME","info_comment");
        }else if(type.equals("knowledge")){
            params.put("TABLE_NAME","knowledge_comment");
        }else if(type.equals("policy")){
            params.put("TABLE_NAME","policy_comment");
        }
        infoCommentMapper.deleteMyComment(params);
    }

    @Override
    public List<InformationDetail> findColumnInforMation(Map<String, Object> map) {
        List<InformationDetail> list = informationDetailMapper.findColumnInforMation(map);
        return list;
    }

    class TreeUtil {
        private List<InfoComment> treeNodeList = new ArrayList<InfoComment>();

        public TreeUtil(List<InfoComment> list) {
            for (InfoComment comment : list) {
                if (null == comment.getPostId()) {
                    comment.setPostId(0);
                }
                treeNodeList.add(comment);
            }
        }

        public List<InfoComment> createTreeList() {
            InfoComment root = generateTreeNode(0);
            return root.getList();
        }

        /**
         * @param nodeId
         * @return
         */
        private InfoComment getNodeById(int nodeId) {
            InfoComment treeNode = new InfoComment();
            for (InfoComment item : treeNodeList) {
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
        private List<InfoComment> getChildrenNodeById(int nodeId) {
            List<InfoComment> childrenTreeNode = new ArrayList<InfoComment>();
            for (InfoComment item : treeNodeList) {
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
        private InfoComment generateTreeNode(int rootId) {
            InfoComment root = this.getNodeById(rootId);
            List<InfoComment> childrenTreeNode = this.getChildrenNodeById(rootId);
            for (InfoComment item : childrenTreeNode) {
                InfoComment node = this.generateTreeNode(item.getId());
                if (null == root.getList()) {
                    List<InfoComment> children = new ArrayList<InfoComment>();
                    root.setList(children);
                }
                root.getList().add(node);
            }
            return root;
        }
    }

    @Override
    public List<Map<String, Object>> getBriefNews() {
        return informationMapper.getBriefNews();
    }

    @Override
    public Map<String, Object> relationWithInformation(int id ,int type) {
        String speciesIds = "";
        if (1 == type) {
            Information information = informationMapper.findInformationByDetailId(id);
            speciesIds = information.getSpeciesId();
        } else if (2 == type) {
            Policy policy = policyMapper.findPolicyByDetailId(id);
            speciesIds = policy.getSpeciesId();
        } else if (3 == type ){
            Knowledge knowledge = knowledgeMapper.findKnowLedgeByDetailId(id);
            speciesIds = knowledge.getSpeciesId();
        } else if (4 == type) {
            String speciesId = standardMapper.findSpeciesIdFromStandard(id);
            speciesIds = speciesId.replaceAll(" ",",");
        }
        List<Map<String,Object>> expertInfoList = new ArrayList<>();
        List<Map<String,Object>> corpInfoList = new ArrayList<>();
        // 关联物种
        if(org.apache.commons.lang.StringUtils.isNotBlank(speciesIds)&&speciesIds.indexOf(",") == -1){// 一个
            expertInfoList = expertInfoMapper.selectExpertBySpecies(speciesIds);
            corpInfoList = corpInfoMapper.selectCorpBySpecies(speciesIds);
        } else if (org.apache.commons.lang.StringUtils.isNotBlank(speciesIds) && speciesIds.indexOf(",") != -1) {//多个
            List<Map<String,Object>> expertInfoListTemp = new ArrayList<>();
            List<Map<String,Object>> corpInfoListTemp = new ArrayList<>();
            List<String> specList = Arrays.asList(speciesIds.split(","));
            List<Map<String,Object>> eList;
            List<Map<String,Object>> cList;
            for (String temp : specList) {
                eList = expertInfoMapper.selectExpertBySpecies(temp);
                cList = corpInfoMapper.selectCorpBySpecies(temp);
                if (!eList.isEmpty())
                    expertInfoListTemp.addAll(eList);
                if (!cList.isEmpty())
                    corpInfoListTemp.addAll(cList);
            }
            // 取前6个
            if (expertInfoListTemp.size() > 6) {
                expertInfoList = expertInfoListTemp.subList(0, 5);
            } else {
                expertInfoList = expertInfoListTemp;
            }
            if (corpInfoListTemp.size() > 6) {
                corpInfoList = corpInfoListTemp.subList(0, 5);
            } else {
                corpInfoList = corpInfoListTemp;
            }
        }
        // 拼接结果
        if(expertInfoList.isEmpty()){
            expertInfoList = expertInfoMapper.selectExpertBySpecies("");
        }
        if(corpInfoList.isEmpty()){
            corpInfoList = corpInfoMapper.selectCorpBySpecies("");
        }
        List<Map<String, Object>> expertList = new ArrayList<>();
        if (!expertInfoList.isEmpty()) {
            Map<String, Object> tempMap;
            for (Map<String,Object> expertInfo : expertInfoList) {
                tempMap = new HashMap<>();
                tempMap.put("name", expertInfo.get("name"));
                tempMap.put("img",  expertInfo.get("img"));
                tempMap.put("link", expertInfo.get("account"));

                expertList.add(tempMap);
            }
        }
        List<Map<String, Object>> corpList = new ArrayList<>();
        if (!corpInfoList.isEmpty()) {
            Map<String, Object> tempMap;
            for (Map<String,Object> corpInfo : corpInfoList) {
                tempMap = new HashMap<>();
                String img ="";
                 if(!StringUtils.isEmpty(corpInfo.get("img"))){
                        if(corpInfo.get("img").toString().indexOf(",") == -1){
                            img = corpInfo.get("img").toString();
                        }else{
                           img = Arrays.asList(corpInfo.get("img").toString().split(",")).get(0);
                        }
                 }
                tempMap.put("name", corpInfo.get("name"));
                tempMap.put("img",img );
                tempMap.put("link",  corpInfo.get("account").toString());
                corpList.add(tempMap);
            }
        }
        Map<String, Object> result = new HashMap<>();
        result.put("expertList", expertList);
        result.put("corpList", corpList);
        return result;
    }

    /**
     * 相关资讯
     * @param map
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public PageInfo<InformationDetail> relevantInformation(Map<String, Object> map, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<InformationDetail> lists = informationDetailMapper.relevantInformation(map);
        for (int i=0; i < lists.size(); i++) {
            InformationDetail info = lists.get(i);
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
        return new PageInfo<InformationDetail>(lists);
    }

    @Override
    public List<Map<String, Object>> dynamicCoverList(String account) {
        return informationMapper.dynamicCoverList(account);
    }
}
