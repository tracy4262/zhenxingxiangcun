package com.ovit.nswy.member.perfectInfo.service;

import java.util.List;
import java.util.Map;

public interface NetWorkInfoService {



    Map<String, Object> getNetworkInfo(Map<String,Object> params);

    String insertNetworkInfo(Map<String,Object> params);

    void updateNetworkInfoName(Map<String,Object> params);
}
