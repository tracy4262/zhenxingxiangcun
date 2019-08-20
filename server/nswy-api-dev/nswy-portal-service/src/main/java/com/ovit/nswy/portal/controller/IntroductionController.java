package com.ovit.nswy.portal.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.github.xphsc.collect.Maps;
import com.github.xphsc.json.JSONHelper;
import com.ovit.nswy.portal.core.Result;
import com.ovit.nswy.portal.core.ResultCode;
import com.ovit.nswy.portal.feign.RelatedServiceFeign;
import com.ovit.nswy.portal.model.SpeciesQuery;
import com.ovit.nswy.portal.model.SpeciesResult;
import com.ovit.nswy.portal.model.WorkInfo;
import com.ovit.nswy.portal.service.IntroductionService;
import com.ovit.nswy.portal.util.ConstantPool;
import com.ovit.nswy.portal.util.ServiceUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 我的门户之介绍
 *
 * @author haoWen
 * @create 2018-01-12 13:32
 **/
@RestController
@RequestMapping("/introduction")
public class IntroductionController implements ConstantPool {
    @Autowired
    private IntroductionService introductionService;

    @Autowired
    private RelatedServiceFeign relatedServiceFeign;
    private Logger logger = LogManager.getLogger(this.getClass());

    /**
     * 通过用户查询简介
     * @param params
     * @return
     */
    @RequestMapping(value = "/getInfo",method = RequestMethod.POST)
    public Result getIntroductionInfo(@RequestBody Map<String,Object> params){
        Result result = null;
        try {
            logger.debug("查询简介信息：{}",params);
            result = new Result();
            Map<String,Object> infoMap = introductionService.getIntroduction(params);
            logger.info("查询简介信息成功：{}",infoMap);
            result.setData(infoMap);
        } catch (Exception e) {
           result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
           logger.error("查询简介信息失败：{}",e);
        }
        return result;
    }

    /**
     * 荣誉查询
     * @param params
     * @return
     */
    @RequestMapping(value = "/honor-aptitude",method = RequestMethod.POST)
    public Result getHonorInfoOrAptitude(@RequestBody Map<String,Object> params){
        Result result = null;
        try {
            result = new Result();
            logger.debug("查询荣誉/资质信息：{}",params);
            PageInfo<List<Map<String,Object>>> infoMap = introductionService.getHonorInfoOrAptitude(params);
            logger.info("查询荣誉/资质信息成功：{}",infoMap);
            result.setData(infoMap);
        } catch (Exception e) {
           result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
           logger.error("荣誉/资质查询失败：{}",e);
        }
        return result;
    }
    /**
     * 查询机关基本信息
     * @param params
     * @return
     */
    @RequestMapping(value = "/getGovBaseInfo",method = RequestMethod.POST)
    public Result getGovBaseInfo(@RequestBody Map<String,Object> params){
        Result result = null;
        try {
            result = new Result();
            logger.debug("查询机关基本信息：{}",params);
            Map<String,Object> map = introductionService.getGovBaseInfo(params);
            logger.info("查询机关基本信息成功：{}",map);
            result.setData(map);
        } catch (Exception e) {
           result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
           logger.error("查询机关基本信息失败：{}",e);
        }
        return result;
    }

    /**
     * 个人文档
     * @return
     */
    @RequestMapping(value = "/getOwnerBaseInfo",method = RequestMethod.POST)
    public Result getOwnerBaseInfo(@RequestBody Map<String,Object> params){
        Result result = null;
        try {
            result = new Result();
            logger.debug("查询个人档：{}",params);
            Map<String,Object> map = introductionService.getOwnerBaseInfo(params);
            String workInfo = MapUtils.getString(map,"work");
            if (StringUtils.isNotEmpty(workInfo)) {
                List<Object> workList = JSON.parseArray(workInfo,Object.class);
                for (Object obj : workList) {
                    logger.info("obj:{}",obj);
                    Map<String,Object>maps =  (Map<String,Object>)obj;
                    List<Map<String,Object>> work = (List<Map<String,Object>>)maps.get("children");
                    map.put("workName",work.get(0).get("value"));
                    map.put("workStatus",work.get(0).get("status"));
                }
            }

            if (MapUtils.isNotEmpty(map)) {
                //我关注的人
                int myFollow = introductionService.queryMyFollow(params);
                //我的粉丝
                int myFans = introductionService.queryMyFans(params);
                map.put("myFollow",myFollow);
                map.put("myFans",myFans);
                map.put("FName",getSpecies(MapUtils.getString(map,"species")));
            }
            logger.info("查询个人档成功：{}",map);
            result.setData(map);
        } catch (Exception e) {
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            logger.error("查询个人档失败：{}",e);
        }
        return result;
    }


    /**
     * 个人联系方式
     * @param params
     * @return
     */
    @RequestMapping(value = "/getOwnerContact",method = RequestMethod.POST)
    public Result getOwnerContact(@RequestBody Map<String,Object> params){
        Result result = null;
        try {
            result = new Result();
            logger.debug("查询个人联系方式：{}",params);
            Map<String,Object> map = introductionService.queryOwnerContact(params);
            logger.info("查询个人联系方式成功：{}",map);
            result.setData(map);
        } catch (Exception e) {
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            logger.error("查询个人联系方式失败：{}",e);
        }
        return result;
    }

    /**
     * 专业信息
     * @param params
     * @return
     */
    @RequestMapping(value = "/getSpecial",method = RequestMethod.POST)
    public Result getSpecial(@RequestBody Map<String,Object> params){
        Result result = null;
        try {
            result = new Result();
            logger.debug("专家专业信息：{}",params);
            Map map = introductionService.getSpecial(params);
            map.put("adeptSpecies",getSpecies(MapUtils.getString(map,"adeptSpecies")));
            map.put("relatedSpecies",getSpecies(MapUtils.getString(map,"relatedSpecies")));
            map.put("relatedService", ServiceUtils.getServiceNameByIdArray(MapUtils.getString(map,"relatedService")));
            logger.info("专家专业信息查询成功：{}",map);
            result.setData(map);
        } catch (Exception e) {
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            logger.error("专家专业信息查询失败：{}",e);
        }
        return result;
    }


    /**
     * 通过物种ID查询物种名称
     * @param name
     * @return
     */
    private String getSpecies(String name){
        StringBuffer FName = new StringBuffer();
        if (StringUtils.isNotEmpty(name)) {
            String[] speciesArray = name.split(" ");
            for (int i = 0; i < speciesArray.length; i++) {
                SpeciesQuery speciesQuery = new SpeciesQuery();
                speciesQuery.setSpeciesid(speciesArray[i]);
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
     * 个人荣誉
     * @return
     */
    @RequestMapping(value = "/getOwnerHonor",method = RequestMethod.POST)
    public Result getOwnerHonor(@RequestBody Map<String,Object> params){
        Result result = null;
        try {
            result = new Result();
            logger.debug("个人荣誉：{}",params);
            List<Map<String,Object>> list = new ArrayList<>();
            List<Map<String,Object>> mapList = introductionService.queryOwnerHonor(params);
            for (Map<String,Object> map : mapList) {
                map.put("honor",StringUtils.join(
                        "荣誉名称：",
                        MapUtils.getString(map,"honor"),
                        "  获得时间：",
                        MapUtils.getString(map,"time")
                ));
                list.add(map);
            }
            logger.info("个人荣誉：{}",params);
            result.setData(list);
        } catch (Exception e) {
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            logger.error("个人荣誉查询失败：{}",e);
        }
        return  result;
    }
}
