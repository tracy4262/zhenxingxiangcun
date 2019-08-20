package com.ovit.nswy.wiki.search.service;

import com.github.xphsc.page.Page;
import com.ovit.nswy.frame.common.exception.ParamValidException;
import com.ovit.nswy.wiki.model.request.SpeciesAddQuery;
import com.ovit.nswy.wiki.model.request.SpeciesQuery;


import java.util.List;
import java.util.Map;

/**
 * Created by ${huipei.x} on 2017-10-19.
 */
public interface SpeciesServce {
    Page<Map<String, Object>> listSpecies(SpeciesQuery speciesQuery)throws ParamValidException;

    Page<Map<String, Object>> getLatelyData(SpeciesQuery speciesQuery)throws ParamValidException;

    Object saveSpecies(SpeciesAddQuery speciesAddQuery);

    Map<String, Object> getSpecies(String indexid);

    void deleteSpecies(String indexid);

    String getPinYin(String chinese);

    void updateFisdeleteByIndexid(String indexid,Integer fisdelete);

    void updateSpecies(SpeciesAddQuery speciesAddQuery);

    Object isNameExist(String name);

    /**
     * 根据条件查出相应的物种信息，返回所有的数据，不分页
     */
    List<Map<String, Object>> getSpeciesList(SpeciesQuery speciesQuery)throws ParamValidException;

    List<Map<String,Object>> speciesPageInfo(List<Map<String, Object>> speciesList, int pageNum, int pageSize);

    Map<String,Object> listClassifySpeciesInfo();

    /**
     * 更新待审核通过之后的操作
     * @param auditSpeciesInfo
     */
    void updateSpeciesPassAudit(Map<String, Object> auditSpeciesInfo);

    SpeciesAddQuery mapToSpeciesAddQuery(SpeciesAddQuery speciesAddQuery, Map<String, Object> auditSpeciesInfo);

    Map<String, Object> getSpeciesClassify(SpeciesQuery speciesQuery);


    List<Map<String, Object>> findListSpecies(SpeciesQuery speciesQuery);

    List<Map<String, Object>> findBySpeciesName(SpeciesQuery speciesQuery);

    void insertSpecies(Map<String, Object> params);

}
