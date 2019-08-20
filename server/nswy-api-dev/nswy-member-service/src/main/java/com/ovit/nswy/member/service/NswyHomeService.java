package com.ovit.nswy.member.service;

import java.util.List;
import java.util.Map;

public interface NswyHomeService {
    List<Map<String,Object>> queryPictureForBanner(Map<String, Object> params);

    List<Map<String,Object>> queryReadingTop();

    List<Map<String,Object>> readingImp();
}
