package com.ovit.nswy.member.certification.model;

import java.io.Serializable;
import java.util.Date;

public class FollowInfo implements Serializable {

    private  Integer followId;

    private Integer sysFollowDictId;

    private String followValue;

    private String followLabel;

    private String followType;

    private Date createTime;

    private String account;

    private String templateId;

    public Integer getFollowId() {
        return followId;
    }

    public void setFollowId(Integer followId) {
        this.followId = followId;
    }

    public Integer getSysFollowDictId() {
        return sysFollowDictId;
    }

    public void setSysFollowDictId(Integer sysFollowDictId) {
        this.sysFollowDictId = sysFollowDictId;
    }

    public String getFollowValue() {
        return followValue;
    }

    public void setFollowValue(String followValue) {
        this.followValue = followValue;
    }

    public String getFollowLabel() {
        return followLabel;
    }

    public void setFollowLabel(String followLabel) {
        this.followLabel = followLabel;
    }

    public String getFollowType() {
        return followType;
    }

    public void setFollowType(String followType) {
        this.followType = followType;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }
}
