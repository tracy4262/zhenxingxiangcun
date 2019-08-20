package com.ovit.nswy.member.perfectInfo.service.impl;

import com.ovit.nswy.frame.common.util.RegexUtil;
import com.ovit.nswy.member.manage.service.TemplateConfigService;
import com.ovit.nswy.member.perfectInfo.mapper.EcoSocialDevelopMapper;
import com.ovit.nswy.member.perfectInfo.mapper.PerfectBasicMapper;
import com.ovit.nswy.member.perfectInfo.service.EcoSocialDevelopService;
import com.ovit.nswy.member.perfectInfo.service.PerfectBasicService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class EcoSocialDevelopServiceImpl implements EcoSocialDevelopService {

    private Logger logger = LogManager.getLogger(this.getClass());

    @Autowired
    private PerfectBasicMapper perfectBasicMapper;

    @Autowired
    private PerfectBasicService perfectBasicService;

    @Autowired
    private EcoSocialDevelopMapper ecoSocialDevelopMapper;


    @Autowired
    private TemplateConfigService templateConfigService;

    /**
     * 通过用户名查询用户的产业信息
     * @param params
     * @return
     */
    @Override
    public Map<String, Object> findIndustry(Map<String, Object> params) {
        Map<String, Object> resultMap = new HashMap<>();
        //第一产业
        params.put("type", "1");
        List<Map<String, Object>> primaryIndustry = ecoSocialDevelopMapper.findIndustry(params);
        if (CollectionUtils.isEmpty(primaryIndustry)) {
            primaryIndustry = new ArrayList<>();
        }
        resultMap.put("primaryIndustry", primaryIndustry);
        //第二产业
        params.put("type", "2");
        List<Map<String, Object>> secondaryIndustry = ecoSocialDevelopMapper.findIndustry(params);
        if (CollectionUtils.isEmpty(secondaryIndustry)) {
            secondaryIndustry = new ArrayList<>();
        }
        resultMap.put("secondaryIndustry", secondaryIndustry);
        //第三产业
        params.put("type", "3");
        List<Map<String, Object>> tertiaryIndustry = ecoSocialDevelopMapper.findIndustry(params);
        if (CollectionUtils.isEmpty(tertiaryIndustry)) {
            tertiaryIndustry = new ArrayList<>();
        }
        resultMap.put("tertiaryIndustry", tertiaryIndustry);

        //获取属性名称
        Map<String,Object> propertyInfo = perfectBasicService.findPropertyInfo(params);
        if (MapUtils.isEmpty(propertyInfo)) {
            resultMap.put("propertyName", "产业信息");
        } else {
            resultMap.put("propertyName", MapUtils.getString(propertyInfo, "propertyName"));
        }
        //获取预览信息
        getPreviewInfo(resultMap, params);
        return resultMap;
    }


    /**
     * 修改用户的产业信息
     * @param params
     * @return
     */
    @Override
    public void saveIndustry(Map<String, Object> params) {

        List<Map<String,Object>> list = (List<Map<String, Object>>) params.get("list");
        for (Map<String,Object> map : list) {
            map.put("account", MapUtils.getString(params, "account"));
            map.put("yearId", MapUtils.getString(params, "yearId"));
            map.put("status", MapUtils.getString(params, "status"));
            map.put("type", MapUtils.getString(params, "type"));
            map.put("templateId", MapUtils.getString(params, "templateId"));
            if (StringUtils.isBlank(MapUtils.getString(map, "id"))) {
                map.put("id", null);
            }
        }

        ecoSocialDevelopMapper.saveIndustry(list);

    }

    /**
     * 删除产业信息
     * @param params
     */
    @Override
    public void deleteIndustry(Map<String, Object> params) {

        ecoSocialDevelopMapper.deleteIndustry(params);

    }

    @Override
    public Map<String, Object> findBreed(Map<String, Object> params) {
        Map<String, Object> resultMap = new HashMap<>();
        //1:农业,2:林业，3:畜牧业,4:水产业
        //农业 agriculture
        params.put("type", "1");
        List<Map<String, Object>> agriculture = ecoSocialDevelopMapper.findBreed(params);
        if (CollectionUtils.isEmpty(agriculture)) {
            agriculture = new ArrayList<>();
        }
        resultMap.put("agriculture", agriculture);
        //林业 forestry
        params.put("type", "2");
        List<Map<String, Object>> forestry = ecoSocialDevelopMapper.findBreed(params);
        if (CollectionUtils.isEmpty(forestry)) {
            forestry = new ArrayList<>();
        }
        resultMap.put("forestry", forestry);
        //畜牧业  animalHusbandry
        params.put("type", "3");
        List<Map<String, Object>> animalHusbandry = ecoSocialDevelopMapper.findBreed(params);
        if (CollectionUtils.isEmpty(animalHusbandry)) {
            animalHusbandry = new ArrayList<>();
        }
        resultMap.put("animalHusbandry", animalHusbandry);
        //水产业  waterIndustry
        params.put("type", "4");
        List<Map<String, Object>> waterIndustry = ecoSocialDevelopMapper.findBreed(params);
        if (CollectionUtils.isEmpty(waterIndustry)) {
            waterIndustry = new ArrayList<>();
        }
        resultMap.put("waterIndustry", waterIndustry);

        //获取属性名称
        Map<String,Object> propertyInfo = perfectBasicService.findPropertyInfo(params);
        if (MapUtils.isEmpty(propertyInfo)) {
            resultMap.put("propertyName", "种养信息");
        } else {
            resultMap.put("propertyName", MapUtils.getString(propertyInfo, "propertyName"));
        }
        //获取预览信息
        getPreviewInfo(resultMap, params);
        return resultMap;
    }

    @Override
    public void saveBreed(Map<String, Object> params) {

        List<Map<String,Object>> list = (List<Map<String, Object>>) params.get("list");
        for (Map<String,Object> map : list) {
            map.put("account", MapUtils.getString(params, "account"));
            map.put("yearId", MapUtils.getString(params, "yearId"));
            map.put("status", MapUtils.getString(params, "status"));
            map.put("type", MapUtils.getString(params, "type"));
            map.put("templateId", MapUtils.getString(params, "templateId"));
            if (StringUtils.isBlank(MapUtils.getString(map, "id"))) {
                map.put("id", null);
            }
        }
        ecoSocialDevelopMapper.saveBreed(params);

    }

    @Override
    public void deleteBreed(Map<String, Object> params) {

        ecoSocialDevelopMapper.deleteBreed(params);

    }

    @Override
    public Map<String, Object> findFarmProduct(Map<String, Object> params) {
        Map<String, Object> resultMap = new HashMap<>();
        //1:农产品,2:林业产品，3:畜牧业产品,4:水产品
        //农产品 agriculture
        params.put("type", "1");
        List<Map<String, Object>> agriculture = ecoSocialDevelopMapper.findFarmProduct(params);
        if (CollectionUtils.isEmpty(agriculture)) {
            agriculture = new ArrayList<>();
        }
        resultMap.put("agriculture", agriculture);
        //林业产品 forestry
        params.put("type", "2");
        List<Map<String, Object>> forestry = ecoSocialDevelopMapper.findFarmProduct(params);
        if (CollectionUtils.isEmpty(forestry)) {
            forestry = new ArrayList<>();
        }
        resultMap.put("forestry", forestry);
        //畜牧业产品  animalHusbandry
        params.put("type", "3");
        List<Map<String, Object>> animalHusbandry = ecoSocialDevelopMapper.findFarmProduct(params);
        if (CollectionUtils.isEmpty(animalHusbandry)) {
            animalHusbandry = new ArrayList<>();
        }
        resultMap.put("animalHusbandry", animalHusbandry);
        //水产品  waterIndustry
        params.put("type", "4");
        List<Map<String, Object>> waterIndustry = ecoSocialDevelopMapper.findFarmProduct(params);
        if (CollectionUtils.isEmpty(waterIndustry)) {
            waterIndustry = new ArrayList<>();
        }
        resultMap.put("waterIndustry", waterIndustry);

        //获取属性名称
        Map<String,Object> propertyInfo = perfectBasicService.findPropertyInfo(params);
        if (MapUtils.isEmpty(propertyInfo)) {
            resultMap.put("propertyName", "农产品信息");
        } else {
            resultMap.put("propertyName", MapUtils.getString(propertyInfo, "propertyName"));
        }
        //获取预览信息
        getPreviewInfo(resultMap, params);
        return resultMap;
    }

    @Override
    public void saveFarmProduct(Map<String, Object> params) {

        List<Map<String,Object>> list = (List<Map<String, Object>>) params.get("list");
        for (Map<String,Object> map : list) {
            map.put("account", MapUtils.getString(params, "account"));
            map.put("yearId", MapUtils.getString(params, "yearId"));
            map.put("status", MapUtils.getString(params, "status"));
            map.put("type", MapUtils.getString(params, "type"));
            map.put("templateId", MapUtils.getString(params, "templateId"));
            if (StringUtils.isBlank(MapUtils.getString(map, "id"))) {
                map.put("id", null);
            }
        }
        ecoSocialDevelopMapper.saveFarmProduct(params);

    }

    @Override
    public void deleteFarmProduct(Map<String, Object> params) {

        ecoSocialDevelopMapper.deleteFarmProduct(params);

    }

    @Override
    public Map<String, Object> findTechnicalProduct(Map<String, Object> params) {
        Map<String, Object> resultMap = new HashMap<>();
        //手工业产品
        params.put("type", "1");
        List<Map<String, Object>> handicraftProducts = ecoSocialDevelopMapper.findTechnicalProduct(params);
        if (CollectionUtils.isEmpty(handicraftProducts)) {
            handicraftProducts = new ArrayList<>();
        }
        resultMap.put("handicraftProducts", handicraftProducts);
        //工业产品
        params.put("type", "2");
        List<Map<String, Object>> technicalProduct = ecoSocialDevelopMapper.findTechnicalProduct(params);
        if (CollectionUtils.isEmpty(technicalProduct)) {
            technicalProduct = new ArrayList<>();
        }
        resultMap.put("technicalProduct", technicalProduct);

        //获取属性名称
        Map<String,Object> propertyInfo = perfectBasicService.findPropertyInfo(params);
        if (MapUtils.isEmpty(propertyInfo)) {
            resultMap.put("propertyName", "工业产品信息");
        } else {
            resultMap.put("propertyName", MapUtils.getString(propertyInfo, "propertyName"));
        }
        //获取预览信息
        getPreviewInfo(resultMap, params);
        return resultMap;
    }

    @Override
    public void saveTechnicalProduct(Map<String, Object> params) {

        List<Map<String,Object>> list = (List<Map<String, Object>>) params.get("list");
        for (Map<String,Object> map : list) {
            map.put("account", MapUtils.getString(params, "account"));
            map.put("yearId", MapUtils.getString(params, "yearId"));
            map.put("status", MapUtils.getString(params, "status"));
            map.put("type", MapUtils.getString(params, "type"));
            map.put("templateId", MapUtils.getString(params, "templateId"));
            if (StringUtils.isBlank(MapUtils.getString(map, "id"))) {
                map.put("id", null);
            }
        }
        ecoSocialDevelopMapper.saveTechnicalProduct(params);

    }

    @Override
    public void deleteTechnicalProduct(Map<String, Object> params) {

        ecoSocialDevelopMapper.deleteTechnicalProduct(params);

    }

    @Override
    public Map<String, Object> findServiceProduct(Map<String, Object> params) {
        Map<String, Object> resultMap = new HashMap<>();
        //agriculturalService
        params.put("type", "1");
        List<Map<String, Object>> agriculturalService = ecoSocialDevelopMapper.findServiceProduct(params);
        if (CollectionUtils.isEmpty(agriculturalService)) {
            agriculturalService = new ArrayList<>();
        }
        resultMap.put("agriculturalProduct", agriculturalService);
        //其他服务业
        params.put("type", "2");
        List<Map<String, Object>> otherService = ecoSocialDevelopMapper.findServiceProduct(params);
        if (CollectionUtils.isEmpty(otherService)) {
            otherService = new ArrayList<>();
        }
        resultMap.put("otherService", otherService);

        //获取属性名称
        Map<String,Object> propertyInfo = perfectBasicService.findPropertyInfo(params);
        if (MapUtils.isEmpty(propertyInfo)) {
            resultMap.put("propertyName", "服务产品信息");
        } else {
            resultMap.put("propertyName", MapUtils.getString(propertyInfo, "propertyName"));
        }
        //获取预览信息
        getPreviewInfo(resultMap, params);
        return resultMap;
    }

    @Override
    public void saveServiceProduct(Map<String, Object> params) {

        List<Map<String,Object>> list = (List<Map<String, Object>>) params.get("list");
        for (Map<String,Object> map : list) {
            map.put("account", MapUtils.getString(params, "account"));
            map.put("yearId", MapUtils.getString(params, "yearId"));
            map.put("status", MapUtils.getString(params, "status"));
            map.put("type", MapUtils.getString(params, "type"));
            map.put("templateId", MapUtils.getString(params, "templateId"));
            if (StringUtils.isBlank(MapUtils.getString(map, "id"))) {
                map.put("id", null);
            }
        }
        ecoSocialDevelopMapper.saveServiceProduct(params);

    }

    @Override
    public void deleteServiceProduct(Map<String, Object> params) {

        ecoSocialDevelopMapper.deleteServiceProduct(params);

    }

    /**
     * 查询查询国民经济行业分类数据
     * @param params
     * @return
     */
    @Override
    public List<Map<String, Object>> findEco(Map<String, Object> params) {

        List<Map<String,Object>> resultList = new ArrayList<>();
        List<String> idList = (List<String>) params.get("ids");
        for (String id : idList) {
            params.put("id", id);
            resultList.addAll(findEcoBasicData(params));
        }
        String keywords = MapUtils.getString(params, "keywords");
        String fpinyin = MapUtils.getString(params, "fpinyin");
        List<Map<String,Object>> loopList = new ArrayList<>();
        if (StringUtils.isNotBlank(keywords) || StringUtils.isNotBlank(fpinyin)) {
            for (Map<String,Object> map : resultList) {
                //通过关键字、拼音筛选
                String pinyin = MapUtils.getString(map, "pinyin");
                String label = MapUtils.getString(map, "label");
                if (StringUtils.isNotBlank(keywords)) {
                    if (!label.contains(keywords)) {
                        continue;
                    }
                }
                if (StringUtils.isNotBlank(fpinyin)) {
                    if (!pinyin.equalsIgnoreCase(fpinyin)) {
                        continue;
                    }
                }
                loopList.add(map);
            }
            List<Map<String,Object>> result = removeDuplicate(loopList);
            return result;
        }

        List<Map<String,Object>> result = removeDuplicate(resultList);
        return result;

    }

    @Override
    public List<Map<String, Object>> findByParentId(String parentId) {
        List<Map<String, Object>> list = ecoSocialDevelopMapper.findByParentId(parentId);
        for (Map<String,Object> map : list) {
            String id = MapUtils.getString(map, "classId");
            List<Map<String, Object>> subList = ecoSocialDevelopMapper.findByParentId(id);
            if (CollectionUtils.isNotEmpty(subList)) {
                map.put("children", new String[0]);
            }
        }
        return list;
    }

    /**
     * 通过value,去重
     * @param SpeciesQueryList
     * @return
     */
    public List<Map<String,Object>> removeDuplicate(List<Map<String,Object>> list) {

        List<Map<String,Object>> result = new ArrayList<>();
        List<String> valueList  = new ArrayList<>();
        for (Map<String,Object> map : list) {
            String value = MapUtils.getString(map, "value");
            if (valueList.contains(value)) {
                //List包含value,重复，不加入到result中
                logger.debug("该分类value：{}，已存在", value);
            } else {
                valueList.add(value);
                result.add(map);
            }
        }
        return result;
    }

    /**
     * 获取预览信息
     * @param result
     * @param params
     * @return
     */
    private Map<String,Object> getPreviewInfo(Map<String,Object> result, Map<String,Object> params) {

        //获取预览信息
        Map<String,Object> textPreview = perfectBasicMapper.findTextPreview(params);

        if(MapUtils.isEmpty(textPreview)){
            Map<String, Object> historyInfo = new HashMap<>();
            historyInfo.put("id", MapUtils.getString(params,"templateId"));
            List<Map<String,Object>> list  = templateConfigService.find(historyInfo);
            if(list.size()>0){
                historyInfo.put("account",MapUtils.getString(list.get(0),"account"));
            }else{
                historyInfo.put("account",MapUtils.getString(params,"account"));
                historyInfo.put("yearId", MapUtils.getString(params,"yearId"));
            }

            historyInfo.put("templateId", MapUtils.getString(params,"templateId"));
            historyInfo.put("dictId", MapUtils.getString(params,"dictId"));
            textPreview =  perfectBasicMapper.findTextPreview(historyInfo);
        }

        if (MapUtils.isNotEmpty(textPreview)) {
            result.put("preview", MapUtils.getString(textPreview, "textPreview"));
            result.put("status", MapUtils.getInteger(textPreview, "status"));
        } else {
            result.put("preview", "");
            result.put("status", 1);
        }

        return result;
    }


    /**
     * 通过ID查到国民经济行业分类最底层的数据
     * @param id
     * @return
     */
    private List<Map<String,Object>> findEcoBasicData(Map<String,Object> params) {
        List<Map<String,Object>> resultList = new ArrayList<>();
        List<Map<String,Object>> basicList = ecoSocialDevelopMapper.findEcoBasicData(params);
        if (CollectionUtils.isNotEmpty(basicList)) {
            for (Map<String,Object> map : basicList) {
                String loopId = MapUtils.getString(map, "value");
                Map<String,Object> loopMap = new HashMap<>();
                loopMap.put("keywords", MapUtils.getString(params, "keywords"));
                loopMap.put("fpinyin", MapUtils.getString(params, "fpinyin"));
                loopMap.put("id", loopId);
                List<Map<String, Object>> loopList = ecoSocialDevelopMapper.findEcoBasicData(loopMap);
                if (CollectionUtils.isNotEmpty(loopList)) {
                    resultList.addAll(findEcoBasicData(loopMap));
                } else {
                    resultList.add(map);
                }
            }

        }
        return resultList;
    }
    /*private List<Map<String,Object>> findEcoBasicData(String id) {
        List<Map<String,Object>> resultList = new ArrayList<>();
        List<Map<String,Object>> basicList = ecoSocialDevelopMapper.findEcoBasicData(id);
        if (CollectionUtils.isNotEmpty(basicList)) {
            for (Map<String,Object> map : basicList) {
                String loopId = MapUtils.getString(map, "value");
                List<Map<String, Object>> loopList = ecoSocialDevelopMapper.findEcoBasicData(loopId);
                if (CollectionUtils.isNotEmpty(loopList)) {
                    resultList.addAll(findEcoBasicData(loopId));
                } else {
                    resultList.add(map);
                }
            }

        }
        return resultList;
    }*/


}
