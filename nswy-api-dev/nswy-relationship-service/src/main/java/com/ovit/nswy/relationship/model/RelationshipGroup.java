package com.ovit.nswy.relationship.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "relationship_group")
public class RelationshipGroup {
    /**
     * 分组Id
     */
    @Id
    @Column(name = "gruop_id")
    private Integer gruopId;

    /**
     * 用户账户
     */
    private String account;

    /**
     * f分组名称
     */
    @Column(name = "gruop_name")
    private String gruopName;

    /**
     * 分组类型
     */
    @Column(name = "group_type")
    private Integer groupType;

    /**
     * 父id
     */
    @Column(name = "parent_id")
    private Integer parentId;
    /**
     * 分组级别
     */
    @Column(name = "group_grade")
    private Integer groupGrade;



    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 修改时间
     */
    @Column(name = "update_time")
    private Date updateTime;

    /**
     * 获取分组Id
     *
     * @return gruop_id - 分组Id
     */
    public Integer getGruopId() {
        return gruopId;
    }

    /**
     * 设置分组Id
     *
     * @param gruopId 分组Id
     */
    public void setGruopId(Integer gruopId) {
        this.gruopId = gruopId;
    }

    /**
     * 获取用户账户
     *
     * @return account - 用户账户
     */
    public String getAccount() {
        return account;
    }

    /**
     * 设置用户账户
     *
     * @param account 用户账户
     */
    public void setAccount(String account) {
        this.account = account == null ? null : account.trim();
    }

    /**
     * 获取f分组名称
     *
     * @return gruop_name - f分组名称
     */
    public String getGruopName() {
        return gruopName;
    }

    /**
     * 设置f分组名称
     *
     * @param gruopName f分组名称
     */
    public void setGruopName(String gruopName) {
        this.gruopName = gruopName == null ? null : gruopName.trim();
    }

    /**
     * 获取分组类型
     *
     * @return group_type - 分组类型
     */
    public Integer getGroupType() {
        return groupType;
    }

    /**
     * 设置分组类型
     *
     * @param groupType 分组类型
     */
    public void setGroupType(Integer groupType) {
        this.groupType = groupType;
    }

    /**
     * 获取父id
     *
     * @return parent_id - 父id
     */
    public Integer getParentId() {
        return parentId;
    }

    /**
     * 设置父id
     *
     * @param parentId 父id
     */
    public void setParentId(Integer parentId) {
        this.parentId = parentId;
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
     * 获取修改时间
     *
     * @return update_time - 修改时间
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 设置修改时间
     *
     * @param updateTime 修改时间
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getGroupGrade() {
        return groupGrade;
    }

    public void setGroupGrade(Integer groupGrade) {
        this.groupGrade = groupGrade;
    }
}