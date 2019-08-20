package com.ovit.nswy.relationship.model.Relationship;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

/**
 * s
 *
 * @author haoWen
 * @create 2018-03-05 15:42
 **/

public class RelationshipGroupResponse {
    private String groupType;
    private String groupName;
    private String groupId;
    private String parentId;
    private String deleteStatus;
    private List<RelationshipGroupResponse> childrenGroupList;
    private List<FriendList> friendList;

    public String getDeleteStatus() {
        return deleteStatus;
    }

    public void setDeleteStatus(String deleteStatus) {
        this.deleteStatus = deleteStatus;
    }

    public String getGroupType() {
        return groupType;
    }

    public void setGroupType(String groupType) {
        this.groupType = groupType;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public List<RelationshipGroupResponse> getChildrenGroupList() {
        return childrenGroupList;
    }

    public void setChildrenGroupList(List<RelationshipGroupResponse> childrenGroupList) {
        this.childrenGroupList = childrenGroupList;
    }

    public List<FriendList> getFriendList() {
        return friendList;
    }

    public void setFriendList(List<FriendList> friendList) {
        this.friendList = friendList;
    }

    @Override
    public String toString() {
        return "RelationshipGroupResponse{" +
                "groupType='" + groupType + '\'' +
                ", groupName='" + groupName + '\'' +
                ", groupId='" + groupId + '\'' +
                ", parentId='" + parentId + '\'' +
                ", deleteStatus='" + deleteStatus + '\'' +
                ", childrenGroupList=" + childrenGroupList +
                ", friendList=" + friendList +
                '}';
    }
}
