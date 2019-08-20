package com.ovit.manager.modules.nswy.entity;


import com.ovit.manager.common.persistence.DataEntity;

import java.util.Date;

/**
 * 1
 *
 * @author wangrongf
 * @create 2018-02-11 14:04
 **/

public class UserTypeInfo extends DataEntity<UserTypeInfo> {



    private Integer infoId;

    private String account;

    private String userType;

    private String userTypeName;

    private String isDisplay;

    private String agencyName;

    private Date createTime;

    public Integer getInfoId() {
        return infoId;
    }

    public void setInfoId(Integer infoId) {
        this.infoId = infoId;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getUserTypeName() {
        return userTypeName;
    }

    public String getAgencyName() {
        return agencyName;
    }

    public void setAgencyName(String agencyName) {
        this.agencyName = agencyName;
    }

    public void setUserTypeName(String userTypeName) {
        this.userTypeName = userTypeName;
    }

    public String getIsDisplay() {
        return isDisplay;
    }

    public void setIsDisplay(String isDisplay) {
        this.isDisplay = isDisplay;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
