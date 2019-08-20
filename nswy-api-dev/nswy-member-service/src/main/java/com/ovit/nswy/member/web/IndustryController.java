package com.ovit.nswy.member.web;

import com.github.pagehelper.PageInfo;
import com.ovit.nswy.frame.common.Constants;
import com.ovit.nswy.member.model.Industrial;
import com.ovit.nswy.member.service.IndustrialService;
import com.ovit.nswy.member.web.base.Result;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/industry")
public class IndustryController {
    /**
     *
     */
    private Logger logger = LogManager.getLogger(this.getClass());

    @Autowired
    private IndustrialService industrialService;

    @Autowired
    private RedisTemplate<String, Map<String, List<Industrial>>> redisTemplate;

    @PostConstruct
    public void initial() {
        Map<String, List<Industrial>> map = redisTemplate.opsForValue().get("industryClass");
        // 如果为空，从数据库查询
        if (null == map || map.isEmpty()) {
            List<Industrial> townList = industrialService.getIndustryType();
            map = new HashMap<String, List<Industrial>>();
            for (Industrial industrial : townList) {
                String key = industrial.getPid();
                if (StringUtils.isEmpty(key)) {
                    continue;
                }
                if (map.containsKey(key)) {
                    List<Industrial> tmp = map.get(key);
                    tmp.add(industrial);
                } else {
                    List<Industrial> tmp = new ArrayList<Industrial>();
                    tmp.add(industrial);
                    map.put(key, tmp);
                }
            }
            redisTemplate.opsForValue().set("industryClass", map);
        }
    }

    @RequestMapping(value = "/findAll", method = RequestMethod.GET)
    public Result findAllIndustry(HttpServletRequest request) {
        Result result = new Result();
        List<Industrial> list = industrialService.getIndustryType();
        result.setData(list);
        return result;
    }

    @RequestMapping(value = "/findChildren/{parentId}", method = RequestMethod.GET)
    public Result findIndustryByParentId(@PathVariable String parentId, HttpServletRequest request) {
        Result result = new Result();
        List<Industrial> list = industrialService.findIndustryByParentId(parentId);
        result.setData(list);
        return result;
    }


    @RequestMapping(value = "/next/{parentId}/{pageNum}", method = RequestMethod.POST)
    public Result next(@PathVariable String parentId, @PathVariable int pageNum, HttpServletRequest request) {
        Result result = new Result();
        //LoginUser loginUser = (LoginUser) request.getSession().getAttribute("currentUser");
        // 先查询redis
        Map<String, List<Industrial>> map = redisTemplate.opsForValue().get("industryClass");
        if (null == map || map.isEmpty()) {
            initial();
        }
        List<Industrial> list = map.get(parentId);
        // 如果为空，从数据库查询
        if (null == list) {
            list = industrialService.findIndustryByParentId(parentId);
            map.put(parentId, list);
            redisTemplate.opsForValue().set("industryClass", map);
        }
        if (null != list) {
            IndustryJson ton = new IndustryJson();
            PageInfo<IndustryJson> pageInfo = ton.convert(list, pageNum);
            result.setData(pageInfo);
        }
        return result;
    }


    @RequestMapping(value = "/findByCharacter", method = RequestMethod.POST)
    public Result findByCharacter(@RequestBody Map<String, Object> params, HttpServletRequest request) {
        Result result = new Result();
        //LoginUser loginUser = (LoginUser) request.getSession().getAttribute("currentUser");
        Industrial industrial = new Industrial();
        String cha = (String) params.get("character");
        if (!StringUtils.isEmpty(cha)) {
            industrial.setPinyin(cha);
        }
        String keyword = (String) params.get("keyword");
        if (!StringUtils.isEmpty(keyword)) {
            industrial.setName(keyword);
        }
        String parentId = (String) params.get("parentId");
        if (!StringUtils.isEmpty(parentId)) {
            industrial.setPid(parentId);
        }
        List<Industrial> list = industrialService.findIndustry(industrial);
        // 如果为空，从数据库查询
        if (null != list) {
            IndustryJson ton = new IndustryJson();
            List<IndustryJson> lists = ton.convert(list);
            result.setData(lists);
        }
        return result;
    }


    class IndustryJson {

        private String value;
        private String label;
        private List<IndustryJson> children;
        private Boolean loading;
        private int items;
        private int page;

        public IndustryJson() {
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

        public List<IndustryJson> getChildren() {
            return children;
        }

        public void setChildren(List<IndustryJson> children) {
            this.children = children;
        }

        public Boolean isLoading() {
            return loading;
        }

        public void setLoading(Boolean loading) {
            this.loading = loading;
        }

        public List<IndustryJson> convert(List<Industrial> townList) {
            List<IndustryJson> list = new ArrayList<IndustryJson>(townList.size());
            for (Industrial industrial : townList) {
                IndustryJson json = new IndustryJson();
                json.setLabel(industrial.getName());
                json.setValue(industrial.getId());
                //setChildrenList(json, map);
                list.add(json);
            }
            return list;
        }

        public PageInfo<IndustryJson> convert(List<Industrial> townList, int pageNum) {
            List<IndustryJson> list = new ArrayList<IndustryJson>(townList.size());
            Map<String, List<Industrial>> map = redisTemplate.opsForValue().get("industryClass");
            for (Industrial town : townList) {
                IndustryJson json = new IndustryJson();
                json.setLabel(town.getName());
                json.setValue(town.getId());
                if (map.containsKey(town.getId())) {
                    json.setLoading(false);
                    json.setChildren(new ArrayList<IndustryJson>());
                }
                //setChildrenList(json, map);
                list.add(json);
            }
            PageInfo<IndustryJson> pageInfo = new PageInfo<IndustryJson>();
            pageInfo.setTotal(list.size());
            pageInfo.setPageNum(pageNum);
            pageInfo.setPageSize(Constants.DEFAUL_PAGE_SIZE);
            calc(list.size(), pageNum);
            pageInfo.setList(list.subList(getStartRow(), getEndRow()));
            return pageInfo;
        }

        private void calc(int items, int pageNum) {
            this.items = (items >= 0) ? items : 0;
            this.page = calcPage(pageNum);
        }

        private int calcPage(int page) {
            int pages;
            if (items <= 0) {
                return 1;
            }
            pages = (int) Math.ceil((double) items / Constants.DEFAUL_PAGE_SIZE);
            if (pages > 0) {
                return (page < 1) ? 1 : ((page > pages) ? pages : page);
            }
            return 0;
        }

        private int getStartRow() {
            int startRow;
            if (page > 0) {
                startRow = (Constants.DEFAUL_PAGE_SIZE * (page - 1));
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
                endRow = Math.min(Constants.DEFAUL_PAGE_SIZE * page, items);
            } else {
                endRow = 0;
            }
            return endRow;
        }
    }

}
