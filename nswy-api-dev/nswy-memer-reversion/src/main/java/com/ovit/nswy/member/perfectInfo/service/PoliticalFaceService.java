package com.ovit.nswy.member.perfectInfo.service;

import java.util.List;
import java.util.Map;

public interface PoliticalFaceService {



    Map<String,Object> findPoliticalFace(Map<String, Object> params);

    void updatePoliticalFaceName(Map<String, Object> params);

    void savePoliticalFace(Map<String, Object> params);

    void saveTextPreview(Map<String, Object> params);
}
