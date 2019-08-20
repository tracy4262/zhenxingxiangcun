package com.ovit.nswy.member.service;


import com.ovit.nswy.member.model.MemberIntroduce;
import com.ovit.nswy.member.model.MemberIntroduceDetail;

import java.util.List;
import java.util.Map;

public interface MemberIntroduceService {

    Map<String,Object> findNswyInfo( Map<String, Object> params);

    void insertIntroduceDetail(MemberIntroduceDetail detail);

    void insertInfo(MemberIntroduce info);

    List<Map<String,Object>> findMediaLibraryInfo(Map<String,Object> params);

    List<Map<String,Object>> findMediaBookDetail(Map<String,Object> params);

    Map<String,Object> findMemberIntroduceInfoById(Map<String,Object> params);

    Map<String,Object> findMemberIntroduceInfo(Map<String,Object> params);

    void findIntroduceDelete(Map<String,Object> params);

}
