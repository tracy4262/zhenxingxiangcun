package com.ovit.nswy.wiki.search.service;

import com.github.xphsc.page.Page;
import com.ovit.nswy.wiki.model.request.SpeciesPropertyListQuery;
import com.ovit.nswy.wiki.model.request.SpeciesPropertyQuery;

import java.util.List;
import java.util.Map;


public interface SpeciesPropertyService {


    /**
     * 保存物种的自定义目录数据
     * @param speciesPropertyQuery
     * @return
     */
    Map<String, Object> saveSpeciesProperty(SpeciesPropertyQuery speciesPropertyQuery);

    /**
     * 根据es中species_property索引的文档的ID查询对应的数据
     * @param indexid
     * @return
     */
    Map<String, Object> getSpeciesProperty(String indexid);

    /**
     * 查询物种自定义目录的列表数据,不分页
     * @param speciesPropertyListQuery
     * @return
     */
    List<Map<String, Object>> listSpeciesProperty(SpeciesPropertyListQuery speciesPropertyListQuery);

    /**
     * 查询物种自定义目录的列表数据,带分页
     * @param speciesPropertyListQuery
     * @return
     */
    Page<Map<String, Object>> propertyPageList(SpeciesPropertyListQuery speciesPropertyListQuery);

    /**
     * 根据es中species_property索引的文档的ID删除对应的数据
     * @param indexid
     * @return
     */
    void deleteSpeciesProperty(String indexid);

    /**
     * 更新物种的自定义目录数据
     * @param speciesPropertyQuery
     * @return
     */
    void updateSpeciesProperty(SpeciesPropertyQuery speciesPropertyQuery);

    /**
     * 更新待审核通过之后的操作
     * @param auditPropertyInfo
     */
    void updatePropertyPassAudit(Map<String, Object> auditPropertyInfo);

}
