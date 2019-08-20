package com.ovit.nswy.member.model.request;

import java.util.List;

public class UserSettingsRequest {

    private String typeName;
    private List<UserSettings> userSettings;
    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
    public String getTypeName() {
        return typeName;
    }

    public void setUserSettings(List<UserSettings> userSettings) {
        this.userSettings = userSettings;
    }
    public List<UserSettings> getUserSettings() {
        return userSettings;
    }
}
