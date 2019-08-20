package com.ovit.nswy.wiki.search.service;


import com.github.xphsc.page.Page;
import com.ovit.nswy.frame.common.exception.ParamValidException;
import com.ovit.nswy.wiki.model.request.SpeciesCatalogQuery;
import com.ovit.nswy.wiki.model.request.SpeciesWikiQuery;


import java.util.List;
import java.util.Map;

/**
 * Created by ${huipei.x} on 2017-10-30.
 */
public interface SpeciesWikiService {
    void updateLikedCount(String indexId,Integer likedcount);

    Integer getLikedCount(String indexId)throws ParamValidException;

    Object saveCatalog(SpeciesWikiQuery speciesWikiQuery);

    void deleteCatalog(String indexid);

    void updateCatalog(SpeciesWikiQuery speciesWikiQuery);

    Map<String, Object> getCatalog(String indexId);

    List<Map<String, Object>> listCatalog(SpeciesCatalogQuery speciesCatalogQuery);

    Integer initCatalog(SpeciesWikiQuery speciesWikiQuery);

    Page<Map<String, Object>> getOriginInfo(Map<String, Object> record, String index, String type);

    Page<Map<String, Object>> dealAuditEmptyData(Page<Map<String, Object>> mapPage, String index, String type);

}
