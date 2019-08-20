package com.ovit.nswy.member.model;

import java.io.Serializable;
import java.util.Date;

public class FarmFamily implements Serializable {

    private Integer farmFamilyId;

    private String farmAccount;

    private String loginAccount;

    private String groupName;

    private String groupId;

    private String password;

    private Date createTime;

    private String title;

    private String idCard;

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getFarmFamilyId() {
        return farmFamilyId;
    }

    public void setFarmFamilyId(Integer farmFamilyId) {
        this.farmFamilyId = farmFamilyId;
    }

    public String getFarmAccount() {
        return farmAccount;
    }

    public void setFarmAccount(String farmAccount) {
        this.farmAccount = farmAccount;
    }

    public String getLoginAccount() {
        return loginAccount;
    }

    public void setLoginAccount(String loginAccount) {
        this.loginAccount = loginAccount;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
