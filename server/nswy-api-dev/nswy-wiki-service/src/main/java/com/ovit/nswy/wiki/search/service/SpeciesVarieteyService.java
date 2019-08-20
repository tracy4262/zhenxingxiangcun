package com.ovit.nswy.wiki.search.service;

import com.github.xphsc.page.Page;
import com.ovit.nswy.frame.common.exception.ParamValidException;
import com.ovit.nswy.wiki.common.search.ESQueryBuilderConstructor;
import com.ovit.nswy.wiki.model.request.SpeciesVarieteyListQuery;
import com.ovit.nswy.wiki.model.request.SpeciesVarieteyQuery;


import java.util.List;
import java.util.Map;

/**
 * Created by ${huipei.x} on 2017-11-1.
 */
public interface SpeciesVarieteyService {

    Page<Map<String, Object>> listSpeciesVarietey(SpeciesVarieteyListQuery speciesVarieteyListQuery);

    List<Map<String, Object>> findByVarieteyName(SpeciesVarieteyListQuery speciesVarieteyListQuery);

    Object saveSpeciesVarietey(SpeciesVarieteyQuery speciesVarieteyQuery) throws ParamValidException;

    Map<String, Object> getSpeciesVarietey(String indexid);

    List<Map<String, Object>> getVarieteyList(SpeciesVarieteyListQuery speciesVarieteyListQuery);

    void deleteSpeciesVarietey(String indexid);

    void updateSpeciesVarietey(SpeciesVarieteyQuery speciesVarieteyQuery);

    Object isNameExist(String name);

    Page<Map<String, Object>> findVarieteyInfo(ESQueryBuilderConstructor constructor);

    /**
     * 更新待审核通过之后的操作
     * @param auditVarieteyInfo
     */
    void updateVarieteyPassAudit(Map<String, Object> auditVarieteyInfo);

    SpeciesVarieteyQuery mapToSpeciesVarieteyQuery(SpeciesVarieteyQuery speciesVarieteyQuery, Map<String, Object> auditVarieteyInfo);
}
