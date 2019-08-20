package com.ovit.nswy.wiki.search.service;

import com.github.xphsc.page.Page;
import com.ovit.nswy.wiki.model.request.BaseQuery;
import com.ovit.nswy.wiki.model.request.SpeciesClassListQuery;

import java.util.List;
import java.util.Map;

/**
 * Created by ${huipei.x} on 2017-10-25.
 */
public interface SpeciesclassifiedService {

    public List<Map<String, Object>> listSpeciesclassified(SpeciesClassListQuery speciesClassListQuery);
    public void saveSpeciesclassified(Map params);
    public void insertSpeciesclass(Map params);
    public Map<String, Object> getSpeciesclassified(String indexid);
    public void deleteSpeciesclassified(String indexid);
    public  Object listAllSpeciesclass();

    Object findSpeciesList();
}
