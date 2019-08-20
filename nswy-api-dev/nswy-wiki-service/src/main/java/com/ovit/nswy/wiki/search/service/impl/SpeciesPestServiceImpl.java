package com.ovit.nswy.wiki.search.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.github.xphsc.collect.Lists;
import com.github.xphsc.collect.Maps;
import com.github.xphsc.date.DateFormat;
import com.github.xphsc.date.DateTimeUtil;
import com.github.xphsc.json.JSONHelper;
import com.github.xphsc.mutable.Integers;
import com.github.xphsc.page.Page;
import com.github.xphsc.util.ObjectUtil;
import com.github.xphsc.util.StringUtil;
import com.ovit.nswy.frame.common.Constants;
import com.ovit.nswy.frame.common.util.RegexUtil;
import com.ovit.nswy.wiki.base.BaseEsearchDao;
import com.ovit.nswy.wiki.common.MapUtil;
import com.ovit.nswy.wiki.common.search.ESQueryBuilderConstructor;
import com.ovit.nswy.wiki.common.search.ESQueryCondition;
import com.ovit.nswy.wiki.model.request.SpeciesPestListQuery;
import com.ovit.nswy.wiki.model.request.SpeciesPestQuery;
import com.ovit.nswy.wiki.search.service.SpeciesPestService;
import com.ovit.nswy.wiki.search.service.SpeciesWikiService;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.index.IndexResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by ${huipei.x} on 2017-11-1.
 */

@Service
public class SpeciesPestServiceImpl implements SpeciesPestService {


    @Autowired
    private BaseEsearchDao baseEsearchDao;

    @Autowired
    private SpeciesWikiService speciesWikiService;

    @Override
    public Page<Map<String, Object>> listSpeciesPest(SpeciesPestListQuery speciesPestListQuery)  {
        ESQueryBuilderConstructor constructor =new ESQueryBuilderConstructor();
        constructor.setSize(speciesPestListQuery.getPageSize());
        constructor.setFrom(speciesPestListQuery.getPageNum());
        constructor = getPageQuery(constructor,speciesPestListQuery);
        constructor.must(new ESQueryCondition().match("fisdelete", 0));
        constructor.setDesc("updatetime");
        //auditstatus为1时表示审核通过
        if (Integers.isNotEmpty(speciesPestListQuery.getAuditstatus())) {
            if (speciesPestListQuery.getAuditstatus() == 6) {
                //查询全部的物种数据,排除审核状态完成的数据，即auditstatus为5
                constructor.mustNot(new ESQueryCondition().term("auditstatus", 5));
            } else {
                constructor.must(new ESQueryCondition().match("auditstatus", speciesPestListQuery.getAuditstatus()));
            }
        } else {
            //只查询审核通过的物种数据
            constructor.must(new ESQueryCondition().match("auditstatus", 1));
        }
        Page<Map<String, Object>> mapPage= baseEsearchDao.search(Constants.IndexConstant.SPECIESPEST_INDEX, Constants.IndexConstant.SPECIESPEST_TYPE, constructor);
        mapPage.getRecordList().forEach(map->{
                    getFimage(map);
                }
        );
        if (Integers.isNotEmpty(speciesPestListQuery.getAuditstatus())) {
            speciesWikiService.dealAuditEmptyData(mapPage, Constants.IndexConstant.SPECIESPEST_INDEX, Constants.IndexConstant.SPECIESPEST_TYPE);
        }
        return mapPage;
    }

    @Override
    public List<Map<String, Object>> findByPestName(SpeciesPestListQuery speciesPestListQuery)  {
        ESQueryBuilderConstructor constructor =new ESQueryBuilderConstructor();
        if(StringUtil.isNotBlank(speciesPestListQuery.getFname())){
            constructor.must(new ESQueryCondition().match("fname",speciesPestListQuery.getFname()));
        }
        if(StringUtil.isNotBlank(speciesPestListQuery.getIndexid())){
            constructor.must(new ESQueryCondition().match("indexid",speciesPestListQuery.getIndexid()));
        }
        if(StringUtil.isNotBlank(speciesPestListQuery.getSpeciesId())){
            constructor.must(new ESQueryCondition().match("speciesid",speciesPestListQuery.getSpeciesId()));
        }


        //auditstatus为1时表示审核通过
        if (Integers.isNotEmpty(speciesPestListQuery.getAuditstatus())) {
            //只查询审核通过的物种数据
            constructor.must(new ESQueryCondition().match("auditstatus", speciesPestListQuery.getAuditstatus()));
        }
        List<Map<String, Object>> mapPage= baseEsearchDao.searchList(Constants.IndexConstant.SPECIESPEST_INDEX, Constants.IndexConstant.SPECIESPEST_TYPE, constructor);

        return mapPage;
    }

    @Override
    public List<Map<String, Object>> getPestList(SpeciesPestListQuery speciesPestListQuery) {
        ESQueryBuilderConstructor constructor = new ESQueryBuilderConstructor();
        constructor = getPageQuery(constructor,speciesPestListQuery);
        constructor.must(new ESQueryCondition().match("fisdelete", 0));
        //auditstatus为1时表示审核通过
        constructor.must(new ESQueryCondition().match("auditstatus", 1));
        List<Map<String, Object>> pestList  = baseEsearchDao.searchList(Constants.IndexConstant.SPECIESPEST_INDEX, Constants.IndexConstant.SPECIESPEST_TYPE, constructor);

        for(Map<String, Object> map:pestList){
            map.put("id",MapUtils.getString(map,"fid"));
            if(StringUtil.isNotBlank(MapUtils.getString(map,"parentid"))){
                map.put("pid",MapUtils.getString(map,"parentid"));
            }else{
                map.put("pid","0");
            }
        }
        return pestList;
    }

    @Override
    public Object saveSpeciesPest(SpeciesPestQuery speciesPestQuery){
        if (StringUtils.isBlank(speciesPestQuery.getFid())) {
            speciesPestQuery.setFid(UUID.randomUUID().toString());
        }
        speciesPestQuery.setFcreatetime(new Date());
        speciesPestQuery.setFdeletetime(new Date());
        speciesPestQuery.setUpdatetime(System.currentTimeMillis());
        speciesPestQuery.setFisdelete(0);
        Object result= baseEsearchDao.createIndex(Constants.IndexConstant.SPECIESPEST_INDEX, Constants.IndexConstant.SPECIESPEST_TYPE, JSONHelper.toJSON(speciesPestQuery));
        return result;
    }

    @Override
    public Map<String, Object> getSpeciesPest(String indexid){
        Map<String,Object> map= baseEsearchDao.getIndexOfMap(Constants.IndexConstant.SPECIESPEST_INDEX, Constants.IndexConstant.SPECIESPEST_TYPE, indexid);
        getFimage(map);
        if (MapUtils.getInteger(map, "auditstatus") != 1) {
            //若auditstatus不为1，则为查询待审核数据,再判断是否存在名称或者拼音为空的情况
            if (StringUtils.isBlank(MapUtils.getString(map, "fname")) || StringUtils.isBlank(MapUtils.getString(map, "fpinyin"))) {
                Page<Map<String, Object>> originPage = null;
                originPage = speciesWikiService.getOriginInfo(map, Constants.IndexConstant.SPECIESPEST_INDEX, Constants.IndexConstant.SPECIESPEST_TYPE);
                if (originPage.getRecordList().size() > 0) {
                    List<Map<String, Object>> originList = originPage.getRecordList();
                    Map<String, Object> originMap = originList.get(0);
                    map.put("fname", MapUtils.getString(originMap, "fname"));
                    map.put("fpinyin", MapUtils.getString(originMap, "fpinyin"));
                }
            }
        }
        return map;
    }


    @Override
    public void deleteSpeciesPest(String indexid){
        baseEsearchDao.deleteIndex(Constants.IndexConstant.SPECIESPEST_INDEX, Constants.IndexConstant.SPECIESPEST_TYPE,indexid);
    }

    @Override
    public void updateSpeciesPest(SpeciesPestQuery speciesPestQuery){
        Map<String, Object> result = Maps.newHashMap();
        if(StringUtil.isNotBlank(speciesPestQuery.getIndexid())){
            result= update(speciesPestQuery);
            baseEsearchDao.update(Constants.IndexConstant.SPECIESPEST_INDEX, Constants.IndexConstant.SPECIESPEST_TYPE, speciesPestQuery.getIndexid(),JSONHelper.toJSON(result));
        }
    }

    @Override
    public Page<Map<String, Object>> findPestInfo(ESQueryBuilderConstructor constructor) {

        Page<Map<String, Object>> mapPage= baseEsearchDao.search(Constants.IndexConstant.SPECIESPEST_INDEX, Constants.IndexConstant.SPECIESPEST_TYPE, constructor);
        mapPage.getRecordList().forEach(map->{
                    getFimage(map);
                }
        );
        return mapPage;
    }

    /**
     * 更新待审核通过之后的操作
     * @param auditPestInfo
     */
    @Override
    public void updatePestPassAudit(Map<String, Object> auditPestInfo) {
        SpeciesPestQuery speciesPestQuery = new SpeciesPestQuery();
        mapToSpeciesPestQuery(speciesPestQuery, auditPestInfo);
        updateSpeciesPest(speciesPestQuery);
    }

    @Override
    public SpeciesPestQuery mapToSpeciesPestQuery(SpeciesPestQuery speciesPestQuery, Map<String, Object> auditPestInfo) {
        if (StringUtils.isNotBlank(MapUtils.getString(auditPestInfo,"indexid"))) {
            speciesPestQuery.setIndexid(MapUtils.getString(auditPestInfo,"indexid"));
        }
        if (StringUtils.isNotBlank(MapUtils.getString(auditPestInfo,"fname"))) {
            speciesPestQuery.setFname(MapUtils.getString(auditPestInfo,"fname"));
        }
        if (StringUtils.isNotBlank(MapUtils.getString(auditPestInfo,"fpinyin"))) {
            speciesPestQuery.setFpinyin(MapUtils.getString(auditPestInfo,"fpinyin"));
        }
        if (StringUtils.isNotBlank(MapUtils.getString(auditPestInfo,"fmainfeatures"))) {
            speciesPestQuery.setFmainfeatures(MapUtils.getString(auditPestInfo,"fmainfeatures"));
        }
        if (StringUtils.isNotBlank(MapUtils.getString(auditPestInfo,"fhabit"))) {
            speciesPestQuery.setFhabit(MapUtils.getString(auditPestInfo,"fhabit"));
        }
        if (StringUtils.isNotBlank(MapUtils.getString(auditPestInfo,"fpetsregular"))) {
            speciesPestQuery.setFpetsregular(MapUtils.getString(auditPestInfo,"fpetsregular"));
        }
        if (StringUtils.isNotBlank(MapUtils.getString(auditPestInfo,"fprotectmethod"))) {
            speciesPestQuery.setFprotectmethod(MapUtils.getString(auditPestInfo,"fprotectmethod"));
        }
        if (StringUtils.isNotBlank(MapUtils.getString(auditPestInfo,"fremarks"))) {
            speciesPestQuery.setFremarks(MapUtils.getString(auditPestInfo,"fremarks"));
        }
        if (StringUtils.isNotBlank(MapUtils.getString(auditPestInfo,"fimagesrc"))) {
            /*JSONArray images = (JSONArray) auditPestInfo.get("fimagesrc");
            if (images.size() > 0) {
                speciesPestQuery.setFimagesrc(images.toString());
            }*/
            speciesPestQuery.setFimagesrc(MapUtils.getString(auditPestInfo,"fimagesrc"));

        }
        return speciesPestQuery;
    }

    private Map update(SpeciesPestQuery speciesPestQuery) {
        Map<String, Object> map= Maps.newHashMap();
        if (StringUtil.isNotBlank(speciesPestQuery.getIndexid())) {
            map = baseEsearchDao.getIndexOfMap(Constants.IndexConstant.SPECIESPEST_INDEX, Constants.IndexConstant.SPECIESPEST_TYPE, speciesPestQuery.getIndexid());
            map.put("updatetime", System.currentTimeMillis());
            if (StringUtil.isNotBlank(speciesPestQuery.getFname())) {
                map.put("fname", speciesPestQuery.getFname());
            }
            if (StringUtil.isNotBlank(speciesPestQuery.getSpeciesid())) {
                map.put("speciesid", speciesPestQuery.getSpeciesid());
            }
            if (StringUtil.isNotBlank(speciesPestQuery.getFimagesrc())) {
                map.put("fimagesrc", speciesPestQuery.getFimagesrc());
            }
            if (StringUtil.isNotBlank(speciesPestQuery.getFpinyin())) {
                map.put("fpinyin", speciesPestQuery.getFpinyin());
            }
            if (StringUtil.isNotBlank(speciesPestQuery.getFenglishname())) {
                map.put("fenglishname", speciesPestQuery.getFenglishname());
            }
            if (StringUtil.isNotBlank(speciesPestQuery.getFdamagecropsid())) {
                map.put("fdamagecropsid", speciesPestQuery.getFdamagecropsid());
            }
            if (ObjectUtil.isNotEmpty(speciesPestQuery.getFispublic())) {
                map.put("fispublic", speciesPestQuery.getFispublic());
            }
            if (StringUtil.isNotBlank(speciesPestQuery.getFcreatorid())) {
                map.put("fcreatorid", speciesPestQuery.getFcreatorid());
            }
            if (StringUtil.isNotBlank(speciesPestQuery.getFupdatorid())) {
                map.put("fupdatorid", speciesPestQuery.getFupdatorid());
            }
            if (StringUtil.isNotBlank(speciesPestQuery.getFremarks())) {
                map.put("fremarks", speciesPestQuery.getFremarks());
            }

            if (StringUtil.isNotBlank( speciesPestQuery.getFhabit())) {
                map.put("fhabit", speciesPestQuery.getFhabit());
            }

            if (StringUtil.isNotBlank(speciesPestQuery.getFmainfeatures())) {
                map.put("fmainfeatures", speciesPestQuery.getFmainfeatures());
            }

            if (StringUtil.isNotBlank(speciesPestQuery.getFpetsregular())) {
                map.put("fpetsregular", speciesPestQuery.getFpetsregular());
            }

            if (StringUtil.isNotBlank(speciesPestQuery.getFprotectmethod())) {
                map.put("fprotectmethod", speciesPestQuery.getFprotectmethod());
            }
            if (Integers.isNotEmpty(speciesPestQuery.getFisdelete())) {
                map.put("fisdelete", speciesPestQuery.getFisdelete());
            }
            if (Integers.isNotEmpty(speciesPestQuery.getAuditstatus())) {
                map.put("auditstatus", speciesPestQuery.getAuditstatus());
            }
        }
        return map;
    }


    private ESQueryBuilderConstructor getPageQuery(ESQueryBuilderConstructor constructor,SpeciesPestListQuery speciesPestListQuery) {
        if(Integers.isNotEmpty(speciesPestListQuery.getSortType())){
            if(speciesPestListQuery.getSortType()==1){
                constructor.setAsc("updatetime");
            }
            if(speciesPestListQuery.getSortType()==2){
                constructor.setDesc("updatetime");
            }
        }
        constructor.setDesc("updatetime");
        if(StringUtil.isNotBlank(speciesPestListQuery.getUserId())){
            constructor.must(new ESQueryCondition().match("fcreatorid", speciesPestListQuery.getUserId()));
        }

        if(StringUtil.isNotBlank(speciesPestListQuery.getSpeciesId())){
            constructor.must(new ESQueryCondition().match("speciesid", speciesPestListQuery.getSpeciesId()));
        }

        if(StringUtil.isNotBlank(speciesPestListQuery.getFid())){
            constructor.must(new ESQueryCondition().match("fid", speciesPestListQuery.getFid()));
        }

        if(StringUtil.isNotBlank(speciesPestListQuery.getValidName())){
            constructor.must(new ESQueryCondition().match("fname", speciesPestListQuery.getValidName()));
        }

        if (StringUtil.isNotBlank(speciesPestListQuery.getKeywords())) {
            if (RegexUtil.regChinese(speciesPestListQuery.getKeywords()) || RegexUtil.isContainNumber(speciesPestListQuery.getKeywords())) {
                constructor.must(new ESQueryCondition().like("fname","*" + speciesPestListQuery.getKeywords()+"*"));
            } else {
                constructor.must(new ESQueryCondition().like("fpinyin", speciesPestListQuery.getKeywords().toLowerCase() + "*"));
            }
        }
        return constructor;
    }

    private Map getFimage(Map<String, Object> map){
        if (!MapUtil.isEmpty(map, "fimagesrc")) {
            String fimage=String.valueOf(map.get("fimagesrc"));
            if (fimage.contains("[")) {
                map.put("fimagesrc", JSONHelper.parseArray(fimage));
            }
        }
        return map;
    }
}
