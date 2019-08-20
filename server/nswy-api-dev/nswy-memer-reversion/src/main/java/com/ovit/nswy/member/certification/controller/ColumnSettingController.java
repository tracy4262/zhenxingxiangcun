package com.ovit.nswy.member.certification.controller;


import com.alibaba.fastjson.JSONArray;
import com.ovit.nswy.member.certification.service.ColumnSettingService;
import com.ovit.nswy.member.certification.service.RealCertificationService;
import com.ovit.nswy.member.util.Result;
import com.ovit.nswy.member.util.ResultCode;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/columnSetting")
public class ColumnSettingController {

    private Logger logger = LogManager.getLogger(this.getClass());

    @Autowired
    private ColumnSettingService columnSettingService;

    /**
     * 通过用户名查询栏目设置信息
     *
     */
    @RequestMapping(value = "/findColumnSettingInfo", method = RequestMethod.POST)
    public Result findWebsiteSettingsInfo(@RequestBody Map<String,Object> params) {
        Result result = new Result();
        try {
            String account = MapUtils.getString(params,"account");
            String templateId = MapUtils.getString(params,"templateId");
            Map<String,Object> accountInfo = new HashMap<>();
            accountInfo.put("account", account);
            accountInfo.put("templateId", templateId);

            List<Map<String,Object>> websiteSettingsInfo = columnSettingService.findColumnSettingByAccount(accountInfo);

            if (CollectionUtils.isNotEmpty(websiteSettingsInfo)) {
                //已认证过栏目设置步骤，存在数据
                Map<String,Object> resultMap = new HashMap<>();
                String templateType = MapUtils.getString(websiteSettingsInfo.get(0), "templateType");
                resultMap.put("templateType", templateType);
                for (Map<String,Object> map : websiteSettingsInfo) {
                    map.put("display", "1".equals(MapUtils.getString(map,"display")) ? true : false);
                }
                resultMap.put("columnSetting", websiteSettingsInfo);
                result.setMsg("查询用户设置的栏目信息成功");
                result.setData(resultMap);
            } else {
                //无数据，初始化网站设置，返回对应类型的所有模板、模块
                Map<String,Object> resultMap = defaultColumnSetting();
                result.setMsg("查询系统默认栏目信息成功");
                result.setData(resultMap);
            }

        } catch (Exception e) {
            logger.error("查询栏目设置失败:{}", e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg("查询栏目设置信息失败");
        }
        logger.info("查询栏目设置信息为： {}", result.getData());
        return result;
    }

    /**
     * 保存或更新应用
     * @param params
     * @return
     */
    @RequestMapping(value = "/saveOrUpdateSettingInfo", method = RequestMethod.POST)
    public Result saveOrUpdateSettingInfo(@RequestBody Map<String, Object> params) {
        Result result = new Result();
        try {
            columnSettingService.saveOrUpdateSettingInfo(params);
            logger.info("保存栏目设置信息成功");

        } catch (Exception e) {
            logger.error("栏目设置信息保存失败: {}", e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg("保存栏目设置信息失败");
        }
        return result;
    }


    /**
     * 栏目设置的默认栏目
     * @return
     */
    private Map<String,Object> defaultColumnSetting() {

        Map<String, Object> defaultMap = new HashMap<>();
        defaultMap.put("templateType", "D");

        List<Map<String,Object>> defaultList = new ArrayList<>();
        Map<String,Object> memberIntroduction = new HashMap<>();
        memberIntroduction.put("columnName", "会员介绍");
        memberIntroduction.put("attribution", "");
        memberIntroduction.put("display", true);
        memberIntroduction.put("authority", 0);
        defaultList.add(memberIntroduction);

        Map<String,Object> dynamic = new HashMap<>();
        dynamic.put("columnName", "我的动态");
        dynamic.put("attribution", "");
        dynamic.put("display", true);
        dynamic.put("authority", 0);
        defaultList.add(dynamic);

        Map<String,Object> policy = new HashMap<>();
        policy.put("columnName", "会员政策");
        policy.put("attribution", "");
        policy.put("display", true);
        policy.put("authority", 0);
        defaultList.add(policy);

        Map<String,Object> knowledge = new HashMap<>();
        knowledge.put("columnName", "会员知识");
        knowledge.put("attribution", "");
        knowledge.put("display", true);
        knowledge.put("authority", 0);
        defaultList.add(knowledge);

        Map<String,Object> product = new HashMap<>();
        product.put("columnName", "会员产品");
        product.put("attribution", "");
        product.put("display", true);
        product.put("authority", 0);
        defaultList.add(product);

        Map<String,Object> service = new HashMap<>();
        service.put("columnName", "会员服务");
        service.put("attribution", "");
        service.put("display", true);
        service.put("authority", 0);
        defaultList.add(service);

        defaultMap.put("columnSetting", defaultList);

        return defaultMap;
    }

    /**
     * 获取栏目字典
     * @return
     */
    @RequestMapping(value = "/columnDict", method = RequestMethod.GET)
    public Result columnDict() {
        Result result = new Result();
        try {
            Map<String,Object> resultMap = columnSettingService.columnDict();
            result.setData(resultMap);
            result.setMsg("栏目字典查询成功");

        } catch (Exception e) {
            logger.error("栏目字典查询失败: {}", e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg("栏目字典查询失败");
        }
        return result;
    }

}
