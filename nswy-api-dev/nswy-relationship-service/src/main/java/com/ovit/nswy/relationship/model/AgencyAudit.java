package com.ovit.nswy.relationship.model;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "agency_audit")
public class AgencyAudit {
    /**
     * 审核Id
     */
    @Id
    @Column(name = "audit_id")
    private Integer auditId;

    /**
     * 审核人
     */
    private String auditor;

    /**
     * 审核状态 0未审核  1已审核 2审核拒绝 
     */
    @Column(name = "audit_status")
    private Integer auditStatus;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 审核时间
     */
    @Column(name = "audit_time")
    private Date auditTime;

    /**
     * 代理机构Id
     */
    @Column(name = "agency_id")
    private Integer agencyId;

    /**
     * 获取审核Id
     *
     * @return audit_id - 审核Id
     */
    public Integer getAuditId() {
        return auditId;
    }

    /**
     * 设置审核Id
     *
     * @param auditId 审核Id
     */
    public void setAuditId(Integer auditId) {
        this.auditId = auditId;
    }

    /**
     * 获取审核人
     *
     * @return auditor - 审核人
     */
    public String getAuditor() {
        return auditor;
    }

    /**
     * 设置审核人
     *
     * @param auditor 审核人
     */
    public void setAuditor(String auditor) {
        this.auditor = auditor == null ? null : auditor.trim();
    }

    /**
     * 获取审核状态 0未审核  1已审核 2审核拒绝 
     *
     * @return audit_status - 审核状态 0未审核  1已审核 2审核拒绝 
     */
    public Integer getAuditStatus() {
        return auditStatus;
    }

    /**
     * 设置审核状态 0未审核  1已审核 2审核拒绝 
     *
     * @param auditStatus 审核状态 0未审核  1已审核 2审核拒绝 
     */
    public void setAuditStatus(Integer auditStatus) {
        this.auditStatus = auditStatus;
    }

    /**
     * 获取创建时间
     *
     * @return create_time - 创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置创建时间
     *
     * @param createTime 创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取审核时间
     *
     * @return audit_time - 审核时间
     */
    public Date getAuditTime() {
        return auditTime;
    }

    /**
     * 设置审核时间
     *
     * @param auditTime 审核时间
     */
    public void setAuditTime(Date auditTime) {
        this.auditTime = auditTime;
    }

    /**
     * 获取代理机构Id
     *
     * @return agency_id - 代理机构Id
     */
    public Integer getAgencyId() {
        return agencyId;
    }

    /**
     * 设置代理机构Id
     *
     * @param agencyId 代理机构Id
     */
    public void setAgencyId(Integer agencyId) {
        this.agencyId = agencyId;
    }
}