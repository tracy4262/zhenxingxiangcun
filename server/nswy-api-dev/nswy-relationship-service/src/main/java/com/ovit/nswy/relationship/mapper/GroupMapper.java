package com.ovit.nswy.relationship.mapper;

import com.ovit.nswy.relationship.model.Relationship.FriendList;
import com.ovit.nswy.relationship.model.Relationship.RelationshipGroup;
import com.ovit.nswy.relationship.model.request.RelationshipGroupListQuery;

import java.util.List;

public interface GroupMapper {
    List<RelationshipGroup> queryGroupList(RelationshipGroupListQuery relationshipGroupListQuery);

    List<FriendList> queryFriendList(String groupId);
}
