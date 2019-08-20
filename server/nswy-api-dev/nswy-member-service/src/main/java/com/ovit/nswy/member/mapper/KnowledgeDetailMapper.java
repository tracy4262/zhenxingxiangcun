package com.ovit.nswy.member.mapper;

import com.ovit.nswy.member.model.KnowledgeDetail;

import java.util.List;
import java.util.Map;

public interface KnowledgeDetailMapper {

    int insert(KnowledgeDetail record);

    KnowledgeDetail selectByPrimaryKey(Integer id);

    List<KnowledgeDetail> findColumnKnow(KnowledgeDetail record);

    List<KnowledgeDetail> findKnowledgeDetailLeftKnowledge(KnowledgeDetail record);

    void updateThumbUpNum(KnowledgeDetail record);

    List<KnowledgeDetail> findBySpeciesName(Map<String,Object> map);

    List<KnowledgeDetail> newknowledge();

    List<Map<String,Object>> findKnowLedgeDetail(Integer id);

    int selectMinId();

    int selectMaxId();

    List<KnowledgeDetail> relevantKnowledge(Map<String,Object> map);

}