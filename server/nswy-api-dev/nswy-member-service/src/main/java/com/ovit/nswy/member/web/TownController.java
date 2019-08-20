package com.ovit.nswy.member.web;

import com.ovit.nswy.member.model.Town;
import com.ovit.nswy.member.service.SystemDictService;
import com.ovit.nswy.member.service.TownService;
import com.ovit.nswy.member.web.base.Result;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.PostConstruct;
import javax.cache.Cache;
import javax.cache.CacheManager;
import javax.cache.Caching;
import javax.cache.configuration.MutableConfiguration;
import javax.cache.spi.CachingProvider;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/town")
public class TownController {

    //地区缓存
    //private static Cache<String, Map<String, List<Town>>> cache;

    private static Cache<String, Map<String, List<Town>>> systemDictCache;

    @Autowired
    private TownService service;

    @Autowired
    private SystemDictService systemDictService;

    //private String AREA_CACHE_KEY = "zone";
    private String SYSTEM_DICT_CACHE_KEY = "system_dict_gov";

    @Autowired
    private RedisTemplate<String, List<Town>> townRedis;

    /**
     * 初始化地区数据
     */
    /*@PostConstruct
    public void initial() {

        List<Town> townList = service.findAllTown();
        Map<String, List<Town>> map = new HashMap<>();
        for (Town town : townList) {
            String key = town.getParentId();
            if (map.containsKey(key)) {
                List<Town> tmp = map.get(key);
                tmp.add(town);
            } else {
                List<Town> tmp = new ArrayList<>();
                tmp.add(town);
                map.put(key, tmp);
            }
        }

        townRedis.opsForValue().set("zone", map);

    }*/

    /**
     * 根据type初始化字典表数据
     */
    @PostConstruct
    public void initialSystemDict() {
        CachingProvider cachingProvider = Caching.getCachingProvider();
        CacheManager cacheManager = cachingProvider.getCacheManager();
        MutableConfiguration<String, Map<String, List<Town>>> config = new MutableConfiguration();
        systemDictCache = cacheManager.createCache(SYSTEM_DICT_CACHE_KEY, config);
        List<Town> townList = systemDictService.findAllSystemDict();
        systemDictCache.put(SYSTEM_DICT_CACHE_KEY, initMap(townList));
    }

    /**
     * 封装数据
     * @param townList
     * @return
     */
    private Map<String, List<Town>> initMap(List<Town> townList){
        Map<String, List<Town>> map = new HashMap<>();
        for (Town town : townList) {
            String key = town.getParentId();
            if (map.containsKey(key)) {
                List<Town> tmp = map.get(key);
                tmp.add(town);
            } else {
                List<Town> tmp = new ArrayList<>();
                tmp.add(town);
                map.put(key, tmp);
            }
        }
        return map;
    }
    /**
     * 地区空间使用
     * 根据上级获取下级数据
     *
     * @param parentId
     * @return
     */
    @RequestMapping(value = "/next/{parentId}", method = RequestMethod.POST)
    public Result next(@PathVariable String parentId) {
        Result result = new Result();
        // 先查询redis
        //Map<String, List<Town>> map = cache.get(AREA_CACHE_KEY);
       /* Map<String, List<Town>> map = townRedis.opsForValue().get("town");
        if (MapUtils.isEmpty(map)) {
            initial();
        }*/
        String redisKey = "town_" + parentId;
        List<Town> townList = townRedis.opsForValue().get(redisKey);
        // 如果为空，从数据库查询
        if (CollectionUtils.isEmpty(townList)) {
            townList = service.findTownByParentId(parentId);
            townRedis.opsForValue().set(redisKey, townList);
        }
        if (CollectionUtils.isNotEmpty(townList)) {
            TownJson ton = new TownJson();
            result.setData(ton.convert(townList));
        }

        /*List<TownJson> townJsonList = townRedis.opsForValue().get(redisKey);
        // 如果为空，从数据库查询
        if (CollectionUtils.isEmpty(townJsonList)) {
            List<Town> townList = service.findTownByParentId(parentId);
            TownJson ton = new TownJson();
            townJsonList = ton.convert(townList);
            townRedis.opsForValue().set(redisKey, townJsonList);
        }
        result.setData(townJsonList);*/

        return result;
    }

    /**
     * 字典表数据公用类
     * 根据上级获取下级数据
     *
     * @param parentId
     * @return
     */
    @RequestMapping(value = "/system-dict-next/{parentId}", method = RequestMethod.POST)
    public Result systemDictNext(@PathVariable String parentId) {
        Result result = new Result();
        // 先查询redis
        Map<String, List<Town>> map = systemDictCache.get(SYSTEM_DICT_CACHE_KEY);
        if (MapUtils.isEmpty(map)) {
            initialSystemDict();
        }
        List<Town> townList = map.get(parentId);
        // 如果为空，从数据库查询
        if (CollectionUtils.isEmpty(townList)) {
            townList = systemDictService.querySystemDictNextByParentId(parentId);
        }
        if (CollectionUtils.isNotEmpty(townList)) {
            TownJson ton = new TownJson();
            result.setData(ton.convert(townList,map));
        }
        return result;
    }

    class TownJson {

        private String value;
        private String label;
        private List<TownJson> children;
        private Boolean loading;

        public TownJson() {
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }

        public List<TownJson> getChildren() {
            return children;
        }

        public void setChildren(List<TownJson> children) {
            this.children = children;
        }

        public Boolean isLoading() {
            return loading;
        }

        public void setLoading(Boolean loading) {
            this.loading = loading;
        }

        public List<TownJson> convert(List<Town> townList,Map<String, List<Town>> map) {
            List<TownJson> list = new ArrayList<>(townList.size());
            for (Town town : townList) {
                TownJson json = new TownJson();
                json.setLabel(town.getName());
                json.setValue(town.getId());
                if (map.containsKey(town.getId())) {
                    json.setLoading(false);
                    json.setChildren(new ArrayList<>());
                }
                list.add(json);
            }
            return list;
        }

        public List<TownJson> convert(List<Town> townList) {
            List<TownJson> list = new ArrayList<>(townList.size());
            for (Town town : townList) {
                TownJson json = new TownJson();
                json.setLabel(town.getName());
                json.setValue(town.getId());
                List<Town> childrenList = service.findTownByParentId(town.getId());
                if (childrenList.size() > 0) {
                    json.setLoading(false);
                    json.setChildren(new ArrayList<>());
                }
                list.add(json);
            }
            return list;
        }
       /* void setChildrenList(TownJson townJson, Map<String, List<Town>> map) {
            List<Town> townList = map.get(townJson.getValue());
            List<TownJson> children = townJson.getChildren();
            if (null == children) {
                children = new ArrayList<>();
            }
            if (null != townList) {
                for (Town town : townList) {
                    TownJson json = new TownJson();
                    json.setLabel(town.getName());
                    json.setValue(town.getId());
                    if (map.containsKey(town.getId())) {
                        townJson.setLoading(true);
                        json.setChildren(new ArrayList<>());
                    }
                    children.add(json);
                }
            }
            if (!children.isEmpty()) {
                townJson.setLoading(true);
                townJson.setChildren(children);
            }
        }*/
    }
}