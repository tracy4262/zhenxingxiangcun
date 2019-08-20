package com.ovit.manager.modules.cms.entity;

import java.util.Date;

/**
 * 政策
 * @author Administrator
 *
 */
public class Policy {
    private Integer id;
    
    private String articleId;

    private Integer informationDetailId;

    private String businessInformation;

    private String allRange;

    private String businessNature;

    private String allBusiness;

    private String title;

    private String imageAdd;

    private Date createTime;

    private Integer commentNum;

    private String lables;
    
    private String industrialId;
    
    private String docType;
    public int getApproveStatus() {
        return approveStatus;
    }

    public void setApproveStatus(int approveStatus) {
        this.approveStatus = approveStatus;
    }

    private int approveStatus;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getArticleId() {
        return articleId;
    }

    public void setArticleId(String articleId) {
        this.articleId = articleId;
    }
    
    public Integer getInformationDetailId() {
        return informationDetailId;
    }

    public void setInformationDetailId(Integer informationDetailId) {
        this.informationDetailId = informationDetailId;
    }

    public String getBusinessInformation() {
        return businessInformation;
    }

    public void setBusinessInformation(String businessInformation) {
        this.businessInformation = businessInformation == null ? null : businessInformation.trim();
    }

    public String getAllRange() {
        return allRange;
    }

    public void setAllRange(String allRange) {
        this.allRange = allRange == null ? null : allRange.trim();
    }

    public String getBusinessNature() {
        return businessNature;
    }

    public void setBusinessNature(String businessNature) {
        this.businessNature = businessNature == null ? null : businessNature.trim();
    }

    public String getAllBusiness() {
        return allBusiness;
    }

    public void setAllBusiness(String allBusiness) {
        this.allBusiness = allBusiness == null ? null : allBusiness.trim();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getImageAdd() {
        return imageAdd;
    }

    public void setImageAdd(String imageAdd) {
        this.imageAdd = imageAdd == null ? null : imageAdd.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getCommentNum() {
        return commentNum;
    }

    public void setCommentNum(Integer commentNum) {
        this.commentNum = commentNum;
    }

    public String getLables() {
        return lables;
    }

    public void setLables(String lables) {
        this.lables = lables == null ? null : lables.trim();
    }
    
    public String getIndustrialId() {
        return industrialId;
    }

    public void setIndustrialId(String industrialId) {
        this.industrialId = industrialId;
    }
    
	public String getDocType() {
		return docType;
	}

	 public void setDocType(String docType) {
		 this.docType = docType;     
	}
}