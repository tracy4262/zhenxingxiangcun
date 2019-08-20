package com.ovit.manager.modules.nswy.entity;

import com.ovit.manager.common.persistence.DataEntity;

/**
 *产品
 *
 * @author rongfwang
 * @create 2018-02-09 10:36
 **/
public class ProductTypeInfo extends DataEntity<ProductTypeInfo> {


    private String id;

    private String classificationName;

    private String classificationCode;

    private String pid;

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    public String getClassificationName() {
        return classificationName;
    }

    public void setClassificationName(String classificationName) {
        this.classificationName = classificationName;
    }

    public String getClassificationCode() {
        return classificationCode;
    }

    public void setClassificationCode(String classificationCode) {
        this.classificationCode = classificationCode;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }
}
