package com.ovit.nswy.member.perfectInfo.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.ovit.nswy.member.perfectInfo.service.EcoSocialDevelopService;
import com.ovit.nswy.member.util.Result;
import com.ovit.nswy.member.util.ResultCode;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;
import com.ovit.nswy.member.util.Pager;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * 经济社会发展
 */
@RestController
@RequestMapping("/ecoSocial")
public class EcoSocialDevelopController {

    private Logger logger = LogManager.getLogger(this.getClass());

    @Autowired
    private EcoSocialDevelopService ecoSocialDevelopService;

    @Autowired
    private RedisTemplate<String, List<Map<String,Object>>> ecoRedis;

    /**
     * 产业信息查询
     * @param params
     * @return
     */
    @RequestMapping(value = "/findIndustry", method = RequestMethod.POST)
    public Result findIndustry(@RequestBody Map<String, Object> params) {
        Result result = new Result();
        try {
            Map<String, Object> industry = ecoSocialDevelopService.findIndustry(params);
            result.setMsg("产业信息查询成功");
            result.setData(industry);
        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     * 插入/更新-产业信息
     * @param params
     * @return
     */
    @RequestMapping(value = "/saveIndustry",method = RequestMethod.POST)
    public Result saveIndustry(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            ecoSocialDevelopService.saveIndustry(params);
            result.setMsg("产业信息保存成功");

        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     * 删除-产业信息
     * @param params
     * @return
     */
    @RequestMapping(value = "/deleteIndustry",method = RequestMethod.POST)
    public Result deleteIndustry(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            ecoSocialDevelopService.deleteIndustry(params);
            result.setMsg("产业信息删除成功");

        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     * 种养信息查询
     * @param params
     * @return
     */
    @RequestMapping(value = "/findBreed", method = RequestMethod.POST)
    public Result findBreed(@RequestBody Map<String, Object> params) {
        Result result = new Result();
        try {
            Map<String, Object> breed = ecoSocialDevelopService.findBreed(params);
            result.setMsg("种养信息查询成功");
            result.setData(breed);
        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     * 插入/更新-种养信息
     * @param params
     * @return
     */
    @RequestMapping(value = "/saveBreed",method = RequestMethod.POST)
    public Result saveBreed(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            ecoSocialDevelopService.saveBreed(params);
            result.setMsg("种养信息保存成功");

        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     * 删除-种养信息
     * @param params
     * @return
     */
    @RequestMapping(value = "/deleteBreed",method = RequestMethod.POST)
    public Result deleteBreed(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            ecoSocialDevelopService.deleteBreed(params);
            result.setMsg("种养信息删除成功");

        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     * 农产品信息查询
     * @param params
     * @return
     */
    @RequestMapping(value = "/findFarmProduct", method = RequestMethod.POST)
    public Result findFarmProduct(@RequestBody Map<String, Object> params) {
        Result result = new Result();
        try {
            Map<String, Object> farmProduct = ecoSocialDevelopService.findFarmProduct(params);
            result.setMsg("农产品信息查询成功");
            result.setData(farmProduct);
        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     * 插入/更新-农产品信息
     * @param params
     * @return
     */
    @RequestMapping(value = "/saveFarmProduct",method = RequestMethod.POST)
    public Result saveFarmProduct(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            ecoSocialDevelopService.saveFarmProduct(params);
            result.setMsg("农产品信息保存成功");

        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     * 删除-农产品信息
     * @param params
     * @return
     */
    @RequestMapping(value = "/deleteFarmProduct",method = RequestMethod.POST)
    public Result deleteFarmProduct(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            ecoSocialDevelopService.deleteFarmProduct(params);
            result.setMsg("农产品信息删除成功");

        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     * 工业产品信息查询
     * @param params
     * @return
     */
    @RequestMapping(value = "/findTechnicalProduct", method = RequestMethod.POST)
    public Result findTechnicalProduct(@RequestBody Map<String, Object> params) {
        Result result = new Result();
        try {
            Map<String, Object> technicalProduct = ecoSocialDevelopService.findTechnicalProduct(params);
            result.setMsg("工业产品信息查询成功");
            result.setData(technicalProduct);
        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     * 插入/更新-工业产品信息
     * @param params
     * @return
     */
    @RequestMapping(value = "/saveTechnicalProduct",method = RequestMethod.POST)
    public Result saveTechnicalProduct(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            ecoSocialDevelopService.saveTechnicalProduct(params);
            result.setMsg("工业产品信息保存成功");

        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     * 删除-工业产品信息
     * @param params
     * @return
     */
    @RequestMapping(value = "/deleteTechnicalProduct",method = RequestMethod.POST)
    public Result deleteTechnicalProduct(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            ecoSocialDevelopService.deleteTechnicalProduct(params);
            result.setMsg("工业产品信息删除成功");

        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     * 服务产品信息查询
     * @param params
     * @return
     */
    @RequestMapping(value = "/findServiceProduct", method = RequestMethod.POST)
    public Result findServiceProduct(@RequestBody Map<String, Object> params) {
        Result result = new Result();
        try {
            Map<String, Object> serviceProduct = ecoSocialDevelopService.findServiceProduct(params);
            result.setMsg("服务产品信息查询成功");
            result.setData(serviceProduct);
        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     * 插入/更新-服务产品信息
     * @param params
     * @return
     */
    @RequestMapping(value = "/saveServiceProduct",method = RequestMethod.POST)
    public Result saveServiceProduct(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            ecoSocialDevelopService.saveServiceProduct(params);
            result.setMsg("服务产品信息保存成功");

        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     * 删除-服务产品信息
     * @param params
     * @return
     */
    @RequestMapping(value = "/deleteServiceProduct",method = RequestMethod.POST)
    public Result deleteServiceProduct(@RequestBody Map<String, Object> params){
        Result result = new Result();
        try {
            ecoSocialDevelopService.deleteServiceProduct(params);
            result.setMsg("服务产品信息删除成功");

        } catch (Exception e) {
            logger.error("数据异常：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    /**
     * 查询国民经济行业分类表
     * @param params
     * @return
     */
    @RequestMapping(value = "/findEcoClassify", method = RequestMethod.POST)
    public Result findEcoClassify(@RequestBody Map<String, Object> params) {
        Result result = null;
        try {
            result = new Result();
            logger.info("查询国民经济行业分类表,参数：{}",params);
            String type = MapUtils.getString(params,"type");
            String keywords = MapUtils.getString(params,"keywords");
            String fclassifiedid = MapUtils.getString(params,"fclassifiedid");
            String pinYin = MapUtils.getString(params,"fpinyin");
            int pageNum = MapUtils.getInteger(params,"pageNum");
            int pageSize = MapUtils.getInteger(params,"pageSize");
            params.remove("pageNum");
            params.remove("pageSize");
            List<String> queryList = JSON.parseArray(fclassifiedid).toJavaList(String.class);
            List<Map<String,Object>> resultList = new ArrayList<>();
            if (queryList.size() == 0) {
                queryList.add("0");
                String redisKey = "ecoClass";
                resultList = ecoRedis.opsForValue().get(redisKey);
                if (CollectionUtils.isEmpty(resultList)) {
                    params.put("ids", queryList);
                    resultList = ecoSocialDevelopService.findEco(params);
                    ecoRedis.opsForValue().set(redisKey, resultList);
                    //设置过期时间为1天
                    ecoRedis.expire(redisKey, 1, TimeUnit.DAYS);
                }
            } else {
                params.put("ids", queryList);
                resultList = ecoSocialDevelopService.findEco(params);
            }
            int totalSize = 0;
            if (CollectionUtils.isNotEmpty(resultList)) {
                totalSize = resultList.size();
            }
            PageInfo<Map<String,Object>> pageInfo = new PageInfo<>();
            pageInfo.setList(new Pager(resultList, pageSize).calc(pageNum));
            pageInfo.setTotal(totalSize);
            pageInfo.setPageSize(pageSize);
            pageInfo.setPageNum(pageNum);
            result.setData(pageInfo);
            result.setMsg("国民经济行业分类数据查询成功");
        } catch (Exception e) {
            logger.error("查询国民经济行业分类数据失败：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg("查询国民经济行业分类数据失败");
        }
        return result;
    }

    /**
     * 根据上级获取下级数据
     *
     * @param parentId
     * @param request
     * @return
     */
    @RequestMapping(value = "/findByParentId/{parentId}", method = RequestMethod.POST)
    public Result findByParentId(@PathVariable String parentId) {

        Result result = null;
        try {
            result = new Result();
            List<Map<String,Object>> list = ecoSocialDevelopService.findByParentId(parentId);
            result.setData(list);
            result.setMsg("子集数据查询成功");
        } catch (Exception e) {
            logger.error("子集数据查询失败：{}",e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg("子集数据查询失败");
        }
        return result;
    }




}
