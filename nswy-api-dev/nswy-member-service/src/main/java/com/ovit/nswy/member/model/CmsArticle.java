package com.ovit.nswy.member.model;

import java.util.Date;

public class CmsArticle {
	private Integer id;
	
	private String author;
	
	private String title;
	
	private Integer type;
	
	private Date createTime;
	
	private Integer visitor;
	
	private String label;
	
	private String contentUrl;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
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

	public Integer getVisitor() {
		return visitor;
	}

	public void setVisitor(Integer visitor) {
		this.visitor = visitor;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getContentUrl() {
		return contentUrl;
	}

	public void setContentUrl(String contentUrl) {
		this.contentUrl = contentUrl;
	}
	
	
	
}
