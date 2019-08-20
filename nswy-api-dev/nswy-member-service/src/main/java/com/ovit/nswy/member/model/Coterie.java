package com.ovit.nswy.member.model;

import java.util.Date;

/**
 * 圈子
 * @author Administrator
 *
 */
public class Coterie {
    private Integer id;

    private String coterieName;

    private String description;

    private Integer followerNum;
    
    private String category;
	private Integer activeValue;

    private String avartar;

    private Date createTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCoterieName() {
        return coterieName;
    }

    public void setCoterieName(String coterieName) {
        this.coterieName = coterieName == null ? null : coterieName.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public Integer getFollowerNum() {
        return followerNum;
    }

    public void setFollowerNum(Integer followerNum) {
        this.followerNum = followerNum;
    }

    public Integer getActiveValue() {
        return activeValue;
    }

    public void setActiveValue(Integer activeValue) {
        this.activeValue = activeValue;
    }

    public String getAvartar() {
        return avartar;
    }

    public void setAvartar(String avartar) {
        this.avartar = avartar == null ? null : avartar.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    

    public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

}