package com.ovit.nswy.member.model;

import java.util.Date;

/**
 * 浏览记录
 * @author Administrator
 *
 */
public class BrowseLog {
    private Integer id;

    private String account;

    private String path;

    private Integer category;

    private Date createTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account == null ? null : account.trim();
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path == null ? null : path.trim();
    }

    public Integer getCategory() {
        return category;
    }

    public void setCategory(Integer category) {
        this.category = category;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "BrowseLog{" +
                "id=" + id +
                ", account='" + account + '\'' +
                ", path='" + path + '\'' +
                ", category=" + category +
                ", createTime=" + createTime +
                '}';
    }
}