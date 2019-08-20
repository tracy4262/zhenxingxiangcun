package com.ovit.nswy.member.service;

import java.util.Map;

public interface TopographyPhysiognomyService {
    Map<String,Object> queryTopographyPhysiognomy(Map<String, Object> params);

    Integer saveTopographyPhysiognomy(Map<String, Object> params);
}
