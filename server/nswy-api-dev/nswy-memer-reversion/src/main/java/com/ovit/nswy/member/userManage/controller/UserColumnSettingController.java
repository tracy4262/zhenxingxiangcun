package com.ovit.nswy.member.userManage.controller;


import com.ovit.nswy.member.certification.service.ColumnSettingService;
import com.ovit.nswy.member.certification.service.LoginUserService;
import com.ovit.nswy.member.manage.service.TemplateConfigService;
import com.ovit.nswy.member.util.Result;
import com.ovit.nswy.member.util.ResultCode;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user/columnSetting")
public class UserColumnSettingController {

    private Logger logger = LogManager.getLogger(this.getClass());

    @Autowired
    private ColumnSettingService columnSettingService;

    @Autowired
    private TemplateConfigService templateConfigService;

    @Autowired
    private LoginUserService loginUserService;

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

            Map<String, Object> historyInfo = new HashMap<>();

            List<Map<String,Object>> websiteSettingsInfo = columnSettingService.findColumnSettingByAccount(accountInfo);

            if (CollectionUtils.isEmpty(websiteSettingsInfo)) {
                //无数据，初始化网站设置，返回对应类型的所有模板、模块

                historyInfo.put("id", templateId);
                List<Map<String,Object>> list  = templateConfigService.find(historyInfo);
                if(list.size()>0){
                    historyInfo.put("account", MapUtils.getString(list.get(0),"account"));
                }else{
                    historyInfo.put("account",MapUtils.getString(params,"account"));
                }
                historyInfo.put("templateId", templateId);

                websiteSettingsInfo = columnSettingService.findColumnSettingByAccount(historyInfo);
            }
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

            Map<String,Object> loginMap = MapUtils.getMap(params,"loginStep");

            //保存用户步骤
            loginUserService.saveOrUpdateStep(loginMap);

            logger.info("保存栏目设置信息成功");



        } catch (Exception e) {
            logger.error("栏目设置信息保存失败: {}", e);
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            result.setMsg("保存栏目设置信息失败");
        }
        return result;
    }


}
