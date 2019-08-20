package com.ovit.nswy.member.model;

/**
 * 
 * @author xp
 * 注册信息表
 */
public class UserInfo {
    private Integer id;
    private String sex;
    private Integer statusE;
    private String acaTitle;//职称

    private String acaTitleId;
    private Integer statusA;
    private String avatar;
    
    private String nswyId ;

    private String account;

    private String realName;

    private String rece;

    private String birdhday;

    private String job;

    private  String profession;

    private String species;

    private String phone;
    private String coordinatePoint;
    private int statusPhone;
    private int statusCoord;

    private Integer statusId;

    private Integer statusN;

    private Integer statusR;

    private Integer statusB;

    private Integer statusJ;

    private Integer statusS;

    private Integer statusD;

    private String speciesId;
    private String addr;
    private int statusAddr;

    public String getAcaTitleId() {
        return acaTitleId;
    }

    public void setAcaTitleId(String acaTitleId) {
        this.acaTitleId = acaTitleId;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public int getStatusAddr() {
        return statusAddr;
    }

    public void setStatusAddr(int statusAddr) {
        this.statusAddr = statusAddr;
    }

    public String getSpeciesId() {
        return speciesId;
    }

    public void setSpeciesId(String speciesId) {
        this.speciesId = speciesId;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getSex() {
        return sex;
    }

    public String getAcaTitle() {
        return acaTitle;
    }

    public void setAcaTitle(String acaTitle) {
        this.acaTitle = acaTitle;
    }

    public Integer getStatusA() {
        return statusA;
    }

    public void setStatusA(Integer statusA) {
        this.statusA = statusA;
    }

    public Integer getStatusE() {
        return statusE;
    }

    public void setStatusE(Integer statusE) {
        this.statusE = statusE;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    private String dscription;

    public String getNswyId() {
		return nswyId;
	}

	public void setNswyId(String nswyId) {
		this.nswyId = nswyId;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account == null ? null : account.trim();
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName == null ? null : realName.trim();
    }

    public String getRece() {
        return rece;
    }

    public void setRece(String rece) {
        this.rece = rece == null ? null : rece.trim();
    }

    public String getBirdhday() {
        return birdhday;
    }

    public void setBirdhday(String birdhday) {
        this.birdhday = birdhday;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job == null ? null : job.trim();
    }

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species == null ? null : species.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCoordinatePoint() {
        return coordinatePoint;
    }

    public void setCoordinatePoint(String coordinatePoint) {
        this.coordinatePoint = coordinatePoint;
    }

    public int getStatusPhone() {
        return statusPhone;
    }

    public void setStatusPhone(int status_phone) {
        this.statusPhone = status_phone;
    }

    public int getStatusCoord() {
        return statusCoord;
    }

    public void setStatusCoord(int statusCoord) {
        this.statusCoord = statusCoord;
    }

    public Integer getStatusId() {
        return statusId;
    }

    public void setStatusId(Integer statusId) {
        this.statusId = statusId;
    }

    public Integer getStatusN() {
        return statusN;
    }

    public void setStatusN(Integer statusN) {
        this.statusN = statusN;
    }

    public Integer getStatusR() {
        return statusR;
    }

    public void setStatusR(Integer statusR) {
        this.statusR = statusR;
    }

    public Integer getStatusB() {
        return statusB;
    }

    public void setStatusB(Integer statusB) {
        this.statusB = statusB;
    }

    public Integer getStatusJ() {
        return statusJ;
    }

    public void setStatusJ(Integer statusJ) {
        this.statusJ = statusJ;
    }

    public Integer getStatusS() {
        return statusS;
    }

    public void setStatusS(Integer statusS) {
        this.statusS = statusS;
    }

    public Integer getStatusD() {
        return statusD;
    }

    public void setStatusD(Integer statusD) {
        this.statusD = statusD;
    }

    public String getDscription() {
        return dscription;
    }

    public void setDscription(String dscription) {
        this.dscription = dscription == null ? null : dscription.trim();
    }
}