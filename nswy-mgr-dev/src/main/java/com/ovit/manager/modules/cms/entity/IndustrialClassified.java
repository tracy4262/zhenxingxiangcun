package com.ovit.manager.modules.cms.entity;

import java.util.Date;
import com.ovit.manager.common.persistence.TreeEntity;

public class IndustrialClassified extends TreeEntity<IndustrialClassified> {
    private String fid;
	private String fname;
	private String fpid;
	private String fpath;
	private Integer fdepth;
	private String fremarks;
	private String fpinyin;
	private Integer fstate;
	private Date fcreatetime;
	private String fcategory;
	private static final long serialVersionUID = 1L;
	
//	private List<Category> childList = Lists.newArrayList(); 	// 拥有子分类列表

	public String getFid() {
		return fid;
	}

	public void setFid(String fid) {
		this.fid = fid == null ? null : fid.trim();
	}

	public String getFname() {
		return fname;
	}

	public void setFname(String fname) {
		this.fname = fname == null ? null : fname.trim();
	}

	public String getFpid() {
		return fpid;
	}

	public void setFpid(String fpid) {
		this.fpid = fpid == null ? null : fpid.trim();
	}

	public String getFpath() {
		return fpath;
	}

	public void setFpath(String fpath) {
		this.fpath = fpath == null ? null : fpath.trim();
	}

	public Integer getFdepth() {
		return fdepth;
	}

	public void setFdepth(Integer fdepth) {
		this.fdepth = fdepth;
	}

	public String getFremarks() {
		return fremarks;
	}

	public void setFremarks(String fremarks) {
		this.fremarks = fremarks == null ? null : fremarks.trim();
	}

	public String getFpinyin() {
		return fpinyin;
	}

	public void setFpinyin(String fpinyin) {
		this.fpinyin = fpinyin == null ? null : fpinyin.trim();
	}

	public Integer getFstate() {
		return fstate;
	}

	public void setFstate(Integer fstate) {
		this.fstate = fstate;
	}

	public Date getFcreatetime() {
		return fcreatetime;
	}

	public void setFcreatetime(Date fcreatetime) {
		this.fcreatetime = fcreatetime;
	}

	public String getFcategory() {
		return fcategory;
	}

	public void setFcategory(String fcategory) {
		this.fcategory = fcategory;
	}
	
	//下面需要在子类中实现
	@Override
	public IndustrialClassified getParent() {
		// TODO Auto-generated method stub
		return parent;
	}

	@Override
	public void setParent(IndustrialClassified parent) {
		// TODO Auto-generated method stub
		this.parent=parent;
	}

	//判断是否为根节点
	public boolean isRoot(){
		return isRoot(this.id);
	}
	
	public static boolean isRoot(String id){
		return id != null && id.equals("1");
	}
}