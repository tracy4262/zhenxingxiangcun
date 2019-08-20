package com.ovit.nswy.member.perfectInfo.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface HonoraryTitleMapper {

    List<Map<String,Object>> findHonoraryTitle(Map<String, Object> params);

    void updateHonoraryTitleName(Map<String, Object> params);

    void saveHonoraryTitle(Map<String, Object> params);

    void deleteHonoraryTitle(Map<String, Object> params);



}
