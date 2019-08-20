package com.ovit.nswy.member.model;

import java.io.Serializable;
import java.util.Date;

public class LoginUser implements Serializable, Cloneable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 6749900880183323588L;

	private Integer id;

    private String uniqueId;

    private String loginAccount;

    private String displayName;

    private String loginPasswd;

    private Date registerTime;

    private String isIdentityVerification;

    private Integer tourNumber;

    private Integer userType;
    
    private String signaTure;

    private String avatar;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    private  String token;
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId == null ? null : uniqueId.trim();
    }

    public String getLoginAccount() {
        return loginAccount;
    }

    public void setLoginAccount(String loginAccount) {
        this.loginAccount = loginAccount == null ? null : loginAccount.trim();
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName == null ? null : displayName.trim();
    }

    public String getLoginPasswd() {
        return loginPasswd;
    }

    public void setLoginPasswd(String loginPasswd) {
        this.loginPasswd = loginPasswd == null ? null : loginPasswd.trim();
    }

    public Date getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(Date registerTime) {
        this.registerTime = registerTime;
    }

    public String getIsIdentityVerification() {
        return isIdentityVerification;
    }

    public void setIsIdentityVerification(String isIdentityVerification) {
        this.isIdentityVerification = isIdentityVerification;
    }

    public Integer getTourNumber() {
        return tourNumber;
    }

    public void setTourNumber(Integer tourNumber) {
        this.tourNumber = tourNumber;
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

	public String getSignaTure() {
		return signaTure;
	}

	public void setSignaTure(String signaTure) {
		this.signaTure = signaTure;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}


    @Override
    public String toString() {
        return "LoginUser{" +
                "id=" + id +
                ", uniqueId='" + uniqueId + '\'' +
                ", loginAccount='" + loginAccount + '\'' +
                ", displayName='" + displayName + '\'' +
                ", loginPasswd='" + loginPasswd + '\'' +
                ", registerTime=" + registerTime +
                ", isIdentityVerification='" + isIdentityVerification + '\'' +
                ", tourNumber=" + tourNumber +
                ", userType=" + userType +
                ", signaTure='" + signaTure + '\'' +
                ", avatar='" + avatar + '\'' +
                ", token='" + token + '\'' +
                '}';
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}