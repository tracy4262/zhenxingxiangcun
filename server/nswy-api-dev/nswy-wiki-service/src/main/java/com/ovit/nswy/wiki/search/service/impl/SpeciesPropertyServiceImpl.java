package com.ovit.nswy.wiki.search.service.impl;


import com.alibaba.fastjson.JSONArray;
import com.github.xphsc.collect.Maps;
import com.github.xphsc.json.JSONHelper;
import com.github.xphsc.mutable.Integers;
import com.github.xphsc.page.Page;
import com.github.xphsc.util.StringUtil;
import com.ovit.nswy.frame.common.Constants;
import com.ovit.nswy.wiki.base.BaseEsearchDao;
import com.ovit.nswy.wiki.common.MapUtil;
import com.ovit.nswy.wiki.common.search.ESQueryBuilderConstructor;
import com.ovit.nswy.wiki.common.search.ESQueryCondition;
import com.ovit.nswy.wiki.model.request.*;
import com.ovit.nswy.wiki.search.service.SpeciesPropertyService;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;


@Service
public class SpeciesPropertyServiceImpl implements SpeciesPropertyService {

    @Autowired
    private BaseEsearchDao baseEsearchDao;

    /**
     * 保存物种的自定义目录数据
     * @param speciesPropertyQuery
     * @return
     */
    @Override
    public Map<String, Object> saveSpeciesProperty(SpeciesPropertyQuery speciesPropertyQuery){

        String propertyId = speciesPropertyQuery.getPropertyid();
        if (StringUtils.isBlank(propertyId)) {
            //生成UUID作为自定义目录对应数据的id
            propertyId = UUID.randomUUID().toString();
            speciesPropertyQuery.setPropertyid(propertyId);
        }
        speciesPropertyQuery.setUpdatetime(System.currentTimeMillis());
        speciesPropertyQuery.setCreatetime(System.currentTimeMillis());
        speciesPropertyQuery.setFisdelete(0);
        Object result= baseEsearchDao.createIndex(Constants.IndexConstant.SPECIESPROPERTY_INDEX, Constants.IndexConstant.SPECIESPROPERTY_TYPE, JSONHelper.toJSON(speciesPropertyQuery));
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("indexid", result);
        resultMap.put("propertyid", propertyId);

        return resultMap;
    }

    /**
     * 根据es中species_property索引的文档的ID查询对应的数据
     * @param indexid
     * @return
     */
    @Override
    public Map<String, Object> getSpeciesProperty(String indexid){
        Map<String,Object> map=baseEsearchDao.getIndexOfMap(Constants.IndexConstant.SPECIESPROPERTY_INDEX, Constants.IndexConstant.SPECIESPROPERTY_TYPE, indexid);
        getimage(map);
        if (MapUtils.getInteger(map, "auditstatus") != 1) {
            //若auditstatus不为1，则为查询待审核数据,再判断是否存在名称或者拼音为空的情况
            if (StringUtils.isBlank(MapUtils.getString(map, "propertytitle"))) {
                Page<Map<String, Object>> originPage = null;
                originPage = getOriginInfo(map, Constants.IndexConstant.SPECIESPROPERTY_INDEX, Constants.IndexConstant.SPECIESPROPERTY_TYPE);
                if (originPage.getRecordList().size() > 0) {
                    List<Map<String, Object>> originList = originPage.getRecordList();
                    Map<String, Object> originMap = originList.get(0);
                    map.put("propertytitle", MapUtils.getString(originMap, "propertytitle"));
                }
            }
        }
        return map;
    }

    /**
     * 查询物种自定义目录的列表数据
     * @param speciesPropertyListQuery
     * @return
     */
    @Override
    public List<Map<String, Object>> listSpeciesProperty(SpeciesPropertyListQuery speciesPropertyListQuery)  {

        ESQueryBuilderConstructor constructor =new ESQueryBuilderConstructor();
        constructor = getPageQuery(constructor, speciesPropertyListQuery);
        constructor.must(new ESQueryCondition().match("fisdelete", 0));
        constructor.setDesc("updatetime");
        //auditstatus为1时表示审核通过
        if (Integers.isNotEmpty(speciesPropertyListQuery.getAuditstatus())) {
            if (speciesPropertyListQuery.getAuditstatus() == 6) {
                //查询全部的物种数据,排除审核状态完成的数据，即auditstatus为5
                constructor.mustNot(new ESQueryCondition().term("auditstatus", 5));
            } else {
                constructor.must(new ESQueryCondition().match("auditstatus", speciesPropertyListQuery.getAuditstatus()));
            }
        } else {
            //只查询审核通过的物种数据
            constructor.must(new ESQueryCondition().match("auditstatus", 1));
        }
        List<Map<String, Object>> speciesList  = baseEsearchDao.searchList(Constants.IndexConstant.SPECIESPROPERTY_INDEX, Constants.IndexConstant.SPECIESPROPERTY_TYPE, constructor);
        for (Map<String, Object> map : speciesList) {
            getimage(map);
            if (StringUtils.isBlank(MapUtils.getString(map,"propertyimage"))) {
                map.put("propertyimage", new String[0]);
            }
        }
        if (Integers.isNotEmpty(speciesPropertyListQuery.getAuditstatus())) {
            dealAuditEmptyData(speciesList, Constants.IndexConstant.SPECIESPROPERTY_INDEX, Constants.IndexConstant.SPECIESPROPERTY_TYPE);
        }
        return speciesList;
    }

    /**
     * 查询物种自定义目录的列表数据,带分页
     * @param speciesPropertyListQuery
     * @return
     */
    @Override
    public Page<Map<String, Object>> propertyPageList(SpeciesPropertyListQuery speciesPropertyListQuery) {
        ESQueryBuilderConstructor constructor =new ESQueryBuilderConstructor();
        constructor.setSize(speciesPropertyListQuery.getPageSize());
        constructor.setFrom(speciesPropertyListQuery.getPageNum());
        constructor = getPageQuery(constructor, speciesPropertyListQuery);
        constructor.must(new ESQueryCondition().match("fisdelete", 0));
        constructor.setDesc("updatetime");
        //auditstatus为1时表示审核通过
        if (Integers.isNotEmpty(speciesPropertyListQuery.getAuditstatus())) {
            if (speciesPropertyListQuery.getAuditstatus() == 6) {
                //查询全部的物种数据,排除审核状态完成的数据，即auditstatus为5
                constructor.mustNot(new ESQueryCondition().term("auditstatus", 5));
            } else {
                constructor.must(new ESQueryCondition().match("auditstatus", speciesPropertyListQuery.getAuditstatus()));
            }
        } else {
            //只查询审核通过的物种数据
            constructor.must(new ESQueryCondition().match("auditstatus", 1));
        }
        Page<Map<String, Object>> mapPage = baseEsearchDao.search(Constants.IndexConstant.SPECIESPROPERTY_INDEX, Constants.IndexConstant.SPECIESPROPERTY_TYPE, constructor);
        mapPage.getRecordList().forEach(map->{
                    getimage(map);
                    if (StringUtils.isBlank(MapUtils.getString(map,"propertyimage"))) {
                        map.put("propertyimage", new String[0]);
                    }
                }
        );
        if (Integers.isNotEmpty(speciesPropertyListQuery.getAuditstatus())) {
            dealAuditEmptyData(mapPage, Constants.IndexConstant.SPECIESPROPERTY_INDEX, Constants.IndexConstant.SPECIESPROPERTY_TYPE);
        }
        return mapPage;
    }

    /**
     * 根据es中species_property索引的文档的ID删除对应的数据
     * @param indexid
     * @return
     */
    @Override
    public void deleteSpeciesProperty(String indexid){
        baseEsearchDao.deleteIndex(Constants.IndexConstant.SPECIESPROPERTY_INDEX, Constants.IndexConstant.SPECIESPROPERTY_TYPE, indexid);
    }

    /**
     * 更新物种的自定义目录数据
     * @param speciesPropertyQuery
     * @return
     */
    @Override
    public void updateSpeciesProperty(SpeciesPropertyQuery speciesPropertyQuery){
        Map<String, Object> result = Maps.newHashMap();
        if(StringUtil.isBlank(speciesPropertyQuery.getIndexid())) {
            SpeciesPropertyListQuery speciesPropertyListQuery = new SpeciesPropertyListQuery();
            speciesPropertyListQuery.setSpeciesId(speciesPropertyQuery.getSpeciesid());
            speciesPropertyListQuery.setPropertyid(speciesPropertyQuery.getPropertyid());
            List<Map<String, Object>> propertyInfo = listSpeciesProperty(speciesPropertyListQuery);
            String propertyIndex = MapUtils.getString(propertyInfo.get(0), "indexid");
            speciesPropertyQuery.setIndexid(propertyIndex);
        }
        result = update(speciesPropertyQuery);
        baseEsearchDao.update(Constants.IndexConstant.SPECIESPROPERTY_INDEX, Constants.IndexConstant.SPECIESPROPERTY_TYPE,speciesPropertyQuery.getIndexid(),JSONHelper.toJSON(result));
    }

    /**
     * 更新待审核通过之后的操作
     * @param auditPropertyInfo
     */
    @Override
    public void updatePropertyPassAudit(Map<String, Object> auditPropertyInfo) {
        SpeciesPropertyQuery speciesPropertyQuery = new SpeciesPropertyQuery();
        if (StringUtils.isNotBlank(MapUtils.getString(auditPropertyInfo,"indexid"))) {
            speciesPropertyQuery.setIndexid(MapUtils.getString(auditPropertyInfo,"indexid"));
        }
        if (StringUtils.isNotBlank(MapUtils.getString(auditPropertyInfo,"propertycontent"))) {
            speciesPropertyQuery.setPropertycontent(MapUtils.getString(auditPropertyInfo,"propertycontent"));
        }
        if (StringUtils.isNotBlank(MapUtils.getString(auditPropertyInfo,"propertytitle"))) {
            speciesPropertyQuery.setPropertytitle(MapUtils.getString(auditPropertyInfo,"propertytitle"));
        }
        if (MapUtils.getString(auditPropertyInfo,"propertyimage").contains("[")) {
            JSONArray atlas = JSONHelper.parseArray(MapUtils.getString(auditPropertyInfo,"propertyimage"));
            if (atlas.size() > 0) {
                speciesPropertyQuery.setPropertyimage(MapUtils.getString(auditPropertyInfo,"propertyimage"));
            }
        }
        updateSpeciesProperty(speciesPropertyQuery);

    }


    private Map update(SpeciesPropertyQuery speciesPropertyQuery) {
        Map<String, Object> map= Maps.newHashMap();
        if (StringUtil.isNotBlank(speciesPropertyQuery.getIndexid())) {
            map = baseEsearchDao.getIndexOfMap(Constants.IndexConstant.SPECIESPROPERTY_INDEX, Constants.IndexConstant.SPECIESPROPERTY_TYPE, speciesPropertyQuery.getIndexid());
            map.put("updatetime", System.currentTimeMillis());

            if (StringUtil.isNotBlank(speciesPropertyQuery.getSpeciesid())) {
                map.put("speciesid", speciesPropertyQuery.getSpeciesid());
            }

            if (StringUtil.isNotBlank(speciesPropertyQuery.getPropertytitle())) {
                map.put("propertytitle", speciesPropertyQuery.getPropertytitle());
            }

            if (StringUtil.isNotBlank(speciesPropertyQuery.getPropertycontent())) {
                map.put("propertycontent", speciesPropertyQuery.getPropertycontent());
            }

            if (StringUtil.isNotBlank(speciesPropertyQuery.getPropertyimage())) {
                map.put("propertyimage", speciesPropertyQuery.getPropertyimage());
            }

            if (Integers.isNotEmpty(speciesPropertyQuery.getFisdelete())) {
                map.put("fisdelete", speciesPropertyQuery.getFisdelete());
            }

            if (Integers.isNotEmpty(speciesPropertyQuery.getAuditstatus())) {
                map.put("auditstatus", speciesPropertyQuery.getAuditstatus());
            }

        }
        return map;
    }


    private ESQueryBuilderConstructor getPageQuery(ESQueryBuilderConstructor constructor, SpeciesPropertyListQuery speciesPropertyListQuery){

        if(Integers.isNotEmpty(speciesPropertyListQuery.getSortType())){
            if(speciesPropertyListQuery.getSortType()==1){
                constructor.setAsc("updatetime");
            }
            if(speciesPropertyListQuery.getSortType()==2){
                constructor.setDesc("updatetime");
            }
        }
        constructor.setDesc("updatetime");
        if(StringUtil.isNotBlank(speciesPropertyListQuery.getSpeciesId())){
            constructor.must(new ESQueryCondition().match("speciesid", speciesPropertyListQuery.getSpeciesId()));
        }

        if(StringUtil.isNotBlank(speciesPropertyListQuery.getPropertyid())){
            constructor.must(new ESQueryCondition().match("propertyid", speciesPropertyListQuery.getPropertyid()));
        }

        if (StringUtil.isNotBlank(speciesPropertyListQuery.getKeywords())) {
            constructor.must(new ESQueryCondition().like("propertytitle","*" + speciesPropertyListQuery.getKeywords()+"*"));
        }

        return constructor;
    }

    private Map getimage(Map<String, Object> map){
        if (!MapUtil.isEmpty(map, "propertyimage")) {
            String image = String.valueOf(map.get("propertyimage"));
            if (image.contains("[")) {
                map.put("propertyimage", JSONHelper.parseArray(image));
            }
        }
        return map;
    }

    /**
     * 获取审核通过的原始数据
     * @param record
     * @return
     */
    private Page<Map<String, Object>> getOriginInfo(Map<String, Object> record, String index, String type) {
        ESQueryBuilderConstructor constructor = new ESQueryBuilderConstructor();
        constructor.setSize(10);
        constructor.setFrom(1);
        constructor.must(new ESQueryCondition().match("fisdelete", 0));
        constructor.must(new ESQueryCondition().match("auditstatus", 1));
        constructor.must(new ESQueryCondition().match("propertyid", MapUtils.getString(record, "propertyid")));
        Page<Map<String, Object>> originPage = null;
        originPage = baseEsearchDao.search(index, type, constructor);

        return originPage;
    }

    /**
     * 若提交后台审核的数据中，若标题名称字段为空，查出审核通过的数据赋值
     * @param mapPage
     * @return
     */
    public Page<Map<String, Object>> dealAuditEmptyData(Page<Map<String, Object>> mapPage, String index, String type) {
        for (Map<String, Object> record : mapPage.getRecordList()) {
            if (StringUtils.isBlank(MapUtils.getString(record, "propertytitle"))) {
                //若标题名称字段为空，查出审核通过的数据
                Page<Map<String, Object>> originPage = null;
                originPage = getOriginInfo(record, index, type);
                if (originPage.getRecordList().size() > 0) {
                    List<Map<String, Object>> originList = originPage.getRecordList();
                    Map<String, Object> originMap = originList.get(0);
                    record.put("propertytitle", MapUtils.getString(originMap, "propertytitle"));
                }
            }
        }
        return mapPage;
    }

    /**
     * 若提交后台审核的数据中，若标题名称字段为空，查出审核通过的数据赋值
     * @param speciesList
     * @return
     */
    public List<Map<String, Object>> dealAuditEmptyData(List<Map<String, Object>> speciesList, String index, String type) {
        for (Map<String, Object> record : speciesList) {
            if (StringUtils.isBlank(MapUtils.getString(record, "propertytitle"))) {
                //若标题名称字段为空，查出审核通过的数据
                Page<Map<String, Object>> originPage = null;
                originPage = getOriginInfo(record, index, type);
                if (originPage.getRecordList().size() > 0) {
                    List<Map<String, Object>> originList = originPage.getRecordList();
                    Map<String, Object> originMap = originList.get(0);
                    record.put("propertytitle", MapUtils.getString(originMap, "propertytitle"));
                }
            }
        }
        return speciesList;
    }

}
