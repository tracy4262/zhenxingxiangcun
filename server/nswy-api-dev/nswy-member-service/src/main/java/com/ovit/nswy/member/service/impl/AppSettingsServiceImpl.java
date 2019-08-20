package com.ovit.nswy.member.service.impl;

import com.ovit.nswy.member.mapper.AppSettingsMapper;
import com.ovit.nswy.member.mapper.CorpCertificationMapper;
import com.ovit.nswy.member.service.AppSettingsService;
import com.ovit.nswy.member.service.CorpCertificationService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AppSettingsServiceImpl implements AppSettingsService {

    private Logger logger = LogManager.getLogger(this.getClass());

    @Autowired
    private AppSettingsMapper appSettingsMapper;

    /**
     * 通过用户名查询用户的应用设置信息
     * @param account
     * @return
     */
    @Override
    public Map<String, Object> findAppSettingsByAccount(String account) {
        Map<String,Object> appSettingsInfo = appSettingsMapper.findAppSettingsByAccount(account);
        logger.info("根据用户名{}，查询的应用设置信息内容为：{}", account, appSettingsInfo);
        return appSettingsInfo;
    }

    /**
     * 通过初始化的应用设置信息
     * @return
     */
    @Override
    public Map<String,List<Map<String,Object>>> findAllAppInfo() {

        List<Map<String,Object>> allAppInfo = appSettingsMapper.findAllAppInfo();
        logger.info("查询的所有应用设置信息内容为：{}", allAppInfo);
        Map<String,List<Map<String,Object>>> initializeAppInfo = new HashMap<>();
        List<Map<String,Object>> basicAppList = new ArrayList<>();
        List<Map<String,Object>> advancedAppList = new ArrayList<>();
        List<Map<String,Object>> thirdAppList = new ArrayList<>();

        for (Map<String,Object> app : allAppInfo) {
            Map<String,Object> AppMap = new HashMap<>();
            AppMap.put("checked", false);
            AppMap.put("name", app.get("appName"));
            AppMap.put("src", app.get("src"));
            int level = Integer.parseInt(String.valueOf(app.get("level")));
            if (level == 0) {
                //基础应用, 默认全部选中
                AppMap.put("checked", true);
                basicAppList.add(AppMap);
            } else if (level == 1) {
                //高级应用
                advancedAppList.add(AppMap);
            } else if (level == 3) {
                //第三方应用
                thirdAppList.add(AppMap);
            }
        }
        initializeAppInfo.put("basicAppData", basicAppList);
        initializeAppInfo.put("advancedAppData", advancedAppList);
        initializeAppInfo.put("thirdAppData", thirdAppList);

        logger.info("初始化应用设置数据为：{}", initializeAppInfo);

        return initializeAppInfo;
    }

    /**
     * 保存应用设置数据
     * @param appSettingsMap
     */
    @Override
    public void saveAppSettings(Map<String, Object> appSettingsMap) {
        logger.info("需保存的应用设置信息内容为：{}", appSettingsMap);
        appSettingsMapper.saveAppSettings(appSettingsMap);
    }

    /**
     * 更新应用设置数据
     * @param appSettingsMap
     */
    @Override
    public void updateAppSettings(Map<String, Object> appSettingsMap) {
        logger.info("需更新的应用设置信息内容为：{}", appSettingsMap);
        appSettingsMapper.updateAppSettings(appSettingsMap);
    }

}
