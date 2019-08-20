package com.ovit.nswy.member.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "media_library_detail")
public class MediaLibraryDetail {
    /**
     * 媒体库详情id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 媒体库Id
     */
    @Column(name = "media_id")
    private Integer mediaId;

    /**
     * 描述
     */
    @Column(name = "media_describe")
    private String mediaDescribe;

    /**
     * 媒体库路径
     */
    @Column(name = "media_url")
    private String mediaUrl;

    /**
     * 名称
     */
    private String name;

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
     * 账号
     */
    private String account;

    @Column(name = "author")
    private String author;

    @Column(name = "photo_time")
    private Date photoTime;

    @Column(name = "photo_address")
    private String photoAddress;

    /**
     * 获取媒体库详情id
     *
     * @return id - 媒体库详情id
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置媒体库详情id
     *
     * @param id 媒体库详情id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取媒体库Id
     *
     * @return media_id - 媒体库Id
     */
    public Integer getMediaId() {
        return mediaId;
    }

    /**
     * 设置媒体库Id
     *
     * @param mediaId 媒体库Id
     */
    public void setMediaId(Integer mediaId) {
        this.mediaId = mediaId;
    }

    /**
     * 获取描述
     *
     * @return media_describe - 描述
     */
    public String getMediaDescribe() {
        return mediaDescribe;
    }

    /**
     * 设置描述
     *
     * @param mediaDescribe 描述
     */
    public void setMediaDescribe(String mediaDescribe) {
        this.mediaDescribe = mediaDescribe == null ? null : mediaDescribe.trim();
    }

    /**
     * 获取媒体库路径
     *
     * @return media_url - 媒体库路径
     */
    public String getMediaUrl() {
        return mediaUrl;
    }

    /**
     * 设置媒体库路径
     *
     * @param mediaUrl 媒体库路径
     */
    public void setMediaUrl(String mediaUrl) {
        this.mediaUrl = mediaUrl == null ? null : mediaUrl.trim();
    }

    /**
     * 获取名称
     *
     * @return name - 名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置名称
     *
     * @param name 名称
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
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
     * 获取账号
     *
     * @return account - 账号
     */
    public String getAccount() {
        return account;
    }

    /**
     * 设置账号
     *
     * @param account 账号
     */
    public void setAccount(String account) {
        this.account = account == null ? null : account.trim();
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Date getPhotoTime() {
        return photoTime;
    }

    public void setPhotoTime(Date photoTime) {
        this.photoTime = photoTime;
    }

    public String getPhotoAddress() {
        return photoAddress;
    }

    public void setPhotoAddress(String photoAddress) {
        this.photoAddress = photoAddress;
    }
}