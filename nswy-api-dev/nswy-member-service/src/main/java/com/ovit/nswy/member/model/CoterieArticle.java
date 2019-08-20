package com.ovit.nswy.member.model;

import com.ovit.nswy.member.util.DateUtil;
import org.springframework.data.annotation.Transient;

import java.text.ParseException;
import java.util.Date;

/**
 * 圈贴
 * @author Administrator
 *
 */
public class CoterieArticle {
    private Integer id;

    private Integer coterieId;

    private String title;

    private String createrAccount;

    private String creatorName;

    private String avartar;

    private Integer commentNum;

    private Integer tourNum;

    private Integer likeNum;
   
    private Date createTime;

    private String content;
    @Transient
    private long time;

    public long getTime() throws ParseException {
		return DateUtil.getTimes(this.createTime);
	}

	public void setTime(long time)  {
		this.time =time ;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCoterieId() {
        return coterieId;
    }

    public void setCoterieId(Integer coterieId) {
        this.coterieId = coterieId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getCreaterAccount() {
        return createrAccount;
    }

    public void setCreaterAccount(String createrAccount) {
        this.createrAccount = createrAccount;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName == null ? null : creatorName.trim();
    }

    public String getAvartar() {
        return avartar;
    }

    public void setAvartar(String avartar) {
        this.avartar = avartar == null ? null : avartar.trim();
    }

    public Integer getCommentNum() {
        return commentNum;
    }

    public void setCommentNum(Integer commentNum) {
        this.commentNum = commentNum;
    }

    public Integer getTourNum() {
        return tourNum;
    }

    public void setTourNum(Integer tourNum) {
        this.tourNum = tourNum;
    }

    public Integer getLikeNum() {
        return likeNum;
    }

    public void setLikeNum(Integer likeNum) {
        this.likeNum = likeNum;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }
    
    
}