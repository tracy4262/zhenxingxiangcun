package com.ovit.nswy.member.model.request;

import java.util.List;

public class ColumnSettingsRequest {
    private List<ColumnSettingsDetailRequest> data;
    public List<ColumnSettingsDetailRequest> getData() {
        return data;
    }

    public void setData(List<ColumnSettingsDetailRequest> data) {
        this.data = data;
    }
}
