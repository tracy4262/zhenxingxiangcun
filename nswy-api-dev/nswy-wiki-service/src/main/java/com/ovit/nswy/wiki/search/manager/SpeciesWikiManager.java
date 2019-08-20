package com.ovit.nswy.wiki.search.manager;

import com.github.xphsc.collect.Lists;
import com.github.xphsc.collect.Maps;
import com.github.xphsc.json.JSONHelper;
import com.github.xphsc.mutable.Integers;
import com.github.xphsc.page.Page;
import com.github.xphsc.util.StringUtil;
import com.ovit.nswy.frame.common.Constants;
import com.ovit.nswy.wiki.base.BaseEsearchDao;
import com.ovit.nswy.wiki.common.MapUtil;
import com.ovit.nswy.wiki.common.enums.FindustriaclassifiedeEunm;
import com.ovit.nswy.wiki.common.enums.IsprotectionEnum;
import com.ovit.nswy.wiki.common.search.ESQueryBuilderConstructor;
import com.ovit.nswy.wiki.common.search.ESQueryCondition;
import com.ovit.nswy.wiki.model.request.*;
import com.ovit.nswy.wiki.search.service.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;

/**
 * Created by ${huipei.x} on 2017-11-10.
 */
@Service
@Slf4j
public class SpeciesWikiManager {

    @Autowired
    private SpeciesVarieteyService speciesVarieteyService;
    @Autowired
    private BaseEsearchDao baseEsearchDao;
    @Autowired
    private SpeciesDiseaseService speciesDiseaseService;
    @Autowired
    private SpeciesPestService speciesPestService;
    @Autowired
    private SpeciesWikiService speciesWikiService;
    @Autowired
    private SpeciesServce speciesServce;
    @Autowired
    private  SpeciesclassifiedService speciesclassifiedService;

    public Object searchSpecies(String name){
        Map result= Maps.newHashMap();
        if(baseEsearchDao.isExistName(Constants.SPECIES_INDEX,"fname",name).equals(1)){
            ESQueryBuilderConstructor constructor =new ESQueryBuilderConstructor();
                constructor.must(new ESQueryCondition().term("fname", name));
            List<Map<String, Object>> mapList=baseEsearchDao.prepareSearch(Constants.SPECIES_INDEX, constructor);
            if(Lists.isNotEmpty(mapList)){
                if(mapList.get(0).get("speciesid") !=null){
                    SpeciesVarieteyListQuery speciesVarieteyListQuery=new SpeciesVarieteyListQuery();
                    speciesVarieteyListQuery.setSpeciesId(mapList.get(0).get("speciesid").toString());
                    Page<Map<String, Object>> page=speciesVarieteyService.listSpeciesVarietey(speciesVarieteyListQuery);
                    SpeciesDiseaseListQuery speciesDiseaseListQuery=new SpeciesDiseaseListQuery();
                    speciesDiseaseListQuery.setSpeciesId(mapList.get(0).get("speciesid").toString());
                    Page<Map<String, Object>> pageOne= speciesDiseaseService.listSpeciesDisease(speciesDiseaseListQuery);
                    SpeciesPestListQuery speciesPestListQuery=new SpeciesPestListQuery();
                    speciesPestListQuery.setSpeciesId(mapList.get(0).get("speciesid").toString());
                    Page<Map<String, Object>> pageTwo= speciesPestService.listSpeciesPest(speciesPestListQuery);
                    SpeciesCatalogQuery speciesCatalogQuery=new SpeciesCatalogQuery();
                    speciesCatalogQuery.setSpecies_id(mapList.get(0).get("speciesid").toString());
                    List<Map<String, Object>> listCatalog=speciesWikiService.listCatalog(speciesCatalogQuery);
                    result.put("species",mapList.get(0) );
                    result.put("varieteyList",page.getRecordList());
                    result.put("diseaseList",pageOne.getRecordList());
                    result.put("pestList",pageTwo.getRecordList());
                    result.put("catalogList",listCatalog);
                }

             }
            }


        return result;
    }

    public Object searchSpecies(SpeciesDetailQuery speciesDetailQuery){
        Map result= Maps.newHashMap();
        if(StringUtil.isNotBlank(speciesDetailQuery.getIndexid())){
            Map stringObjectMap=speciesServce.getSpecies(speciesDetailQuery.getIndexid());
            getSpeciesEscape(stringObjectMap);
            SpeciesWikiQuery speciesWikiQuery = new SpeciesWikiQuery();
            speciesWikiQuery.setSpecies_id(Maps.getString(stringObjectMap, "speciesid"));
            Integer rows = speciesWikiService.initCatalog(speciesWikiQuery);
            SpeciesVarieteyListQuery speciesVarieteyListQuery=new SpeciesVarieteyListQuery();
            speciesVarieteyListQuery.setSpeciesId(Maps.getString(stringObjectMap, "speciesid"));
            Page<Map<String, Object>> speciesVarietePage=speciesVarieteyService.listSpeciesVarietey(speciesVarieteyListQuery);
            speciesVarietePage=getVarietey(speciesVarietePage);
            SpeciesDiseaseListQuery speciesDiseaseListQuery=new SpeciesDiseaseListQuery();
            speciesDiseaseListQuery.setSpeciesId(Maps.getString(stringObjectMap, "speciesid"));
            Page<Map<String, Object>> speciesDiseasePage= speciesDiseaseService.listSpeciesDisease(speciesDiseaseListQuery);
            SpeciesPestListQuery speciesPestListQuery=new SpeciesPestListQuery();
            speciesPestListQuery.setSortType(2);
            speciesPestListQuery.setSpeciesId(Maps.getString(stringObjectMap, "speciesid"));
            Page<Map<String, Object>> speciesPestPage= speciesPestService.listSpeciesPest(speciesPestListQuery);
            SpeciesCatalogQuery speciesCatalogQuery=new SpeciesCatalogQuery();
            speciesCatalogQuery.setSpecies_id(Maps.getString(stringObjectMap, "speciesid"));
            List<Map<String, Object>> listCatalog=speciesWikiService.listCatalog(speciesCatalogQuery);
            result.put("species",stringObjectMap );
            result.put("varieteyList",speciesVarietePage.getRecordList());
            result.put("diseaseList",speciesDiseasePage.getRecordList());
            result.put("pestList",speciesPestPage.getRecordList());
            result.put("catalogList",listCatalog);
        }else {
            if(StringUtil.isNotBlank(speciesDetailQuery.getName())){
            if (baseEsearchDao.isExistName(Constants.SPECIES_INDEX, "fname", speciesDetailQuery.getName()).equals(1)) {
                ESQueryBuilderConstructor constructor = new ESQueryBuilderConstructor();
                constructor.must(new ESQueryCondition().term("fname", speciesDetailQuery.getName()));
                List<Map<String, Object>> mapList = baseEsearchDao.prepareSearch(Constants.SPECIES_INDEX, constructor);
                if (Lists.isNotEmpty(mapList)) {
                    Map<String, Object> valueEscape= mapList.get(0);
                    getSpeciesEscape(valueEscape);
                    Map value=mapList.get(0);
                    if (value.get("speciesid") != null) {
                        SpeciesVarieteyListQuery speciesVarieteyListQuery = new SpeciesVarieteyListQuery();
                        speciesVarieteyListQuery.setSpeciesId(Maps.getString(value, "speciesid"));
                        Page<Map<String, Object>> page = speciesVarieteyService.listSpeciesVarietey(speciesVarieteyListQuery);
                        page=getVarietey(page);
                        SpeciesDiseaseListQuery speciesDiseaseListQuery = new SpeciesDiseaseListQuery();
                        speciesDiseaseListQuery.setSpeciesId(Maps.getString(value, "speciesid"));
                        Page<Map<String, Object>> pageOne = speciesDiseaseService.listSpeciesDisease(speciesDiseaseListQuery);
                        SpeciesPestListQuery speciesPestListQuery = new SpeciesPestListQuery();
                        speciesPestListQuery.setSpeciesId(Maps.getString(value, "speciesid"));
                        Page<Map<String, Object>> pageTwo = speciesPestService.listSpeciesPest(speciesPestListQuery);
                        SpeciesCatalogQuery speciesCatalogQuery = new SpeciesCatalogQuery();
                        speciesCatalogQuery.setSpecies_id(Maps.getString(value, "speciesid"));
                        List<Map<String, Object>> listCatalog = speciesWikiService.listCatalog(speciesCatalogQuery);
                        result.put("species", mapList.get(0));
                        result.put("varieteyList", page.getRecordList());
                        result.put("diseaseList", pageOne.getRecordList());
                        result.put("pestList", pageTwo.getRecordList());
                        result.put("catalogList", listCatalog);
                    }

                }

            }
         }
        }

        return result;
    }



    public  Object existName(Integer type ,String name) {
        Object result = null;
        if (Integers.isNotEmpty(type)) {
            switch (type) {
                case 1:
                    result = baseEsearchDao.isExistName(Constants.SPECIES_INDEX, "fname", name);
                    break;
                case 2:
                    result = baseEsearchDao.isExistName(Constants.IndexConstant.SPECIESVARIETEY_INDEX, "fname", name);
                    break;
                case 3:
                    result = baseEsearchDao.isExistName(Constants.IndexConstant.SPECIESDISEASE_INDEX, "fname", name);
                    break;
                case 4:
                    result = baseEsearchDao.isExistName(Constants.IndexConstant.SPECIESPEST_INDEX, "fname", name);
                    break;
                    default:

            }
        }
        return result;
    }

private String  getSpeciesClass(Map params,String value){
    String result=null;
    SpeciesClassListQuery speciesClassListQuery=new SpeciesClassListQuery();
    speciesClassListQuery.setClassId(Maps.getString(params, value));
    List<Map<String, Object>> rows= speciesclassifiedService.listSpeciesclassified(speciesClassListQuery);
    if(Lists.isNotEmpty(rows)){
        SpeciesClassListQuery speciesClassQuery=new SpeciesClassListQuery();
        speciesClassQuery.setClassId(String.valueOf(rows.get(0).get("parentId")));
        List<Map<String, Object>> list= speciesclassifiedService.listSpeciesclassified(speciesClassQuery);
        if(Lists.isNotEmpty(list)){
            String name= (String) list.get(0).get("className");
            result= String.valueOf(name + "/" + rows.get(0).get("className"));
        }else{
            result= String.valueOf(rows.get(0).get("className"));
        }

    }
   return result;
 }

    public Map<String, Object>  getSpeciesEscape(Map<String, Object> stringObjectMap){
        Map findustriaclassifiedidInfo= Maps.newHashMap();
        Map fclassifiedidInfo= Maps.newHashMap();
        Map fisprotectionInfo= Maps.newHashMap();
        findustriaclassifiedidInfo.put("id",stringObjectMap.get("findustriaclassifiedid"));
        findustriaclassifiedidInfo.put("val", FindustriaclassifiedeEunm.findustriaclassifiedeTypeEscape(stringObjectMap.get("findustriaclassifiedid").toString()));
        stringObjectMap.put("findustriaclassifiedidInfo", findustriaclassifiedidInfo);
        String value= getSpeciesClass(stringObjectMap,"fclassifiedid");
        fclassifiedidInfo.put("id",stringObjectMap.get("fclassifiedid"));
        fclassifiedidInfo.put("val", value);
        String fouthclassifiedidvalue= getSpeciesClass(stringObjectMap,"fouthclassifiedid");
        stringObjectMap.put("fouthclassifiedid",fouthclassifiedidvalue);
        stringObjectMap.put("fclassifiedidInfo", fclassifiedidInfo);
        if(stringObjectMap.get("fisprotection") !=null&&!stringObjectMap.get("fisprotection").equals("")){
            fisprotectionInfo.put("id",stringObjectMap.get("fisprotection"));
            fisprotectionInfo.put("val", IsprotectionEnum.IsprotectionEscape(Integer.valueOf(stringObjectMap.get("fisprotection").toString())));
        }
        stringObjectMap.put("fisprotectionInfo", fisprotectionInfo);
        //其他分类数据处理
        if (StringUtils.isNotBlank(MapUtils.getString(stringObjectMap, "otherClassify"))) {
            Map otherClassifyInfo = Maps.newHashMap();
            String otherValue = getSpeciesClass(stringObjectMap,"otherClassify");
            otherClassifyInfo.put("id",stringObjectMap.get("otherClassify"));
            otherClassifyInfo.put("val", otherValue);
            stringObjectMap.put("otherClassifyInfo", otherClassifyInfo);
        }

        return stringObjectMap;
    }

    private  Page<Map<String, Object>>  getVarietey(Page<Map<String, Object>> page){
        for(Map map :page.getRecordList()){
            if(!MapUtil.isEmpty(map, "fistransgene")) {
                String fistransgene=  Maps.getString(map, "fistransgene");
                if (StringUtil.isNotBlank(fistransgene)) {
                    if (fistransgene.equals("0")) {
                        map.put("fistransgene", "否");
                    }
                    if (fistransgene.equals("1")) {
                        map.put("fistransgene", "是");
                    }
                }
            }
        }
        return page;
    }

}
