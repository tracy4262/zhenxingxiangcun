package com.ovit.nswy.wiki.search.service.impl;

import com.github.xphsc.collect.Lists;
import com.github.xphsc.json.JSONHelper;
import com.github.xphsc.util.StringUtil;
import com.google.common.collect.Maps;
import com.ovit.nswy.frame.common.Constants;
import com.ovit.nswy.wiki.common.MapUtil;
import com.ovit.nswy.wiki.common.enums.SpeciesclassEnums;
import com.ovit.nswy.wiki.common.search.ESQueryBuilderConstructor;
import com.ovit.nswy.wiki.common.search.ESQueryCondition;
import com.ovit.nswy.wiki.model.request.SpeciesClassListQuery;
import com.ovit.nswy.wiki.model.request.SpeciesQuery;
import com.ovit.nswy.wiki.search.dao.SpeciesclassifiedDao;
import com.ovit.nswy.wiki.search.service.SpeciesServce;
import com.ovit.nswy.wiki.search.service.SpeciesclassifiedService;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ${huipei.x} on 2017-10-25.
 */
@Service
public class SpeciesclassifiedServiceImpl implements SpeciesclassifiedService {

    @Autowired
    private SpeciesclassifiedDao speciesclassifiedDao;

    @Autowired
    private SpeciesServce speciesServce;

    @Override
    public List<Map<String, Object>> listSpeciesclassified(SpeciesClassListQuery speciesClassListQuery){
        List<Map<String, Object>> mapList= Lists.newArrayList();
        ESQueryBuilderConstructor constructor =new ESQueryBuilderConstructor();
        if(StringUtil.isNotBlank(speciesClassListQuery.getParentId())){
            constructor.must(new ESQueryCondition().match("parentId",speciesClassListQuery.getParentId()));
            mapList= speciesclassifiedDao.prepareSearch(Constants.IndexConstant.SPECIECLASS_INDEX, constructor);
        }
        if(StringUtil.isNotBlank(speciesClassListQuery.getClassId())){
            constructor.must(new ESQueryCondition().match("classId",speciesClassListQuery.getClassId()));
            mapList= speciesclassifiedDao.prepareSearch(Constants.IndexConstant.SPECIECLASS_INDEX, constructor);
        }
        return mapList;
    }

    @Override
    public  Object listAllSpeciesclass(){
        Map result= Maps.newHashMap();
        Map type= Maps.newHashMap();
        Map typeOne= Maps.newHashMap();
        List valueList=Lists.newArrayList();
        ESQueryBuilderConstructor constructorOne =new ESQueryBuilderConstructor();
        constructorOne.must(new ESQueryCondition().match("parentId",0));
        ESQueryBuilderConstructor constructorTwo =new ESQueryBuilderConstructor();
        constructorTwo.must(new ESQueryCondition().match("parentId",1));
        List<Map<String, Object>> mapList= speciesclassifiedDao.prepareSearch(Constants.IndexConstant.SPECIECLASS_INDEX, constructorOne);
        List<Map<String, Object>> mapOneList= speciesclassifiedDao.prepareSearch(Constants.IndexConstant.SPECIECLASS_INDEX, constructorTwo);
        type.put("id", SpeciesclassEnums.CLASS_ZERO.getCode());
        type.put("val",SpeciesclassEnums.CLASS_ZERO.getName());
        typeOne.put("id", SpeciesclassEnums.CLASS_ONE.getCode());
        typeOne.put("val",SpeciesclassEnums.CLASS_ONE.getName());
        valueList.add(type);
        valueList.add(typeOne);
        result.put("animalList",mapList);
        result.put("plantList",mapOneList);
        result.put("classType",valueList);
        return result;
    }


    @Override
    public  Object findSpeciesList(){
        Map result= Maps.newHashMap();
        List valueList=Lists.newArrayList();
        //查询物种
        ESQueryBuilderConstructor constructorOne =new ESQueryBuilderConstructor();


        List<Map<String, Object>> mapList= speciesclassifiedDao.prepareSearch(Constants.IndexConstant.SPECIECLASS_INDEX, constructorOne);

        //根据动物下的类别去查询相应类别下已审核的物种
        for(Map<String, Object> map:mapList){

            if(MapUtils.getString(map,"parentId").equals("")){
                if(MapUtils.getString(map,"classId").equals("0")){
                    map.put("id","2");
                }else{
                    map.put("id",MapUtils.getString(map,"classId"));
                }
                map.put("pid","0");
                map.put("fcode","");
                map.put("classLevel","1");


            }else if(MapUtils.getString(map,"parentId").equals("0")){
                map.put("classLevel","2");
                map.put("id",MapUtils.getString(map,"classId"));
                map.put("pid","2");
                map.put("fcode","");

            }else if(MapUtils.getString(map,"parentId").equals("1") ){
                map.put("classLevel","2");
                map.put("id",MapUtils.getString(map,"classId"));
                map.put("pid",MapUtils.getString(map,"parentId"));
                map.put("fcode","");

            }
            map.put("remark","");

            valueList.add(map);

            String fclassifiedid =  MapUtils.getString(map,"classId");

            SpeciesQuery speciesQuery = new SpeciesQuery();
            speciesQuery.setFclassifiedid(fclassifiedid);
            List<Map<String,Object>> speciesList = speciesServce.findListSpecies(speciesQuery);
            for(Map<String,Object> species:speciesList){


                species.put("id",MapUtils.getString(species,"speciesid"));
                species.put("fcode",MapUtils.getString(species,"fcode"));
                species.put("className",MapUtils.getString(species,"fname"));
                species.put("pid",MapUtils.getString(map,"classId"));
                species.put("classLevel","3");
                species.put("remark",StringUtil.isBlank(MapUtils.getString(species,"fshapefeatureid")) ? "" : MapUtils.getString(species,"fshapefeatureid"));

                valueList.add(species);
            }

        }

        result.put("list",valueList);
        return result;
    }

    /**
     * 批量更新物种分类 classLevel
     */
    public void updateClassLevel(){
        //查询物种
        ESQueryBuilderConstructor constructorOne =new ESQueryBuilderConstructor();


        List<Map<String, Object>> mapList= speciesclassifiedDao.prepareSearch(Constants.IndexConstant.SPECIECLASS_INDEX, constructorOne);

        for(Map<String, Object> map : mapList){

        }

    }


    @Override
    public void saveSpeciesclassified(Map params){
        speciesclassifiedDao.createIndex(Constants.IndexConstant.SPECIECLASS_INDEX, Constants.IndexConstant.SPECICLASS_TYPE,params.toString());
    }

    @Override
    public void insertSpeciesclass(Map params){
        speciesclassifiedDao.createIndex(Constants.IndexConstant.SPECIECLASS_INDEX, Constants.IndexConstant.SPECICLASS_TYPE, JSONHelper.toJSON(params));
    }

    @Override
    public Map<String, Object> getSpeciesclassified(String indexid){
        return speciesclassifiedDao.getIndexOfMap(Constants.IndexConstant.SPECIECLASS_INDEX, Constants.IndexConstant.SPECICLASS_TYPE, indexid);
    }

    @Override
    public void deleteSpeciesclassified(String indexid){
        speciesclassifiedDao.deleteIndex(Constants.IndexConstant.SPECIECLASS_INDEX, Constants.IndexConstant.SPECICLASS_TYPE, indexid);
    }
}
