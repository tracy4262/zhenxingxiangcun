package com.ovit.nswy.member.model;

import java.io.Serializable;
import java.util.Date;

// 区域管理
public class Town implements Serializable{

	private String id;

	private String name;

	private String parentId;
	
//	private String code;
//
//	private Date createTime;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

//	public String getCode() {
//		return code;
//	}
//
//	public void setCode(String code) {
//		this.code = code;
//	}
//
//	public Date getCreateTime() {
//		return createTime;
//	}
//
//	public void setCreateTime(Date createTime) {
//		this.createTime = createTime;
//	}

	@Override
	public String toString() {
		return "Town{" +
				"id='" + id + '\'' +
				", name='" + name + '\'' +
				", parentId='" + parentId + '\'' +
//				", code='" + code + '\'' +
//				", createTime=" + createTime +
				'}';
	}
}
