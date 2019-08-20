package com.ovit.nswy.relationship.mapper;

import com.ovit.nswy.frame.base.BaseMapper;
import com.ovit.nswy.relationship.model.RelationshipFriend;
import com.ovit.nswy.relationship.model.request.RelationshipGroupQuery;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

public interface RelationshipFriendMapper extends BaseMapper<RelationshipFriend> {
    @Select(" select * from relationship_friend WHERE  group_id in (${ids}) ")
    @ResultType(RelationshipFriend.class)
    List<RelationshipFriend> findByIds(@Param("ids") String ids);

    @Select(" select from_account as fromAccount from relationship_friend WHERE  account = #{account} ")
    List<String> queryFriendByAccount(@Param("account")String account);

    @Delete("delete from relationship_friend where account = #{account} and from_account = #{fromAccount}")
    int deleteFriendByAccount(@Param("account")String account, @Param("fromAccount")String fromAccount);

}