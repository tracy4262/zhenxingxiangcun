package com.ovit.nswy.member.mapper;

import java.util.Map;

public interface TopographyPhysiognomyMapper {
    Map<String,Object> queryTopographyPhysiognomy(Map<String, Object> params);

    Integer saveTopographyPhysiognomy(Map<String, Object> params);
}
