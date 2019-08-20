package com.ovit.nswy.member.mapper;


import com.ovit.nswy.member.model.ThumbDetail;

public interface ThumbDetailMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ThumbDetail record);

    int insertSelective(ThumbDetail record);

    ThumbDetail selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ThumbDetail record);

    int updateByPrimaryKey(ThumbDetail record);

    ThumbDetail selectByThumbDetail(ThumbDetail thumbDetail);

    int findThumbDetailCount(ThumbDetail thumbDetail );
}