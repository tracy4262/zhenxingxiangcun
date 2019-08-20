package com.ovit.nswy.member.certification.service.impl;

import com.ovit.nswy.member.certification.mapper.WebsiteSettingsMapper;
import com.ovit.nswy.member.certification.service.WebsiteSettingsService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class WebsiteSettingsServiceImpl implements WebsiteSettingsService {

    private Logger logger = LogManager.getLogger(this.getClass());

    @Autowired
    private WebsiteSettingsMapper websiteSettingsMapper;

    /**
     * 通过用户名查询用户的网站设置信息
     * @param accountInfo
     * @return
     */
    @Override
    public Map<String, Object> findWebsiteSettingsByAccount(Map<String, Object> accountInfo) {
        Map<String,Object> websiteSettingsInfo = websiteSettingsMapper.findWebsiteSettingsByAccount(accountInfo);
        logger.info("根据用户名及用户类型{}，查询的网站设置信息内容为：{}", accountInfo, websiteSettingsInfo);
        return websiteSettingsInfo;
    }

    /**
     * 查询对应角色下所有的网站模块、模板信息
     * @return
     */
    @Override
    public Map<String,Object> findAllWebsiteInfo(String userType) {

        List<Map<String,Object>> allWebsiteInfo = websiteSettingsMapper.findAllWebsiteInfo(userType);
        logger.info("查询的所有网站模板、模块信息内容为：{}", allWebsiteInfo);
        Map<String,Object> initializeWebsiteInfo = new HashMap<>();
        List<Map<String,Object>> templateList = new ArrayList<>();
        List<Map<String,Object>> moduleList = new ArrayList<>();

        for (Map<String,Object> website : allWebsiteInfo) {
            Map<String,Object> websiteMap = new HashMap<>();
            websiteMap.put("checked", false);

            int appType = Integer.parseInt(String.valueOf(website.get("app_type")));
            if (appType == 0) {
                //网站模板
                websiteMap.put("name", website.get("name"));
                websiteMap.put("background", website.get("image"));

                templateList.add(websiteMap);
            } else if (appType == 1) {
                //网站模块
                websiteMap.put("name", website.get("name"));
                websiteMap.put("src", website.get("image"));
                moduleList.add(websiteMap);
            }
        }
        initializeWebsiteInfo.put("templateData", templateList);
        initializeWebsiteInfo.put("moduleData", moduleList);

        logger.info("初始化应用设置数据为：{}", initializeWebsiteInfo);

        return initializeWebsiteInfo;
    }

    /**
     * 保存网站设置数据
     * @param websiteSettingsMap
     */
    @Override
    public void saveWebsiteSettings(Map<String, Object> websiteSettingsMap) {
        logger.info("需保存的网站设置信息内容为：{}", websiteSettingsMap);
        websiteSettingsMapper.saveWebsiteSettings(websiteSettingsMap);
    }

    /**
     * 更新网站设置数据
     * @param websiteSettingsMap
     */
    @Override
    public void updateWebsiteSettings(Map<String, Object> websiteSettingsMap) {
        logger.info("需更新的网站设置信息内容为：{}", websiteSettingsMap);
        websiteSettingsMapper.updateWebsiteSettings(websiteSettingsMap);
    }

    /**
     * 更新认证步骤
     * @param params
     */
    @Override
    public void updateIdentityFlag(Map<String, Object> params) {

        websiteSettingsMapper.updateIdentityFlag(params);

    }

}
