package com.ovit.nswy.member.web;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.ovit.nswy.member.service.NameLibraryService;
import com.ovit.nswy.member.service.SpeciesClassService;
import com.ovit.nswy.member.web.base.Result;
import com.ovit.nswy.member.web.base.ResultCode;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.annotation.PostConstruct;
import javax.cache.Cache;
import javax.cache.CacheManager;
import javax.cache.Caching;
import javax.cache.configuration.MutableConfiguration;
import javax.cache.expiry.Duration;
import javax.cache.expiry.ModifiedExpiryPolicy;
import javax.cache.spi.CachingProvider;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.*;


@RestController
@RequestMapping("/specicesClass")
public class SpeciesClassController {

    private static Cache<String, Map<String, JSONArray>> cache;
    private Logger logger = LogManager.getLogger(this.getClass());
    @Autowired
    private SpeciesClassService service;

    @Autowired
    private NameLibraryService nameLibraryService;

    private List<String> plantList= Arrays.asList("WZ0101","WZ0102","WZ0103","WZ0104","WZ0105",
            "WZ0106","WZ0107","WZ0108","WZ0109","WZ0110","WZ0111","WZ0112","WZ0113","WZ0114","WZ0115",
            "WZ0116","WZ0117","WZ0118","WZ0119","WZ0120","WZ0121");
    private List<String> animalList = Arrays.asList("WZ0201","WZ0202","WZ0203","WZ0204","WZ0206","WZ0207",
            "WZ0208","WZ0209","WZ0210","WZ0211","WZ0212","WZ0213");
    @PostConstruct
    public void init() {
        CachingProvider cachingProvider = Caching.getCachingProvider();
        CacheManager cacheManager = cachingProvider.getCacheManager();
        MutableConfiguration<String, Map<String, JSONArray>> config = new MutableConfiguration();
        config.setExpiryPolicyFactory(ModifiedExpiryPolicy.factoryOf(Duration.TEN_MINUTES))
                .setStatisticsEnabled(false);
        cache = cacheManager.createCache("specicesClass", config);
    }

    /**
     * 根据上级获取下级数据
     *
     * @param parentId
     * @param request
     * @return
     */
    @RequestMapping(value = "/findByParentId/{parentId}", method = RequestMethod.POST)
    public Result findByParentId(@PathVariable String parentId, HttpServletRequest request) {
        //LoginUser loginUser = (LoginUser) request.getSession().getAttribute("currentUser");
        Map<String, Object> map = new HashMap<>();
        map.put("parentId", parentId);
        //map.put("auditStatus",1);
        return service.findByParentId(JSON.toJSON(map));
    }

    @RequestMapping(value = "/findAll", method = RequestMethod.GET)
    public Result findAll() {
        //LoginUser loginUser = (LoginUser) request.getSession().getAttribute("currentUser");
        return service.findAll();
    }

    @RequestMapping(value = "/findSpecies", method = RequestMethod.POST)
    public Result CustomSet(@RequestBody Map<String, Object> params) {
        Result result = null;
        try {
            result = new Result();
            logger.info("从wiki获取物种分类,参数：{}",params);
            String type = MapUtils.getString(params,"type");
            String keywords = MapUtils.getString(params,"keywords");
            String fclassifiedid = MapUtils.getString(params,"fclassifiedid");
            String pinYin = MapUtils.getString(params,"fpinyin");

            /*StringBuffer redisKey = new StringBuffer("species");
            if (StringUtils.isNotEmpty(type)) {
                redisKey.append("_").append(type);
            }
            if (StringUtils.isNotEmpty(fclassifiedid)) {
                redisKey.append("_").append(fclassifiedid);
            }
            if (StringUtils.isNotEmpty(keywords)) {
                redisKey.append("_").append(keywords);
            }
            String key = redisKey.toString();
            logger.info("key:{}",key);*/
            //如果存在缓存则从缓存从获取数据，反之则查询ES并设置缓存
            //Map<String, JSONArray> jcache = cache.get("speciesClass");
            JSONArray jsonData = null;
            /*if (null != jcache) {
                jsonData = jcache.get(key);
            } else {
                jcache = new HashMap<>();
            }*/
            if (null == jsonData || 0 == jsonData.size()) {
                List<String> queryList = null;
                if (fclassifiedid != null){
                    queryList = removeEmpty(JSON.parseArray(fclassifiedid).toJavaList(String.class));
                } else {
                    new ArrayList<>();
                }
                JSONObject jsonObj = (JSONObject) JSON.toJSON(params);
                jsonObj.put("pageNum", 1);
                jsonObj.put("pageSize", 10000);
                //jsonObj.put("auditStatus",1);
                //如果type为空查询所有 0 动物 1植物
                if (CollectionUtils.isEmpty(queryList) && StringUtils.isEmpty(type)) {
                    jsonObj.remove("type");
                    jsonObj.remove("fclassifiedid");
                    if (StringUtils.isEmpty(pinYin)){jsonObj.remove("fpinyin");}
                    if (StringUtils.isEmpty(keywords)){jsonObj.remove("keywords");}
                    JSONObject data = (JSONObject) service.findSpecies(jsonObj);
                    jsonData = data.getJSONArray("data");
                    logger.info("从wiki获取物种分类,参数：{}，返回：{}", jsonObj, jsonData.size());
                } else {
                    jsonData = new JSONArray();
                    //如果动植物不为空，查询动植物，合并
                    if (StringUtils.isNotEmpty(type)) {
                        //选动物大类时，动物类型添加到选中的类型，去重取得所有选中的小类，植物同理，都不是则根据id查询
                        if ("0".equals(type)) {
                            queryList.addAll(animalList);
                        } else if ("1".equals(type)){
                            queryList.addAll(plantList);
                        }
                    }
                    //如果fclassifiedid不为空，合并
                    if (CollectionUtils.isNotEmpty(queryList)) {
                        //去重
                        Set<String> set = new HashSet(queryList);
                        for (String id : set) {
                            jsonObj.put("fclassifiedid", id);
                            JSONObject data = (JSONObject) service.findSpecies(jsonObj);
                            JSONArray jsonArray = data.getJSONArray("data");
                            logger.info("从wiki获取物种分类,参数：{}，返回：{}", jsonObj, jsonArray.size());
                            jsonData.addAll(jsonArray);
                        }
                    }
                }
                /*jcache.put(key, jsonData);
                cache.put("speciesClass", jcache);*/
            }
            PageInfo<SpeciesJson> pageInfo = new PageInfo<>();
            SpeciesJson json = new SpeciesJson();
            List<SpeciesJson> totalList = json.convert(jsonData);
            params.put("type","0");
            List<Map<String,Object>> list = nameLibraryService.findConllectionList(params);
            List<String> speciesIdList = new ArrayList<>();
            for (Map<String,Object> map :list) {
                speciesIdList.add(MapUtils.getString(map, "libraryId"));
            }
            List<SpeciesJson> list1 = new ArrayList<>();
            Iterator<SpeciesJson> iterator = totalList.iterator();
            while (iterator.hasNext()) {
                SpeciesJson speciesJson = iterator.next();
                String speciesId = speciesJson.getValue();
                if (speciesIdList.contains(speciesId)) {
                    list1.add(speciesJson);
                    iterator.remove();
                }
            }
            list1.addAll(totalList);

            logger.info("jsonData：{}，totalList：{}",jsonData.size(),totalList.size());
            int pageNum = MapUtils.getInteger(params,"pageNum");
            int pageSize = MapUtils.getInteger(params,"pageSize");
            pageInfo.setList(new Pager(list1, pageSize).calc(pageNum));
            pageInfo.setTotal(totalList.size());
            pageInfo.setPageSize(pageSize);
            pageInfo.setPageNum(pageNum);
            result.setData(pageInfo);
        } catch (Exception e) {
            logger.error("查询物种失败",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg(e.getMessage());
        }
        return result;
    }

    class Pager {

        private int items;
        private int page;
        private int pageSize;
        private List<SpeciesJson> data = new ArrayList<>();

        public Pager(List<SpeciesJson> list, int pageSize) {
            this.pageSize = pageSize;
            this.data.addAll(list);
        }

        public List<SpeciesJson> calc(int pageNum) {
            this.page = calcPage(pageNum);
            return data.subList(getStartRow(), getEndRow());
        }

        private int calcPage(int page) {
            int pages;
            items = data.size();
            if (items <= 0) {
                return 1;
            }
            pages = (int) Math.ceil((double) items / pageSize);
            if (pages > 0) {
                return (page < 1) ? 1 : ((page > pages) ? pages : page);
            }
            return 0;
        }

        private int getStartRow() {
            int startRow;
            if (page > 0) {
                startRow = (pageSize * (page - 1));
            } else {
                startRow = 0;
            }
            return startRow;
        }

        /**
         * @return the endRow
         */
        private int getEndRow() {
            int endRow;
            if (page > 0) {
                endRow = Math.min(pageSize * page, items);
            } else {
                endRow = 0;
            }
            return endRow;
        }
    }
    class SpeciesJson implements Serializable {
        private String label;
        private String value;

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        private List<SpeciesJson> convert(JSONArray array) {
            List<SpeciesJson> list = new ArrayList<>(array.size());
            for (Object json : array) {
                SpeciesJson sjson = new SpeciesJson();
                sjson.setLabel(((JSONObject) json).getString("fname"));
                sjson.setValue(((JSONObject) json).getString("speciesid"));
                list.add(sjson);
            }
            return list;
        }
    }
    private List<String> removeEmpty(List<String> oldList){
        List newList = new ArrayList<>();
        for (String value : oldList) {
            if (StringUtils.isNotEmpty(value)) {
                newList.add(value);
            }
        }
        return newList;
    }
}