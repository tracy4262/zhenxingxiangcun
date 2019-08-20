package com.ovit.nswy.member.service.impl;

import com.ovit.nswy.member.mapper.*;
import com.ovit.nswy.member.model.*;
import com.ovit.nswy.member.service.ThumbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.ws.Action;

/**
 * @Author: wxn
 * @Description:
 * @Date :17:04
 */
@Service
public class ThumbServiceImpl implements ThumbService {
    @Autowired
    private ThumbDetailMapper thumbDetailMapper;
    @Autowired
    private ThumbCommentMapper thumbCommentMapper;
    @Autowired
    private InformationDetailMapper informationDetailMapper ;
    @Autowired
    private PolicyDetailMapper policyDetailMapper;
    @Autowired
    private KnowledgeDetailMapper knowledgeDetailMapper ;
    @Autowired
    private InfoCommentMapper infoCommentMapper ;
    @Autowired
    private PolicyCommentMapper policyCommentMapper ;
    @Autowired
    private KnowledgeCommentMapper knowledgeCommentMapper;


    @Override
    public ThumbDetail findThumbDetail(ThumbDetail thumbDetail) {
        return thumbDetailMapper.selectByThumbDetail(thumbDetail);
    }

    @Override
    public int findThumbDetailCount(ThumbDetail thumbDetail) {
        return thumbDetailMapper.findThumbDetailCount(thumbDetail);
    }

    /**
     * 详情页点赞 点赞之后需要更新点赞的文章的点赞总数
     * @param thumbDetail
     */
    @Override
    public void addThumbDetail(ThumbDetail thumbDetail) {
        thumbDetailMapper.insert(thumbDetail);
        String type = thumbDetail.getDetailType() ;
        if("information".trim().equalsIgnoreCase(type)){
            InformationDetail informationDetail = new InformationDetail();
            informationDetail.setId(thumbDetail.getDetailId());
            informationDetailMapper.updateThumbUpNum(informationDetail);
        }else if("policy".trim().equalsIgnoreCase(type)){
            PolicyDetail policyDetail = new PolicyDetail();
            policyDetail.setId(thumbDetail.getDetailId());
            policyDetailMapper.updateThumbUpNum(policyDetail);
        }else if("knowledge".trim().equalsIgnoreCase(type)){
            KnowledgeDetail knowledgeDetail = new KnowledgeDetail();
            knowledgeDetail.setId(thumbDetail.getDetailId());
            knowledgeDetailMapper.updateThumbUpNum(knowledgeDetail);
        }
    }

    @Override
    public ThumbComment findThumbComment(ThumbComment thumbComment) {
        return thumbCommentMapper.selectByThumbComment(thumbComment);
    }

    @Override
    public int findThumbCommentCount(ThumbComment thumbComment) {
        return thumbCommentMapper.findThumbCommentCount(thumbComment);
    }

    @Override
    public void addThumbComment(ThumbComment thumbComment) {
        thumbCommentMapper.insert(thumbComment);
        String type = thumbComment.getCommentType() ;
        if("information".trim().equalsIgnoreCase(type)){
            InfoComment infoComment = new InfoComment();
            infoComment.setId(thumbComment.getCommentId());
            infoCommentMapper.updateThumbUpNum(infoComment);
        }else if("policy".trim().equalsIgnoreCase(type)){
            PolicyComment policyComment = new PolicyComment();
            policyComment.setId(thumbComment.getCommentId());
            policyCommentMapper.updateThumbUpNum(policyComment);
        }else if("knowledge".trim().equalsIgnoreCase(type)){
            KnowledgeComment knowledgeComment= new KnowledgeComment() ;
            knowledgeComment.setId(thumbComment.getCommentId());
            knowledgeCommentMapper.updateThumbUpNum(knowledgeComment);
        }else if("standard".equals(thumbComment.getCommentType())){
            thumbCommentMapper.updateStandardCommentUpNum(thumbComment.getCommentId());
        }

    }
}
