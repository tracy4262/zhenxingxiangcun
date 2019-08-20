package com.ovit.nswy.member.mapper;

import com.ovit.nswy.member.model.Information;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface InformationMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(Information record);

    int insertSelective(Information record);

    Information selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Information record);

    int updateByPrimaryKey(Information record);

    List<Information> findInForMation();

    List<Information> find(@Param("docType") String docType);

    List<Information> findInformationTitle(Information info);

    List<Map<String, Object>> getBriefNews();

    Information findInformationByDetailId(Integer id);

    List<Map<String, Object>> dynamicCoverList(String account);
}