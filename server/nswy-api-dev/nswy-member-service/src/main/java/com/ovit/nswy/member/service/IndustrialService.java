package com.ovit.nswy.member.service;

import com.ovit.nswy.member.model.Industrial;

import java.util.List;
import java.util.Map;

public interface IndustrialService {

    List<Industrial> getIndustryType();

    List<Industrial> findIndustryByParentId(String parentId);

    List<Industrial> findIndustry(Industrial industrial);

    List<Map<String,Object>> getIndustryByIdArray(List<String> relatedIndustryIdArray);
}
