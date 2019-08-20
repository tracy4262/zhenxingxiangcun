package com.ovit.nswy.member.mapper;

import com.ovit.nswy.member.model.MydynamicComment;

public interface MydynamicCommentMapper {

    int insert(MydynamicComment record);

    MydynamicComment findMydynamicComment(Integer id);

    int updateByMydynamicComment(MydynamicComment record);
}