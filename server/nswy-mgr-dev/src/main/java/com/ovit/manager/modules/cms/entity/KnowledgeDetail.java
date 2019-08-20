package com.ovit.manager.modules.cms.entity;

import java.util.Date;

/**
 * 知识明细
 * @author Administrator
 *
 */
public class KnowledgeDetail {
    private Integer id;

    private String title;

    private Date createTime;

    private String source;

    private Integer browseNumber;

    private Integer isReport;

    private String editer;

    private Integer previousId;

    private String previousTitle;

    private Integer nextId;

    private String nextTitle;

    private String content;
    
    private Integer thumbUpNum;
    
    private String lables;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source == null ? null : source.trim();
    }

    public Integer getBrowseNumber() {
        return browseNumber;
    }

    public void setBrowseNumber(Integer browseNumber) {
        this.browseNumber = browseNumber;
    }

    public Integer getIsReport() {
        return isReport;
    }

    public void setIsReport(Integer isReport) {
        this.isReport = isReport;
    }

    public String getEditer() {
        return editer;
    }

    public void setEditer(String editer) {
        this.editer = editer == null ? null : editer.trim();
    }

    public Integer getPreviousId() {
        return previousId;
    }

    public void setPreviousId(Integer previousId) {
        this.previousId = previousId;
    }

    public String getPreviousTitle() {
        return previousTitle;
    }

    public void setPreviousTitle(String previousTitle) {
        this.previousTitle = previousTitle == null ? null : previousTitle.trim();
    }

    public Integer getNextId() {
        return nextId;
    }

    public void setNextId(Integer nextId) {
        this.nextId = nextId;
    }

    public String getNextTitle() {
        return nextTitle;
    }

    public void setNextTitle(String nextTitle) {
        this.nextTitle = nextTitle == null ? null : nextTitle.trim();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

	public Integer getThumbUpNum() {
		return thumbUpNum;
	}

	public void setThumbUpNum(Integer thumbUpNum) {
		this.thumbUpNum = thumbUpNum;
	}
    
	public String getLables() {
		return lables;
	}

	public void setLables(String lables) {
		this.lables = lables;
	}
	
}