package com.ovit.nswy.member.certification.mapper;


import com.ovit.nswy.member.certification.model.FriendGroup;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface FriendGroupMapper {

    int deleteByAccount(Map<String,Object> accountInfo);

    int insert(List<FriendGroup> record);

    List<FriendGroup> selectByAccount(Map<String,Object> accountInfo);

    int updateByPrimaryKey(FriendGroup record);

}