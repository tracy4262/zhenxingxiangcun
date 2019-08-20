package com.ovit.nswy.member.model;

public class UserWorkExperience {
    private Integer id;

    private String account;

    private String organazationName;

    private String startDate;

    private String endDate;

    private String content;

    private String job;

    private Integer statusN;

    private Integer statusJ;

    private Integer statusD;

    private Integer statusC;

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

    public String getOrganazationName() {
        return organazationName;
    }

    public void setOrganazationName(String organazationName) {
        this.organazationName = organazationName == null ? null : organazationName.trim();
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job == null ? null : job.trim();
    }

    public Integer getStatusN() {
        return statusN;
    }

    public void setStatusN(Integer statusN) {
        this.statusN = statusN;
    }

    public Integer getStatusJ() {
        return statusJ;
    }

    public void setStatusJ(Integer statusJ) {
        this.statusJ = statusJ;
    }

    public Integer getStatusD() {
        return statusD;
    }

    public void setStatusD(Integer statusD) {
        this.statusD = statusD;
    }

    public Integer getStatusC() {
        return statusC;
    }

    public void setStatusC(Integer statusC) {
        this.statusC = statusC;
    }
}