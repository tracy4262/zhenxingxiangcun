package com.ovit.nswy.member.service;


import java.util.List;
import java.util.Map;

public interface SpeciesWikiRelevantInfoService {

    /**
     * 根据物种名称查询相关产品信息
     * @param speciesName
     * @return
     */
    List<Map<String,Object>> relevantProduct(String speciesName);

    /**
     * 根据物种名称查询相关专家信息
     * @param speciesName
     * @return
     */
    List<Map<String,Object>> relevantExpert(String speciesName);

    /**
     * 根据物种名称查询相关企业信息
     * @param speciesName
     * @return
     */
    Map<String,Object> relevantCorp(String speciesName);



}