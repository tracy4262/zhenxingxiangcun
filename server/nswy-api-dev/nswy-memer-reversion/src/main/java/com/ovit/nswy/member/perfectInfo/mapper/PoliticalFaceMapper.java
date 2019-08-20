package com.ovit.nswy.member.perfectInfo.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface PoliticalFaceMapper {

    Map<String,Object> findPoliticalFace(Map<String, Object> params);

    void updatePoliticalFaceName(Map<String, Object> params);

    void savePoliticalFace(Map<String, Object> params);

}
