package com.ovit.nswy.wiki.search.service.impl;



import com.github.xphsc.collect.Maps;
import com.github.xphsc.date.DateTimeUtil;
import com.github.xphsc.date.DateUtil;
import com.github.xphsc.json.JSONHelper;
import com.github.xphsc.mutable.Integers;
import com.github.xphsc.page.Page;
import com.github.xphsc.util.ObjectUtil;
import com.github.xphsc.util.StringUtil;
import com.ovit.nswy.frame.common.Constants;
import com.ovit.nswy.frame.common.exception.ParamValidException;
import com.ovit.nswy.frame.common.util.RegexUtil;
import com.ovit.nswy.wiki.base.BaseEsearchDao;
import com.ovit.nswy.wiki.common.MapUtil;
import com.ovit.nswy.wiki.common.search.ESQueryBuilderConstructor;
import com.ovit.nswy.wiki.common.search.ESQueryCondition;
import com.ovit.nswy.wiki.model.request.SpeciesVarieteyListQuery;
import com.ovit.nswy.wiki.model.request.SpeciesVarieteyQuery;
import com.ovit.nswy.wiki.search.service.SpeciesVarieteyService;
import com.ovit.nswy.wiki.search.service.SpeciesWikiService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by ${huipei.x} on 2017-11-1.
 */
@Service
@Slf4j
public class SpeciesVarieteyServiceImpl implements SpeciesVarieteyService {

    @Autowired
    private BaseEsearchDao baseEsearchDao;

    @Autowired
    private SpeciesWikiService speciesWikiService;

    @Override
    public Page<Map<String, Object>> listSpeciesVarietey(SpeciesVarieteyListQuery speciesVarieteyListQuery)  {
        ESQueryBuilderConstructor constructor =new ESQueryBuilderConstructor();
        constructor.setSize(speciesVarieteyListQuery.getPageSize());
        constructor.setFrom(speciesVarieteyListQuery.getPageNum());
        constructor = getPageQuery(constructor,speciesVarieteyListQuery);
        constructor.must(new ESQueryCondition().match("fisdelete", 0));
        constructor.setDesc("updatetime");
        //auditstatus为1时表示审核通过
        if (Integers.isNotEmpty(speciesVarieteyListQuery.getAuditstatus())) {
            if (speciesVarieteyListQuery.getAuditstatus() == 6) {
                //查询全部的物种数据,排除审核状态完成的数据，即auditstatus为5
                constructor.mustNot(new ESQueryCondition().term("auditstatus", 5));
            } else {
                constructor.must(new ESQueryCondition().match("auditstatus", speciesVarieteyListQuery.getAuditstatus()));
            }
        } else {
            //只查询审核通过的物种数据
            constructor.must(new ESQueryCondition().match("auditstatus", 1));
        }
        Page<Map<String, Object>> mapPage= baseEsearchDao.search(Constants.IndexConstant.SPECIESVARIETEY_INDEX, Constants.IndexConstant.SPECIESVARIETEY_TYPE, constructor);
        mapPage.getRecordList().forEach(map->{
                    getimage(map);
                }
        );
        if (Integers.isNotEmpty(speciesVarieteyListQuery.getAuditstatus())) {
            speciesWikiService.dealAuditEmptyData(mapPage, Constants.IndexConstant.SPECIESVARIETEY_INDEX, Constants.IndexConstant.SPECIESVARIETEY_TYPE);
        }
        return mapPage;
    }

    @Override
    public List<Map<String, Object>> findByVarieteyName(SpeciesVarieteyListQuery speciesVarieteyListQuery)  {
        ESQueryBuilderConstructor constructor =new ESQueryBuilderConstructor();
        if(StringUtil.isNotBlank(speciesVarieteyListQuery.getFname())){
            constructor.must(new ESQueryCondition().match("fname",speciesVarieteyListQuery.getFname()));
        }
        if(StringUtil.isNotBlank(speciesVarieteyListQuery.getFid())){
            constructor.must(new ESQueryCondition().match("fid",speciesVarieteyListQuery.getFid()));
        }
        if(StringUtil.isNotBlank(speciesVarieteyListQuery.getSpeciesId())){
            constructor.must(new ESQueryCondition().match("speciesid",speciesVarieteyListQuery.getSpeciesId()));
        }
        if(StringUtil.isNotBlank(speciesVarieteyListQuery.getFpinyin())){
            constructor.must(new ESQueryCondition().match("fpinyin",speciesVarieteyListQuery.getFpinyin()));
        }
        if(StringUtil.isNotBlank(speciesVarieteyListQuery.getIndexid())){
            constructor.must(new ESQueryCondition().match("indexid",speciesVarieteyListQuery.getIndexid()));
        }
        //auditstatus为1时表示审核通过
        if (Integers.isNotEmpty(speciesVarieteyListQuery.getAuditstatus())) {
            //只查询审核通过的物种数据
            constructor.must(new ESQueryCondition().match("auditstatus", speciesVarieteyListQuery.getAuditstatus()));
        }
        List<Map<String,Object>> mapList = baseEsearchDao.searchList(Constants.IndexConstant.SPECIESVARIETEY_INDEX, Constants.IndexConstant.SPECIESVARIETEY_TYPE, constructor);
        return mapList;
    }

    @Override
    public Object saveSpeciesVarietey(SpeciesVarieteyQuery speciesVarieteyQuery) throws ParamValidException {
        /*if((isNameExist(speciesVarieteyQuery.getFname())).equals(1)){
            throw new ParamValidException("物种品种已存在");
        }*/
        if (StringUtils.isBlank(speciesVarieteyQuery.getFid())) {
            speciesVarieteyQuery.setFid(UUID.randomUUID().toString());
        }
        speciesVarieteyQuery.setUpdatetime(System.currentTimeMillis());
        speciesVarieteyQuery.setFisdelete(0);
        Object result=baseEsearchDao.createIndex(Constants.IndexConstant.SPECIESVARIETEY_INDEX, Constants.IndexConstant.SPECIESVARIETEY_TYPE, JSONHelper.toJSON(speciesVarieteyQuery));
        return result;
    }

    @Override
    public List<Map<String, Object>> getVarieteyList(SpeciesVarieteyListQuery speciesVarieteyListQuery) {
        ESQueryBuilderConstructor constructor = new ESQueryBuilderConstructor();
        constructor = getPageQuery(constructor,speciesVarieteyListQuery);
        constructor.must(new ESQueryCondition().match("fisdelete", 0));
        //auditstatus为1时表示审核通过
        constructor.must(new ESQueryCondition().match("auditstatus", 1));

        List<Map<String, Object>> varieteyList  = baseEsearchDao.searchList(Constants.IndexConstant.SPECIESVARIETEY_INDEX, Constants.IndexConstant.SPECIESVARIETEY_TYPE, constructor);


        for(Map<String, Object> map:varieteyList){
            map.put("id",MapUtils.getString(map,"fid"));
            if(StringUtil.isNotBlank(MapUtils.getString(map,"parentid"))){
                map.put("pid",MapUtils.getString(map,"parentid"));
            }else{
                map.put("pid","0");
            }


        }

        return varieteyList;
    }

    @Override
    public Map<String, Object> getSpeciesVarietey(String indexid){
        Map<String, Object> map= baseEsearchDao.getIndexOfMap(Constants.IndexConstant.SPECIESVARIETEY_INDEX, Constants.IndexConstant.SPECIESVARIETEY_TYPE, indexid);
        getimage(map);
        if (MapUtils.getInteger(map, "auditstatus") != 1) {
            //若auditstatus不为1，则为查询待审核数据,再判断是否存在名称或者拼音为空的情况
            if (StringUtils.isBlank(MapUtils.getString(map, "fname")) || StringUtils.isBlank(MapUtils.getString(map, "fpinyin"))) {
                Page<Map<String, Object>> originPage = null;
                originPage = speciesWikiService.getOriginInfo(map, Constants.IndexConstant.SPECIESVARIETEY_INDEX, Constants.IndexConstant.SPECIESVARIETEY_TYPE);
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
    public void deleteSpeciesVarietey(String indexid){
        baseEsearchDao.deleteIndex(Constants.IndexConstant.SPECIESVARIETEY_INDEX, Constants.IndexConstant.SPECIESVARIETEY_TYPE,indexid);
    }

    @Override
    public void updateSpeciesVarietey(SpeciesVarieteyQuery speciesVarieteyQuery){
        Map<String, Object> result = Maps.newHashMap();
        if(StringUtil.isNotBlank(speciesVarieteyQuery.getIndexid())){
            result = update(speciesVarieteyQuery);
            baseEsearchDao.update(Constants.IndexConstant.SPECIESVARIETEY_INDEX, Constants.IndexConstant.SPECIESVARIETEY_TYPE, speciesVarieteyQuery.getIndexid(), JSONHelper.toJSON(result));
        }
    }


    @Override
    public  Object isNameExist(String name){
        return  baseEsearchDao.isExistName(Constants.IndexConstant.SPECIESVARIETEY_INDEX,"fname",name);
    }

    @Override
    public Page<Map<String, Object>> findVarieteyInfo(ESQueryBuilderConstructor constructor) {

        Page<Map<String, Object>> mapPage= baseEsearchDao.search(Constants.IndexConstant.SPECIESVARIETEY_INDEX, Constants.IndexConstant.SPECIESVARIETEY_TYPE, constructor);
        return mapPage;
    }

    /**
     * 更新待审核通过之后的操作
     * @param auditVarieteyInfo
     */
    @Override
    public void updateVarieteyPassAudit(Map<String, Object> auditVarieteyInfo) {
        SpeciesVarieteyQuery speciesVarieteyQuery = new SpeciesVarieteyQuery();
        mapToSpeciesVarieteyQuery(speciesVarieteyQuery, auditVarieteyInfo);
        updateSpeciesVarietey(speciesVarieteyQuery);
    }

    @Override
    public SpeciesVarieteyQuery mapToSpeciesVarieteyQuery(SpeciesVarieteyQuery speciesVarieteyQuery, Map<String, Object> auditVarieteyInfo) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        if (StringUtils.isNotBlank(MapUtils.getString(auditVarieteyInfo,"indexid"))) {
            speciesVarieteyQuery.setIndexid(MapUtils.getString(auditVarieteyInfo,"indexid"));
        }
        if (StringUtils.isNotBlank(MapUtils.getString(auditVarieteyInfo,"fid"))) {
            speciesVarieteyQuery.setFid(MapUtils.getString(auditVarieteyInfo,"fid"));
        }
        if (StringUtils.isNotBlank(MapUtils.getString(auditVarieteyInfo,"fname"))) {
            speciesVarieteyQuery.setFname(MapUtils.getString(auditVarieteyInfo,"fname"));
        }
        if (StringUtils.isNotBlank(MapUtils.getString(auditVarieteyInfo,"fpinyin"))) {
            speciesVarieteyQuery.setFpinyin(MapUtils.getString(auditVarieteyInfo,"fpinyin"));
        }
        if (StringUtils.isNotBlank(MapUtils.getString(auditVarieteyInfo,"fvarietykind"))) {
            speciesVarieteyQuery.setFvarietykind(MapUtils.getString(auditVarieteyInfo,"fvarietykind"));
        }
        if (StringUtils.isNotBlank(MapUtils.getString(auditVarieteyInfo,"fvarietyorigin"))) {
            speciesVarieteyQuery.setFvarietyorigin(MapUtils.getString(auditVarieteyInfo,"fvarietyorigin"));
        }
        if (StringUtils.isNotBlank(MapUtils.getString(auditVarieteyInfo,"fbreedingunit"))) {
            speciesVarieteyQuery.setFbreedingunit(MapUtils.getString(auditVarieteyInfo,"fbreedingunit"));
        }
        if (StringUtils.isNotBlank(MapUtils.getString(auditVarieteyInfo,"fistransgene"))) {
            speciesVarieteyQuery.setFistransgene(Byte.parseByte(MapUtils.getString(auditVarieteyInfo,"fistransgene")));
        }
        if (StringUtils.isNotBlank(MapUtils.getString(auditVarieteyInfo,"fapplydate"))) {
            Date applyDate = null;
            try {
                applyDate = dateFormat.parse(MapUtils.getString(auditVarieteyInfo,"fapplydate"));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            speciesVarieteyQuery.setFapplydate(applyDate);
        }
        if (StringUtils.isNotBlank(MapUtils.getString(auditVarieteyInfo,"fapplynumber"))) {
            speciesVarieteyQuery.setFapplynumber(MapUtils.getString(auditVarieteyInfo,"fapplynumber"));
        }
        if (StringUtils.isNotBlank(MapUtils.getString(auditVarieteyInfo,"fapplyannouncedate"))) {
            Date applyAnnounceDate = null;
            try {
                applyAnnounceDate = dateFormat.parse(MapUtils.getString(auditVarieteyInfo,"fapplyannouncedate"));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            speciesVarieteyQuery.setFapplyannouncedate(applyAnnounceDate);
        }
        if (StringUtils.isNotBlank(MapUtils.getString(auditVarieteyInfo,"fapplyannouncenumber"))) {
            speciesVarieteyQuery.setFapplyannouncenumber(MapUtils.getString(auditVarieteyInfo,"fapplyannouncenumber"));
        }
        if (StringUtils.isNotBlank(MapUtils.getString(auditVarieteyInfo,"fauthdate"))) {
            Date authDate = null;
            try {
                authDate = dateFormat.parse(MapUtils.getString(auditVarieteyInfo,"fauthdate"));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            speciesVarieteyQuery.setFauthdate(authDate);
        }
        if (StringUtils.isNotBlank(MapUtils.getString(auditVarieteyInfo,"fauthnumber"))) {
            speciesVarieteyQuery.setFauthnumber(MapUtils.getString(auditVarieteyInfo,"fauthnumber"));
        }
        if (StringUtils.isNotBlank(MapUtils.getString(auditVarieteyInfo,"fauthannouncedate"))) {
            Date authAnnounceDate = null;
            try {
                authAnnounceDate = dateFormat.parse(MapUtils.getString(auditVarieteyInfo,"fauthannouncedate"));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            speciesVarieteyQuery.setFauthannouncedate(authAnnounceDate);
        }
        if (StringUtils.isNotBlank(MapUtils.getString(auditVarieteyInfo,"fauthannouncenumber"))) {
            speciesVarieteyQuery.setFauthannouncenumber(MapUtils.getString(auditVarieteyInfo,"fauthannouncenumber"));
        }
        if (StringUtils.isNotBlank(MapUtils.getString(auditVarieteyInfo,"fgrowpeople"))) {
            speciesVarieteyQuery.setFgrowpeople(MapUtils.getString(auditVarieteyInfo,"fgrowpeople"));
        }
        if (StringUtils.isNotBlank(MapUtils.getString(auditVarieteyInfo,"fvarietyowner"))) {
            speciesVarieteyQuery.setFvarietyowner(MapUtils.getString(auditVarieteyInfo,"fvarietyowner"));
        }
        if (StringUtils.isNotBlank(MapUtils.getString(auditVarieteyInfo,"fvarietyapprnum"))) {
            speciesVarieteyQuery.setFvarietyapprnum(MapUtils.getString(auditVarieteyInfo,"fvarietyapprnum"));
        }
        if (StringUtils.isNotBlank(MapUtils.getString(auditVarieteyInfo,"fvarietyapprdate"))) {
            speciesVarieteyQuery.setFvarietyapprdate(MapUtils.getString(auditVarieteyInfo,"fvarietyapprdate"));
        }
        if (StringUtils.isNotBlank(MapUtils.getString(auditVarieteyInfo,"fvarietyapprunit"))) {
            speciesVarieteyQuery.setFvarietyapprunit(MapUtils.getString(auditVarieteyInfo,"fvarietyapprunit"));
        }
        if (StringUtils.isNotBlank(MapUtils.getString(auditVarieteyInfo,"ffeature"))) {
            speciesVarieteyQuery.setFfeature(MapUtils.getString(auditVarieteyInfo,"ffeature"));
        }
        if (StringUtils.isNotBlank(MapUtils.getString(auditVarieteyInfo,"foutput"))) {
            speciesVarieteyQuery.setFoutput(MapUtils.getString(auditVarieteyInfo,"foutput"));
        }
        if (StringUtils.isNotBlank(MapUtils.getString(auditVarieteyInfo,"fgrowteachology"))) {
            speciesVarieteyQuery.setFgrowteachology(MapUtils.getString(auditVarieteyInfo,"fgrowteachology"));
        }
        if (StringUtils.isNotBlank(MapUtils.getString(auditVarieteyInfo,"fsuiteplatearea"))) {
            speciesVarieteyQuery.setFsuiteplatearea(MapUtils.getString(auditVarieteyInfo,"fsuiteplatearea"));
        }
        if (StringUtils.isNotBlank(MapUtils.getString(auditVarieteyInfo,"fmarketsituation"))) {
            speciesVarieteyQuery.setFmarketsituation(MapUtils.getString(auditVarieteyInfo,"fmarketsituation"));
        }
        if (StringUtil.isNotBlank(MapUtils.getString(auditVarieteyInfo,"ficon"))) {
            speciesVarieteyQuery.setFicon(MapUtils.getString(auditVarieteyInfo,"ficon"));
        }
        return speciesVarieteyQuery;
    }


    private Map update(SpeciesVarieteyQuery speciesVarieteyQuery) {
        Map<String, Object> map= Maps.newHashMap();
        if (StringUtil.isNotBlank(speciesVarieteyQuery.getIndexid())) {
            map =  baseEsearchDao.getIndexOfMap(Constants.IndexConstant.SPECIESVARIETEY_INDEX, Constants.IndexConstant.SPECIESVARIETEY_TYPE, speciesVarieteyQuery.getIndexid());
            map.put("updatetime", System.currentTimeMillis());
            if (StringUtil.isNotBlank(speciesVarieteyQuery.getSpeciesid())) {
                map.put("speciesid", speciesVarieteyQuery.getSpeciesid());
            }
            if (StringUtil.isNotBlank(speciesVarieteyQuery.getFname())) {
                map.put("fname", speciesVarieteyQuery.getFname());
            }
            if (StringUtil.isNotBlank(speciesVarieteyQuery.getFpinyin())) {
                map.put("fpinyin", speciesVarieteyQuery.getFpinyin());
            }
            if (StringUtil.isNotBlank(speciesVarieteyQuery.getFicon())) {
                map.put("ficon", speciesVarieteyQuery.getFicon());
            }
            if (StringUtil.isNotBlank(speciesVarieteyQuery.getFvarietyapprcertificationicon())) {
                map.put("fvarietyapprcertificationicon", speciesVarieteyQuery.getFvarietyapprcertificationicon());
            }
            if (StringUtil.isNotBlank( speciesVarieteyQuery.getFvarietyowner())) {
                map.put("fvarietyowner", speciesVarieteyQuery.getFvarietyowner());
            }
            if (ObjectUtil.isNotEmpty(speciesVarieteyQuery.getFvarietyapprnum())) {
                map.put("fvarietyapprnum", speciesVarieteyQuery.getFvarietyapprnum());
            }

            if (ObjectUtil.isNotEmpty(speciesVarieteyQuery.getFvarietykind())) {
                map.put("fvarietykind", speciesVarieteyQuery.getFvarietykind());
            }
            if (StringUtil.isNotBlank(speciesVarieteyQuery.getFdeleteid())) {
                map.put("fdeleteid", speciesVarieteyQuery.getFdeleteid());
            }
            if (ObjectUtil.isNotEmpty(speciesVarieteyQuery.getFisattention())) {
                map.put("fisattention", speciesVarieteyQuery.getFisattention());
            }
            if (ObjectUtil.isNotEmpty(speciesVarieteyQuery.getFapplydate())) {
                map.put("fapplydate", speciesVarieteyQuery.getFapplydate());
            }
            if (ObjectUtil.isNotEmpty(speciesVarieteyQuery.getFapplyannouncedate())) {
                map.put("fapplyannouncedate", speciesVarieteyQuery.getFapplyannouncedate());
            }

            if (ObjectUtil.isNotEmpty(speciesVarieteyQuery.getFauthdate())) {
                map.put("fauthdate", speciesVarieteyQuery.getFauthdate());
            }

            if (ObjectUtil.isNotEmpty(speciesVarieteyQuery.getFauthannouncedate())) {
                map.put("fauthannouncedate", speciesVarieteyQuery.getFauthannouncedate());
            }

            if (StringUtil.isNotBlank(speciesVarieteyQuery.getFvarietyapprdate())) {
                map.put("fvarietyapprdate", speciesVarieteyQuery.getFvarietyapprdate());
            }

            if (StringUtil.isNotBlank(speciesVarieteyQuery.getFapplynumber())) {
                map.put("fapplynumber", speciesVarieteyQuery.getFapplynumber());
            }

            if (ObjectUtil.isNotEmpty(speciesVarieteyQuery.getFapplyannouncenumber())) {
                map.put("fapplyannouncenumber", speciesVarieteyQuery.getFapplyannouncenumber());
            }
            if (StringUtil.isNotBlank(speciesVarieteyQuery.getFauthnumber())) {
                map.put("fauthnumber",speciesVarieteyQuery.getFauthnumber());
            }
            if (StringUtil.isNotBlank(speciesVarieteyQuery.getFauthannouncenumber())) {
                map.put("fauthannouncenumber",speciesVarieteyQuery.getFauthannouncenumber());
            }

            if (StringUtil.isNotBlank(speciesVarieteyQuery.getFvarietyapprunit())) {
                map.put("fvarietyapprunit", speciesVarieteyQuery.getFvarietyapprunit());
            }


            if (StringUtil.isNotBlank(speciesVarieteyQuery.getFareateststate())) {
                map.put("fareateststate", speciesVarieteyQuery.getFareateststate());
             }

            if (StringUtil.isNotBlank(speciesVarieteyQuery.getFvarietyorigin())) {
                map.put("fvarietyorigin", speciesVarieteyQuery.getFvarietyorigin());
            }

            if (StringUtil.isNotBlank(speciesVarieteyQuery.getFsuiteplatearea())) {
                map.put("fsuiteplatearea", speciesVarieteyQuery.getFsuiteplatearea());
            }

            if (StringUtil.isNotBlank(speciesVarieteyQuery.getFvarietyrecommand())) {
                map.put("fvarietyrecommand", speciesVarieteyQuery.getFvarietyrecommand());
            }

            if (StringUtil.isNotBlank(speciesVarieteyQuery.getFremarks())) {
                map.put("fremarks", speciesVarieteyQuery.getFremarks());
            }

            if (StringUtil.isNotBlank(speciesVarieteyQuery.getFgrowpeople())) {
                map.put("fgrowpeople", speciesVarieteyQuery.getFgrowpeople());
            }

            if (StringUtil.isNotBlank(speciesVarieteyQuery.getFfeature())) {
                map.put("ffeature", speciesVarieteyQuery.getFfeature());
            }

            if (StringUtil.isNotBlank(speciesVarieteyQuery.getFoutput())) {
                map.put("foutput", speciesVarieteyQuery.getFoutput());
            }

            if (StringUtil.isNotBlank(speciesVarieteyQuery.getFgrowteachology())) {
                map.put("fgrowteachology", speciesVarieteyQuery.getFgrowteachology());
            }

            if (StringUtil.isNotBlank(speciesVarieteyQuery.getFmarketsituation())) {
                map.put("fmarketsituation", speciesVarieteyQuery.getFmarketsituation());
            }

            if (StringUtil.isNotBlank(speciesVarieteyQuery.getImage())) {
                map.put("image", speciesVarieteyQuery.getImage());
            }
            if (ObjectUtil.isNotEmpty(speciesVarieteyQuery.getFistransgene())) {
                map.put("fistransgene", speciesVarieteyQuery.getFistransgene());
            }
            if (Integers.isNotEmpty(speciesVarieteyQuery.getFisdelete())) {
                map.put("fisdelete", speciesVarieteyQuery.getFisdelete());
            }
            if (Integers.isNotEmpty(speciesVarieteyQuery.getAuditstatus())) {
                map.put("auditstatus", speciesVarieteyQuery.getAuditstatus());
            }
            if (StringUtils.isNotBlank(speciesVarieteyQuery.getFbreedingunit())) {
                map.put("fbreedingunit", speciesVarieteyQuery.getFbreedingunit());
            }
        }
        return map;
    }

    private ESQueryBuilderConstructor getPageQuery(ESQueryBuilderConstructor constructor,SpeciesVarieteyListQuery speciesVarieteyListQuery){
        if(Integers.isNotEmpty(speciesVarieteyListQuery.getSortType())){
            if(speciesVarieteyListQuery.getSortType()==1){
                constructor.setAsc("updatetime");
            }
            if(speciesVarieteyListQuery.getSortType()==2){
                constructor.setDesc("updatetime");
            }
        }
        constructor.setDesc("updatetime");
        if(StringUtil.isNotBlank(speciesVarieteyListQuery.getUserId())){
            constructor.must(new ESQueryCondition().term("fcreatorid", speciesVarieteyListQuery.getUserId()));
        }

        if(StringUtil.isNotBlank(speciesVarieteyListQuery.getSpeciesId())){
            constructor.must(new ESQueryCondition().match("speciesid", speciesVarieteyListQuery.getSpeciesId()));
        }

        if(StringUtil.isNotBlank(speciesVarieteyListQuery.getFid())){
            constructor.must(new ESQueryCondition().match("fid", speciesVarieteyListQuery.getFid()));
        }

        if(StringUtil.isNotBlank(speciesVarieteyListQuery.getFpinyin())){
            constructor.must(new ESQueryCondition().like("fpinyin", speciesVarieteyListQuery.getFpinyin().toLowerCase() + "*"));
        }

        if(StringUtil.isNotBlank(speciesVarieteyListQuery.getValidName())){
            constructor.must(new ESQueryCondition().match("fname", speciesVarieteyListQuery.getValidName()));
        }

        if (StringUtil.isNotBlank(speciesVarieteyListQuery.getKeywords())) {
            if (RegexUtil.regChinese(speciesVarieteyListQuery.getKeywords()) || RegexUtil.isContainNumber(speciesVarieteyListQuery.getKeywords())) {
                constructor.must(new ESQueryCondition().like("fname", "*" + speciesVarieteyListQuery.getKeywords()+"*"));
            } else {
                constructor.must(new ESQueryCondition().like("fpinyin", speciesVarieteyListQuery.getKeywords().toLowerCase() + "*"));
            }
        }
        return constructor;
    }

    private Map getimage(Map map){
        if (!MapUtil.isEmpty(map, "image")) {
            String image=  Maps.getString(map, "image");
            if (image.contains("[")) {
                map.put("image", JSONHelper.parseArray(image));
            }
        }
        if(!MapUtil.isEmpty(map, "ficon")) {
            String ficon=  Maps.getString(map, "ficon");
            if (ficon.contains("[")) {
                map.put("ficon", JSONHelper.parseArray(ficon));
            }
        }
        /*if(!MapUtil.isEmpty(map, "fvarietyapprdate")) {
             String fvarietyapprdate=  Maps.getString(map, "fvarietyapprdate");
            String data=DateTimeUtil.StringTimeToDateFormat(fvarietyapprdate,"yyy");
                map.put("fvarietyapprdate",data);

        }*/
        /*if(!MapUtil.isEmpty(map, "fistransgene")) {
            String fistransgene=  Maps.getString(map, "fistransgene");
            if (StringUtil.isNotBlank(fistransgene)) {
                if (fistransgene.equals("0")) {
                    map.put("fistransgene", "否");
                }
                if (fistransgene.equals("1")) {
                    map.put("fistransgene", "是");
                }
            }
        }*/
        return map;
    }

}
