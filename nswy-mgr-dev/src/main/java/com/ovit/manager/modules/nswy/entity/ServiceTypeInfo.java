/*
package com.ovit.manager.modules.nswy.entity;*/
package com.ovit.manager.modules.nswy.entity;

import com.ovit.manager.common.persistence.DataEntity;

public class ServiceTypeInfo extends DataEntity<ServiceTypeInfo> {
    private String id;
    private String dictType;
    private String dictValue;
    private String dictName;

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    public String getDictType() {
        return dictType;
    }

    public void setDictType(String dictType) {
        this.dictType = dictType;
    }

    public String getDictValue() {
        return dictValue;
    }

    public void setDictValue(String dictValue) {
        this.dictValue = dictValue;
    }

    public String getDictName() {
        return dictName;
    }

    public void setDictName(String dictName) {
        this.dictName = dictName;
    }




}
