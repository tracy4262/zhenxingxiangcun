package com.ovit.nswy.member.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 资讯明细
 * @author Administrator
 *
 */
public class MemberIntroduceDetail {
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

    private String account;

    private String label;

    private String avatar;

    private String ycAuthor;

    private String columnType;

    private String docType;

    private String abstracts;

    private String coverPhoto;

    private String author;

    private String postNum;

    private String summary;

    private String dataType;

    private String imageAdd;

    private String typeList;

    private String species;

    private String product;

    private String industry;

    private String service;

    private String atlasList;

    private String network;

    public String getNetwork() {
        return network;
    }

    public void setNetwork(String network) {
        this.network = network;
    }

    public String getAtlasList() {
        return atlasList;
    }

    public void setAtlasList(String atlasList) {
        this.atlasList = atlasList;
    }

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getTypeList() {
        return typeList;
    }

    public void setTypeList(String typeList) {
        this.typeList = typeList;
    }

    public String getImageAdd() {
        return imageAdd;
    }

    public void setImageAdd(String imageAdd) {
        this.imageAdd = imageAdd;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getPostNum() {
        return postNum;
    }

    public void setPostNum(String postNum) {
        this.postNum = postNum;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    private List<InfoComment> list=new ArrayList<>();

    private List<VideoImg> videoImgs= new ArrayList();

    private MemberIntroduceDetail informationDetail;

    public MemberIntroduceDetail getInformationDetail() {
        return informationDetail;
    }

    public void setInformationDetail(MemberIntroduceDetail informationDetail) {
        this.informationDetail = informationDetail;
    }

    public List<VideoImg> getVideoImgs() {
        return videoImgs;
    }

    public void setVideoImgs(List<VideoImg> videoImgs) {
        this.videoImgs = videoImgs;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public List<InfoComment> getList() {
		return list;
	}

	public void setList(List<InfoComment> list) {
		this.list = list;
	}



	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

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

    public Integer getNextId() {
        return nextId;
    }

    public void setNextId(Integer nextId) {
        this.nextId = nextId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

	public String getPreviousTitle() {
		return previousTitle;
	}

	public void setPreviousTitle(String previousTitle) {
		this.previousTitle = previousTitle;
	}

	public String getNextTitle() {
		return nextTitle;
	}

	public void setNextTitle(String nextTitle) {
		this.nextTitle = nextTitle;
	}

	public Integer getThumbUpNum() {
		return thumbUpNum;
	}

	public void setThumbUpNum(Integer thumbUpNum) {
		this.thumbUpNum = thumbUpNum;
	}

    public String getYcAuthor() {
        return  ycAuthor ;
    }

    public void setYcAuthor(String ycAuthor) {
        this.ycAuthor = ycAuthor;
    }

    public String getColumnType() {
        return columnType;
    }

    public void setColumnType(String columnType) {
        this.columnType = columnType;
    }

    public String getDocType() {
        return docType;
    }

    public void setDocType(String docType) {
        this.docType = docType;
    }


    public String getAbstracts() {
        return abstracts;
    }

    public void setAbstracts(String abstracts) {
        this.abstracts = abstracts;
    }

    public String getCoverPhoto() {
        return coverPhoto;
    }

    public void setCoverPhoto(String coverPhoto) {
        this.coverPhoto = coverPhoto;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }
}