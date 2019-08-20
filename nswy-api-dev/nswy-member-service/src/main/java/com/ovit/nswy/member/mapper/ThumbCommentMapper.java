package com.ovit.nswy.member.mapper;


import com.ovit.nswy.member.model.StandardComment;
import com.ovit.nswy.member.model.ThumbComment;

public interface ThumbCommentMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ThumbComment record);

    int insertSelective(ThumbComment record);

    ThumbComment selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ThumbComment record);

    int updateByPrimaryKey(ThumbComment record);

    ThumbComment selectByThumbComment(ThumbComment ThumbComment);

    int findThumbCommentCount(ThumbComment ThumbComment );

    void updateStandardCommentUpNum(Integer id);

}