package com.ovit.nswy.wiki.search.service.impl;


import com.alibaba.fastjson.JSONArray;
import com.github.xphsc.collect.Maps;
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
import com.ovit.nswy.wiki.model.request.SpeciesDiseaseListQuery;
import com.ovit.nswy.wiki.model.request.SpeciesDiseaseQuery;
import com.ovit.nswy.wiki.search.service.SpeciesDiseaseService;
import com.ovit.nswy.wiki.search.service.SpeciesWikiService;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by ${huipei.x} on 2017-11-1.
 */
@Service
public class SpeciesDiseaseServiceImpl implements SpeciesDiseaseService {

    @Autowired
    private BaseEsearchDao baseEsearchDao;

    @Autowired
    private SpeciesWikiService speciesWikiService;

    @Override
    public Page<Map<String, Object>> listSpeciesDisease(SpeciesDiseaseListQuery speciesDiseaseListQuery)  {
        ESQueryBuilderConstructor constructor =new ESQueryBuilderConstructor();
        constructor.setSize(speciesDiseaseListQuery.getPageSize());
        constructor.setFrom(speciesDiseaseListQuery.getPageNum());
        constructor = getPageQuery(constructor,speciesDiseaseListQuery);
        constructor.must(new ESQueryCondition().match("fisdelete", 0));
        constructor.setDesc("updatetime");
        //auditstatus为1时表示审核通过
        if (Integers.isNotEmpty(speciesDiseaseListQuery.getAuditstatus())) {
            if (speciesDiseaseListQuery.getAuditstatus() == 6) {
                //查询全部的物种数据,排除审核状态完成的数据，即auditstatus为5
                constructor.mustNot(new ESQueryCondition().term("auditstatus", 5));
            } else {
                constructor.must(new ESQueryCondition().match("auditstatus", speciesDiseaseListQuery.getAuditstatus()));
            }
        } else {
            //只查询审核通过的物种数据
            constructor.must(new ESQueryCondition().match("auditstatus", 1));
        }
        Page<Map<String, Object>> mapPage= baseEsearchDao.search(Constants.IndexConstant.SPECIESDISEASE_INDEX, Constants.IndexConstant.SPECIESDISEASE_TYPE, constructor);
        mapPage.getRecordList().forEach(map->{
                    getimage(map);
                }
        );
        if (Integers.isNotEmpty(speciesDiseaseListQuery.getAuditstatus())) {
            speciesWikiService.dealAuditEmptyData(mapPage, Constants.IndexConstant.SPECIESDISEASE_INDEX, Constants.IndexConstant.SPECIESDISEASE_TYPE);
        }
        return mapPage;
    }

    @Override
    public List<Map<String, Object>> findByDiseaseName(SpeciesDiseaseListQuery speciesDiseaseListQuery)  {
        ESQueryBuilderConstructor constructor =new ESQueryBuilderConstructor();
        if(StringUtil.isNotBlank(speciesDiseaseListQuery.getFname())){
            constructor.must(new ESQueryCondition().match("fname",speciesDiseaseListQuery.getFname()));
        }
        if(StringUtil.isNotBlank(speciesDiseaseListQuery.getIndexid())){
            constructor.must(new ESQueryCondition().match("indexid",speciesDiseaseListQuery.getIndexid()));
        }
        if(StringUtil.isNotBlank(speciesDiseaseListQuery.getSpeciesId())){
            constructor.must(new ESQueryCondition().match("speciesid",speciesDiseaseListQuery.getSpeciesId()));
        }
        if(StringUtil.isNotBlank(speciesDiseaseListQuery.getFcreatorid())){
            constructor.must(new ESQueryCondition().match("fcreatorid",speciesDiseaseListQuery.getFcreatorid()));
        }
        //auditstatus为1时表示审核通过
        if (Integers.isNotEmpty(speciesDiseaseListQuery.getAuditstatus())) {
            //只查询审核通过的物种数据
            constructor.must(new ESQueryCondition().match("auditstatus", speciesDiseaseListQuery.getAuditstatus()));
        }
        List<Map<String, Object>> mapList= baseEsearchDao.searchList(Constants.IndexConstant.SPECIESDISEASE_INDEX, Constants.IndexConstant.SPECIESDISEASE_TYPE, constructor);

        return mapList;
    }

    @Override
    public List<Map<String, Object>> getDiseaseList(SpeciesDiseaseListQuery speciesDiseaseListQuery) {
        ESQueryBuilderConstructor constructor = new ESQueryBuilderConstructor();
        constructor = getPageQuery(constructor,speciesDiseaseListQuery);
        constructor.must(new ESQueryCondition().match("fisdelete", 0));
        //auditstatus为1时表示审核通过
        constructor.must(new ESQueryCondition().match("auditstatus", 1));

        List<Map<String, Object>> diseaseList  = baseEsearchDao.searchList(Constants.IndexConstant.SPECIESDISEASE_INDEX, Constants.IndexConstant.SPECIESDISEASE_TYPE, constructor);


        for(Map<String, Object> map:diseaseList){
            map.put("id",MapUtils.getString(map,"fid"));
            if(StringUtil.isNotBlank(MapUtils.getString(map,"parentid"))){
                map.put("pid",MapUtils.getString(map,"parentid"));
            }else{
                map.put("pid","0");
            }


        }

        return diseaseList;
    }

    @Override
    public Object saveSpeciesDisease(SpeciesDiseaseQuery speciesDiseaseQuery){
        if (StringUtils.isBlank(speciesDiseaseQuery.getFid())) {
            speciesDiseaseQuery.setFid(UUID.randomUUID().toString());
        }
        speciesDiseaseQuery.setFcreatetime(new Date());
        speciesDiseaseQuery.setFdeletetime(new Date());
        speciesDiseaseQuery.setUpdatetime(System.currentTimeMillis());
        speciesDiseaseQuery.setFisdelete(0);
        Object result= baseEsearchDao.createIndex(Constants.IndexConstant.SPECIESDISEASE_INDEX, Constants.IndexConstant.SPECIESDISEASE_TYPE, JSONHelper.toJSON(speciesDiseaseQuery));
        return result;
    }


    @Override
    public Map<String, Object> getSpeciesDisease(String indexid){
        Map<String,Object> map = baseEsearchDao.getIndexOfMap(Constants.IndexConstant.SPECIESDISEASE_INDEX, Constants.IndexConstant.SPECIESDISEASE_TYPE, indexid);
        getimage(map);
        if (MapUtils.getInteger(map, "auditstatus") != 1) {
            //若auditstatus不为1，则为查询待审核数据,再判断是否存在名称或者拼音为空的情况
            if (StringUtils.isBlank(MapUtils.getString(map, "fname")) || StringUtils.isBlank(MapUtils.getString(map, "fpinyin"))) {
                Page<Map<String, Object>> originPage = null;
                originPage = speciesWikiService.getOriginInfo(map, Constants.IndexConstant.SPECIESDISEASE_INDEX, Constants.IndexConstant.SPECIESDISEASE_TYPE);
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
    public void deleteSpeciesDisease(String indexid){
        baseEsearchDao.deleteIndex(Constants.IndexConstant.SPECIESDISEASE_INDEX, Constants.IndexConstant.SPECIESDISEASE_TYPE,indexid);
    }

    @Override
    public void updateSpeciesDisease(SpeciesDiseaseQuery speciesDiseaseQuery){
        Map<String, Object> result = Maps.newHashMap();
        if(StringUtil.isNotBlank(speciesDiseaseQuery.getIndexid())){
            result=update(speciesDiseaseQuery);
            baseEsearchDao.update(Constants.IndexConstant.SPECIESDISEASE_INDEX, Constants.IndexConstant.SPECIESDISEASE_TYPE,speciesDiseaseQuery.getIndexid(),JSONHelper.toJSON(result));
        }
    }

    @Override
    public Page<Map<String, Object>> findDiseaseInfo(ESQueryBuilderConstructor constructor) {

        Page<Map<String, Object>> mapPage= baseEsearchDao.search(Constants.IndexConstant.SPECIESDISEASE_INDEX, Constants.IndexConstant.SPECIESDISEASE_TYPE, constructor);
        mapPage.getRecordList().forEach(map->{
                    getimage(map);
                }
        );
        return mapPage;
    }

    /**
     * 更新待审核通过之后的操作
     * @param auditDiseaseInfo
     */
    @Override
    public void updateDiseasePassAudit(Map<String, Object> auditDiseaseInfo) {
        SpeciesDiseaseQuery speciesDiseaseQuery = new SpeciesDiseaseQuery();
        mapToSpeciesDiseaseQuery(speciesDiseaseQuery, auditDiseaseInfo);
        updateSpeciesDisease(speciesDiseaseQuery);

    }

    @Override
    public SpeciesDiseaseQuery mapToSpeciesDiseaseQuery(SpeciesDiseaseQuery speciesDiseaseQuery, Map<String, Object> auditDiseaseInfo) {
        if (StringUtils.isNotBlank(MapUtils.getString(auditDiseaseInfo,"indexid"))) {
            speciesDiseaseQuery.setIndexid(MapUtils.getString(auditDiseaseInfo,"indexid"));
        }
        if (StringUtils.isNotBlank(MapUtils.getString(auditDiseaseInfo,"fname"))) {
            speciesDiseaseQuery.setFname(MapUtils.getString(auditDiseaseInfo,"fname"));
        }
        if (StringUtils.isNotBlank(MapUtils.getString(auditDiseaseInfo,"fpinyin"))) {
            speciesDiseaseQuery.setFpinyin(MapUtils.getString(auditDiseaseInfo,"fpinyin"));
        }
        if (StringUtils.isNotBlank(MapUtils.getString(auditDiseaseInfo,"ffeature"))) {
            speciesDiseaseQuery.setFfeature(MapUtils.getString(auditDiseaseInfo,"ffeature"));
        }
        if (StringUtils.isNotBlank(MapUtils.getString(auditDiseaseInfo,"fdiseaseregular"))) {
            speciesDiseaseQuery.setFdiseaseregular(MapUtils.getString(auditDiseaseInfo,"fdiseaseregular"));
        }
        if (StringUtils.isNotBlank(MapUtils.getString(auditDiseaseInfo,"fprotectmethod"))) {
            speciesDiseaseQuery.setFprotectmethod(MapUtils.getString(auditDiseaseInfo,"fprotectmethod"));
        }
        if (StringUtils.isNotBlank(MapUtils.getString(auditDiseaseInfo,"fcommonfeature"))) {
            speciesDiseaseQuery.setFcommonfeature(MapUtils.getString(auditDiseaseInfo,"fcommonfeature"));
        }
        if (StringUtils.isNotBlank(MapUtils.getString(auditDiseaseInfo,"fcausediseasesubject"))) {
            speciesDiseaseQuery.setFcausediseasesubject(MapUtils.getString(auditDiseaseInfo,"fcausediseasesubject"));
        }
        if (StringUtils.isNotBlank(MapUtils.getString(auditDiseaseInfo,"fpathologycheck"))) {
            speciesDiseaseQuery.setFpathologycheck(MapUtils.getString(auditDiseaseInfo,"fpathologycheck"));
        }
        if (StringUtils.isNotBlank(MapUtils.getString(auditDiseaseInfo,"fdiagnose"))) {
            speciesDiseaseQuery.setFdiagnose(MapUtils.getString(auditDiseaseInfo,"fdiagnose"));
        }
        if (StringUtils.isNotBlank(MapUtils.getString(auditDiseaseInfo,"fprevention"))) {
            speciesDiseaseQuery.setFprevention(MapUtils.getString(auditDiseaseInfo,"fprevention"));
        }
        if (StringUtils.isNotBlank(MapUtils.getString(auditDiseaseInfo,"fimagesrc"))) {
            /*JSONArray images = (JSONArray) auditDiseaseInfo.get("fimagesrc");
            if (images.size() > 0) {
                speciesDiseaseQuery.setFimagesrc(images.toString());
            }*/
            speciesDiseaseQuery.setFimagesrc(MapUtils.getString(auditDiseaseInfo,"fimagesrc"));

        }
        return speciesDiseaseQuery;
    }


    private Map update(SpeciesDiseaseQuery speciesDiseaseQuery) {
        Map<String, Object> map= Maps.newHashMap();
        if (StringUtil.isNotBlank(speciesDiseaseQuery.getIndexid())) {
            map = baseEsearchDao.getIndexOfMap(Constants.IndexConstant.SPECIESDISEASE_INDEX, Constants.IndexConstant.SPECIESDISEASE_TYPE, speciesDiseaseQuery.getIndexid());
            map.put("updatetime", System.currentTimeMillis());
            if (StringUtil.isNotBlank(speciesDiseaseQuery.getFname())) {
                map.put("fname", speciesDiseaseQuery.getFname());
            }
            if (StringUtil.isNotBlank(speciesDiseaseQuery.getSpeciesid())) {
                map.put("speciesid", speciesDiseaseQuery.getSpeciesid());
            }
            if (StringUtil.isNotBlank(speciesDiseaseQuery.getFimagesrc())) {
                map.put("fimagesrc", speciesDiseaseQuery.getFimagesrc());
            }
            if (StringUtil.isNotBlank(speciesDiseaseQuery.getFpinyin())) {
                map.put("fpinyin", speciesDiseaseQuery.getFpinyin());
            }
            if (StringUtil.isNotBlank(speciesDiseaseQuery.getFenglishname())) {
                map.put("fenglishname", speciesDiseaseQuery.getFenglishname());
            }
            if (StringUtil.isNotBlank(speciesDiseaseQuery.getFdamagecropsid())) {
                map.put("fdamagecropsid", speciesDiseaseQuery.getFdamagecropsid());
            }
            if (ObjectUtil.isNotEmpty(speciesDiseaseQuery.getFispublic())) {
                map.put("fispublic", speciesDiseaseQuery.getFispublic());
            }
            if (StringUtil.isNotBlank(speciesDiseaseQuery.getFcreatorid())) {
                map.put("fcreatorid", speciesDiseaseQuery.getFcreatorid());
            }
            if (StringUtil.isNotBlank(speciesDiseaseQuery.getFupdatorid())) {
                map.put("fupdatorid", speciesDiseaseQuery.getFupdatorid());
            }
            if (StringUtil.isNotBlank(speciesDiseaseQuery.getFremarks())) {
                map.put("fremarks", speciesDiseaseQuery.getFremarks());
            }

            if (StringUtil.isNotBlank(speciesDiseaseQuery.getFfeature())) {
                map.put("ffeature", speciesDiseaseQuery.getFfeature());
            }

            if (StringUtil.isNotBlank(speciesDiseaseQuery.getFmainfeatures())) {
                map.put("fmainfeatures", speciesDiseaseQuery.getFmainfeatures());
            }

            if (StringUtil.isNotBlank(speciesDiseaseQuery.getFdiseaseregular())) {
                map.put("fdiseaseregular", speciesDiseaseQuery.getFdiseaseregular());
            }

            if (StringUtil.isNotBlank(speciesDiseaseQuery.getFprotectmethod())) {
                map.put("fprotectmethod", speciesDiseaseQuery.getFprotectmethod());
            }
            if (StringUtil.isNotBlank(speciesDiseaseQuery.getFcausediseasesubject())) {
                map.put("fcausediseasesubject", speciesDiseaseQuery.getFcausediseasesubject());
            }

            if (StringUtil.isNotBlank(speciesDiseaseQuery.getFcommonfeature())) {
                map.put("fcommonfeature", speciesDiseaseQuery.getFcommonfeature());
            }

            if (StringUtil.isNotBlank(speciesDiseaseQuery.getFpathologycheck())) {
                map.put("fpathologycheck", speciesDiseaseQuery.getFpathologycheck());
            }

            if (StringUtil.isNotBlank(speciesDiseaseQuery.getFdiagnose())) {
                map.put("fdiagnose", speciesDiseaseQuery.getFdiagnose());
            }

            if (StringUtil.isNotBlank(speciesDiseaseQuery.getFprevention())) {
                map.put("fprevention", speciesDiseaseQuery.getFprevention());
            }
            if (Integers.isNotEmpty(speciesDiseaseQuery.getFisdelete())) {
                map.put("fisdelete", speciesDiseaseQuery.getFisdelete());
            }
            if (Integers.isNotEmpty(speciesDiseaseQuery.getAuditstatus())) {
                map.put("auditstatus", speciesDiseaseQuery.getAuditstatus());
            }


        }
        return map;
    }


    private ESQueryBuilderConstructor getPageQuery(ESQueryBuilderConstructor constructor,SpeciesDiseaseListQuery speciesDiseaseListQuery){
        if(Integers.isNotEmpty(speciesDiseaseListQuery.getSortType())){
            if(speciesDiseaseListQuery.getSortType()==1){
                constructor.setAsc("updatetime");
            }
            if(speciesDiseaseListQuery.getSortType()==2){
                constructor.setDesc("updatetime");
            }
        }
        constructor.setDesc("updatetime");
        if(StringUtil.isNotBlank(speciesDiseaseListQuery.getUserId())){
            constructor.must(new ESQueryCondition().match("fcreatorid", speciesDiseaseListQuery.getUserId()));
        }

        if(StringUtil.isNotBlank(speciesDiseaseListQuery.getSpeciesId())){
            constructor.must(new ESQueryCondition().match("speciesid", speciesDiseaseListQuery.getSpeciesId()));
        }

        if(StringUtil.isNotBlank(speciesDiseaseListQuery.getFid())){
            constructor.must(new ESQueryCondition().match("fid", speciesDiseaseListQuery.getFid()));
        }

        if (StringUtil.isNotBlank(speciesDiseaseListQuery.getValidName())) {
            constructor.must(new ESQueryCondition().match("fname", speciesDiseaseListQuery.getValidName()));
        }

        if (StringUtil.isNotBlank(speciesDiseaseListQuery.getKeywords())) {
            if (RegexUtil.regChinese(speciesDiseaseListQuery.getKeywords()) || RegexUtil.isContainNumber(speciesDiseaseListQuery.getKeywords())) {
                constructor.must(new ESQueryCondition().like("fname", "*" + speciesDiseaseListQuery.getKeywords()+"*"));
            } else {
                constructor.must(new ESQueryCondition().like("fpinyin", speciesDiseaseListQuery.getKeywords().toLowerCase() + "*"));
            }
        }

      return constructor;
    }

    private Map getimage(Map<String, Object> map){
        if (!MapUtil.isEmpty(map, "fimagesrc")) {
            String image=String.valueOf(map.get("fimagesrc"));
            if (image.contains("[")) {
                map.put("fimagesrc", JSONHelper.parseArray(image));
            }
        }
        return map;
    }

}
