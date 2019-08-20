package com.ovit.nswy.member.mapper;

import java.util.List;
import java.util.Map;

public interface NswyHomeMapper {
    List<Map<String,Object>> queryPictureForBanner(Map<String, Object> params);

    List<Map<String,Object>> queryReadingTop();

    List<Map<String,Object>> readingImp();
}
