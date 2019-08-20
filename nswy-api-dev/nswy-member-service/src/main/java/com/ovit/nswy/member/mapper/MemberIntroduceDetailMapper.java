package com.ovit.nswy.member.mapper;

import com.ovit.nswy.member.model.MemberIntroduceDetail;

import java.util.Map;

public interface MemberIntroduceDetailMapper {

    void insert(MemberIntroduceDetail detail);

    Map<String,Object> findMemberIntroduceDetail(Integer id);

    Map<String,Object> findMemberIntroduceDetailById(Integer id);


}
