package com.ovit.nswy.portal.model;

import com.ovit.nswy.frame.common.request.BaseQuery;

/**
 * 物种
 *
 * @author haoWen
 * @create 2018-01-22 17:32
 **/

public class SpeciesQuery extends BaseQuery {
    private String speciesid;

    public String getSpeciesid() {
        return speciesid;
    }

    public void setSpeciesid(String speciesid) {
        this.speciesid = speciesid;
    }

    private String fid;

    public String getFid() {
        return fid;
    }

    public void setFid(String fid) {
        this.fid = fid;
    }
}
