package com.ovit.nswy.member.perfectInfo.service;

import java.util.List;
import java.util.Map;

public interface EducationLiveService {



    Map<String,Object> findEducationLive(Map<String,Object> params);

    void updateEducationalLiveName(Map<String,Object> params);

    void saveEducationLive(Map<String,Object> params);

    void saveTextPreview(Map<String,Object> params);

    void deleteEducationalExperience(Map<String,Object> params);


}
