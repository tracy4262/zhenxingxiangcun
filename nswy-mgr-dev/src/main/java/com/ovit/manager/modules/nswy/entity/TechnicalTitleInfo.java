package com.ovit.manager.modules.nswy.entity;

import com.ovit.manager.common.persistence.DataEntity;

/**
 * 职称
 *
 * @author rongfwang
 * @create 2018-02-09 10:36
 **/
public class TechnicalTitleInfo extends DataEntity<TechnicalTitleInfo> {


    private String id;

    private String technicalTitleName;

    private String pid;

    private String titleType;

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    public String getTechnicalTitleName() {
        return technicalTitleName;
    }

    public void setTechnicalTitleName(String technicalTitleName) {
        this.technicalTitleName = technicalTitleName;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getTitleType() {
        return titleType;
    }

    public void setTitleType(String titleType) {
        this.titleType = titleType;
    }
}
