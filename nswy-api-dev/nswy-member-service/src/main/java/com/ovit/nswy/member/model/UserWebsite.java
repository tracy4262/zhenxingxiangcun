package com.ovit.nswy.member.model;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;

/**
 * 网页设置
 */
public class UserWebsite {
    private Integer id;

    private String name;

    private String status;

    private String position;

    private String logo;

    private String banner;

    private String summary;

    private String template;

    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    private String modular;

    private String account;

    private String introduce;
    private Integer currentType;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position == null ? null : position.trim();
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo == null ? null : logo.trim();
    }

    public String getBanner() {
        return banner;
    }

    public void setBanner(String banner) {
        this.banner = banner == null ? null : banner.trim();
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary == null ? null : summary.trim();
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template == null ? null : template.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getModular() {
        return modular;
    }

    public void setModular(String modular) {
        this.modular = modular == null ? null : modular.trim();
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account == null ? null : account.trim();
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce == null ? null : introduce.trim();
    }

    public Integer getCurrentType() {
        return currentType;
    }

    public void setCurrentType(Integer currentType) {
        this.currentType = currentType;
    }

    @Override
    public String toString() {
        return "UserWebsite{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", status='" + status + '\'' +
                ", position='" + position + '\'' +
                ", logo='" + logo + '\'' +
                ", banner='" + banner + '\'' +
                ", summary='" + summary + '\'' +
                ", template='" + template + '\'' +
                ", createTime=" + createTime +
                ", modular='" + modular + '\'' +
                ", account='" + account + '\'' +
                ", introduce='" + introduce + '\'' +
                ", currentType=" + currentType +
                '}';
    }
}