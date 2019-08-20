package com.ovit.nswy.member.perfectInfo.service;

import java.util.List;
import java.util.Map;

public interface HonoraryTitleService {



    Map<String,Object> findHonoraryTitle(Map<String, Object> params);

    void updateHonoraryTitleName(Map<String, Object> params);

    void saveHonoraryTitle(Map<String, Object> params);

    void saveTextPreview(Map<String, Object> params);

    void deleteHonoraryTitle(Map<String,Object> params);

    List<Map<String,Object>> findHonoraryTitleByAccount(Map<String,Object> params);
}
