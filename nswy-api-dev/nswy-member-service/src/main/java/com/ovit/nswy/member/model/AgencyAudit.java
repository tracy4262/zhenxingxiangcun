package com.ovit.nswy.member.model;

import java.util.Date;

/**
 * 代理审核
 * @author Administrator
 *
 */
public class AgencyAudit {
    private Integer id;

    private String type;

    private String name;

    private String account;

    private String agencyer;

    private String picAddr;

    private Date createTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account == null ? null : account.trim();
    }

    public String getAgencyer() {
        return agencyer;
    }

    public void setAgencyer(String agencyer) {
        this.agencyer = agencyer == null ? null : agencyer.trim();
    }

    public String getPicAddr() {
        return picAddr;
    }

    public void setPicAddr(String picAddr) {
        this.picAddr = picAddr == null ? null : picAddr.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}