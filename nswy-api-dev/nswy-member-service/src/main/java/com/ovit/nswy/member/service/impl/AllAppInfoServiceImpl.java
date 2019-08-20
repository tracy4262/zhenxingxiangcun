package com.ovit.nswy.member.service.impl;

import com.ovit.nswy.member.mapper.AllAppInfoMapper;
import com.ovit.nswy.member.model.AllAppInfo;
import com.ovit.nswy.member.service.AllAppInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class AllAppInfoServiceImpl implements AllAppInfoService {
    @Autowired
    private AllAppInfoMapper AllAppInfoDao;

    /**
     * 查询所有的应用数据
     * @return
     */
    @Override
    public List<AllAppInfo> selectAllAppInfo(int level) {
        return AllAppInfoDao.selectAllAppInfo(level);
    }

    /**
     * 根据账户查询相应的应用数据
     * @param account
     * @return
     */
    @Override
    public List<AllAppInfo> selectByAccount(Map<String, Object> appInfo) {
        return AllAppInfoDao.selectByAccount(appInfo);
    }

}
