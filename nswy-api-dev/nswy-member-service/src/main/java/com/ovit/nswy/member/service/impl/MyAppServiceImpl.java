package com.ovit.nswy.member.service.impl;

import com.ovit.nswy.member.mapper.MyAppMapper;
import com.ovit.nswy.member.model.MyApp;
import com.ovit.nswy.member.service.MyAppService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class MyAppServiceImpl implements MyAppService {
    @Autowired
    private MyAppMapper MyAppIDao;

    @Override
    public int insert(List<MyApp> record) {
        return MyAppIDao.insert(record);
    }

    @Override
    public List<MyApp> selectByAccount(String account) {
        return MyAppIDao.selectByAccount(account);
    }

    @Override
    public List<MyApp> selectByLevel(Integer level) {
        return MyAppIDao.selectByLevel(level);
    }

    @Override
    public void deleteByLevel(Integer level) {
        MyAppIDao.deleteByLevel(level);
    }

    @Override
    public void deleteAll(Map<String, Object> map) {
        MyAppIDao.deleteAll(map);
    }

    @Override
    public int queryAppUseStatus(Map<String, Object> params) {
        String statusId = MyAppIDao.queryAppUseStatus(params);
        if (StringUtils.isNotEmpty(statusId)) {
            return Integer.parseInt(statusId);
        }
        return 0;
    }

    @Override
    public void saveAppStatus(Map<String, Object> params) {
        MyAppIDao.saveAppStatus(params);
    }

    @Override
    public List<Map<String, Object>> queryMyAppByAccount(Map<String, Object> params) {
        return MyAppIDao.queryMyAppByAccount(params);
    }

    @Override
    public Map<String, Object> queryAppUseStatusCountByAccount(Map<String, Object> params) {
        return MyAppIDao.queryAppUseStatusCountByAccount(params);
    }

    @Override
    public void updateByAccount(Map<String, Object> params) {
        MyAppIDao.updateByAccount(params);
    }

}
