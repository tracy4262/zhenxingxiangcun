package com.ovit.nswy.relationship.service;

import com.ovit.nswy.relationship.model.Relationship.FriendList;
import com.ovit.nswy.relationship.model.Relationship.RelationshipGroup;
import com.ovit.nswy.relationship.model.request.RelationshipGroupListQuery;

import java.util.List;

public interface GroupService {
    List<RelationshipGroup> queryGroupList(RelationshipGroupListQuery relationshipGroupListQuery);

    List<FriendList> queryFriendList(String groupId);
}
