package com.ovit.nswy.member.mapper;

import com.ovit.nswy.member.model.VideoImg;

import java.util.List;
import java.util.Map;

public interface VideoImgMapper {
    int deleteByAccount(Integer id);

    void deleteByAccount(String account);

    int insert(List<VideoImg> list);

    int insertSelective(VideoImg record);

    List<String> selectByAccount(VideoImg record);

    int updateByPrimaryKeySelective(VideoImg record);

    int update(VideoImg record);

    List<VideoImg> selectByRelativeId(Map<String ,Object> map);

    void saveHonorAptitude(Map<String, Object> map);
}