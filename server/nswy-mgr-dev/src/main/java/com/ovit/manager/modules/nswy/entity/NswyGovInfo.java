package com.ovit.manager.modules.nswy.entity;

import java.util.Date;

import com.ovit.manager.common.persistence.DataEntity;

public class NswyGovInfo extends DataEntity<NswyGovInfo> {
    /**
	 * 
	 */
	private static final long serialVersionUID = 9101007359610417346L;
	private Integer  govInfoId;
    private String govName;

    private String loginAccount;

    private String organizationCode;

    private Date createTime;

    private String approveStatus;

    private String govType;

    private String govLevel;

    private String location;

    private String logoPictureList;

    private String unitPersonPictureList;

    private String coordinate;
    private String time;
    private Integer userType;
    private String  userName;
    private String  isHomeDisplay;

    private String agencyer;

    private String countryType;

    private String remark;

    private String addrDetail;

    private String isToPage;

    private  String isNavDisplay;

    private String orderNum;

    public String getAddrDetail() {
        return addrDetail;
    }

    public void setAddrDetail(String addrDetail) {
        this.addrDetail = addrDetail;
    }

    public String getIsToPage() {
        return isToPage;
    }

    public void setIsToPage(String isToPage) {
        this.isToPage = isToPage;
    }

    public String getIsNavDisplay() {
        return isNavDisplay;
    }

    public void setIsNavDisplay(String isNavDisplay) {
        this.isNavDisplay = isNavDisplay;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public String getCountryType() {
        return countryType;
    }

    public void setCountryType(String countryType) {
        this.countryType = countryType;
    }

    public String getAgencyer() {
        return agencyer;
    }

    public void setAgencyer(String agencyer) {
        this.agencyer = agencyer;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Integer getGovInfoId() {
        return govInfoId;
    }

    public void setGovInfoId(Integer govInfoId) {
        this.govInfoId = govInfoId;
    }

    public String getGovName() {
        return govName;
    }

    public void setGovName(String govName) {
        this.govName = govName == null ? null : govName.trim();
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getLoginAccount() {
        return loginAccount;
    }

    public void setLoginAccount(String loginAccount) {
        this.loginAccount = loginAccount;
    }

    public String getOrganizationCode() {
        return organizationCode;
    }

    public void setOrganizationCode(String organizationCode) {
        this.organizationCode = organizationCode;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getApproveStatus() {
        return approveStatus;
    }

    public void setApproveStatus(String approveStatus) {
        this.approveStatus = approveStatus;
    }

    public String getGovType() {
        return govType;
    }

    public void setGovType(String govType) {
        this.govType = govType;
    }

    public String getGovLevel() {
        return govLevel;
    }

    public void setGovLevel(String govLevel) {
        this.govLevel = govLevel;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLogoPictureList() {
        return logoPictureList;
    }

    public void setLogoPictureList(String logoPictureList) {
        this.logoPictureList = logoPictureList;
    }

    public String getUnitPersonPictureList() {
        return unitPersonPictureList;
    }

    public void setUnitPersonPictureList(String unitPersonPictureList) {
        this.unitPersonPictureList = unitPersonPictureList;
    }

    public String getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(String coordinate) {
        this.coordinate = coordinate;
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getIsHomeDisplay() {
        return isHomeDisplay;
    }

    public void setIsHomeDisplay(String isHomeDisplay) {
        this.isHomeDisplay = isHomeDisplay;
    }
}