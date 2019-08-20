package com.ovit.nswy.member.mapper;

import com.ovit.nswy.member.model.CorpInfo;
import com.ovit.nswy.member.model.Member;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface MemberMapper {
    List<Map<String, Object>> selectByName(Map<String, Object> params);

    List<Map<String, Object>> findMemberType(Map<String, Object> params);

    List<Map<String, Object>> findRealMember(Map<String, Object> params);

    List<Map<String, Object>> findExpertlist(Map<String, Object> params);

    Integer findNotExpert(Map<String, Object> params);
}