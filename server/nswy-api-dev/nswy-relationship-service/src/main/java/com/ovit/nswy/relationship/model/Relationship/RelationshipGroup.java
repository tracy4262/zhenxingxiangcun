package com.ovit.nswy.relationship.model.Relationship;

/**
 * 关系圈分组
 *
 * @author haoWen
 * @create 2018-03-05 15:45
 **/

public class RelationshipGroup {
    private String groupId;
    private String account;
    private String groupName;
    private String groupType;
    private String parentId;
    private String createTime;
    private String updateTime;
    private String groupGrade;
    private String deleteStatus;//能否被删除

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupType() {
        return groupType;
    }

    public void setGroupType(String groupType) {
        this.groupType = groupType;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getGroupGrade() {
        return groupGrade;
    }

    public void setGroupGrade(String groupGrade) {
        this.groupGrade = groupGrade;
    }

    public String getDeleteStatus() {
        return deleteStatus;
    }

    public void setDeleteStatus(String deleteStatus) {
        this.deleteStatus = deleteStatus;
    }

    @Override
    public String toString() {
        return "RelationshipGroup{" +
                "groupId='" + groupId + '\'' +
                ", account='" + account + '\'' +
                ", groupName='" + groupName + '\'' +
                ", groupType='" + groupType + '\'' +
                ", parentId='" + parentId + '\'' +
                ", createTime='" + createTime + '\'' +
                ", updateTime='" + updateTime + '\'' +
                ", groupGrade='" + groupGrade + '\'' +
                ", deleteStatus='" + deleteStatus + '\'' +
                '}';
    }
}
