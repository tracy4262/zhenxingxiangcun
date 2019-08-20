package com.ovit.nswy.member.model;

import java.util.Date;

/**
 * 实名认证
 *
 * @author Administrator
 *
 */
public class Certification {
    private Integer id;

    private String userAccount;
    
    private String realname;

    private String photo;

    private Integer sex;

    private String skilledField;

    private String major;

    private String title;

    private Integer status;

    private String idCard;

    private String province;

    private String city;

    private Date createTime;
    
    private String mobile;

    private String locationId;

    private String addrDetail;

    public String getAddrDetail() {
        return addrDetail;
    }

    public void setAddrDetail(String addrDetail) {
        this.addrDetail = addrDetail;
    }

    public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount == null ? null : userAccount.trim();
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo == null ? null : photo.trim();
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getSkilledField() {
        return skilledField;
    }

    public void setSkilledField(String skilledField) {
        this.skilledField = skilledField == null ? null : skilledField.trim();
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major == null ? null : major.trim();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard == null ? null : idCard.trim();
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province == null ? null : province.trim();
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city == null ? null : city.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    @Override
    public String toString() {
        return "Certification{" +
                "id=" + id +
                ", userAccount='" + userAccount + '\'' +
                ", realname='" + realname + '\'' +
                ", photo='" + photo + '\'' +
                ", sex=" + sex +
                ", skilledField='" + skilledField + '\'' +
                ", major='" + major + '\'' +
                ", title='" + title + '\'' +
                ", status=" + status +
                ", idCard='" + idCard + '\'' +
                ", province='" + province + '\'' +
                ", city='" + city + '\'' +
                ", createTime=" + createTime +
                ", mobile='" + mobile + '\'' +
                ", locationId='" + locationId + '\'' +
                '}';
    }
}