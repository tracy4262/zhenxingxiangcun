package com.ovit.nswy.member.mapper;

import com.ovit.nswy.member.model.Industrial;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface IndustrialMapper {

    void insertIndu(Industrial record);

    List<Industrial> findIndu();

    List<Industrial> findIndustrial(Industrial record);

    void updateIndu(Industrial record);

    List<Industrial> getIndustryType();

    List<Industrial> findIndustryByParentId(@Param("parentId") String parentId);

    List<Map<String,Object>> getIndustryByIdArray(List<String> relatedIndustryIdArray);
}