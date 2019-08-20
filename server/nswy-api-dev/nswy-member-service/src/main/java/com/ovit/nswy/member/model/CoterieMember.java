package com.ovit.nswy.member.model;

import java.util.Date;

/**
 * 圈子成员
 */
public class CoterieMember {
    private Integer id;

    private Integer coterieId;

    private String account;

    private Integer type;

    private Date createTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCoterieId() {
        return coterieId;
    }

    public void setCoterieId(Integer coterieId) {
        this.coterieId = coterieId;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account == null ? null : account.trim();
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}