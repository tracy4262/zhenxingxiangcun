package com.ovit.nswy.wiki.search.service;

import com.github.xphsc.page.Page;
import com.ovit.nswy.wiki.common.search.ESQueryBuilderConstructor;
import com.ovit.nswy.wiki.model.request.SpeciesPestListQuery;
import com.ovit.nswy.wiki.model.request.SpeciesPestQuery;


import java.util.List;
import java.util.Map;

/**
 * Created by ${huipei.x} on 2017-11-1.
 */
public interface SpeciesPestService {

    Page<Map<String, Object>> listSpeciesPest(SpeciesPestListQuery speciesPestListQuery);

    List<Map<String, Object>> getPestList(SpeciesPestListQuery speciesPestListQuery);

    List<Map<String, Object>> findByPestName(SpeciesPestListQuery speciesPestListQuery);

    Object saveSpeciesPest(SpeciesPestQuery speciesVarieteyQuery);

    Map<String, Object> getSpeciesPest(String indexid);

    void deleteSpeciesPest(String indexid);

    void updateSpeciesPest(SpeciesPestQuery speciesPestQuery);

    Page<Map<String, Object>> findPestInfo(ESQueryBuilderConstructor constructor);

    /**
     * 更新待审核通过之后的操作
     * @param auditPestInfo
     */
    void updatePestPassAudit(Map<String, Object> auditPestInfo);

    SpeciesPestQuery mapToSpeciesPestQuery(SpeciesPestQuery speciesPestQuery, Map<String, Object> auditPestInfo);
}
