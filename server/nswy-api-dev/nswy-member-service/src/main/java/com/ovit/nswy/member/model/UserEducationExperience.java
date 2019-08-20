package com.ovit.nswy.member.model;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 教育信息
 *
 */
public class UserEducationExperience {
    private Integer id;

    private String account;

    private String shcoolName;

    private String profession;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private String entranceDate;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private String  graduationDate;

    private Integer status;

    private String degree;

    private Integer statusN;

    private Integer statusD;

    private Integer statusP;

    private Integer statusS;

    private Integer statuaE;


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

    public String getShcoolName() {
        return shcoolName;
    }

    public void setShcoolName(String shcoolName) {
        this.shcoolName = shcoolName == null ? null : shcoolName.trim();
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession == null ? null : profession.trim();
    }

    public String getEntranceDate() {
        return entranceDate;
    }

    public void setEntranceDate(String entranceDate) {
        this.entranceDate = entranceDate;
    }

    public String getGraduationDate() {
        return graduationDate;
    }

    public void setGraduationDate(String graduationDate) {
        this.graduationDate = graduationDate;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree == null ? null : degree.trim();
    }

    public Integer getStatusN() {
        return statusN;
    }

    public void setStatusN(Integer statusN) {
        this.statusN = statusN;
    }

    public Integer getStatusD() {
        return statusD;
    }

    public void setStatusD(Integer statusD) {
        this.statusD = statusD;
    }

    public Integer getStatusP() {
        return statusP;
    }

    public void setStatusP(Integer statusP) {
        this.statusP = statusP;
    }

    public Integer getStatusS() {
        return statusS;
    }

    public void setStatusS(Integer statusS) {
        this.statusS = statusS;
    }

    public Integer getStatuaE() {
        return statuaE;
    }

    public void setStatuaE(Integer statuaE) {
        this.statuaE = statuaE;
    }

}