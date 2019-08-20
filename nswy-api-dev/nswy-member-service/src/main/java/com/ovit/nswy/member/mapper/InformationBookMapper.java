package com.ovit.nswy.member.mapper;

import java.util.List;
import java.util.Map;

public interface InformationBookMapper {

    public int insert(Map<String, Object> map);

    public Map<String, Object> getInformationBookInfoByDetailId(int detaild);

    public List<Map<String, Object>> getInformationBookInfoList();

    List<Map<String, Object>> findMediaBookInfo(Map<String, Object> map);

    List<Map<String, Object>> findMediaBookDetailInfo(Map<String, Object> map);


    int insertMediaBook(Map<String, Object> map);

    void deleteMedia(Map<String, Object> map);

    void deleteMediaBookInfo(Map<String, Object> map);

    void deleteMediaBookDetail(Map<String, Object> map);

    void deleteBookById(Integer id);

    void deleteBookDetailById(Integer id);

    int insertMemberIntroduceBook(Map<String, Object> map);

    List<Map<String, Object>> findMemberIntroduceBookInfo(Map<String, Object> map);

    List<Map<String, Object>> findMemberIntroduceBookDetailInfo(Map<String, Object> map);






}