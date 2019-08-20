package com.ovit.nswy.member.model;

import java.util.Date;

/**
 * 专家
 * @author Administrator
 *
 */
public class Expert {
    private Integer id;

    private String account;

    private String expertName;

    private String organaztion;
    
    private String industry;

    private String field;

    private Integer point;

    private Date createTime;
    
    private Integer star;
    
    private String title;
    
    
    
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public Integer getStar() {
		return star;
	}

	public void setStar(Integer star) {
		this.star = star;
	}
	
	
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

    public String getExpertName() {
        return expertName;
    }

    public void setExpertName(String expertName) {
        this.expertName = expertName == null ? null : expertName.trim();
    }

    public String getOrganaztion() {
        return organaztion;
    }

    public void setOrganaztion(String organaztion) {
        this.organaztion = organaztion == null ? null : organaztion.trim();
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field == null ? null : field.trim();
    }

    public Integer getPoint() {
        return point;
    }

    public void setPoint(Integer point) {
        this.point = point;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

	public String getIndustry() {
		return industry;
	}

	public void setIndustry(String industry) {
		this.industry = industry;
	}
    
    
}