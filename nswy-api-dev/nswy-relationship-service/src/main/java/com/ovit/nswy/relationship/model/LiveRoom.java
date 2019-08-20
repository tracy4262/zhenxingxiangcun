package com.ovit.nswy.relationship.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "live_room")
public class LiveRoom {
    /**
     * 房间id
     */
    @Id
    @Column(name = "live_id")
    @GeneratedValue(generator = "JDBC")
    private Integer liveId;

    /**
     * 房间标题
     */
    @Column(name = "live_name")
    private String liveName;

    /**
     * 房间描述
     */
    @Column(name = "live_describe")
    private String liveDescribe;

    /**
     * 房间封面
     */
    @Column(name = "live_image")
    private String liveImage;

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
     * 用户账号
     */
    private String account;

    /**
     * 分类Id
     */
    @Column(name = "live_category_id")
    private Integer liveCategoryId;

    /**
     * 直播状态
     */
    @Column(name = "live_status")
    private Integer liveStatus;





    /**
     * 获取房间id
     *
     * @return live_id - 房间id
     */
    public Integer getLiveId() {
        return liveId;
    }

    /**
     * 设置房间id
     *
     * @param liveId 房间id
     */
    public void setLiveId(Integer liveId) {
        this.liveId = liveId;
    }

    /**
     * 获取房间标题
     *
     * @return live_name - 房间标题
     */
    public String getLiveName() {
        return liveName;
    }

    /**
     * 设置房间标题
     *
     * @param liveName 房间标题
     */
    public void setLiveName(String liveName) {
        this.liveName = liveName == null ? null : liveName.trim();
    }

    /**
     * 获取房间描述
     *
     * @return live_describe - 房间描述
     */
    public String getLiveDescribe() {
        return liveDescribe;
    }

    /**
     * 设置房间描述
     *
     * @param liveDescribe 房间描述
     */
    public void setLiveDescribe(String liveDescribe) {
        this.liveDescribe = liveDescribe == null ? null : liveDescribe.trim();
    }

    /**
     * 获取房间封面
     *
     * @return live_image - 房间封面
     */
    public String getLiveImage() {
        return liveImage;
    }

    /**
     * 设置房间封面
     *
     * @param liveImage 房间封面
     */
    public void setLiveImage(String liveImage) {
        this.liveImage = liveImage == null ? null : liveImage.trim();
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

    /**
     * 获取分类Id
     *
     * @return live_category_id - 分类Id
     */
    public Integer getLiveCategoryId() {
        return liveCategoryId;
    }

    /**
     * 设置分类Id
     *
     * @param liveCategoryId 分类Id
     */
    public void setLiveCategoryId(Integer liveCategoryId) {
        this.liveCategoryId = liveCategoryId;
    }

    /**
     * 获取直播状态
     *
     * @return live_status - 直播状态
     */
    public Integer getLiveStatus() {
        return liveStatus;
    }

    /**
     * 设置直播状态
     *
     * @param liveStatus 直播状态
     */
    public void setLiveStatus(Integer liveStatus) {
        this.liveStatus = liveStatus;
    }


}