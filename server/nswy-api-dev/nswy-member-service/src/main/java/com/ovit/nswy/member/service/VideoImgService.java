package com.ovit.nswy.member.service;

import com.ovit.nswy.member.model.VideoImg;
import java.util.List;
import java.util.Map;

public interface VideoImgService {

    int insert(List<VideoImg> list);

    List<VideoImg> selectByRelativeId(Map<String ,Object> map);

    int update(VideoImg record);

    void deleteByAccount(String account);

    List<String> selectByAccount(VideoImg record);

    void saveHonorAptitude(String name, String proxyName, List<String> aptitudeList);
}
