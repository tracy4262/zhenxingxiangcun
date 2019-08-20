package com.ovit.nswy.member.perfectInfo.service;

import java.util.List;
import java.util.Map;

public interface WorkExperienceService {



    Map<String,Object> findWorkExperience(Map<String, Object> params);

    void updateWorkExperienceName(Map<String, Object> params);

    void saveWorkExperience(Map<String, Object> params);

    void saveTextPreview(Map<String, Object> params);

    void deleteWorkExperience(Map<String, Object> params);
}
