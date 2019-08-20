package com.ovit.nswy.member.model;

import java.util.Date;

public class SignIn {
	private int id;

    private String account;

    private Date signTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account == null ? null : account.trim();
    }

    public Date getSignTime() {
        return signTime;
    }

    public void setSignTime(Date signTime) {
        this.signTime = signTime;
    }
    
    @Override
 	public String toString() {
 		return "SignIn [id=" + id + ", account=" + account + ", signTime=" + signTime + "]";
 	}
}