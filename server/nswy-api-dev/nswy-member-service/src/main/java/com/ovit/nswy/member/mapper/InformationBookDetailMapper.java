package com.ovit.nswy.member.mapper;

import java.util.List;
import java.util.Map;

public interface InformationBookDetailMapper {

    int batchInsert(List<Map<String,Object>> map);

    Map<String, Object> getInformation(int id);

    void updateInformationNumber(int id);

    void updateKnowledgeNumber(int id);

    void updatePolicyNumber(int id);

    Map<String, Object> getKnowledge(int id);

    Map<String, Object> getPolicy(int id);

    Map<String, Object> findInformationByDetailID(int informationDetailId);

    Map<String, Object> findKnowledgeByDetailID(int informationDetailId);

    Map<String, Object> findPolicyByDetailID(int informationDetailId);

    Map<String, Object> getInformationDetail(int informationDetailId);

    Map<String, Object> getKnowledgeDetail(int informationDetailId);

    Map<String, Object> getPolicyDetail(int informationDetailId);

    Map<String, Object> getInformationBookInfo(Map<String,Object> informationDetailMap);

    List<Map<String, Object>> getInformationBookDetail(Map<String,Object> informationDetailMap);

    Map<String, Object> getBookPartInfo(Map<String,Object> bookPartInfo);

    void deleteInformation(int id);

    void deleteKnowledge(int id);

    void deletePolicy(int id);

    void deleteInformationDetail(int informationDetailId);

    void deleteKnowledgeDetail(int informationDetailId);

    void deletePolicyDetail(int informationDetailId);

    void deleteInformationBookInfo(Map<String,Object> informationDeleteMap);

    void deleteInformationBookDetail(Map<String,Object> bookDetailMap);

    void deleteMediaBookDetail(Map<String,Object> bookDetailMap);

    int batchInsertMediaBookInfo(List<Map<String,Object>> map);

    void deleteMemberIntroduceBookDetail(Map<String,Object> bookDetailMap);

    int batchInsertMemberIntroduceBookInfo(List<Map<String,Object>> map);







}