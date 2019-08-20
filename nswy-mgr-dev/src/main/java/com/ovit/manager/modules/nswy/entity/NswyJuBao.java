package com.ovit.manager.modules.nswy.entity;

import java.util.Date;

import com.ovit.manager.common.persistence.DataEntity;

public class NswyJuBao extends DataEntity<NswyJuBao> {
   /**
	 * 
	 */
	private static final long serialVersionUID = 2944156331967950401L;
	/* private Integer id;*/
    private Integer juBaoId;
    private String type;

    private String description;

    private String link;

    private String creator;

    private Date createTime;

    private String imgUrl;
    private String time;
    public Integer getJuBaoId() {
        return juBaoId;
    }

    public void setJuBaoId(Integer juBaoId) {
        this.juBaoId = juBaoId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link == null ? null : link.trim();
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator == null ? null : creator.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl == null ? null : imgUrl.trim();
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}