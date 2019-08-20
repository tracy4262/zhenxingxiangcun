package com.ovit.nswy.relationship.model;

import javax.persistence.*;

@Table(name = "im_group")
public class ImGroup {
    /**
     * 群ID
     */
    @Id
    @Column(name = "im_group_id")
    private Integer imGroupId;

    /**
     * 群名称
     */
    @Column(name = "group_name")
    private String groupName;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 父Id
     */
    @Column(name = "parent_id")
    private Integer parentId;

    /**
     * 用户账号
     */
    private String account;

    /**
     * 获取群ID
     *
     * @return im_group_id - 群ID
     */
    public Integer getImGroupId() {
        return imGroupId;
    }

    /**
     * 设置群ID
     *
     * @param imGroupId 群ID
     */
    public void setImGroupId(Integer imGroupId) {
        this.imGroupId = imGroupId;
    }

    /**
     * 获取群名称
     *
     * @return group_name - 群名称
     */
    public String getGroupName() {
        return groupName;
    }

    /**
     * 设置群名称
     *
     * @param groupName 群名称
     */
    public void setGroupName(String groupName) {
        this.groupName = groupName == null ? null : groupName.trim();
    }

    /**
     * 获取头像
     *
     * @return avatar - 头像
     */
    public String getAvatar() {
        return avatar;
    }

    /**
     * 设置头像
     *
     * @param avatar 头像
     */
    public void setAvatar(String avatar) {
        this.avatar = avatar == null ? null : avatar.trim();
    }

    /**
     * 获取父Id
     *
     * @return parent_id - 父Id
     */
    public Integer getParentId() {
        return parentId;
    }

    /**
     * 设置父Id
     *
     * @param parentId 父Id
     */
    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    /**
     * 获取用户账号
     *
     * @return account - 用户账号
     */
    public String getAccount() {
        return account;
    }

    /**
     * 设置用户账号
     *
     * @param account 用户账号
     */
    public void setAccount(String account) {
        this.account = account == null ? null : account.trim();
    }
}