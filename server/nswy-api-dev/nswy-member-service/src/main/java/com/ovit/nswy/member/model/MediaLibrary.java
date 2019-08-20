package com.ovit.nswy.member.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "media_library")
public class MediaLibrary {
    /**
     * 媒体库Id
     */
    @Id
    @Column(name = "media_id")
    private Integer mediaId;

    /**
     * 媒体库类型 1：相册，2：视频:，3：文档
     */
    @Column(name = "media_type")
    private Integer mediaType;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 媒体库路径
     */
    @Column(name = "image_url")
    private String imageUrl;

    /**
     * 修改时间
     */
    @Column(name = "update_time")
    private Date updateTime;

    /**
     * 媒体库名称
     */
    @Column(name = "media_name")
    private String mediaName;

    /**
     * 用户名
     */
    private String account;

    @Column(name = "media_describe")
    private String mediaDescribe;

    @Column(name = "author")
    private String author;

    @Column(name = "photo_time")
    private Date photoTime;

    @Column(name = "photo_address")
    private String photoAddress;


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
     * 获取媒体库类型 1：相册，2：视频:，3：文档
     *
     * @return media_type - 媒体库类型 1：相册，2：视频:，3：文档
     */
    public Integer getMediaType() {
        return mediaType;
    }

    /**
     * 设置媒体库类型 1：相册，2：视频:，3：文档
     *
     * @param mediaType 媒体库类型 1：相册，2：视频:，3：文档
     */
    public void setMediaType(Integer mediaType) {
        this.mediaType = mediaType;
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
     * 获取媒体库路径
     *
     * @return image_url - 媒体库路径
     */
    public String getImageUrl() {
        return imageUrl;
    }

    /**
     * 设置媒体库路径
     *
     * @param imageUrl 媒体库路径
     */
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl == null ? null : imageUrl.trim();
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
     * 获取媒体库名称
     *
     * @return media_name - 媒体库名称
     */
    public String getMediaName() {
        return mediaName;
    }

    /**
     * 设置媒体库名称
     *
     * @param mediaName 媒体库名称
     */
    public void setMediaName(String mediaName) {
        this.mediaName = mediaName == null ? null : mediaName.trim();
    }

    /**
     * 获取用户名
     *
     * @return account - 用户名
     */
    public String getAccount() {
        return account;
    }

    /**
     * 设置用户名
     *
     * @param account 用户名
     */
    public void setAccount(String account) {
        this.account = account == null ? null : account.trim();
    }

    public String getMediaDescribe() {
        return mediaDescribe;
    }

    public void setMediaDescribe(String mediaDescribe) {
        this.mediaDescribe = mediaDescribe;
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