package com.ovit.nswy.member.model;

import java.io.Serializable;
import java.util.Date;

public class FarmGroup implements Serializable {

    private Integer groupId;

    private String groupName;

    private Date createTime;

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
