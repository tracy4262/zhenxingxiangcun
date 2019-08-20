package com.ovit.nswy.wiki.search.service.impl;

import com.github.xphsc.collect.Lists;
import com.github.xphsc.collect.Maps;
import com.github.xphsc.json.JSONHelper;
import com.github.xphsc.mutable.Integers;
import com.github.xphsc.page.Page;
import com.github.xphsc.util.ObjectUtil;
import com.github.xphsc.util.StringUtil;
import com.ovit.nswy.frame.common.exception.ParamValidException;
import com.ovit.nswy.wiki.common.enums.CatalogEnums;
import com.ovit.nswy.wiki.common.search.ESQueryBuilderConstructor;
import com.ovit.nswy.wiki.common.search.ESQueryCondition;
import com.ovit.nswy.frame.common.Constants;
import com.ovit.nswy.wiki.model.request.SpeciesCatalogQuery;
import com.ovit.nswy.wiki.model.request.SpeciesPropertyListQuery;
import com.ovit.nswy.wiki.model.request.SpeciesWikiQuery;
import com.ovit.nswy.wiki.search.dao.SpeciesDao;
import com.ovit.nswy.wiki.search.service.SpeciesPropertyService;
import com.ovit.nswy.wiki.search.service.SpeciesWikiService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by ${huipei.x} on 2017-10-30.
 */
@Service
@Slf4j
public class SpeciesWikiServiceImpl implements SpeciesWikiService {

    @Autowired
    private SpeciesDao speciesDao;

    @Autowired
    private SpeciesPropertyService speciesPropertyService;


    @Override
    public void updateLikedCount(String indexId, Integer likedcount) {
        Map params = Maps.newHashMap();
        params.put("likedcount", likedcount);
        speciesDao.update(Constants.SPECIES_INDEX, Constants.SPECIES_TYPE, indexId, JSONHelper.toJSON(params));
    }

    @Override
    public Integer getLikedCount(String indexId) throws ParamValidException {
        if(StringUtil.isBlank(indexId)){
            throw new ParamValidException("indexId:indexId不能为空");
        }
        Map<String, Object> result = speciesDao.getIndexOfMap(Constants.SPECIES_INDEX, Constants.SPECIES_TYPE, indexId);
        return Maps.getInteger(result, "likedcount");
    }

    @Override
    public List<Map<String, Object>> listCatalog(SpeciesCatalogQuery speciesCatalogQuery){
        String speciesId = speciesCatalogQuery.getSpecies_id();
        List<Map<String, Object>> mapList = Lists.newArrayList();
        //Map<String, Object> stringObjectMap=Maps.newHashMap();
        //List<Map<String, Object>>  list=Lists.newArrayList(stringObjectMap);
        ESQueryBuilderConstructor constructor = new ESQueryBuilderConstructor();
        constructor.setAsc("catalog_type");
        constructor.setFrom(speciesCatalogQuery.getPageNum());
        constructor.setSize(speciesCatalogQuery.getPageSize());
        //constructor.must(new ESQueryCondition().match("fisdelete", 0));
        //auditstatus为1时表示审核通过
        /*if (Integers.isNotEmpty(speciesCatalogQuery.getAuditstatus())) {
            if (speciesCatalogQuery.getAuditstatus() == 6) {
                //查询全部的物种数据
            } else {
                constructor.must(new ESQueryCondition().match("auditstatus", speciesCatalogQuery.getAuditstatus()));
            }
        } else {
            //只查询审核通过的物种数据
            constructor.must(new ESQueryCondition().match("auditstatus", 1));
        }*/
        if(StringUtil.isNotBlank(speciesCatalogQuery.getPropertyid())){
            constructor.must(new ESQueryCondition().match("propertyid", speciesCatalogQuery.getPropertyid()));
        }
        if(StringUtil.isNotBlank(speciesCatalogQuery.getUserId())){
            constructor.must(new ESQueryCondition().match("user_id", speciesCatalogQuery.getUserId()));
            mapList = speciesDao.searchList(Constants.IndexConstant.SPECIEWIKI_TYPE, Constants.IndexConstant.SPECIEWIKI_TYPE, constructor);
        }
        if(StringUtil.isNotBlank(speciesId)){
            SpeciesWikiQuery SpeciesWikiQuery=new SpeciesWikiQuery();
            SpeciesWikiQuery.setSpecies_id(speciesId);
            constructor.must(new ESQueryCondition().match("species_id", speciesId));
            mapList = speciesDao.searchList(Constants.IndexConstant.SPECIEWIKI_TYPE, Constants.IndexConstant.SPECIEWIKI_TYPE, constructor);
        }
        List<Map<String, Object>> dealList = new ArrayList<>();
        for (Map<String, Object> map : mapList) {
            Map<String, Object> catalogMap = new HashMap<>();
            catalogMap.put("checked", false);
            catalogMap.put("species_id", speciesId);
            catalogMap.put("catalog_name", map.get("catalog_name"));
            catalogMap.put("catalog_type", map.get("catalog_type"));
            catalogMap.put("indexid", map.get("indexid"));
            if (StringUtils.isBlank(MapUtils.getString(map, "propertyid"))) {
                catalogMap.put("propertyid", UUID.randomUUID().toString());
            } else {
                catalogMap.put("propertyid", map.get("propertyid"));
            }
            dealList.add(catalogMap);
        }
        //获取物种对应的动态目录信息
        SpeciesPropertyListQuery speciesPropertyListQuery = new SpeciesPropertyListQuery();
        speciesPropertyListQuery.setSpeciesId(speciesCatalogQuery.getSpecies_id());
        List<Map<String, Object>> propertyInfo = speciesPropertyService.listSpeciesProperty(speciesPropertyListQuery);
        for (Map<String, Object> property : propertyInfo) {
            Map<String, Object> propertyMap = new HashMap<>();
            propertyMap.put("checked", false);
            propertyMap.put("species_id", speciesId);
            propertyMap.put("catalog_name", property.get("propertytitle"));
            propertyMap.put("catalog_type", "");
            propertyMap.put("indexid", property.get("indexid"));
            propertyMap.put("propertyid", property.get("propertyid"));
            dealList.add(propertyMap);
        }

        return dealList;


    }


    @Override
    public  Map<String, Object> getCatalog(String indexId){
        return speciesDao.getIndexOfMap(Constants.IndexConstant.SPECIEWIKI_INDEX, Constants.IndexConstant.SPECIEWIKI_TYPE, indexId);
    }


    @Override
    public Object saveCatalog(SpeciesWikiQuery speciesWikiQuery) {
        Object result = null;
        if(StringUtil.isNotBlank(speciesWikiQuery.getSpecies_id())){
            SpeciesCatalogQuery speciesCatalogQuery = new SpeciesCatalogQuery();
            speciesCatalogQuery.setSpecies_id(speciesWikiQuery.getSpecies_id());
            List<Map<String, Object>> catalogList = listCatalog(speciesCatalogQuery);
            int logNum = 0;
            for (Map<String, Object> catalog : catalogList) {
                int sortNum = MapUtils.getInteger(catalog, "catalog_type");
                if (sortNum > logNum) {
                    logNum = sortNum;
                }
            }
            speciesWikiQuery.setCatalog_type(logNum + 1);
            result = speciesDao.createIndex(Constants.IndexConstant.SPECIEWIKI_INDEX, Constants.IndexConstant.SPECIEWIKI_TYPE, JSONHelper.toJSON(speciesWikiQuery));
            /*if((isExitstype(speciesWikiQuery)).equals(1)){
                Integer value= counType(speciesWikiQuery.getSpecies_id());
                speciesWikiQuery.setCatalog_type(value+1);
                result = speciesDao.createIndex(Constants.IndexConstant.SPECIEWIKI_INDEX, Constants.IndexConstant.SPECIEWIKI_TYPE, JSONHelper.toJSON(speciesWikiQuery));
            }else{
                speciesWikiQuery.setCreate_time(new Date());
                Integer value= counType(speciesWikiQuery.getSpecies_id());
                speciesWikiQuery.setCatalog_type(value + 1);

                result = speciesDao.createIndex(Constants.IndexConstant.SPECIEWIKI_INDEX, Constants.IndexConstant.SPECIEWIKI_TYPE, JSONHelper.toJSON(speciesWikiQuery));
            }*/
        }
        return result;
    }


    @Override
    public Integer initCatalog(SpeciesWikiQuery speciesWikiQuery) {
        Integer  rows=null;
        if(StringUtil.isNotBlank(speciesWikiQuery.getSpecies_id())){
           Object value= isExitstype(speciesWikiQuery);
            if(value.equals(1)){
                rows=1;
            }else{
                if(value.equals(2)){
                    Object addvalue= addCatalog(speciesWikiQuery);
                    rows=2;
                }
            }
        }
        return rows;
    }

    @Override
    public void deleteCatalog(String indexid){
        speciesDao.deleteIndex(Constants.IndexConstant.SPECIEWIKI_INDEX, Constants.IndexConstant.SPECIEWIKI_TYPE, indexid);
    }

    @Override
    public void updateCatalog(SpeciesWikiQuery speciesWikiQuery){
        Map<String, Object> result = Maps.newHashMap();
        if(speciesWikiQuery.getIndexid()!=null){
            result = update(speciesWikiQuery);
            speciesDao.update(Constants.IndexConstant.SPECIEWIKI_INDEX, Constants.IndexConstant.SPECIEWIKI_TYPE, speciesWikiQuery.getIndexid(),JSONHelper.toJSON(result));
        }
    }


    public Integer isExitstype(SpeciesWikiQuery speciesWikiQuery) {
        List<Integer> stringList = Lists.newArrayList(1,2,3,4);
        ESQueryBuilderConstructor constructor = new ESQueryBuilderConstructor();
        Integer result = null;
        constructor.must(new ESQueryCondition().match("species_id", speciesWikiQuery.getSpecies_id()));
        List<Map<String, Object>> mapList = speciesDao.prepareSearch(Constants.IndexConstant.SPECIEWIKI_INDEX, constructor);
        if(Lists.isNotEmpty(mapList)){
                result = 1;
        }else{
            result = 2;
        }

        return result;
    }

    public Integer counType(String  speciesId) {
        ESQueryBuilderConstructor constructor = new ESQueryBuilderConstructor();
        constructor.setDesc("catalog_type");
        constructor.must(new ESQueryCondition().match("species_id", speciesId));
        List<Map<String, Object>> mapList = speciesDao.prepareSearch(Constants.IndexConstant.SPECIEWIKI_INDEX, constructor);
        Integer rows=null;
        if(Lists.isNotEmpty(mapList)){
            rows=Integers.toInteger(mapList.get(0).get("catalog_type"));
        }
        return rows==null?0:rows;
    }


    private Integer addCatalog(SpeciesWikiQuery speciesWikiQuery) {
        List<String> stringList = Lists.newArrayList();
        Integer result=null;
        if (StringUtil.isNotBlank(speciesWikiQuery.getSpecies_id())) {
            SpeciesWikiQuery speciesWikiQuerytwo = new SpeciesWikiQuery();
            speciesWikiQuerytwo.setSpecies_id(speciesWikiQuery.getSpecies_id());
            speciesWikiQuerytwo.setCatalog_type(CatalogEnums.CATALOG_ONE.getCode());
            speciesWikiQuerytwo.setCatalog_name(CatalogEnums.CATALOG_ONE.getName());
            SpeciesWikiQuery speciesWikiQuerythree = new SpeciesWikiQuery();
            speciesWikiQuerythree.setSpecies_id(speciesWikiQuery.getSpecies_id());
            speciesWikiQuerythree.setCatalog_type(CatalogEnums.CATALOG_TWO.getCode());
            speciesWikiQuerythree.setCatalog_name(CatalogEnums.CATALOG_TWO.getName());
            SpeciesWikiQuery speciesWikiQueryFour = new SpeciesWikiQuery();
            speciesWikiQueryFour.setSpecies_id(speciesWikiQuery.getSpecies_id());
            speciesWikiQueryFour.setCatalog_type(CatalogEnums.CATALOG_THREE.getCode());
            speciesWikiQueryFour.setCatalog_name(CatalogEnums.CATALOG_THREE.getName());
            SpeciesWikiQuery speciesWikiQueryFive = new SpeciesWikiQuery();
            speciesWikiQueryFive.setSpecies_id(speciesWikiQuery.getSpecies_id());
            speciesWikiQueryFive.setCatalog_type(CatalogEnums.CATALOG_FOUR.getCode());
            speciesWikiQueryFive.setCatalog_name(CatalogEnums.CATALOG_FOUR.getName());
            stringList.add(JSONHelper.toJSON(speciesWikiQuerythree));
            stringList.add(JSONHelper.toJSON(speciesWikiQuerytwo));
            stringList.add(JSONHelper.toJSON(speciesWikiQueryFour));
            stringList.add(JSONHelper.toJSON(speciesWikiQueryFive));
            Object rows=speciesDao.bulkInsert(Constants.IndexConstant.SPECIEWIKI_INDEX, Constants.IndexConstant.SPECIEWIKI_TYPE, stringList);
           if(ObjectUtil.isNotEmpty(rows)){
               result= Integers.toInteger(rows);
           }
        }
        return result;
    }



    private Map update(SpeciesWikiQuery speciesWikiQuery) {
        Map<String, Object> map= Maps.newHashMap();
        if (StringUtil.isNotBlank(speciesWikiQuery.getIndexid())) {
            map = speciesDao.getIndexOfMap(Constants.IndexConstant.SPECIEWIKI_INDEX, Constants.IndexConstant.SPECIEWIKI_TYPE, speciesWikiQuery.getIndexid());
            map.put("update_time", System.currentTimeMillis());
            if (StringUtil.isNotBlank( speciesWikiQuery.getSpecies_id())) {
                map.put("species_id", speciesWikiQuery.getSpecies_id());
            }
            if (Integers.isNotEmpty(speciesWikiQuery.getSpecies_type())) {
                map.put("species_type", speciesWikiQuery.getSpecies_type());
            }
            if (Integers.isNotEmpty(speciesWikiQuery.getCatalog_type())) {
                map.put("catalog_type", speciesWikiQuery.getCatalog_type());
            }
            if (StringUtil.isNotBlank(speciesWikiQuery.getCatalog_name())) {
                map.put("catalog_name", speciesWikiQuery.getCatalog_name());
            }
            if (StringUtil.isNotBlank(speciesWikiQuery.getCatalog_content())) {
                map.put("catalog_content", speciesWikiQuery.getCatalog_content());
            }


        }
        return map;
    }

    /**
     * 若提交后台审核的数据中，若物种名称或者汉语拼音字段为空时，查出审核通过的数据赋值
     * @param mapPage
     * @return
     */
    public Page<Map<String, Object>> dealAuditEmptyData(Page<Map<String, Object>> mapPage, String index, String type) {
        for (Map<String, Object> record : mapPage.getRecordList()) {
            if (StringUtils.isBlank(MapUtils.getString(record, "fname")) || StringUtils.isBlank(MapUtils.getString(record, "fpinyin"))) {
                //若物种名称或者汉语拼音字段为空，查出审核通过的数据
                Page<Map<String, Object>> originPage = null;
                originPage = getOriginInfo(record, index, type);
                if (originPage.getRecordList().size() > 0) {
                    List<Map<String, Object>> originList = originPage.getRecordList();
                    Map<String, Object> originMap = originList.get(0);
                    record.put("fname", MapUtils.getString(originMap, "fname"));
                    record.put("fpinyin", MapUtils.getString(originMap, "fpinyin"));
                }
            }
        }
        return mapPage;
    }

    /**
     * 获取审核通过的原始数据
     * @param record
     * @return
     */
    public Page<Map<String, Object>> getOriginInfo(Map<String, Object> record, String index, String type) {
        ESQueryBuilderConstructor constructor = new ESQueryBuilderConstructor();
        constructor.setSize(10);
        constructor.setFrom(1);
        constructor.must(new ESQueryCondition().match("fisdelete", 0));
        constructor.must(new ESQueryCondition().match("auditstatus", 1));
        constructor.must(new ESQueryCondition().match("fid", MapUtils.getString(record, "fid")));
        Page<Map<String, Object>> originPage = null;
        originPage = speciesDao.search(index, type, constructor);

        return originPage;
    }

}
