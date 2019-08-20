package com.ovit.nswy.member.model;

import java.util.List;

public class Tree {
	 private String fid;

	 private String fname;

	 private String fpid;
	 
	 private List<Tree> list;

	public String getFid() {
		return fid;
	}

	public void setFid(String fid) {
		this.fid = fid;
	}

	public String getFname() {
		return fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	public String getFpid() {
		return fpid;
	}

	public void setFpid(String fpid) {
		this.fpid = fpid;
	}

	public List<Tree> getList() {
		return list;
	}

	public void setList(List<Tree> list) {
		this.list = list;
	}
	
	
	 
	 
}
