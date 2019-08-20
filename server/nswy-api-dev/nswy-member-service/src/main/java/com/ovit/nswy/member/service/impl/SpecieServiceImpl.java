package com.ovit.nswy.member.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.xphsc.json.JSONHelper;
import com.ovit.nswy.member.feigen.RelatedServiceFeign;
import com.ovit.nswy.member.mapper.SpeciesMapper;
import com.ovit.nswy.member.model.FavoriteSpecies;
import com.ovit.nswy.member.model.Species;
import com.ovit.nswy.member.model.SpeciesQuery;
import com.ovit.nswy.member.model.SpeciesResult;
import com.ovit.nswy.member.service.LoginUserService;
import com.ovit.nswy.member.service.SpecieService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.cache.Cache;
import javax.cache.CacheManager;
import javax.cache.Caching;
import javax.cache.configuration.MutableConfiguration;
import javax.cache.expiry.Duration;
import javax.cache.expiry.ModifiedExpiryPolicy;
import javax.cache.spi.CachingProvider;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SpecieServiceImpl implements SpecieService {

    private Logger logger = LogManager.getLogger(this.getClass());
    @Autowired
    private SpeciesMapper dao;

    @Autowired
    private RelatedServiceFeign relatedServiceFeign;

    @Autowired
    private LoginUserService loginUserService;

    private static Cache<String, List<Map<String, Object>>> spciesClassCache;

    @PostConstruct
    public void init() {
        CachingProvider cachingProvider = Caching.getCachingProvider();
        CacheManager cacheManager = cachingProvider.getCacheManager();
        MutableConfiguration<String, List<Map<String, Object>>> config = new MutableConfiguration();
        config.setExpiryPolicyFactory(ModifiedExpiryPolicy.factoryOf(Duration.ONE_DAY))
                .setStatisticsEnabled(false);
        spciesClassCache = cacheManager.createCache("speciesClass", config);
    }

    @Override
    public int insert(FavoriteSpecies favoriteSpecies) {
        return dao.insert(favoriteSpecies);
    }

    @Override
    public List<Species> findFollowedSpecies(String account) {
        return dao.findFollowedSpecies(account);
    }

    public void empty(String account) {
        dao.empty(account);
    }

    @Override
    public List<String> findByAccount(String account) {
        return dao.findByAccount(account);
    }

    @Override
    public FavoriteSpecies findSpecByAccount(String account) {

        Map<String,Object> useTemplate = loginUserService.findUseTemplate(account);
        return dao.findSpecByAccount(useTemplate);
    }

    @Override
    public String getSpecies(String speciesId) {
        StringBuffer FName = new StringBuffer();
        if (StringUtils.isNotEmpty(speciesId)) {
            String[] speciesArray = speciesId.split(" ");
            for (String species : speciesArray) {
                SpeciesQuery speciesQuery = new SpeciesQuery();
                speciesQuery.setSpeciesid(species);
                Object data = relatedServiceFeign.listSpecies(speciesQuery);
                JSONObject jsonObject = JSONObject.parseObject(data.toString());
                List<SpeciesResult> objectList = JSONHelper.parseArray(jsonObject.get("data").toString(), SpeciesResult.class);
                if (CollectionUtils.isNotEmpty(objectList)) {
                    FName.append(objectList.get(0).getFname());
                    FName.append(" ");
                }
            }
        }
        return FName.toString();
    }

    /**
     * 根据ID更新关联物种表
     * @param favoriteSpecies
     */
    @Override
    public void updateByPrimaryKey(FavoriteSpecies favoriteSpecies) {
        dao.updateByPrimaryKey(favoriteSpecies);
    }

    /**
     * 查询全部物种分类列表
     * @return
     */
    @Override
    public List<Map<String, Object>> listAllSpeciesClass() {

        List<Map<String, Object>> allclassifyList = spciesClassCache.get("SPECIES_CLASS");
        if (allclassifyList == null || allclassifyList.size() == 0) {
            //远程调用wiki工程的查看全部分类接口
            Object detailInfo = relatedServiceFeign.listAllSpeciesclass();
            JSONObject jsonObject = JSONObject.parseObject(detailInfo.toString());
            Map<String,Object> allclassifyInfo = (Map<String, Object>) jsonObject.get("data");
            List<Map<String,Object>> animalList = JSON.parseObject(allclassifyInfo.get("animalList").toString(), List.class);
            List<Map<String,Object>> plantList = JSON.parseObject(allclassifyInfo.get("plantList").toString(), List.class);
            logger.info("动物分类：{}, 植物分类：{}", animalList, plantList);
            Map<String, Object> animalclassify = getClassifyInfo(animalList, "动物", 0);
            Map<String, Object> plantclassify = getClassifyInfo(plantList, "植物", 1);
            allclassifyList = new ArrayList<>();
            allclassifyList.add(animalclassify);
            allclassifyList.add(plantclassify);
            spciesClassCache.put("SPECIES_CLASS", allclassifyList);
            logger.debug("物种全部分类信息为：{}", allclassifyList);
        } else {
            logger.debug("从缓存中获取物种分类信息：{}", allclassifyList);
        }

        return allclassifyList;
    }

    @Override
    public Map<String, Object> listClassifySpeciesInfo() {
        //远程调用wiki工程的查看全部分类接口
        Object detailInfo = relatedServiceFeign.listAllSpeciesclass();
        JSONObject jsonObject = JSONObject.parseObject(detailInfo.toString());
        Map<String,Object> allclassifyInfo = (Map<String, Object>) jsonObject.get("data");
        List<Map<String,Object>> animalList = JSON.parseObject(allclassifyInfo.get("animalList").toString(), List.class);
        List<Map<String,Object>> plantList = JSON.parseObject(allclassifyInfo.get("plantList").toString(), List.class);
        Map<String,Object> speciesInfo = new HashMap<>();
        speciesInfo.put("animalList", animalList);
        speciesInfo.put("plantList", plantList);

        return speciesInfo;
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
    public List<Map<String,Object>> speciesPageInfo(List<Map<String, Object>> speciesList, int pageNum, int pageSize){
        List<Map<String,Object>> speciesDealList = new ArrayList<>();
        List<Map<String, Object>> subList = null;
        try {
            for (Map<String,Object> species: speciesList) {
                Map<String,Object> speciesDeal = new HashMap<>();
                speciesDeal.put("name", species.get("fname"));
                speciesDeal.put("checked", false);
                speciesDeal.put("id", species.get("fclassifiedid"));
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
}
