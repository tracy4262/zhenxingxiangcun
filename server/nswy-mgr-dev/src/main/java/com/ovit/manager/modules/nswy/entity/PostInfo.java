package com.ovit.manager.modules.nswy.entity;

import com.ovit.manager.common.persistence.DataEntity;

/**
 * 职称
 *
 * @author rongfwang
 * @create 2018-02-09 10:36
 **/
public class PostInfo extends DataEntity<PostInfo> {


    private String id;

    private String postName;

    private String pid;

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    public String getPostName() {
        return postName;
    }

    public void setPostName(String postName) {
        this.postName = postName;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }
}
