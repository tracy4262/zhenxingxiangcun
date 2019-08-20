package com.ovit.nswy.member.perfectInfo.service;

import java.util.List;
import java.util.Map;

public interface ExpertInfoService {



    Map<String,Object> findExpertInfo(Map<String, Object> params);

    void updateExpertInfoName(Map<String, Object> params);

    void insertExpertInfo(Map<String, Object> params);

    void saveTextPreview(Map<String, Object> params);
}
