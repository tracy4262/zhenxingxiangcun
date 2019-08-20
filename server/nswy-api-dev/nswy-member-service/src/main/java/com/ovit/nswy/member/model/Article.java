package com.ovit.nswy.member.model;

import java.util.Date;

public class Article {

    private int category;// 分类编号
    private String title;    // 标题
    private String keywords;// 关键字
    private String description;// 描述、摘要
    private String docType;//动态分类, '020101'行业动态 '020102'企业动态 '020103'专家动态 '020104'乡村动态 '020105'群协动态 '020106'国际动态
    private String author;
    private Date createDate;

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    private Date updateDate;
    private int approveStatus;
    private String id;

    public Article(int category){
        super();
        this.category = category;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public int getApproveStatus() {
        return approveStatus;
    }

    public void setApproveStatus(int approveStatus) {
        this.approveStatus = approveStatus;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDocType() {
        return docType;
    }

    public void setDocType(String docType) {
        this.docType = docType;
    }

}


