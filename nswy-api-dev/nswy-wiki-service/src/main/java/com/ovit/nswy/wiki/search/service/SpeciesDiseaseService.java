package com.ovit.nswy.wiki.search.service;

import com.github.xphsc.page.Page;
import com.ovit.nswy.wiki.common.search.ESQueryBuilderConstructor;
import com.ovit.nswy.wiki.model.request.SpeciesDiseaseListQuery;
import com.ovit.nswy.wiki.model.request.SpeciesDiseaseQuery;


import java.util.List;
import java.util.Map;

/**
 * Created by ${huipei.x} on 2017-11-1.
 */
public interface SpeciesDiseaseService {
    Page<Map<String, Object>> listSpeciesDisease(SpeciesDiseaseListQuery speciesDiseaseListQuery);

    List<Map<String, Object>> findByDiseaseName(SpeciesDiseaseListQuery speciesDiseaseListQuery);

    List<Map<String, Object>> getDiseaseList(SpeciesDiseaseListQuery speciesDiseaseListQuery);

    Object saveSpeciesDisease(SpeciesDiseaseQuery speciesDiseaseQuery);

    Map<String, Object> getSpeciesDisease(String indexid);

    void deleteSpeciesDisease(String indexid);

    void updateSpeciesDisease(SpeciesDiseaseQuery speciesDiseaseQuery);

    Page<Map<String, Object>> findDiseaseInfo(ESQueryBuilderConstructor constructor);

    /**
     * 更新待审核通过之后的操作
     * @param auditDiseaseInfo
     */
    void updateDiseasePassAudit(Map<String, Object> auditDiseaseInfo);

    SpeciesDiseaseQuery mapToSpeciesDiseaseQuery(SpeciesDiseaseQuery speciesDiseaseQuery, Map<String, Object> auditDiseaseInfo);
}
