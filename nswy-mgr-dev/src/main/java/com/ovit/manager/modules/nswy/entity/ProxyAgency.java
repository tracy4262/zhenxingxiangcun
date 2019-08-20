package com.ovit.manager.modules.nswy.entity;

import java.util.Date;

import com.ovit.manager.common.persistence.DataEntity;

/**
 * 代理
 * @author Administrator
 *
 */
public class ProxyAgency extends DataEntity<ProxyAgency> {
    /**
	 * 
	 */
	private static final long serialVersionUID = -8149692707246238305L;

	private Integer proxyId;

    private String account;

    private String myProxy;

    private Date createTime;

    private String corpName;

    private String legalMan;

    private Integer approveStatus;

    private Date approveTime;

    private String approveAccount;

    private String attachment;
    
    private String ctime;
    private String atime;
    private String statusDesc;
    private String examStatus;

    public Integer getProxyId() {
        return proxyId;
    }

    public void setProxyId(Integer proxyId) {
        this.proxyId = proxyId;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account == null ? null : account.trim();
    }

    public String getMyProxy() {
        return myProxy;
    }

    public void setMyProxy(String myProxy) {
        this.myProxy = myProxy == null ? null : myProxy.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCorpName() {
        return corpName;
    }

    public void setCorpName(String corpName) {
        this.corpName = corpName == null ? null : corpName.trim();
    }

    public String getLegalMan() {
        return legalMan;
    }

    public void setLegalMan(String legalMan) {
        this.legalMan = legalMan == null ? null : legalMan.trim();
    }

    public Integer getApproveStatus() {
        return approveStatus;
    }

    public void setApproveStatus(Integer approveStatus) {
        this.approveStatus = approveStatus;
    }

    public Date getApproveTime() {
        return approveTime;
    }

    public void setApproveTime(Date approveTime) {
        this.approveTime = approveTime;
    }

    public String getApproveAccount() {
        return approveAccount;
    }

    public void setApproveAccount(String approveAccount) {
        this.approveAccount = approveAccount == null ? null : approveAccount.trim();
    }

    public String getAttachment() {
        return attachment;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment == null ? null : attachment.trim();
    }
    
    public String getCtime() {
        return ctime;
    }

    public void setCtime(String ctime) {
        this.ctime = ctime;
    }
    
    public String getAtime() {
        return atime;
    }

    public void setAtime(String atime) {
        this.atime = atime;
    }
    
    public String getStatusDesc() {
        return statusDesc;
    }

    public void setStatusDesc(String statusDesc) {
        this.statusDesc = statusDesc;
    }
    
    public String getExamStatus() {
        return examStatus;
    }

    public void setExamStatus(String examStatus) {
        this.examStatus = examStatus;
    }
}