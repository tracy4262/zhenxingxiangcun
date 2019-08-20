package com.ovit.nswy.member.service;

import com.github.pagehelper.PageInfo;
import com.ovit.nswy.member.model.InfoComment;
import com.ovit.nswy.member.model.Information;
import com.ovit.nswy.member.model.InformationDetail;

import java.util.List;
import java.util.Map;

public interface InformationService {

    List<InformationDetail> findColumnInforMation(Map<String, Object> map);

    PageInfo<Information> findInForMation(int pageNum, int pageSize);

    List<Map<String,Object>> findInForMationDetail(String id);

    PageInfo<Information> findInformationTitle(int pageNum, int pageSize, Information info);

    PageInfo<InfoComment> findInfoComment(int pageNum, int pageSize, Integer informationDetailId);

    PageInfo<InfoComment> findMyInfoComment(int pageNum, int pageSize, Map<String, Object> map);

    List<InfoComment> findInfoComments(Map<String, Object> map);

    InfoComment update(InfoComment info);

    void insert(InfoComment info);

    void insertInformationDetail(InformationDetail info);

    void insertInfo(Information info);

    List<InfoComment> listComment(Map<String, Object> map);

    PageInfo<Map<String,Object>> findComment(Map<String, Object> map);

    Integer findInfoCommentCount(Integer informationDetailId);

    List<Information> find(String docType);

    List<InfoComment> findMyComment(int pageNum, int pageSize, InfoComment info);

    InformationDetail upvote(Integer id);

    void updateBrowse(Map<String, Object> map);

    PageInfo<InformationDetail> findByLabel(Map<String,Object> map, int pageNum, int pageSize);

    PageInfo<InfoComment> replylist(int pageNum, int pageSize, Map<String, String> map);

    PageInfo<InfoComment> replyToOther(int pageNum, int pageSize, Integer postId);

    List<InformationDetail> newinfo();

    Map<String,Object> findUpAndNextPage(int id);

    List<Map<String, Object>> getBriefNews();

    Map<String, Object> relationWithInformation(int id ,int type);

    void deleteMyComment(Map<String, Object> params);

    void listCommentCounts(Map<String, Object> map);

    /**
     * 相关资讯
     * @param map
     * @param pageNum
     * @param pageSize
     * @return
     */
    PageInfo<InformationDetail> relevantInformation(Map<String,Object> map, int pageNum, int pageSize);

    /**
     * 获取门户中动态轮播图
     * @param account
     * @return
     */
    List<Map<String, Object>> dynamicCoverList(String account);
}
