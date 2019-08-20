package com.ovit.nswy.wiki.search.service.impl;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.github.xphsc.collect.Lists;
import com.github.xphsc.collect.Maps;
import com.github.xphsc.date.DateTimeUtil;
import com.github.xphsc.json.JSONHelper;
import com.github.xphsc.json.JSONObject;
import com.github.xphsc.mutable.Integers;
import com.github.xphsc.page.Page;
import com.github.xphsc.util.PinyinUtil;
import com.github.xphsc.util.RandomUtil;
import com.github.xphsc.util.StringUtil;
import com.ovit.nswy.frame.common.Constants;
import com.ovit.nswy.frame.common.exception.ParamValidException;
import com.ovit.nswy.frame.common.util.RegexUtil;
import com.ovit.nswy.wiki.common.MapUtil;
import com.ovit.nswy.wiki.common.search.ESQueryBuilderConstructor;
import com.ovit.nswy.wiki.common.search.ESQueryCondition;
import com.ovit.nswy.wiki.model.request.SpeciesAddQuery;
import com.ovit.nswy.wiki.model.request.SpeciesCatalogQuery;
import com.ovit.nswy.wiki.model.request.SpeciesClassListQuery;
import com.ovit.nswy.wiki.model.request.SpeciesQuery;
import com.ovit.nswy.wiki.search.dao.SpeciesDao;
import com.ovit.nswy.wiki.search.service.SpeciesServce;
import com.ovit.nswy.wiki.search.service.SpeciesWikiService;
import com.ovit.nswy.wiki.search.service.SpeciesclassifiedService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by ${huipei.x} on 2017-10-19.
 */
@Service
@Slf4j
public class SpeciesServceImpl implements SpeciesServce {

    private Logger logger = LogManager.getLogger(this.getClass());

    @Autowired
    private SpeciesDao speciesDao;
    @Autowired
    SpeciesWikiService speciesWikiService;
    @Autowired
    private SpeciesclassifiedService speciesclassifiedService;

    @Override
    public Page<Map<String, Object>> listSpecies(SpeciesQuery speciesQuery) throws ParamValidException {
        ESQueryBuilderConstructor constructor = new ESQueryBuilderConstructor();
        constructor.setSize(speciesQuery.getPageSize());
        constructor.setFrom(speciesQuery.getPageNum());
        constructor = getPageQuery(constructor,speciesQuery);
        constructor.must(new ESQueryCondition().match("fisdelete", 0));
        constructor.must(new ESQueryCondition().match("fstate", 1));
        //auditstatus为1时表示审核通过
        if (Integers.isNotEmpty(speciesQuery.getAuditStatus())) {
            if (speciesQuery.getAuditStatus() == 6) {
                //查询全部的物种数据,排除审核状态完成的数据，即auditstatus为5
                constructor.mustNot(new ESQueryCondition().term("auditstatus", 5));
            } else {
                constructor.must(new ESQueryCondition().match("auditstatus", speciesQuery.getAuditStatus()));
            }
            if (Integers.isEmpty(speciesQuery.getSortType())) {
                //后台审核查询物种列表时，若没有传排序的字段，则根据创建时间排序
                constructor.setDesc("updatetime");
            }
        } else {
            //只查询审核通过的物种数据
            constructor.must(new ESQueryCondition().match("auditstatus", 1));
            constructor.mustNot(new ESQueryCondition().term("fcode.keyword", ""));
        }
        if (StringUtils.isNotBlank(speciesQuery.getIsLemma())) {
            //如查询的是详情页右侧的相关词条时，则排除自身的物种。
            constructor.mustNot(new ESQueryCondition().term("fname", speciesQuery.getIsLemma()));
        }
        Page<Map<String, Object>> mapPage = null;
        mapPage = speciesDao.search(Constants.SPECIES_INDEX, Constants.SPECIES_TYPE, constructor);
        mapPage.getRecordList().forEach(map -> {
                    getFimage(map);
                }
        );

        return mapPage;
    }


    @Override
    public List<Map<String, Object>> findBySpeciesName(SpeciesQuery speciesQuery){
        List<Map<String, Object>> mapList= Lists.newArrayList();
        ESQueryBuilderConstructor constructor =new ESQueryBuilderConstructor();
        if(StringUtil.isNotBlank(speciesQuery.getFname())){
            constructor.must(new ESQueryCondition().match("fname",speciesQuery.getFname()));
        }
        if(StringUtil.isNotBlank(speciesQuery.getIndexid())){
            constructor.must(new ESQueryCondition().match("indexid",speciesQuery.getIndexid()));
        }
        if(Integers.isNotEmpty(speciesQuery.getAuditStatus())){
            //只查询审核通过的物种数据
            constructor.must(new ESQueryCondition().match("auditstatus", speciesQuery.getAuditStatus()));
        }
        if(StringUtil.isNotBlank(speciesQuery.getFcreatorid())){
            constructor.must(new ESQueryCondition().match("fcreatorid",speciesQuery.getFcreatorid()));
        }
        if(StringUtil.isNotBlank(speciesQuery.getSpeciesid())){
            constructor.must(new ESQueryCondition().match("speciesid",speciesQuery.getSpeciesid()));
        }

        mapList = speciesDao.searchList(Constants.SPECIES_INDEX, Constants.SPECIES_TYPE, constructor);
        return mapList;
    }

    @Override
    public List<Map<String, Object>> findListSpecies(SpeciesQuery speciesQuery) throws ParamValidException {
        ESQueryBuilderConstructor constructor = new ESQueryBuilderConstructor();
        constructor.setSize(speciesQuery.getPageSize());
        constructor.setFrom(speciesQuery.getPageNum());
        constructor = getPageQuery(constructor,speciesQuery);
        constructor.must(new ESQueryCondition().match("fisdelete", 0));
        constructor.must(new ESQueryCondition().match("fstate", 1));
        //auditstatus为1时表示审核通过
        if (Integers.isNotEmpty(speciesQuery.getAuditStatus())) {
            if (speciesQuery.getAuditStatus() == 6) {
                //查询全部的物种数据,排除审核状态完成的数据，即auditstatus为5
                constructor.mustNot(new ESQueryCondition().term("auditstatus", 5));
            } else {
                constructor.must(new ESQueryCondition().match("auditstatus", speciesQuery.getAuditStatus()));
            }
            if (Integers.isEmpty(speciesQuery.getSortType())) {
                //后台审核查询物种列表时，若没有传排序的字段，则根据创建时间排序
                constructor.setDesc("updatetime");
            }
        } else {
            //只查询审核通过的物种数据
            constructor.must(new ESQueryCondition().match("auditstatus", 1));
        }
        if (StringUtils.isNotBlank(speciesQuery.getIsLemma())) {
            //如查询的是详情页右侧的相关词条时，则排除自身的物种。
            constructor.mustNot(new ESQueryCondition().term("fname", speciesQuery.getIsLemma()));
        }
        List<Map<String, Object>> list = speciesDao.searchList(Constants.SPECIES_INDEX, Constants.SPECIES_TYPE, constructor);


        return list;
    }

    @Override
    public Page<Map<String, Object>> getLatelyData(SpeciesQuery speciesQuery) throws ParamValidException {
        ESQueryBuilderConstructor constructor = new ESQueryBuilderConstructor();
        constructor.setSize(speciesQuery.getPageSize());
        constructor.setFrom(speciesQuery.getPageNum());
        if (speciesQuery.getSortType() == 1) {
            constructor.setAsc("updatetime");
        }
        if (speciesQuery.getSortType() == 2) {
            constructor.setDesc("updatetime");
        }

        constructor.must(new ESQueryCondition().match("fisdelete", 0));
        Page<Map<String, Object>> mapPage = null;
        mapPage = speciesDao.search(Constants.SPECIES_INDEX, Constants.SPECIES_TYPE, constructor);
        mapPage.getRecordList().forEach(map -> {
                    getFimage(map);
                }
        );

        return mapPage;
    }

    @Override
    public List<Map<String, Object>> getSpeciesList(SpeciesQuery speciesQuery) throws ParamValidException {
        ESQueryBuilderConstructor constructor = new ESQueryBuilderConstructor();
        constructor = getPageQuery(constructor,speciesQuery);
        constructor.must(new ESQueryCondition().match("fisdelete", 0));
        constructor.must(new ESQueryCondition().match("fstate", 1));
        //auditstatus为1时表示审核通过
        constructor.must(new ESQueryCondition().match("auditstatus", 1));
        List<Map<String, Object>> speciesList  = speciesDao.searchList(Constants.SPECIES_INDEX, Constants.SPECIES_TYPE, constructor);
        speciesList.forEach(map -> {
            getFimage(map);
        });

        return speciesList;
    }

    @Override
    public Object saveSpecies(SpeciesAddQuery speciesAddQuery) {
        Object rows= null;
        //speciesAddQuery.setSpeciesid(RandomUtil.randomNumbers(10));
        if (StringUtils.isBlank(speciesAddQuery.getSpeciesid())) {
            speciesAddQuery.setSpeciesid(UUID.randomUUID().toString());
        }
        speciesAddQuery.setFcreatetime(new Date());
        speciesAddQuery.setFisdelete(0);
        speciesAddQuery.setFstate(1);
        speciesAddQuery.setUpdatetime(System.currentTimeMillis());
        speciesAddQuery.setFupdatorid(speciesAddQuery.getFcreatorid());
        Object result = speciesDao.createIndex(Constants.SPECIES_INDEX, Constants.SPECIES_TYPE, JSONHelper.toJSON(speciesAddQuery));
        Map<String, Object> map = speciesDao.getIndexOfMap(Constants.SPECIES_INDEX, Constants.SPECIES_TYPE, String.valueOf(result));
        if(MapUtils.isNotEmpty(map)){
            rows=map.get("speciesid");
        }
        return rows == null ? 0 : rows;
    }


    @Override
    public Map<String, Object> getSpecies(String indexid) {
        Map<String, Object> map=null;
        //List list = Lists.newArrayList();
        if(StringUtil.isNotBlank(indexid)){
            map = speciesDao.getIndexOfMap(Constants.SPECIES_INDEX, Constants.SPECIES_TYPE, indexid);
            getFimage(map);
            SpeciesCatalogQuery speciesCatalogQuery = new SpeciesCatalogQuery();
            if (map.get("speciesid") != null && !map.get("speciesid").equals("")) {
                speciesCatalogQuery.setSpecies_id(String.valueOf(map.get("speciesid")));
                List<Map<String, Object>> page = speciesWikiService.listCatalog(speciesCatalogQuery);
                Map<String, Object> stringObjectMap =Maps.newHashMap();
                page.forEach(objectMap->{
                    stringObjectMap.put("id", objectMap.get("indexid"));
                    stringObjectMap.put("val", objectMap.get("catalog_name"));
                    //list.add(stringObjectMap);
                });
                //map.put("catalogTypeInfo", list);
            }

        }
        return map;
    }

    @Override
    public void deleteSpecies(String indexid) {
        if(StringUtil.isNotBlank(indexid)) {
            speciesDao.deleteIndex(Constants.SPECIES_INDEX, Constants.SPECIES_TYPE, indexid);
        }
    }

    @Override
    public String getPinYin(String chinese) {
        String rows = PinyinUtil.getCamelPinYin(chinese);
        return rows;
    }

    @Override
    public void updateFisdeleteByIndexid(String indexid, Integer fisdelete) {
        JSONObject params = new JSONObject();
        params.put("fisdelete", fisdelete);
        speciesDao.update(Constants.SPECIES_INDEX, Constants.SPECIES_TYPE, indexid, params.toJSONString());
    }


    @Override
    public void updateSpecies(SpeciesAddQuery speciesAddQuery) {
        Map<String, Object> result =Maps.newHashMap();
        if (StringUtil.isNotBlank(speciesAddQuery.getIndexid())) {
            result = update(speciesAddQuery);
            speciesDao.update(Constants.SPECIES_INDEX, Constants.SPECIES_TYPE, speciesAddQuery.getIndexid(), JSONHelper.toJSON(result));
        }
    }

    @Override
    public Object isNameExist(String name) {
        ESQueryBuilderConstructor constructor = new ESQueryBuilderConstructor();
        Object result = null;
        constructor.must(new ESQueryCondition().term("fname", name));
        //auditstatus为1时表示审核通过
        constructor.must(new ESQueryCondition().match("auditstatus", 1));
        constructor.must(new ESQueryCondition().match("fisdelete", 0));
        List<Map<String, Object>> mapList = speciesDao.prepareSearch(Constants.SPECIES_INDEX, constructor);
        if (mapList.size() == 1) {
            result = 1;
        } else {
            result = 0;
        }
        return result;
    }

    private Map update(SpeciesAddQuery speciesAddQuery) {
        Map<String, Object> map=Maps.newHashMap();
        if (StringUtil.isNotBlank(speciesAddQuery.getIndexid())) {
           map = speciesDao.getIndexOfMap(Constants.SPECIES_INDEX, Constants.SPECIES_TYPE, speciesAddQuery.getIndexid());
            map.put("updatetime", System.currentTimeMillis());
            if (StringUtil.isNotBlank(speciesAddQuery.getFclassifiedid())) {
                map.put("fclassifiedid", speciesAddQuery.getFclassifiedid());
            }
            if (StringUtil.isNotBlank(speciesAddQuery.getFname())) {
                map.put("fname", speciesAddQuery.getFname());
            }
            if (StringUtil.isNotBlank(speciesAddQuery.getFpinyin())) {
                map.put("fpinyin", speciesAddQuery.getFpinyin());
            }
            if (StringUtil.isNotBlank(speciesAddQuery.getFouthclassifiedid())) {
                map.put("fouthclassifiedid", speciesAddQuery.getFouthclassifiedid());
            }
            if (StringUtil.isNotBlank(speciesAddQuery.getFindustriaclassifiedid())) {
                map.put("findustriaclassifiedid", speciesAddQuery.getFindustriaclassifiedid());
            }
            if (StringUtil.isNotBlank(speciesAddQuery.getFicon())) {
                map.put("ficon", speciesAddQuery.getFicon());
            }
            if (StringUtil.isNotBlank(speciesAddQuery.getFimage())) {
                map.put("fimage", speciesAddQuery.getFimage());
            }
            if (StringUtil.isNotBlank(speciesAddQuery.getFshapefeatureid())) {
                map.put("fshapefeatureid", speciesAddQuery.getFshapefeatureid());
            }
            if (StringUtil.isNotBlank(speciesAddQuery.getFremarks())) {
                map.put("fremarks", speciesAddQuery.getFremarks());
            }
            if (Integers.isNotEmpty(speciesAddQuery.getFisattention())) {

                map.put("fisattention", speciesAddQuery.getFisattention());
            }

            if (StringUtil.isNotBlank(speciesAddQuery.getFisprotection())) {

                map.put("fisprotection", speciesAddQuery.getFisprotection());
            }

            if (StringUtil.isNotBlank(speciesAddQuery.getFcreatorid())) {
                map.put("fupdatorid", speciesAddQuery.getFcreatorid());
            }

            if (Integers.isNotEmpty(speciesAddQuery.getAuditstatus())) {
                map.put("auditstatus", speciesAddQuery.getAuditstatus());
            }
            if (StringUtil.isNotBlank(speciesAddQuery.getDescribe())) {
                map.put("describe", speciesAddQuery.getDescribe());
            }

            if (StringUtil.isNotBlank(speciesAddQuery.getImage())) {
                map.put("image", speciesAddQuery.getImage());
            }
            if (StringUtil.isNotBlank(speciesAddQuery.getSpeciesVulgo())) {
                map.put("speciesVulgo", speciesAddQuery.getSpeciesVulgo());
            }
            if (StringUtil.isNotBlank(speciesAddQuery.getOtherClassify())) {
                map.put("otherClassify", speciesAddQuery.getOtherClassify());
            }
            if (StringUtil.isNotBlank(speciesAddQuery.getMajorProduct())) {
                map.put("majorProduct", speciesAddQuery.getMajorProduct());
            }
            if (StringUtil.isNotBlank(speciesAddQuery.getSpeciesAtlas())) {
                map.put("speciesAtlas", speciesAddQuery.getSpeciesAtlas());
            }
            if (Integers.isNotEmpty(speciesAddQuery.getFisdelete())) {
                map.put("fisdelete", speciesAddQuery.getFisdelete());
            }
        }
        return map;
    }


    private ESQueryBuilderConstructor getPageQuery(ESQueryBuilderConstructor constructor,SpeciesQuery speciesQuery){

        if (StringUtil.isNotBlank(speciesQuery.getUserId())) {
            constructor.must(new ESQueryCondition().match("fcreatorid", speciesQuery.getUserId()));
        }
        if (Integers.isNotEmpty(speciesQuery.getSortType())) {
            if (speciesQuery.getSortType() == 1) {
                constructor.setAsc("updatetime");
            }
            if (speciesQuery.getSortType() == 2) {
                constructor.setDesc("updatetime");
            }
        }
        //constructor.setDesc("updatetime");
         if (StringUtil.isNotBlank(speciesQuery.getKeywords())) {

            if (RegexUtil.regChinese(speciesQuery.getKeywords()) || RegexUtil.isContainNumber(speciesQuery.getKeywords())) {
                constructor.must(new ESQueryCondition().like("fname","*" + speciesQuery.getKeywords()+ "*"));
            } else {
                constructor.must(new ESQueryCondition().like("fpinyin", speciesQuery.getKeywords().toLowerCase() + "*"));
            }
        }
        if (StringUtil.isNotBlank(speciesQuery.getFname())) {
            constructor.must(new ESQueryCondition().prefix("fname", speciesQuery.getFname()));
        }
        if (StringUtil.isNotBlank(speciesQuery.getSpeciesid())) {
            constructor.must(new ESQueryCondition().term("speciesid", speciesQuery.getSpeciesid()));
        }

        if (StringUtil.isNotBlank(speciesQuery.getFcode())) {
            constructor.must(new ESQueryCondition().match("fcode",speciesQuery.getFcode()));
        }

        if (StringUtil.isNotBlank(speciesQuery.getFclassifiedid())) {
            constructor.must(new ESQueryCondition().match("fclassifiedid", speciesQuery.getFclassifiedid()));
        }
        if (StringUtil.isNotBlank(speciesQuery.getFpinyin())) {
            constructor.must(new ESQueryCondition().like("fpinyin", speciesQuery.getFpinyin().toLowerCase() + "*"));
        }
        if (Integers.isNotEmpty(speciesQuery.getLatelyType())) {
            Long time = DateTimeUtil.dateTimePlusDaysHMS$ToZero(DateTimeUtil.dateTime(), -30);
            if (speciesQuery.getLatelyType() == 1) {
                constructor.must(new ESQueryCondition().rangeOfGT("updatetime", time));
            }
            Long latelyTypetime = DateTimeUtil.dateTimePlusDaysHMS$ToZero(DateTimeUtil.dateTime(), -15);
            if (speciesQuery.getLatelyType() == 2) {
                constructor.must(new ESQueryCondition().rangeOfGT("updatetime", latelyTypetime));
            }
        }

        if (Integers.isNotEmpty(speciesQuery.getSeeType())) {
            if (speciesQuery.getSeeType() == 1) {
                if (StringUtil.isNotBlank(speciesQuery.getUserId())) {
                    constructor.must(new ESQueryCondition().match("fupdatorid", speciesQuery.getUserId()));
                }
            }
        }

        if (StringUtil.isNotBlank(speciesQuery.getFindustriaclassifiedid())) {
            constructor.must(new ESQueryCondition().match("findustriaclassifiedid", speciesQuery.getFindustriaclassifiedid()));
        }

        return constructor;
    }

    private Map getFimage(Map<String, Object> map){
      if (!MapUtil.isEmpty(map,"fimage")) {
            String fimage=String.valueOf(map.get("fimage"));
            if (String.valueOf(map.get("fimage")).contains("[")) {
                map.put("fimage", JSONHelper.parseArray(fimage));
            }
        }
        if(!MapUtil.isEmpty(map,"image")){
            if (String.valueOf(map.get("image")).contains("[")) {
                map.put("image", JSONHelper.parseArray(String.valueOf(map.get("image"))));
            }
        }
        if(!MapUtil.isEmpty(map,"speciesAtlas")){
            if (String.valueOf(map.get("speciesAtlas")).contains("[")) {
                map.put("speciesAtlas", JSONHelper.parseArray(String.valueOf(map.get("speciesAtlas"))));
            }
        }
        if(!MapUtil.isEmpty(map,"ficon")){
            if (String.valueOf(map.get("ficon")).contains("[")) {
                map.put("ficon", JSONHelper.parseArray(String.valueOf(map.get("ficon"))));
            }
        }
        return map;
    }

    @Override
    public List<Map<String,Object>> speciesPageInfo(List<Map<String, Object>> speciesList, int pageNum, int pageSize){
        List<Map<String,Object>> speciesDealList = new ArrayList<>();
        List<Map<String, Object>> subList = null;
        try {
            //根据Collections.sort重载方法来实现
            Collections.sort(speciesList, new Comparator<Map<String, Object>>() {
                @Override
                public int compare(Map<String, Object> b1, Map<String, Object> b2) {
                    //升序
                    return MapUtils.getString(b1, "fcode").compareTo(MapUtils.getString(b2, "fcode"));
                }
            });

            for (Map<String,Object> species: speciesList) {
                Map<String,Object> speciesDeal = new HashMap<>();
                speciesDeal.put("name", species.get("fname"));
                speciesDeal.put("checked", false);
                speciesDeal.put("id", species.get("fclassifiedid"));
                speciesDeal.put("indexid", species.get("indexid"));
                speciesDeal.put("ficon", species.get("ficon"));
                speciesDeal.put("speciesid", species.get("speciesid"));
                speciesDealList.add(speciesDeal);
            }
            logger.info("处理后的物种列表数据为：{}", speciesDealList);
            subList = pageForList(pageNum, pageSize, speciesDealList);
            logger.info("分页后的List为：{}", subList);

        } catch (Exception e) {
            logger.error(e);
        }

        return subList;
    }

    /**
     *
     * @param pageNo 当前页码
     * @param pageSize 页数
     * @param list  所有集合
     * @return
     * @throws Exception
     */
    public static List<Map<String, Object>> pageForList(int pageNo,int pageSize,List<Map<String, Object>> list) throws Exception{
        List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
        if(list != null && list.size() > 0){
            int allCount = list.size();
            int pageCount = (allCount + pageSize-1) / pageSize;
            if(pageNo >= pageCount){
                pageNo = pageCount;
            }
            int start = (pageNo-1) * pageSize;
            int end = pageNo * pageSize;
            if(end >= allCount){
                end = allCount;
            }
            for(int i = start; i < end; i ++){
                result.add(list.get(i));
            }
        }
        return (result != null && result.size() > 0) ? result : null;
    }

    @Override
    public Map<String, Object> listClassifySpeciesInfo() {
        Object detailInfo= speciesclassifiedService.listAllSpeciesclass();
        Map<String,Object> allclassifyInfo = (Map<String,Object>) detailInfo;
        List<Map<String,Object>> animalList = (List<Map<String, Object>>) allclassifyInfo.get("animalList");
        List<Map<String,Object>> plantList = (List<Map<String, Object>>) allclassifyInfo.get("plantList");

        Map<String,Object> speciesInfo = new HashMap<>();
        speciesInfo.put("animalList", animalList);
        speciesInfo.put("plantList", plantList);

        return speciesInfo;
    }

    /**
     * 更新待审核通过之后的操作
     * @param auditSpeciesInfo
     */
    @Override
    public void updateSpeciesPassAudit(Map<String, Object> auditSpeciesInfo) {
        SpeciesAddQuery speciesAddQuery = new SpeciesAddQuery();
        mapToSpeciesAddQuery(speciesAddQuery, auditSpeciesInfo);
        updateSpecies(speciesAddQuery);
    }

    private Map<String,Object> getClassifyInfo(List<Map<String,Object>> list, String type, int id) {
        Map<String, Object> classify = new HashMap<>();
        classify.put("name", type);
        classify.put("checked", false);
        classify.put("classId", String.valueOf(id));
        List<Map<String,Object>> classifyList = new ArrayList<>();
        for (Map<String, Object> map : list) {
            Map<String, Object> childrenMap = new HashMap<>();
            childrenMap.put("name", map.get("className"));
            childrenMap.put("checked", false);
            childrenMap.put("classId", map.get("classId"));
            childrenMap.put("indexid", map.get("indexid"));
            classifyList.add(childrenMap);
        }
        classify.put("children", classifyList);
        return classify;
    }

    @Override
    public SpeciesAddQuery mapToSpeciesAddQuery(SpeciesAddQuery speciesAddQuery, Map<String, Object> auditSpeciesInfo) {
        if (StringUtils.isNotBlank(MapUtils.getString(auditSpeciesInfo,"indexid"))) {
            speciesAddQuery.setIndexid(MapUtils.getString(auditSpeciesInfo,"indexid"));
        }
        if (StringUtils.isNotBlank(MapUtils.getString(auditSpeciesInfo,"speciesid"))) {
            speciesAddQuery.setSpeciesid(MapUtils.getString(auditSpeciesInfo,"speciesid"));
        }
        if (StringUtils.isNotBlank(MapUtils.getString(auditSpeciesInfo,"fclassifiedid"))) {
            speciesAddQuery.setFclassifiedid(MapUtils.getString(auditSpeciesInfo,"fclassifiedid"));
        }
        if (StringUtils.isNotBlank(MapUtils.getString(auditSpeciesInfo,"findustriaclassifiedid"))) {
            speciesAddQuery.setFindustriaclassifiedid(MapUtils.getString(auditSpeciesInfo,"findustriaclassifiedid"));
        }
        if (StringUtils.isNotBlank(MapUtils.getString(auditSpeciesInfo,"fname"))) {
            speciesAddQuery.setFname(MapUtils.getString(auditSpeciesInfo,"fname"));
        }
        if (StringUtils.isNotBlank(MapUtils.getString(auditSpeciesInfo,"fpinyin"))) {
            speciesAddQuery.setFpinyin(MapUtils.getString(auditSpeciesInfo,"fpinyin"));
        }
        if (StringUtils.isNotBlank(MapUtils.getString(auditSpeciesInfo,"fisprotection"))) {
            speciesAddQuery.setFisprotection(MapUtils.getString(auditSpeciesInfo,"fisprotection"));
        }
        if (Integers.isNotEmpty(MapUtils.getInteger(auditSpeciesInfo,"likedcount"))) {
            speciesAddQuery.setLikedcount(MapUtils.getInteger(auditSpeciesInfo,"likedcount"));
        }
        if (StringUtils.isNotBlank(MapUtils.getString(auditSpeciesInfo,"fcreatorid"))) {
            speciesAddQuery.setFcreatorid(MapUtils.getString(auditSpeciesInfo,"fcreatorid"));
        }
        if (StringUtils.isNotBlank(MapUtils.getString(auditSpeciesInfo,"fshapefeatureid"))) {
            speciesAddQuery.setFshapefeatureid(MapUtils.getString(auditSpeciesInfo,"fshapefeatureid"));
        }
        if (StringUtils.isNotBlank(MapUtils.getString(auditSpeciesInfo,"speciesVulgo"))) {
            speciesAddQuery.setSpeciesVulgo(MapUtils.getString(auditSpeciesInfo,"speciesVulgo"));
        }
        if (StringUtils.isNotBlank(MapUtils.getString(auditSpeciesInfo,"otherClassify"))) {
            speciesAddQuery.setOtherClassify(MapUtils.getString(auditSpeciesInfo,"otherClassify"));
        }
        if (StringUtils.isNotBlank(MapUtils.getString(auditSpeciesInfo,"majorProduct"))) {
            speciesAddQuery.setMajorProduct(MapUtils.getString(auditSpeciesInfo,"majorProduct"));
        }
        if (StringUtils.isNotBlank(MapUtils.getString(auditSpeciesInfo,"ficon"))) {
            if (auditSpeciesInfo.get("ficon") instanceof String[]) {
                String[] ficonArray = (String[]) auditSpeciesInfo.get("ficon");
                if (ficonArray.length > 0) {
                    speciesAddQuery.setFicon(MapUtils.getString(auditSpeciesInfo,"ficon"));
                }
            } else {
                speciesAddQuery.setFicon(MapUtils.getString(auditSpeciesInfo,"ficon"));
            }
        }
        if (MapUtils.getString(auditSpeciesInfo,"speciesAtlas").contains("[")) {
            JSONArray atlas = JSONHelper.parseArray(MapUtils.getString(auditSpeciesInfo,"speciesAtlas"));
            if (atlas.size() > 0) {
                speciesAddQuery.setSpeciesAtlas(MapUtils.getString(auditSpeciesInfo,"speciesAtlas"));
            }
        }
        if (MapUtils.getString(auditSpeciesInfo,"fimage").contains("[")) {
            JSONArray atlas = JSONHelper.parseArray(MapUtils.getString(auditSpeciesInfo,"fimage"));
            if (atlas.size() > 0) {
                speciesAddQuery.setFimage(MapUtils.getString(auditSpeciesInfo,"fimage"));
            }
        }
        if (StringUtils.isNotBlank(MapUtils.getString(auditSpeciesInfo,"fremarks"))) {
            speciesAddQuery.setFremarks(MapUtils.getString(auditSpeciesInfo,"fremarks"));
        }

        return speciesAddQuery;
    }

    @Override
    public Map<String, Object> getSpeciesClassify(SpeciesQuery speciesQuery) {
        ESQueryBuilderConstructor constructor = new ESQueryBuilderConstructor();
        constructor.setSize(speciesQuery.getPageSize());
        constructor.setFrom(speciesQuery.getPageNum());
        constructor.must(new ESQueryCondition().match("fisdelete", 0));
        //只查询审核通过的物种数据
        constructor.must(new ESQueryCondition().match("auditstatus", 1));
        constructor.must(new ESQueryCondition().term("speciesid", speciesQuery.getSpeciesid()));
        Page<Map<String, Object>> mapPage = null;
        mapPage = speciesDao.search(Constants.SPECIES_INDEX, Constants.SPECIES_TYPE, constructor);
        Map<String, Object> speciesInfo =  new HashMap<>();
        String classId = MapUtils.getString(mapPage.getRecordList().get(0), "fclassifiedid");
        SpeciesClassListQuery speciesClassListQuery = new SpeciesClassListQuery();
        speciesClassListQuery.setClassId(classId);
        List<Map<String, Object>> classifyInfo = speciesclassifiedService.listSpeciesclassified(speciesClassListQuery);
        String parentId = MapUtils.getString(classifyInfo.get(0), "parentId");
        speciesInfo.put("classId", classId);
        speciesInfo.put("type", "0".equals(parentId) ? "动物" : "植物");

        return speciesInfo;
    }


    @Override
    public void insertSpecies(Map params){
        speciesDao.createIndex(Constants.SPECIES_INDEX, Constants.SPECIES_TYPE, JSONHelper.toJSON(params));
    }

}




